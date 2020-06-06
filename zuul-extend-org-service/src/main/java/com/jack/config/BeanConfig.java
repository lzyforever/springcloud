package com.jack.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.jack.apilimit.aspect.ApiLimitAspect;
import com.jack.feign.FeignBasicAuthRequestInterceptor;
import com.jack.filter.HttpHeaderParamFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 配置类
 * 进行feign配置，Feign的拦截器使用需要在@FeignClient注解中指定Feign的自定义配置，自定义配置类中配置Feign的拦截器即可
 */
@Configuration
public class BeanConfig {


    @Bean
    public FilterRegistrationBean<HttpHeaderParamFilter> filterRegistrationBean() {
        FilterRegistrationBean<HttpHeaderParamFilter> registrationBean = new FilterRegistrationBean<>();
        HttpHeaderParamFilter httpHeaderParamFilter = new HttpHeaderParamFilter();
        registrationBean.setFilter(httpHeaderParamFilter);
        List<String> urlpatterns = new ArrayList<>();
        urlpatterns.add("/*");
        registrationBean.setUrlPatterns(urlpatterns);
        return registrationBean;
    }

    @Bean
    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

    @Bean
    public ApiLimitAspect apiLimitAspect() {
        return new ApiLimitAspect();
    }


    /**
     * Apollo配置更新回调
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {

        for (String key : changeEvent.changedKeys()) {
            if (key.startsWith("open.api.")) {
                if (ApiLimitAspect.semaphoreMap.keySet().contains(key)) {
                    int num = Integer.parseInt(changeEvent.getChange(key).getNewValue());
                    ApiLimitAspect.semaphoreMap.put(key, new Semaphore(num));
                }
            }
        }
    }
}
