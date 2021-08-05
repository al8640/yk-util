package com.yangke.util.response;

/**
 * @Description :
 * @Author : yangke
 * @Date : 2021年08月04日
 */
public class BaseResultDomain {

    private Integer code;

    private String message;

    private Long timestamp;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public BaseResultDomain() {

    }

    public BaseResultDomain(Integer code, String msg) {
        this.code = code;
        this.message = msg;
        this.timestamp = getTimestamp();
    }

    @Override
    public String toString() {
        return "BaseResultDomain{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
