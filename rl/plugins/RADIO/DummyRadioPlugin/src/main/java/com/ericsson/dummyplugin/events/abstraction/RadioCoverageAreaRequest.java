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
public class RadioCoverageAreaRequest {
    private long req;

    public RadioCoverageAreaRequest(long req) {
        this.req = req;
    }

    public long getReq() {
        return req;
    }

    public void setReq(long req) {
        this.req = req;
    }
    
}
