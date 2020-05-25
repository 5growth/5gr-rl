/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkTermination;

import java.util.List;

/**
 *
 * @author efabuba
 */
public class IntraPoPTerminateDbReply {
    private long reqid;
    private List<String> netreslist;

    public IntraPoPTerminateDbReply(long reqid, List<String> netreslist) {
        this.reqid = reqid;
        this.netreslist = netreslist;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public List<String> getNetreslist() {
        return netreslist;
    }

    public void setNetreslist(List<String> netreslist) {
        this.netreslist = netreslist;
    }
    
    
}
