package com.yangke.base.exception;

import com.yangke.base.response.ApiResult;
import com.yangke.base.response.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 10:15 下午
 */
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ApiResult errorHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ApiResult.fail(ResultCodeEnum.SYSTEM_ERROR.code(), ResultCodeEnum.SYSTEM_ERROR.message());
    }

    @ResponseBody
    @ExceptionHandler({ServiceException.class})
    public ApiResult baseErrorHandler(ServiceException ex) {
        return ApiResult.fail(ex);
    }

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResult illegalArgumentErrorHandler(IllegalArgumentException ex) {
        return ApiResult.fail(ResultCodeEnum.ILLEGAL_ARGUMENT.code(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({BindException.class})
    public ApiResult bindExceptionErrorHandler(BindException ex) {
        return ApiResult.fail(ResultCodeEnum.ILLEGAL_ARGUMENT.code(), ex.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResult methodArgumentNotValidExceptionErrorHandler(MethodArgumentNotValidException ex) {
        return ApiResult.fail(ResultCodeEnum.ILLEGAL_ARGUMENT.code(), ex.getBindingResult().getFieldError().getDefaultMessage());
    }
}
