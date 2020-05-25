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
public class PhysicalTerminateDBQuery {
    private long reqid;
    private long servid; //service identifiers
    private PNFReply pnfreq; //contains vim computation info

    public PhysicalTerminateDBQuery(long reqid, long servid, PNFReply pnfreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.pnfreq = pnfreq;
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

    public PNFReply getPnfreq() {
        return pnfreq;
    }

    public void setPnfreq(PNFReply pnfreq) {
        this.pnfreq = pnfreq;
    }
    
}
