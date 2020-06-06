package com.jack.mysql.controller;

import com.jack.jdbc.PageQueryParam;
import com.jack.mysql.entity.Student;
import com.jack.mysql.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/count")
    public Object count() {
        return studentService.count();
    }

    @GetMapping("/findAll")
    public Object findAll() {
        return studentService.findAll();
    }

    @GetMapping("/findByCity/{city}")
    public Object findByCity(@PathVariable String city) {
        return studentService.find(city);
    }

    @GetMapping("/findByCityAndRegion/{city}/{region}")
    public Object findByCityAndRegion(@PathVariable String city, @PathVariable String region) {
        return studentService.find(city, region);
    }

    @GetMapping("/findByCityAndRegionAndName/{city}/{region}/{name}")
    public Object findByCityAndRegionAndName(@PathVariable String city, @PathVariable String region, @PathVariable String name) {
        return studentService.find(city, region, name);
    }

    @GetMapping("/findByPage/{page}/{limit}")
    public Object findByPage(@PathVariable int page, @PathVariable int limit) {
        PageQueryParam param = new PageQueryParam(page, limit);
        return studentService.findAll(param);
    }

    @GetMapping("/existsCity/{city}")
    public Object existsCity(@PathVariable String city) {
        return studentService.exists(city);
    }

    @GetMapping("/inName/{names}")
    public Object inName(@PathVariable String names) {
        return studentService.in(names.split(","));
    }

    @GetMapping("/getById/{id}")
    public Object getById(@PathVariable String id) {
        return studentService.get(id);
    }

    @GetMapping("/deleteByName/{name}")
    public Object deleteByName(@PathVariable String name) {
        studentService.delete(name);
        return "success";
    }

    @GetMapping("/saveStudent")
    public Object saveStudent() {
        Student student = new Student();
        student.setId("1");
        student.setName("张三");
        student.setCity("成都");
        student.setRegion("武候区");
        student.setLdNum("11栋");
        student.setUnitNum("2单元");
        studentService.save(student);
        return "success";
    }

    @GetMapping("/saveBatchStudent")
    public Object saveBatchStudent() {
        List<Student> stus = new ArrayList<>();
        Student student = null;
        for (int i = 10; i < 100; i ++) {
            student = new Student();
            student.setId(i+"");
            student.setName("张三" + i);
            student.setCity("成都");
            student.setRegion("武候区");
            student.setLdNum("11栋");
            student.setUnitNum("2单元");
            stus.add(student);
        }
        studentService.saveList(stus);
        return "success";
    }

    @GetMapping("/updateStudent")
    public Object updateStudent() {
        Student student = new Student();
        student.setId("1");
        student.setName("张三峰");
        student.setCity("上海");
        student.setRegion("浦东新区");
        student.setLdNum("110栋");
        student.setUnitNum("20单元");
        studentService.update(student);
        return "success";
    }

    @GetMapping("/updateBatchStudent")
    public Object updateBatchStudent() {
        List<Student> stus = new ArrayList<>();
        Student student = null;
        for (int i = 10; i < 100; i ++) {
            student = new Student();
            student.setId(i+"");
            student.setName("张三" + i * 100);
            student.setCity("成都" + i);
            student.setRegion("武候区" + i);
            student.setLdNum(i * new Random().nextInt(100) + "栋");
            student.setUnitNum(i * 2 * new Random().nextInt(100) + "单元");
            stus.add(student);
        }
        studentService.updateList(stus);
        return "success";
    }

    @GetMapping("/generator")
    public String generator() {
        studentService.generator();
        return "success";
    }

}
