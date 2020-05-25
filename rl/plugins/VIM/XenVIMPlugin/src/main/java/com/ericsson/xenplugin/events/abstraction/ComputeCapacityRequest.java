/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.abstraction;

import com.ericsson.xenplugin.nbi.swagger.model.QueryComputeCapacityRequest;









/**
 *
 * @author efabuba
 */
public class ComputeCapacityRequest {
    private long reqid;
    private QueryComputeCapacityRequest request;
    
    public ComputeCapacityRequest(long id) {
        reqid = id;
        request = null;
    } 

    public ComputeCapacityRequest(long reqid, QueryComputeCapacityRequest request) {
        this.reqid = reqid;
        this.request = request;
    }
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void setCapacityRequest(QueryComputeCapacityRequest req) {
        request = req;
    }
    
    public QueryComputeCapacityRequest getCapacityRequest() {
        return request;
    }
}
