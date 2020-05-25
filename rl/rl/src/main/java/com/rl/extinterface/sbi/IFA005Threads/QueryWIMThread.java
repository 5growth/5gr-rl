/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.common.objects.MapResources;
import com.rl.common.objects.NetworkResElem;
import com.rl.events.abstraction.Creation.WIMResAbstractionEvent;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2004;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkAttributes;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.VirtualLinksInnerVirtualLink;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.WimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class QueryWIMThread extends Thread {
    private DomainElem dominfo;
    public  QueryWIMThread (DomainElem val) {
        dominfo = val;
    }
    
    @Override
    public void run() {
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort()+ "/" + dominfo.getName();
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        
        WimNetworkResourcesApi api = new WimNetworkResourcesApi(capi);
        
     
        List<NfviPop> poplist = new ArrayList(); 
        List<ResourceZone> zonelist= new ArrayList();
        List<VirtualNetworkResourceInformation> netinfolist = new ArrayList();
        List<LogicalLinkAttributes> linkinfolist = new ArrayList();
        CapacityInformation caprep;
        
        WIMResAbstractionEvent ev = new WIMResAbstractionEvent(dominfo.getId());
        InlineResponse2004 resp = new InlineResponse2004();
        //Retrieve nfvipop query, no filter
        try {
            resp = api.collectWimAbstractedInformation();
        } catch (ApiException e) {
            System.out.println("ApiException inside collectWimAbstractedInformation()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        
        //build nfvipop
            ResourceZone zonel = new ResourceZone();
            NfviPop popel = new NfviPop();
            popel.setGeographicalLocationInfo(resp.getGateways().get(0).getGatewayAttributes().getGeographicalLocationInfo());
            String connedp = new String("");
        for (int i = 0; i < resp.getGateways().size(); i++) {            
            if (i != 0) {
                //new edgepoint to insert
                connedp += ";";
            }
            String edpel = resp.getGateways().get(i).getGatewayAttributes().getGatewayId();
            connedp += edpel;
        }
        popel.setNetworkConnectivityEndpoint(connedp);
        popel.setNfviPopId(resp.getGateways().get(0).getGatewayAttributes().getWimId());
        popel.setVimId(resp.getGateways().get(0).getGatewayAttributes().getWimId());
        poplist.add(popel);
        //Create fake zone for the network
        zonel.setNfviPopId(popel.getNfviPopId());
        zonel.setZoneId(popel.getNfviPopId());
        zonel.setZoneName("WIMname");
        zonel.setZoneProperty("WIMProperty");
        zonel.setZoneState("enabled");
        zonelist.add(zonel);

        //set poplist to event
        ev.setPopList(poplist);
        //add zoneid to event
        ev.setZoneList(zonelist);
        
        //create resources for each virtual links
        for (int i = 0; i < resp.getVirtualLinks().size(); i++) {
            VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
            LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
            AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
            List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
            
            //add networkinfo
            VirtualLinksInnerVirtualLink link = resp.getVirtualLinks().get(i).getVirtualLink();
            qos.setQosName("delay");
            qos.setQosValue(link.getNetworkQoS().getLinkDelayValue().toString());
            qoslist.add(qos);
            resnetel.setNetworkQoS(qoslist);
            resnetel.setBandwidth(link.getTotalBandwidth()); //minimum bw required is one
            resnetel.setNetworkResourceTypeId(link.getVirtualLinkId());
            resnetel.setNetworkType(link.getNetworkLayer());
            netinfolist.add(resnetel);
            
            //add linkinfo
            resInfo.setSrcGwIpAddress(link.getSrcGwId());
            resInfo.setDstGwIpAddress(link.getDstGwId());
            resInfo.setLocalLinkId(link.getSrcLinkId());
            resInfo.setRemoteLinkId(link.getDstLinkId());
            resInfo.setLogicalLinkId(link.getVirtualLinkId());
            linkinfolist.add(resInfo);            
        }
            
        for (int i = 0; i < netinfolist.size(); i++) {

            caprep = new CapacityInformation();
            caprep.setAllocatedCapacity("0");
            caprep.setAvailableCapacity(resp.getVirtualLinks().get(i).getVirtualLink().getAvailableBandwidth().toString());
            caprep.setReservedCapacity("0");
            caprep.setTotalCapacity(resp.getVirtualLinks().get(i).getVirtualLink().getTotalBandwidth().toString());
            //Add element to compute list in the event
            NetworkResElem netel = new NetworkResElem();
            netel.setNetworkElem(netinfolist.get(i));
            netel.setCapacityElem(caprep);
            netel.setLinkinfo(linkinfolist.get(i));
            ev.setNetworkResElem(netel);
            //create element in map
            MapResources mapel = new MapResources(zonelist.get(0).getNfviPopId(),
                    zonelist.get(0).getZoneId(),
                    netinfolist.get(i).getNetworkResourceTypeId());
            ev.setNetworkMapElem(mapel);
        }//Loop on virtual network resources            
        //send event
        SingletonEventBus.getBus().post(ev);
    }
}
