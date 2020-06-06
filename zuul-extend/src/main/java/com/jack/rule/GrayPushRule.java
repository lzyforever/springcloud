package com.jack.rule;

import com.jack.support.RibbonFilterContextHolder;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡策略，在策略中加入灰度发布逻辑
 * 这里是基于RoundRobinRule规则来进行改造的
 */
public class GrayPushRule extends AbstractLoadBalancerRule {

    private AtomicInteger nextServerCyclicCounter;

    private static final boolean AVAILABLE_ONLY_SERVERS = true;

    private static final boolean ALL_SERVERS = false;

    private static Logger logger = LoggerFactory.getLogger(GrayPushRule.class);

    public GrayPushRule() {
        this.nextServerCyclicCounter = new AtomicInteger(0);
    }

    public GrayPushRule(ILoadBalancer lb) {
        this();
        this.setLoadBalancer(lb);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            logger.warn("no load balancer");
            return null;
        } else {
            // 当前有灰度的用户和灰度的服务配置信息，并且灰度的服务在所有服务中则返回该灰度服务给用户
            String curUserId = RibbonFilterContextHolder.getCurrentContext().get("userId");
            String userIds = RibbonFilterContextHolder.getCurrentContext().get("userIds");
            String servers = RibbonFilterContextHolder.getCurrentContext().get("servers");
            System.out.println(Thread.currentThread().getName()+":"+servers);
            if (StringUtils.isNotBlank(servers)) {
                List<String> grayServers = Arrays.asList(servers.split(","));
                if (StringUtils.isNotBlank(userIds) && StringUtils.isNotBlank(curUserId)) {
                    String[] uids = userIds.split(",");
                    if (Arrays.asList(uids).contains(curUserId)) {
                        List<Server> allServers = lb.getAllServers();
                        for (Server server : allServers) {
                            if (grayServers.contains(server.getHostPort())) {
                                return server;
                            }
                        }
                    }
                }
            }

            Server server = null;
            int count = 0;

            while(true) {
                if (server == null && count++ < 10) {
                    List<Server> reachableServers = lb.getReachableServers();
                    List<Server> allServers = lb.getAllServers();
                    // 移除已经设置为灰度发布的服务信息
                    if (StringUtils.isNotBlank(servers)) {
                        reachableServers = removeServer(reachableServers, servers);
                        allServers = removeServer(allServers, servers);
                    }
                    int upCount = reachableServers.size();
                    int serverCount = allServers.size();
                    if (upCount != 0 && serverCount != 0) {
                        int nextServerIndex = this.incrementAndGetModulo(serverCount);
                        server = allServers.get(nextServerIndex);
                        if (server == null) {
                            Thread.yield();
                        } else {
                            if (server.isAlive() && server.isReadyToServe()) {
                                return server;
                            }

                            server = null;
                        }
                        continue;
                    }

                    logger.warn("No up servers available from load balancer: " + lb);
                    return null;
                }

                if (count >= 10) {
                    logger.warn("No available alive servers after 10 tries from load balancer: " + lb);
                }

                return server;
            }
        }
    }

    /**
     * 从服务列表中移除指定的服务器
     */
    private List<Server> removeServer(List<Server> allServers, String servers) {
        List<Server> newServers = new ArrayList<>();
        List<String> grayServers = Arrays.asList(servers.split(","));
        for (Server server : allServers) {
            String hostPort = server.getHostPort();
            if (!grayServers.contains(hostPort)) {
                newServers.add(server);
            }
        }
        return newServers;
    }

    private int incrementAndGetModulo(int modulo) {
        int current;
        int next;
        do {
            current = this.nextServerCyclicCounter.get();
            next = (current + 1) % modulo;
        } while (!this.nextServerCyclicCounter.compareAndSet(current, next));
        return next;
    }


    @Override
    public Server choose(Object key) {
        return this.choose(this.getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }
}
