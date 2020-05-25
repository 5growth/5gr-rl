/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalTermination;

import com.rl.extinterface.nbi.swagger.model.PNFReply;

/**
 *
 * @author osboxes
 */
public class E2EPhysicalTerminateInstanceOutcome {
    private long reqid;
    private long servid; //service identifiers 
    private PNFReply pnfresp;

    public E2EPhysicalTerminateInstanceOutcome(long reqid, long servid, PNFReply pnfresp) {
        this.reqid = reqid;
        this.servid = servid;
        this.pnfresp = pnfresp;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public long getServid() {
        return servid;
    }

    public void setServid(long servid) {
        this.servid = servid;
    }

    public PNFReply getPnfresp() {
        return pnfresp;
    }

    public void setPnfresp(PNFReply pnfresp) {
        this.pnfresp = pnfresp;
    }
    
}
