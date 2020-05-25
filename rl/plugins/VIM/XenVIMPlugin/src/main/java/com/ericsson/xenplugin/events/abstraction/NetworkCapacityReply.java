/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.abstraction;

import com.mtp.extinterface.nbi.swagger.model.CapacityInformation;



/**
 *
 * @author efabuba
 */
public class NetworkCapacityReply {
    private long reqid;
    private CapacityInformation request;
    
    public NetworkCapacityReply(long id) {
        reqid = id;
        request = null;
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
