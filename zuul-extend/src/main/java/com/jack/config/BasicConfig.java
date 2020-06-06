package com.jack.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.google.common.util.concurrent.RateLimiter;
import com.jack.filter.ClusterLimitFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 基础配置类
 */
@Data
@Configuration
public class BasicConfig {

    /** API接口白名单，多个用逗号分隔 */
    @Value("${apiWhiteStr:/zuul-extend-user-service/user/login}")
    private String apiWhiteStr;

    /** 限流速率，不要写死，需要实时修改并生效，通过apollo来配置 */
    @Value("${limitRate:10}")
    private double limitRate;

    /** 集群限流速率 */
    @Value("${api.clusterLimitRate:100}")
    private double clusterLimitRate;

    /** Apollo Config对象 */
    @ApolloConfig
    private Config config;

    /** 降级的服务ID，多个用逗号分隔 */
    @Value("${downGradeServiceStr:default}")
    private String downGradeServiceStr;

    /** 灰度发布的服务列表，多个用逗号分隔 */
    @Value("${grayPushServers:default}")
    private String grayPushServers;

    /** 灰度发布的用户列表，多个用逗号分隔 */
    @Value("${grayPushUsers:default}")
    private String grayPushUsers;

    /**
     * Apollo配置更新回调
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        if (changeEvent.isChanged("limitRate")) {
            System.err.println(config.getDoubleProperty("limitRate", 10.0));
            // LimitFilter.rateLimiter = RateLimiter.create(config.getDoubleProperty("limitRate", 10.0));
            ClusterLimitFilter.rateLimiter = RateLimiter.create(config.getDoubleProperty("limitRate", 10.0));
        }
    }

}
