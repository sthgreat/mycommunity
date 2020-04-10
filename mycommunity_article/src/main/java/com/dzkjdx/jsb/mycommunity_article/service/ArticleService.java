package com.dzkjdx.jsb.mycommunity_article.service;

import com.dzkjdx.jsb.mycommunity_article.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_article.dao.ArticleMapper;
import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import com.dzkjdx.jsb.mycommunity_article.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public ResponseVo<Article> addArticle(Article article){
        int i = articleMapper.addArticle(article);
        if(i<=0){
            return ResponseVo.error(StatusCode.ARTICLE_ADD_FAIL);
        }
        return ResponseVo.success(StatusCode.ARTICLE_ADD_SUCCESS);
    }
}
