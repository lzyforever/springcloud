package com.jack.sharding;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot版 Sharding JDBC 既分库又分表 + 读写分离 示例
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
