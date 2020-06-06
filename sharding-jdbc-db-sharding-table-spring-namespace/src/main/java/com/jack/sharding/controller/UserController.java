package com.jack.sharding.controller;

import com.jack.sharding.entity.User;
import com.jack.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Object list() {
        return userService.list();
    }

    @GetMapping("/add")
    public Object add() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setCity("深圳");
            user.setName("李四");
            userService.add(user);
        }
        return "success";
    }

}
