package com.dzkjdx.jsb.mycommunity_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan(basePackages = "com.dzkjdx.jsb.mycommunity_user.dao")
@EnableDiscoveryClient
@EnableRedisHttpSession
public class MycommunityUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycommunityUserApplication.class, args);
    }

}
