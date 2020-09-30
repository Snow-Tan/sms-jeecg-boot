package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyGlobalExcutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("currentActivitiId:  " + execution.getCurrentActivityId());
        System.out.println("currentActivitiName:  " + execution.getCurrentActivityName());
        System.out.println("EventName:  " + execution.getEventName());
        System.out.println("Id:  " + execution.getId());
        System.out.println("ParentId:  " + execution.getParentId());
        System.out.println("ProcessBusinessKey:  " + execution.getProcessBusinessKey());
        System.out.println("ProcessDefinitionId:  " + execution.getProcessDefinitionId());
        System.out.println("ProcessInstanceId:  " + execution.getProcessInstanceId());
        System.out.println("SuperExecutionId:  " + execution.getSuperExecutionId());

    }
}
