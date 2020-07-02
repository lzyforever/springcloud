package com.jack.kafka.config;

import com.jack.kafka.constant.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * Kafka 配置
 * 多消费者组配置
 */
@Configuration
public class KafkaConfig {

//    /**
//     * JSON消息转换器
//     * 如果需要将内容转成JSON，那么就加这个，对应解析的数据也就是JSON，不能当字符串去解
//     */
//    @Bean
//    public RecordMessageConverter jsonConverter() {
//        return new StringJsonMessageConverter();
//    }

    /**
     * 通过注入一个 NewTopic 类型的 Bean 来创建 topic，如果 topic 已存在，则会忽略
     * 如果要修改分区数，只需修改配置值重启项目即可
     * 修改分区数并不会导致数据的丢失，但是分区数只能增大不能减小
     */
    @Bean
    public NewTopic groupTopic() {
        // 指定主题名称，分区数量，分区副本数
        return new NewTopic(Topic.GROUP, 1, (short) 2);
    }


    /**
     * 新建一个 ConsumerAwareListenerErrorHandler 类型的异常处理方法，用@Bean注入，BeanName默认就是方法名
     * 然后我们将这个异常处理器的BeanName放到@KafkaListener注解的errorHandler属性里面，当监听抛出异常的时候，则会自动调用异常处理器
     */
    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareErrorHandler() {
        return (message, exception, consumer) -> {
            System.out.println("消费异常：" + message.getPayload());
            return null;
        };

    }
}