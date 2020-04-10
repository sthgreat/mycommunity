package com.dzkjdx.jsb.mycommunity_user.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {
    int id;
    @NotBlank(message = "性别不能为空！")
    String userSex;
    @NotBlank(message = "号码不能为空！")
    String userPhoneNumber;
    @NotBlank(message = "昵称不能为空！")
    String userName;
    @NotBlank(message = "密码不能为空！")
    String userPassword;
    @Email(message = "不是有效邮箱！")
    String email;
}
