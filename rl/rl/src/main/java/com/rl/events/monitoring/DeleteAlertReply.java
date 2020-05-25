/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

import java.util.List;

/**
 *
 * @author efabuba
 */
public class DeleteAlertReply {
    private String alarmid;
    private List<String> alertlist;

    public DeleteAlertReply(String alarmid, List<String> alertlist) {
        this.alarmid = alarmid;
        this.alertlist = alertlist;
    }



    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid;
    }

    public List<String> getAlertlist() {
        return alertlist;
    }

    public void setAlertlist(List<String> alertlist) {
        this.alertlist = alertlist;
    }
 
    
}
