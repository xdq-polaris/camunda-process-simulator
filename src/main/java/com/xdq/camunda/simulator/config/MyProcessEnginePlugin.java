package com.xdq.camunda.simulator.config;

import com.xdq.camunda.simulator.SimulateDelegateInterceptor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.configuration.Ordering;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordering.DEFAULT_ORDER+1)
public class MyProcessEnginePlugin implements ProcessEnginePlugin {
    public MyProcessEnginePlugin(){
        System.out.println("my process engine plugin");
    }
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setDelegateInterceptor(new SimulateDelegateInterceptor());
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
