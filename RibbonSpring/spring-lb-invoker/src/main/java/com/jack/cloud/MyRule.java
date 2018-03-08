package com.jack.cloud;

import java.util.List;
import java.util.Random;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

public class MyRule implements IRule {
	
	private ILoadBalancer lb;

	@Override
	public Server choose(Object key) {
		System.out.println("这个是自定义的规则类");
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
