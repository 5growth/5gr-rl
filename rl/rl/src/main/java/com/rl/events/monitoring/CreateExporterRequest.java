/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.monitoring;

import com.rl.extinterface.mon.swagger.client.model.Endpoint;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class CreateExporterRequest {
    private String name;
    private String pmid;
    private Integer period;
    private ArrayList<Endpoint> endplist;

    public CreateExporterRequest(String name, String pmid, Integer period, ArrayList<Endpoint> endplist) {
        this.name = name;
        this.pmid = pmid;
        this.period = period;
        this.endplist = endplist;
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

    public ArrayList<Endpoint> getEndplist() {
        return endplist;
    }

    public void setEndplist(ArrayList<Endpoint> endplist) {
        this.endplist = endplist;
    }
    
    
}
