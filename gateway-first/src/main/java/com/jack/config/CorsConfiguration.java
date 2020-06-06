package com.jack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * Gateway中跨域代码配置
 * Gateway中配置跨域有两种方式，分别是代码配置和配置文件配置。
 */
@Configuration
public class CorsConfiguration {
    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders reqHeaders = request.getHeaders();
                ServerHttpResponse response = exchange.getResponse();
                HttpMethod reqMethod = reqHeaders.getAccessControlRequestMethod();
                HttpHeaders resHeader = response.getHeaders();
                resHeader.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, reqHeaders.getOrigin());
                resHeader.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, reqHeaders.getAccessControlRequestHeaders());
                if (reqMethod != null) {
                    resHeader.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, reqMethod.name());
                }
                resHeader.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                resHeader.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                resHeader.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "18000L");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(exchange);
        };
    }
}
