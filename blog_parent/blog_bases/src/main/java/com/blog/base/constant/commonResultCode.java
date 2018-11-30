package com.blog.base.constant;

import constant.ResultCode;

/**
 * @ClassName commResultCode
 * @Description TODO
 * @Author zhangxiaoxiong
 * @Date 2018/11/27 9:09
 * @Version 1.0
 **/
public enum  commonResultCode implements ResultCode {

    请求成功(0, "请求成功"),

    系统繁忙(500001, "系统繁忙,请稍后再试"),
    系统错误(500002, "系统错误");

    private int code;
    private String message;

    commonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
