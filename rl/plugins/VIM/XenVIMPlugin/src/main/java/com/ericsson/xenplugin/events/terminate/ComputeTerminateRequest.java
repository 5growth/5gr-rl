/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.terminate;


import java.util.List;
/**
 *
 * @author Lenovo
 */
public class ComputeTerminateRequest {
    private long reqid;
    private List<String> e2ewimelem; //contains vim computation info 
    /*
     * TODO: Create object for allocation request of virtual resources according 
     * IFA005
     */
    
    public ComputeTerminateRequest() {
        reqid = 0;
        e2ewimelem = null;
    }

    public ComputeTerminateRequest(long reqid, List<String> e2ewimelem) {
        this.reqid = reqid;
        this.e2ewimelem = e2ewimelem;
    }

    
    //insert set/get methods
    public long getReqId() {
        return reqid;
    }
    public void setReqId( long reqval) {
        reqid = reqval;
    }

    public List<String> getRequest() {
        return e2ewimelem;
    }
    public void setRequest(List<String> wimelem) {
        e2ewimelem = wimelem;
    }
}
