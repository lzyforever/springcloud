package com.jack.rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消息消费者
 */
@SpringBootApplication
public class RabbitComsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitComsumerApp.class, args);
    }
}
