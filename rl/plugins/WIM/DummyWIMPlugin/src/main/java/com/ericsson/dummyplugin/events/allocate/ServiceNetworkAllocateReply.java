/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.AllocateReply;
import com.ericsson.dummyplugin.sbi.objects.NetworkResource;
import com.ericsson.dummyplugin.sbi.objects.NetworkService;

/**
 *
 * @author Lenovo
 */
public class ServiceNetworkAllocateReply {
    private long reqid;
    private AllocateReply request; 

    public ServiceNetworkAllocateReply(long reqid, AllocateReply request) {
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

    public AllocateReply getRequest() {
        return request;
    }

    public void setRequest(AllocateReply request) {
        this.request = request;
    }

  


}
