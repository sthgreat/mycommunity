package com.dzkjdx.jsb.mycommunity_article.Interceptor;

import com.dzkjdx.jsb.mycommunity_article.constant.RedisConst;
import com.dzkjdx.jsb.mycommunity_article.exception.UserLoginException;
import com.dzkjdx.jsb.mycommunity_article.pojo.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先从本地session以及redisSession中拿登录状态
        String userString = (String) request.getSession().getAttribute("CurrentUser");
        Gson gson = new Gson();
        if (userString == null) {
            throw new UserLoginException();
        }else{
            request.getSession().setAttribute("CurrentUser", userString);
        }
        return true;
    }
}
