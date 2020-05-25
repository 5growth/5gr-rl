/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;


/**
 *
 * @author efabuba
 */
public class ComputeResElem {
    private VirtualComputeResourceInformation resinfo;
    private CapacityInformation capinfo;
    
    public ComputeResElem() {
        resinfo = null;
        capinfo = null;
    }
    
    public void setComputeElem (VirtualComputeResourceInformation id) {
        resinfo = id;
    }
    
    public VirtualComputeResourceInformation getComputeElem () {
        return resinfo;
    }
     
    public void setCapacityElem (CapacityInformation id) {
        capinfo = id;
    }
      
    public CapacityInformation getCapacityElem () {
        return capinfo;
    }
}
