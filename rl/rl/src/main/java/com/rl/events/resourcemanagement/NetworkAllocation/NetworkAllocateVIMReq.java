/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;



import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;

public class NetworkAllocateVIMReq {

    private long reqid;
    private String servid; //service identifiers 
    private long logicalPathId;
    private InterNfviPopConnectivityRequest networkRequest; // wimelem renamed 
    private ArrayList<Long> vimdomlist; //contains domain managing the VIM abstract resources
    private ArrayList<Long> interdomainLinks;
    private ArrayList<String> intraPopLinks;
    private ArrayList<Long> vimPopList;
    private ArrayList<String> vimNetworkType;
    private ArrayList<VirtualNetwork> wimnetlist;
    private ArrayList<Long> vlans;

    public NetworkAllocateVIMReq() {
        reqid = 0;
        servid = "";
    }

    public NetworkAllocateVIMReq(long reqid, String servid, long logicalPathId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> vimdomlist, ArrayList<Long> interdomainLinks, ArrayList<String> intraPopLinks, ArrayList<Long> vimPopList, ArrayList<String> vimNetworkType, ArrayList<VirtualNetwork> wimnetlist, ArrayList<Long> vlans) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalPathId = logicalPathId;
        this.networkRequest = networkRequest;
        this.vimdomlist = vimdomlist;
        this.interdomainLinks = interdomainLinks;
        this.intraPopLinks = intraPopLinks;
        this.vimPopList = vimPopList;
        this.vimNetworkType = vimNetworkType;
        this.wimnetlist = wimnetlist;
        this.vlans = vlans;
    }

    public ArrayList<Long> getVlans() {
        return vlans;
    }

    public void setVlans(ArrayList<Long> vlans) {
        this.vlans = vlans;
    }

    
    //insert set/get methods
    public long getReqId() {
        return reqid;
    }

    public void setReqId(long reqval) {
        reqid = reqval;
    }

    public String getServId() {
        return servid;
    }

    public void setServId(String servval) {
        servid = servval;
    }

    public long getLogicalPathId() {
        return logicalPathId;
    }

    public void setLogicalPathId(long logicalPathId) {
        this.logicalPathId = logicalPathId;
    }

    public InterNfviPopConnectivityRequest getNetworkRequest() {
        return networkRequest;
    }

    public void setNetworkRequest(InterNfviPopConnectivityRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public ArrayList<Long> getVimdomlist() {
        return vimdomlist;
    }

    public void setVimdomlist(ArrayList<Long> vimdomlist) {
        this.vimdomlist = vimdomlist;
    }

    public ArrayList<Long> getInterdomainLinks() {
        return interdomainLinks;
    }

    public void setInterdomainLinks(ArrayList<Long> interdomainLinks) {
        this.interdomainLinks = interdomainLinks;
    }

    public ArrayList<String> getIntraPopLinks() {
        return intraPopLinks;
    }

    public void setIntraPopLinks(ArrayList<String> intraPopLinks) {
        this.intraPopLinks = intraPopLinks;
    }

    public ArrayList<Long> getVimPopList() {
        return vimPopList;
    }

    public void setVimPopList(ArrayList<Long> vimPopList) {
        this.vimPopList = vimPopList;
    }

    public ArrayList<String> getVimNetworkType() {
        return vimNetworkType;
    }

    public void setVimNetworkType(ArrayList<String> vimNetworkType) {
        this.vimNetworkType = vimNetworkType;
    }

    public ArrayList<VirtualNetwork> getWimnetlist() {
        return wimnetlist;
    }

    public void setWimnetlist(ArrayList<VirtualNetwork> vimnetlist) {
        this.wimnetlist = vimnetlist;
    }

}
