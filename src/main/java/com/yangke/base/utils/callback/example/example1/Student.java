package com.yangke.base.utils.callback.example.example1;

/**
 * @author ke.yang1
 * @description
 * @date 2022/6/23 11:13 下午
 */
public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    private void call(int a,int b){
        System.out.println("student job start");
        new SuperCalculator().addCallBack(a, b, new CallBack() {
            @Override
            public void onFinish(int a, int b, int result) {
                System.out.println(name+"求助小红计算"+a+"+"+b+"="+result);
            }
        });
        System.out.println("student job end");
    }
    public static void main(String[] args) {
        new Student("小明").call(1,2);
        new Seller("婆婆").call(1,2);
    }
}
