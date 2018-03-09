package com.jack.cloud;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class MyInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		System.out.println("-----------这个是自定义拦截器---------");
		System.out.println("source URI: " + request.getURI());
		
		HttpRequest newRequest = new MyRequest(request);
		System.out.println("new URI: " + newRequest.getURI());
		return execution.execute(newRequest, body);
	}

}
