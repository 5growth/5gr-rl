
package com.sssa.wimplugin.sbi.objects_;


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
