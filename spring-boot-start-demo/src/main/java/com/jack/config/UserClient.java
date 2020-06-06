package com.jack.config;

/**
 * 定义一个用户操作模板类
 * @author jack
 */
public class UserClient {

    private UserProperties userProperties;

    public UserClient() {
    }

    public UserClient(UserProperties p) {
        this.userProperties = p;
    }

    public String getName() {
        return userProperties.getName();
    }
}
