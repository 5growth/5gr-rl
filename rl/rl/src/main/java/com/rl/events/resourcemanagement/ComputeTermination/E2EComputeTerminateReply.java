/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeTermination;

import java.util.List;

public class E2EComputeTerminateReply {

    private long reqid;
    private long servid; //service identifiers
    private List<String> computeIdList;

    public E2EComputeTerminateReply() {
        reqid = 0;
        servid = 0;
        computeIdList = null;
    }

    public E2EComputeTerminateReply(long reqid, long servid, List<String> computeIdList) {
        this.reqid = reqid;
        this.servid = servid;
        this.computeIdList = computeIdList;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public long getServid() {
        return servid;
    }

    public void setServid(long servid) {
        this.servid = servid;
    }

    public List<String> getComputeIdList() {
        return computeIdList;
    }

    public void setComputeIdList(List<String> computeIdList) {
        this.computeIdList = computeIdList;
    }

}
