/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.events.abstraction;




import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeResourceInformation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class ComputeResourceInformationReply {
    private long reqid;
    private List<VirtualComputeResourceInformation> compreslist;
    
    public ComputeResourceInformationReply(long id) {
        reqid = id;
        compreslist = new ArrayList();
    }

    public ComputeResourceInformationReply(long reqid, List<VirtualComputeResourceInformation> compreslist) {
        this.reqid = reqid;
        this.compreslist = compreslist;
    }
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void addcompresinfo(VirtualComputeResourceInformation el) {
        compreslist.add(el);
    }
    
    public List<VirtualComputeResourceInformation> getcompresinfolist() {
        return compreslist;
    }
    
}
