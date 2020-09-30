package org.jeecg.modules.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.springframework.stereotype.Component;

@Component
public class MyActivitiEventListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent event) {
        System.out.println("===============");
        System.out.println(event.getType());
        System.out.println("===============");
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
