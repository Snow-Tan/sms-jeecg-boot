package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTaskThrowError implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        throw new BpmnError("MyErrorCode", "myError");

    }
}
