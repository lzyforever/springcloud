package com.jack.rmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息监听配置
 */
@Configuration
public class MessageListenerConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * 消息接收处理类
     */
    @Autowired
    private MyAckReceiver myAckReceiver;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // RabbitMQ默认是自动确认，这里改成手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置一个队列
        container.setQueueNames("testDirectQueue");
        
        // 如果同时设置多个如下：前提是队列都是必须已经创建存在的
        container.setQueueNames("testDirectQueue", "topic.man", "topic.woman", "fanout.A", "fanout.B", "fanout.C");

        // 另一种设置队列的方法，如果使用这种情况，那 么要设置多个，就使用addQueues
        // container.setQueues(new Queue("testDirectQueue"));
        // container.setQueues(new Queue("topic.man"));
        // container.setQueues(new Queue("topic.woman"));
        // container.setQueues(new Queue("fanout.A"));
        // container.setQueues(new Queue("fanout.B"));
        // container.setQueues(new Queue("fanout.C"));

        // 设置消息监听处理器
        container.setMessageListener(myAckReceiver);

        return container;
    }

}
