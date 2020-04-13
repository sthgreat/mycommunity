package com.dzkjdx.jsb.mycommunity_user.service;

import com.dzkjdx.jsb.mycommunity_user.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_user.constant.RedisConst;
import com.dzkjdx.jsb.mycommunity_user.dao.UserMapper;
import com.dzkjdx.jsb.mycommunity_user.form.UserLoginForm;
import com.dzkjdx.jsb.mycommunity_user.pojo.User;
import com.dzkjdx.jsb.mycommunity_user.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public ResponseVo<User> register(User user) {
        //TODO 注册时频繁查找数据库会带来压力。
        //校验 用户名，邮箱不能重复
        int countByUsername = userMapper.countByUserName(user.getUserName());
        if (countByUsername > 0) {
            return ResponseVo.error(StatusCode.USERNAME_EXIST);
        }
        int countByEmail = userMapper.countByUserEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(StatusCode.EMAIL_EXIST);
        }
        //md5加密（Spring自带）
        user.setUserPassword(DigestUtils.md5DigestAsHex(
                user.getUserPassword().getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        int resultConut = userMapper.insertNewUser(user);
        if (resultConut == 0) {
            return ResponseVo.error(StatusCode.ERROR);
        }

        return ResponseVo.success(StatusCode.REGISTER_SUCCESS.getCode(),
                StatusCode.REGISTER_SUCCESS.getDesc());
    }

    public ResponseVo<User> login(UserLoginForm userLoginForm,
                                  HttpServletResponse response,
                                  HttpSession session) {
        //TODO 登陆时频繁查找数据库可能造成压力
        //查数据库验证用户名与密码
        String userName = userLoginForm.getUserName();
        String password = DigestUtils.md5DigestAsHex(
                userLoginForm.getUserPassWord().getBytes(StandardCharsets.UTF_8));
        User user = userMapper.selectByUserName(userName);
        if (user == null) {
            return ResponseVo.error(StatusCode.USER_NOT_EXIST);
        }
        if (!user.getUserPassword().equals(password)) {
            return ResponseVo.error(StatusCode.USERNAME_PASSWORD_WRONG);
        }

        //使用redis作分布式session（登陆时先验证本地session，然后验证分布式session，都没有就返回需要登陆）
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        UUID uuid = UUID.randomUUID();
        response.addCookie(new Cookie("UID", uuid + ""));
        redisTemplate.opsForValue().set(RedisConst.USER_SESSION + uuid, userString,
                10, TimeUnit.MINUTES);

        //本地session保存
        session.setAttribute("CurrentUser", user);
        return ResponseVo.success(StatusCode.LOGIN_SUCCESS.getCode(),
                StatusCode.LOGIN_SUCCESS.getDesc());
    }

    public ResponseVo<User> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String uuid = null;
        Cookie UidCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("UID")) {
                UidCookie = cookie;
                uuid = cookie.getValue();
                UidCookie.setValue(null);
            }
        }
        if (uuid != null && uuid.length() != 0) {
            //删除redis的登录状态
            redisTemplate.opsForValue().set(RedisConst.USER_SESSION + uuid, "");
        }
        //删除session的登录状态
        request.getSession().removeAttribute("CurrentUser");

        //删除cookie中的uid
        response.addCookie(UidCookie);

        return ResponseVo.success(StatusCode.LOGOUT_SUCCESS.getCode(),
                StatusCode.LOGOUT_SUCCESS.getDesc());
    }

    public User selectByUserId(Integer userId) {
        return userMapper.selectByUserId(userId);
    }
}
