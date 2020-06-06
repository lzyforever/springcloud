package com.jack.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RestTemplate的拦截器传递Token
 */
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        System.err.println("进入了RestTemplate拦截器。。。");
        HttpHeaders headers = request.getHeaders();
        String token = System.getProperty("jwt-auth-invoke.auth.token");
        System.out.println("Interceptor token: " + token);
        headers.add("Authorization", token);
        return execution.execute(request, body);
    }
}
