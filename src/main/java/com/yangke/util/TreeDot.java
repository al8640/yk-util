package com.yangke.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/***
 * 树点
 * 需要实现树的类可以继承该类，手写set方法，在设定本身属性值时同时设置该类中的相关属性
 * @ClassName TreeTreeDot
 * @Author xiaowd
 * @DateTime 2020/2/23 15:58
 */
@Data
public class TreeDot<T> {

    /**
     * 点ID
     */
    private Long id;
    /**
     * 点名称
     */
    private String label;
    /**
     * 父点ID
     */
    private Long parentId;

    /**
     * 节点类型
     */
    private Integer type;

    /**
     * url地址
     */
    private String url;

    /**
     * 该点的子树集合
     */
    private List<TreeDot<T>> children = new ArrayList<>();

    /**
     * 标识code 保留字段
     * private String code;
     */
    /**
     * 该点是否展开，默认不展开 保留字段
     * private Boolean spread = false;
     */
    /**
     * 该点是否选中，默认不选中 保留字段
     * private Boolean checked = false;
     */
    /**
     * 该点的图标，默认不设置 保留字段
     * private String icon;
     */
    /**
     * 该点的其他属性 保留字段
     * private Map<String,Object> attributes = new HashMap<>();
     */

}
