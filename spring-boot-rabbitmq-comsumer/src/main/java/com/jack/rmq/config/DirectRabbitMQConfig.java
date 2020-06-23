package com.jack.rmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 * 使用Direct Exchange直连交换机
 *
 * 消费者单纯的使用，其实可以不用添加这个配置，直接建监听类就好，使用注解来让监听器监听对应的队列即可。
 * 配置上了的话，其实消费者也是生成者的身份，也能推送该消息。
 *
 */
@Configuration
public class DirectRabbitMQConfig {

    /**
     * 队列 testDirectQueue
     * 注意：队列名称大小写
     */
    @Bean
    public Queue testDirectQueue() {

        // 参数描述：
        // durable：是否持久化，默认是false，持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive：默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参数优先级高于durable
        // autoDelete：是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // 一般设置一下队列的持久化就好，其余两个就是默认false
        return new Queue("testDirectQueue", true);
    }

    /**
     * Direct交换机 testDirectExchange
     * 注意：交换机名称大小写
     */
    @Bean
    public DirectExchange testDirectExchange() {
        return new DirectExchange("testDirectExchange", true, false);
    }


    /**
     * 绑定队列和交换机，并设置用于匹配键 testDirectRouting
     * 注意：绑定队列和交换机名称大小写
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("testDirectRouting");
    }
    
}
