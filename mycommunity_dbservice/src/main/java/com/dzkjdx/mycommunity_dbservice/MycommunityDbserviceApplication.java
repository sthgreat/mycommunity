package com.dzkjdx.mycommunity_dbservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.dzkjdx.mycommunity_dbservice.dao")
public class MycommunityDbserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycommunityDbserviceApplication.class, args);
    }

}
