package org.jack.cloud;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class CxfClient {
	public static void main(String[] args) throws Exception {
		// ����WebClient
		WebClient client = WebClient.create("http://localhost:8080/person/1");
		// ��ȡ��Ӧ
		Response response = client.get();
		// ��ȡ��Ӧ����
		InputStream ent = (InputStream) response.getEntity();
		String content = IOUtils.readStringFromStream(ent);
		// ����ַ���
		System.out.println(content);
	}
}
