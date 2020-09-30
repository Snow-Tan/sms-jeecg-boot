package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrorStartEventTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("============================");
        System.out.println("ErrorStartEventDelegate ----> execute");
        System.out.println("============================");
        //throw new BpmnError("my Exception Info");
    }
}
