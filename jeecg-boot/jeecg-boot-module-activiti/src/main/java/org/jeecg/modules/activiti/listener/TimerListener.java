package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

public class TimerListener implements TaskListener, ExecutionListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("TimerListener -- Task!");
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("TimerListener -- execution!");
    }
}
