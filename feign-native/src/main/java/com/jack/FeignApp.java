package com.jack;

/**
 * 原生的Feign使用
 */
public class FeignApp {
    public static void main(String[] args) {
        UserRemoteClient client = RestApiCallUtils.getRestClient(UserRemoteClient.class, "http://localhost:8083");
        System.out.println("调用结果是：" + client.hello());
    }
}
