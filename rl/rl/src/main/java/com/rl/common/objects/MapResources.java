/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

/**
 *
 * @author efabuba
 */
public class MapResources {
    private String popid;
    private String zoneid;
    private String resourceid;
    
    public MapResources(String pid, String zid, String resid) {
        popid = pid;
        zoneid = zid;
        resourceid = resid;   
    }
    
    public void setPopId(String id) {
        popid = id;
    }
    
    public String getPopId() {
        return popid;
    }
    
    public void setZoneId(String id) {
        zoneid = id;
    }
    
    public String getZoneId() {
        return zoneid;
    }
    
    public void setResourceId(String id) {
        resourceid = id;
    }
    
    public String getResourceId() {
        return resourceid;
    }
}
