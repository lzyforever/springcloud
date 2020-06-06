package com.jack.transaction.mq.test.controller;

import com.jack.transaction.mq.test.pojo.Student;
import com.jack.transaction.mq.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
public class TestController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/test")
    public String test() {
        return studentService.update(new Student(1L, "张三")) ? "成功" : "失败";
    }
}
