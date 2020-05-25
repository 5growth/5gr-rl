/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.abstraction;


import com.ericsson.dummyplugin.nbi.swagger.model.ResourceZone;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class ComputeZoneReply {
    private long reqid;
    private List<ResourceZone> zonelist;

    public ComputeZoneReply(long reqid, List<ResourceZone> zonelist) {
        this.reqid = reqid;
        this.zonelist = zonelist;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public List<ResourceZone> getZonelist() {
        return zonelist;
    }

    public void setZonelist(List<ResourceZone> zonelist) {
        this.zonelist = zonelist;
    }


    
    
}
