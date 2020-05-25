/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon.monThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.CreateExporterReply;
import com.rl.events.monitoring.CreateExporterRequest;
import com.rl.events.placement.PAComputeReply;
import com.rl.extinterface.mon.swagger.client.model.Exporter;
import com.rl.extinterface.mon.swagger.client.model.ExporterDescription;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ExporterApi;

/**
 *
 * @author efabuba
 */
public class CreateExporterThread extends Thread {
    private MonitoringConfig moninfo;
    private CreateExporterRequest monreq;
    public CreateExporterThread (MonitoringConfig val, CreateExporterRequest req) {
        moninfo = val;
        monreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + moninfo.getIp() + ":" + moninfo.getPort() + "/" + moninfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        ExporterApi api = new ExporterApi(capi);
        
        ExporterDescription callreq = new ExporterDescription();
        Exporter response;
        
        //set route input according the pa compute call
        
        callreq.setCollectionPeriod(monreq.getPeriod());
        callreq.setName(monreq.getName());
        callreq.setEndpoint(monreq.getEndplist());

        CreateExporterReply monrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            response = api.postExporter(callreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside postExporter()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //send event

        monrep = new CreateExporterReply(response.getName(), monreq.getPmid(), response.getCollectionPeriod(),
                            response.getEndpoint(), response.getExporterId());
        SingletonEventBus.getBus().post(monrep);
    } 
}
