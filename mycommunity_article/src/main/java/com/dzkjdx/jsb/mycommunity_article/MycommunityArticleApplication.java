package com.dzkjdx.jsb.mycommunity_article;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "com.dzkjdx.jsb.mycommunity_article.dao")
@EnableDiscoveryClient
@EnableFeignClients
@EnableRedisHttpSession
public class MycommunityArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycommunityArticleApplication.class, args);
    }

    private static void initFlowRules(){
        List<FlowRule> rules=new ArrayList<>(); //限流规则的集合
        FlowRule flowRule=new FlowRule();
        flowRule.setResource("sayHello");//资源(方法名称、接口）
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS); //限流的阈值的类型
        flowRule.setCount(2);
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
    }
}
