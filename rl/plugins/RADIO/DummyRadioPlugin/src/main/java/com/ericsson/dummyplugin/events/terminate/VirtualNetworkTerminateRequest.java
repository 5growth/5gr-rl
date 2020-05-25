/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.events.terminate;



import java.util.List;
/**
 *
 * @author Lenovo
 */
public class VirtualNetworkTerminateRequest {
    private long reqid;
    private List<String> e2ewimelem; //contains vim computation info 
    /*
     * TODO: Create object for allocation request of virtual resources according 
     * IFA005
     */
    
    public VirtualNetworkTerminateRequest() {
        reqid = 0;
        e2ewimelem = null;
    }

    public VirtualNetworkTerminateRequest(long reqid, List<String> e2ewimelem) {
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
    public List<String> getE2EWIMElem() {
        return e2ewimelem;
    }
    public void setE2EWIMElem(List<String> wimelem) {
        e2ewimelem = wimelem;
    }
}
