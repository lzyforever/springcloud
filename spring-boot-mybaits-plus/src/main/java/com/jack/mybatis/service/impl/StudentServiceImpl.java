package com.jack.mybatis.service.impl;

import com.jack.mybatis.entity.Student;
import com.jack.mybatis.mapper.StudentMapper;
import com.jack.mybatis.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack luo
 * @since 2020-07-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
