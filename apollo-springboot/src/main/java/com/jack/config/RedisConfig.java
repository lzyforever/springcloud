package com.jack.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 使用ConfigurationProperties方式注入配置
 * 注意：ConfigurationProperties有一个缺点，当配置在值发生变化时不会自动刷新，而是需要
 * 手动实现刷新逻辑，建议不要使用这种方式，比较繁琐
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis.cache")
public class RedisConfig {
    private String host;
    private String port;
}
