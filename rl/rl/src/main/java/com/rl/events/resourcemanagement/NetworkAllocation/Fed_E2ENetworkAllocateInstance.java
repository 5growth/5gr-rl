/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.common.objects.NetworkEndpoints;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import java.util.ArrayList;



public class Fed_E2ENetworkAllocateInstance {

    private long reqid;
    private String servid; //service identifiers 
    private long logicalPathId;
    private ArrayList<Long> wimdomlist; //contains domain managing the WIM abstract resources
    private ArrayList<Long> vimdomlist; //contains domain managing the VIM abstract resources

    private ArrayList<Long> interdomainLinks;
    private ArrayList<String> intraPopLinks;
    private ArrayList<Long> wanLinks;
    private ArrayList<Long> wimPopList;
    private ArrayList<Long> vimPopList;
    private InterNfviPopConnectivityRequest networkRequest;
    private ArrayList<String> wimNetworkType;
    private ArrayList<String> vimNetworkType;
    private ArrayList<NetworkEndpoints> flowRuleEndPointList;
    private boolean abstrSrcNfviPopOfLogicalPathIsFed;

    public Fed_E2ENetworkAllocateInstance() {
        reqid = 0;
        servid = "";
        logicalPathId = 0;
        wimdomlist = new ArrayList();
        vimdomlist = new ArrayList();
        interdomainLinks = new ArrayList();
        intraPopLinks = new ArrayList();
        wanLinks = new ArrayList();
        wimPopList = new ArrayList();
        vimPopList = new ArrayList();
        networkRequest = new InterNfviPopConnectivityRequest();
        wimNetworkType = new ArrayList();
        vimNetworkType = new ArrayList();
           flowRuleEndPointList = new ArrayList();
   abstrSrcNfviPopOfLogicalPathIsFed=false;
        

    }

    public Fed_E2ENetworkAllocateInstance(long reqid, String servid, long logicalPathId, ArrayList<Long> wimdomlist, ArrayList<Long> vimdomlist, ArrayList<Long> interdomainLinks, ArrayList<String> intraPopLinks, ArrayList<Long> wanLinks, ArrayList<Long> wimPopList, ArrayList<Long> vimPopList, InterNfviPopConnectivityRequest networkRequest, ArrayList<String> wimNetworkType, ArrayList<String> vimNetworkType, ArrayList<NetworkEndpoints> flowRuleEndPointList, boolean abstrSrcNfviPopOfLogicalPathIsFed) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalPathId = logicalPathId;
        this.wimdomlist = wimdomlist;
        this.vimdomlist = vimdomlist;
        this.interdomainLinks = interdomainLinks;
        this.intraPopLinks = intraPopLinks;
        this.wanLinks = wanLinks;
        this.wimPopList = wimPopList;
        this.vimPopList = vimPopList;
        this.networkRequest = networkRequest;
        this.wimNetworkType = wimNetworkType;
        this.vimNetworkType = vimNetworkType;
        this.flowRuleEndPointList = flowRuleEndPointList;
         this.abstrSrcNfviPopOfLogicalPathIsFed = abstrSrcNfviPopOfLogicalPathIsFed;
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

    
    
    
    
    
    public ArrayList<String> getWimNetworkType() {
        return wimNetworkType;
    }

    public void setWimNetworkType(ArrayList<String> wimNetworkType) {
        this.wimNetworkType = wimNetworkType;
    }

    public ArrayList<String> getVimNetworkType() {
        return vimNetworkType;
    }

    public void setVimNetworkType(ArrayList<String> vimNetworkType) {
        this.vimNetworkType = vimNetworkType;
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

    public ArrayList<Long> getWimdomlist() {
        return wimdomlist;
    }

    public void setWimdomlist(ArrayList<Long> wimdomlist) {
        this.wimdomlist = wimdomlist;
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

    public ArrayList<Long> getVimPopList() {
        return vimPopList;
    }

    public void setVimPopList(ArrayList<Long> vimPopList) {
        this.vimPopList = vimPopList;
    }

}
