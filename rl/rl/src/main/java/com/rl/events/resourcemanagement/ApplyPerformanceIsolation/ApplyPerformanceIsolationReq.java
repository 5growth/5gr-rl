/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.ApplyPerformanceIsolation;


import com.rl.extinterface.nbi.swagger.model.SlaRequirementsRequest;

public class ApplyPerformanceIsolationReq {

    private long reqid;
    private String servid; //service identifiers
    private long domid; //id of vim domain to contact
    //private long computeservid;
    private SlaRequirementsRequest slareq;
    private long nfvipopid;

    public ApplyPerformanceIsolationReq(long reqid, String servid, long domid, SlaRequirementsRequest slareq, long nfvipopid) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.slareq = slareq;
        this.nfvipopid = nfvipopid;
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

    public long getDomid() {
        return domid;
    }

    public void setDomid(long domid) {
        this.domid = domid;
    }

    public SlaRequirementsRequest getSlareq() {
        return slareq;
    }

    public void setSlareq(SlaRequirementsRequest slareq) {
        this.slareq = slareq;
    }


    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }


}
