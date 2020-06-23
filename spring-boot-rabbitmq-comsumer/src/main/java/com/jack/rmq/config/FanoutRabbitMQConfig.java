package com.jack.rmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 使用 Fanout Exchange扇型交换机
 *
 * 创建三个队列，fanout.A、fanout.B、fanout.C
 * 将三个队列都绑定在交换机fanoutExchange上
 * 因为是扇型交换机，路由键无需配置，配置也不会起作用
 */
@Configuration
public class FanoutRabbitMQConfig {

    public static final String QUEUE_A = "fanout.A";
    public static final String QUEUE_B = "fanout.B";
    public static final String QUEUE_C = "fanout.C";

    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A);
    }


    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B);
    }


    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingExchangeC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

}
