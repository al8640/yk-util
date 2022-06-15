package com.yangke.base.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yangke.base.exception.ServiceException;

import java.util.Objects;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 10:12 下午
 */
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> ok(T data) {
        ApiResult<T> apiResult = new ApiResult();
        apiResult.setCode(ResultCodeEnum.SUCCESS.code());
        apiResult.setMessage(ResultCodeEnum.SUCCESS.message());
        apiResult.setData(data);
        return apiResult;
    }

    public static ApiResult ok() {
        return ok((Object)null);
    }

    public static ApiResult fail(int code, String msg) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(code);
        apiResult.setMessage(msg);
        return apiResult;
    }

    public static ApiResult fail(ServiceException exception) {
        return fail(exception.getCode(), exception.getMessage());
    }

    @JsonIgnore
    public boolean isOk() {
        return Objects.equals(ResultCodeEnum.SUCCESS.code(), this.code);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiResult() {
    }
}
