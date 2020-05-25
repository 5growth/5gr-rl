/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

import com.rl.extinterface.mon.swagger.client.model.Exporter;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class RetrieveExporterReply {
    private String pmid;
    private List<Exporter> explist;

    public RetrieveExporterReply(String pmid, List<Exporter> explist) {
        this.pmid = pmid;
        this.explist = explist;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public List<Exporter> getExplist() {
        return explist;
    }

    public void setExplist(List<Exporter> explist) {
        this.explist = explist;
    }
}
