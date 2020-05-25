/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon.monThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.RetrieveExporterReply;
import com.rl.events.monitoring.RetrieveExporterRequest;
import com.rl.extinterface.mon.swagger.client.model.Exporter;
import com.rl.extinterface.mon.swagger.client.model.ExporterQueryResult;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ExporterApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class GetExporterThread extends Thread{
    private MonitoringConfig moninfo;
    private RetrieveExporterRequest monreq;
    public GetExporterThread (MonitoringConfig val, RetrieveExporterRequest req) {
        moninfo = val;
        monreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + moninfo.getIp() + ":" + moninfo.getPort() + "/" + moninfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        ExporterApi api = new ExporterApi(capi);
        
        RetrieveExporterReply monrep;
        
        if (monreq.isAllexp() == true) {
            //retrieve all PM
            ExporterQueryResult response;
            try {
                //Filter nfviPopComputeInformationRequest = null;
                response = api.getExporters();
            } catch (ApiException e) {
                System.out.println("ApiException inside getExporters().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;
            }
            
            //send event
            monrep = new RetrieveExporterReply(monreq.getPmid(), response.getExporter());
            SingletonEventBus.getBus().post(monrep);
        } else {
            //retrieve specific PM
            Exporter response;
            try {
                //Filter nfviPopComputeInformationRequest = null;
                response = api.getExporter(monreq.getExporterid());
            } catch (ApiException e) {
                System.out.println("ApiException inside getExporter().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;
            }
            
            //add event in the list
            List <Exporter> explist = new ArrayList();
            explist.add(response);
            
            //send event
            monrep = new RetrieveExporterReply(monreq.getPmid(), explist);
            SingletonEventBus.getBus().post(monrep);
        }
    } 
}
