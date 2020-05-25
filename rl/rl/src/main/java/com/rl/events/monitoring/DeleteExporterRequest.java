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
public class DeleteExporterRequest {
    private String pmid;
    private String exporterId;

    public DeleteExporterRequest(String pmid, String exporterId) {
        this.pmid = pmid;
        this.exporterId = exporterId;
    }

    public String getExporterId() {
        return exporterId;
    }

    public void setExporterId(String exporterId) {
        this.exporterId = exporterId;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }
    
    
}
