/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.abstraction;

/**
 *
 * @author efabuba
 */
public class NfviPopAbstractionRequest {
    private long reqid;
    
    public NfviPopAbstractionRequest(long id) {
        reqid = id;
    }

    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
}
