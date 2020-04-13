package com.dzkjdx.jsb.mycommunity_article.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class User {
    int id;
    String userSex;
    String email;
    String userPhoneNumber;
    String userName;
    String userPassword;
    BigDecimal userMoney;
    Integer userTotalArticle;
    Integer userTotalPraise;
    Timestamp createTime;
    Timestamp updateTime;
}