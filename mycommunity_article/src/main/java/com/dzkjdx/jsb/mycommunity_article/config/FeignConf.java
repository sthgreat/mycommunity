package com.dzkjdx.jsb.mycommunity_article.config;

import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FeignConf {
    ILoadBalancer balancer = new BaseLoadBalancer();

    @Bean
    public IRule feignRule() {
//        List<Server> allServers = balancer.getAllServers();
        return new BestAvailableRule();
    }
}
