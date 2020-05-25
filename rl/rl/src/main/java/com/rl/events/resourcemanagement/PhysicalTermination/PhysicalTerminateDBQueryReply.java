/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalTermination;

/**
 *
 * @author osboxes
 */
public class PhysicalTerminateDBQueryReply {
    private long reqid;
    private long servid; //service identifiers

    private long domid; //contains domain managing the abstract resources
    private long popid;
    private long abstractpopid;
   // List of Virtual Machine identifiers for which the termination is to be requested 
    private String vmid; 
    private String pnfid;

    public PhysicalTerminateDBQueryReply(long reqid, long servid, long domid, long popid, long abstractpopid, String vmid, String pnfid) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.popid = popid;
        this.abstractpopid = abstractpopid;
        this.vmid = vmid;
        this.pnfid = pnfid;
    }

    public String getPnfid() {
        return pnfid;
    }

    public void setPnfid(String pnfid) {
        this.pnfid = pnfid;
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

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public long getPopid() {
        return popid;
    }

    public void setPopid(long popid) {
        this.popid = popid;
    }

    public long getAbstractpopid() {
        return abstractpopid;
    }

    public void setAbstractpopid(long abstractpopid) {
        this.abstractpopid = abstractpopid;
    }

    public String getVmid() {
        return vmid;
    }

    public void setVmid(String vmid) {
        this.vmid = vmid;
    }
    
    
    
}
