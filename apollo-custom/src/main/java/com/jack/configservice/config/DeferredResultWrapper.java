package com.jack.configservice.config;

import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

/**
 * 延迟消息业务包装类
 * Apollo的实时推送是基于Spring DeferredResult 实现的，这里对它进行业务包装
 *
 * 通过setResult()方法设置返回结果客户端，当配置发生变化，然后能过消息监听器
 * 通知客户端的原理，具体通知哪个客户端，在NotificationControllerV2中getApolloConfigNotifications中进行定义
 */
public class DeferredResultWrapper {
    /** 默认超时时间 60s*/
    private static final long TIMEOUT = 60 * 1000;

    private static final ResponseEntity<List<ApolloConfigNotification>> NOT_MODIFIED_RESPONSE_LIST = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    private DeferredResult<ResponseEntity<List<ApolloConfigNotification>>> result;

    public DeferredResultWrapper() {
        // 指定超时的时间和超时后返回的响应码，如果60s内没有消息监听器进行通知
        // 那么这个请求就会超时，超时后客户端收到的响应码就是304
        result = new DeferredResult<>(TIMEOUT, NOT_MODIFIED_RESPONSE_LIST);
    }

    public void onTimeout(Runnable callback) {
        result.onTimeout(callback);
    }

    public void onCompletion(Runnable callback) {
        result.onCompletion(callback);
    }

    public void setResult(ApolloConfigNotification notification) {
        setResult(Lists.newArrayList(notification));
    }

    public void setResult(List<ApolloConfigNotification> notifications) {
        result.setResult(new ResponseEntity<>(notifications, HttpStatus.OK));
    }

    public DeferredResult<ResponseEntity<List<ApolloConfigNotification>>> getResult() {
        return this.result;
    }
}
