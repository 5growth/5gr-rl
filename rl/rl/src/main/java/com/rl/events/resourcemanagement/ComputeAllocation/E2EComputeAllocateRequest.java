/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;

public class E2EComputeAllocateRequest {

    private long reqid;
    private String servid; //service identifiers
    private AllocateComputeRequest request; //contains vim computation info 

    public E2EComputeAllocateRequest() {
        reqid = 0;
        servid = "";
        request = null;
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

    public AllocateComputeRequest getRequest() {
        return request;
    }

    public void setRequest(AllocateComputeRequest request) {
        this.request = request;
    }

}
