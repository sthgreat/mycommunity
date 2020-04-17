package com.dzkjdx.jsb.mycommunity_article.service;

import com.dzkjdx.jsb.mycommunity_article.MycommunityArticleApplicationTests;
import com.dzkjdx.jsb.mycommunity_article.constant.RedisConst;
import com.dzkjdx.jsb.mycommunity_article.dao.ArticleMapper;
import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArticleServiceTest extends MycommunityArticleApplicationTests {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void selectById() {
        Article article = articleMapper.selectDescById(1);
        System.out.println(article.toString());
    }

    @Test
    void testRedis(){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(RedisConst.MsgNeedReconsume);
        System.out.println(entries.toString());

    }
}