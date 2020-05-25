/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

import java.util.List;

/**
 *
 * @author efabuba
 */
public class DeleteExporterReply {
    private String pmid;
    private List<String> deleted;

    public DeleteExporterReply(String pmid, List<String> deleted) {
        this.pmid = pmid;
        this.deleted = deleted;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public List<String> getDeleted() {
        return deleted;
    }

    public void setDeleted(List<String> deleted) {
        this.deleted = deleted;
    }
    
    
}
