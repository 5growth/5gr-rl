/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

import com.rl.extinterface.mon.swagger.client.model.Endpoint;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class CreateExporterReply {
    private String name;
    private String pmid;
    private Integer period;
    private List<Endpoint> endplist;
    private String exporterId;

    public CreateExporterReply(String name, String pmid, Integer period, List<Endpoint> endplist, String exporterId) {
        this.name = name;
        this.pmid = pmid;
        this.period = period;
        this.endplist = endplist;
        this.exporterId = exporterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public List<Endpoint> getEndplist() {
        return endplist;
    }

    public void setEndplist(List<Endpoint> endplist) {
        this.endplist = endplist;
    }

    public String getExporterId() {
        return exporterId;
    }

    public void setExporterId(String exporterId) {
        this.exporterId = exporterId;
    }
    
    
}
