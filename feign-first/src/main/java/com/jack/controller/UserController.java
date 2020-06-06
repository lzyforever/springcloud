package com.jack.controller;

import com.jack.remote.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Feign调用的Controller
 */
@RestController
public class UserController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    @GetMapping("/callHello")
    public String callHello() {
        String result = userRemoteClient.hello();
        System.out.println("调用的结果是： " + result);
        return result;
    }
}
