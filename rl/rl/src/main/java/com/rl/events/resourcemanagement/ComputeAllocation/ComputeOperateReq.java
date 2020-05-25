/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.OperateComputeRequest;


/**
 *
 * @author efabuba
 */
public class ComputeOperateReq {
    private long reqid;
    private long domid;
    private OperateComputeRequest operateinfo;

    public ComputeOperateReq(long reqid, long domid, OperateComputeRequest operateinfo) {
        this.reqid = reqid;
        this.domid = domid;
        this.operateinfo = operateinfo;
    }


    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public OperateComputeRequest getOperateinfo() {
        return operateinfo;
    }

    public void setOperateinfo(OperateComputeRequest operateinfo) {
        this.operateinfo = operateinfo;
    }

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }
    
}
