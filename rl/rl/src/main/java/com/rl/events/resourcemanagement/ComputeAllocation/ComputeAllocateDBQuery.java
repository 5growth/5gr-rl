/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;



public class ComputeAllocateDBQuery {

    private long reqid;
    private String servid; //service identifiers
    private AllocateComputeRequest computereq; //contains vim computation info

    public ComputeAllocateDBQuery(long reqid, String servid, AllocateComputeRequest computereq) {
        this.reqid = reqid;
        this.servid = servid;
        this.computereq = computereq;
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

    public AllocateComputeRequest getComputereq() {
        return computereq;
    }

    public void setComputereq(AllocateComputeRequest computereq) {
        this.computereq = computereq;
    }


}
