package com.jack.controller;

import com.jack.entity.Person;
import com.jack.mongodb.repository.PersonRepository;
import com.jack.service.CacheService;
import com.jack.service.Closure;
import com.jack.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 测试Redis
 */
@RestController
public class CacheController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private CacheService cacheService;

    @GetMapping("/test")
    public String test() {
        // 设置一个key为jack，value为luozy的缓存，缓存时间为1小时
        stringRedisTemplate.opsForValue().set("jack", "luozy", 1, TimeUnit.HOURS);

        // 把将刚设进去的缓存通过key来取出来
        String value = stringRedisTemplate.opsForValue().get("jack");
        System.out.println("刚才设进去的值是：" + value);

        // 将刚才设置去的缓存通过指定key来删除
        stringRedisTemplate.delete("jack");

        // 判断一个key在不在redis当中
        boolean exists = stringRedisTemplate.hasKey("jack");
        System.out.println("key 为jack的缓存是否存在：" + exists);

        // 获取Redis的连接
        RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();

        // 通过调用底层命令的方式进行设置缓存
        connection.set("haha".getBytes(), "123321".getBytes());

        // 通过调用底层命令的方式进行获取缓存
        System.out.println("设置的key为 haha 的值为：" + new String(connection.get("haha".getBytes())));
        return "success";
    }

    @GetMapping("/test2")
    public String testRespository() {
        Person person = new Person();
        person.setName("jack luo");
        person.setAddress("成都");
        // 注意：数据保存到Redis中会变成两部分
        // 一部分是一个set，里面存储的是所有数据的ID值
        // 另一部分是一个Hash，存储的是具体每条数据
        personRepository.save(person);

        Optional<Person> personObj = personRepository.findById(person.getId());
        System.out.println("id: " + personObj.get().getId());
        System.out.println("name: " + personObj.get().getName());
        System.out.println("address: " + personObj.get().getAddress());
        System.out.println("repository count: " + personRepository.count());

        personRepository.delete(person);

        return "success";
    }

    @GetMapping("/get")
    public Person get() {
        return personService.get("1221");
    }

    @GetMapping("/get2")
    public Person get2() {
        return personService.get2("3333");
    }

    @GetMapping("/callback")
    public String getCallback() {
        String cacheKey = "1221";
        return cacheService.getCache(cacheKey, new Closure<String, String>() {
            @Override
            public String execute(String id) {
                // 在这里面可以查询数据库获取数据
                return id + "hello";
            }
        });
    }

}
