package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 测试Ribbon与RestTemplate整合
 */
@EnableEurekaClient
@SpringBootApplication
public class RibbonRestTest {
    public static void main(String[] args) {
        SpringApplication.run(RibbonRestTest.class, args);
    }
}
