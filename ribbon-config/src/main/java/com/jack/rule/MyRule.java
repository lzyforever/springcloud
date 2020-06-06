package com.jack.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 自定义Ribbon负载策略
 */
public class MyRule implements IRule {

    private ILoadBalancer loadBalancer;

    @Override
    public Server choose(Object o) {
        List<Server> servers = loadBalancer.getAllServers();
        for (Server server : servers) {
            System.out.println(server.getHostPort());
        }
        // 这里简单的演示，就直接返回第一个Server，可以在这个里面根据自己的需求加逻辑处理，决定返回哪个Server
        return servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        this.loadBalancer = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.loadBalancer;
    }
}
