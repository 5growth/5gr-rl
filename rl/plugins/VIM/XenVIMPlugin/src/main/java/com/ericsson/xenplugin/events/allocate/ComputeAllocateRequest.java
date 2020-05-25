/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.allocate;

import com.ericsson.xenplugin.nbi.swagger.model.VIMAllocateComputeRequest;





/**
 *
 * @author Lenovo
 */
public class ComputeAllocateRequest {
    private long reqid;

    private VIMAllocateComputeRequest request; //contains vim computation info 
    /*
     * TODO: Create object for allocation request of virtual resources according 
     * IFA005
     */
    
    public ComputeAllocateRequest() {
        reqid = 0;
        request = null;
    }

    public ComputeAllocateRequest(long reqid, VIMAllocateComputeRequest request) {
        this.reqid = reqid;
        this.request = request;
    }


    
    //insert set/get methods
    public long getReqId() {
        return reqid;
    }
    public void setReqId( long reqval) {
        reqid = reqval;
    }

    public VIMAllocateComputeRequest getRequest() {
        return request;
    }
    public void setRequest(VIMAllocateComputeRequest request) {
        this.request = request;
    }
}
