package com.jack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/sayHi/{name}")
    public String sayHi(@PathVariable String name) {
        return "hello: " + name;
    }
}
