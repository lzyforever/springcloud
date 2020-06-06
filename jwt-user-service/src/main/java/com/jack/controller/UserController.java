package com.jack.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户功能控制层
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/hello")
    public String hello() {
        logger.info("我是jwt-auth-invoke项目UserController中/user/hello接口啊！");
        return "hello";
    }
}
