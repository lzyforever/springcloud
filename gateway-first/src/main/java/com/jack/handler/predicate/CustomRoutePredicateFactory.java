package com.jack.handler.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * 自定义一个断言工厂
 * 继承org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory，实现apply方法逻辑
 */
@Component
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

    public CustomRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                System.out.println("进入了自定义的断言工厂！！！！配置名称：" + config.getName());
                if (config.getName().equals("luozy")) {
                    return true;
                }
                return false;
            }
        };
    }

    public static class Config{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
