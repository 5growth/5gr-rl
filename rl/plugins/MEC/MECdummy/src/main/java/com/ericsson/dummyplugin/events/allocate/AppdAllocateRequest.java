/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;


import com.ericsson.dummyplugin.nbi.swagger.model.MECTrafficServiceCreationRequest;

/**
 *
 * @author Lenovo
 */
public class AppdAllocateRequest {
    private long reqid;
    private MECTrafficServiceCreationRequest request; //contains vim computation info 
    
    public AppdAllocateRequest() {
        reqid = 0;
        request = null;
    }

    public AppdAllocateRequest(long reqid, MECTrafficServiceCreationRequest request) {
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


    public MECTrafficServiceCreationRequest getRequest() {
        return request;
    }

    public void setRequest(MECTrafficServiceCreationRequest request) {
        this.request = request;
    }

}
