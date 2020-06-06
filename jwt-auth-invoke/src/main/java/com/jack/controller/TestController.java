package com.jack.controller;

import com.jack.feign.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试认证
 */
@RestController
public class TestController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test/call")
    public String callHello() {
        System.out.println("我是jwt-auth-invoke的TestController中的 callHello 方法...");
        return userRemoteClient.hello();
    }

    @GetMapping("/test/say")
    public String sayHi() {
        System.out.println("我是jwt-auth-invoke的TestController中的 sayHi 方法...");
        return restTemplate.getForObject("http://jwt-user-service/user/hello", String.class);
    }
}
