package com.jack.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 事件发布类，用来发布事件
 */
@Component
public class MyEventPublisher2  {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void publish(String msg) {
        publisher.publishEvent(new MyEvent(this, msg));
    }

}
