package com.jack.rmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 使用Topic Exchange 主题交换机
 */
@Configuration
public class TopicRabbitMQConfig {

    // 绑定键
    public static final String MAN = "topic.man";
    public static final String WOMAN = "topic.woman";

    /**
     * 队列一，放男人
     */
    @Bean
    public Queue firstQueue() {
        return new Queue(MAN);
    }

    /**
     * 队列二，放女人
     */
    @Bean
    public Queue secondQueue() {
        return new Queue(WOMAN);
    }

    /**
     * Topic Exchange主题交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 绑定队列和交换机，并设置用于匹配键
     * 将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
     * 这样只要是消息携带的路由键是topic.man,才会分发到该队列
     */
    @Bean
    public Binding bindingExchangeMsgForMan() {
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(MAN);
    }


    /**
     * 绑定队列和交换机，并设置用于匹配键
     * 将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头，都会分发到该队列
     */
    @Bean
    public Binding bindingExchangeMsg() {
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
    }

}
