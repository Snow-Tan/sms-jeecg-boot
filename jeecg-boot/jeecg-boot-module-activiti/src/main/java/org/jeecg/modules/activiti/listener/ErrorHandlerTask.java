package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ErrorHandlerTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("============================");
        System.out.println("ErrorHandlerDelegate ---> execute");
        System.out.println("============================");
    }
}
