package com.jack.controller;

import com.jack.entity.User;
import org.springframework.web.bind.annotation.*;

/**
 * 消费者接口定义
 */
@RestController
public class UserController {

    /**
     * 通过@RequestParam来传递参数，返回一个对象信息
     */
    @GetMapping("/user/data")
    public User getData(@RequestParam("name") String name) {
        return new User(1, "luozy", 18, "成都高新");
    }

    /**
     * 通过@PathVariable来传递参数，返回一个字符串
     */
    @GetMapping("/user/data/{name}")
    public String getName(@PathVariable("name") String name) {
        return name;
    }

    /**
     * 通过@RequestBody来接收参数，返回一个标识
     */
    @PostMapping("/user/save")
    public int addData(@RequestBody User user) {
        System.out.println(user.getName());
        return user.getId();
    }

}
