/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.PNFRequest;

/**
 *
 * @author osboxes
 */
public class PNFAllocateRequest {
    private long reqid;
    private PNFRequest request;

    public PNFAllocateRequest(long reqid, PNFRequest request) {
        this.reqid = reqid;
        this.request = request;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public PNFRequest getRequest() {
        return request;
    }

    public void setRequest(PNFRequest request) {
        this.request = request;
    }
}
