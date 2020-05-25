/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.placement;

import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPopReqs;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPops;


/**
 *
 * @author efabuba
 */
public class PAComputeCall {
    private long reqid;
    private String servid;
    private long domid;
    private long nfvipopid;
    private CompRouteInputNfviPops poplist;
    private CompRouteInputNfviPopReqs popreq;

    public PAComputeCall(long reqid, String servid, long domid, long nfvipopid, CompRouteInputNfviPops poplist, CompRouteInputNfviPopReqs popreq) {
        this.reqid = reqid;
        this.servid = servid;
        this.domid = domid;
        this.nfvipopid = nfvipopid;
        this.poplist = poplist;
        this.popreq = popreq;
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

    public long getNfvipopid() {
        return nfvipopid;
    }

    public void setNfvipopid(long nfvipopid) {
        this.nfvipopid = nfvipopid;
    }

    public CompRouteInputNfviPops getPoplist() {
        return poplist;
    }

    public void setPoplist(CompRouteInputNfviPops poplist) {
        this.poplist = poplist;
    }

    public CompRouteInputNfviPopReqs getPopreq() {
        return popreq;
    }

    public void setPopreq(CompRouteInputNfviPopReqs popreq) {
        this.popreq = popreq;
    }
    
    
    
}
