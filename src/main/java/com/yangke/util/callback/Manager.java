package com.yangke.util.callback;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/3 10:58 下午
 */
public class Manager implements Task {
    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void doOther(){
        System.out.println("项目经理去做别的了");
    }

    public void doOtherContinue(){
        System.out.println("项目经理收到员工信息后继续去做别的了");
    }

    public void setTask(){
        System.out.println("经理给员工布置任务");
        doOther();
        employee.doTask(this);
        doOtherContinue();
    }
    @Override
    public void callBack(String result) {
        System.out.println("收到员工处理结果" +result);
    }
}
