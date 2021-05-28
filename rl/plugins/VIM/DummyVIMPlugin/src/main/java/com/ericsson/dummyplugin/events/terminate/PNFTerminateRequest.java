/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.terminate;

import com.ericsson.dummyplugin.nbi.swagger.model.PNFReply;

/**
 *
 * @author osboxes
 */
public class PNFTerminateRequest {
    private long reqid;
    private PNFReply request;

    public PNFTerminateRequest(long reqid, PNFReply request) {
        this.reqid = reqid;
        this.request = request;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public PNFReply getRequest() {
        return request;
    }

    public void setRequest(PNFReply request) {
        this.request = request;
    }
    
    
}
