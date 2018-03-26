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
		// ����get��������������������GET
		Representation response = client.get(MediaType.APPLICATION_JSON);
		// ����JacksonRepresentationʵ��������Ӧת��ΪMap
		JacksonRepresentation jr = new JacksonRepresentation(response, HashMap.class);
		// ��ȡת�����Map����
		Map result = (HashMap) jr.getObject();
		// ������
		System.out.println(result.get("id") + "-" + result.get("name") + "-" + result.get("age") + "-" + result.get("message"));
	}
}
