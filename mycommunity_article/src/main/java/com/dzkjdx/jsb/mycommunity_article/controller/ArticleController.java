package com.dzkjdx.jsb.mycommunity_article.controller;

import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import com.dzkjdx.jsb.mycommunity_article.service.ArticleService;
import com.dzkjdx.jsb.mycommunity_article.vo.ArticleVo;
import com.dzkjdx.jsb.mycommunity_article.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
/**
 * 假设有一个需求，需要在返回article详细信息的同时，返回作者的名字等详细信息，
 * 需要调用其他的接口，在这里实践微服务（分布式）
 */
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    public ResponseVo<Article> addNewArticle(@RequestBody Article article){
        //TODO 传入消息队列中，经由消息队列放入数据库，需要一个服务专门处理数据库
        return articleService.addArticle(article);
    }

    @GetMapping("/get")
    public ResponseVo<ArticleVo> getArticleDetail(@RequestParam(value = "AuthorId") Integer authorId){
        return null;
    }
}
