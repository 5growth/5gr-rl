/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.PhysicalAllocation;

import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;

/**
 *
 * @author osboxes
 */
public class PhysicalAllocateRadioReply {
    private long reqid;
    private String servid; //service identifiers 
    private long domid; //id of vim domain to contact
    private boolean outcome; //result of the request
    private int errorcode;
    private String errormsg; //result of the request
    private PNFReply pnfreply;
    //private long computeservid;
    private long nfvipopid;
    private PNFRequest pnfrequest;

    public PhysicalAllocateRadioReply(long reqid, String servid, long domid, boolean outcome, int errorcode, String errormsg, PNFReply pnfreply, long nfvipopid, PNFRequest pnfrequest) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.outcome = outcome;
        this.errorcode = errorcode;
        this.errormsg = errormsg;
        this.pnfreply = pnfreply;
        this.nfvipopid = nfvipopid;
        this.pnfrequest = pnfrequest;
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

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public PNFReply getPnfreply() {
        return pnfreply;
    }

    public void setPnfreply(PNFReply pnfreply) {
        this.pnfreply = pnfreply;
    }

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public PNFRequest getPnfrequest() {
        return pnfrequest;
    }

    public void setPnfrequest(PNFRequest pnfrequest) {
        this.pnfrequest = pnfrequest;
    }
    
}
