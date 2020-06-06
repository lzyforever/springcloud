package com.jack.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Elastic Job XML配置示例
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:applicationContext.xml"})
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
