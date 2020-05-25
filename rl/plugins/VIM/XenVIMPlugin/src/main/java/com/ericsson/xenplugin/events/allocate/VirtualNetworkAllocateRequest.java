/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.allocate;

import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkRequest;





/**
 *
 * @author Lenovo
 */
public class VirtualNetworkAllocateRequest {
    private long reqid;
    private AllocateNetworkRequest e2ewimelem; //contains vim computation info 

    public VirtualNetworkAllocateRequest(long reqid, AllocateNetworkRequest e2ewimelem) {
        this.reqid = reqid;
        this.e2ewimelem = e2ewimelem;
    }

    //insert set/get methods

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public AllocateNetworkRequest getE2ewimelem() {
        return e2ewimelem;
    }

    public void setE2ewimelem(AllocateNetworkRequest e2ewimelem) {
        this.e2ewimelem = e2ewimelem;
    }

}
