/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
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
public class TerminateVIMNetworkStub extends Thread {

    private HashMap<Integer, List<DomainElem>> dominfoMap;
    private NetworkTerminateVIMReq request;

    public TerminateVIMNetworkStub(HashMap<Integer, List<DomainElem>> val, NetworkTerminateVIMReq req) {
        dominfoMap = val;
        request = req;
    }

    @Override
    public void run() {

        NetworkTerminateVIMReply termvimrep;
        HashMap<Integer, ArrayList<Boolean>> outcomeListMap = new HashMap();
        HashMap<Integer, ArrayList<Integer>> errorcodeListMap = new HashMap();
        HashMap<Integer, ArrayList<String>> errormsgListMap = new HashMap();
        ArrayList<Boolean> outcomeList = new ArrayList();
        ArrayList<Integer> errorcodeList = new ArrayList();
        ArrayList<String> errormsgList = new ArrayList();
        //Loop on logicaLinks
        for (int j = 0; j < dominfoMap.size(); j++) {
            //Loop on domIds
            for (int i = 0; i < dominfoMap.get(j).size(); i++) {

                outcomeList.add(Boolean.TRUE);

                 //Identifier of resource to terminate in the domain 
                System.out.println("TerminateVIMNetworkStub --> ResourceId to terminate:" +  request.getIntraPopLinksMap().get(j).get(i) + "");
                
                
            }//END loop on domIds

            outcomeListMap.put(j, outcomeList);
            errorcodeListMap.put(j, errorcodeList);
            errormsgListMap.put(j, errormsgList);
        }//END lopp on  logicaLinks
        //send event with all id OK
        termvimrep = new NetworkTerminateVIMReply(request.getReqid(), request.getServid(),
                outcomeListMap, errorcodeListMap, errormsgListMap);

        SingletonEventBus.getBus().post(termvimrep);
    }
}
