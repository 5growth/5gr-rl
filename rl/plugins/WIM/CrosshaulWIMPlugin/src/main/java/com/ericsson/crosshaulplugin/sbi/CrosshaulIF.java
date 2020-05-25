/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.crosshaulplugin.sbi;

import com.ericsson.crosshaulplugin.SingletonEventBus;
import com.ericsson.crosshaulplugin.events.abstraction.Parsedomainlist;
import com.ericsson.crosshaulplugin.events.abstraction.StartServer;
import com.ericsson.crosshaulplugin.events.abstraction.WIMAbstractionReply;
import com.ericsson.crosshaulplugin.events.abstraction.WIMAbstractionRequest;
import com.ericsson.crosshaulplugin.events.allocate.ServiceNetworkAllocateReply;
import com.ericsson.crosshaulplugin.events.allocate.ServiceNetworkAllocateRequest;
import com.ericsson.crosshaulplugin.events.terminate.ServiceNetworkTerminateReply;
import com.ericsson.crosshaulplugin.events.terminate.ServiceNetworkTerminateRequest;
import com.ericsson.crosshaulplugin.nbi.swagger.model.AllocateReply;
import com.ericsson.crosshaulplugin.nbi.swagger.model.Gateways;
import com.ericsson.crosshaulplugin.nbi.swagger.model.GatewaysInner;
import com.ericsson.crosshaulplugin.nbi.swagger.model.GatewaysInnerGatewayAttributes;
import com.ericsson.crosshaulplugin.nbi.swagger.model.GatewaysInnerGatewayAttributesNetworkConnectivityEndpoint;
import com.ericsson.crosshaulplugin.nbi.swagger.model.InlineResponse200;
import com.ericsson.crosshaulplugin.nbi.swagger.model.NetworkIds;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualLinks;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualLinksInner;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualLinksInnerVirtualLink;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualLinksInnerVirtualLinkNetworkQoS;
import com.ericsson.crosshaulplugin.sbi.objects.NFVIPop;
import com.ericsson.crosshaulplugin.sbi.objects.NetworkResource;
import com.ericsson.crosshaulplugin.sbi.objects.RANService;
import com.ericsson.crosshaulplugin.sbi.objects.NetworkService;
import com.ericsson.crosshaulplugin.sbi.objects.Zone;
import com.google.common.eventbus.Subscribe;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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
    private HashMap<String, RANService> ranreslist;
    private HashMap<String, NetworkService> netservlist;
    private int servcounter;
    public CrosshaulIF() {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        netreslist = new HashMap();
        ranreslist = new HashMap();
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
        ranreslist = xmldom.getRaninfolist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }
    

    @Subscribe
    public void handle_ServiceNetworkAllocateRequest(ServiceNetworkAllocateRequest servallreq) {
        System.out.println("CrossHaulIF ---> allocate network service request");
        DatagramSocket dstx, dsrx;
        InetAddress ip;
        DatagramPacket dptx, dprx;

        NetworkResource servel = netreslist.get(servallreq.getRequest().getWanLinkId());
        RANService ranel = ranreslist.get(servallreq.getRequest().getWanLinkId());

        if ((servel == null) || (ranel == null)) {
            System.out.println("CrossHaulIF ---> no network resource found for service. resid = " + servallreq.getRequest().getWanLinkId());
            ServiceNetworkAllocateReply servallrep = new ServiceNetworkAllocateReply(servallreq.getReqid(), null);
            System.out.println("CrossHaulIF ---> post ServiceNetworkAllocateReply");
            SingletonEventBus.getBus().post(servallrep);
            return;
        }

//        try {
//            dstx = new DatagramSocket();
//        } catch (SocketException e) {
//            System.out.println("CrossHaulIF ---> error open tx socket");
//            System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//            return;
//        }
//        try {
//            Integer rxport = Integer.parseInt(ranel.getRxport());
//            dsrx = new DatagramSocket(rxport);
//        } catch (SocketException e) {
//            System.out.println("CrossHaulIF ---> error open rx socket");
//            System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//            return;
//        }
//        String txstr = "SERVICECONFREQ@" + ranel.getProvId() + "," + ranel.getTenId() + ","
//                + ranel.getServId() + "," + ranel.getSrcId() + "," + ranel.getDstId()
//                + "," + ranel.getQosId();
//        try {
//            ip = InetAddress.getByName(ranel.getIp());
//        } catch (UnknownHostException e) {
//            System.out.println("CrossHaulIF ---> error format ip socket");
//            System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//            return;
//        }
//        byte[] buf = new byte[1024];
//        dprx = new DatagramPacket(buf, 1024);
//        Integer txport = Integer.parseInt(ranel.getTxport());
//        dptx = new DatagramPacket(txstr.getBytes(), txstr.length(), ip, txport);
//        try {
//            dstx.send(dptx);
//        } catch (IOException e) {
//            System.out.println("CrossHaulIF ---> error sending allocate command");
//            System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//            return;
//        }
//        try {
//            dsrx.receive(dprx);
//        } catch (IOException e) {
//            System.out.println("CrossHaulIF ---> error receiving allocate command");
//            System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//            return;
//        }
//        String rxstr = new String(dprx.getData(), 0, dprx.getLength());
//        String[] tok1 = rxstr.split("@");
//        String[] tok2 = tok1[1].split(",");
//        dstx.close();
//        dsrx.close();

        long totbw = Long.parseLong(servel.getTotcap());
        long bw = servallreq.getRequest().getBandwidth().longValue();
        NetworkService netservel = new NetworkService(Long.toString(servcounter), Long.toString(bw), servel.getDelay(), servel.getId(), servallreq.getRequest().getMetadata());

        //TODO: Send allocation service to CROSSHAUL Control 
        //scale bw from link
        long allbw = Long.parseLong(servel.getAllcap());
        long freebw = totbw - bw;
        allbw += bw;
        //Update link
        servel.setFreecap(Long.toString(freebw));
        servel.setAllcap(Long.toString(allbw));

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
        RANService ranel = ranreslist.get(key);
        DatagramSocket dstx, dsrx;
        InetAddress ip;
        DatagramPacket dptx, dprx;
        if ((servel != null) && (ranel != null)) {

//             try {
//                 dstx = new DatagramSocket();
//             } catch (SocketException e) {
//                 System.out.println("CrossHaulIF ---> error open tx socket");
//                 System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//                 return;
//             } 
//             try {
//                 Integer rxport = Integer.parseInt(ranel.getRxport());
//                 dsrx = new DatagramSocket(rxport);
//             } catch (SocketException e) {
//                 System.out.println("CrossHaulIF ---> error open rx socket");
//                 System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//                 return;
//             } 
//            String txstr = "SERVICECREMREQ@" + ranel.getProvId() + "," + ranel.getTenId() + "," +
//                    ranel.getServId() + "," + ranel.getSrcId() + "," + ranel.getDstId() + 
//                    "," + ranel.getQosId();
//            try {
//                ip = InetAddress.getByName(ranel.getIp());
//            } catch (UnknownHostException e) {
//                System.out.println("CrossHaulIF ---> error format ip socket");
//                System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//                return;
//            }
//            byte[] buf = new byte[1024];  
//            dprx = new DatagramPacket(buf, 1024);
//            Integer txport = Integer.parseInt(ranel.getTxport());
//            dptx = new DatagramPacket(txstr.getBytes(), txstr.length(), ip, txport);
//            try {
//                dstx.send(dptx);
//            } catch (IOException e) {
//               System.out.println("CrossHaulIF ---> error sending allocate command");
//               System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//               return; 
//            }
//            try {
//                dsrx.receive(dprx); 
//            } catch (IOException e) {
//               System.out.println("CrossHaulIF ---> error receiving allocate command");
//               System.out.println("CrossHaulIF ---> error message" + e.getMessage());
//               return; 
//            }
//            String rxstr = new String(dprx.getData(), 0, dprx.getLength()); 
//            String[] tok1 = rxstr.split("@");
//            String[] tok2 = tok1[1].split(",");
//            dstx.close();
//            dsrx.close();                       
            
            NetworkResource el = netreslist.get(servel.getNetresid());
//            if ((el != null) && (tok2[0].compareTo("OK") == 0)) {
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
        
        InlineResponse200 abstrrep = new InlineResponse200();
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
