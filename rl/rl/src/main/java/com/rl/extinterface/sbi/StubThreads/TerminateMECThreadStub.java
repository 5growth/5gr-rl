/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReq;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class TerminateMECThreadStub extends Thread {
     private List<DomainElem> dominfolist;
    private ComputeTerminateMECReq request;
    public TerminateMECThreadStub (List<DomainElem> val, ComputeTerminateMECReq req) {
        dominfolist = val;
        request = req;
    }
    
    @Override
    public void run() {
        
        
        
        ComputeTerminateMECReply ev = new ComputeTerminateMECReply(request.getReqid(),request.getServid(),
                    request.getComputereq(),request.getMecreq());
        //send event
        SingletonEventBus.getBus().post(ev);
    }   
}
