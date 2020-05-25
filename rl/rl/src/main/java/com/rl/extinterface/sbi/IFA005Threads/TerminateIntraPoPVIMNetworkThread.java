/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMRequest;
import com.rl.extinterface.nbi.swagger.model.NetworkIds;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimNetworkResourcesApi;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class TerminateIntraPoPVIMNetworkThread extends Thread {

    private List<DomainElem> dominfoMap;
    private IntraPoPTerminateVIMRequest request;

    public TerminateIntraPoPVIMNetworkThread(List<DomainElem> val, IntraPoPTerminateVIMRequest req) {
        dominfoMap = val;
        request = req;
    }

    @Override
    public void run() {

        IntraPoPTerminateVIMReply termwimrep;
        List<String> netidlist = new ArrayList<String>();

        //Loop on logicaLinks
        for (int i = 0; i < dominfoMap.size(); i++) {
//            String basepath = "http://" + dominfoMap.get(i).getIp() + ":"
//                    + dominfoMap.get(i).getPort() + "/" + dominfoMap.get(i).getName();
            String basepath = null;
            if (dominfoMap.get(i).getName().contains("OpenStack") == true) {
                basepath = "http://" + dominfoMap.get(i).getIp() + ":" + dominfoMap.get(i).getPort() + "/v1";
            } else {
                basepath = "http://" + dominfoMap.get(i).getIp() + ":" + dominfoMap.get(i).getPort();
            }
            ApiClient capi = new ApiClient();

            capi.setBasePath(basepath);
            capi.setConnectTimeout(600000);
            capi.setReadTimeout(600000);
            capi.setWriteTimeout(600000);
            WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);

            List<NetworkIds> resplist = new ArrayList();
//            if (dominfoMap.get(i).getName().contains("OpenStack") == true) {
            VimNetworkResourcesApi vapi = new VimNetworkResourcesApi(capi);
            vapi.getApiClient().setReadTimeout(600000);
            vapi.getApiClient().setConnectTimeout(600000);
            vapi.getApiClient().setWriteTimeout(600000);

            List<String> vnlist = new ArrayList();
            List<String> vnreqlist = new ArrayList();

            if (request.getPortnetreslist().get(i).compareTo("") != 0) {
                List<String> portreqlist = new ArrayList();
                portreqlist.add(request.getPortnetreslist().get(i));
                try {
                    //Filter nfviPopComputeInformationRequest = null;
                    System.out.println("Send delete network port request");
                    vnlist = vapi.vIMterminateNetworks(portreqlist);
                } catch (ApiException e) {
                    System.out.println("ApiException inside terminateNetworks() VIM.");
                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                    break;
                }
            }

            if (request.getSubnetreslist().get(i).compareTo("") != 0) {
                List<String> subnetreqlist = new ArrayList();
                subnetreqlist.add(request.getSubnetreslist().get(i));
                try {
                    //Filter nfviPopComputeInformationRequest = null;
                    System.out.println("Send delete subnet request");
                    vnlist = vapi.vIMterminateNetworks(subnetreqlist);
                } catch (ApiException e) {
                    System.out.println("ApiException inside terminateNetworks() VIM.");
                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                    break;
                }
            }

            vnreqlist.add(request.getDomreslist().get(i));
            try {
                //Filter nfviPopComputeInformationRequest = null;
                System.out.println("Send delete network request");
                vnlist = vapi.vIMterminateNetworks(vnreqlist);
            } catch (ApiException e) {
                System.out.println("ApiException inside terminateNetworks() VIM.");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                break;
            }

            //insert resources in netidlist
            netidlist.add(request.getNetreslist().get(i));
//            } else {
//
//                try {
//                    //Filter nfviPopComputeInformationRequest = null;
//                    resplist = api.terminateNetworks(request.getNetreslist().get(i));
//                } catch (ApiException e) {
//                    System.out.println("ApiException inside terminateNetworks() VIM.");
//                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
//                    break;
//                }
//
//                //insert resources in netidlist
//                for (int j = 0; j < resplist.size(); j++) {
//                    netidlist.add(resplist.get(j).getNetworkId());
//                }
//            }
        }//END loop on domIds

        //send event 
        termwimrep = new IntraPoPTerminateVIMReply(request.getReqid(), request.getDomlist(),
                request.getNetreslist(), netidlist);
        SingletonEventBus.getBus().post(termwimrep);
    }
}
