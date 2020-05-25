/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon.monThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.RetrieveAlertReply;
import com.rl.events.monitoring.RetrieveAlertRequest;
import com.rl.events.monitoring.RetrieveExporterReply;
import com.rl.extinterface.mon.swagger.client.model.Alert;
import com.rl.extinterface.mon.swagger.client.model.AlertQueryResult;
import com.rl.extinterface.mon.swagger.client.model.Exporter;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.AlertApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class GetAlertThread extends Thread {
    private MonitoringConfig moninfo;
    private RetrieveAlertRequest monreq;
    public GetAlertThread (MonitoringConfig val, RetrieveAlertRequest req) {
        moninfo = val;
        monreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + moninfo.getIp() + ":" + moninfo.getPort() + "/" + moninfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        AlertApi api = new AlertApi(capi);
        
        RetrieveAlertReply monrep;
        
        if (monreq.isAllalrm() == true) {
            //retrieve all PM
            AlertQueryResult response;
            try {
                //Filter nfviPopComputeInformationRequest = null;
                response = api.getAllAlerts();
            } catch (ApiException e) {
                System.out.println("ApiException inside getAllAlerts().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;
            }
            
            //send event
            monrep = new RetrieveAlertReply(monreq.getAlarmid(), response.getAlert());
            SingletonEventBus.getBus().post(monrep);
        } else {
            //retrieve specific PM
            Alert response;
            try {
                //Filter nfviPopComputeInformationRequest = null;
                response = api.getAlert(monreq.getAlertid());
            } catch (ApiException e) {
                System.out.println("ApiException inside getAlert().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;
            }
            
            //add event in the list
            List <Alert> alertlist = new ArrayList();
            alertlist.add(response);
            
            //send event
            monrep = new RetrieveAlertReply(monreq.getAlarmid(), alertlist);
            SingletonEventBus.getBus().post(monrep);
        }
    }
}
