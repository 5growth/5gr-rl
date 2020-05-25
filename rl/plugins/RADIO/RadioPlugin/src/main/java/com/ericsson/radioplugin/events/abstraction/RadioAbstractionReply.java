/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.radioplugin.events.abstraction;

import com.ericsson.radioplugin.nbi.swagger.model.InlineResponse2001;

/**
 *
 * @author efabuba
 */
public class RadioAbstractionReply {
    private long req;
    private InlineResponse2001 resp;

    public RadioAbstractionReply(long req, InlineResponse2001 resp) {
        this.req = req;
        this.resp = resp;
    }

    public long getReq() {
        return req;
    }

    public void setReq(long req) {
        this.req = req;
    }

    public InlineResponse2001 getResp() {
        return resp;
    }

    public void setResp(InlineResponse2001 resp) {
        this.resp = resp;
    }
    
    
}
