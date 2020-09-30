package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncludeGatewayListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("====================");
        System.out.println("ReceiveTaskListener run...");
        System.out.println("EventName： " + execution.getEventName());
        System.out.println("ProcessInstanceId: " + execution.getProcessInstanceId());
        System.out.println("executionId:  " + execution.getId());
        System.out.println("====================");
        String executionId = execution.getId();
        List<ProcessDefinition> list = execution.getEngineServices()
                .getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey("includegatewayProcess")
                .list();
        for (ProcessDefinition definition : list) {

        }
        Map map = new HashMap();
        map.put("age", 12);
        execution.getEngineServices()
                .getRuntimeService()
                .setVariables(executionId,map);
    }
}
