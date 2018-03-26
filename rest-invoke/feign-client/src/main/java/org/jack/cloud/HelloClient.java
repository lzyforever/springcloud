package org.jack.cloud;

import feign.RequestLine;

/**
 * 客户端调用的服务接口
 *
 */
public interface HelloClient {

	@RequestLine("GET /hello")
	String sayHello();
}
