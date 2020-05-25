/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.AllocateParameters;

/**
 *
 * @author Lenovo
 */
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
