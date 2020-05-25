/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.mon;

import com.google.common.eventbus.Subscribe;
import com.rl.common.objects.MonitoringConfig;
import com.rl.events.monitoring.CreateAlertRequest;
import com.rl.events.monitoring.CreateExporterRequest;
import com.rl.events.monitoring.DeleteAlertRequest;
import com.rl.events.monitoring.DeleteExporterRequest;
import com.rl.events.monitoring.MonConfig;
import com.rl.events.monitoring.RetrieveAlertRequest;
import com.rl.events.monitoring.RetrieveExporterRequest;
import com.rl.extinterface.mon.monThreads.CreateAlertThread;
import com.rl.extinterface.mon.monThreads.CreateExporterThread;
import com.rl.extinterface.mon.monThreads.DeleteAlertThread;
import com.rl.extinterface.mon.monThreads.DeleteExporterThread;

/**
 *
 * @author user
 */
public class MonitoringIF {
    private MonitoringConfig monconf;
    public MonitoringIF() {
        monconf=null;
    }
    
    
    //////////////////////EVENT-Handlers//////////////////////////////////////
    @Subscribe
    public void handle_MonConfig(MonConfig moncfg) {
        System.out.println("monitoringIF --> Handle MonConfig Event");
        monconf = moncfg.getMonel();
    }  
    
    @Subscribe
    public void handle_CreateExporterRequest(CreateExporterRequest expreq) {
        System.out.println("monitoringIF --> Handle CreateExporterRequest Event");
        //Call Thread
        CreateExporterThread thr = new CreateExporterThread(monconf, expreq);
        thr.run();
    }  
    
    @Subscribe
    public void handle_DeleteExporterRequest(DeleteExporterRequest expreq) {
        System.out.println("monitoringIF --> Handle DeleteExporterRequest Event");
        //Call Thread
        DeleteExporterThread thr = new DeleteExporterThread(monconf, expreq);
        thr.run();
    }  
    
    @Subscribe
    public void handle_RetrieveExporterRequest(RetrieveExporterRequest expreq) {
        System.out.println("monitoringIF --> Handle RetrieveExporterRequest Event");
    }  
    
    @Subscribe
    public void handle_CreateAlertRequest(CreateAlertRequest alertreq) {
        System.out.println("monitoringIF --> Handle CreateAlertRequest Event");
        CreateAlertThread thr = new CreateAlertThread(monconf, alertreq);
        thr.run();
    } 
    
    @Subscribe
    public void handle_DeleteAlertRequest(DeleteAlertRequest alertreq) {
        System.out.println("monitoringIF --> Handle DeleteAlertRequest Event");
        DeleteAlertThread thr = new DeleteAlertThread(monconf, alertreq);
        thr.run();
    }
    
    @Subscribe
    public void handle_RetrieveAlertRequest(RetrieveAlertRequest alertreq) {
        System.out.println("monitoringIF --> Handle RetrieveAlertRequest Event");
    } 
}
