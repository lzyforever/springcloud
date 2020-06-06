package com.jack.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 通过自定义请求拦截器，实现自己的认证
 * 在请求之前做认证操作，然后往请求头中设置认证之后的信息
 * 通过实现RequestInterceptor接口的apply方法，来自定认证逻辑
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("进入了自定义拦截器认证....");
        String token = System.getProperty("jwt-auth-invoke.auth.token");
        System.out.println("Interceptor token: " + token);
        requestTemplate.header("Authorization", token);
    }
}