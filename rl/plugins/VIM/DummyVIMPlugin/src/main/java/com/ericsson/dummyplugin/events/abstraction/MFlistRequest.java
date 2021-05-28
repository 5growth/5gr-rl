/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

/**
 *
 * @author osboxes
 */
public class MFlistRequest {
    private long reqid;

    public MFlistRequest(long reqid) {
        this.reqid = reqid;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }
    
    
    
}
