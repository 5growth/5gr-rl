/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse200;

/**
 *
 * @author efabuba
 */
public class MECAbstractionReply {
    private long req;
    private InlineResponse200 resp;

    public MECAbstractionReply(long req, InlineResponse200 resp) {
        this.req = req;
        this.resp = resp;
    }

    public long getReq() {
        return req;
    }

    public void setReq(long req) {
        this.req = req;
    }

    public InlineResponse200 getResp() {
        return resp;
    }

    public void setResp(InlineResponse200 resp) {
        this.resp = resp;
    }
    
    
}
