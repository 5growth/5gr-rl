/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;




public class Fed_NetworkAllocateDBQuery {

    private long reqid;
    private String servid; //service identifiers
    private InterNfviPopConnectivityRequest networkreq; //contains vim computation info
    private long logicalpath; //selected logical path
    private String fed_vlanid; //service identifiers
 
 
    public Fed_NetworkAllocateDBQuery(long reqid, String servid, InterNfviPopConnectivityRequest networkreq, long logicalpath, String fed_vlanid) {
        this.reqid = reqid;
        this.servid = servid;
        this.networkreq = networkreq;
        this.logicalpath = logicalpath;
        this.fed_vlanid = fed_vlanid;
    }

    public String getFed_vlanid() {
        return fed_vlanid;
    }

    public void setFed_vlanid(String fed_vlanid) {
        this.fed_vlanid = fed_vlanid;
    }

    
    
    
    
    
    public InterNfviPopConnectivityRequest getNetworkreq() {
        return networkreq;
    }

    public void setNetworkreq(InterNfviPopConnectivityRequest networkreq) {
        this.networkreq = networkreq;
    }

 

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }

    public long getLogicalpath() {
        return logicalpath;
    }

    public void setLogicalpath(long logicalpath) {
        this.logicalpath = logicalpath;
    }


 
}
