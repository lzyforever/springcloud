package com.jack.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * 用户实体类
 */
@Data
@RedisHash("persons")
public class Person {
    @Id
    String id;
    String name;
    String address;
}
