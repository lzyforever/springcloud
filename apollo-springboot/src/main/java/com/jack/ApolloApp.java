package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Springboot中使用Apollo
 */
@SpringBootApplication
public class ApolloApp {

    public static void main(String[] args) {
        // 在代码里面测试使用，指定配置环境
        System.setProperty("env", "DEV");
        SpringApplication.run(ApolloApp.class, args);
    }
}
