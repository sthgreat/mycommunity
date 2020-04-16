package com.dzkjdx.jsb.mycommunity_article.service;

import com.dzkjdx.jsb.mycommunity_article.Enum.MsgStatus;
import com.dzkjdx.jsb.mycommunity_article.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_article.config.RabbitConfig;
import com.dzkjdx.jsb.mycommunity_article.constant.RedisConst;
import com.dzkjdx.jsb.mycommunity_article.dao.ArticleMapper;
import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import com.dzkjdx.jsb.mycommunity_article.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void addArticle(Article article){
        String messageId = String.valueOf(UUID.randomUUID());
        CorrelationData id = new CorrelationData(messageId);//定义消息id
        Gson gson = new Gson();
        String articleString = gson.toJson(article);
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,String> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",articleString);
        map.put("createTime",createTime);
        map.put("exchange",RabbitConfig.articleAddExchange);
        map.put("routingKey", RabbitConfig.articleAddBindKey);
        rabbitTemplate.convertAndSend(RabbitConfig.articleAddExchange, RabbitConfig.articleAddBindKey,map,id);
        //创建并维护消息状态，状态为1
        redisTemplate.opsForHash().put(RedisConst.MsgNeedResend,
                messageId, gson.toJson(map, HashMap.class));
        redisTemplate.opsForHash().put(RedisConst.MsgId,messageId, MsgStatus.SENDED.getStatus());
    }

    public Article selectById(Integer ArticleId) {
        return articleMapper.selectDescById(ArticleId);
    }
}
