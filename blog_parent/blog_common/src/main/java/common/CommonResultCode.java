package common;

import constant.ResultCode;

/**
 * @ClassName CommonResultCode
 * @Description TODO
 * @Author zhangxiaoxiong
 * @Date 2018/11/29 18:07
 * @Version 1.0
 **/
//全局系统错误码
public enum CommonResultCode implements ResultCode{

    成功(0,"成功"),
    /**
     * 服务端-系统错误: 500000-500999
     */
    系统繁忙(500001, "系统繁忙,请稍后再试"),
    系统错误(500002, "系统错误"),
    服务提供方不存在(500003, "服务提供方不存在"),
    参数有误(500004, "参数有误");

    private int code;
    private String message;

    CommonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}