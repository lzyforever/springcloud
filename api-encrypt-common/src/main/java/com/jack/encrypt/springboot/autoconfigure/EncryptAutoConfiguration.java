package com.jack.encrypt.springboot.autoconfigure;

import com.jack.encrypt.algorithm.EncryptAlgorithm;
import com.jack.encrypt.core.EncryptionConfig;
import com.jack.encrypt.core.EncryptionFilter;
import com.jack.encrypt.springboot.endpoint.EncryptEndpoint;
import com.jack.encrypt.springboot.init.ApiEncryptDataInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加解密自动配置
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(EncryptionConfig.class)
public class EncryptAutoConfiguration {

    @Autowired
    private EncryptionConfig encryptionConfig;

    @Autowired(required = false)
    private EncryptAlgorithm encryptAlgorithm;

    /**
     * 不要用泛型注册Filter，泛型在Spring Boot2.x版本才有
     * @return 过滤器
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        if (null != encryptAlgorithm) {
            registrationBean.setFilter(new EncryptionFilter(encryptionConfig, encryptAlgorithm));
        } else {
            registrationBean.setFilter(new EncryptionFilter(encryptionConfig));
        }
        registrationBean.addUrlPatterns(encryptionConfig.getUrlPatterns());
        registrationBean.setName("EncryptionFilter");
        registrationBean.setOrder(encryptionConfig.getOrder());
        return registrationBean;
    }

    @Bean
    public ApiEncryptDataInit apiEncryptDataInit() {
        return new ApiEncryptDataInit();
    }

    @Bean
    public EncryptEndpoint encryptEndpoint() {
        return new EncryptEndpoint();
    }

}
