/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.common.objects.NetworkEndpoints;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateWIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import com.rl.extinterface.nbi.swagger.model.AllocateParameters;
import com.rl.extinterface.nbi.swagger.model.AllocateReply;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class Fed_AllocateWIMNetworkThread extends Thread {
    // private DomainElem dominfo;

    private List<DomainElem> dominfo;
    private Fed_NetworkAllocateWIMReq request;

//    public AllocateWIMNetworkThread (DomainElem val, NetworkAllocateWIMReq req) {
//        dominfo = val;
//        request = req;
//    }
    public Fed_AllocateWIMNetworkThread(List<DomainElem> val, Fed_NetworkAllocateWIMReq req) {
        dominfo = val;
        request = req;

    }

    @Override
    public void run() {

        Fed_NetworkAllocateWIMReply allwimrep;
        ArrayList<VirtualNetwork> wimnetlist = new ArrayList();

        for (int i = 0; i < request.getWimdomlist().size(); i++) {
            //String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/" + dominfo.get(i).getName();
            String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort();
            ApiClient capi = new ApiClient();
            capi.setConnectTimeout(600000);
            capi.setReadTimeout(600000);
            capi.setWriteTimeout(600000);
            capi.setBasePath(basepath);
            WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);

            AllocateParameters param = new AllocateParameters();
            param.setBandwidth(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqBandwidth());
            param.setDelay(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqLatency().toString());
            param.setEgressPointIPAddress(request.getFlowRuleEndPointList().get(i).getEgressIp());
            param.setEgressPointPortAddress(request.getFlowRuleEndPointList().get(i).getEgressPort());
            param.setIngressPointIPAddress(request.getFlowRuleEndPointList().get(i).getIngressIp());
            param.setIngressPointPortAddress(request.getFlowRuleEndPointList().get(i).getIngressPort());
            //param.setMetadata(request.getServid());
            param.setSegmentType(request.getNetworkRequest().getNetworkLayer());
            param.setWanLinkId(request.getWanLinks().get(i).toString());

            //Take the vlan and set in the request. Acouple of value are present for each wimelem
//            switch (request.getNetworkRequest().getNetworkLayer()) {
//                case "VLAN":
//                    String vlans = request.getVlans().get(2*i) + ";" + request.getVlans().get(2*i + 1);
//                    param.setSegmentId(vlans);
//                    break;
//                case "VXLAN":
//                    String ips = request.getIps().get(2*i) + ";" + request.getIps().get(2*i + 1);;
//                    param.setSegmentId(ips);
//                    break;
//                
//            }
            String[] vals_ingress = request.getIntraPopLinks().get(0).split(";");
            String[] vals_egress = request.getIntraPopLinks().get(1).split(";");
            String vlans = vals_ingress[2] + ";" + vals_egress[2];
            param.setSegmentId(vlans);

            AllocateReply networkresponse;
            try {
                //Filter nfviPopComputeInformationRequest = null;
                System.out.println("WIM network allocate request time:" + ZonedDateTime.now());
                networkresponse = api.allocateNetwork(param);
                System.out.println("WIM network allocate response time:" + ZonedDateTime.now());
            } catch (ApiException e) {
                System.out.println("ApiException inside allocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                allwimrep = new Fed_NetworkAllocateWIMReply(request.getReqid(), request.getServid(),
                        false, 0, "", null, request.getLogicalPathId(), null);
                allwimrep.setErrorcode(e.getCode());
                allwimrep.setErrormsg(e.getMessage());
                SingletonEventBus.getBus().post(allwimrep);
                return;

            }

            VirtualNetwork netel = new VirtualNetwork();
            List<AllocateNetworkResultNetworkDataNetworkQoS> networkQoS = new ArrayList();

            netel.setNetworkResourceId(networkresponse.getNetworkId());
            netel.setSegmentType(networkresponse.getSegmentType());
            netel.setNetworkType(networkresponse.getNetworkType());
            netel.setIsShared(Boolean.FALSE);
            netel.setBandwidth(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqBandwidth());
            AllocateNetworkResultNetworkDataNetworkQoS qosel = new AllocateNetworkResultNetworkDataNetworkQoS();
            qosel.setQosName("delay");
            qosel.setQosValue(request.getNetworkRequest().getLogicalLinkPathList().get(i).getReqLatency().toString());
            networkQoS.add(qosel);
            netel.setNetworkQoS(networkQoS);
            netel.setOperationalState("enabled");
            wimnetlist.add(netel);
        }
        //send event
        allwimrep = new Fed_NetworkAllocateWIMReply(request.getReqid(), request.getServid(),
                true, 0, "", wimnetlist, request.getLogicalPathId(), request.getPendingFedNetAllocReqId());
        SingletonEventBus.getBus().post(allwimrep);
    }
}
