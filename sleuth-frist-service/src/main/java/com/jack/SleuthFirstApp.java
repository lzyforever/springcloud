package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Sleuth测试服务A
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SleuthFirstApp {
    public static void main(String[] args) {
        SpringApplication.run(SleuthFirstApp.class, args);
    }
}
