/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon.monThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.CreateExporterReply;
import com.rl.events.monitoring.DeleteExporterReply;
import com.rl.events.monitoring.DeleteExporterRequest;
import com.rl.extinterface.mon.swagger.client.model.InlineResponse200;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ExporterApi;

/**
 *
 * @author efabuba
 */
public class DeleteExporterThread extends Thread {
    private MonitoringConfig moninfo;
    private DeleteExporterRequest monreq;
    public DeleteExporterThread (MonitoringConfig val, DeleteExporterRequest req) {
        moninfo = val;
        monreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + moninfo.getIp() + ":" + moninfo.getPort() + "/" + moninfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        ExporterApi api = new ExporterApi(capi);
        
        InlineResponse200 response;
        
        //set route input according the pa compute call


        DeleteExporterReply monrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            response = api.deleteExporter(monreq.getExporterId());
        } catch (ApiException e) {
            System.out.println("ApiException inside deleteExporter()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //send event

        monrep = new DeleteExporterReply(monreq.getPmid(), response.getDeleted());
        SingletonEventBus.getBus().post(monrep);
    } 
}
