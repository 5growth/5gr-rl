
package com.sssa.wimplugin.sbi.objects_;


public class Zone {
    private String id;
    private String name;
    private String popid;
    private String status;
    private String property;
    
    public Zone (String idval, String nameval, String pop, String stat, String prop) {
        id = idval;
        name = nameval;
        popid = pop;
        status = stat;
        property = prop;
    }
    
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPop() {
        return popid;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getProperty() {
        return property;
    }
}
