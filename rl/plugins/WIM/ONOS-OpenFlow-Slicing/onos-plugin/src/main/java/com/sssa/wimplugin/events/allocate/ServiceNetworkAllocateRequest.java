package com.sssa.wimplugin.events.allocate;

import com.sssa.wimplugin.nbi.swagger.model.AllocateParameters;


public class ServiceNetworkAllocateRequest {
    private long reqid;
    private AllocateParameters request; //contains vim computation info 
    
    public ServiceNetworkAllocateRequest() {
        reqid = 0;
        request = null;
    }

    public ServiceNetworkAllocateRequest(long reqid, AllocateParameters request) {
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


    public AllocateParameters getRequest() {
        return request;
    }

    public void setRequest(AllocateParameters request) {
        this.request = request;
    }

}
