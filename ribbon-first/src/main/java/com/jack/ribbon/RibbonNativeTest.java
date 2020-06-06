package com.jack.ribbon;

import com.netflix.loadbalancer.*;
import com.netflix.loadbalancer.reactive.LoadBalancerCommand;
import com.netflix.loadbalancer.reactive.ServerOperation;
import rx.Observable;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Ribbon本地测试
 * 该示例主要演示了Ribbon如何去做负载操作，调用接口的最底层的HttpURLConnection，当然也可以用别的客户端来测试
 * 或者使用Ribbon Client执行程序
 */
public class RibbonNativeTest {
    public static void main(String[] args) {
        // 服务列表
        List<Server> serverList = Arrays.asList(new Server("localhost", 8081), new Server("localhost", 8083));
        //构建负载实例
        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder().buildFixedServerListLoadBalancer(serverList);
        // 这个是轮询规则
        loadBalancer.setRule(new RoundRobinRule());
        // 这个是随机规则
        //loadBalancer.setRule(new RandomRule());
        // 调用5次来测试效果
        for (int i = 0; i < 5; i++) {
            String result = LoadBalancerCommand.<String>builder().withLoadBalancer(loadBalancer)
                    .build().submit(new ServerOperation<String>() {
                        public Observable<String> call(Server server) {
                            try {
                                String addr = "http://" + server.getHost() + ":" + server.getPort() + "/user/hello";
                                System.out.println("调用地址： " + addr);
                                URL url = new URL(addr);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.connect();
                                InputStream ins = conn.getInputStream();
                                byte[] data = new byte[ins.available()];
                                ins.read(data);
                                return Observable.just(new String(data));
                            } catch(Exception ex) {
                                return Observable.error(ex);
                            }
                        }
                    }).toBlocking().first();
            System.out.println("调用结果: " + result);
        }
    }
}
