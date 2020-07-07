package com.jack.zk.config;

import com.jack.zk.client.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Zookeeper配置类
 */
@Configuration
@EnableConfigurationProperties({ZookeeperProperties.class})
@ConditionalOnProperty(value = {"zookeeper.enable"}, matchIfMissing = false) // 配置文件属性是否为true
public class ZookeeperConfig {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Bean(initMethod = "init", destroyMethod = "stop")
    public ZkClient zkClient(){
        ZkClient zkClient = new ZkClient(zookeeperProperties);
        return zkClient;
    }

}
