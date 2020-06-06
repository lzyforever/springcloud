package com.jack;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * Guava Cache的使用
 */
public class GuavaApp {
    public static void main(String[] args) {
        final PersonDao dao = new PersonDao();
        // 创建的缓存在1分钟后失效
        LoadingCache<Integer, Person> cacheBuilder = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, Person>() {
                    @Override
                    public Person load(Integer id) throws Exception {
                        return dao.findById(id);
                    }
                });

        try {
            for (;;) {
                Person person = cacheBuilder.get(1);
                System.out.println("person id: " + person.getId() + " person name: " + person.getName());
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
