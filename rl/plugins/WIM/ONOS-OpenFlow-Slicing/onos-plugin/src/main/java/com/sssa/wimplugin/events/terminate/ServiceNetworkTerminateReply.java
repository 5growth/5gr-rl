
package com.sssa.wimplugin.events.terminate;


import com.sssa.wimplugin.nbi.swagger.model.NetworkIds;
import java.util.List;

public class ServiceNetworkTerminateReply {
    private long reqid;
    private List<NetworkIds> netlist; //contains vim computation info 

    
    public ServiceNetworkTerminateReply() {
        reqid = 0;
        netlist = null;
    }


    public ServiceNetworkTerminateReply(long reqid, List<NetworkIds> netlist) {
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

    public List<NetworkIds> getNetlist() {
        return netlist;
    }

    public void setNetlist(List<NetworkIds> netlist) {
        this.netlist = netlist;
    }


}
