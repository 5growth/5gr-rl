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
public class WIMAbstractElem {
    //TODO: format WIM resources according IFA005 model
    private long nodeid;
    
    public WIMAbstractElem() { 
        nodeid = -1;
    }
    public WIMAbstractElem(long nodeidval) {
        nodeid = nodeidval;
    }
    
    //get/set methods
}
