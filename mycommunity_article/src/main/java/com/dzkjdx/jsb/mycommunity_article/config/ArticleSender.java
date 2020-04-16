package com.dzkjdx.jsb.mycommunity_article.config;

import com.dzkjdx.jsb.mycommunity_article.Enum.MsgStatus;
import com.dzkjdx.jsb.mycommunity_article.constant.RedisConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ArticleSender implements RabbitTemplate.ConfirmCallback {
    private static final Logger log = LoggerFactory.getLogger(ArticleSender.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //TODO 这里在测试的时候没有进入该方法，另，需要定时任务处理重发和重新消费
        if(ack){
            log.info("消息:{}已发至队列",correlationData.getId());
            redisTemplate.opsForHash().put(RedisConst.MsgId,
                    Objects.requireNonNull(correlationData.getId()),
                    MsgStatus.INQUEUE.getStatus());
            //删除待发送缓存中的消息
            redisTemplate.opsForHash().delete(RedisConst.MsgNeedResend,
                    Objects.requireNonNull(correlationData.getId()));

        }else{
            // TODO 标记未发送消息，加入缓存中，使用定时任务来处理重发
            log.info("消息:{}未发至队列，定时重发",correlationData.getId());
            redisTemplate.opsForHash().put(RedisConst.MsgId,
                    Objects.requireNonNull(correlationData.getId()),
                    MsgStatus.NEEDRESEND.getStatus());
        }
    }
}
