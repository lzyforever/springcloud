package com.jack.mysql.service;

import com.jack.jdbc.PageQueryParam;
import com.jack.mysql.entity.Student;

import java.util.List;

/**
 * 学生业务接口类
 */
public interface StudentService {
    long count();

    List<Student> findAll();

    List<Student> find(String city);

    List<Student> find(String city, String region);

    List<Student> find(String city, String region, String name);

    List<Student> findAll(PageQueryParam page);

    boolean exists(String city);

    List<Student> in(String[] names);

    Student get(String id);

    void delete(String name);

    void save(Student student);

    void saveList(List<Student> list);

    void update(Student student);

    void updateList(List<Student> list);

    void generator();
}
