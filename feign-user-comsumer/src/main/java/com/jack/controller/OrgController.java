package com.jack.controller;

import com.jack.api.feign.user.User;
import com.jack.api.feign.user.UserRemoteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试用户的Feign接口调用
 */
@RestController
public class OrgController {

    @Autowired
    private UserRemoteClient userRemoteClient;

    /**
     * 测试调用feign-user-provider当中提供的接口
     */
    @GetMapping("/call")
    public String callGetName() {
        StringBuilder msg = new StringBuilder();

        String name = userRemoteClient.getName();
        msg.append("调用getName的结果是：").append(name);

        String userInfo = userRemoteClient.getUserInfo("luozy");
        msg.append("<br/>").append("调用getUserInfo的结果是：").append(userInfo);

        Map<String, Object> params = new HashMap<>();
        params.put("id", "123");
        params.put("name", "jack");
        params.put("age", "18");
        String result = userRemoteClient.getUserDetail(params);
        msg.append("<br/>").append("调用getUserDetail的结果是：").append(result);

        User user = new User();
        user.setId(111);
        user.setName("骆治宇");
        user.setAge(18);
        result = userRemoteClient.addUser(user);
        msg.append("<br/>").append("调用addUser的结果是：").append(result);

        return msg.toString();
    }
}
