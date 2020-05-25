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
public class ComputeOperateDBReq {
    private long reqid;
    private OperateComputeRequest operateinfo;

    public ComputeOperateDBReq(long reqid, OperateComputeRequest operateinfo) {
        this.reqid = reqid;
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
    
}
