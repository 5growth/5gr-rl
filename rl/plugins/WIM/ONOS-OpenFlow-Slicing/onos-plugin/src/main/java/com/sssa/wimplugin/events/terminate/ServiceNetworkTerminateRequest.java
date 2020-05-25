package com.sssa.wimplugin.events.terminate;


import java.util.List;

public class ServiceNetworkTerminateRequest {
    private long reqid;

    private String netlist; //contains vim computation info 
    /*
     * TODO: Create object for allocation request of virtual resources according 
     * IFA005
     */
    
    public ServiceNetworkTerminateRequest() {
        reqid = 0;
        netlist = null;
    }

    public ServiceNetworkTerminateRequest(long reqid, String netlist) {
        this.reqid = reqid;
        this.netlist = netlist;
    }
    

    
    //insert set/get methods

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getNetlist() {
        return netlist;
    }

    public void setNetlist(String netlist) {
        this.netlist = netlist;
    }

}
