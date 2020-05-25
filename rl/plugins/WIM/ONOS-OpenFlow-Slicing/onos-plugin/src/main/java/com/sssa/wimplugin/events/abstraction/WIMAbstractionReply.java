package com.sssa.wimplugin.events.abstraction;

import com.sssa.wimplugin.nbi.swagger.model.InlineResponse200;


public class WIMAbstractionReply {
    private long req;
    private InlineResponse200 resp;

    public WIMAbstractionReply(long req, InlineResponse200 resp) {
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
