package com.xdq.camunda.simulator;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutionListener {
    @EventListener
    public void onTaskEvent(DelegateTask delegateTask) {
        String eventName= delegateTask.getEventName();
        System.out.println("["+delegateTask.getEventName()+"] task:"+delegateTask.getTaskDefinitionKey()+" name:"+delegateTask.getName());
        if(eventName.equals(TaskListener.EVENTNAME_CREATE)){
            delegateTask.getExecution().setVariable("reviewResult","pass");
            delegateTask.complete();
        }
    }

    @EventListener
    public void onExecutionEvent(DelegateExecution delegateExecution){
        System.out.println("on execution["+delegateExecution.getClass()+"] "+delegateExecution.getEventName()+":"+delegateExecution.getCurrentActivityId()+" name:"+delegateExecution.getCurrentActivityName());

    }
}
