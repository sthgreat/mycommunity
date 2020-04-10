package com.dzkjdx.jsb.mycommunity_user.exception;

import com.dzkjdx.jsb.mycommunity_user.Enum.StatusCode;
import com.dzkjdx.jsb.mycommunity_user.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo UserLoginHandle() {
        return ResponseVo.error(StatusCode.NEED_LOGIN);
    }
}
