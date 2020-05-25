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
public class NetworkResourceInformationRequest {
    private long reqid;
    private String filter;
    
    public NetworkResourceInformationRequest(long id, String filt) {
        reqid = id;
        filter = filt;
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
}
