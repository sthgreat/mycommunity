package com.dzkjdx.jsb.mycommunity_article;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = "com.dzkjdx.jsb.mycommunity_article.dao")
@EnableDiscoveryClient
@EnableFeignClients
public class MycommunityArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycommunityArticleApplication.class, args);
    }

}
