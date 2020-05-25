/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

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
public class TerminateVIMNetworkThread extends Thread {
    private HashMap<Integer, List<DomainElem>> dominfoMap;
    private NetworkTerminateVIMReq request;

    public TerminateVIMNetworkThread(HashMap<Integer, List<DomainElem>> val, NetworkTerminateVIMReq req) {
        dominfoMap = val;
        request = req;
    }

    @Override
    public void run() {
        NetworkTerminateVIMReply termvimrep;
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
//                String basepath = "http://" + dominfoMap.get(j).get(i).getIp() + ":" 
//                       + dominfoMap.get(j).get(i).getPort();
                
                String basepath = null;
                if (dominfoMap.get(j).get(i).getName().contains("OpenStack") == true) {
                    basepath = "http://" + dominfoMap.get(j).get(i).getIp() + ":" + dominfoMap.get(j).get(i).getPort() + "/v1";
                } else {
                    basepath = "http://" + dominfoMap.get(j).get(i).getIp() + ":" + dominfoMap.get(j).get(i).getPort();
                }
                
//
                    String osid = new String();
                    //osid = Long.toString(request.getIntraPopLinksMap().get(j).get(i));
                    osid = request.getIntraPopLinksMap().get(j).get(i);
                    if (osid.compareTo("dummy") == 0) {
                        //nothing to do
                        outcomeList.add(Boolean.TRUE);
                        errorcodeList.add(0);
                        errormsgList.add("");
                    } else {
                        //TODO: Remove VLAN update resource
                        ApiClient capi = new ApiClient();
                        capi.setBasePath(basepath);
                        VimNetworkResourcesApi api = new VimNetworkResourcesApi(capi);

                        List<String> netlist = new ArrayList();
                        List<String> resplist = new ArrayList();
                        String idel = new String();
                        //idel = Long.toString(request.getIntraPopLinksMap().get(j).get(i));
                        idel = request.getIntraPopLinksMap().get(j).get(i);
                        netlist.add(idel);

                        try {
                            //Filter nfviPopComputeInformationRequest = null;
                            resplist = api.vIMterminateNetworks(netlist);
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
                }
   
            }//END loop on domIds

            outcomeListMap.put(j, outcomeList);
            errorcodeListMap.put(j, errorcodeList);
            errormsgListMap.put(j, errormsgList);
        }//END lopp on  logicaLinks
        //send event 
        termvimrep = new NetworkTerminateVIMReply(request.getReqid(), request.getServid(),
                outcomeListMap, errorcodeListMap, errormsgListMap);
        SingletonEventBus.getBus().post(termvimrep);
    }
}
