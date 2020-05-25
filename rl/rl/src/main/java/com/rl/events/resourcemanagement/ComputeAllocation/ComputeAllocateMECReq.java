/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.extinterface.nbi.swagger.model.MECTrafficServiceCreationRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;

/**
 *
 * @author ezimbgi
 */
public class ComputeAllocateMECReq {
    private long reqid;
    private String servid;
    private long mecdomid;
    private MECTrafficServiceCreationRequest Mecreq;
    private VirtualCompute vmreq;

    public ComputeAllocateMECReq(long reqid, String servid, long mecdomid, MECTrafficServiceCreationRequest Mecreq, VirtualCompute vmreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.mecdomid = mecdomid;
        this.Mecreq = Mecreq;
        this.vmreq = vmreq;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }

    public long getMecdomid() {
        return mecdomid;
    }

    public void setMecdomid(long mecdomid) {
        this.mecdomid = mecdomid;
    }

    public MECTrafficServiceCreationRequest getMecreq() {
        return Mecreq;
    }

    public void setMecreq(MECTrafficServiceCreationRequest Mecreq) {
        this.Mecreq = Mecreq;
    }

    public VirtualCompute getVmreq() {
        return vmreq;
    }

    public void setVmreq(VirtualCompute vmreq) {
        this.vmreq = vmreq;
    }
    
    
}
