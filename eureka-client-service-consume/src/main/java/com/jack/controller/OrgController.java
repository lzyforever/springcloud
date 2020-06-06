package com.jack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 调用服务提供者提供的接口
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    /** 注入RestTemplate用于调用服务提供者提供的接口 */
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/callHello")
    public String callHello() {
        return restTemplate.getForObject("http://service-provider/user/hello", String.class);
    }
}
