package com.jack.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 使用Java Config获取配置信息
 */
@Data
@Configuration
public class UserConfig {
    @Value("${username:jackluo}")
    private String username;
}
