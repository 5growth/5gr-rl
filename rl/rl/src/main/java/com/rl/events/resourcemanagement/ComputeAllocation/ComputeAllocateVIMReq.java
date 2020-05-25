/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;



public class ComputeAllocateVIMReq {

    private long reqid;
    private String servid; //service identifiers 
    private long domid; //id of vim domain to contact
    //private long computeservid;
    private AllocateComputeRequest computereq;
    private long nfvipopid;

    public ComputeAllocateVIMReq(long reqid, String servid, long domid, AllocateComputeRequest computereq, long nfvipopid) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.computereq = computereq;
        this.nfvipopid = nfvipopid;
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

    public AllocateComputeRequest getComputereq() {
        return computereq;
    }

    public void setComputereq(AllocateComputeRequest computereq) {
        this.computereq = computereq;
    }


    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

  
}
