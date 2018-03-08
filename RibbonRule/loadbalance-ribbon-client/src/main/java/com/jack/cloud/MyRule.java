package com.jack.cloud;

import java.util.List;
import java.util.Random;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

/**
 * �Զ��帺�ؾ������
 */
public class MyRule implements IRule {
	
	private ILoadBalancer lb;

	/**
	 * �ص������ͨ��ʵ�ָýӿڣ������Լ��Ĺ����ڴ�
	 * ����8080�������Ĵ����������Ի�ȡһ��10�����������7������8080�Ļ������
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
