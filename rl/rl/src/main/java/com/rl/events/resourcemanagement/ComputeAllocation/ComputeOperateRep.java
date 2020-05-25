/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.VirtualCompute;


/**
 *
 * @author efabuba
 */
public class ComputeOperateRep {
    private long reqid;
    private VirtualCompute response;
    private boolean outcome;

    public ComputeOperateRep(long reqid, VirtualCompute response) {
        this.reqid = reqid;
        this.response = response;
        this.outcome = true;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public VirtualCompute getResponse() {
        return response;
    }

    public void setResponse(VirtualCompute response) {
        this.response = response;
    }
    
    
    
}
