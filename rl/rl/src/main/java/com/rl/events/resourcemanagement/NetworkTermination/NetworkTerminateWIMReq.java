/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkTermination;

import java.util.ArrayList;
import java.util.HashMap;

public class NetworkTerminateWIMReq {

    private long reqid;
    private long servid; //service identifiers 

    private HashMap<Integer, ArrayList<Long>> wimdomlistMap; //contains domain managing the WIM abstract resources
    private HashMap<Integer, ArrayList<Long>> interdomainLinksMap;
    private HashMap<Integer, ArrayList<Long>> wanLinksMap;
    private HashMap<Integer, ArrayList<Long>> wimPopListMap;
    private HashMap<Integer, ArrayList<String>> wimNetworkTypeMap;
    private HashMap<Integer, ArrayList<Long>> wanResourceIdListMap;
    
    

    public NetworkTerminateWIMReq(long reqid, long servid, HashMap<Integer, ArrayList<Long>> wimdomlistMap, HashMap<Integer, ArrayList<Long>> interdomainLinksMap, HashMap<Integer, ArrayList<Long>> wanLinksMap, HashMap<Integer, ArrayList<Long>> wimPopListMap, HashMap<Integer, ArrayList<String>> wimNetworkTypeMap,  HashMap<Integer, ArrayList<Long>> wanResourceIdListMap) {
        this.reqid = reqid;
        this.servid = servid;
        this.wimdomlistMap = wimdomlistMap;
        this.interdomainLinksMap = interdomainLinksMap;
        this.wanLinksMap = wanLinksMap;
        this.wimPopListMap = wimPopListMap;
        this.wimNetworkTypeMap = wimNetworkTypeMap;
        this.wanResourceIdListMap = wanResourceIdListMap;
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

    public HashMap<Integer, ArrayList<Long>> getInterdomainLinksMap() {
        return interdomainLinksMap;
    }

    public void setInterdomainLinksMap(HashMap<Integer, ArrayList<Long>> interdomainLinksMap) {
        this.interdomainLinksMap = interdomainLinksMap;
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

    public HashMap<Integer, ArrayList<String>> getWimNetworkTypeMap() {
        return wimNetworkTypeMap;
    }

    public void setWimNetworkTypeMap(HashMap<Integer, ArrayList<String>> wimNetworkTypeMap) {
        this.wimNetworkTypeMap = wimNetworkTypeMap;
    }

}
