package model;

import common.CommonResultCode;
import constant.ResultCode;
import lombok.Data;

/**
 * @ClassName ServiceException
 * @Description TODO
 * @Author zhangxiaoxiong
 * @Date 2018/11/30 9:21
 * @Version 1.0
 **/

/**
 * 服务异常类
 */

@Data
public class ServiceException extends RuntimeException{

    /**
     * 错误编码
     */
    private int code = CommonResultCode.系统繁忙.getCode();

//    private String message = CommonResultCode.系统繁忙.getMessage();

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(ResultCode code) {
        super(code.getMessage());
//        this.message=code.getMessage();
        this.code = code.getCode();
    }

    public ServiceException(ResultCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.code = code.getCode();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
