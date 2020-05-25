/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.crosshaulplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class RANService {
    private String resid;
    private String provid;
    private String tenid;
    private String srcid;
    private String dstid;
    private String qosid;
    private String servid;
    private String ip;
    private String rxport;
    private String txport;

    public RANService(String resid, String provid, String tenid, String srcid, String dstid, String qosid, String servid, String ip, String rxport, String txport) {
        this.resid = resid;
        this.provid = provid;
        this.tenid = tenid;
        this.srcid = srcid;
        this.dstid = dstid;
        this.qosid = qosid;
        this.servid = servid;
        this.ip = ip;
        this.rxport = rxport;
        this.txport = txport;
    }


    
    public String getResId() {
        return resid;
    }
    public String getProvId() {
        return provid;
    }
    public String getTenId() {
        return tenid;
    }
    public String getSrcId() {
        return srcid;
    }
    public String getDstId() {
        return dstid;
    }
    public String getQosId() {
        return qosid;
    }
    public String getServId() {
        return servid;
    } 

    public String getIp() {
        return ip;
    }

    public String getRxport() {
        return rxport;
    }

    public String getTxport() {
        return txport;
    }


    
}
    
