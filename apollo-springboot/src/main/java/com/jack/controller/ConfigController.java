package com.jack.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import com.jack.config.RedisConfig;
import com.jack.config.User;
import com.jack.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试Apollo Spring Annotation支持
 */
@CrossOrigin
@RestController
public class ConfigController {

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private RedisConfig redisConfig;

    /**
     * 直接注入Apollo Config对象
     */
    @ApolloConfig
    private Config config;

    /**
     * 使用@ApolloJsonValue将json字符串注入成为对象
     */
    @ApolloJsonValue("${users:[]}")
    private List<User> users;


    @GetMapping("/test")
    public String test() {
        return userConfig.getUsername();
    }

    @GetMapping("/redis")
    public String redis() {
        return redisConfig.getHost() +":"+ redisConfig.getPort();
    }

    @GetMapping("/name")
    private String name() {
        return config.getProperty("username", "jack");
    }

    /**
     * 在apollo的配置中心里面添加一个key为users，value为[{"id":1, "name":"luozy"}]的配置项
     * 这里就可以获取到对应的信息
     */
    @GetMapping("/users")
    public List<User> users(){
        return this.users;
    }

    /**
     * 自动注册Apollo事件监听器
     */
    @ApolloConfigChangeListener
    private void someOnChanged(ConfigChangeEvent changeEvent) {
        if (changeEvent.isChanged("username")) {
            System.out.println("username发生了改变！");
        }
    }

}
