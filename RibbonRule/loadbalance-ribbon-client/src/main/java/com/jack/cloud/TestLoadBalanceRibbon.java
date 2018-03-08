package com.jack.cloud;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.client.http.RestClient;

/**
 * 使用Ribbon的负载均衡器
 */
public class TestLoadBalanceRibbon {

	public static void main(String[] args) throws Exception {
		//test1();
		//test2();
		test3();
	}

	/**
	 * 测试默认的负载均衡
	 */
	public static void test1() {
		ILoadBalancer lBalancer = new BaseLoadBalancer();
		List<Server> servers = new ArrayList<>();
		servers.add(new Server("localhost", 8080));
		servers.add(new Server("localhost", 8081));
		lBalancer.addServers(servers);

		for (int i = 0; i < 10; i++) {
			Server server = lBalancer.chooseServer(null); // 默认是采用RuleRibonRule
			System.out.println(server);
		}
	}

	/**
	 * 测试自定义规则的负载均衡
	 */
	public static void test2() {
		BaseLoadBalancer lb = new BaseLoadBalancer();

		MyRule rule = new MyRule();
		rule.setLoadBalancer(lb);
		lb.setRule(rule);

		List<Server> servers = new ArrayList<>();
		servers.add(new Server("localhost", 8080));
		servers.add(new Server("localhost", 8081));

		lb.addServers(servers);

		for (int i = 0; i < 10; i++) {
			Server server = lb.chooseServer(null); // 默认是采用RuleRibonRule
			System.out.println(server);
		}
	}
	
	/**
	 * 测试自定义规则，并配置该规的负载
	 */
	public static void test3() throws Exception {
		//ConfigurationManager.loadPropertiesFromResources("ribbon.properties");
		// 硬编码的方式
		ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.listOfServers", "localhost:8080,localhost:8081");
		ConfigurationManager.getConfigInstance().setProperty("my-client.ribbon.NFLoadBalancerRuleClassName", MyRule.class.getName());
		//ConfigurationManager.getConfigInstance().getProperty("my-client.ribbon.listOfServers");
		RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
		HttpRequest request = HttpRequest.newBuilder().uri(new URI("/person/1")).build();
		for (int i = 0; i < 20; i++) {
			HttpResponse response = client.executeWithLoadBalancer(request);
			String json = response.getEntity(String.class);
			System.out.println(json);
		}
	}
}
