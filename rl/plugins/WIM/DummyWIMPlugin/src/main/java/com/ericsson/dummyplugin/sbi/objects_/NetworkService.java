/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class NetworkService {
    private String netservid;
    private String bw;
    private String delay;
    private String netresid;
    private String mtpservid;

    public NetworkService(String netservid, String bw, String delay, String netresid, String mtpservid) {
        this.netservid = netservid;
        this.bw = bw;
        this.delay = delay;
        this.netresid = netresid;
        this.mtpservid = mtpservid;
    }

    public String getNetservid() {
        return netservid;
    }

    public void setNetservid(String netservid) {
        this.netservid = netservid;
    }

    public String getBw() {
        return bw;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getNetresid() {
        return netresid;
    }

    public void setNetresid(String netresid) {
        this.netresid = netresid;
    }

    public String getMtpservid() {
        return mtpservid;
    }

    public void setMtpservid(String mtpservid) {
        this.mtpservid = mtpservid;
    }
    
    
}
