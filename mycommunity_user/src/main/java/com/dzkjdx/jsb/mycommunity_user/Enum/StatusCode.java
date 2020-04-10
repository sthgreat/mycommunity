package com.dzkjdx.jsb.mycommunity_user.Enum;

import lombok.Getter;

@Getter
public enum StatusCode {

    //1000为用户有关
    REGISTER_SUCCESS(1001, "用户注册成功"),

    USERNAME_EXIST(1002, "用户名已存在"),

    EMAIL_EXIST(1003, "邮箱已存在"),

    USERNAME_PASSWORD_WRONG(1004, "用户名或密码错误"),

    LOGIN_SUCCESS(1005, "登陆成功"),

    USER_NOT_EXIST(1006, "用户不存在"),

    LOGOUT_SUCCESS(1007, "登出成功"),

    NEED_LOGIN(1008, "未登录，需要登陆"),

    //5000为服务器本身有关
    ERROR(5001, "服务出错");

    Integer code;

    String desc;

    StatusCode(Integer statusCode, String description) {
        this.code = statusCode;
        this.desc = description;
    }
}
