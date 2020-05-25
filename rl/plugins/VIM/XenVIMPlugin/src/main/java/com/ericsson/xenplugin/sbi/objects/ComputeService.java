/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class ComputeService {
    private String servid;
    private String vmlabel;
    private String zoneid;

    public ComputeService(String servid, String vmlabel, String zoneid) {
        this.servid = servid;
        this.vmlabel = vmlabel;
        this.zoneid = zoneid;
    }

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }

    public String getVmlabel() {
        return vmlabel;
    }

    public void setVmlabel(String vmlabel) {
        this.vmlabel = vmlabel;
    }

    public String getZoneid() {
        return zoneid;
    }

    public void setZoneid(String zoneid) {
        this.zoneid = zoneid;
    }
    
    
    
    
}
