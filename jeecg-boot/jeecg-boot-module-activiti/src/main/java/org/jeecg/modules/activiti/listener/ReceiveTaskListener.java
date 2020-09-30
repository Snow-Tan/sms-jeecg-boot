package org.jeecg.modules.activiti.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveTaskListener implements JavaDelegate {
    @Autowired
    private RuntimeService runtimeService;
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("====================");
        System.out.println("ReceiveTaskListener run...");
        System.out.println("Id： " + execution.getId());
        System.out.println("EventName： " + execution.getEventName());
        System.out.println("====================");
        //execution.getEngineServices().getRuntimeService().signal(execution.getId());
        //execution.getEngineServices().getRuntimeService().signal("287533");

    }
}
