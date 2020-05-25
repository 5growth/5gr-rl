/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;

import com.ericsson.dummyplugin.nbi.swagger.model.VirtualNetworkResourceInformation;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class NetworkResourceInformationReply {
    private long reqid;
    private ArrayList<VirtualNetworkResourceInformation> netreslist;
    
    public NetworkResourceInformationReply(long id) {
        reqid = id;
        netreslist = new ArrayList();
    }
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void addnetresinfo(VirtualNetworkResourceInformation el) {
        netreslist.add(el);
    }
    
    public ArrayList<VirtualNetworkResourceInformation> getnetresinfolist() {
        return netreslist;
    }
    
}
