/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.allocate;

import com.ericsson.xenplugin.nbi.swagger.model.VIMVirtualCompute;



/**
 *
 * @author Lenovo
 */
public class ComputeAllocateReply {
    private long reqid;
    private VIMVirtualCompute reply; //TODO: Change according IFA005

    /*
     * TODO: Create object for allocation request of virtual resources according 
     * IFA005
     */
    
    public ComputeAllocateReply() {
        reqid = 0;
        reply = null;
    }
    
    public ComputeAllocateReply(long id, VIMVirtualCompute str) {
        reqid = id;

        reply = str;
    }

    
    //insert set/get methods
    public long getReqId() {
        return reqid;
    }
    public void setReqId( long reqval) {
        reqid = reqval;
    }
    public VIMVirtualCompute getReply() {
        return reply;
    }
    public void setReply(VIMVirtualCompute reply) {
        this.reply = reply;
    }
}
