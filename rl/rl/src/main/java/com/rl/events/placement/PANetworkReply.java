/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.placement;

import com.rl.extinterface.pa.swagger.client.model.CompRouteOutputInterWanLinks;
import com.rl.extinterface.pa.swagger.client.model.CompRouteOutputNfviPopResp;
import com.rl.extinterface.pa.swagger.client.model.CompRouteOutputWanPaths;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author efabuba
 */
public class PANetworkReply {
    private long reqid;
    private String servid;
    private long logicalpathid;
    private String interNfviConnectivityId;
    private BigDecimal reqBw;
    private List<CompRouteOutputInterWanLinks> interWanLinks;
    private List<CompRouteOutputWanPaths> wanPaths;
    private List<CompRouteOutputNfviPopResp> nfviPopResp;

    public PANetworkReply(long reqid, String servid, long logicalpathid, String interNfviConnectivityId, BigDecimal reqBw, List<CompRouteOutputInterWanLinks> interWanLinks, List<CompRouteOutputWanPaths> wanPaths, List<CompRouteOutputNfviPopResp> nfviPopResp) {
        this.reqid = reqid;
        this.servid = servid;
        this.logicalpathid = logicalpathid;
        this.interNfviConnectivityId = interNfviConnectivityId;
        this.reqBw = reqBw;
        this.interWanLinks = interWanLinks;
        this.wanPaths = wanPaths;
        this.nfviPopResp = nfviPopResp;
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

    public String getInterNfviConnectivityId() {
        return interNfviConnectivityId;
    }

    public void setInterNfviConnectivityId(String interNfviConnectivityId) {
        this.interNfviConnectivityId = interNfviConnectivityId;
    }

    public BigDecimal getReqBw() {
        return reqBw;
    }

    public void setReqBw(BigDecimal reqBw) {
        this.reqBw = reqBw;
    }

    public List<CompRouteOutputInterWanLinks> getInterWanLinks() {
        return interWanLinks;
    }

    public void setInterWanLinks(List<CompRouteOutputInterWanLinks> interWanLinks) {
        this.interWanLinks = interWanLinks;
    }

    public List<CompRouteOutputWanPaths> getWanPaths() {
        return wanPaths;
    }

    public void setWanPaths(List<CompRouteOutputWanPaths> wanPaths) {
        this.wanPaths = wanPaths;
    }

    public List<CompRouteOutputNfviPopResp> getNfviPopResp() {
        return nfviPopResp;
    }

    public void setNfviPopResp(List<CompRouteOutputNfviPopResp> nfviPopResp) {
        this.nfviPopResp = nfviPopResp;
    }

    public long getLogicalpathid() {
        return logicalpathid;
    }

    public void setLogicalpathid(long logicalpathid) {
        this.logicalpathid = logicalpathid;
    }
    
    
}
