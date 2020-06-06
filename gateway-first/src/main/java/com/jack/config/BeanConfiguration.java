package com.jack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 配置类，进行自定义的全局的过滤器配置
 * 注解@Order的数值越低，优先级越高
 * 当过滤器的业务较多时，还是建议单独写一个类，实现GlobalFilter和Ordered接口来实现
 */
@Configuration
public class BeanConfiguration {

    private Logger logger = LoggerFactory.getLogger(BeanConfiguration.class);

    @Bean
    @Order(1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            logger.info("进入了自定义全局过滤器 a filter......");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("a filter 过滤器开始操作了。。。。");
            }));
        };
    }

    @Bean
    @Order(-1)
    public GlobalFilter b() {
        return (exchange, chain) -> {
            logger.info("进入了自定义全局过滤器 b filter......");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("b filter 过滤器开始操作了。。。。");
            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter c() {
        return (exchange, chain) -> {
            logger.info("进入了自定义全局过滤器 c filter......");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("c filter 过滤器开始操作了。。。。");
            }));
        };
    }

    /**
     * IP限流
     * 返回IP限制的Key
     * 通过exchange对象可以获取请求信息，这里用了HostName，
     * 在生产环境中可以根据Nginx转发过来的请求头获取真实的IP
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 用户限流
     * 获取用户Id作为限制的Key
     */
//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }

    /**
     * 接口限流
     * 获取请求地址URI作为限流Key
     */
//    @Bean
//    public KeyResolver apiKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getPath().value());
//    }

    /**
     * 获取真实IP
     */
    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> ips = headers.get("X-Forwarded-For");
        String ip = "192.168.1.1";
        if (ips != null && ips.size() > 0) {
            ip = ips.get(0);
        }
        return ip;
    }
}
