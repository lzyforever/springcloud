package com.jack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试Controller
 */
@RestController
public class TestController {


    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;



    @GetMapping("/test")
    public String test(){
        return "hello world!";
    }

    @GetMapping("/add")
    public String add() {
        ReactiveHashOperations<String, String, String> hashOpts = reactiveRedisTemplate.opsForHash();
        Mono<Boolean> mono1 = hashOpts.put("apple", "x", "6000");
        mono1.subscribe(System.out::println);
        Mono<Boolean> mono2 = hashOpts.put("apple", "xr", "5000");
        mono2.subscribe(System.out::println);
        Mono<Boolean> mono3 = hashOpts.put("apple", "xs max", "8000");
        mono3.subscribe(System.out::println);
        return "OK";
    }

    @GetMapping("/get/{key}/{value}")
    public Mono get(@PathVariable String key, @PathVariable String value) {
        ReactiveHashOperations hashOpts = reactiveRedisTemplate.opsForHash();
        return hashOpts.get(key, value);
    }

}
