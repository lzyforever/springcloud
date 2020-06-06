package com.jack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Swagger
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }
}
