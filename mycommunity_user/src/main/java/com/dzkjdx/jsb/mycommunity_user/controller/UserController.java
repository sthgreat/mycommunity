package com.dzkjdx.jsb.mycommunity_user.controller;

import com.dzkjdx.jsb.mycommunity_user.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_user.form.UserLoginForm;
import com.dzkjdx.jsb.mycommunity_user.form.UserRegisterForm;
import com.dzkjdx.jsb.mycommunity_user.pojo.User;
import com.dzkjdx.jsb.mycommunity_user.service.UserService;
import com.dzkjdx.jsb.mycommunity_user.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseVo<User> register(@RequestBody @Valid UserRegisterForm userRegisterForm) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseVo<User> login(@RequestBody @Valid UserLoginForm userLoginForm,
                                  HttpServletResponse response, HttpServletRequest request) {
        return userService.login(userLoginForm, response, request.getSession());
    }

    @GetMapping("/logout")
    public ResponseVo<User> logout(HttpServletRequest request, HttpServletResponse response) {
        return userService.logout(request, response);
    }

    @GetMapping("/getUser")
    public ResponseVo<User> getUser(@RequestParam(value = "UserId") Integer userId){
        User user = userService.selectByUserId(userId);
        return new ResponseVo<User>(StatusCode.LINK_SUCCESS.getCode(),
                StatusCode.LINK_SUCCESS.getDesc(),
                user);
    }
}