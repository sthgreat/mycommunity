package com.dzkjdx.mycommunity_dbservice.receiver;

import com.dzkjdx.mycommunity_dbservice.Enum.MsgStatus;
import com.dzkjdx.mycommunity_dbservice.Enum.StatusCode;
import com.dzkjdx.mycommunity_dbservice.constant.RedisConst;
import com.dzkjdx.mycommunity_dbservice.dao.ArticleMapper;
import com.dzkjdx.mycommunity_dbservice.pojo.Article;
import com.dzkjdx.mycommunity_dbservice.service.ArticleService;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RabbitListener(queues = "addArticle")
public class addArticleReceiver {
    private static final Logger log = LoggerFactory.getLogger(addArticleReceiver.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitHandler
    @Transactional
    public void addArticle(Map<String,String> msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        boolean flag = true;
        Gson gson = new Gson();
        log.info("消息："+msg.toString());
        if(channel!=null){
            log.info("传入了channel");
        }
        try {
            //检查消息是否已经被消费
            log.info("进入try");
            String status = (String)redisTemplate.opsForHash().get(RedisConst.MsgId, msg.get("messageId"));
            log.info("消息状态是："+status + "消息id是："+msg.get("messageId"));
            assert status != null;
            if(!status.equals("4")){
                log.info("消息未被消费");
                String messageData = msg.get("messageData");
                Article article = gson.fromJson(messageData, Article.class);
                articleService.addArticle(article);
                //修改消息状态为成功消费
                redisTemplate.opsForHash().put(RedisConst.MsgId,
                        msg.get("messageId"), MsgStatus.SUCCESS.getStatus());
                //删除redis待消费消息
                redisTemplate.opsForHash().delete(RedisConst.MsgNeedReconsume,
                        msg.get("messageId"));
            }
        }catch (Exception e){
            flag = false;
            // 修改消息状态为未成功消费
            log.info("出错，消息未成功消费");
            redisTemplate.opsForHash().put(RedisConst.MsgId,
                    msg.get("messageId"), MsgStatus.NEEDRECONSUME.getStatus());
            //将消息加入redis待消费消息中
            redisTemplate.opsForHash().put(RedisConst.MsgNeedReconsume,
                    msg.get("messageId"), gson.toJson(msg, HashMap.class));
        }finally {
            if(flag){
                channel.basicAck(tag,false);
                log.info("成功消费消息");
            }else{
                channel.basicReject(tag, false);
                log.info("消息消费失败");
            }
        }
    }
}
