/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

/**
 *
 * @author efabuba
 */
public class FreeVlanRequest {
    private long reqid;

    public FreeVlanRequest(long reqid) {
        this.reqid = reqid;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }
    
}
