/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon.monThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.CreateAlertReply;
import com.rl.events.monitoring.CreateAlertRequest;
import com.rl.extinterface.mon.swagger.client.model.Alert;
import com.rl.extinterface.mon.swagger.client.model.AlertDescription;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.AlertApi;

/**
 *
 * @author efabuba
 */
public class CreateAlertThread extends Thread {
    private MonitoringConfig moninfo;
    private CreateAlertRequest monreq;
    public CreateAlertThread (MonitoringConfig val, CreateAlertRequest req) {
        moninfo = val;
        monreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + moninfo.getIp() + ":" + moninfo.getPort() + "/" + moninfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        AlertApi api = new AlertApi(capi);
        
        AlertDescription callreq = new AlertDescription();
        Alert response;
        
        //set route input according the pa compute call
        
        callreq.setQuery(monreq.getQuery());
        callreq.setLabel(monreq.getLabel());
        callreq.setSeverity(AlertDescription.SeverityEnum.fromValue(monreq.getSeverity()));
        callreq.setValue(monreq.getValue());
        callreq.setKind(AlertDescription.KindEnum.fromValue(monreq.getKind()));
        callreq.setTarget(monreq.getTarget());


        CreateAlertReply monrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            response = api.postAlert(callreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside postAlert()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //send event

        monrep = new CreateAlertReply(monreq.getAlarmid(), response.getQuery(),
                            response.getLabel(), response.getSeverity().getValue(), response.getValue(),
                            response.getKind().getValue(), response.getTarget(), response.getAlertId());
        SingletonEventBus.getBus().post(monrep);
    }
}