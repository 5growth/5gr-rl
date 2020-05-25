/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;



public class ComputeAllocateDBQueryOutcome {

    private long reqid;
    private String servid; //service identifiers 
    private long domid; //id of vim domain to contact
    private boolean outcome; //result of the request
    private VirtualCompute computereply;
    //private long computeserviceid;
    private long nfvipopid;
    private AllocateComputeRequest computerequest;

    public ComputeAllocateDBQueryOutcome(long reqid, String servid, long domid, boolean outcome, VirtualCompute computereply, long nfvipopid, AllocateComputeRequest computerequest) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.outcome = outcome;
        this.computereply = computereply;
        this.nfvipopid = nfvipopid;
        this.computerequest = computerequest;
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

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public VirtualCompute getComputereply() {
        return computereply;
    }

    public void setComputereply(VirtualCompute computereply) {
        this.computereply = computereply;
    }

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public AllocateComputeRequest getComputerequest() {
        return computerequest;
    }

    public void setComputerequest(AllocateComputeRequest computerequest) {
        this.computerequest = computerequest;
    }

    
}
