package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 测试API网关 Zuul
 * 通过使用@EnableZuulProxy来开启Zuul的路由代理功能
 * 注解@EnableZuulProxy自带了@EnableDiscoveryClient注解，所以不需要单独添加此Eureka注解
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulApp {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApp.class, args);
    }
}
