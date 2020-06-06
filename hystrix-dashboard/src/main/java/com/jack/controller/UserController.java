package com.jack.controller;

import com.jack.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Feign Client调用
 */
@RestController
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/sayHi")
    public String sayHi() {
        return userFeignClient.sayHi();
    }
}
