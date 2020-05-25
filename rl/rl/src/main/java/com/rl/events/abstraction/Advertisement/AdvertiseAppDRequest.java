/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

/**
 *
 * @author efabuba
 */
public class AdvertiseAppDRequest {
    private long reqid;

    public AdvertiseAppDRequest(long reqid) {
        this.reqid = reqid;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }
    
}
