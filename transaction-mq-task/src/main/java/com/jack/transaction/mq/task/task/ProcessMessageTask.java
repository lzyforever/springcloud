package com.jack.transaction.mq.task.task;

import com.jack.transaction.mq.client.TransactionMqRemoteClient;
import com.jack.transaction.mq.client.pojo.TransactionMessage;
import com.jack.transaction.mq.task.pojo.Message;
import com.jack.transaction.mq.task.publish.Publisher;
import com.jack.transaction.mq.task.utils.JsonUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 发送消息任务
 */
@Service
public class ProcessMessageTask {
    private static final Logger logger = LoggerFactory.getLogger(ProcessMessageTask.class);

    @Autowired
    private TransactionMqRemoteClient transactionMqRemoteClient;

    @Autowired
    private Publisher publisher;

    @Autowired
    private RedissonClient redissonClient;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    private Semaphore semaphore = new Semaphore(20);

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final RLock lock = redissonClient.getLock("transaction-mq-task");
                    try {
                        lock.lock();
                        System.out.println("开始发送消息：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        int sleepTime = process();
                        if (sleepTime > 0) {
                            Thread.sleep(10000);
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }).start();
    }

    private int process() throws Exception {
        // 默认执行完之后等待10秒
        int sleepTime = 10000;
        int limit = 5000;
        // 从消息服务获取5000条没有被消费的消息
        List<TransactionMessage> messageList = transactionMqRemoteClient.findByWaitingMessage(limit);
        // 如果拿到了5000条就证明还有其余没有被消费的消息
        if (messageList.size() == limit) {
            // 就把休眠的时间改为0，不进行休眠操作
            sleepTime = 0;
        }
        // 使用CountDownLatch来保证这一批数据都处理完成之后再执行下面的逻辑
        final CountDownLatch latch = new CountDownLatch(messageList.size());
        for (final TransactionMessage message : messageList) {
            // 使用Semaphore来控制处理的速度
            // 获取许可,其底层实现与CountDownLatch.countdown()类似
            semaphore.acquire();
            // 通过线程的方式丢到线程池中进行处理
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        doProcess(message);
                    } catch (Exception e) {
                        logger.error("", e);
                    } finally {
                        // 释放许可,其底层实现与acquire()是一个互逆的过程
                        semaphore.release();
                        // 将 CountDownLatch 中count 值减1
                        latch.countDown();
                    }
                }
            });
        }
        // 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        latch.await();
        return sleepTime;
    }

    private void doProcess(TransactionMessage message) {
        // 检查此消息是否满足死亡条件
        if (message.getSendCount() > message.getDieCount()) {
            // 如果发送的次数超出了设定的死亡次数，就把这条消息改成死亡消息，不再进行处理
            transactionMqRemoteClient.confirmDieMessage(message.getId());
            return;
        }

        long currentTime = System.currentTimeMillis();
        long sendTime = 0;
        if (message.getSendDate() != null) {
            sendTime = message.getSendDate().getTime();
        }
        // 距离上次发送时间超过一分钟才继续发送，这个一分钟的数值可以定义成配置的
        // 如果想做得更灵活，可以把这个时间当成消息的一个字段，与死亡次数一样，让使用者来定义即可
        if (currentTime - sendTime > 60000) {
            System.out.println("发送具体消息：messageId：" + message.getId() + " messageContent：" + message.getMessage());
            // 向MQ发送消息
            Message msg = new Message();
            msg.setMessageId(message.getId());
            msg.setMessage(message.getMessage());
            // 如果是在1分钟之外的消息，就在向MQ中发送消息的同时更新这条消息的发送时间和发送次数
            publisher.send(message.getQueue(), JsonUtils.toJson(msg));

            // 修改消息发送次数以及最近发送时间
            transactionMqRemoteClient.incrSendCount(message.getId(), DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
    }

}
