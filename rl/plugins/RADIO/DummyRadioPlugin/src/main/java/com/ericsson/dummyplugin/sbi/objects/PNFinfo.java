/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi.objects;

import com.ericsson.dummyplugin.nbi.swagger.model.MetaData;

/**
 *
 * @author osboxes
 */
public class PNFinfo {
    private String pnfid;
    private MetaData metadata;

    public PNFinfo(String pnfid, MetaData metadata) {
        this.pnfid = pnfid;
        this.metadata = metadata;
    }

    public String getPnfid() {
        return pnfid;
    }

    public void setPnfid(String pnfid) {
        this.pnfid = pnfid;
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }
    
    
}
