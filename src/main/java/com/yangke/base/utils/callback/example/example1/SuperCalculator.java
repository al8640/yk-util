package com.yangke.base.utils.callback.example.example1;

/**
 * @author ke.yang1
 * @description
 * @date 2022/6/23 11:23 下午
 */
public class SuperCalculator {
    public void  addCallBack(int a, int b,CallBack callBack){
        int result = a + b;
        callBack.onFinish(a,b,result);
    }
}
