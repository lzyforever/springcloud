package com.jack.rmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ消息监听类
 */
@Component
@RabbitListener(queues = "testDirectQueue") // 监听的队列名称 testDirectQueue
public class DirectReceiver {
    @RabbitHandler
    public void process(Map msg) {
        System.out.println("DirectReceiver 消费者收到消息：" + msg.toString());
    }
}
