package com.yangke.util.response;

import java.util.List;

/**
 * @Description :
 * @Author : yangke
 * @Date : 2021年08月04日
 */
public class PageResultDomain<T> extends BaseResultDomain {

    private int total;

    private List<T> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
