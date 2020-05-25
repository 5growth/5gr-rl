/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi.objects;

import com.ericsson.dummyplugin.nbi.swagger.model.MECTrafficServiceCreationRequest;

/**
 *
 * @author efabuba
 */
public class MECService {
    private MECTrafficServiceCreationRequest appdel;
    private String mecreqid;

    public MECService(MECTrafficServiceCreationRequest appdel, String mecreqid) {
        this.appdel = appdel;
        this.mecreqid = mecreqid;
    }

    public MECTrafficServiceCreationRequest getAppdel() {
        return appdel;
    }

    public void setAppdel(MECTrafficServiceCreationRequest appdel) {
        this.appdel = appdel;
    }

    public String getMecreqid() {
        return mecreqid;
    }

    public void setMecreqid(String mecreqid) {
        this.mecreqid = mecreqid;
    }

    
    
    
}
