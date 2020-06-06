package com.jack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定义回退接口
 */
@RestController
public class FallbackController {
    @GetMapping("/fallback")
    public String fallback() {
        return "fallback";
    }
}
