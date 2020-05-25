/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateRadioReply;



/**
 *
 * @author efabuba
 */
public class AllocateRadioThread extends Thread  {
    public AllocateRadioThread () {

    }
    
    @Override
    public void run() {
        //TODO: perform action to MEC list for MEC domain
        
        
        ComputeAllocateRadioReply ev = new ComputeAllocateRadioReply();
        //TODO: prepare event to collect info
        //send event
        SingletonEventBus.getBus().post(ev);
    }  
}
