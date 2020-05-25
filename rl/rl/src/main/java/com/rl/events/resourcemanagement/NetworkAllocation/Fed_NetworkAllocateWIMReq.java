/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;


import com.rl.common.objects.NetworkEndpoints;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import java.util.ArrayList;

public class Fed_NetworkAllocateWIMReq {

    private long reqid;
    private String servid; //service identifiers
    private long logicalPathId;
    private InterNfviPopConnectivityRequest networkRequest;
    private ArrayList<Long> wimdomlist;
    private ArrayList<Long> interdomainLinks;
    private ArrayList<String> intraPopLinks;
    private ArrayList<Long> wanLinks;
    private ArrayList<Long> wimPopList;
    private ArrayList<String> wimNetworkType;
    private ArrayList<NetworkEndpoints> flowRuleEndPointList;
     private boolean abstrSrcNfviPopOfLogicalPathIsFed;
    private Integer pendingFedNetAllocReqId; 
    
    //private ArrayList<Long> vlans;
    //private ArrayList<String> ips;

    public Fed_NetworkAllocateWIMReq() {
        reqid = 0;
        servid = "";
        logicalPathId = 0;
        networkRequest = new InterNfviPopConnectivityRequest();
        wimdomlist = new ArrayList();
        interdomainLinks = new ArrayList();
        intraPopLinks = new ArrayList();
        wanLinks = new ArrayList();
        wimPopList = new ArrayList();
        wimNetworkType = new ArrayList();
        flowRuleEndPointList= new ArrayList();
        abstrSrcNfviPopOfLogicalPathIsFed=false;
        pendingFedNetAllocReqId=null;
    }

    public Fed_NetworkAllocateWIMReq(long reqid, String servid, long logicalPathId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> wimdomlist, ArrayList<Long> interdomainLinks, ArrayList<String> intraPopLinks, ArrayList<Long> wanLinks, ArrayList<Long> wimPopList, ArrayList<String> wimNetworkType, ArrayList<NetworkEndpoints> flowRuleEndPointList, boolean abstrSrcNfviPopOfLogicalPathIsFed, Integer pendingFedNetAllocReqId) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalPathId = logicalPathId;
        this.networkRequest = networkRequest;
        this.wimdomlist = wimdomlist;
        this.interdomainLinks = interdomainLinks;
        this.intraPopLinks = intraPopLinks;
        this.wanLinks = wanLinks;
        this.wimPopList = wimPopList;
        this.wimNetworkType = wimNetworkType;
            this.flowRuleEndPointList = flowRuleEndPointList;
         this.abstrSrcNfviPopOfLogicalPathIsFed = abstrSrcNfviPopOfLogicalPathIsFed;
         this.pendingFedNetAllocReqId= pendingFedNetAllocReqId;
    }

    public void setPendingFedNetAllocReqId(Integer pendingFedNetAllocReqId) {
        this.pendingFedNetAllocReqId = pendingFedNetAllocReqId;
    }

    public Integer getPendingFedNetAllocReqId() {
        return pendingFedNetAllocReqId;
    }

    
    
    
    public ArrayList<String> getIntraPopLinks() {
        return intraPopLinks;
    }

    public void setIntraPopLinks(ArrayList<String> intraPopLinks) {
        this.intraPopLinks = intraPopLinks;
    }

    
    
    
    
    
    public ArrayList<NetworkEndpoints> getFlowRuleEndPointList() {
        return flowRuleEndPointList;
    }

    public void setFlowRuleEndPointList(ArrayList<NetworkEndpoints> flowRuleEndPointList) {
        this.flowRuleEndPointList = flowRuleEndPointList;
    }

    public boolean isAbstrSrcNfviPopOfLogicalPathIsFed() {
        return abstrSrcNfviPopOfLogicalPathIsFed;
    }

    public void setAbstrSrcNfviPopOfLogicalPathIsFed(boolean abstrSrcNfviPopOfLogicalPathIsFed) {
        this.abstrSrcNfviPopOfLogicalPathIsFed = abstrSrcNfviPopOfLogicalPathIsFed;
    }

    
    
    
    
    
//    public ArrayList<String> getIps() {
//        return ips;
//    }
//
//    public void setIps(ArrayList<String> ips) {
//        this.ips = ips;
//    }

   
//
//    public ArrayList<Long> getVlans() {
//        return vlans;
//    }
//
//    public void setVlans(ArrayList<Long> vlans) {
//        this.vlans = vlans;
//    }

    public ArrayList<String> getWimNetworkType() {
        return wimNetworkType;
    }

    public void setWimNetworkType(ArrayList<String> wimNetworkType) {
        this.wimNetworkType = wimNetworkType;
    }

    public long getLogicalPathId() {
        return logicalPathId;
    }

    public void setLogicalPathId(long logicalPathId) {
        this.logicalPathId = logicalPathId;
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
    public InterNfviPopConnectivityRequest getNetworkRequest() {
        return networkRequest;
    }

    public void setNetworkRequest(InterNfviPopConnectivityRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public ArrayList<Long> getWimdomlist() {
        return wimdomlist;
    }

    public void setWimdomlist(ArrayList<Long> wimdomlist) {
        this.wimdomlist = wimdomlist;
    }

    public ArrayList<Long> getInterdomainLinks() {
        return interdomainLinks;
    }

    public void setInterdomainLinks(ArrayList<Long> interdomainLinks) {
        this.interdomainLinks = interdomainLinks;
    }

    public ArrayList<Long> getWanLinks() {
        return wanLinks;
    }

    public void setWanLinks(ArrayList<Long> wanLinks) {
        this.wanLinks = wanLinks;
    }

    public ArrayList<Long> getWimPopList() {
        return wimPopList;
    }

    public void setWimPopList(ArrayList<Long> wimPopList) {
        this.wimPopList = wimPopList;
    }
    
}
