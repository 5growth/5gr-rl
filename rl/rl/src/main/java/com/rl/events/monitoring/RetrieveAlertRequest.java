/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

/**
 *
 * @author efabuba
 */
public class RetrieveAlertRequest {
    private boolean allalrm;
    private String alarmid;
    private String alertid;

    public RetrieveAlertRequest(boolean allalrm, String alarmid, String alertid) {
        this.allalrm = allalrm;
        this.alarmid = alarmid;
        this.alertid = alertid;
    }

    public boolean isAllalrm() {
        return allalrm;
    }

    public void setAllalrm(boolean allalrm) {
        this.allalrm = allalrm;
    }

    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid;
    }

    public String getAlertid() {
        return alertid;
    }

    public void setAlertid(String alertid) {
        this.alertid = alertid;
    }
    
    
}
