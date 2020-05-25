/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.allocate;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class FreeVlanReply {
    private long reqid;
    private List<BigDecimal> vlanlist;

    public FreeVlanReply(long reqid, List<BigDecimal> vlanlist) {
        this.reqid = reqid;
        this.vlanlist = vlanlist;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public List<BigDecimal> getVlanlist() {
        return vlanlist;
    }

    public void setVlanlist(List<BigDecimal> vlanlist) {
        this.vlanlist = vlanlist;
    }
    
    
}
