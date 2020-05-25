package com.sssa.wimplugin.events.allocate;

import com.sssa.wimplugin.nbi.swagger.model.AllocateReply;
import com.sssa.wimplugin.sbi.objects_.NetworkResource;
import com.sssa.wimplugin.sbi.objects_.NetworkService;


public class ServiceNetworkAllocateReply {
    private long reqid;
    private AllocateReply request; 
    private boolean allocated;

    public ServiceNetworkAllocateReply(long reqid, AllocateReply request) {
        this.reqid = reqid;
        this.request = request;
    }
    
    public ServiceNetworkAllocateReply(long reqid, AllocateReply request, boolean allocated) {
        this.reqid = reqid;
        this.request = request;
        this.allocated = allocated;
    }

    //insert set/get methods

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }
    
    
    public AllocateReply getRequest() {
        return request;
    }

    public void setRequest(AllocateReply request) {
        this.request = request;
    }

  public boolean getAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }


}
