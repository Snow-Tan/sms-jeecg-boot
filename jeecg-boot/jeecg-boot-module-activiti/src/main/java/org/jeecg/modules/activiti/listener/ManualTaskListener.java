package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManualTaskListener implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String key = execution.getEngineServices().getRepositoryService().getProcessDefinition(execution.getProcessDefinitionId()).getKey();
        //
        List<Execution> list = execution.getEngineServices()
                .getRuntimeService()
                .createExecutionQuery()
                .processDefinitionKey(key)
                .list();
        for (Execution e : list) {
            // execution.getEngineServices().getRuntimeService().signal(e.getId());
        }
        System.out.println("........");
    }
}
