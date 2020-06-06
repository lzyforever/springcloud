package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *  服务提供者，它将注册到Eureka上面去
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApp.class, args);
    }
}
