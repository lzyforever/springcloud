package org.jack.cloud;

import java.util.HashMap;
import java.util.Map;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class RestletClient {

	public static void main(String[] args) throws Exception {
		ClientResource client = new ClientResource("http://localhost:8080/person/1");
		// 调用get方法，服务器发布的是GET
		Representation response = client.get(MediaType.APPLICATION_JSON);
		// 创建JacksonRepresentation实例，将响应转换为Map
		JacksonRepresentation jr = new JacksonRepresentation(response, HashMap.class);
		// 获取转换后的Map对象
		Map result = (HashMap) jr.getObject();
		// 输出结果
		System.out.println(result.get("id") + "-" + result.get("name") + "-" + result.get("age") + "-" + result.get("message"));
	}
}
