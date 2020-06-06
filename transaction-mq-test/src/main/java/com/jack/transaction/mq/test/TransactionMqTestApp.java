package com.jack.transaction.mq.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 测试实现的可靠消息服务
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.jack.transaction.mq.client")
@SpringBootApplication
public class TransactionMqTestApp {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMqTestApp.class, args);
    }
}
