/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

/**
 *
 * @author efabuba
 */
public class ComputeZoneRequest {
    private long reqid;
    private String filter;

    public ComputeZoneRequest(long reqid, String filter) {
        this.reqid = reqid;
        this.filter = filter;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
    
    
}
