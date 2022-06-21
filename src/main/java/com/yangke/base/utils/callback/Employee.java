package com.yangke.base.utils.callback;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 10:59 下午
 */
public class Employee {
    public void doTask(Task task){
        try {
            System.out.println("Employee开始执行任务");
            Thread.sleep(5000);
            System.out.println("Employee处理完这项任务了");
            task.callBack("finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
