package com.dzkjdx.jsb.mycommunity_user.vo;

import com.dzkjdx.jsb.mycommunity_user.Enum.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    Integer statusCode;
    String msg;
    T Data;

    public ResponseVo(Integer statusCode, String msg, T data) {
        this.statusCode = statusCode;
        this.msg = msg;
        Data = data;
    }

    public ResponseVo(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public static <T> ResponseVo<T> success(Integer code, String msg) {
        return new ResponseVo<T>(code, msg);
    }

    public static <T> ResponseVo<T> error(StatusCode statusCode) {
        return new ResponseVo<>(statusCode.getCode(), statusCode.getDesc());
    }
}
