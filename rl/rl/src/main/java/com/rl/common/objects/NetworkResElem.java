/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkAttributes;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;

/**
 *
 * @author efabuba
 */
public class NetworkResElem {
    private VirtualNetworkResourceInformation netinfo;
    private CapacityInformation capinfo;
    private LogicalLinkAttributes linkinfo;
    
    public NetworkResElem() {
        netinfo = null;
        capinfo = null;
        linkinfo = null;
    }
    
    public void setNetworkElem (VirtualNetworkResourceInformation id) {
        netinfo = id;
    }
    
    public VirtualNetworkResourceInformation getNetworkElem () {
        return netinfo;
    }
     
    public void setCapacityElem (CapacityInformation id) {
        capinfo = id;
    }
      
    public CapacityInformation getCapacityElem () {
        return capinfo;
    }

    public LogicalLinkAttributes getLinkinfo() {
        return linkinfo;
    }

    public void setLinkinfo(LogicalLinkAttributes linkinfo) {
        this.linkinfo = linkinfo;
    }
}
