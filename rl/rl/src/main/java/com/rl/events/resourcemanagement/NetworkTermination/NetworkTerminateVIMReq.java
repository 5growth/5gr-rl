/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkTermination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkTerminateVIMReq {

    private long reqid;
    private long servid; //service identifiers 
    private HashMap<Integer, ArrayList<Long>> vimdomlistMap; //contains domain managing the VIM abstract resources
    private HashMap<Integer, ArrayList<Long>> interdomainLinksMap;
    private HashMap<Integer, ArrayList<String>> intraPopLinksMap;
    private HashMap<Integer, ArrayList<Long>> vimPopListMap;
    private HashMap<Integer, ArrayList<String>> vimNetworkTypeMap;
    private List<String> logicalPathList;

    public NetworkTerminateVIMReq() {
        reqid = 0;
        servid = 0;
        interdomainLinksMap = new HashMap();
        intraPopLinksMap = new HashMap();
        vimPopListMap = new HashMap();
        vimNetworkTypeMap = new HashMap();
        logicalPathList = new ArrayList();
    }

    public NetworkTerminateVIMReq(long reqid, long servid, HashMap<Integer, ArrayList<Long>> vimdomlistMap, HashMap<Integer, ArrayList<Long>> interdomainLinksMap, HashMap<Integer, ArrayList<String>> intraPopLinksMap, HashMap<Integer, ArrayList<Long>> vimPopListMap, HashMap<Integer, ArrayList<String>> vimNetworkTypeMap, List<String> logicalPathList) {
        this.reqid = reqid;
        this.servid = servid;
        this.vimdomlistMap = vimdomlistMap;
        this.interdomainLinksMap = interdomainLinksMap;
        this.intraPopLinksMap = intraPopLinksMap;
        this.vimPopListMap = vimPopListMap;
        this.vimNetworkTypeMap = vimNetworkTypeMap;
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

    public HashMap<Integer, ArrayList<String>> getIntraPopLinksMap() {
        return intraPopLinksMap;
    }

    public void setIntraPopLinksMap(HashMap<Integer, ArrayList<String>> intraPopLinksMap) {
        this.intraPopLinksMap = intraPopLinksMap;
    }

    public HashMap<Integer, ArrayList<Long>> getVimPopListMap() {
        return vimPopListMap;
    }

    public void setVimPopListMap(HashMap<Integer, ArrayList<Long>> vimPopListMap) {
        this.vimPopListMap = vimPopListMap;
    }

    public HashMap<Integer, ArrayList<String>> getVimNetworkTypeMap() {
        return vimNetworkTypeMap;
    }

    public void setVimNetworkTypeMap(HashMap<Integer, ArrayList<String>> vimNetworkTypeMap) {
        this.vimNetworkTypeMap = vimNetworkTypeMap;
    }

    public List<String> getLogicalPathList() {
        return logicalPathList;
    }

    public void setLogicalPathList(List<String> logicalPathList) {
        this.logicalPathList = logicalPathList;
    }

}
