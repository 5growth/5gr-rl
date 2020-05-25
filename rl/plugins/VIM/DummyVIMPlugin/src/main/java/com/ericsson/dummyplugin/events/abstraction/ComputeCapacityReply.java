/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

import com.ericsson.dummyplugin.nbi.swagger.model.CapacityInformation;



/**
 *
 * @author efabuba
 */
public class ComputeCapacityReply {
    private long reqid;
    private CapacityInformation request;
    
    public ComputeCapacityReply(long id) {
        reqid = id;
        request = null;
    } 

    public ComputeCapacityReply(long reqid, CapacityInformation request) {
        this.reqid = reqid;
        this.request = request;
    }
    
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void setCapacityInfo(CapacityInformation req) {
        request = req;
    }
    
    public CapacityInformation getCapacityInfo() {
        return request;
    }
    
}
