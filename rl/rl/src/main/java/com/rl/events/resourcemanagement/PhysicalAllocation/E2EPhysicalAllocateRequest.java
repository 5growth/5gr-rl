/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalAllocation;

import com.rl.extinterface.nbi.swagger.model.PNFRequest;

/**
 *
 * @author osboxes
 */
public class E2EPhysicalAllocateRequest {
    private long reqid;
    private String servid; //service identifiers
    private PNFRequest request;
    
    public E2EPhysicalAllocateRequest() {
        reqid = 0;
        servid = "";
        request = null;
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

    public PNFRequest getRequest() {
        return request;
    }

    public void setRequest(PNFRequest request) {
        this.request = request;
    }
}
