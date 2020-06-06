package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul API网关扩展
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulApp {
    public static void main(String[] args) {
        System.setProperty("env", "DEV");
        SpringApplication.run(ZuulApp.class, args);
    }
}
