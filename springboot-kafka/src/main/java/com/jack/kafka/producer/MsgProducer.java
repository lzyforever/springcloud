package com.jack.kafka.producer;

import com.jack.kafka.bean.KafkaInMsg;
import com.jack.kafka.constant.Topic;
import com.jack.kafka.dao.KafkaInMsgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;

/**
 * 消息生产者
 */
@Component
@Slf4j
public class MsgProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaInMsgMapper inMsgMapper;

    /**
     * 单条发送
     *
     * @param topic   主题名称
     * @param content 发送内容
     */
    public void sendMsg(String topic, Object content) {
        // 这里的ListenableFuture类是Spring对Java原生Future的扩展增强，是一个泛型接口，用于监听异步方法的回调
        // 而对于kafka send方法皇家马德里回值而言，这里的泛型所代表的实际类型就是SendResult<K, V>,而这里K,V的
        // 泛型实际上初用于ProducerRecord<K, V> producerRecord，即生产者发送消息的key, value类型
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, content);
        future.addCallback(
                o -> log.info("消息发送成功，{}", o.toString()),
                throwable -> log.info("消息发送失败，{}", throwable.getMessage())
        );

        KafkaInMsg inMsg = new KafkaInMsg();
        inMsg.setNum(System.currentTimeMillis());
        inMsg.setCreateTime(new Date());
        inMsg.setContent(content.toString());
        KafkaInMsg msg = inMsgMapper.selectByNum(inMsg.getNum());
        if (null == msg) {
            int num = inMsgMapper.insertSelective(inMsg);
            log.info("保存 InMsg result：{}", num == 1 ? "成功" : "失败");
        }
    }

    /**
     * 单条分组发送
     *
     * @param topic   主题名称
     * @param content 发送内容
     */
    public void sendGroupMsg(String topic, Object content) {
        // 第一个参数指Topic分组，第二个参数指定分区，第三个参数指定消息键，分区优先
        // 注意：如果需要多个分区的话，需要修改 server.proerties文件汇总的num.partitions =1 这个配置
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, 0, "key", content);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("发送消息失败，{}", throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> sendResult) {
                log.info("发送消息成功，{}", sendResult.toString());
            }
        });
    }

    /**
     * kafka事务提交
     * 如果在发送消息时需要创建事务，可以使用 KafkaTemplate 的 executeInTransaction 方法来声明事务
     */
    public void sendMessageUseTrans() {
        // 声明事务：后面报错消息不会发出去
        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            kafkaOperations.send(Topic.SIMPLE, "test executeInTransaction");
            throw new RuntimeException("exec fail");
        });

        // 不声明事务：后面报错但前面消息已经发送成功了
        kafkaTemplate.send("topic1","test executeInTransaction");
        throw new RuntimeException("fail");

    }
}
