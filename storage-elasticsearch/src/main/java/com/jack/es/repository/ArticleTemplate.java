package com.jack.es.repository;

import com.jack.es.entity.Article;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Repository
public class ArticleTemplate {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 简单的模糊查询
     */
    public List<Article> queryByTitle(String title) {
        return elasticsearchTemplate.queryForList(new CriteriaQuery(Criteria.where("title").contains(title)), Article.class);
    }

    /**
     * 根据标题全文检索，高亮显示分司结果
     */
    public List<Article> query(String keyword) {
        NativeSearchQueryBuilder builder = buildQuery(keyword);
        return builderResult(builder);
    }

    /**
     * 根据标题全文检索，高亮显示分词结果，分页查询
     */
    public List<Article> queryByPage(String keyword, int page, int limit) {
        NativeSearchQueryBuilder builder = buildQuery(keyword);
        builder.withPageable(PageRequest.of(page, limit));
        return builderResult(builder);
    }

    /**
     * 标题检索结果总数量
     */
    public Long queryTitleCount(String keyword) {
        NativeSearchQueryBuilder builder = buildQuery(keyword);
        return elasticsearchTemplate.count(builder.build());
    }

    /**
     * 查询sid下的标题信息，相当于sql中的 select * from article where title like '%keyword%' and sid = sid;
     */
    public List<Article> query(String keyword, String sid) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withIndices("jack");
        builder.withTypes("article");
        builder.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>"));
        builder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title", keyword))
                .must(QueryBuilders.matchQuery("sid", sid)));
        return builderResult(builder);
    }


    /**
     * 查询sid下的标题信息，相当于sql中的 select * from article where title like '%keyword%' or sid = sid;
     */
    public List<Article> queryByOr(String keyword, String sid) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withIndices("jack");
        builder.withTypes("article");
        builder.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>"));
        builder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title", keyword))
                .should(QueryBuilders.matchQuery("sid", sid)));
        return builderResult(builder);
    }


    /**
     * 构建查询条件生成器
     */
    private NativeSearchQueryBuilder buildQuery(String keyword) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        query.withIndices("jack");
        query.withTypes("article");
        // 生成高亮查询器
        query.withHighlightFields(new HighlightBuilder.Field("title").preTags("<font style='color:red;'>").postTags("</font>"));
        query.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", keyword)));
        return query;
    }

    /** 
     * 构建查询结果
     */
    private List<Article> builderResult(NativeSearchQueryBuilder builder) {
        return elasticsearchTemplate.query(builder.build(), new ResultsExtractor<List<Article>>() {
            @Override
            public List<Article> extract(SearchResponse searchResponse) {
                List<Article> list = new ArrayList<>();
                searchResponse.getHits().forEach(hit -> {
                    Article article = new Article();
                    article.setId(Integer.parseInt(hit.getId()));
                    article.setTitle(hit.getHighlightFields().get("title").fragments()[0].toString());
                    article.setUrl(hit.getSourceAsMap().get("url").toString());
                    article.setContent(hit.getSourceAsMap().get("content").toString());
                    list.add(article);
                });
                return list;
            }
        });
    }
}
