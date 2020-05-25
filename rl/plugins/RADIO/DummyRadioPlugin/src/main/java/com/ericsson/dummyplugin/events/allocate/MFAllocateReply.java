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
public class MFAllocateReply {
    private long reqid;
    private MFRequest response;

    public MFAllocateReply(long reqid, MFRequest response) {
        this.reqid = reqid;
        this.response = response;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public MFRequest getResponse() {
        return response;
    }

    public void setResponse(MFRequest response) {
        this.response = response;
    }
    
    
}
