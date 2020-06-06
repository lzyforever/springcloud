package com.jack.config;

import lombok.Data;

/**
 * 用户实体类
 * 通过@ApolloJsonValue将字符串转成User对象
 */
@Data
public class User {
    private int id;
    private String name;
}
