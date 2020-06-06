package com.jack.sharding.controller;

import com.jack.sharding.po.User;
import com.jack.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public Object findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/query")
    public Object findByName(String name) {
        return userService.findByName(name);
    }

    @GetMapping("/add")
    public Object add() {
        for (long i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i);
            user.setName("jack" + i);
            user.setCity("成都");
            userService.addUser(user);
        }
        return "success";
    }
}
