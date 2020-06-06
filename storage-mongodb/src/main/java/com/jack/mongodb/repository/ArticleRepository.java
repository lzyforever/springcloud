package com.jack.mongodb.repository;

import com.jack.mongodb.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通过repository操作mongodb
 * 只需要定义一个接口，按照一定的规则去定义方法名称就可以了，不需要写方法实现
 * 对于一些简单的操作是非常方便的，可以提高开发效率
 *
 * 我们可以发现规律，这里所有的查询方法都以find开头，比如说findAll()表示查询所有
 * 如果我们要根据某个字段去查询就使用findByAuthor()，author就是你要查询的字段
 * 如果多个字段的话就是findBy字段1 And 字段2
 * 还有排序、忽略大小写、模糊查询等都有类似的语法。看起来真的很简单，只要掌握它的规律
 * 就行，即使你完全不懂Mongodb的语法也能去操作Mongodb
 */
@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, String> {

    /**
     * 分页查询
     */
    Page<Article> findAll(Pageable pageable);

    /**
     * 根据author查询
     */
    List<Article> findByAuthor(String author);

    /**
     * 根据作者和标题查询
     */
    List<Article> findByAuthorAndTitle(String author, String title);

    /**
     * 根据author查询，忽略参数大小写
     */
    List<Article> findByAuthorIgnoreCase(String author);

    /**
     * 根据作者和标题查询，忽略参数大小写
     */
    List<Article> findByAuthorAndTitleAllIgnoreCase(String author, String title);

    /**
     * 根据author查询并按visitCount来倒序排列
     */
    List<Article> findByAuthorOrderByVisitCountDesc(String author);

    /**
     * 根据author查询并按visitCount来正序排列
     */
    List<Article> findByAuthorOrderByVisitCountAsc(String author);

    /**
     * 根据author查询自带排序条件
     */
    List<Article> findByAuthor(String author, Sort sort);
}
