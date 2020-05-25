/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2001;

/**
 *
 * @author osboxes
 */
public class RadioCoverageAreaReply {
    private long reqid;
    private InlineResponse2001 response;

    public RadioCoverageAreaReply(long reqid, InlineResponse2001 response) {
        this.reqid = reqid;
        this.response = response;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public InlineResponse2001 getResponse() {
        return response;
    }

    public void setResponse(InlineResponse2001 response) {
        this.response = response;
    }
    
    
    
}
