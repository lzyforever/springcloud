package com.jack.rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消息提供者
 */
@SpringBootApplication
public class RabbitProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(RabbitProviderApp.class, args);
    }
}
