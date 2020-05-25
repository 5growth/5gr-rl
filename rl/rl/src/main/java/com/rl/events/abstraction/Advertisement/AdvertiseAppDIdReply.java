/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

import com.rl.extinterface.nbi.swagger.model.AppD;

/**
 *
 * @author efabuba
 */
public class AdvertiseAppDIdReply {
    private long reqid;
    private AppD appd;

    public AdvertiseAppDIdReply(long reqid, AppD appd) {
        this.reqid = reqid;
        this.appd = appd;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public AppD getAppd() {
        return appd;
    }

    public void setAppd(AppD appd) {
        this.appd = appd;
    }
    
    
    
}
