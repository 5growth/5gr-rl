/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.placement;

import com.rl.common.objects.ComputeAllocateElem;

/**
 *
 * @author efabuba
 */
public class PAComputeRequest {
    private long reqid;
    private String servid;
    private long domid;
    private long nfvipopid;    
    private ComputeAllocateElem compel;

    public PAComputeRequest(long reqid, String zoneid, long domid, long nfvipopid, ComputeAllocateElem compel) {
        this.reqid = reqid;
        this.servid = zoneid;
        this.nfvipopid = nfvipopid;
        this.domid = domid;
        this.compel = compel;
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

    public void setServid(String zoneid) {
        this.servid = zoneid;
    }

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public ComputeAllocateElem getCompel() {
        return compel;
    }

    public void setCompel(ComputeAllocateElem compel) {
        this.compel = compel;
    }
    
    
}
