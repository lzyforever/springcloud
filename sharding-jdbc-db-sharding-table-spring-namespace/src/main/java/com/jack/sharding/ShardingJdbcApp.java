package com.jack.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Sharding-JDBC分库分表
 * 通过sharding.xml的这种是Spring命名空间配置
 *
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:sharding.xml"})
public class ShardingJdbcApp {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApp.class, args);
    }
}
