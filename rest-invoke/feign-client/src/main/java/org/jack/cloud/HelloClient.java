package org.jack.cloud;

import feign.RequestLine;

/**
 * �ͻ��˵��õķ���ӿ�
 *
 */
public interface HelloClient {

	@RequestLine("GET /hello")
	String sayHello();
}
