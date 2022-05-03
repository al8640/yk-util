package com.yangke.util.config;

import com.yangke.base.ApiResult;
import com.yangke.base.GlobalExceptionHandler;
import com.yangke.base.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author songsheng.yu
 * @date 2020/7/29
 */
@ControllerAdvice
public class BusinessExceptionHandler extends GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    @Override
    public ApiResult baseErrorHandler(ServiceException ex) {
        return ApiResult.fail(ex);
    }
}
