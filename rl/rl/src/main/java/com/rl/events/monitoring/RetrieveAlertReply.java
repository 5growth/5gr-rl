/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

import com.rl.extinterface.mon.swagger.client.model.Alert;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class RetrieveAlertReply {
    private String alarmid;
    private List<Alert> alertlist;

    public RetrieveAlertReply(String alarmid, List<Alert> alertlist) {
        this.alarmid = alarmid;
        this.alertlist = alertlist;
    }

    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid;
    }

    public List<Alert> getAlertlist() {
        return alertlist;
    }

    public void setAlertlist(List<Alert> alertlist) {
        this.alertlist = alertlist;
    }

    
    
}
