package com.jack.encrypt;

import com.jack.encrypt.aonnotation.EnableEncrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API加解密测试示例
 */
@SpringBootApplication
@EnableEncrypt
public class EncryptApp {
    public static void main(String[] args) {
        SpringApplication.run(EncryptApp.class, args);
    }
}
