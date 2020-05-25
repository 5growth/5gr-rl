/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;

/**
 *
 * @author efabuba
 */
public class AdvertiseAppDIdRequest {
  private long reqid;
  private String appid;

    public AdvertiseAppDIdRequest(long reqid, String appid) {
        this.reqid = reqid;
        this.appid = appid;
    }

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
  
  
}
