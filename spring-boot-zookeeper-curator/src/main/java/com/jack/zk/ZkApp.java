package com.jack.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于Apache的curator来操作zookeeper
 */
@SpringBootApplication
public class ZkApp {
    public static void main(String[] args) {
        SpringApplication.run(ZkApp.class, args);
    }
}
