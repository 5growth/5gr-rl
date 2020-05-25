/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeAllocation;

//import com.mtp.common.objects.VIMAbstractElem;
import java.util.ArrayList;

public class ComputeAllocateDBQueryReply {

    private long reqid;
    private String servid; //service identifiers

    private ArrayList<Long> domlist;
    private ArrayList<Long> poplist;

    public ComputeAllocateDBQueryReply() {
        reqid = 0;
        servid = "";
        poplist = new ArrayList();
        domlist = new ArrayList();
    }

    public ComputeAllocateDBQueryReply(long reqid, String servid, ArrayList<Long> domlist, ArrayList<Long> poplist) {
        this.reqid = reqid;
        this.servid = servid;
        this.domlist = domlist;
        this.poplist = poplist;
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
