package com.jack.filter.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * 自定义过滤器工厂
 * 如果是key value形式的配置，可以直接继承AbstractNameValueGatewayFilterFactory，使用NameValueConfig来做配置类
 */
@Component
public class Custom2GatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return ((exchange, chain) -> {
            System.out.println("进入了自定义过滤器2工厂！" + config.getName());
            ServerHttpRequest request = exchange.getRequest().mutate().build();
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

}
