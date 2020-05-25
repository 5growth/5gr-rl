/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalAllocation;

import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;

/**
 *
 * @author osboxes
 */
public class E2EPhysicalAllocateReply {
    private long reqid;
    private String servid; //service identifiers
    private PNFReply pnfreply; //contains vim computation info 
    private boolean outcome;

    public E2EPhysicalAllocateReply(long reqid, String servid, PNFReply pnfreply, boolean outcome) {
        this.reqid = reqid;
        this.servid = servid;
        this.pnfreply = pnfreply;
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

    public PNFReply getPnfreply() {
        return pnfreply;
    }

    public void setPnfreply(PNFReply pnfreply) {
        this.pnfreply = pnfreply;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

}
