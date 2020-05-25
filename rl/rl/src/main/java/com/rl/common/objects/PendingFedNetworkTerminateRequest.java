/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author ezimbgi
 */
public class PendingFedNetworkTerminateRequest {

    private long reqid;
    private long servid; //service identifiers
    private HashMap<Integer, ArrayList<Long>> wimdomlistMap; //contains domain managing the WIM abstract resources
    private HashMap<Integer, ArrayList<Long>> vimdomlistMap; //contains domain managing the VIM abstract resources
    private HashMap<Integer, ArrayList<Long>> interdomainLinksMap;
    private HashMap<Integer, ArrayList<Long>> fed_interdomainLinksMap;
    private HashMap<Integer, ArrayList<Long>> intraPopLinksMap;
    private HashMap<Integer, ArrayList<Long>> wanLinksMap;
    private HashMap<Integer, ArrayList<Long>> wimPopListMap;
    private HashMap<Integer, ArrayList<Long>> vimPopListMap;
    private HashMap<Integer, ArrayList<String>> wimNetworkTypeMap;
    private HashMap<Integer, ArrayList<String>> vimNetworkTypeMap;
     private HashMap<Integer, ArrayList<Long>> wanResourceIdListMap;
    
     private List<String> netServIdList;
    private List<String> locicalLinkList;

    public PendingFedNetworkTerminateRequest(long reqid, long servid, HashMap<Integer, ArrayList<Long>> wimdomlistMap, HashMap<Integer, ArrayList<Long>> vimdomlistMap, HashMap<Integer, ArrayList<Long>> interdomainLinksMap, HashMap<Integer, ArrayList<Long>> fed_interdomainLinksMap, HashMap<Integer, ArrayList<Long>> intraPopLinksMap, HashMap<Integer, ArrayList<Long>> wanLinksMap, HashMap<Integer, ArrayList<Long>> wimPopListMap, HashMap<Integer, ArrayList<Long>> vimPopListMap, HashMap<Integer, ArrayList<String>> wimNetworkTypeMap, HashMap<Integer, ArrayList<String>> vimNetworkTypeMap, HashMap<Integer, ArrayList<Long>> wanResourceIdListMap, List<String> netServIdList, List<String> locicalLinkList) {
        this.reqid = reqid;
        this.servid = servid;
        this.wimdomlistMap = wimdomlistMap;
        this.vimdomlistMap = vimdomlistMap;
        this.interdomainLinksMap = interdomainLinksMap;
        this.fed_interdomainLinksMap = fed_interdomainLinksMap;
        this.intraPopLinksMap = intraPopLinksMap;
        this.wanLinksMap = wanLinksMap;
        this.wimPopListMap = wimPopListMap;
        this.vimPopListMap = vimPopListMap;
        this.wimNetworkTypeMap = wimNetworkTypeMap;
        this.vimNetworkTypeMap = vimNetworkTypeMap;    
        this.wanResourceIdListMap = wanResourceIdListMap;
        this.netServIdList = netServIdList;
        this.locicalLinkList = locicalLinkList;
    }

    public HashMap<Integer, ArrayList<Long>> getFed_interdomainLinksMap() {
        return fed_interdomainLinksMap;
    }

    public void setFed_interdomainLinksMap(HashMap<Integer, ArrayList<Long>> fed_interdomainLinksMap) {
        this.fed_interdomainLinksMap = fed_interdomainLinksMap;
    }

    
    
    
    
    
    public HashMap<Integer, ArrayList<Long>> getWanResourceIdListMap() {
        return wanResourceIdListMap;
    }

    public void setWanResourceIdListMap(HashMap<Integer, ArrayList<Long>> wanResourceIdListMap) {
        this.wanResourceIdListMap = wanResourceIdListMap;
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

    public HashMap<Integer, ArrayList<Long>> getWimdomlistMap() {
        return wimdomlistMap;
    }

    public void setWimdomlistMap(HashMap<Integer, ArrayList<Long>> wimdomlistMap) {
        this.wimdomlistMap = wimdomlistMap;
    }

    public HashMap<Integer, ArrayList<Long>> getVimdomlistMap() {
        return vimdomlistMap;
    }

    public void setVimdomlistMap(HashMap<Integer, ArrayList<Long>> vimdomlistMap) {
        this.vimdomlistMap = vimdomlistMap;
    }

    public HashMap<Integer, ArrayList<Long>> getInterdomainLinksMap() {
        return interdomainLinksMap;
    }

    public void setInterdomainLinksMap(HashMap<Integer, ArrayList<Long>> interdomainLinksMap) {
        this.interdomainLinksMap = interdomainLinksMap;
    }

    public HashMap<Integer, ArrayList<Long>> getIntraPopLinksMap() {
        return intraPopLinksMap;
    }

    public void setIntraPopLinksMap(HashMap<Integer, ArrayList<Long>> intraPopLinksMap) {
        this.intraPopLinksMap = intraPopLinksMap;
    }

    public HashMap<Integer, ArrayList<Long>> getWanLinksMap() {
        return wanLinksMap;
    }

    public void setWanLinksMap(HashMap<Integer, ArrayList<Long>> wanLinksMap) {
        this.wanLinksMap = wanLinksMap;
    }

    public HashMap<Integer, ArrayList<Long>> getWimPopListMap() {
        return wimPopListMap;
    }

    public void setWimPopListMap(HashMap<Integer, ArrayList<Long>> wimPopListMap) {
        this.wimPopListMap = wimPopListMap;
    }

    public HashMap<Integer, ArrayList<Long>> getVimPopListMap() {
        return vimPopListMap;
    }

    public void setVimPopListMap(HashMap<Integer, ArrayList<Long>> vimPopListMap) {
        this.vimPopListMap = vimPopListMap;
    }

    public HashMap<Integer, ArrayList<String>> getWimNetworkTypeMap() {
        return wimNetworkTypeMap;
    }

    public void setWimNetworkTypeMap(HashMap<Integer, ArrayList<String>> wimNetworkTypeMap) {
        this.wimNetworkTypeMap = wimNetworkTypeMap;
    }

    public HashMap<Integer, ArrayList<String>> getVimNetworkTypeMap() {
        return vimNetworkTypeMap;
    }

    public void setVimNetworkTypeMap(HashMap<Integer, ArrayList<String>> vimNetworkTypeMap) {
        this.vimNetworkTypeMap = vimNetworkTypeMap;
    }

    public List<String> getNetServIdList() {
        return netServIdList;
    }

    public void setNetServIdList(List<String> netServIdList) {
        this.netServIdList = netServIdList;
    }

    public List<String> getLocicalLinkList() {
        return locicalLinkList;
    }

    public void setLocicalLinkList(List<String> locicalLinkList) {
        this.locicalLinkList = locicalLinkList;
    }

    
    
    
    
    
    
    
    
    
}
