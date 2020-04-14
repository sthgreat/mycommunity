package com.dzkjdx.mycommunity_dbservice.receiver;

import com.dzkjdx.mycommunity_dbservice.dao.ArticleMapper;
import com.dzkjdx.mycommunity_dbservice.pojo.Article;
import com.dzkjdx.mycommunity_dbservice.service.ArticleService;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "addArticle")
public class addArticleReceiver {
    @Autowired
    private ArticleService articleService;

    @RabbitHandler
    public void addArticle(Map<String,String> msg){
        Gson gson = new Gson();
        String messageData = msg.get("messageData");
        Article article = gson.fromJson(messageData, Article.class);
        articleService.addArticle(article);
    }
}
