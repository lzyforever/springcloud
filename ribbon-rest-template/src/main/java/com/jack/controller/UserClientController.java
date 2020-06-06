package com.jack.controller;

import com.jack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 调用接口定义
 * RestTemplate当中比较实用的方法有如下：
 * getForObject
 * getForEntity
 * postForObject
 * 还有put、delete、exchange等，其中exchange可以执行get、post、put、delete这4种请求方式
 */
@RestController
public class UserClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call/data")
    public User getData(@RequestParam("name") String name) {
        return restTemplate.getForObject("http://localhost:8084/user/data?name=" + name, User.class);
    }

    @GetMapping("/call/data/{name}")
    public String getName(@PathVariable("name") String name) {
        return restTemplate.getForObject("http://localhost:8084/user/data/{name}", String.class, name);
    }

    @GetMapping("/call/entity")
    public User getEntity(@RequestParam("name") String name) {
        ResponseEntity<User> user = restTemplate.getForEntity("http://localhost:8084/user/data?name=" + name, User.class);
        if (user.getStatusCodeValue() == 200) {
            return user.getBody();
        }
        return null;
    }

    @GetMapping("/call/save")
    public Integer add() {
        User user = new User(1, "宇哆哆", 18, "成都高新");
        Integer id = restTemplate.postForObject("http://localhost:8084/user/save", user, Integer.class);
        return id;
    }
}
