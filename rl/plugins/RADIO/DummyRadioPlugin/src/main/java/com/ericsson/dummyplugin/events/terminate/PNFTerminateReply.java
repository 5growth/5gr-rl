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
public class PNFTerminateReply {
    private long reqid;
    private PNFReply response;

    public PNFTerminateReply(long reqid, PNFReply response) {
        this.reqid = reqid;
        this.response = response;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public PNFReply getResponse() {
        return response;
    }

    public void setResponse(PNFReply response) {
        this.response = response;
    }
    
    
}
