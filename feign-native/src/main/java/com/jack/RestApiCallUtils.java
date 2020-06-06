package com.jack;

import feign.Feign;
import feign.Logger;
import feign.Request;

/**
 * REST接口调用帮助类
 * 构建一个Feign对象
 */
public class RestApiCallUtils {
    /**
     * 构建Feign对象，传入一个定义好的接口类和URL地址，就可以获取该接口的代理对象，通过代理对象
     * 调用接口中的方法即可实现远程调用
     */
    public static <T> T getRestClient(Class<T> apiType, String url) {

//        return Feign.builder()
//                .encoder(new MyEncoder()) // 设置编码器
//                .decoder(new MyDecoder()) // 设置解码器
//                .logger(new Logger.JavaLogger().appendToFile(System.getProperty("logpath") + "/http.log")) // 设置日志配置
//                .logLevel(Logger.Level.FULL)  // 设置日志等级
//                .options(new Request.Options(1000, 1000)) //设置超时时间
//                .requestInterceptor(new MyRequestInterceptor()) // 设置拦截器
//                .retryer(new MyRetryer()); // 设置重试策略

        return Feign.builder().target(apiType, url);
    }
}
