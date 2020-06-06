package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 测试Ribbon配置的应用
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RibbonConfigApp {
    public static void main(String[] args) {
        SpringApplication.run(RibbonConfigApp.class, args);
    }
}
