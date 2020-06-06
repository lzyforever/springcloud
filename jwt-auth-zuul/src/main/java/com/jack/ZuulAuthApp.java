package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 调用JWT进行认证示例
 * 第三种使用：Zuul中传递Token到路由的服务中
 */
@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
@EnableScheduling
public class ZuulAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(ZuulAuthApp.class, args);
    }
}
