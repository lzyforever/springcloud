package com.jack.transaction.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息队列实现最终一致性分布式事务解决方案
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TransactionMqServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMqServiceApp.class, args);
    }
}
