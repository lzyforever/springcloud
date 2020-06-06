package com.jack.controller;

import com.jack.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供接口控制器类
 */
@RestController
public class UserController {
    @GetMapping("/user/data")
    public User getData(@RequestParam("name") String name){
        return new User(1, "luozy", 18, "成都高新");
    }
}
