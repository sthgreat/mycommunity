package com.dzkjdx.mycommunity_dbservice.SchedulingMisson;

import com.dzkjdx.mycommunity_dbservice.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableScheduling
public class MsgMisson {
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Scheduled(fixedDelay = 3000000)
    public void reConsumeMsg(){

    }
}
