package com.jack.controller;

import com.jack.config.AsyncTaskExecutePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AsyncController {

    @Autowired
    private AsyncTaskExecutePool pool;

    @GetMapping("/async")
    @ResponseBody
    public String test() {
        pool.getAsyncExecutor().execute(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        return null;
    }

}
