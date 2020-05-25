/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

/**
 *
 * @author efabuba
 */
public class DeletePM {
    private String pmid;

    public DeletePM(String pmid) {
        this.pmid = pmid;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }
    
    
}
