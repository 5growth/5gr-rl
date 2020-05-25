/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.ComputeResElem;
import com.rl.common.objects.DomainElem;
import com.rl.common.objects.MapResources;
import com.rl.events.abstraction.Creation.VIMResAbstractionEvent;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimComputeResourcesApi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class QueryVIMThread extends Thread {
    private DomainElem dominfo;
    public  QueryVIMThread (DomainElem val) {
        dominfo = val;
    }
    
    
    @Override
    public void run() {
        
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = null;
        if (dominfo.getName().contains("OpenStack") == true) {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/v1";
        } else {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        }
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        VimComputeResourcesApi api = new VimComputeResourcesApi(capi);

        List<NfviPop> poplist = new ArrayList(); 
        List<ResourceZone> zonelist= new ArrayList();
        List<VirtualComputeResourceInformation> resinfolist = new ArrayList();
        List<VirtualNetworkResourceInformation> netinfolist = new ArrayList();
        int count, i, j;
        String tmpzone, tmppop;
        String reqcap = new String();
        CapacityInformation caprep = new CapacityInformation();
        
        VIMResAbstractionEvent ev = new VIMResAbstractionEvent(dominfo.getId());
        //Retrieve nfvipop query, no filter
        try {
            poplist = api.queryNFVIPoPComputeInformation();
        } catch (ApiException e) {
            System.out.println("ApiException inside queryNFVIPoPComputeInformation()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //add nfvipop to event
        ev.setPopList(poplist);
        //Retrieve zoneid query for each nfvipop, no filter
        try {
 
            zonelist = api.queryComputeResourceZone();
        } catch (ApiException e) {
            System.out.println("ApiException inside queryComputeResourceZone().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //add zoneid to event
        ev.setZoneList(zonelist);
        //Retrieve resource information for each zonid
        for (i = 0; i < zonelist.size(); i++ ) {
            tmpzone = zonelist.get(i).getZoneId();
            tmppop = zonelist.get(i).getNfviPopId();
            
            //Retrieve compute info
            try {
                String obj = "zoneid=" + tmpzone;
                resinfolist = api.queryComputeInformation(obj);
            } catch (ApiException e) {
                System.out.println("ApiException inside queryComputeInformation().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return;
            }
            //query compute capacity
            for (j = 0; j < resinfolist.size(); j++) {
                if (resinfolist.get(j).getVirtualCPU() != null) {
                    //ask capacity for cpu
                    try {
                        reqcap = resinfolist.get(j).getComputeResourceTypeId();
                        caprep = api.queryComputeCapacity(reqcap);
                    } catch (ApiException e) {
                        System.out.println("ApiException inside cpu queryComputeCapacity().");
                        System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                        return;
                    }
                    //Add element to compute list in the event
                    ComputeResElem el = new ComputeResElem();
                    el.setComputeElem(resinfolist.get(j));
                    el.setCapacityElem(caprep);
                    ev.setComputeResElem(el);
                    //create element in map
                    MapResources mapel = new MapResources(zonelist.get(i).getNfviPopId(),
                                zonelist.get(i).getZoneId(),
                                resinfolist.get(j).getComputeResourceTypeId());
                    ev.setCompMapElem(mapel);
                }
                if (resinfolist.get(j).getVirtualMemory() != null) {
                    //ask capacity for memory
                    try {
                        reqcap = resinfolist.get(j).getComputeResourceTypeId();
                        caprep = api.queryComputeCapacity(reqcap);
                    } catch (ApiException e) {
                        System.out.println("ApiException inside memory queryComputeCapacity().");
                        System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                        return;
                    }
                    //Add element to compute list in the event
                    ComputeResElem el = new ComputeResElem();
                    el.setComputeElem(resinfolist.get(j));
                    el.setCapacityElem(caprep);
                    ev.setComputeResElem(el);
                    //create element in map
                    MapResources mapel = new MapResources(zonelist.get(i).getNfviPopId(),
                                zonelist.get(i).getZoneId(),
                                resinfolist.get(j).getComputeResourceTypeId());
                    ev.setCompMapElem(mapel);
                }  
            }
            
//            //Retrieve network info
//            try {
//                Filter NetInfoRequest = new Filter();
//                String obj = "zoneid=" + tmpzone + ";nfvipopid=" + tmppop;
//                NetInfoRequest.filter(obj);
//                netinfolist = apinetinfo.queryNetworkInformation(NetInfoRequest);
//            } catch (ApiException e) {
//                System.out.println("ApiException inside queryNetworkInformation().");
//                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
//                return;
//            }
//            //query compute capacity
//            for (j = 0; j < netinfolist.size(); j++) {
//                
//                //ask capacity for cpu
//                try {
//                    reqnetcap.setNetworkResourceTypeId(netinfolist.get(j).getNetworkResourceTypeId());
//                    caprep = apinet.queryNetworkCapacity(reqnetcap);
//                } catch (ApiException e) {
//                    System.out.println("ApiException inside cpu queryNetworkCapacity().");
//                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
//                    return;
//                }
//                //Add element to compute list in the event
//                NetworkResElem netel = new NetworkResElem();
//                netel.setNetworkElem(netinfolist.get(i));
//                netel.setCapacityElem(caprep);
//                ev.setNetworkResElem(netel);
//                //create element in map
//                MapResources mapel = new MapResources(Long.valueOf(zonelist.get(i).getNfviPopId()),
//                        Long.valueOf(zonelist.get(i).getZoneId()),
//                        Long.valueOf(netinfolist.get(j).getNetworkResourceTypeId()));
//                ev.setNetworkMapElem(mapel);
//                
//            }    
        }
        //send event
        SingletonEventBus.getBus().post(ev);
    }    
}
