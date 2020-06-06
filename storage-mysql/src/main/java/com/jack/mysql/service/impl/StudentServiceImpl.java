package com.jack.mysql.service.impl;

import com.jack.jdbc.EntityService;
import com.jack.jdbc.PageQueryParam;
import com.jack.mysql.entity.Student;
import com.jack.mysql.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生业务实现类
 */
@Service
public class StudentServiceImpl extends EntityService<Student> implements StudentService {

    @Override
    public long count() {
        return super.count();
    }

    @Override
    public List<Student> findAll() {
        return super.list(Student.ORDER_FIELDS);
    }

    @Override
    public List<Student> find(String city) {
        return super.list("city", city);
    }

    @Override
    public List<Student> find(String city, String region) {
        return super.list(new String[]{"city", "region"}, new Object[]{city, region});
    }

    @Override
    public List<Student> find(String city, String region, String name) {
        return super.list(Student.SHOW_FIELDS, Student.QUERY_FIELDS, new Object[] {city, region, name});
    }

    @Override
    public List<Student> findAll(PageQueryParam page) {
        return super.listForPage(page.getStart(), page.getLimit(), Student.ORDER_FIELDS);
    }

    @Override
    public boolean exists(String city) {
        return super.exists("city", city);
    }

    @Override
    public List<Student> in(String[] names) {
        return super.in(new String[] {"city", "region"}, "name", names);
    }

    @Override
    public Student get(String id) {
        return super.getById("id", id);
    }

    @Transactional
    @Override
    public void delete(String name) {
        super.deleteById("name", name);
    }

    @Override
    public void save(Student student) {
        super.save(student);
    }

    @Override
    public void saveList(List<Student> list) {
        super.batchSave(list);
    }

    @Override
    public void update(Student student) {
        super.update(student, "id");
    }

    @Override
    public void updateList(List<Student> list) {
        super.batchUpdate(list, "id", "city");
    }

    @Override
    public void generator() {
        super.getJdbcTemplate().generatePoClass("com.jack.mysql.entity", "jack luo", "F://generater//java");
    }
}
