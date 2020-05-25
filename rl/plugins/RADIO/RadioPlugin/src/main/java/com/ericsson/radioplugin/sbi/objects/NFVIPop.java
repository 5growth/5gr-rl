/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.radioplugin.sbi.objects;

/**
 *
 * @author efabuba
 */
public class NFVIPop {
    private String endp;
    private String locinfo;
    private String popid;
    private String vimid;
    
    public NFVIPop (String endpoints, String location, String pop, String vim) {
        endp = endpoints;
        locinfo = location;
        popid = pop;
        vimid = vim;
    }
    
    
    public String getEndpoints() {
        return endp;
    }
    
    public String getLocation() {
        return locinfo;
    }
    
    public String getPop() {
        return popid;
    }
    
    public String getVim() {
        return vimid;
    }
}
