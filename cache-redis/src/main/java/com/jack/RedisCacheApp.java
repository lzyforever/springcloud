package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Redis缓存Demo
 */
@SpringBootApplication
public class RedisCacheApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisCacheApp.class, args);
    }
}
