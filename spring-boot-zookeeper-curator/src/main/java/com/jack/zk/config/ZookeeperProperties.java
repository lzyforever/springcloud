package com.jack.zk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Zookeeper属性配置
 */
@ConfigurationProperties(prefix = "zookeeper")
@Validated
@Data
public class ZookeeperProperties {

    @NotNull(message = "zookeeper服务地址不能为空")
    private String server;

    @NotNull(message = "namespace不能为空")
    private String namespace;

    private String digest;

    private Integer sessionTimeoutMs;

    private Integer connectionTimeoutMs;

    private Integer maxRetries;

    private Integer baseSleepTimeMs;

}
