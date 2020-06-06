package com.jack.feign;

import com.jack.support.RibbonFilterContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Map;

/**
 * Feign的拦截器，传递用户信息到被调用的服务
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    public FeignBasicAuthRequestInterceptor() {

    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 通过获取InheritableThreadLocal中的数据添加到请求头中，这里不用具体的名字去获取数据是为了扩展，这样后面
        // 添加任何的参数都能直接传递过去了
        Map<String, String> attributes = RibbonFilterContextHolder.getCurrentContext().getAttributes();
        for (String key: attributes.keySet()) {
            String value = attributes.get(key);
            requestTemplate.header(key, value);
        }
    }
}
