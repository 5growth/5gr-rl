/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.AllocateNetworkRequest;

/**
 *
 * @author efabuba
 */
public class IntraPoPAllocateDbRequest {
    private long reqid;
    private String servid;
    private long nfvipopid;
    private AllocateNetworkRequest intrapopreq;

    public IntraPoPAllocateDbRequest(long reqid, String servid, long nfvipopid, AllocateNetworkRequest intrapopreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.nfvipopid = nfvipopid;
        this.intrapopreq = intrapopreq;
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

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public AllocateNetworkRequest getIntrapopreq() {
        return intrapopreq;
    }

    public void setIntrapopreq(AllocateNetworkRequest intrapopreq) {
        this.intrapopreq = intrapopreq;
    }
    
    
}
