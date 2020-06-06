package com.jack.configservice.controller;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.jack.configservice.config.ApolloConfigNotification;
import com.jack.configservice.config.DeferredResultWrapper;
import com.jack.configservice.config.ReleaseMessageListener;
import com.jack.configservice.entity.ReleaseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 通知Controller
 * 在apollo里面的的推送逻辑比较多，这里就简单的写一个接口，用队列存储，测试的时候
 * 直接调用这个接口摸拟配置有更新，发送ReleaseMessage
 * 提供/getConfig接口，供客户端启动时调用
 */
@RestController
public class NotificationControllerV2 implements ReleaseMessageListener {

    /** 模拟配置更新，往里插入数据表示有更新 */
    public static Queue<String> queue = new LinkedBlockingDeque<>();

    /** 消息内容，Key其实就是消息内容，Value就是DeferredResult的业务包装类 */
    private final Multimap<String, DeferredResultWrapper> deferredResults = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    @GetMapping("/addMsg")
    public String addMsg() {
        queue.add("xxx");
        return "success";
    }

    /**
     * 客户端在启动时会调用/getConfig接口
     * 这个时修会调用getApolloConfigNotifications()方法，获取有没有配置的变更信息
     * 如果有的话证明配置修改过，直接就通过 deferredResultWrapper.setResult(newNotifications)返回给客户端
     * 客户端收到结果后重新拉取配置的信息覆盖本地的配置
     */
    @GetMapping("/getConfig")
    public DeferredResult<ResponseEntity<List<ApolloConfigNotification>>> getConfig() {
        DeferredResultWrapper deferredResultWrapper = new DeferredResultWrapper();
        List<ApolloConfigNotification> newNotifications = getApolloConfigNotifications();

        if (!CollectionUtils.isEmpty(newNotifications)) {
            // 如果有的话证明配置修改过，将配置返回给客户端
            deferredResultWrapper.setResult(newNotifications);
        } else {
            deferredResultWrapper.onTimeout(() -> {
                System.err.println("onTimeout");
            });
            deferredResultWrapper.onCompletion(() -> {
                System.err.println("onCompletion");
            });
            // 如果没有修改，就将deferredResultWrapper添加到deferredResults中，等待后续配置发生
            // 变化时消息监听器进行通知，同时这个请求就会挂起，不会立即返回
            // 挂起是通过DeferredResultWrapper中的构造函数中实现的
            deferredResults.put("xxxx", deferredResultWrapper);
        }
        return deferredResultWrapper.getResult();
    }

    /**
     * 获取配置修改信息
     */
    private List<ApolloConfigNotification> getApolloConfigNotifications() {
        List<ApolloConfigNotification> list = new ArrayList<>();
        String result = queue.poll();
        if (null != result) {
            list.add(new ApolloConfigNotification("application", 1));
        }
        return list;
    }

    @Override
    public void handleMessage(ReleaseMessage message) {
        System.out.println("handleMessage：" + message);
        List<DeferredResultWrapper> results = Lists.newArrayList(deferredResults.get("xxxx"));
        for (DeferredResultWrapper deferredResultWrapper: results) {
            List<ApolloConfigNotification> list = new ArrayList<>();
            list.add(new ApolloConfigNotification("application", 1));
            deferredResultWrapper.setResult(list);
        }
    }
}
