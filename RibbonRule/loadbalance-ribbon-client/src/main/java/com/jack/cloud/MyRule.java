package com.jack.cloud;

import java.util.List;
import java.util.Random;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

/**
 * 自定义负载均衡规则
 */
public class MyRule implements IRule {
	
	private ILoadBalancer lb;

	/**
	 * 重点在这里：通过实现该接口，定义自己的规则，在此
	 * 想让8080负责更多的处理请求，所以获取一个10随机数并大于7，所以8080的机会更大
	 */
	@Override
	public Server choose(Object key) {
		Random random = new Random();
		int num = random.nextInt(10);
		List<Server> servers = lb.getAllServers();
		if (num > 7) {
			return getServerByPort(servers, 8081);
		}
		return getServerByPort(servers, 8080);
	}
	
	private Server getServerByPort(List<Server> servers, int port) {
		for (Server server : servers) {
			if (server.getPort() == port) {
				return server;
			}
		}
		return null;
	}

	@Override
	public void setLoadBalancer(ILoadBalancer lb) {
		this.lb = lb;

	}

	@Override
	public ILoadBalancer getLoadBalancer() {
		return this.lb;
	}

}
