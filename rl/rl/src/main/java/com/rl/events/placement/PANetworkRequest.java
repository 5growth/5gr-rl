/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.placement;

import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import java.math.BigDecimal;

/**
 *
 * @author efabuba
 */
public class PANetworkRequest {
    private long reqid;
    private String servid;
    private InterNfviPopConnectivityRequest networkreq; 
    private long logicalpathid;
    private BigDecimal reqbw;
    private BigDecimal reqlatency;
    private String PEsrc;
    private String PEdst;

    public PANetworkRequest(long reqid, String servid, InterNfviPopConnectivityRequest networkreq, long logicalpathid, BigDecimal reqbw, BigDecimal reqlatency, String PEsrc, String PEdst) {
        this.reqid = reqid;
        this.servid = servid;
        this.networkreq = networkreq;
        this.logicalpathid = logicalpathid;
        this.reqbw = reqbw;
        this.reqlatency = reqlatency;
        this.PEsrc = PEsrc;
        this.PEdst = PEdst;
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

    public InterNfviPopConnectivityRequest getNetworkreq() {
        return networkreq;
    }

    public void setNetworkreq(InterNfviPopConnectivityRequest networkreq) {
        this.networkreq = networkreq;
    }

    public long getLogicalpathid() {
        return logicalpathid;
    }

    public void setLogicalpathid(long logicalpathid) {
        this.logicalpathid = logicalpathid;
    }

    public BigDecimal getReqbw() {
        return reqbw;
    }

    public void setReqbw(BigDecimal reqbw) {
        this.reqbw = reqbw;
    }

    public BigDecimal getReqlatency() {
        return reqlatency;
    }

    public void setReqlatency(BigDecimal reqlatency) {
        this.reqlatency = reqlatency;
    }

    public String getPEsrc() {
        return PEsrc;
    }

    public void setPEsrc(String PEsrc) {
        this.PEsrc = PEsrc;
    }

    public String getPEdst() {
        return PEdst;
    }

    public void setPEdst(String PEdst) {
        this.PEdst = PEdst;
    }
    
    
}
