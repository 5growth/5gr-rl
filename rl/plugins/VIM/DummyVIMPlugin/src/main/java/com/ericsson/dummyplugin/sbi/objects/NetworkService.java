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
public class NetworkService {
    
    private String serviceid;
    private String networktype;
    private String segmenttype;

    public NetworkService(String serviceid, String networktype, String segmenttype) {
        this.serviceid = serviceid;
        this.networktype = networktype;
        this.segmenttype = segmenttype;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getNetworktype() {
        return networktype;
    }

    public void setNetworktype(String networktype) {
        this.networktype = networktype;
    }

    public String getSegmenttype() {
        return segmenttype;
    }

    public void setSegmenttype(String segmenttype) {
        this.segmenttype = segmenttype;
    }


}
