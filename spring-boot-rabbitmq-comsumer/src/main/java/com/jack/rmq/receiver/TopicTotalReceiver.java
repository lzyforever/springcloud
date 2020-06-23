package com.jack.rmq.receiver;

import com.jack.rmq.config.TopicRabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ消息监听类
 * 它监听topic.# 消息队列
 */
@Component
@RabbitListener(queues = TopicRabbitMQConfig.WOMAN)
public class TopicTotalReceiver {
    @RabbitHandler
    public void process(Map msg) {
        System.out.println("TopicTotalReceiver 消费者收到消息：" + msg.toString());
    }
}
