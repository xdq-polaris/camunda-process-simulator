package com.xdq.camunda.simulator;

import com.xdq.camunda.simulator.model.DeployAndSimRequest;
import com.xdq.camunda.simulator.model.DeployAndSimResponse;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/process")
public class ProcessSimulateController {


    @RequestMapping("/deployAndSim")
    public ResponseEntity<DeployAndSimResponse> deployAndSimulate(@RequestBody DeployAndSimRequest requestBody) throws InterruptedException{
        CountDownLatch countDownLatch=new CountDownLatch(1);
        SimulateBpmnParseListener simulateBpmnParseListener=new SimulateBpmnParseListener();
        simulateBpmnParseListener.processEndListener.add(new Runnable() {
            @Override
            public void run() {
                countDownLatch.countDown();
            }
        });
        StandaloneInMemProcessEngineConfiguration processEngineConfig
                = new StandaloneInMemProcessEngineConfiguration();
        processEngineConfig.setDatabaseSchemaUpdate("true");
        List<BpmnParseListener> bpmnParseListenerList=new ArrayList<>();
        bpmnParseListenerList.add(simulateBpmnParseListener);
        processEngineConfig.setCustomPostBPMNParseListeners(bpmnParseListenerList);
        processEngineConfig.setDelegateInterceptor(new SimulateDelegateInterceptor());
        ProcessEngine engine= processEngineConfig.buildProcessEngine();
        RepositoryService repositoryService= engine.getRepositoryService();
        //加载bpmn数据并部署
        byte[] bpmnXmlData=requestBody.bpmnXml.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream bis=new ByteArrayInputStream(bpmnXmlData);
        Deployment deployment= repositoryService.createDeployment()
                .addInputStream(requestBody.fileName,bis)
                .deploy();
        RuntimeService runtimeService= engine.getRuntimeService();
        ProcessInstance processInstance= runtimeService.startProcessInstanceByKey(requestBody.processDefKey,requestBody.variableValueMap);
        //等待实例完成
        countDownLatch.await();
        System.out.println("wait completed");
        //提取执行历史
        HistoryService historyService= engine.getHistoryService();
        List<HistoricActivityInstance> historicActivityInstanceList= historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        DeployAndSimResponse responseBody=new DeployAndSimResponse();
        responseBody.historicActivityInstanceList=historicActivityInstanceList;
        return ResponseEntity.ok(responseBody);
    }
}
