/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.VirtualCompute;
/**
 *
 * @author efabuba
 */
public class ComputeOperateVIMReply {
    private long reqid;
    private VirtualCompute response;
    private long errcode;
    private String errmsg;

    public ComputeOperateVIMReply(long reqid, VirtualCompute response, long errcode, String errmsg) {
        this.reqid = reqid;
        this.response = response;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }



    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public VirtualCompute getResponse() {
        return response;
    }

    public void setResponse(VirtualCompute response) {
        this.response = response;
    }

    public long getErrcode() {
        return errcode;
    }

    public void setErrcode(long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    
        
}
