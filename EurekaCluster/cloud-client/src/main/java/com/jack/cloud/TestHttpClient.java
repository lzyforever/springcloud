package com.jack.cloud;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestHttpClient {
	public static void main(String[] args) throws Exception {
		// ����Ĭ�ϵ�HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// ����6�η���������
		for (int i = 0; i < 6; i++) {
			// ���� GET �����������
			HttpGet httpget = new HttpGet("http://localhost:9000/router");
			// ��ȡ��Ӧ
			HttpResponse response = httpclient.execute(httpget);
			// ���� ��Ӧ�������ַ���
			System.out.println(EntityUtils.toString(response.getEntity()));
		}
	}
}
