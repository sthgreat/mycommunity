package com.dzkjdx.jsb.mycommunity_user.dao;

import com.dzkjdx.jsb.mycommunity_user.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    @Insert("insert into m_user (user_sex,user_phone_number,user_name,user_password,email) " +
            "values(#{userSex},#{userPhoneNumber},#{userName},#{userPassword},#{email})")
    int insertNewUser(User user);

    @Select("select count(1) from m_user where user_name = #{name}")
    int countByUserName(@Param(value = "name") String userName);

    @Select("select count(*) from m_user where email = #{email}")
    int countByUserEmail(@Param(value = "email") String email);

    @Select("select * from m_user where user_name = #{name}")
    User selectByUserName(@Param(value = "name") String name);

    @Select("select * from m_user where id = #{id}")
    User selectByUserId(@Param(value = "id") Integer id);
}
