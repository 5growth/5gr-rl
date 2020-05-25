/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi;

import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.Parsedomainlist;
import com.ericsson.dummyplugin.events.abstraction.StartServer;
import com.ericsson.dummyplugin.events.abstraction.WIMAbstractionReply;
import com.ericsson.dummyplugin.events.abstraction.WIMAbstractionRequest;
import com.ericsson.dummyplugin.events.allocate.ServiceNetworkAllocateReply;
import com.ericsson.dummyplugin.events.allocate.ServiceNetworkAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.ServiceNetworkTerminateReply;
import com.ericsson.dummyplugin.events.terminate.ServiceNetworkTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateReply;
import com.ericsson.dummyplugin.nbi.swagger.model.Gateways;
import com.ericsson.dummyplugin.nbi.swagger.model.GatewaysInner;
import com.ericsson.dummyplugin.nbi.swagger.model.GatewaysInnerGatewayAttributes;
import com.ericsson.dummyplugin.nbi.swagger.model.GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint;
import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2004;
import com.ericsson.dummyplugin.nbi.swagger.model.NetworkIds;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualLinks;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualLinksInner;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualLinksInnerVirtualLink;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualLinksInnerVirtualLinkNetworkQoS;
import com.ericsson.dummyplugin.sbi.objects.NFVIPop;
import com.ericsson.dummyplugin.sbi.objects.NetworkResource;
import com.ericsson.dummyplugin.sbi.objects.NetworkService;
import com.ericsson.dummyplugin.sbi.objects.Zone;
import com.google.common.eventbus.Subscribe;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class CrosshaulIF {
    private List<NFVIPop> poplist;
    private List<Zone> zonelist;
    private HashMap<String, NetworkResource> netreslist;
    private HashMap<String, NetworkService> netservlist;
    private int servcounter;
    public CrosshaulIF() {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        netreslist = new HashMap();
        netservlist = new HashMap();
        servcounter = 1;
    }
    
    
    
     //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        System.out.println("CrossHaulIF ---> Parse domlist from xml file");
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        
        poplist = xmldom.getPoplist();
        zonelist = xmldom.getZonelist();
        netreslist = xmldom.getReslist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }

    @Subscribe
    public void handle_ServiceNetworkAllocateRequest(ServiceNetworkAllocateRequest servallreq) {
        System.out.println("CrossHaulIF ---> allocate network service request" );

        NetworkResource el = netreslist.get(servallreq.getRequest().getWanLinkId());
        
        if (el == null) {
            System.out.println("CrossHaulIF ---> no network resource found for service. resid = " + servallreq.getRequest().getWanLinkId());
            ServiceNetworkAllocateReply servallrep = new ServiceNetworkAllocateReply(servallreq.getReqid(), null);
            System.out.println("CrossHaulIF ---> post ServiceNetworkAllocateReply");
            SingletonEventBus.getBus().post(servallrep);
            return;
        }
        long totbw = Long.parseLong(el.getTotcap());
        long bw = servallreq.getRequest().getBandwidth().longValue();
        NetworkService netservel = new NetworkService(Long.toString(servcounter), Long.toString(bw), el.getDelay(), el.getId(), servallreq.getRequest().getMetadata());
        
        //scale bw from link
        long allbw = Long.parseLong(el.getAllcap()); 
        long freebw = totbw - bw;
        allbw += bw;
        //Update link
        el.setFreecap(Long.toString(freebw));
        el.setAllcap(Long.toString(allbw));
        
        //Insert Network Service in HashMap
        netservlist.put(Long.toString(servcounter), netservel);
        AllocateReply rep = new AllocateReply();
        rep.setSegmentType(Long.toString(servcounter));
        rep.setNetworkType(servallreq.getRequest().getSegmentType());
        rep.setNetworkId(Long.toString(servcounter));
        servcounter++;
        
        ServiceNetworkAllocateReply servallrep = new ServiceNetworkAllocateReply(servallreq.getReqid(),
                    rep);
        System.out.println("CrossHaulIF ---> post ServiceNetworkAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
    }
    
    @Subscribe
    public void handle_ServiceNetworkTerminateRequest(ServiceNetworkTerminateRequest servtermreq) {
        System.out.println("CrossHaulIF ---> terminate network service request" );
        List<NetworkIds> resplist = new ArrayList();
        String key = servtermreq.getNetlist();
        NetworkService servel = netservlist.get(key);
        if (servel != null) {
            NetworkResource el = netreslist.get(servel.getNetresid());
            if (el != null) {
                long totbw = Long.parseLong(el.getTotcap());
                long bw = Long.parseLong(servel.getBw());
                long allbw = Long.parseLong(el.getAllcap());
                //scale bw from link
                long freebw = totbw + bw;
                allbw -= bw;
                //Update link
                el.setFreecap(Long.toString(freebw));
                el.setAllcap(Long.toString(allbw));
            }
            NetworkIds respel = new NetworkIds();
            respel.setNetworkId(key);
            resplist.add(respel);
            netservlist.remove(key);
        }
      
        ServiceNetworkTerminateReply servtermrep = new ServiceNetworkTerminateReply(servtermreq.getReqid(), resplist);
        System.out.println("CrossHaulIF ---> post ServiceNetworkTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }
    
    @Subscribe
    public void handle_WIMAbstractionRequest(WIMAbstractionRequest abstrreq) {
        System.out.println("CrossHaulIF ---> retrieve abstraction reqid = " + abstrreq.getReq());
        
        InlineResponse2004 abstrrep = new InlineResponse2004();
        Gateways gwlist = new Gateways();
        VirtualLinks linklist = new VirtualLinks();
        
        String[] tok = poplist.get(0).getEndpoints().split(";");
        for (String token : tok) {
            GatewaysInner el = new GatewaysInner();
            GatewaysInnerGatewayAttributes elattr = new GatewaysInnerGatewayAttributes();
            List<GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint> endplist = new ArrayList();
            elattr.setGatewayId(token);
            elattr.setGeographicalLocationInfo(poplist.get(0).getLocation());
            elattr.setWimId(poplist.get(0).getVim());
            GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint endp = new GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint();
            endp.setNetGwIpAddress(token);
            endplist.add(endp);
            elattr.setNetworkConnectivityEndpoint(endplist);
            el.setGatewayAttributes(elattr);
            gwlist.add(el);
        }
        
        for (NetworkResource value : netreslist.values()) {
           VirtualLinksInner linkel = new VirtualLinksInner();
           VirtualLinksInnerVirtualLink linkattr = new VirtualLinksInnerVirtualLink();
           VirtualLinksInnerVirtualLinkNetworkQoS qos = new VirtualLinksInnerVirtualLinkNetworkQoS();
            qos.setLinkCostValue(BigDecimal.ONE);
            qos.setPacketLossRate(BigDecimal.ZERO);
            String valstr = value.getDelay();
            qos.setLinkDelayValue(new BigDecimal(value.getDelay()));
            linkattr.setNetworkQoS(qos);
            linkattr.setAvailableBandwidth(new BigDecimal(value.getFreecap()));
            linkattr.setDstGwId(value.getDstip());
            linkattr.setDstLinkId(Integer.parseInt(value.getRemid()));
            linkattr.setNetworkLayer(value.getType());
            linkattr.setSrcGwId(value.getSrcip());
            linkattr.setSrcLinkId(Integer.parseInt(value.getLocid()));
            linkattr.setTotalBandwidth(new BigDecimal(value.getTotcap()));
            linkattr.setVirtualLinkId(value.getId());
            linkel.setVirtualLink(linkattr);
            linklist.add(linkel);
        }
             
        abstrrep.setGateways(gwlist);
        abstrrep.setVirtualLinks(linklist);
        WIMAbstractionReply wimabstrrep = new WIMAbstractionReply(abstrreq.getReq(),abstrrep);
        System.out.println("CrossHaulIF ---> post WIMAbstractionReply");
        SingletonEventBus.getBus().post(wimabstrrep);
    }
    

}
