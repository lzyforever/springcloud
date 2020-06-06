package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务消费者，注册到Eureka注册中心上
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceConsumeApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumeApp.class, args);
    }
}
