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
public class NetworkService {
    
    private String serviceid;
    private String networkid;
    private String subnetid;
    private String portid;
    private String PIFref;
    private String VLANref;

    public NetworkService(String serviceid, String networkid, String subnetid, String portid, String PIFref, String VLANref) {
        this.serviceid = serviceid;
        this.networkid = networkid;
        this.subnetid = subnetid;
        this.portid = portid;
        this.PIFref = PIFref;
        this.VLANref = VLANref;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getSubnetid() {
        return subnetid;
    }

    public void setSubnetid(String subnetid) {
        this.subnetid = subnetid;
    }

    public String getPortid() {
        return portid;
    }

    public void setPortid(String portid) {
        this.portid = portid;
    }

    public String getPIFref() {
        return PIFref;
    }

    public void setPIFref(String PIFref) {
        this.PIFref = PIFref;
    }

    public String getVLANref() {
        return VLANref;
    }

    public void setVLANref(String VLANref) {
        this.VLANref = VLANref;
    }



}
