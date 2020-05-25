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
import com.rl.events.abstraction.Creation.RadioResAbstractionEvent;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2001;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2002;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2003;
import com.rl.extinterface.nbi.swagger.model.MFInfo;
import com.rl.extinterface.nbi.swagger.model.MFlistInner;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.PNFInfo;
import com.rl.extinterface.nbi.swagger.model.PNFlistInner;
import com.rl.extinterface.nbi.swagger.model.RadioCoverageAreaListInner;
import com.rl.extinterface.nbi.swagger.model.RadioCoverageAreaListInnerRadioCoverageAreaInfo;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.RadioResourcesApi;
import io.swagger.client.api.VimComputeResourcesApi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class QueryRadioThread extends Thread {
    private DomainElem dominfo;
    public QueryRadioThread (DomainElem val) {
        dominfo = val;
    }
    
    @Override
    public void run() {
       
        
        
        RadioResAbstractionEvent ev = new RadioResAbstractionEvent(dominfo.getId());
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
        RadioResourcesApi radioapi = new RadioResourcesApi(capi);

        List<NfviPop> poplist = new ArrayList(); 
        List<ResourceZone> zonelist= new ArrayList();
        List<VirtualComputeResourceInformation> resinfolist = new ArrayList();
        List<VirtualNetworkResourceInformation> netinfolist = new ArrayList();
        int count, i, j;
        String tmpzone, tmppop;
        String reqcap = new String();
        CapacityInformation caprep = new CapacityInformation();
        

        //Retrieve nfvipop query, no filter
        try {
            poplist = api.queryNFVIPoPComputeInformation();
        } catch (ApiException e) {
            System.out.println("ApiException inside queryNFVIPoPComputeInformation()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //add nfvipop to event
        ev.setPoplist(poplist);
        //Retrieve zoneid query for each nfvipop, no filter
        try {
 
            zonelist = api.queryComputeResourceZone();
        } catch (ApiException e) {
            System.out.println("ApiException inside queryComputeResourceZone().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //add zoneid to event
        ev.setZonelist(zonelist);
        //Retrieve resource information for each zoneid
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
        
        //Perform coverage area call
        InlineResponse2001 covarea = new InlineResponse2001();
        //Retrieve coverage area query
        try {
            covarea = radioapi.collectRadioCoverageareaInformation();
        } catch (ApiException e) {
            System.out.println("ApiException inside collectRadioCoverageareaInformation()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        if (covarea.getRegions().isEmpty() == false) {
             List<RadioCoverageAreaListInnerRadioCoverageAreaInfo>covlist = new ArrayList();
             
             Iterator<RadioCoverageAreaListInner> coviter = covarea.getRegions().iterator();
             while (coviter.hasNext()) {
                 RadioCoverageAreaListInner el = coviter.next();
                 covlist.add(el.getRadioCoverageAreaInfo());
             }
             ev.setRadioinfolist(covlist);
        }        
        //Perform PNFList call
        InlineResponse2002 pnfs = new InlineResponse2002();
        //Retrieve pnf  query
        try {
            pnfs = radioapi.collectRadioPnflist();
        } catch (ApiException e) {
            System.out.println("ApiException inside collectRadioPnflist()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        if (pnfs.getPnFList().isEmpty() == false) {
             List<PNFInfo>pnflist = new ArrayList();
             
             Iterator<PNFlistInner> pnfiter = pnfs.getPnFList().iterator();
             while (pnfiter.hasNext()) {
                 PNFlistInner el = pnfiter.next();
                 pnflist.add(el.getPnfinfo());
             }
             ev.setPnflist(pnflist);
        }  
        //Perform MFlist calls
        InlineResponse2003 mfs = new InlineResponse2003();
        //Retrieve pnf  query
        try {
            mfs = radioapi.collectRadioMflist();
        } catch (ApiException e) {
            System.out.println("ApiException inside collectRadioMflist()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        if (mfs.getMfList().isEmpty() == false) {
             List<MFInfo>mflist = new ArrayList();
             
             Iterator<MFlistInner> mfiter = mfs.getMfList().iterator();
             while (mfiter.hasNext()) {
                 MFlistInner el = mfiter.next();
                 mflist.add(el.getMfinfo());
             }
             ev.setMflist(mflist);
        }
        
        //send event
        SingletonEventBus.getBus().post(ev);
    }    
}
