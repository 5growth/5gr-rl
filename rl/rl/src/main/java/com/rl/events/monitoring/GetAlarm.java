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
public class GetAlarm {
    private boolean allalrm;
    private String alarmid;

    public GetAlarm(boolean allalrm, String alarmid) {
        this.allalrm = allalrm;
        this.alarmid = alarmid;
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
    
    
}
