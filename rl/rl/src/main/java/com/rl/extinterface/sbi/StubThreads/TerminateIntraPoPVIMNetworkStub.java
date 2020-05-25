/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMRequest;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReq;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReply;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class TerminateIntraPoPVIMNetworkStub extends Thread {

   private List<DomainElem> dominfoMap;
    private IntraPoPTerminateVIMRequest request;

    public TerminateIntraPoPVIMNetworkStub( List<DomainElem> val, IntraPoPTerminateVIMRequest req) {
        dominfoMap = val;
        request = req;
    }

    @Override
    public void run() {

            IntraPoPTerminateVIMReply termwimrep;
             List<String> netidlist = new ArrayList<String>();
            
            
            
       
       
        for (int j = 0; j < dominfoMap.size(); j++) {
            //Loop on domIds
            //for (int i = 0; i < dominfoMap.get(j).size(); i++) {
                netidlist.add(request.getNetreslist().get(j));

        
        }//END lopp on  logicaLinks
  
        
        
         termwimrep = new IntraPoPTerminateVIMReply(request.getReqid(), request.getDomlist(),
                        request.getNetreslist(), netidlist);
        SingletonEventBus.getBus().post(termwimrep);
        
        
        
        
//        termvimrep = new NetworkTerminateVIMReply(request.getReqid(), request.getServid(),
//                outcomeListMap, errorcodeListMap, errormsgListMap);
//
//        SingletonEventBus.getBus().post(termvimrep);
    }
}
