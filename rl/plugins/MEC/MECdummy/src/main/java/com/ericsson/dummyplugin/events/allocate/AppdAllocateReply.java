/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.MECTrafficServiceCreationResponse;


/**
 *
 * @author Lenovo
 */
public class AppdAllocateReply {
    private long reqid;
    private MECTrafficServiceCreationResponse request; 

    public AppdAllocateReply(long reqid, MECTrafficServiceCreationResponse request) {
        this.reqid = reqid;
        this.request = request;
    }

    



    
    
    //insert set/get methods

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public MECTrafficServiceCreationResponse getRequest() {
        return request;
    }

    public void setRequest(MECTrafficServiceCreationResponse request) {
        this.request = request;
    }

  


}
