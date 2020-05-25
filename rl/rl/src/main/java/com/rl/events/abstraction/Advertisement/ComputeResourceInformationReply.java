/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.events.abstraction.Advertisement;


import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class ComputeResourceInformationReply {
    private long reqid;
    private ArrayList<VirtualComputeResourceInformation> compreslist;
    
    public ComputeResourceInformationReply(long id) {
        reqid = id;
        compreslist = new ArrayList();
    }
    
    public void setReqId(long id) {
        reqid = id;
    }
    
    public long getReqId() {
        return reqid;
    }
    
    public void addnetresinfo(VirtualComputeResourceInformation el) {
        compreslist.add(el);
    }
    
    public ArrayList<VirtualComputeResourceInformation> getnetresinfolist() {
        return compreslist;
    }
}
