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
public class ComputeTerminateMECReply {
    private long reqid;
    private long servid; //service identifiers
    private List<String> computereq; //contains vim computation info
    private List<String> mecreq; //contains mec instance

    public ComputeTerminateMECReply(long reqid, long servid, List<String> computereq, List<String> mecreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.computereq = computereq;
        this.mecreq = mecreq;
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
    
    
}
