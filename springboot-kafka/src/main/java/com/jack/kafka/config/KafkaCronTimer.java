package com.jack.kafka.config;

import com.jack.kafka.constant.Topic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Kafka 定时启动、停止监听器
 * <p>
 * 定时任务类，用注解@EnableScheduing声明
 * <p>
 * 默认情况下，当消费者项目启动的时候，监听器就开始工作，监听消费发送到指定topic的消息，那如果我们不
 * 想让监听器立即工作，想让它在我们指定的时间点开始工作，或者在我们指定的时间点停止工作，该怎么处理喃？
 * 可以使用KafkaListenerEndpointRegistry来进行控制
 * <p>
 * 1、禁止监听器自启动
 * 2、创建两个定时任务，一个用来在指定时间点启动定时器，另一个在指定时间点停止定时器
 */
@EnableScheduling
@Component
public class KafkaCronTimer {

    /**
     * 使用@KafkaListener注解所标注的方法并不会在IOC容器中被注册为Bean
     * 而是会被注册在KafkaListenerEndpointRegistry中
     * 而KafkaListenerEndpointRegistry在SpringIOC中已经被注册为Bean
     */
    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private ConsumerFactory consumerFactory;

    /**
     * 监听器容器工厂（设置禁止KafkaListener自启动）
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory delayContainerFactory() {
        ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
        container.setConsumerFactory(consumerFactory);
        // 禁止KafkaListener自启动
        container.setAutoStartup(false);
        return container;
    }

    @KafkaListener(id = "kafkaCronTimerConsumer", groupId = "timerGroup", topics = {"JACK"}, containerFactory = "delayContainerFactory")
    public void onMessage(ConsumerRecord<?, ?> record) {
        System.out.println("消息成功：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

    /**
     * 定时启动监听器
     */
    @Scheduled(cron = "0 52 10 * * ?")
    public void startListener() {
        System.out.println("启动监听器...");
        if (!registry.getListenerContainer("kafkaCronTimerConsumer").isRunning()) {
            registry.getListenerContainer("kafkaCronTimerConsumer").start();
        }
        // registry.getListenerContainer("kafkaCronTimerConsumer").resume();
    }

    /**
     * 定时停止监听器
     */
    @Scheduled(cron = "0 55 10 * * ?")
    public void shutdownListener() {
        System.out.println("关闭监听器");
        registry.getListenerContainer("kafkaCronTimerConsumer").pause();
    }

}
