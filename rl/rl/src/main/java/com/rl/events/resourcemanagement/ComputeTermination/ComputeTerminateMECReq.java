/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ComputeTermination;

import java.util.List;

/**
 *
 * @author ezimbgi
 */
public class ComputeTerminateMECReq {
    private long reqid;
    private long servid; //service identifiers
    private List<String> computereq; //contains vim computation info
    private List<String> mecreq; //contains mec instance
    private List<Long> mecdom; //contains mec dom list

    public ComputeTerminateMECReq(long reqid, long servid, List<String> computereq, List<String> mecreq, List<Long> mecdom) {
        this.reqid = reqid;
        this.servid = servid;
        this.computereq = computereq;
        this.mecreq = mecreq;
        this.mecdom = mecdom;
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

    public List<String> getComputereq() {
        return computereq;
    }

    public void setComputereq(List<String> computereq) {
        this.computereq = computereq;
    }

    public List<String> getMecreq() {
        return mecreq;
    }

    public void setMecreq(List<String> mecreq) {
        this.mecreq = mecreq;
    }

    public List<Long> getMecdom() {
        return mecdom;
    }

    public void setMecdom(List<Long> mecdom) {
        this.mecdom = mecdom;
    }
    
    
}
