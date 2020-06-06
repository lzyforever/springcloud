package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign测试启动类
 * 需要在启动类上添加@EnableFeignClients注解
 * 如果接口定义跟启动类不在同一个包名下，还需要指定扫描的包名
 * 如下：@EnableFeignClients(basePackages="com.jack.remote")
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeignApp {
    public static void main(String[] args) {
        SpringApplication.run(FeignApp.class, args);
    }
}
