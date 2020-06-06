package com.jack.service;

import com.jack.entity.Person;

/**
 * 用户服务接口类
 */
public interface PersonService {
    Person get(String id);
    Person get2(String id);
}
