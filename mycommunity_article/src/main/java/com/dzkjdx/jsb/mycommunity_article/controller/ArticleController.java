package com.dzkjdx.jsb.mycommunity_article.controller;

import com.dzkjdx.jsb.mycommunity_article.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_article.foreign.UserApp;
import com.dzkjdx.jsb.mycommunity_article.pojo.Article;
import com.dzkjdx.jsb.mycommunity_article.pojo.User;
import com.dzkjdx.jsb.mycommunity_article.service.ArticleService;
import com.dzkjdx.jsb.mycommunity_article.vo.ArticleVo;
import com.dzkjdx.jsb.mycommunity_article.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = {"http://localhost:8081","null"})
/**
 * 假设有一个需求，需要在返回article详细信息的同时，返回作者的名字等详细信息，
 * 需要调用其他的接口，在这里实践微服务（分布式）
 */
public class ArticleController {
    Gson gson = new Gson();

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserApp userApp;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/add")
    public ResponseVo<Article> addNewArticle(@RequestBody Article article,
                                             HttpServletRequest request){
        //传入消息队列中，经由消息队列放入数据库，需要一个服务专门处理数据库有关操作
        String currentUser = (String) request.getSession().getAttribute("CurrentUser");
        User user = gson.fromJson(currentUser, User.class);
        article.setAuthorId(user.getId());
        articleService.addArticle(article);
        return ResponseVo.success(StatusCode.SUCCESS);
    }

    @GetMapping("/get")
    public ResponseVo<ArticleVo> getArticleDetail(@RequestParam(value = "ArticleId") Integer ArticleId){
        Article article = articleService.selectById(ArticleId);
        if(article == null){
            return ResponseVo.error(StatusCode.ARTICLE_NOT_FOUND);
        }
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        User user = userApp.getUser(article.getAuthorId()).getData();
        System.out.println(user.toString());
        BeanUtils.copyProperties(user, articleVo);
        return new ResponseVo<>(StatusCode.SUCCESS.getCode(),
                StatusCode.SUCCESS.getDesc(),
                articleVo);
    }

    @DeleteMapping("/delete")
    public ResponseVo deleteArticle(@RequestParam(value = "ArticleId") Integer ArticleId,
                                    HttpServletRequest request){
        Article article = articleService.selectById(ArticleId);
        if(article == null){
            return ResponseVo.error(StatusCode.ARTICLE_NOT_FOUND);
        }
        String currentUser = (String) request.getSession().getAttribute("CurrentUser");
        User user = gson.fromJson(currentUser, User.class);
        if(article.getAuthorId() != user.getId()){ //文章的作者与登陆人不符合
            return ResponseVo.error(StatusCode.ARTICLE_DELETE_FAIL);
        }
        if(articleService.deleteById(ArticleId) > 0){ //删除成功
            return ResponseVo.success(StatusCode.ARTICLE_DELETE_SUCCESS);
        }
        return ResponseVo.error(StatusCode.ARTICLE_DELETE_FAIL);
    }
}
