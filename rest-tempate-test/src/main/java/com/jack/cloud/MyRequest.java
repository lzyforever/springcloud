package com.jack.cloud;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

/**
 * �Զ���HttpRequest����װԭ���ľ�������������
 */
public class MyRequest implements HttpRequest {
	
	// ���ڱ���Դ����
	private HttpRequest sourceRequest;
	
	public MyRequest(HttpRequest sourceRequest) {
		this.sourceRequest = sourceRequest;
	}

	@Override
	public HttpHeaders getHeaders() {
		return sourceRequest.getHeaders();
	}

	@Override
	public HttpMethod getMethod() {
		return sourceRequest.getMethod();
	}

	@Override
	public URI getURI() {
		
		try {
			return new URI("http://localhost:8080/hello");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return sourceRequest.getURI();
	}

}
