package com.xdq.camunda.simulator;

import com.xdq.camunda.simulator.model.DeployAndSimRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SimulateControllerTests {
    @Test
    public void testRequest() throws IOException {
        RestTemplate restTemplate=new RestTemplate();
        DeployAndSimRequest request=new DeployAndSimRequest();
        File file=new File("src/test/resources/test.bpmn");
        byte[] bpmnXmlData=null;
        try(FileInputStream fis=new FileInputStream(file)){
            bpmnXmlData= fis.readAllBytes();
        }
        request.fileName=file.getName();
        request.bpmnXml=new String(bpmnXmlData);
        request.processDefKey="library_file_upload_review_process";
        var response= restTemplate.postForEntity("http://localhost:8080/process/deployAndSim",request,
                Object.class);
        System.out.println("response:"+response);
    }
}
