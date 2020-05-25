/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;


/**
 *
 * @author efabuba
 */
public class IntraPoPAllocateDbReply {
    private long reqid;
    private String servid;
    private long nfvipopid;
    private AllocateNetworkResult intrapoprep;

    public IntraPoPAllocateDbReply(long reqid, String servid, long nfvipopid, AllocateNetworkResult intrapoprep) {
        this.reqid = reqid;
        this.servid = servid;
        this.nfvipopid = nfvipopid;
        this.intrapoprep = intrapoprep;
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

    public AllocateNetworkResult getIntrapoprep() {
        return intrapoprep;
    }

    public void setIntrapoprep(AllocateNetworkResult intrapoprep) {
        this.intrapoprep = intrapoprep;
    }
    
    
}
