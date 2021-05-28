/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2002;

/**
 *
 * @author osboxes
 */
public class PNFlistReply {
    private long req;
    InlineResponse2002 response;

    public PNFlistReply(long req, InlineResponse2002 response) {
        this.req = req;
        this.response = response;
    }

    public long getReq() {
        return req;
    }

    public void setReq(long req) {
        this.req = req;
    }

    public InlineResponse2002 getResponse() {
        return response;
    }

    public void setResponse(InlineResponse2002 response) {
        this.response = response;
    }
    
}
