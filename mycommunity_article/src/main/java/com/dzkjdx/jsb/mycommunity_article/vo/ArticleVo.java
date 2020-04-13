package com.dzkjdx.jsb.mycommunity_article.vo;

import lombok.Data;

@Data
public class ArticleVo {
    String title;
    int likeCount;
    int readCount;
    int commentCount;
    String userName;
}
