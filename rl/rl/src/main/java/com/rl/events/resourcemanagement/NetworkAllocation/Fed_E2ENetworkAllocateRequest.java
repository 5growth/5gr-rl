/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;


import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;

public class Fed_E2ENetworkAllocateRequest {

    private long reqid;
    private String servid; //service identifiers
    private InterNfviPopConnectivityRequest networkreq; //contains vim computation info

    public Fed_E2ENetworkAllocateRequest(long reqid, String servid, InterNfviPopConnectivityRequest networkreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.networkreq = networkreq;
    }


    //insert set/get methods
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

    public InterNfviPopConnectivityRequest getNetworkreq() {
        return networkreq;
    }

    public void setNetworkreq(InterNfviPopConnectivityRequest networkreq) {
        this.networkreq = networkreq;
    }
    
}
