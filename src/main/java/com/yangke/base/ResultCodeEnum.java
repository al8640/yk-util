package com.yangke.base;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 10:14 下午
 */
public enum ResultCodeEnum {
    SUCCESS(1, "SUCCESS"),
    SYSTEM_ERROR(10001, "系统繁忙"),
    ILLEGAL_ARGUMENT(20001, "参数非法"),
    NO_LOGIN(20002, "未登录"),
    THIRD_PARTY_ERROR(20003, "第三方服务异常"),
    REQUEST_LIMIT(20004, "系统繁忙,请稍后再试"),
    AUTHORIZATION_NOT_FOUND(20005, "未获得授权");

    private int code;
    private String message;

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    private ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
