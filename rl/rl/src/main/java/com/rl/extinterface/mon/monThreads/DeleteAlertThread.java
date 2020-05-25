/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon.monThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.DeleteAlertReply;
import com.rl.events.monitoring.DeleteAlertRequest;
import com.rl.extinterface.mon.swagger.client.model.InlineResponse2001;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.AlertApi;

/**
 *
 * @author efabuba
 */
public class DeleteAlertThread extends Thread {
    private MonitoringConfig moninfo;
    private DeleteAlertRequest monreq;
    public DeleteAlertThread (MonitoringConfig val, DeleteAlertRequest req) {
        moninfo = val;
        monreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + moninfo.getIp() + ":" + moninfo.getPort() + "/" + moninfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        AlertApi api = new AlertApi(capi);
        
        InlineResponse2001 response;
        
        //set route input according the pa compute call


        DeleteAlertReply monrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            response = api.deleteAlert(monreq.getAlertid());
        } catch (ApiException e) {
            System.out.println("ApiException inside deleteAlert()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //send event

        monrep = new DeleteAlertReply(monreq.getAlarmid(), response.getDeleted());
        SingletonEventBus.getBus().post(monrep);
    }
}
