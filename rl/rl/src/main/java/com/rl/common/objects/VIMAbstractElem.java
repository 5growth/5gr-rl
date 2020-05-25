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
public class VIMAbstractElem {
    //TODO: format VIM resources according IFA005 model
    private long popid;
    public VIMAbstractElem() { 
        popid = -1;
    }
    public VIMAbstractElem(long popidval) {
        popid = popidval;
    }
    
    //get/set methods

    public long getPopid() {
        return popid;
    }

    public void setPopid(long popid) {
        this.popid = popid;
    }
    
    
    
    
}
