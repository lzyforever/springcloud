package com.jack.filter.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局过滤器
 */
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    private Logger logger = LoggerFactory.getLogger(MyGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("进入了MyGlobalFilter全局过滤器。。。");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("进入了MyGlobalFilter全局过滤器过滤器开始工作啊。。。");
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
