package com.jack.cloud;

import java.net.URI;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;

/**
 * ����Ribbonԭ��API���е�һ��Ribbon�������
 */
public class TestRibbon {
	public static void main(String[] args) throws Exception {
		ConfigurationManager.loadPropertiesFromResources("ribbon.properties");
		// Ӳ����ķ�ʽ
		// ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
		ConfigurationManager.getConfigInstance().getProperty("my-client.ribbon.listOfServers");
		RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
		HttpRequest request = HttpRequest.newBuilder().uri(new URI("/person")).build();
		for (int i = 0; i < 20; i++) {
			HttpResponse response = client.executeWithLoadBalancer(request);
			String json = response.getEntity(String.class);
			System.out.println(json);
		}
	}
}
