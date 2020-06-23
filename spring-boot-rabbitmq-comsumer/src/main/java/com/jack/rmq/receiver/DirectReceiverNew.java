package com.jack.rmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ消息监听类
 * 可以创建多个消息监听绑定到同一个直接交互的同一个队列上
 */
@Component
@RabbitListener(queues = "testDirectQueue") // 监听的队列名称 testDirectQueue
public class DirectReceiverNew {
    @RabbitHandler
    public void process(Map msg) {
        System.out.println("DirectReceiverNew 消费者收到消息：" + msg.toString());
    }
}
