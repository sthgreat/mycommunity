package com.dzkjdx.mycommunity_dbservice.Enum;

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

    //2000文章有关
    ARTICLE_ADD_FAIL(2001, "添加文章失败"),

    ARTICLE_ADD_SUCCESS(2002, "添加文章成功"),

    ARTICLE_NOT_FOUND(2003, "查找文章失败"),


    //5000为服务器本身有关
    ERROR(5001, "服务出错"),

    SUCCESS(5002,"成功");

    Integer code;

    String desc;

    StatusCode(Integer statusCode, String description) {
        this.code = statusCode;
        this.desc = description;
    }
}
