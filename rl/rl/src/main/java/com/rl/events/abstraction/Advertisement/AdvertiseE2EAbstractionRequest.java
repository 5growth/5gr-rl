/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

/**
 *
 * @author user
 */
public class AdvertiseE2EAbstractionRequest {
    private long reqid;
    
    
    public AdvertiseE2EAbstractionRequest() {
        reqid = 0;
    }
    public AdvertiseE2EAbstractionRequest(long reqval) {
        reqid = reqval;
    }
    
    //get/set methods
    public long getReqId() {
        return reqid;
    }
    
    public void setReqId(long val) {
        reqid = val;
    }
}
