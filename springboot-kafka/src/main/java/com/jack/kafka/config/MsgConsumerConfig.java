package com.jack.kafka.config;

import com.jack.kafka.constant.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

/**
 * 消息过滤器
 * 消息过滤器可以在消息抵达consumer之前被拦截，在实际应用中，我们可以根据自己的业务逻辑，筛选出
 * 需要的信息再交由KafkaListener处理，不需要的消息则过滤掉。
 *
 * 配置消息过滤只需要为 监听器工厂 配置一个RecordFilterStrategy（消息过滤策略），返回true的时候消息
 * 将会被抛弃，返回false时，消息能正常抵达监听容器。
 */
@Component
public class MsgConsumerConfig {

    @Autowired
    private ConsumerFactory consumerFactory;

    /**
     * 消息过滤器
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory filterContainerFactory() {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略
        factory.setRecordFilterStrategy(consumerRecord -> {
            // 消息里面有jack的才发送
            if (consumerRecord.value().toString().indexOf("jack") > 1) {
                return false;
            }
            //返回true消息则被过滤
            return true;
        });
        return factory;
    }

    /**
     * 消息过滤监听
     */
    @KafkaListener(groupId = "simpleGroup", topics = {Topic.SIMPLE}, containerFactory = "filterContainerFactory")
    public void onMessage(ConsumerRecord<?, ?> record) {
        System.out.println(record.value());
    }
}
