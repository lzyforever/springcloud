package com.jack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置类
 * 使用@ConfigurationProperties注解来指定配置的前缀
 */
@ConfigurationProperties("spring.user")
public class UserProperties {
    /** 名称 */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
