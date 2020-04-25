package com.dzkjdx.jsb.mycommunity_article.foreign;

import com.alibaba.nacos.api.naming.pojo.AbstractHealthChecker;
import com.dzkjdx.jsb.mycommunity_article.pojo.User;
import com.dzkjdx.jsb.mycommunity_article.vo.ResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "userApplication")
public interface UserApp {
    @RequestMapping(value = "/user/getUser", method = RequestMethod.GET)
    ResponseVo<User> getUser(@RequestParam(value = "UserId") Integer userId);
}
