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
public class RetrieveExporterRequest {
    private boolean allexp;
    private String pmid;
    private String exporterid;

    public RetrieveExporterRequest(boolean allexp, String pmid, String exporterid) {
        this.allexp = allexp;
        this.pmid = pmid;
        this.exporterid = exporterid;
    }

    public boolean isAllexp() {
        return allexp;
    }

    public void setAllexp(boolean allexp) {
        this.allexp = allexp;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getExporterid() {
        return exporterid;
    }

    public void setExporterid(String exporterid) {
        this.exporterid = exporterid;
    }
    
    
}
