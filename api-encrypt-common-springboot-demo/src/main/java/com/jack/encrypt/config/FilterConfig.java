package com.jack.encrypt.config;

import com.jack.encrypt.core.EncryptionConfig;
import com.jack.encrypt.core.EncryptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 配置类
 */
@Configuration
public class FilterConfig {

    @SuppressWarnings({"rawtype", "unchecked"})
    @Bean
    public FilterRegistrationBean filterRegistration() {
        EncryptionConfig config = new EncryptionConfig();
        // 如果是AES就配Key
        config.setKey("031d46bb084f4c729a09b61637d0a5c8");
        config.setRequestDecryptUriList(Arrays.asList("/save", "/decryptEntityXml"));
        config.setResponseEncryptUriList(Arrays.asList("/save", "/decryptEntityXml", "encryptEntityXml", "/encryptEntity", "/encryptStr"));
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new EncryptionFilter(config));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("EncryptionFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
