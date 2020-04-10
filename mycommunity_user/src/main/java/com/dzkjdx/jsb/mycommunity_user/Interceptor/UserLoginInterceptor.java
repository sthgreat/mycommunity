package com.dzkjdx.jsb.mycommunity_user.Interceptor;

import com.dzkjdx.jsb.mycommunity_user.constant.RedisConst;
import com.dzkjdx.jsb.mycommunity_user.exception.UserLoginException;
import com.dzkjdx.jsb.mycommunity_user.pojo.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class UserLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先从本地session中拿登录状态
        User user = (User) request.getSession().getAttribute("CurrentUser");
        String UID = "";
        if (user == null) {
            //从redis中拿登录状态
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("UID")) {
                    UID = cookie.getValue();
                    break;
                }
            }
            if (UID.equals("")) {//cookie中没有，返回需要登陆的错误
                throw new UserLoginException();
            }
            String userString = redisTemplate.opsForValue().get(RedisConst.USER_SESSION + UID);
            if (userString == null) {//redis中没有，返回需要登陆的错误
                throw new UserLoginException();
            }
            redisTemplate.opsForValue().set(RedisConst.USER_SESSION + UID, userString,
                    10, TimeUnit.MINUTES); //重设redis中的session
            //重设本地session
            Gson gson = new Gson();
            user = gson.fromJson(userString, User.class);
            request.getSession().setAttribute("CurrentUser", user);
        }
        return true;
    }
}
