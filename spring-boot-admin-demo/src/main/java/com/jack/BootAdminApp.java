package com.jack;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot admin server测试Demo
 * 通过@EnableAdminServer来开启SpringBootAdmin
 */
@SpringBootApplication
@EnableAdminServer
public class BootAdminApp {
    public static void main(String[] args) {
        SpringApplication.run(BootAdminApp.class, args);
    }
}
