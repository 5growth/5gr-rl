/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

/**
 *
 * @author Lenovo
 */
public class WIME2EAbstractElem {
    //TODO: format WIM resources according IFA005 model
    private long nodeid;
    
    public WIME2EAbstractElem() { 
        nodeid = -1;
    }
    public WIME2EAbstractElem(long nodeidval) {
        nodeid = nodeidval;
    }
    
    //get/set methods
}
