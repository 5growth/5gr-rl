/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkPathListInner;




public class NetworkAllocateDBQuery {

    private long reqid;
    private String servid; //service identifiers
    //private InterNfviPopConnectivityRequest networkreq; //contains vim computation info
    private LogicalLinkPathListInner networkreq; //contains vim computation info
    private long logicalpath; //selected logical path

    public NetworkAllocateDBQuery(long reqid, String servid, LogicalLinkPathListInner networkreq, long logicalpath) {
        this.reqid = reqid;
        this.servid = servid;
        this.networkreq = networkreq;
        this.logicalpath = logicalpath;
    }

    public LogicalLinkPathListInner getNetworkreq() {
        return networkreq;
    }

    public void setNetworkreq(LogicalLinkPathListInner networkreq) {
        this.networkreq = networkreq;
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

    public long getLogicalpath() {
        return logicalpath;
    }

    public void setLogicalpath(long logicalpath) {
        this.logicalpath = logicalpath;
    }


 
}
