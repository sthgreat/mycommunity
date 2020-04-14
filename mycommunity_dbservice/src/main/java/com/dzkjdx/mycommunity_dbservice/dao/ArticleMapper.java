package com.dzkjdx.mycommunity_dbservice.dao;

import com.dzkjdx.mycommunity_dbservice.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper {
    @Insert("insert into user_article (author_id, title, description,tag)" +
            "values (#{authorId},#{title},#{description},#{tag})")
    int addArticle(Article article);
}
