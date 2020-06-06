package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul整合Swagger2
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulSwaggerApp {
    public static void main(String[] args) {
        SpringApplication.run(ZuulSwaggerApp.class, args);
    }
}
