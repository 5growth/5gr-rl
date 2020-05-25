/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

import com.rl.extinterface.nbi.swagger.model.QueryNetworkCapacityRequest;



/**
 *
 * @author efabuba
 */
public class NetworkCapacityRequest {
    private long reqid;
    private QueryNetworkCapacityRequest request;
    
    public NetworkCapacityRequest(long id) {
        reqid = id;
        request = null;
    } 
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void setCapacityRequest(QueryNetworkCapacityRequest req) {
        request = req;
    }
    
    public QueryNetworkCapacityRequest getCapacityRequest() {
        return request;
    }
}
