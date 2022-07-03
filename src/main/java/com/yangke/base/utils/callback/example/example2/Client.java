package com.yangke.base.utils.callback.example.example2;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 11:04 下午
 */
public class Client {
    public static void main(String[] args) {
        Employee employee = new Employee();
        Manager manager = new Manager();
        manager.setEmployee(employee);
        manager.setTask();
    }
}
