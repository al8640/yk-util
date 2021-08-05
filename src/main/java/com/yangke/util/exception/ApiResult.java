package com.yangke.util.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * @author ke.yang1
 * @description
 * @date 2021/8/4 1:49 下午
 */
public class ApiResult<T> {
    private Integer code;
    private String message;
    private T data;
    private Long total;
    private Long timestamp;

    public static <T> ApiResult<T> ok(T data) {
        ApiResult<T> apiResult = new ApiResult();
        apiResult.setCode(ResultCodeEnum.SUCCESS.code());
        apiResult.setMessage(ResultCodeEnum.SUCCESS.message());
        apiResult.setData(data);
        apiResult.setTimestamp(System.currentTimeMillis());
        return apiResult;
    }

    public static ApiResult ok() {
        return ok((Object)null);
    }

    public static ApiResult fail(int code, String msg) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(code);
        apiResult.setMessage(msg);
        apiResult.setTimestamp(System.currentTimeMillis());
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public ApiResult() {
    }
}
