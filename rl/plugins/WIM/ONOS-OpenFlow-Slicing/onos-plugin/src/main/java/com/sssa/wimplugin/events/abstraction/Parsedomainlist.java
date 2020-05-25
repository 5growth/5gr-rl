
package com.sssa.wimplugin.events.abstraction;


public class Parsedomainlist {
    
    private String filename;
    
    public Parsedomainlist() {
        filename = "";
    }
    public Parsedomainlist(String str) {
        filename = str;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String str) {
        filename = str;
    }
}
