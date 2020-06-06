package com.jack;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Spring Boot Admin 与 Eureka集成
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class BootAdminEurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(BootAdminEurekaApp.class, args);
    }
}
