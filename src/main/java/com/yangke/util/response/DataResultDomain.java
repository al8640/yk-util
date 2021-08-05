package com.yangke.util.response;

/**
 * @Description :
 * @Author : yangke
 * @Date : 2021年08月04日
 */
public class DataResultDomain<T> extends BaseResultDomain {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "DataResultDomain{data=" + data + "}";
    }
}
