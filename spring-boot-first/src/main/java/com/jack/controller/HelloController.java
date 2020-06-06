package com.jack.controller;

import com.jack.config.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private Environment env;

    @Value("${server.port}")
    private String port;

    @Autowired
    private MyConfig config;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2() {
        String port = env.getProperty("server.port");
        return port;
    }

    @GetMapping("/hello3")
    public String hello3() {
        return port;
    }

    @GetMapping("/hello4")
    public String hello4() {
        return "name: " + config.getName() + ", nickName: " + config.getNickName();
    }

}
