/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.MFRequest;

/**
 *
 * @author osboxes
 */
public class MFAllocateRequest {
    private long reqid;
    private MFRequest request;

    public MFAllocateRequest(long reqid, MFRequest request) {
        this.reqid = reqid;
        this.request = request;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public MFRequest getRequest() {
        return request;
    }

    public void setRequest(MFRequest request) {
        this.request = request;
    }
    
    
}
