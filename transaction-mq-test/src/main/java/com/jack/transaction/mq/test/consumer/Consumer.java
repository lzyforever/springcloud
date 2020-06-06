package com.jack.transaction.mq.test.consumer;

import com.jack.transaction.mq.client.TransactionMqRemoteClient;
import com.jack.transaction.mq.test.pojo.Message;
import com.jack.transaction.mq.test.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 事务消息消费类
 * 当MQ中有消息时，通过Consumer来接收消息。我们存储的消息内容是JSON字符串的方式，接收
 * 后我们将消息反序列化成实体对象，以方便操作。然后进行需要修改的操作，操作没问题后再
 * 调用消息服务的接口，进行消息已被正常消费的确认工作，同时手动确认MQ的消费。当发生异
 * 常时就不会手动被消费，这里MQ会重发消息，直到超过了重发的次数才不会进行重发。
 */
@Component
public class Consumer {
    @Autowired
    private TransactionMqRemoteClient transactionMqRemoteClient;

    @JmsListener(destination = "jack_queue")
    public void receiveQueue(TextMessage text) {
        try {
            System.out.println("可靠消息服务消费消息：" + text.getText());
            Message msg = JsonUtils.toBean(Message.class, text.getText());
            // 执行修改逻辑
            // ...
            // 修改成功后调用消息确认消费接口，确认该消息已被消费
            boolean result = transactionMqRemoteClient.confirmCustomerMessage("transaction-mq-test", msg.getMessageId());
            // 手动确认 ACK
            if (result) {
                text.acknowledge();
            }
        } catch (JMSException e) {
            // 异常时会触发重试机制，重试次数完成之后还是错误，消息会进入DLQ队列中
            throw new RuntimeException(e);
        }
    }
}
