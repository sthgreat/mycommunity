package com.dzkjdx.jsb.mycommunity_article.SchedulingMisson;

import com.dzkjdx.jsb.mycommunity_article.constant.RedisConst;
import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import com.dzkjdx.jsb.mycommunity_article.service.ArticleService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableScheduling
public class MsgMisson {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ArticleService articleService;

    @Scheduled(fixedDelay = 3000000) //每隔5分钟执行一次定时任务
    public void reSendMsg(){
        Gson gson = new Gson();
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(RedisConst.MsgNeedResend);
        for(Object msg : entries.values()){
            String msgString = (String) msg;
            HashMap<String,String> map = gson.fromJson(msgString, HashMap.class);
            String messageId = map.get("messageId");
            String messageData = map.get("messageData");
            Article article = gson.fromJson(messageData, Article.class);
            articleService.addArticle(article);
            //同时删除缓存中需重新发送消息存储
            redisTemplate.opsForHash().delete(RedisConst.MsgNeedResend, messageId);
            //同时删除缓存中相应的消息状态
            redisTemplate.opsForHash().delete(RedisConst.MsgId, messageId);
        }
    }
}
