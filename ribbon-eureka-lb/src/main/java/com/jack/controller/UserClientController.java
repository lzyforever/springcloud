package com.jack.controller;

import com.jack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试接口控制器
 */
@RestController
public class UserClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call/data")
    public User getData(@RequestParam("name") String name) {
        return restTemplate.getForObject("http://ribbon-eureka-lb/user/data?name=" + name, User.class);
    }

}
