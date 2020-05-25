/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

import com.rl.extinterface.nbi.swagger.model.QueryComputeCapacityRequest;



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
