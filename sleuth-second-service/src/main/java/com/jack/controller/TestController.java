package com.jack.controller;

import brave.Tracer;
import com.jack.feign.SleuthFirstFeign;
import com.jack.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SleuthFirstFeign sleuthFirstFeign;

    @Autowired
    private Tracer tracer;

    @Autowired
    private MyService myService;

    @GetMapping("/test")
    public String test() {
        logger.info("invoke sleuth-second-service TestController test method");
        return "hello service B";
    }

    @GetMapping("/call")
    public String call() {
        logger.info("invoke sleuth-second-service TestController call method");
        return sleuthFirstFeign.test();
    }

    @GetMapping("/msg/{msg}")
    public String msg(@PathVariable String msg) {
        logger.info("invoke sleuth-second-service TestController msg method");
        String rs = sleuthFirstFeign.test();
        myService.writeMsg(msg);
        return rs;
    }

    @GetMapping("/log/{log}")
    public String log(@PathVariable String log) {
        logger.info("invoke sleuth-second-service TestController log method");
        tracer.currentSpan().tag("用户", "luozy");
        String rs = sleuthFirstFeign.test();
        myService.saveLog(log);
        return rs;
    }

}
