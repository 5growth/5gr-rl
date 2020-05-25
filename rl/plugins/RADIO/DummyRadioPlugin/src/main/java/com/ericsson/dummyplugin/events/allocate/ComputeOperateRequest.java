/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.OperateComputeRequest;

/**
 *
 * @author efabuba
 */
public class ComputeOperateRequest {
   private long reqid;

    private OperateComputeRequest request; //contains vim computation info  

    public ComputeOperateRequest(long reqid, OperateComputeRequest request) {
        this.reqid = reqid;
        this.request = request;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public OperateComputeRequest getRequest() {
        return request;
    }

    public void setRequest(OperateComputeRequest request) {
        this.request = request;
    }
    
    
}
