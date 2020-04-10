package com.dzkjdx.jsb.mycommunity_article.dao;

import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper {
    @Insert("insert into user_article (author_id, title, description,tag)" +
            "values (#{authorId},#{title},#{description},#{tag})")
    int addArticle(Article article);
}
