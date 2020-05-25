/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.placement;

import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputAbsWanTopo;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputInterWanLinks;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPops;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputQosCons;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class PANetworkCall {

    private long reqid;
    private String servid;
    private long logicallinkid;
    private ArrayList<CompRouteInputInterWanLinks> interWanLinks;
    private ArrayList<CompRouteInputAbsWanTopo> absWanTopo;
    private CompRouteInputQosCons qosCons;
    private ArrayList<CompRouteInputNfviPops> nfviPops;
    private InterNfviPopConnectivityRequest networkreq;
    private String PEsrc;
    private String PEdst;

    public PANetworkCall(long reqid, String servid, long logicallinkid, ArrayList<CompRouteInputInterWanLinks> interWanLinks, ArrayList<CompRouteInputAbsWanTopo> absWanTopo, CompRouteInputQosCons qosCons, ArrayList<CompRouteInputNfviPops> nfviPops, InterNfviPopConnectivityRequest networkreq, String PEsrc, String PEdst) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicallinkid = logicallinkid;
        this.interWanLinks = interWanLinks;
        this.absWanTopo = absWanTopo;
        this.qosCons = qosCons;
        this.nfviPops = nfviPops;
        this.networkreq = networkreq;
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

    public ArrayList<CompRouteInputInterWanLinks> getInterWanLinks() {
        return interWanLinks;
    }

    public void setInterWanLinks(ArrayList<CompRouteInputInterWanLinks> interWanLinks) {
        this.interWanLinks = interWanLinks;
    }

    public ArrayList<CompRouteInputAbsWanTopo> getAbsWanTopo() {
        return absWanTopo;
    }

    public void setAbsWanTopo(ArrayList<CompRouteInputAbsWanTopo> absWanTopo) {
        this.absWanTopo = absWanTopo;
    }

    public CompRouteInputQosCons getQosCons() {
        return qosCons;
    }

    public void setQosCons(CompRouteInputQosCons qosCons) {
        this.qosCons = qosCons;
    }

    public ArrayList<CompRouteInputNfviPops> getNfviPops() {
        return nfviPops;
    }

    public void setNfviPops(ArrayList<CompRouteInputNfviPops> nfviPops) {
        this.nfviPops = nfviPops;
    }

    public InterNfviPopConnectivityRequest getNetworkreq() {
        return networkreq;
    }

    public void setNetworkreq(InterNfviPopConnectivityRequest networkreq) {
        this.networkreq = networkreq;
    } 

    public long getLogicallinkid() {
        return logicallinkid;
    }

    public void setLogicallinkid(long logicallinkid) {
        this.logicallinkid = logicallinkid;
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
