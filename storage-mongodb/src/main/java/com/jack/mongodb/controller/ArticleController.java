package com.jack.mongodb.controller;

import com.jack.mongodb.batchupdate.BatchUpdateOptions;
import com.jack.mongodb.batchupdate.MongoBaseDao;
import com.jack.mongodb.po.Article;
import com.jack.mongodb.repository.ArticleRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 文章控制器
 */
@RestController
public class ArticleController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ArticleRepository articleRepository;


    /**
     * 单条数据添加
     */
    @GetMapping("/save")
    public String save() {
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle("MongoTemplate 的使用 ");
            article.setAuthor("jack luo");
            article.setUrl("http://localhost:9100/detail/" + i);
            article.setTags(Arrays.asList("java", "C#", ".Net", "Python", "Nodejs"));
            article.setVisitCount(0L);
            article.setAddTime(new Date());
            mongoTemplate.save(article);
        }
        return "success";
    }

    /**
     * 批量添加
     */
    @GetMapping("/batchSave")
    public String batchSave() {
        List<Article> articles = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle("MongoTemplate 的使用 ");
            article.setAuthor("jack luo");
            article.setUrl("http://localhost:9100/detail/" + i);
            article.setTags(Arrays.asList("java", "C#", ".Net", "Python", "Nodejs"));
            article.setVisitCount(0L);
            article.setAddTime(new Date());
            articles.add(article);
        }
        mongoTemplate.insert(articles, Article.class);
        return "success";
    }

    /**
     * 更新操作
     */
    @GetMapping("/update")
    public String update() {
        Query query = Query.query(Criteria.where("author").is("jack luo"));
        Update update = Update.update("title", "我要把它们全部都改了").set("visitCount", 10);
        // 只修改第一条
        mongoTemplate.updateFirst(query, update, Article.class);
        // 修改所有的
        // mongoTemplate.updateMulti(query, update, Article.class);

        // 特殊更新，更新author为 luozy 的数据，如果没有author为 luozy 的数据则在此条件创建一条新的数据
        // 当没有符合条件的文档，就以这个条件和更新文档为基础创建一个新的文档，如找到匹配的文档就正常更新
        query = Query.query(Criteria.where("author").is("luozy"));
        update = Update.update("title", "这是一条没有的数据啊，我要加进去！").set("visitCount", 100);
        // 可以看到结果是新插入了一条数据，但是字段只有4个，即：_id、author、title、visit_count
        mongoTemplate.upsert(query, update, Article.class);

        // 更新条件不变，更新字段改成了集合中不存在的，如果用set方法更新的key不存在则创建一个新的key
        query = Query.query(Criteria.where("author").is("luozy"));
        update = Update.update("title", "这个是要新建新的key啊").set("money", 1000);
        mongoTemplate.updateMulti(query, update, Article.class);

        // update的inc方法用于累加操作，将money在之前的基础上加上100
        query = Query.query(Criteria.where("author").is("luozy"));
        update = Update.update("title", "这个是要更新money字段的啊！").inc("money", 100);
        mongoTemplate.updateMulti(query, update, Article.class);

        // update的rename方法用于修改key的名称
        query = Query.query(Criteria.where("author").is("luozy"));
        update = Update.update("title", "这个是要重新改字段名称的啊！").rename("visitCount", "vc");
        mongoTemplate.updateMulti(query, update, Article.class);

        // update的unset方法用于删除key
        query = Query.query(Criteria.where("author").is("luozy"));
        update = Update.update("title", "这个是要删除的字段啊！").unset("money");
        mongoTemplate.updateMulti(query, update, Article.class);

        // update的pull方法用于删除tags数组中的C#
        query = Query.query(Criteria.where("author").is("jack luo"));
        update = Update.update("title", "这个是要删除tags中的C#的啊！").pull("tags", "C#");
        mongoTemplate.updateMulti(query, update, Article.class);

        return "success";
    }

    /**
     * 删除操作
     */
    @GetMapping("/delete")
    public String delete() {
        Query query = Query.query(Criteria.where("author").is("luozy"));
        mongoTemplate.remove(query, Article.class);

        // 如果实体类中没配集合名词，可在删除的时候单独指定集合名称article_info（假如在mongo里面是这个名称）
        query = Query.query(Criteria.where("author").is("luozy"));
        mongoTemplate.remove(query, "article");

        // 查询出符合条件的第一个结果，并将符合条件的数据删除，只会删除第一条
        query = Query.query(Criteria.where("author").is("jack luo"));
        Article article = mongoTemplate.findAndRemove(query, Article.class);
        System.out.println(article);

        // 查询出符合条件的全部数据，并将符合条件的数据全部删除
        query = Query.query(Criteria.where("author").is("jack luo"));
        List<Article> articles = mongoTemplate.findAllAndRemove(query, Article.class);
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // 删除集合，可以传实体类，也可以传名称
        mongoTemplate.dropCollection(Article.class);
        // mongoTemplate.dropCollection("article");

        // 删除数据库
        // mongoTemplate.getDb().drop();

        return "success";
    }

    @GetMapping("/query")
    public String query() {
        // 根据author查询所有符合条件的数据
        Query query = Query.query(Criteria.where("author").is("jack luo"));
        List<Article> articles = mongoTemplate.find(query, Article.class);
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // 只查询符合条件的第一条数据
        query = Query.query(Criteria.where("author").is("jack luo"));
        Article ar = mongoTemplate.findOne(query, Article.class);
        System.out.println(ar);

        //  查询集合中所有数据，不加条件
        List<Article> all = mongoTemplate.findAll(Article.class);
        for (Article ac : all) {
            System.out.println(ac);
        }

        // 查询符合条件的数量
        query = Query.query(Criteria.where("author").is("jack luo"));
        long count = mongoTemplate.count(query, Article.class);
        System.out.println("author为jack luo的有" + count + "条");

        // 根据主键ID查询
        ar = mongoTemplate.findById(new ObjectId("5eb94fd2850c965f9c5ba5ee"), Article.class);
        System.out.println("ID为5eb94fd2850c965f9c5ba5ee：");

        // in查询
        List<String> authors = Arrays.asList("jack luo", "luozy");
        query = Query.query(Criteria.where("author").in(authors));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("author为jack luo和luozy的：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // ne(!=)查询
        query = Query.query(Criteria.where("author").ne(authors));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("author不为jack luo和luozy的：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // lt(<)查询
        query = Query.query(Criteria.where("visitCount").lt(10));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("visitCount小于10的：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // 范围查询，大于5小于10
        query = Query.query(Criteria.where("visitCount").gt(5).lt(10));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("visitCount大于5小于10的：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // 模糊查询，author中包含a的数据
        query = Query.query(Criteria.where("author").regex("a"));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("author中包含 a字符的：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // 数组查询，查询tags里数量为3的数据
        query = Query.query(Criteria.where("tags").size(3));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("tags的数量为3的：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        // or查询，查询author为jack luo或者visitCount为0的数据
        query = Query.query(Criteria.where("").orOperator(
                Criteria.where("author").is("jack luo"),
                Criteria.where("visitCount").is(0)
        ));
        articles = mongoTemplate.find(query, Article.class);
        System.out.println("author为jack luo或者visitCount为0的数据：");
        for (Article ac : articles) {
            System.out.println(ac);
        }

        return "success";
    }

    /**
     * 使用repository的方式进行查询
     */
    @GetMapping("/findAll")
    public String findAll() {
        Iterable<Article> all = articleRepository.findAll();
        all.forEach(System.out::println);

        System.out.println("--------------");
        List<Article> list = articleRepository.findByAuthor("jack luo");
        list.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByAuthorAndTitle")
    public String findByAuthorAndTitle() {
        List<Article> rs = articleRepository.findByAuthorAndTitle("jack luo", "MongoTemplate 的使用");
        rs.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByAuthorIgnoreCase")
    public String findByAuthorIgnoreCase() {
        List<Article> rs = articleRepository.findByAuthorIgnoreCase("JACK LUO");
        rs.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByAuthorAndTitleIgnoreCase")
    public String findByAuthorAndTitleIgnoreCase() {
        List<Article> rs = articleRepository.findByAuthorAndTitleAllIgnoreCase("JACK LUO", "MONGOTEMPLATE 的使用");
        rs.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByAuthorOrderByVisitCountDesc")
    public String findByAuthorOrderByVisitCountDesc() {
        List<Article> rs = articleRepository.findByAuthorOrderByVisitCountDesc("jack luo");
        rs.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByAuthorOrderByVisitCountAsc")
    public String findByAuthorOrderByVisitCountAsc() {
        List<Article> rs = articleRepository.findByAuthorOrderByVisitCountAsc("jack luo");
        rs.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByAuthor")
    public String findByAuthorBySort() {
        List<Article> rs = articleRepository.findByAuthor("jack luo", new Sort(Sort.Direction.DESC, "visitCount"));
        rs.forEach(System.out::println);
        return "success";
    }

    @GetMapping("/findByPage")
    public String findByPage() {
        int page = 1;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.ASC, "visitCount"));
        Page<Article> pageInfo = articleRepository.findAll(pageable);
        System.out.println("总数量为：" + pageInfo.getTotalElements());
        System.out.println("总页面为：" + pageInfo.getTotalPages());
        pageInfo.getContent().forEach(System.out::println);

        return "success";
    }

    @GetMapping("/batchUpdate")
    public Object batchUpdate() {
        List<BatchUpdateOptions> list = new ArrayList<>();
        list.add(new BatchUpdateOptions(Query.query(Criteria.where("author").is("jack luo")), Update.update("title", "批量更新"), true, true));
        list.add(new BatchUpdateOptions(Query.query(Criteria.where("author").is("luozy")), Update.update("title", "批量更新"), true, true));
        int n = MongoBaseDao.batchUpdate(mongoTemplate, "article", list, true);
        System.out.println("受影响的行数：" + n);
        return n;
    }
    
}
