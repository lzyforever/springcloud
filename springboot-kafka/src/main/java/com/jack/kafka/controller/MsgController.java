package com.jack.kafka.controller;

import com.jack.kafka.constant.Topic;
import com.jack.kafka.producer.MsgProducer;
import com.jack.kafka.service.MsgService;
import com.jack.kafka.thread.MsgConsumerThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@Slf4j
public class MsgController {
    @Autowired
    private MsgProducer msgProducer;

    @Autowired
    private MsgService msgService;

    @GetMapping("/sendSimpleMsg")
    public String sendSimpleMsg() {
        msgProducer.sendMsg(Topic.SIMPLE, "hello my name is jack!");
        return "success";
    }

    @GetMapping("/sendGroupMsg")
    public String sendGroup() {
        for (int i = 0; i < 4; i++) {
            msgProducer.sendGroupMsg(Topic.GROUP, "hello group " + i);
        }
        return "group success";
    }


    /**
     * 获取消息
     */
    @GetMapping("/getMsg")
    public String getMsg() {
        long startTime = System.currentTimeMillis();
        String msg = msgService.getMsg();
        long endTime = System.currentTimeMillis();
        log.info("获取任务时间：{}ms", endTime - startTime);
        return msg;
    }

    /**
     * 获取消息
     */
    @GetMapping("/getMsg2")
    public String getMsg2() {
        long startTime = System.currentTimeMillis();
        String msg = msgService.getMsg2();
        long endTime = System.currentTimeMillis();
        log.info("获取任务时间：{}ms", endTime - startTime);
        return msg;
    }

    /**
     * 处理完成业务回传业务唯一标识修改状态
     * @param num 档案号
     */
    @GetMapping("/resolve")
    public String resolve(@RequestParam("num") Long num) {
        msgService.resolve(num);
        return "success";
    }

    @GetMapping("/getMsgThread")
    public String getMsgThread() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        StringBuilder builder = new StringBuilder(10);
        for (int i = 0; i < 10; i ++) {
            MsgConsumerThread thread = new MsgConsumerThread(msgService);
            Future future = pool.submit(thread);
            String msg = future.get() == null ? "abc" : future.get().toString();
            builder.append(msg).append(";");
        }
        pool.shutdown();
        return builder.toString();
    }


    @GetMapping("/sendJackMsg")
    public String sendJackMsg() {
        msgProducer.sendMsg("JACK", "hello jack");
        return "success";
    }

}
