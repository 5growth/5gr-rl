/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateWIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateWIMReq;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReq;
import com.rl.extinterface.nbi.swagger.model.NetworkIds;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class Fed_TerminateWIMNetworkThread extends Thread {
    private HashMap<Integer, List<DomainElem>> dominfoMap;
    private Fed_NetworkTerminateWIMReq request;

    public Fed_TerminateWIMNetworkThread(HashMap<Integer, List<DomainElem>> val, Fed_NetworkTerminateWIMReq req) {
        dominfoMap = val;
        request = req;
    }

    @Override
    public void run() {

        Fed_NetworkTerminateWIMReply termwimrep;
        HashMap<Integer, ArrayList<Boolean>> outcomeListMap = new HashMap();
        HashMap<Integer, ArrayList<Integer>> errorcodeListMap = new HashMap();
        HashMap<Integer, ArrayList<String>> errormsgListMap = new HashMap();

        //Loop on logicaLinks
        for (int j = 0; j < dominfoMap.size(); j++) {
            //Loop on domIds
            ArrayList<Boolean> outcomeList = new ArrayList();
            ArrayList<Integer> errorcodeList = new ArrayList();
            ArrayList<String> errormsgList = new ArrayList();
            for (int i = 0; i < dominfoMap.get(j).size(); i++) { 
//                String basepath = "http://" + dominfoMap.get(j).get(i).getIp() + ":" 
//                        + dominfoMap.get(j).get(i).getPort() + "/" + dominfoMap.get(j).get(i).getName();
                String basepath = "http://" + dominfoMap.get(j).get(i).getIp() + ":" 
                        + dominfoMap.get(j).get(i).getPort();
                ApiClient capi = new ApiClient();
                capi.setBasePath(basepath);
                WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);

                List<NetworkIds> resplist = new ArrayList();
                String idel = new String();
                idel = Long.toString(request.getWanResourceIdListMap().get(j).get(i));
                

                try {
                    //Filter nfviPopComputeInformationRequest = null;
                    resplist = api.terminateNetworks(idel);
                } catch (ApiException e) {
                    System.out.println("ApiException inside termianteNetwork() VIM.");
                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                    outcomeList.add(Boolean.FALSE);
                    errorcodeList.add(e.getCode());
                    errormsgList.add(e.getMessage());
                    break;
                }
                outcomeList.add(Boolean.TRUE);
                errorcodeList.add(0);
                errormsgList.add("");
            }//END loop on domIds

            outcomeListMap.put(j, outcomeList);
            errorcodeListMap.put(j, errorcodeList);
            errormsgListMap.put(j, errormsgList);
        }//END lopp on  logicaLinks
        //send event 
        termwimrep = new Fed_NetworkTerminateWIMReply(request.getReqid(), request.getServid(),
                outcomeListMap, errorcodeListMap, errormsgListMap);
        SingletonEventBus.getBus().post(termwimrep);
    }
}
