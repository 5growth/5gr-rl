/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkTermination;


import java.util.List;

public class E2ENetworkTerminateInstanceOutcome {

    private long reqid;
    private long servid; //service identifiers 

    private List<String> netServIdList;
    private List<String> logicalPathList;

    public E2ENetworkTerminateInstanceOutcome() {
        reqid = 0;
        servid = 0;
        netServIdList = null;
        logicalPathList = null;
    }

    public E2ENetworkTerminateInstanceOutcome(long reqid, long servid, List<String> netServIdList, List<String> logicalPathList) {
        this.reqid = reqid;
        this.servid = servid;
        this.netServIdList = netServIdList;
        this.logicalPathList = logicalPathList;
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

    public List<String> getNetServIdList() {
        return netServIdList;
    }

    public void setNetServIdList(List<String> netServIdList) {
        this.netServIdList = netServIdList;
    }

    public List<String> getLogicalPathList() {
        return logicalPathList;
    }

    public void setLogicalPathList(List<String> logicalPathList) {
        this.logicalPathList = logicalPathList;
    }

    

}
