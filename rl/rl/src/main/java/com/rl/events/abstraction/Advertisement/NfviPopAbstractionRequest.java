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
public class NfviPopAbstractionRequest {
    private long reqid;
    private String filter;
    private boolean compute_flag;
    private boolean network_flag;
    
    public NfviPopAbstractionRequest(long id, String filter_str, boolean cflag, boolean nflag) {
        reqid = id;
        filter = filter_str;
        compute_flag = cflag;
        network_flag = nflag;
    } 
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void setFilter(String id) {
        filter = id;
    }
    
    public String getFilter() {
        return filter;
    }
    
    public void setComputeFlag(boolean id) {
        compute_flag = id;
    }
    
    public boolean getComputeFlag() {
        return compute_flag;
    }
    
    public void setNetworkFlag(boolean id) {
        network_flag = id;
    }
    
    public boolean getNetworkFlag() {
        return network_flag;
    }
}
