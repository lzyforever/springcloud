package com.jack.mongodb.controller;

import com.jack.mongodb.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/test")
    public String test() {

        List<Student> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Student stu = new Student();
            stu.setName("张三" + i);
            list.add(stu);
        }
        mongoTemplate.insert(list, Student.class);

        return "success";
    }
}
