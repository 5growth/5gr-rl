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
public class IntraPoPTerminateDbRequest {
    private long reqid;
    private List<String> networklist;

    public IntraPoPTerminateDbRequest(long reqid, List<String> networklist) {
        this.reqid = reqid;
        this.networklist = networklist;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public List<String> getNetworklist() {
        return networklist;
    }

    public void setNetworklist(List<String> networklist) {
        this.networklist = networklist;
    }
    
    
}
