package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 集群模式下的Eureka注册中心
 * 在配置文件中默认启动的是master节点，这个在其他的机器上运行slave节点时
 * 需要添加启动参数--spring.profiles.active=slaveA或是slaveB即可
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
