package com.jack.encrypt;

import com.jack.encrypt.springboot.annotation.EnableEncrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API加解密示例 SpringBoot版
 */
@SpringBootApplication
@EnableEncrypt
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
