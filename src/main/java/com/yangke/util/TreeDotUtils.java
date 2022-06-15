package com.yangke.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * 操作“树”的工具
 * @ClassName TreeDotUtils
 * @Author xiaowd
 * @DateTime 2020/4/22 9:14
 */
public class TreeDotUtils {

    /**
     * 将List转换为Tree
     * @MethosName convertListToTreeDot
     * @param tList
     * @return java.util.List<cn.eshore.common.entity.Tree<T>>
     */
    public static <T extends TreeDot> List<TreeDot<T>> convertListToTreeDot(List<T> tList){
        List<TreeDot<T>> treeDotList = new ArrayList<>();
        if(tList != null && tList.size() > 0){
            for(T t:tList){
                if(!isTreeDotExist(tList,t.getParentId())){
                    //不存在以父ID为ID的点，说明是当前点是顶级节点
                    TreeDot<T> tTreeDot = getTreeDotByT(t, tList);
                    treeDotList.add(tTreeDot);
                }
            }
        }
        return treeDotList;
    }

    /**
     * 根据ID判断该点是否存在
     * @MethosName isTreeDotExist
     * @param tList
     * @param id 点ID
     * @return java.lang.Boolean
     */
    private static <T extends TreeDot> Boolean isTreeDotExist(List<T> tList, Long id) {
        for(T t:tList){
            if(t.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定父点的子树
     * @MethosName getChildTreeList
     * @param parentTreeDot 父点
     * @param tList
     * @return java.util.List<cn.eshore.common.entity.Tree<T>>
     */
    private static <T extends TreeDot> List<TreeDot<T>> getChildTreeDotList(TreeDot<T> parentTreeDot, List<T> tList){
        List<TreeDot<T>> childTreeDotList = new ArrayList<>();
        for(T t:tList){
            if(parentTreeDot.getId().equals(t.getParentId())){
                //如果父ID是传递树点的ID，那么就是传递树点的子点
                TreeDot<T> tTreeDot = getTreeDotByT(t,tList);
                childTreeDotList.add(tTreeDot);
            }
        }
        return childTreeDotList;
    }

    /**
     * 根据实体获取TreeDot
     * tTreeDot.setCode(t.getCode()); 保留
     * tTreeDot.setIcon(t.getIcon()); 保留
     * tTreeDot.setAttributes(t.getAttributes()); 保留
     * @MethosName getTreeDotByT
     * @param t
     * @param tList
     * @return pri.xiaowd.layui.pojo.TreeDot<T>
     */
    private static <T extends TreeDot> TreeDot<T> getTreeDotByT(T t,List<T> tList){
        TreeDot<T> tTreeDot = new TreeDot<>();
        tTreeDot.setId(t.getId());
        tTreeDot.setParentId(t.getParentId());
        tTreeDot.setLabel(t.getLabel());
        tTreeDot.setType(t.getType());
        tTreeDot.setUrl(t.getUrl());
        tTreeDot.setChildren(getChildTreeDotList(tTreeDot,tList));
        return tTreeDot;
    }

    /**
     * 获取根据指定ID所在点为父点的树
     * @MethosName getTreeDotById
     * @param id
     * @param treeDotList
     * @return cn.eshore.common.entity.TreeDot<T>
     */
    public static <T extends TreeDot> TreeDot<T> getTreeDotById(Long id,List<TreeDot<T>> treeDotList){
        if(id != null && !"".equals(id) && treeDotList != null && treeDotList.size() > 0){
            for(TreeDot<T> treeDot:treeDotList){
                if(id.equals(treeDot.getId())){
                    return treeDot;
                }
                if(treeDot.getChildren() != null && treeDot.getChildren().size() > 0){
                    TreeDot<T> td = getTreeDotById(id, treeDot.getChildren());
                    if(td != null){
                        return td;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 将TreeList的所有点转换为ID的Set集合
     * @MethosName convertTreeDotToIdSet
     * @param treeDotList
     * @param kClass ID的类型
     * @return java.util.Set<K>
     */
    public static <T extends TreeDot,K> Set<K> convertTreeDotToIdSet(List<TreeDot<T>> treeDotList,Class<K> kClass){
        Set<K> idSet = new HashSet<>();
        if(treeDotList != null && treeDotList.size() > 0){
            for(TreeDot<T> treeDot:treeDotList){
                idSet.add((K)treeDot.getId());
                if(treeDot.getChildren() != null && treeDot.getChildren().size() > 0){
                    idSet.addAll(convertTreeDotToIdSet(treeDot.getChildren(),kClass));
                }
            }
        }
        return idSet;
    }

    /**
     * 将Tree(单点)的所有点转换为ID的Set集合
     * @MethosName convertTreeDotToIdSet
     * @Author xiaowd
     * @Date 2020/4/29 9:08
     * @param treeDot
     * @param kClass
     * @return java.util.Set<K>
     */
    public static <T extends TreeDot,K> Set<K> convertTreeDotToIdSet(TreeDot<T> treeDot,Class<K> kClass){
        Set<K> idSet = new HashSet<>();
        if(treeDot != null){
            idSet.add((K)treeDot.getId());
            if(treeDot.getChildren() != null && treeDot.getChildren().size() > 0){
                idSet.addAll(convertTreeDotToIdSet(treeDot.getChildren(),kClass));
            }
        }
        return idSet;
    }

}
