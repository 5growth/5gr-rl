/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.radioplugin.sbi;

import com.ericsson.radioplugin.SingletonEventBus;
import com.ericsson.radioplugin.events.abstraction.Parsedomainlist;
import com.ericsson.radioplugin.events.abstraction.StartServer;
import com.ericsson.radioplugin.events.abstraction.RadioAbstractionReply;
import com.ericsson.radioplugin.events.abstraction.RadioAbstractionRequest;
import com.ericsson.radioplugin.nbi.swagger.model.InlineResponse2001;
import com.ericsson.radioplugin.nbi.swagger.model.LocationInfo;
import com.ericsson.radioplugin.nbi.swagger.model.RadioCoverageAreaList;
import com.ericsson.radioplugin.nbi.swagger.model.RadioCoverageAreaListInner;
import com.ericsson.radioplugin.nbi.swagger.model.RadioCoverageAreaListInnerRadioCoverageAreaInfo;
import com.ericsson.radioplugin.sbi.objects.NFVIPop;
import com.ericsson.radioplugin.sbi.objects.RadioCoverageAreaResource;
import com.google.common.eventbus.Subscribe;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class RadioIF {
    private List<NFVIPop> poplist;

    private HashMap<String, RadioCoverageAreaResource> netreslist;
    private int servcounter;
    public RadioIF() {
        poplist = new ArrayList();
        netreslist = new HashMap();
        servcounter = 1;
    }
    
    
    
     //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        System.out.println("RadioIF ---> Parse domlist from xml file");
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        
        poplist = xmldom.getPoplist();
        netreslist = xmldom.getReslist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }
    
    @Subscribe
    public void handle_RadioAbstractionRequest(RadioAbstractionRequest abstrreq) {
        System.out.println("RadioIF ---> retrieve radio coverage area list reqid = " + abstrreq.getReq());
        
        InlineResponse2001 rcaregions = new InlineResponse2001();
        
        RadioCoverageAreaList rcalist = new RadioCoverageAreaList();
                
        
        
                
       for (RadioCoverageAreaResource value : netreslist.values()) {
           RadioCoverageAreaListInner rcalistinner = new RadioCoverageAreaListInner();
           RadioCoverageAreaListInnerRadioCoverageAreaInfo rcainfo = new RadioCoverageAreaListInnerRadioCoverageAreaInfo();
           LocationInfo locinfo = new LocationInfo();
           
           locinfo.setAltitude(new BigDecimal(value.getAltitude()));
           locinfo.setLatitude(new BigDecimal(value.getLatitude()));
           locinfo.setLongitude(new BigDecimal(value.getLongitude()));
           locinfo.setRange(new BigDecimal(value.getRange()));
           rcainfo.setCoverageAreaDelay(new BigDecimal(value.getDelay()));
           rcainfo.setCoverageAreaId(value.getId());
           rcainfo.setCoverageAreaMaxBandwidth(new BigDecimal(value.getMaxbw()));
           rcainfo.setCoverageAreaMinBandwidth(new BigDecimal(value.getMinbw()));
           rcainfo.setLocationInfo(locinfo);
           rcalistinner.setRadioCoverageAreaInfo(rcainfo);
           rcalist.add(rcalistinner);
        }
             
        rcaregions.setRegions(rcalist);
        RadioAbstractionReply wimabstrrep = new RadioAbstractionReply(abstrreq.getReq(),rcaregions);
        System.out.println("RadioIF ---> post RadioAbstractionReply");
        SingletonEventBus.getBus().post(wimabstrrep);
    }
    

}
