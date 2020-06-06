package com.jack.transaction.mq.task.publish;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息发布者
 */
@Component
public class Publisher {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送消息
     * @param queue 队列名称
     * @param msg 消息内容
     */
    public void send(String queue, String msg) {
        this.jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(queue), msg);
    }

}
