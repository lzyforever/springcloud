package com.jack.mybatis.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.mybatis.entity.Student;
import com.jack.mybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jack luo
 * @since 2020-07-14
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/add")
    public String add() {
        Student student = new Student();
        student.setName("jack luo");
        student.setAge(12);
        student.setAddress("成都");
        studentService.save(student);
        return "success";
    }


    @GetMapping("/addBatch")
    public String addBatch() {
        List<Student> list = new ArrayList<>();
        Student student = null;
        for (int i = 0; i < 10; i++) {
            student = new Student();
            student.setName("jack luo");
            student.setAge(12);
            student.setAddress("成都");
            list.add(student);
        }
        studentService.saveBatch(list);

        return "success";
    }

    @GetMapping("/get")
    public Object get() {
        Student stu = studentService.getById(1);
        return stu;
    }

    @GetMapping("/page")
    public IPage<Student> page() {
        IPage<Student> page = studentService.page(new Page<>(1, 10));
        return page;
    }

    @GetMapping("/list")
    public List<Student> list() {
        return studentService.list();
    }

    @GetMapping("/listByMap")
    public Object listByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 18);
        return studentService.listByMap(map);
    }

    @GetMapping("/update")
    public Object update() {
        Student student = new Student();
        student.setId(1);
        student.setAge(99);
        student.setName("王二麻子");
        student.setAddress("成都高新");
        return studentService.updateById(student) ? "成功" : "失败";
    }

    @GetMapping("/delete")
    public Object delete() {
        return studentService.removeById(6) ? "成功" : "失败";
    }

    @GetMapping("/deleteList")
    public Object deleteList() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        return studentService.removeByIds(ids) ? "成功" : "失败";
    }

    @GetMapping("/deleteByMap")
    public Object deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "A");
        map.put("age", 17);
        return studentService.removeByMap(map) ? "成功" : "失败";
    }



}
