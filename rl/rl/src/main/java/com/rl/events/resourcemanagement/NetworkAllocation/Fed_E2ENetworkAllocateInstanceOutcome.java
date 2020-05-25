/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;


public class Fed_E2ENetworkAllocateInstanceOutcome {

    private long reqid;
    private String servid;
    private VirtualNetwork vn;
    private boolean outcome;
    private long logicalpath;

    public Fed_E2ENetworkAllocateInstanceOutcome(long reqid, String servid, VirtualNetwork vn, boolean outcome, long logicalpath) {
        this.reqid = reqid;
        this.servid = servid;
        this.vn = vn;
        this.outcome = outcome;
        this.logicalpath = logicalpath;
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

    public VirtualNetwork getVn() {
        return vn;
    }

    public void setVn(VirtualNetwork vn) {
        this.vn = vn;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public long getLogicalpath() {
        return logicalpath;
    }

    public void setLogicalpath(long logicalpath) {
        this.logicalpath = logicalpath;
    }
    

}
