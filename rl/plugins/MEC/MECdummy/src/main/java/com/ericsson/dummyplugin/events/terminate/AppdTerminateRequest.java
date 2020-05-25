/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.terminate;



/**
 *
 * @author Lenovo
 */
public class AppdTerminateRequest {
    private long reqid;

    private String netlist; //contains vim computation info 
    /*
     * TODO: Create object for allocation request of virtual resources according 
     * IFA005
     */
    
    public AppdTerminateRequest() {
        reqid = 0;
        netlist = null;
    }

    public AppdTerminateRequest(long reqid, String netlist) {
        this.reqid = reqid;
        this.netlist = netlist;
    }
    

    
    //insert set/get methods

    public long getReqid() {
        return reqid;
    }

    public void setReqid(long reqid) {
        this.reqid = reqid;
    }

    public String getNetlist() {
        return netlist;
    }

    public void setNetlist(String netlist) {
        this.netlist = netlist;
    }

}
