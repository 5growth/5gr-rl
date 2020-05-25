/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.abstraction;

/**
 *
 * @author efabuba
 */
public class TableReference {
    private long interdomainLinkId;
    private long networkResourcesId;
    private long fed_interdomainLinkId;
    
    
    
    
    public long getFed_interdomainLinkId() {
        return fed_interdomainLinkId;
    }

    public void setFed_interdomainLinkId(long fed_interdomainLinkId) {
        this.fed_interdomainLinkId = fed_interdomainLinkId;
    }
  

    public TableReference(long interdomainLinkId, long networkResourcesId,  long fed_interdomainLinkId) {
        this.interdomainLinkId = interdomainLinkId;
        this.fed_interdomainLinkId = fed_interdomainLinkId;
        this.networkResourcesId = networkResourcesId;
    }

   

    public long getInterdomainLinkId() {
        return interdomainLinkId;
    }

    public void setInterdomainLinkId(long interdomainLinkId) {
        this.interdomainLinkId = interdomainLinkId;
    }

    public long getNetworkResourcesId() {
        return networkResourcesId;
    }

    public void setNetworkResourcesId(long networkResourcesId) {
        this.networkResourcesId = networkResourcesId;
    }
    
    
}
