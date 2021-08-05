package com.yangke.util.exception;

/**
 * @author ke.yang1
 * @description
 * @date 2021/8/4 1:51 下午
 */
public class ServiceException extends RuntimeException{
    private int code = 0;

    public ServiceException() {
    }

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
        this.code = ResultCodeEnum.ILLEGAL_ARGUMENT.code();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(ResultCodeEnum error) {
        super(error.message());
        this.code = error.code();
    }

    public int getCode() {
        return this.code;
    }
}
