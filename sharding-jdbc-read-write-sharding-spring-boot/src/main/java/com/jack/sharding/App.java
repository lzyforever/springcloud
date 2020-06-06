package com.jack.sharding;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot版 Sharding JDBC 垂直拆分（不同的表在不同的库中）+ 读写分离
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
