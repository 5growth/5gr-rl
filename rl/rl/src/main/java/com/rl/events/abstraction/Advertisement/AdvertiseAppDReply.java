/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

import com.rl.extinterface.nbi.swagger.model.AppD;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class AdvertiseAppDReply {
    private long reqid;
    private List<AppD> appdlist;

    public AdvertiseAppDReply(long reqid, List<AppD> appdlist) {
        this.reqid = reqid;
        this.appdlist = appdlist;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public List<AppD> getAppdlist() {
        return appdlist;
    }

    public void setAppdlist(List<AppD> appdlist) {
        this.appdlist = appdlist;
    }
    
}
