/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi;

import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.Parsedomainlist;
import com.ericsson.dummyplugin.events.abstraction.StartServer;
import com.ericsson.dummyplugin.events.abstraction.MECAbstractionReply;
import com.ericsson.dummyplugin.events.abstraction.MECAbstractionRequest;
import com.ericsson.dummyplugin.events.allocate.AppdAllocateReply;
import com.ericsson.dummyplugin.events.allocate.AppdAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.AppdTerminateReply;
import com.ericsson.dummyplugin.events.terminate.AppdTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse200;
import com.ericsson.dummyplugin.nbi.swagger.model.LocationInfo;
import com.ericsson.dummyplugin.nbi.swagger.model.MECRegionInfo;
import com.ericsson.dummyplugin.nbi.swagger.model.MECRegionInfoMecRegionInfo;
import com.ericsson.dummyplugin.nbi.swagger.model.MECTrafficServiceCreationResponse;
import com.ericsson.dummyplugin.sbi.objects.MECRegionResource;
import com.ericsson.dummyplugin.sbi.objects.MECService;

import com.google.common.eventbus.Subscribe;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class MECIF {
    private HashMap<String, MECRegionResource> netreslist;
    private HashMap<String, MECService> netservlist;
    private int servcounter;
    public MECIF() {
        netreslist = new HashMap();
        netservlist = new HashMap();
        servcounter = 1;
    }
    
    
    
     //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        System.out.println("MECIF ---> Parse domlist from xml file");
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        
        netreslist = xmldom.getReslist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }

    @Subscribe
    public void handle_ServiceNetworkAllocateRequest(AppdAllocateRequest servallreq) {
        System.out.println("MECIF ---> allocate mec service request" );

        
        MECService servel = new MECService(servallreq.getRequest(), String.valueOf(servcounter));
        //Insert Network Service in HashMap
        netservlist.put(Long.toString(servcounter), servel);
        MECTrafficServiceCreationResponse rep = new MECTrafficServiceCreationResponse();
        rep.setRequestId(Long.toString(servcounter));
        servcounter++;
        
        AppdAllocateReply servallrep = new AppdAllocateReply(servallreq.getReqid(),
                    rep);
        System.out.println("MECIF ---> post AppdAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
    }
    
    @Subscribe
    public void handle_ServiceNetworkTerminateRequest(AppdTerminateRequest servtermreq) {
        System.out.println("MECIF ---> terminate mec service request" );
        String key = servtermreq.getNetlist();
        MECService servel = netservlist.get(key);
        if (servel != null) {
            netservlist.remove(key);
        }
      
        AppdTerminateReply servtermrep = new AppdTerminateReply(servtermreq.getReqid());
        System.out.println("MECIF ---> post AppdTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }
    
    @Subscribe
    public void handle_MECAbstractionRequest(MECAbstractionRequest abstrreq) {
        System.out.println("MECIF ---> retrieve MEC region abstraction reqid = " + abstrreq.getReq());
        
        InlineResponse200 mecregions = new InlineResponse200();
        
        List<MECRegionInfo> mecreglist = new ArrayList();
       
        
        
                
       for (MECRegionResource value : netreslist.values()) {
           LocationInfo locinfo = new LocationInfo();
           MECRegionInfoMecRegionInfo regioninfo = new MECRegionInfoMecRegionInfo();
           MECRegionInfo mecinfo = new MECRegionInfo();
           locinfo.setAltitude(new BigDecimal(value.getAltitude()));
           locinfo.setLatitude(new BigDecimal(value.getLatitude()));
           locinfo.setLongitude(new BigDecimal(value.getLongitude()));
           locinfo.setRange(new BigDecimal(value.getRange()));
           regioninfo.setLocationInfo(locinfo);
           regioninfo.setRegionId(value.getId());
           mecinfo.setMecRegionInfo(regioninfo);
           mecreglist.add(mecinfo);
        }
             
        mecregions.setRegions(mecreglist);
        MECAbstractionReply wimabstrrep = new MECAbstractionReply(abstrreq.getReq(),mecregions);
        System.out.println("MECIF ---> post WIMAbstractionReply");
        SingletonEventBus.getBus().post(wimabstrrep);
    }
    

}
