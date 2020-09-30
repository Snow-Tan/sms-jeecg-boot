package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask implements JavaDelegate {

    private String name = "jjjj";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("====================");
        System.out.println(name);
        System.out.println("ReceiveTaskListener run...");
        System.out.println("Id： " + execution.getId());
        System.out.println("EventName： " + execution.getEventName());
        System.out.println("====================");

    }
}
