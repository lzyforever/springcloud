package com.jack;

/**
 * 用户数据操作类，这里简化了，就不写接口，直接模拟操作
 */
public class PersonDao {
    Person findById(Integer id) {
        System.out.println("query id : " + id);
        Person person = new Person();
        person.setId(1);
        person.setName("jack luo");
        return person;
    }
}
