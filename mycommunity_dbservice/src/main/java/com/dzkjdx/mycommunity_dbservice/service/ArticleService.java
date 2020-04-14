package com.dzkjdx.mycommunity_dbservice.service;

import com.dzkjdx.mycommunity_dbservice.Enum.StatusCode;
import com.dzkjdx.mycommunity_dbservice.dao.ArticleMapper;
import com.dzkjdx.mycommunity_dbservice.pojo.Article;
import com.dzkjdx.mycommunity_dbservice.vo.ResponseVo;
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
