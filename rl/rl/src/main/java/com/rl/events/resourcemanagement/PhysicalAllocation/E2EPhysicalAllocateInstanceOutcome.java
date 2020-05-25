/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalAllocation;

import com.rl.extinterface.nbi.swagger.model.PNFReply;

/**
 *
 * @author osboxes
 */
public class E2EPhysicalAllocateInstanceOutcome {
    private long reqid;
    private String servid; //service identifiers 
    private PNFReply computereply;
    private boolean outcome;

    public E2EPhysicalAllocateInstanceOutcome(long reqid, String servid, PNFReply computereply, boolean outcome) {
        this.reqid = reqid;
        this.servid = servid;
        this.computereply = computereply;
        this.outcome = outcome;
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

    public PNFReply getComputereply() {
        return computereply;
    }

    public void setComputereply(PNFReply computereply) {
        this.computereply = computereply;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }
    
    
    
}
