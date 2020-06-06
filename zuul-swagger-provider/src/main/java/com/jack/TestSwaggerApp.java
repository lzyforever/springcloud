package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 这个项目是提供Swagger接口的，用来测试Swagger
 * Swagger2的注解@EnableSwagger2，必须加，否则解析不到
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class TestSwaggerApp {
    public static void main(String[] args) {
        SpringApplication.run(TestSwaggerApp.class, args);
    }
}
