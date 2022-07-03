package com.yangke.base.utils.callback.example.example1;

/**
 * @author ke.yang1
 * @description
 * @date 2022/6/23 11:35 下午
 */
public class Seller {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public Seller(String name) {
        this.name = name;
    }

    public void call(int a,int b){
        System.out.println("seller job start");
        new SuperCalculator().addCallBack(a, b, (a1, b1, result) -> System.out.println(name+"求助小红算账"+ a1 +"+"+ b1 +"="+result));
        System.out.println("seller job end");
    }
}
