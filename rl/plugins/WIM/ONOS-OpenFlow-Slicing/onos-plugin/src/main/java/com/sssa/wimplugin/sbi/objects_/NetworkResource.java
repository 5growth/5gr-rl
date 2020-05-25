
package com.sssa.wimplugin.sbi.objects_;


public class NetworkResource {
    private String id;
    private String bw;
    private String type;
    private String delay;
    private String srcip;
    private String dstip;
    private String locid;
    private String remid;
    private String totcap;
    private String rescap;
    private String allcap;
    private String freecap;

    public NetworkResource(String id, String bw, String type, String delay, String srcip, String dstip, String locid, String remid, String totcap, String rescap, String allcap, String freecap) {
        this.id = id;
        this.bw = bw;
        this.type = type;
        this.delay = delay;
        this.srcip = srcip;
        this.dstip = dstip;
        this.locid = locid;
        this.remid = remid;
        this.totcap = totcap;
        this.rescap = rescap;
        this.allcap = allcap;
        this.freecap = freecap;
    }
    


    public String getId() {
        return id;
    }

    public String getBw() {
        return bw;
    }

    public String getType() {
        return type;
    }

    public String getDelay() {
        return delay;
    }

    public String getSrcip() {
        return srcip;
    }

    public String getDstip() {
        return dstip;
    }

    public String getLocid() {
        return locid;
    }

    public String getRemid() {
        return remid;
    }

    public String getTotcap() {
        return totcap;
    }

    public String getRescap() {
        return rescap;
    }

    public String getAllcap() {
        return allcap;
    }

    public String getFreecap() {
        return freecap;
    }
    
    


    public void setId(String id) {
        this.id = id;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setSrcip(String srcip) {
        this.srcip = srcip;
    }

    public void setDstip(String dstip) {
        this.dstip = dstip;
    }

    public void setLocid(String locid) {
        this.locid = locid;
    }

    public void setRemid(String remid) {
        this.remid = remid;
    }

    public void setTotcap(String totcap) {
        this.totcap = totcap;
    }

    public void setRescap(String rescap) {
        this.rescap = rescap;
    }

    public void setAllcap(String allcap) {
        this.allcap = allcap;
    }

    public void setFreecap(String freecap) {
        this.freecap = freecap;
    }
    
    
}  

