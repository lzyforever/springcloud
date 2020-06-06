package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 测试Zuul文件上传
 */
@EnableDiscoveryClient
@SpringBootApplication
public class FileUploadApp {
    public static void main(String[] args) {
        SpringApplication.run(FileUploadApp.class, args);
    }
}
