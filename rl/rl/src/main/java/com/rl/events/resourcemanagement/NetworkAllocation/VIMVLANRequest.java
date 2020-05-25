/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.resourcemanagement.NetworkAllocation;

import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class VIMVLANRequest {
    private long logicalLinkId;
    private InterNfviPopConnectivityRequest networkRequest; // wimelem renamed
    private ArrayList<Long> vimdomlist; //contains domain managing the VIM abstract resources
    private ArrayList<String> intrapoplinks; //contains domain managing the VIM abstract resources

    public VIMVLANRequest(long logicalLinkId, InterNfviPopConnectivityRequest networkRequest, ArrayList<Long> vimdomlist, ArrayList<String> intrapoplinks) {
        this.logicalLinkId = logicalLinkId;
        this.networkRequest = networkRequest;
        this.vimdomlist = vimdomlist;
        this.intrapoplinks = intrapoplinks;
    }

    public long getLogicalLinkId() {
        return logicalLinkId;
    }

    public void setLogicalLinkId(long logicalLinkId) {
        this.logicalLinkId = logicalLinkId;
    }

  
    public InterNfviPopConnectivityRequest getNetworkRequest() {
        return networkRequest;
    }

    public void setNetworkRequest(InterNfviPopConnectivityRequest networkRequest) {
        this.networkRequest = networkRequest;
    }

    public ArrayList<Long> getVimdomlist() {
        return vimdomlist;
    }

    public void setVimdomlist(ArrayList<Long> vimdomlist) {
        this.vimdomlist = vimdomlist;
    }

    public ArrayList<String> getIntrapoplinks() {
        return intrapoplinks;
    }

    public void setIntrapoplinks(ArrayList<String> intrapoplinks) {
        this.intrapoplinks = intrapoplinks;
    }
    

}
