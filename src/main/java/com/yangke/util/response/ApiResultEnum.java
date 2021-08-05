package com.yangke.util.response;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @Description :
 * @Author : yangke
 * @Date : 2021年08月04日
 */
public enum ApiResultEnum {
    /**
     *
     */
    WARN(2, "警告"),
    /**
     *
     */
    SUCCESS(1, "操作成功"),
    /**
     *
     */
    FAILED(0, "操作失败"),
    /**
     *
     */
    ERROR(-1, "操作异常"),
    /**
     *
     */
    INVALID(-2, "参数错误")
    ;

    int code;
    String message;

    ApiResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static <T> T result(BaseResultDomain resultDomain, ApiResultEnum resultEnum) {
        resultDomain.setCode(resultEnum.code());
        resultDomain.setMessage(resultEnum.message());
        return (T) resultDomain;
    }

    public static <T> T resultError(BaseResultDomain resultDomain) {
        resultDomain.setCode(ApiResultEnum.ERROR.code());
        resultDomain.setMessage(ApiResultEnum.ERROR.message());
        return (T) resultDomain;
    }

    public static <T> T resultError(BaseResultDomain resultDomain, Throwable e) {
        resultDomain.setCode(ApiResultEnum.ERROR.code());
        if (e instanceof UndeclaredThrowableException) {
            resultDomain.setMessage(e.getMessage());
            e = ((UndeclaredThrowableException) e).getUndeclaredThrowable();
            if (e != null) {
                resultDomain.setMessage(causeMessage(e));
            }
        } else {
            resultDomain.setMessage(causeMessage(e));
        }
        return (T) resultDomain;
    }

    public static <T> T resultFailed(BaseResultDomain resultDomain) {
        resultDomain.setCode(ApiResultEnum.FAILED.code());
        resultDomain.setMessage(ApiResultEnum.FAILED.message());
        return (T) resultDomain;
    }

    public static <T> T resultSuccess(BaseResultDomain resultDomain) {
        resultDomain.setCode(ApiResultEnum.SUCCESS.code());
        resultDomain.setMessage(ApiResultEnum.SUCCESS.message());
        return (T) resultDomain;
    }

    public static <T> T resultSuccess() {
        BaseResultDomain resultDomain = new BaseResultDomain();
        resultDomain.setCode(ApiResultEnum.SUCCESS.code());
        resultDomain.setMessage(ApiResultEnum.SUCCESS.message());
        return (T) resultDomain;
    }

    public static <T> T resultInvalid(BaseResultDomain resultDomain) {
        resultDomain.setCode(ApiResultEnum.INVALID.code());
        resultDomain.setMessage(ApiResultEnum.INVALID.message());
        return (T) resultDomain;
    }

    public static <T> T resultInvalid(BaseResultDomain resultDomain, String message) {
        resultDomain.setCode(ApiResultEnum.INVALID.code());
        resultDomain.setMessage(message);
        return (T) resultDomain;
    }

    public static <T> T resultWarn(BaseResultDomain resultDomain) {
        resultDomain.setCode(ApiResultEnum.WARN.code());
        resultDomain.setMessage(ApiResultEnum.WARN.message());
        return (T) resultDomain;
    }

    private static String causeMessage(Throwable e) {
        String msg = e.getMessage();
        e = e.getCause();
        if (e == null) {
            return msg;
        }
        return causeMessage(e);
    }

    public static <T> T resultFailed(BaseResultDomain resultDomain, String message) {
        resultDomain.setCode(ApiResultEnum.FAILED.code());
        resultDomain.setMessage(ApiResultEnum.FAILED.message() + "（" + message + "）");
        return (T) resultDomain;
    }

    public static <T> T resultByFlag(boolean flag, BaseResultDomain baseResultDomain) {
        if (flag) {
            ApiResultEnum.resultSuccess(baseResultDomain);
        } else {
            ApiResultEnum.resultFailed(baseResultDomain);
        }
        return (T) baseResultDomain;
    }

    public static <T> T result(BaseResultDomain resultDomain) {
        resultDomain.setCode(resultDomain.getCode());
        resultDomain.setMessage(resultDomain.getMessage());
        return (T) resultDomain;
    }
}
