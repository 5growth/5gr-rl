/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResult;


/**
 *
 * @author Lenovo
 */
public class VirtualNetworkAllocateReply {
    private long reqid;

    private AllocateNetworkResult e2ewimelem; //TODO: Change according IFA005


    
    public VirtualNetworkAllocateReply(long id, AllocateNetworkResult str) {
        reqid = id;
        e2ewimelem = str;
    }

    
    //insert set/get methods
    public long getReqId() {
        return reqid;
    }
    public void setReqId( long reqval) {
        reqid = reqval;
    }
    public AllocateNetworkResult getE2EWIMElem() {
        return e2ewimelem;
    }
    public void setE2EWIMElem(AllocateNetworkResult wimelem) {
        e2ewimelem = wimelem;
    }
}
