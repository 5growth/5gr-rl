/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;



public class Fed_NetworkAllocateDBQueryOutcome {

    private long reqid;
    private String servid; //service identifiers 
    private boolean outcome; //result of the request
    private long logicalPathId;
    private InterNfviPopConnectivityRequest networkRequest;
    private ArrayList<Long> wimdomlist;
    private ArrayList<Long> interdomainLinks;
  
    private ArrayList<Long> wanLinks;
    private ArrayList<Long> wimPopList;
    private ArrayList<String> wimNetworkType;
    private ArrayList<VirtualNetwork> wimnetlist;
     private boolean abstrSrcNfviPopOfLogicalPathIsFed;  
 

    public Fed_NetworkAllocateDBQueryOutcome(long reqid, String servid, boolean outcome, long logicalPathId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> wimdomlist,  ArrayList<Long> interdomainLinks, ArrayList<Long> wanLinks, ArrayList<Long> wimPopList, ArrayList<String> wimNetworkType, boolean abstrSrcNfviPopOfLogicalPathIsFed, ArrayList<VirtualNetwork> wimnetlist) {
        this.reqid = reqid;
        this.servid = servid;
        this.outcome = outcome;
        this.logicalPathId = logicalPathId;
        this.networkRequest = networkRequest;
        this.wimdomlist = wimdomlist; 
        this.interdomainLinks = interdomainLinks;    
       
        this.wanLinks = wanLinks;
        this.wimPopList = wimPopList;    
        this.wimNetworkType = wimNetworkType;
        this.abstrSrcNfviPopOfLogicalPathIsFed = abstrSrcNfviPopOfLogicalPathIsFed;
        this.wimnetlist = wimnetlist;
        
    }

    public boolean isAbstrSrcNfviPopOfLogicalPathIsFed() {
        return abstrSrcNfviPopOfLogicalPathIsFed;
    }

    public void setAbstrSrcNfviPopOfLogicalPathIsFed(boolean abstrSrcNfviPopOfLogicalPathIsFed) {
        this.abstrSrcNfviPopOfLogicalPathIsFed = abstrSrcNfviPopOfLogicalPathIsFed;
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

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
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



    public ArrayList<String> getWimNetworkType() {
        return wimNetworkType;
    }

    public void setWimNetworkType(ArrayList<String> wimNetworkType) {
        this.wimNetworkType = wimNetworkType;
    }


    public ArrayList<VirtualNetwork> getWimnetlist() {
        return wimnetlist;
    }

    public void setWimnetlist(ArrayList<VirtualNetwork> wimnetlist) {
        this.wimnetlist = wimnetlist;
    }


}
