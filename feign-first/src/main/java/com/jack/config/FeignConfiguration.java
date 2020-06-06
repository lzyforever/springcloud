package com.jack.config;

import com.jack.auth.FeignBasicAuthRequestInterceptor;
import feign.Contract;
import feign.Logger;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign的配置
 */
@Configuration
public class FeignConfiguration {
    /**
     * 日志级别
     *  NONE      不输出日志
     *  BASIC     只输出请求方法的URL和响应的状态码以及接口执行时间
     *  HEADERS   将BASIC信息和请求头信息输出
     *  FULL      输出完整的请求信息
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 这个是修改Feign的契约配置，如果想用原生的Feign，就改这里
     * 原生的Feign是不支持SpringMVC注解的
     * 如果改成了默认的，之前定义的FeignClient就用不了了，因为上面的SpringMVC的注解
     */
    @Bean
    public Contract feignContract() {
        //return new Contract.Default();
        return new org.springframework.cloud.openfeign.support.SpringMvcContract();
    }

    /**
     * Basic认证方式
     * 第一个参数是用户名，第二个参数是密码
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("luozy", "123456");
    }

    /**
     * 基于拦截器来实现自定义认证
     * 在这里实例化一个自定义的拦截器实例就OK
     * 这样当Feign去请求接口的时候，每次请求之前都会进入此拦截器的apply方法中
     * 认证逻辑就写在这个里面
     */
    @Bean
    public FeignBasicAuthRequestInterceptor feignBasicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

    /**
     * 通过Options可以配置Feign的连接超时和读取超时时间
     * Options参数
     * 第一个参数：连接超时时间(单位：ms)，默认值是10 * 1000
     * 第二个参数：取超时时间(单位：ms)，默认值是60 * 1000
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5000,10000);
    }

    /**
     * 只需要在Feign配置类中注册就可以使用自定义解码器
     */
//    @Bean
//    public Decoder decoder() {
//        return new MyDecoder();
//    }

    /**
     * 只需要在Feign配置类中注册就可以使用自定义编码器
     */
//    @Bean
//    public Encoder encoder() {
//        return new MyEncoder();
//    }
}
