package org.jeecg.modules.activiti.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class NodeListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("====================");
        System.out.println("currentActivitiId:  " + execution.getCurrentActivityId());
        System.out.println("currentActivitiName:  " + execution.getCurrentActivityName());
        System.out.println("EventName:  " + execution.getEventName());
        System.out.println("Id:  " + execution.getId());
        System.out.println("ParentId:  " + execution.getParentId());
        System.out.println("ProcessBusinessKey:  " + execution.getProcessBusinessKey());
        System.out.println("ProcessDefinitionId:  " + execution.getProcessDefinitionId());
        System.out.println("ProcessInstanceId:  " + execution.getProcessInstanceId());
        System.out.println("SuperExecutionId:  " + execution.getSuperExecutionId());
        System.out.println("====================");

        Map<String, Object> map = new HashMap<>();
//        System.in.read()
        String executionId = "232545";
        map.put("signalfor " + executionId, "消息.uuuuu");
        map.put("condition","0");
        execution.getEngineServices().getRuntimeService().signal("305309",map);
    }

}
