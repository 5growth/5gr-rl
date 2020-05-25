/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.monitoring;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.monitoring.CreateAlarm;
import com.rl.events.monitoring.CreateAlertReply;
import com.rl.events.monitoring.CreateAlertRequest;
import com.rl.events.monitoring.CreateExporterReply;
import com.rl.events.monitoring.CreateExporterRequest;
import com.rl.events.monitoring.CreatePM;
import com.rl.events.monitoring.DeleteAlarm;
import com.rl.events.monitoring.DeleteAlertReply;
import com.rl.events.monitoring.DeleteAlertRequest;
import com.rl.events.monitoring.DeleteExporterReply;
import com.rl.events.monitoring.DeleteExporterRequest;
import com.rl.events.monitoring.DeletePM;
import com.rl.events.monitoring.GetAlarm;
import com.rl.events.monitoring.GetPM;
import com.rl.events.monitoring.RetrieveAlertReply;
import com.rl.events.monitoring.RetrieveAlertRequest;
import com.rl.events.monitoring.RetrieveExporterReply;
import com.rl.events.monitoring.RetrieveExporterRequest;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class MonitoringDriver {
    private HashMap<String, String> exportermap;
    private HashMap<String, String> alertmap;
    public MonitoringDriver() {
        exportermap = new HashMap();
        alertmap = new HashMap();
    }
    
    //////////////////////EVENT-Handlers//////////////////////////////////////
    
    @Subscribe
    public void handle_CreatePM(CreatePM pmreq) {
        System.out.println("MonitoringDriver --> Handle CreatePM Event");
        //Create Exporter request event
        CreateExporterRequest expreq = new CreateExporterRequest(pmreq.getName(), pmreq.getPmid(),
                                            pmreq.getPeriod(),pmreq.getEndplist());
        //send event
        System.out.println("MonitoringDriver --> Post CreateExporterRequest Event");
        SingletonEventBus.getBus().post(expreq);  
    }

    @Subscribe
    public void handle_DeletePM(DeletePM pmreq) {
        System.out.println("MonitoringDriver --> Handle DeletePM Event");
        String val;
        val = exportermap.get(pmreq.getPmid());
        if (val == null) {
           System.out.println("MonitoringDriver --> No PM found for " + pmreq.getPmid() + "");
           return;
        }
        DeleteExporterRequest expreq = new DeleteExporterRequest(pmreq.getPmid(), val);
        //send event
        System.out.println("MonitoringDriver --> Post DeleteExporterRequest Event");
        SingletonEventBus.getBus().post(expreq);  
    }

    @Subscribe
    public void handle_GetPM(GetPM pmreq) {
        System.out.println("MonitoringDriver --> Handle GetPM Event");
        RetrieveExporterRequest expreq;
        if (pmreq.isAllexp() == true) {
            //request all exporter
            System.out.println("MonitoringDriver --> Retrieve all PM");
            expreq = new RetrieveExporterRequest(true, "", "");
            System.out.println("MonitoringDriver --> Post RetrieveExporterRequest Event");
            SingletonEventBus.getBus().post(expreq); 
            return;
        }
        String val;
        val = exportermap.get(pmreq.getPmid());
        if (val == null) {
           System.out.println("MonitoringDriver --> No PM found for " + pmreq.getPmid() + "");
           return;
        }
        expreq = new RetrieveExporterRequest(false, pmreq.getPmid(), val);
        System.out.println("MonitoringDriver --> Post RetrieveExporterRequest Event");
        SingletonEventBus.getBus().post(expreq); 
    }

    @Subscribe
    public void handle_CreateAlarm(CreateAlarm pmreq) {
        System.out.println("MonitoringDriver --> Handle CreateAlarm Event");
        //Create Alert request event
        CreateAlertRequest alertreq = new CreateAlertRequest(pmreq.getAlarmid(), pmreq.getQuery(),
                                            pmreq.getLabel(),pmreq.getSeverity(), pmreq.getValue(),
                                            pmreq.getKind(), pmreq.getTarget());
        //send event
        System.out.println("MonitoringDriver --> Post CreateAlertRequest Event");
        SingletonEventBus.getBus().post(alertreq); 
        
    }

    @Subscribe
    public void handle_DeleteAlarm(DeleteAlarm pmreq) {
        System.out.println("MonitoringDriver --> Handle DeleteAlarm Event");
        String val;
        val = alertmap.get(pmreq.getAlarmid());
        if (val == null) {
           System.out.println("MonitoringDriver --> No Alarm found for " + pmreq.getAlarmid() + "");
           return;
        }
        DeleteAlertRequest expreq = new DeleteAlertRequest(pmreq.getAlarmid(), val);
        //send event
        System.out.println("MonitoringDriver --> Post DeleteAlertRequest Event");
        SingletonEventBus.getBus().post(expreq);
    }

    @Subscribe
    public void handle_GetAlarm(GetAlarm pmreq) {
        System.out.println("MonitoringDriver --> Handle GetAlarm Event");
        RetrieveAlertRequest alertreq;
        if (pmreq.isAllalrm() == true) {
            //request all exporter
            System.out.println("MonitoringDriver --> Retrieve all Alarm");
            alertreq = new RetrieveAlertRequest(true, "", "");
            System.out.println("MonitoringDriver --> Post RetrieveAlertRequest Event");
            SingletonEventBus.getBus().post(alertreq); 
            return;
        }
        String val;
        val = alertmap.get(pmreq.getAlarmid());
        if (val == null) {
           System.out.println("MonitoringDriver --> No Alarm found for " + pmreq.getAlarmid() + "");
           return;
        }
        alertreq = new RetrieveAlertRequest(false, pmreq.getAlarmid(), val);
        System.out.println("MonitoringDriver --> Post RetrieveAlertRequest Event");
        SingletonEventBus.getBus().post(alertreq);
    }  
    
    @Subscribe
    public void handle_CreateExporterReply(CreateExporterReply exprep) {
        System.out.println("MonitoringDriver --> Handle CreateExporterReply Event");
        //insert request in exporter map
        exportermap.put(exprep.getPmid(), exprep.getExporterId());
    }  
    
    @Subscribe
    public void handle_DeleteExporterReply(DeleteExporterReply exprep) {
        System.out.println("MonitoringDriver --> Handle DeleteExporterReply Event");
        //delete request in exporter map
        exportermap.remove(exprep.getPmid());
    }  
    
    @Subscribe
    public void handle_RetrieveExporterReply(RetrieveExporterReply exprep) {
        System.out.println("MonitoringDriver --> Handle RetrieveExporterReply Event");
        //nothing to do
    }  
    
    @Subscribe
    public void handle_CreateAlertReply(CreateAlertReply alertrep) {
        System.out.println("MonitoringDriver --> Handle CreateAlertReply Event");
        //insert request in alert map
        alertmap.put(alertrep.getAlarmid(), alertrep.getAlertid());
        
    } 
    
    @Subscribe
    public void handle_DeleteAlertReply(DeleteAlertReply alertrep) {
        System.out.println("MonitoringDriver --> Handle DeleteAlertReply Event");
        //delete request in alert map
        alertmap.remove(alertrep.getAlarmid());
    }
    
    @Subscribe
    public void handle_RetrieveAlertReply(RetrieveAlertReply alertrep) {
        System.out.println("MonitoringDriver --> Handle RetrieveAlertReply Event");
        //nothing to do
    }  
}
