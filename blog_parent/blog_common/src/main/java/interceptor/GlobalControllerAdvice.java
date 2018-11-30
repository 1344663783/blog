package interceptor;

import com.alibaba.fastjson.JSON;
import common.CommonResultCode;
import model.Result;
import model.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utils.CurrentHttpServletRequestHelper;
import utils.RequestUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.Set;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@RestController
public class GlobalControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    /**
     * 是否保存error log
     */
    @Value("${autosaas.saveErrorLog:false}")
    private Boolean saveErrorLog = false;

    /**
     * 框架异常的处理
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMessageNotReadableException.class})
    public Result<Object> handleFrameworkException(HttpServletResponse response, Exception e) {

        String msg = MessageFormat.format("fromClientIp: \"{2}\" ,message: {0}, request: {1}"
                , e.getMessage()
                , CurrentHttpServletRequestHelper.dumpRequest()
                , getClientIp()
        );

        logger.warn(msg, e);

        return new Result<>(CommonResultCode.系统繁忙);
    }

    /**
     * 验证异常的处理(处理Controller上@Validated，)
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result<Object> handleConstraintViolationException(HttpServletResponse response, ConstraintViolationException e) {

        String defaultMessage = "";
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        for (ConstraintViolation cv : set) {
            defaultMessage = cv.getMessage();
            break;
        }

        String msg = MessageFormat.format("fromClientIp: \"{2}\" ,message: {0}, request: {1}"
                , e.getMessage()
                , CurrentHttpServletRequestHelper.dumpRequest()
                , getClientIp()
        );

        logger.warn(msg, e);

        Result result = convert(defaultMessage);
        if (result == null) {
            result = new Result<>(CommonResultCode.参数有误.getCode(), defaultMessage);
        }
        return result;
    }


    /**
     * 验证异常的处理(处理Controller方法里的@Valid model)
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(HttpServletResponse response, MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        logger.warn(MessageFormat.format("message: {0}, request: {1}", e.getMessage(), CurrentHttpServletRequestHelper.dumpRequest()), e);

        String defaultMessage = bindingResult.getFieldError().getDefaultMessage();
        Result result = convert(defaultMessage);
        if (result == null) {
            result = new Result<>(CommonResultCode.参数有误.getCode(), defaultMessage);
        }
        return result;
    }


    /**
     * 业务异常的处理
     */
    @ExceptionHandler(value = ServiceException.class)
    public Result<Object> handleServiceException(HttpServletResponse response, ServiceException e) {

        String msg = MessageFormat.format("fromClientIp: \"{2}\" ,message: {0}, request: {1}"
                , e.getMessage()
                , CurrentHttpServletRequestHelper.dumpRequest()
                , getClientIp()
        );

        logger.warn(msg, e);

        return new Result<>(e.getCode(),e.getMessage());
    }

    /**
     * 其他异常统一处理
     */
    @ExceptionHandler(value = Exception.class)
    public Result<Object> handleException(HttpServletResponse response, Exception e) {

        String msg = MessageFormat.format("fromClientIp: \"{2}\" ,message: {0}, request: {1}"
                , e.getMessage()
                , CurrentHttpServletRequestHelper.dumpRequest()
                , getClientIp()
        );
        logger.error(msg, e);

        if (saveErrorLog) {
            errorLogger.error(msg, e);
        }

        return new Result<>(CommonResultCode.系统错误);

    }

    private static Result convert(String strJson) {
        if (strJson == null || strJson.isEmpty()) {
            return null;
        }
        Result result = null;
        try {
            result = JSON.parseObject(strJson, Result.class);
        } catch (Exception e) {
            return null;
        }

        return result;


    }

    private static String getClientIp() {
        try {
            return RequestUtil.getClientIp();
        } catch (Exception ex) {
            return "";
        }
    }

}
