package com.jack.rmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ消息监听类
 * 监听fanout.A队列
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {

    @RabbitHandler
    public void process(Map msg) {
        System.out.println("FanoutReceiverA消费者收到消息：" + msg.toString());
    }
}
