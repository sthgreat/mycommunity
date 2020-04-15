package com.dzkjdx.jsb.mycommunity_article.config;

import com.dzkjdx.jsb.mycommunity_article.Enum.MsgStatus;
import com.dzkjdx.jsb.mycommunity_article.constant.RedisConst;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleSender implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            redisTemplate.opsForValue().set(
                    RedisConst.MsgId+correlationData.getId(),
                    MsgStatus.INQUEUE.getStatus());
        }else{
            // TODO 加入缓存中，使用定时任务来处理重发
            redisTemplate.opsForValue().set(
                    RedisConst.MsgId+correlationData.getId(),
                    MsgStatus.NEEDRESEND.getStatus());
        }
    }
}
