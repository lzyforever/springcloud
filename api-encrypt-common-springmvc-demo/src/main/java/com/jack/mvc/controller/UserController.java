package com.jack.mvc.controller;

import com.jack.mvc.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/encryptEntity")
    public User encryptEntity() {
        User user = new User();
        user.setId(1);
        user.setName("这个是加密的实体对象");
        return user;
    }

    @PostMapping("/save")
    public User save(@RequestBody User user) {
        System.out.println("id：" + user.getId() + " name：" + user.getName());
        return user;
    }
}
