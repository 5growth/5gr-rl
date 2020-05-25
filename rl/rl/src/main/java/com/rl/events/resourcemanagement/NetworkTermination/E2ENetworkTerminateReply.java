/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkTermination;


import java.util.List;

public class E2ENetworkTerminateReply {

    private long reqid;
    private long servid; //service identifiers
    private List<String> netServIdList; //contains vim computation info 

    public E2ENetworkTerminateReply() {
        reqid = 0;
        servid = 0;
        netServIdList = null;
    }

    public E2ENetworkTerminateReply(long reqid, long servid, List<String> netServIdList) {
        this.reqid = reqid;
        this.servid = servid;
        this.netServIdList = netServIdList;
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

}
