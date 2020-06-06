package com.jack.api.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 用户接口定义
 */
@FeignClient("feign-user-provider") // 指定提供服务的服务名称
public interface UserRemoteClient {
    /**
     * 定义一个获取用户名的接口
     */
    @GetMapping("/user/name")
    String getName();

    /**
     * GET请求因定参数接口
     */
    @GetMapping("/user/info")
    String getUserInfo(@RequestParam("name") String name);

    /**
     * GET请求不固定多参数接口
     */
    @GetMapping("/user/detail")
    String getUserDetail(@RequestParam Map<String, Object> param);

    /**
     * POST请求，实体类参数接口
     */
    @PostMapping("/user/add")
    String addUser(@RequestBody User user);
}