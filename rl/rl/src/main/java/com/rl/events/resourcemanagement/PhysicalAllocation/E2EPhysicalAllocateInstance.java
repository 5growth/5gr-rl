/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalAllocation;

import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import java.util.List;

/**
 *
 * @author osboxes
 */
public class E2EPhysicalAllocateInstance {
    private long reqid;
    private String servid; //service identifiers 
    private long domid; //id of vim domain to contact
    //private long computeservid;
    //private VIMAbstractElem vimelem;
    private long nfvipopid;
    private PNFRequest request;
    private List<String> mflist;

    public E2EPhysicalAllocateInstance(long reqid, String servid, long domid, long nfvipopid, PNFRequest request, List<String> mflist) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.nfvipopid = nfvipopid;
        this.request = request;
        this.mflist = mflist;
    }

    public List<String> getMflist() {
        return mflist;
    }

    public void setMflist(List<String> mflist) {
        this.mflist = mflist;
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

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public PNFRequest getRequest() {
        return request;
    }

    public void setRequest(PNFRequest request) {
        this.request = request;
    }
    
    
}
