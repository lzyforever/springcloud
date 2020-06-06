package com.jack.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 * 某些场景下，UserAutoConfigure中会配置多个对象，对于这些对象，如果不想全部配置，或是想让用户指定需要开启配
 * 置的时候去构建对象，这个时候我们可以通过@ConditionOnProperty这个注解来指定是否开启配置的功能。
 * 如果需要自动配置，那么需要在application.yml中指定：spring.user.enabled=true 才会进行自动配置UserClient
 *
 * 在自定义Starter包的过程中，还有一点比较重要，就是对配置的内容项进行提示，需要注意的是，Eclipse中是不支持的，
 * STS中可以提示，IDEA也可以提示，需要在resource/META-INF目录下定义一个spring-configuration-metadata.json文件
 * name：配置名称，type：配置的数据类型，defaultValue：默认值
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfigure {

    @Bean
    @ConditionalOnProperty(prefix = "spring.user", value = "enabled", havingValue = "true")
    public UserClient userClient(UserProperties userProperties) {
        return new UserClient(userProperties);
    }
}
