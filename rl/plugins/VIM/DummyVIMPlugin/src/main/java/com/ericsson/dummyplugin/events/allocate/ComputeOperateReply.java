/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import com.ericsson.dummyplugin.nbi.swagger.model.VIMVirtualCompute;

/**
 *
 * @author efabuba
 */
public class ComputeOperateReply {
    private long reqid;
    private VIMVirtualCompute reply; 

    public ComputeOperateReply(long reqid, VIMVirtualCompute reply) {
        this.reqid = reqid;
        this.reply = reply;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public VIMVirtualCompute getReply() {
        return reply;
    }

    public void setReply(VIMVirtualCompute reply) {
        this.reply = reply;
    }
    
}
