package com.jack.service.impl;

import com.jack.entity.Person;
import com.jack.service.PersonService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Cacheable(value = "get", key = "'get'+ #id")
    @Override
    public Person get(String id) {
        Person person = new Person();
        person.setId("1221");
        person.setName("jack luo");
        person.setAddress("成都");
        return person;
    }

    @Cacheable(value = "get2", keyGenerator = "keyGenerator")
    @Override
    public Person get2(String id) {
        Person person = new Person();
        person.setId("3333");
        person.setName("luozy");
        person.setAddress("成都彭州");
        return person;
    }
}
