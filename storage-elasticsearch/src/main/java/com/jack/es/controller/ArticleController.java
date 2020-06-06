package com.jack.es.controller;

import com.jack.es.entity.Article;
import com.jack.es.repository.ArticleRepository;
import com.jack.es.repository.ArticleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTemplate articleTemplate;


    @GetMapping("/add")
    public Object add() {
        Article article = new Article();
        article.setId(1);
        article.setSid("abcdefg");
        article.setTitle("abc");
        article.setUrl("http://www.baidu.com");
        article.setContent("我是内容啊你是什么啊111");
        articleRepository.save(article);
        return "success";
    }

    @GetMapping("/findAll")
    public Object findAll() {
        List<Article> list = new ArrayList<>();
        articleRepository.findAll().forEach(article -> {
            list.add(article);
        });
        return list;
    }


    @GetMapping("/query/{title}")
    public Object query(@PathVariable String title) {
        return articleRepository.findByTitleContaining(title);
    }


    // -----------template使用--------------

    @GetMapping("/queryByTitle/{title}")
    public Object queryByTitle(@PathVariable String title) {
        return articleTemplate.queryByTitle(title);
    }

    @GetMapping("/queryKey/{keyword}")
    public Object queryKey(@PathVariable String keyword) {
        return articleTemplate.query(keyword);
    }

    @GetMapping("/queryByPage/{keyword}/{page}/{limit}")
    public Object queryByPage(@PathVariable String keyword, @PathVariable int page, @PathVariable int limit) {
        return articleTemplate.queryByPage(keyword, page, limit);
    }

    @GetMapping("/queryTitleCount/{keyword}")
    public Object queryTitleCount(@PathVariable String keyword) {
        return articleTemplate.queryTitleCount(keyword);
    }

    @GetMapping("/queryKeyAndSid/{keyword}/{sid}")
    public Object queryKeyAndSid(@PathVariable String keyword, @PathVariable String sid) {
        return articleTemplate.query(keyword, sid);
    }

    @GetMapping("/queryKeyOrSid/{keyword}/{sid}")
    public Object queryKeyOrSid(@PathVariable String keyword, @PathVariable String sid) {
        return articleTemplate.queryByOr(keyword, sid);
    }

}
