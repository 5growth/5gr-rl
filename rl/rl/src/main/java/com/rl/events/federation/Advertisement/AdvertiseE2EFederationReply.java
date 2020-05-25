/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.federation.Advertisement;

import com.rl.extinterface.nbi.swagger.model.InlineResponse2005;

/**
 *
 * @author ezimbgi
 */
public class AdvertiseE2EFederationReply {
       private long reqid;
    private InlineResponse2005 response;

    
    
    
    
    public AdvertiseE2EFederationReply(long reqid, InlineResponse2005 response) {
        this.reqid = reqid;
        this.response = response;
    }

    
    
    
    
    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public InlineResponse2005 getResponse() {
        return response;
    }

    public void setResponse(InlineResponse2005 response) {
        this.response = response;
    }
    
    
    
    
    
    
}
