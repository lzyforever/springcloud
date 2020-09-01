package com.jack.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private ApplicationEventPublisher publisher;

    public void sendMsg(String msg) {
        publisher.publishEvent("MyService Msg... :" + msg);
    }

    @EventListener
    public void test(MyEvent myEvent) {
        sendMsg(myEvent.getMsg());
    }

}
