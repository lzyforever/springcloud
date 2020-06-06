package com.jack.controller;

import com.jack.config.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/name")
    public String getUserName() {
        return userClient.getName();
    }
}
