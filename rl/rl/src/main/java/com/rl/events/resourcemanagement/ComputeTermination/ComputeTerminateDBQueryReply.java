/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeTermination;

//import com.mtp.common.objects.VIMAbstractElem;
import java.util.ArrayList;

public class ComputeTerminateDBQueryReply {

    private long reqid;
    private long servid; //service identifiers

    private ArrayList<Long> domlist; //contains domain managing the abstract resources
    private ArrayList<Long> poplist;
   // List of Virtual Machine identifiers for which the termination is to be requested 
    private ArrayList<String> vmIdList; 
    private ArrayList<String> vnfIdList; 

    public ComputeTerminateDBQueryReply(long reqid, long servid, ArrayList<Long> domlist, ArrayList<Long> poplist, ArrayList<String> vmIdList, ArrayList<String> vnfIdList) {
        this.reqid = reqid;
        this.servid = servid;
        this.domlist = domlist;
        this.poplist = poplist;
        this.vmIdList = vmIdList;
        this.vnfIdList = vnfIdList;
    }

    public ArrayList<String> getVnfIdList() {
        return vnfIdList;
    }

    public void setVnfIdList(ArrayList<String> vnfIdList) {
        this.vnfIdList = vnfIdList;
    }
    
    
    
    
    

    public ArrayList<String> getVmIdList() {
        return vmIdList;
    }

    public void setVmIdList(ArrayList<String> vmIdList) {
        this.vmIdList = vmIdList;
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

    public ArrayList<Long> getDomlist() {
        return domlist;
    }

    public void setDomlist(ArrayList<Long> domlist) {
        this.domlist = domlist;
    }

    public ArrayList<Long> getPoplist() {
        return poplist;
    }

    public void setPoplist(ArrayList<Long> poplist) {
        this.poplist = poplist;
    }


}
