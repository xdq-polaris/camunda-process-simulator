package com.xdq.camunda.simulator;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.delegate.DelegateInvocation;
import org.camunda.bpm.engine.impl.delegate.ExpressionGetInvocation;
import org.camunda.bpm.engine.impl.interceptor.DelegateInterceptor;
import org.camunda.bpm.engine.impl.task.delegate.TaskListenerInvocation;

import java.lang.reflect.Field;

public class SimulateDelegateInterceptor implements DelegateInterceptor {
    @Override
    public void handleInvocation(DelegateInvocation invocation) throws Exception {
        if(invocation.getContextExecution()==null){
            invocation.proceed();
            return;
        }
        String eventID=invocation.getContextExecution().getId();
        String eventName= invocation.getContextExecution().getEventName();
        System.out.println("event id:"+eventID+" event name:"+eventName+" invocation type:"+invocation.getClass());
        if(invocation instanceof TaskListenerInvocation){
            System.out.println("user task detected");
            Field field= TaskListenerInvocation.class.getDeclaredField("delegateTask");
            field.setAccessible(true);
            DelegateTask delegateTask= (DelegateTask) field.get(invocation);
            delegateTask.getExecution().setVariable("reviewResult","pass");
            delegateTask.complete();
        }
        if(invocation instanceof ExpressionGetInvocation){
            System.out.println("service task detected");
        }
        invocation.proceed();
    }
}
