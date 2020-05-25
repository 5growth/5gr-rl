/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;


import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;

/**
 *
 * @author ezimbgi
 */
public class PendingFedNetworkAllocateRequest {

    private long reqid;
    private String servid; //service identifiers
    private long logicalLinkId;
    private InterNfviPopConnectivityRequest networkRequest; // wimelem renamed 
    private ArrayList<Long> wimdomlist;
    private ArrayList<Long> vimdomlist; //contains domain managing the VIM abstract resources
    private ArrayList<Long> interdomainLinks;

    private ArrayList<String> intraPopLinks;
    private ArrayList<Long> wanLinks;
    private ArrayList<Long> wimPopList;
    private ArrayList<Long> vimPopList;
    private ArrayList<String> wimNetworkType;
    private ArrayList<String> vimNetworkType;
    private ArrayList<NetworkEndpoints> flowRuleEndPointList;
    private boolean abstrSrcNfviPopOfLogicalPathIsFed;
  
   

    public PendingFedNetworkAllocateRequest(long reqid, String servid, long logicalLinkId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> wimdomlist, ArrayList<Long> vimdomlist, ArrayList<Long> interdomainLinks, ArrayList<String> intraPopLinks, ArrayList<Long> wanLinks, ArrayList<Long> wimPopList, ArrayList<Long> vimPopList, ArrayList<String> wimNetworkType, ArrayList<String> vimNetworkType, ArrayList<NetworkEndpoints> flowRuleEndPointList, boolean abstrSrcNfviPopOfLogicalPathIsFed) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalLinkId = logicalLinkId;
        this.networkRequest = networkRequest;
        this.wimdomlist = wimdomlist;
        this.vimdomlist = vimdomlist;
        this.interdomainLinks = interdomainLinks;
    
        this.intraPopLinks = intraPopLinks;
        this.wanLinks = wanLinks;
        this.wimPopList = wimPopList;
        this.vimPopList = vimPopList;
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

    public long getLogicalLinkId() {
        return logicalLinkId;
    }

    public void setLogicalLinkId(long logicalLinkId) {
        this.logicalLinkId = logicalLinkId;
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

 
    
    
    
    
    
    

}
