package com.jack;

import org.cxytiandi.conf.client.init.ConfInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * smconf配置中心客户端测试
 *
 * 如果需要在Spring还没有初始化之前加载配置信息，可以在启动类中通过设置
 * 全局变量System.setProperty()来告诉smconf client从哪个包下面加载配置的实体类信息
 *
 * 在非Spring的项目中可以手动调用初始化的方法进行加载初始化配置
 * SmconfInit.init("com.jack.config");
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SmconfDemoApp {
    public static void main(String[] args) {
        // 初始化配置信息
        // System.setProperty("smconf.conf.package", "com.jack.config");

        SpringApplication.run(SmconfDemoApp.class, args);
    }

    /**
     * 启动Smconf配置客户端
     *
     * 当项目启动的时候，smconf首先会将配配置信息初始化注册中心，如果配置已存在，则会拉取配置
     * 中心的配置到本地使用。通过配置env=true，属性文件中可以直接通过$符号获取配置的值，然后连接Eureka
     */
    @Bean
    public ConfInit confInit() {
        return new ConfInit();
    }
}
