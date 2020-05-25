/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.abstraction.Creation.RadioResAbstractionEvent;

/**
 *
 * @author efabuba
 */
public class QueryRadioStub extends Thread {
    private DomainElem dominfo;
    public QueryRadioStub (DomainElem val) {
        dominfo = val;
    }
    
    @Override
    public void run() {
        //TODO: perform query list for Radio domain
        
        
        RadioResAbstractionEvent ev = new RadioResAbstractionEvent(dominfo.getId());
        //TODO: prepare event to collect info
        //send event
        SingletonEventBus.getBus().post(ev);
    }    
}
