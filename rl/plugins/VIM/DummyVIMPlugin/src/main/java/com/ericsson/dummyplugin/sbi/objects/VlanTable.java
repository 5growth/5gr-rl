/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class VlanTable {
    private long vlanid;
    private boolean free;

    public VlanTable(long vlanid, boolean free) {
        this.vlanid = vlanid;
        this.free = free;
    }

    public long getVlanid() {
        return vlanid;
    }

    public void setVlanid(long vlanid) {
        this.vlanid = vlanid;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
    
    
}
