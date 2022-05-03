package com.yangke.base;

import java.util.List;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 10:13 下午
 */
public class PageResult<T> {
    private long total;
    private List<T> data;

    public PageResult() {
    }

    public static <T> PageResult<T> ok(List<T> data, long total) {
        PageResult<T> pageResult = new PageResult();
        pageResult.setData(data);
        pageResult.setTotal(total);
        return pageResult;
    }

    public long getTotal() {
        return this.total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
