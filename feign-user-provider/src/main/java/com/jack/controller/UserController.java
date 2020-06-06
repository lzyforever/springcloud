package com.jack.controller;

import com.jack.api.feign.user.User;
import com.jack.api.feign.user.UserRemoteClient;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户服务提供者
 * 实现在feign-common-api当中定义的接口
 */
@RestController
public class UserController implements UserRemoteClient {

    @Override
    public String getName() {
        return "jack luo";
    }

    @Override
    public String getUserInfo(String name) {
        return name;
    }

    @Override
    public String getUserDetail(Map<String, Object> param) {
        System.out.println(param.toString());
        return param.get("name").toString();
    }

    @Override
    public String addUser(User user) {
        return user.getName();
    }
}
