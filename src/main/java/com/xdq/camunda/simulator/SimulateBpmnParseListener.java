package com.xdq.camunda.simulator;

import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.ExternalTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.NoneEndEventActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExternalTaskEntity;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.camunda.bpm.engine.delegate.ExecutionListener.*;

public class SimulateBpmnParseListener extends AbstractBpmnParseListener {
    private static final List<String> EXECUTION_EVENTS = Arrays.asList(
            EVENTNAME_START,
            EVENTNAME_END);

    public List<Runnable> processEndListener=new ArrayList<>();

    @Override
    public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
        super.parseUserTask(userTaskElement, scope, activity);

    }

    @Override
    public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
        ActivityBehavior oldActivityBehavior= activity.getActivityBehavior();
        if(oldActivityBehavior instanceof ExternalTaskActivityBehavior externalTaskActivityBehavior){
            ExternalTaskActivityBehavior newBehavior=new ExternalTaskActivityBehavior(null,null){
                @Override
                public void execute(ActivityExecution execution) throws Exception {
                    //leave(execution); //直接视为已经完成
                    oldActivityBehavior.execute(execution);
                    ExecutionEntity executionEntity=(ExecutionEntity)execution;
                    //ExternalTaskEntity.createAndInsert(executionEntity,"make",0);
                    System.out.println("after external task behavior execution:"+execution);
                    executionEntity.getExternalTasks().stream().filter(new Predicate<ExternalTaskEntity>() {
                        @Override
                        public boolean test(ExternalTaskEntity externalTaskEntity) {
                            return Objects.equals(externalTaskEntity.getExecutionId(), executionEntity.getId());
                        }
                    }).forEach(new Consumer<ExternalTaskEntity>() {
                        @Override
                        public void accept(ExternalTaskEntity externalTaskEntity) {
                            Map<String,Object> varibleMap= executionEntity.getVariables();
                            Map<String,Object> localVariableMap=executionEntity.getVariablesLocal();
                            externalTaskEntity.complete(varibleMap,localVariableMap);
                        }
                    });
                }
            };
            activity.setActivityBehavior(newBehavior);
            activity.addListener(EVENTNAME_START, new DelegateListener<BaseDelegateExecution>() {
                @Override
                public void notify(BaseDelegateExecution instance) throws Exception {
                    System.out.println("[start] service task:"+instance);
                }
            });
        }
    }

    @Override
    public void parseEndEvent(Element endEventElement, ScopeImpl scope, ActivityImpl activity) {
        System.out.println("parse end event:"+endEventElement);
        activity.addListener(EVENTNAME_END, new DelegateListener<BaseDelegateExecution>() {
            @Override
            public void notify(BaseDelegateExecution instance) throws Exception {
                System.out.println("[end] end event:"+instance);
                processEndListener.forEach(Runnable::run);
            }
        });
    }
}
