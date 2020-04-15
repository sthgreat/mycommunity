package com.dzkjdx.jsb.mycommunity_article.controller;

import com.dzkjdx.jsb.mycommunity_article.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_article.config.RabbitConfig;
import com.dzkjdx.jsb.mycommunity_article.foreign.UserApp;
import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import com.dzkjdx.jsb.mycommunity_article.pojo.User;
import com.dzkjdx.jsb.mycommunity_article.service.ArticleService;
import com.dzkjdx.jsb.mycommunity_article.vo.ArticleVo;
import com.dzkjdx.jsb.mycommunity_article.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/article")
/**
 * 假设有一个需求，需要在返回article详细信息的同时，返回作者的名字等详细信息，
 * 需要调用其他的接口，在这里实践微服务（分布式）
 */
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserApp userApp;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/add")
    public ResponseVo<Article> addNewArticle(@RequestBody Article article){
        //传入消息队列中，经由消息队列放入数据库，需要一个服务专门处理数据库有关操作
        articleService.addArticle(article);
        return ResponseVo.success(StatusCode.SUCCESS);
    }

    @GetMapping("/get")
    public ResponseVo<ArticleVo> getArticleDetail(@RequestParam(value = "ArticleId") Integer ArticleId){
        Article article = articleService.selectById(ArticleId);
        if(article == null){
            return ResponseVo.error(StatusCode.ARTICLE_NOT_FOUND);
        }
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        User user = userApp.getUser(article.getAuthorId()).getData();
        System.out.println(user.toString());
        BeanUtils.copyProperties(user, articleVo);
        return new ResponseVo<>(StatusCode.SUCCESS.getCode(),
                StatusCode.SUCCESS.getDesc(),
                articleVo);
    }
}
