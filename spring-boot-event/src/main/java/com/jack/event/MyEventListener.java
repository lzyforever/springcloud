package com.jack.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 实现ApplicationListener接口，并指定监听的事件类型
 */
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    /**
     * 使用onApplicationEvent方法对消息进行接收处理
     * @param myEvent
     */
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        String msg = myEvent.getMsg();
        System.out.println("Bean MyEventListener接收到了Bean MyEventPublisher发布的消息：" + msg);
    }
}
