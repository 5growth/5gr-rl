/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;


import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class ComputeResourceZoneReply {
    private long reqid;
    private ArrayList<ResourceZone> zonereslist;
    
    public ComputeResourceZoneReply(long id) {
        reqid = id;
        zonereslist = new ArrayList();
    }
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void addnetresinfo(ResourceZone el) {
        zonereslist.add(el);
    }
    
    public ArrayList<ResourceZone> getnetresinfolist() {
        return zonereslist;
    }
}
