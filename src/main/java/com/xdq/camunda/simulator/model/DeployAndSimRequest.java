package com.xdq.camunda.simulator.model;

import java.util.HashMap;
import java.util.Map;

public class DeployAndSimRequest {
    public String fileName;
    public String bpmnXml;
    public String processDefKey;
    public Map<String,Object> variableValueMap=new HashMap<>();
}
