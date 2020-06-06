package com.jack.controller;

import com.jack.apilimit.annotation.ApiRateLimit;
import com.jack.feign.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrgController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRemoteClient userRemoteClient;

    @ApiRateLimit(confKey = "open.api.callHello")
    @GetMapping("/org/callHello")
    public String callHello() {
        System.out.println("用户ID：" + request.getHeader("uid"));
        return userRemoteClient.hello();
    }
}
