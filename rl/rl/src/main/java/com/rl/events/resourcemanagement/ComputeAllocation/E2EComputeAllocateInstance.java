/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

import com.rl.common.objects.ComputeAllocateElem;
import java.util.List;

public class E2EComputeAllocateInstance {

    private long reqid;
    private String servid; //service identifiers 
    private long domid; //id of vim domain to contact
    //private long computeservid;
    //private VIMAbstractElem vimelem;
    private long nfvipopid;
    private List<String> mflist;
    private ComputeAllocateElem compAllocElem;

    public E2EComputeAllocateInstance(long reqid, String servid, long domid, long nfvipopid, List<String> mflist, ComputeAllocateElem compAllocElem) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.nfvipopid = nfvipopid;
        this.mflist = mflist;
        this.compAllocElem = compAllocElem;
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

    public ComputeAllocateElem getCompAllocElem() {
        return compAllocElem;
    }

    public void setCompAllocElem(ComputeAllocateElem compAllocElem) {
        this.compAllocElem = compAllocElem;
    }

   
}
