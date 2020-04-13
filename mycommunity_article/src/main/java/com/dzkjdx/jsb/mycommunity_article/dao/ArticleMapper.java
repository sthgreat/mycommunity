package com.dzkjdx.jsb.mycommunity_article.dao;

import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper {
    @Insert("insert into user_article (author_id, title, description,tag)" +
            "values (#{authorId},#{title},#{description},#{tag})")
    int addArticle(Article article);

    @Select("select id,author_id,title,like_count,read_count,comment_count from user_article " +
            "where id = #{id}")
    Article selectDescById(@Param(value = "id") Integer id);
}
