/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi.objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author osboxes
 */
public class MFinfo {
    private String pnfid;
    private String vnfid;
    List<String> mfidlist;

    public MFinfo(String pnfid, String vnfid, List<String> mfidlist) {
        this.pnfid = pnfid;
        this.vnfid = vnfid;
        this.mfidlist = mfidlist;
    }

    public MFinfo(String pnfid, String vnfid) {
        this.pnfid = pnfid;
        this.vnfid = vnfid;
        mfidlist = new ArrayList();
    }

    public String getPnfid() {
        return pnfid;
    }

    public void setPnfid(String pnfid) {
        this.pnfid = pnfid;
    }

    public String getVnfid() {
        return vnfid;
    }

    public void setVnfid(String vnfid) {
        this.vnfid = vnfid;
    }

    public List<String> getMfidlist() {
        return mfidlist;
    }

    public void setMfidlist(List<String> mfidlist) {
        this.mfidlist = mfidlist;
    }
    
      
}
