package com.jack.kafka.thread;

import com.jack.kafka.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 消费消息线程类
 */
@Component
@Slf4j
public class MsgConsumerThread implements Callable {

    @Autowired
    private MsgService msgService;

    public MsgConsumerThread(MsgService msgService) {
        this.msgService = msgService;
    }

    @Override
    public Object call() throws Exception {
        log.info("当前线程：{}", Thread.currentThread().getName());
        String msg = msgService.getMsg2();
        System.err.println("本条线程 = " + Thread.currentThread().getName() +", 消息 = " + msg);
        log.info("线程返回消息 = {}", msg);
        return msg;
    }
}
