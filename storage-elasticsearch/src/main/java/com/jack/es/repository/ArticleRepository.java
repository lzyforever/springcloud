package com.jack.es.repository;

import com.jack.es.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定义一个自己的Repository同时继承最基础的CrudRepository
 * CrudRepository中定义了很多基本的操作方法，除了CrudRepository其实还有很多
 * Repository可以让我们直接使用，通过查找Repository接口的实现类就可以看出来
 *
 * 关于Repository方式的使用就不用多说了，在mongodb中的使用方式一样，可以根据
 * 一定的规则定义方法名来操作数据，也可以基于Repository提供的基础方法去操作
 *
 */
@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTitleContaining(String title);
}
