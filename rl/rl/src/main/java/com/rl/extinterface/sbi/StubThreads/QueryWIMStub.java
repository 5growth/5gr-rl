/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.common.objects.MapResources;
import com.rl.common.objects.NetworkResElem;
import com.rl.events.abstraction.Creation.WIMResAbstractionEvent;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkAttributes;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//import com.mtp.extinterface.nbi.swagger.model.ResourceInfo;

/**
 *
 * @author efabuba
 */
public class QueryWIMStub extends Thread {

    private DomainElem dominfo;

    public QueryWIMStub(DomainElem val) {
        dominfo = val;
    }

    @Override
    public void run() {
        List<NfviPop> poplist = new ArrayList();
        List<ResourceZone> zonelist = new ArrayList();

        List<VirtualNetworkResourceInformation> netinfolist = new ArrayList();
        List<LogicalLinkAttributes> linkinfolist = new ArrayList();
        int i, j;
        String tmpzone, tmppop;
        CapacityInformation caprep;

        WIMResAbstractionEvent ev = new WIMResAbstractionEvent(dominfo.getId());

        NfviPop popel = new NfviPop();
        popel.setGeographicalLocationInfo("NetworkFR-E-I");
        popel.setNetworkConnectivityEndpoint("83951617;84017153;84082689;84148225"); //("5.1.0.1;5.2.0.1;5.3.0.1;5.4.0.1")
        popel.setNfviPopId("51");//DomId5|Pop1
        popel.setVimId("51");

        poplist.add(popel);
        //add nfvipop to event
        ev.setPopList(poplist);

        ResourceZone zonel = new ResourceZone();
        zonel.setNfviPopId("51"); //DomId5|PopId1
        zonel.setZoneId("511");//DomId5|PopId1|Zone1
        zonel.setZoneName("NetwFR-E-I");
        zonel.setZoneProperty("Power4");
        zonel.setZoneState("enable");
        zonelist.add(zonel);

        //add zoneid to event
        ev.setZoneList(zonelist);
        //Retrieve resource information for each zonid
        for (i = 0; i < zonelist.size(); i++) { 
            tmpzone = zonelist.get(i).getZoneId();
            tmppop = zonelist.get(i).getNfviPopId();
            //Retrieve network info
            //LINK R1(GW1-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-I");
                qos.setQosValue("4");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511011");//Dom5|Pop1|Zone1|Resource1|Subres1  -> Resource1=01, Resource2=02, ... , Resource10=10 
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setDstGwIpAddress("84082689");//Dom5|Gw3|0|1  - "5.3.0.1"
                resInfo.setLocalLinkId(101011);//10|10|R1|Gw1
                resInfo.setRemoteLinkId(101013);//10|10|R1|Gw3
                resInfo.setLogicalLinkId("511011");
                linkinfolist.add(resInfo);
                //resnetel.setResInfo(dominfo);
                netinfolist.add(resnetel);
            }
            //LINK R1(GW3-GW1)(See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-I");
                qos.setQosValue("4");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511012");//Dom5|Pop1|Zone1|R1|Subres2 -> R1=01, R2=02, ... , Resource10=10 
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84082689");//Dom5|Gw3|0|1 "5.3.0.1"
                resInfo.setDstGwIpAddress("83951617");//Dom5|Gw1|0|1 "5.1.0.1"
                resInfo.setLocalLinkId(101013);//10|10|R1|Gw3
                resInfo.setRemoteLinkId(101011);//10|10|R1|Gw1
                resInfo.setLogicalLinkId("511012");
                linkinfolist.add(resInfo);
                linkinfolist.add(resInfo);
                //resnetel.setResInfo(dominfo);
                netinfolist.add(resnetel);
            }

            //LINK R2(GW1-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511021"); //Dom5|Pop1|Zone1|R2|Subres1
                resnetel.setNetworkQoS(qoslist);
                  resnetel.setNetworkType("mpls");
                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("83951617"); //"5.1.0.1"
                resInfo.setDstGwIpAddress("84082689"); //"5.3.0.1"
                resInfo.setLocalLinkId(101021);//10|10|R2|Gw1
                resInfo.setRemoteLinkId(101023);//10|10|R2|Gw3
                resInfo.setLogicalLinkId("511021");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);


               

                netinfolist.add(resnetel);
            }
            //LINK R2(GW3-GW1) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511022"); //Dom5|Pop1|Zone1|R2|Subres2
                resnetel.setNetworkQoS(qoslist);
                  resnetel.setNetworkType("mpls");
                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84082689"); //"5.3.0.1"
                resInfo.setDstGwIpAddress("83951617"); //"5.1.0.1"
                resInfo.setLocalLinkId(101023);//10|10|R2|Gw3
                resInfo.setRemoteLinkId(101021);//10|10|R2|Gw1
                resInfo.setLogicalLinkId("511022");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                

                netinfolist.add(resnetel);
            }

            //LINK R3(GW1-GW4) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511031"); //Dom5|Pop1|Zone1|R3|Subres1
                resnetel.setNetworkQoS(qoslist);
                  resnetel.setNetworkType("mpls");
               // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setDstGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setLocalLinkId(101031);//10|10|R3|Gw1
                resInfo.setRemoteLinkId(101034);//10|10|R3|Gw4
                resInfo.setLogicalLinkId("511031");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
                
                netinfolist.add(resnetel);
            }
            //LINK R3(GW4-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511032"); //Dom5|Pop1|Zone1|R3|Subres2
                resnetel.setNetworkQoS(qoslist);
                  resnetel.setNetworkType("mpls");
                     // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setDstGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setLocalLinkId(101034);//10|10|R3|Gw4
                resInfo.setRemoteLinkId(101031);//10|10|R3|Gw1
                resInfo.setLogicalLinkId("511032");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
           
                netinfolist.add(resnetel);
            }

            //LINK R4(GW1-GW4) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511041"); //Dom5|Pop1|Zone1|R4|Subres1
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
                    // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setDstGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setLocalLinkId(101041);//10|10|R4|Gw1
                resInfo.setRemoteLinkId(101044);//10|10|R4|Gw4
                resInfo.setLogicalLinkId("511041");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
            
                netinfolist.add(resnetel);
            }
            //LINK R4(GW4-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511042"); //Dom5|Pop1|Zone1|R4|Subres2
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
                        // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setDstGwIpAddress("83951617");//Dom5|Gw1|0|1 -  "5.1.0.1"
                resInfo.setLocalLinkId(101044);//10|10|R4|Gw4
                resInfo.setRemoteLinkId(101041);//10|10|R4|Gw1
                resInfo.setLogicalLinkId("511042");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
        
                netinfolist.add(resnetel);
            }

            //LINK R5(GW1-GW2) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511051"); //Dom5|Pop1|Zone1|R5|Subres1
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                       // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setDstGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setLocalLinkId(101051);//10|10|R5|Gw1
                resInfo.setRemoteLinkId(101052);//10|10|R5|Gw2
                resInfo.setLogicalLinkId("511051");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
         
                netinfolist.add(resnetel);
            }
            //LINK R5(GW2-GW1) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511052"); //Dom5|Pop1|Zone1|R5|Subres2 
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                       // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setDstGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setLocalLinkId(101052);//10|10|R5|Gw2
                resInfo.remoteLinkId(101051);//10|10|R5|Gw1
                resInfo.setLogicalLinkId("511052");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
         
                netinfolist.add(resnetel);
            }

            //LINK R6(GW1-GW2) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511061"); //Dom5|Pop1|Zone1|R6|Subres1
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
                    // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setDstGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setLocalLinkId(101061);//10|10|R6|Gw1
                resInfo.setRemoteLinkId(101062);//10|10|R6|Gw2
                resInfo.setLogicalLinkId("511061");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
            
                netinfolist.add(resnetel);
            }
            //LINK R6(GW2-GW1) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511062"); //Dom5|Pop1|Zone1|R6|Subres2 
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                          // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setDstGwIpAddress("83951617");//Dom5|Gw1|0|1 - "5.1.0.1"
                resInfo.setLocalLinkId(101062);//10|10|R6|Gw2
                resInfo.setRemoteLinkId(101061);//10|10|R6|Gw1
                resInfo.setLogicalLinkId("511062");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
      
                netinfolist.add(resnetel);
            }

            //LINK R7(GW2-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511071"); //Dom5|Pop1|Zone1|R7|Subres1
                resnetel.setNetworkQoS(qoslist);
            resnetel.setNetworkType("mpls");
                     // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setDstGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setLocalLinkId(101072);//10|10|R7|Gw2
                resInfo.setRemoteLinkId(101073);//10|10|R7|Gw3
                resInfo.setLogicalLinkId("511071");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
           
                netinfolist.add(resnetel);
            }
            //LINK R7(GW3-GW2) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511072"); //Dom5|Pop1|Zone1|R7|Subres2 
                resnetel.setNetworkQoS(qoslist);
         resnetel.setNetworkType("mpls");
                      // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setDstGwIpAddress("84017153");//Dom5|Gw2|0|1 - 5.2.0.1"
                resInfo.setLocalLinkId(101073);//10|10|R7|Gw3
                resInfo.setRemoteLinkId(101072);//10|10|R7|Gw2
                resInfo.setLogicalLinkId("511072");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
          
                netinfolist.add(resnetel);
            }

            //LINK R8(GW2-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511081"); //Dom5|Pop1|Zone1|R8|Subres1
                resnetel.setNetworkQoS(qoslist);
             resnetel.setNetworkType("mpls");
                                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setDstGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setLocalLinkId(101082);//10|10|R8|Gw2
                resInfo.setRemoteLinkId(101083);//10|10|R8|Gw3
                resInfo.setLogicalLinkId("511081");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                

                netinfolist.add(resnetel);
            }
            //LINK R8(GW3-GW2) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511082"); //Dom5|Pop1|Zone1|R8|Subres2 
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
                     // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setDstGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setLocalLinkId(101083);//10|10|R8|Gw3
                resInfo.setRemoteLinkId(101082);//10|10|R8|Gw2
                resInfo.setLogicalLinkId("511082");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
           
                netinfolist.add(resnetel);
            }

            //LINK R9(GW2-GW4) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511091"); //Dom5|Pop1|Zone1|R9|Subres1
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                         // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setDstGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setLocalLinkId(101092);//10|10|R9|Gw2
                resInfo.setRemoteLinkId(101094);//10|10|R9|Gw4
                resInfo.setLogicalLinkId("511091");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
       
                netinfolist.add(resnetel);
            }
            //LINK R9(GW4-GW2) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511092"); //Dom5|Pop1|Zone1|R9|Subres2 
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
              
                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setDstGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setLocalLinkId(101094);//10|10|R9|Gw4
                resInfo.setRemoteLinkId(101092);//10|10|R9|Gw2
                resInfo.setLogicalLinkId("511092");;
                linkinfolist.add(resInfo);
//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
  
                netinfolist.add(resnetel);
            }

            //LINK R10(GW2-GW4) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511101"); //Dom5|Pop1|Zone1|R10|Subres1
                resnetel.setNetworkQoS(qoslist);
                 resnetel.setNetworkType("mpls");
                      // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setDstGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setLocalLinkId(1010102);//10|10|R10|Gw2
                resInfo.setRemoteLinkId(1010104);//10|10|R10|Gw4
                resInfo.setLogicalLinkId("511101");;
                linkinfolist.add(resInfo);
//
//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
          
                netinfolist.add(resnetel);
            }
            //LINK R10(GW4-GW2) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511102"); //Dom5|Pop1|Zone1|R10|Subres2 
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
                       // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setDstGwIpAddress("84017153");//Dom5|Gw2|0|1 - "5.2.0.1"
                resInfo.setLocalLinkId(1010104);//10|10|R10|Gw4
                resInfo.setRemoteLinkId(1010102);//10|10|R10|Gw2
                resInfo.setLogicalLinkId("511102");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
         
                netinfolist.add(resnetel);
            }

            //LINK R11(GW4-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511111"); //Dom5|Pop1|Zone1|R11|Subres1
                resnetel.setNetworkQoS(qoslist);
                resnetel.setNetworkType("mpls");
                     // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84148225");//Dom5|Gw4|0|1  - "5.4.0.1"
                resInfo.setDstGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setLocalLinkId(1010114);//10|10|R11|Gw4
                resInfo.setRemoteLinkId(1010113);//10|10|R11|Gw3
                resInfo.setLogicalLinkId("511111");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
           
                netinfolist.add(resnetel);
            }
            //LINK R11(GW3-GW4) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511112"); //Dom5|Pop1|Zone1|R11|Subres2 
                resnetel.setNetworkQoS(qoslist);
               resnetel.setNetworkType("mpls");
                    // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setDstGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setLocalLinkId(1010113);//10|10|R11|Gw3
                resInfo.setRemoteLinkId(1010114);//10|10|R11|Gw4
                resInfo.setLogicalLinkId("511112");;
                linkinfolist.add(resInfo);
//
//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
            
                netinfolist.add(resnetel);
            }

            //LINK R12(GW4-GW3) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511121"); //Dom5|Pop1|Zone1|R12|Subres1
                resnetel.setNetworkQoS(qoslist);
              
                        resnetel.setNetworkType("mpls");
                // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setDstGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setLocalLinkId(1010124);//10|10|R12|Gw4
                resInfo.setRemoteLinkId(1010123);//10|10|R12|Gw3
                resInfo.setLogicalLinkId("511121");;
                linkinfolist.add(resInfo);
//
//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
          
                netinfolist.add(resnetel);
            }
            //LINK R12(GW3-GW4) (See WAN topology)
            {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List <AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delayE-Fr");
                qos.setQosValue("5");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("511122"); //Dom5|Pop1|Zone1|R12|Subres2 
                resnetel.setNetworkQoS(qoslist);
              resnetel.setNetworkType("mpls");
                          // Below fields need exstension
                LogicalLinkAttributes resInfo = new LogicalLinkAttributes();
                resInfo.setSrcGwIpAddress("84082689");//Dom5|Gw3|0|1 - "5.3.0.1"
                resInfo.setDstGwIpAddress("84148225");//Dom5|Gw4|0|1 - "5.4.0.1"
                resInfo.setLocalLinkId(1010123);//10|10|R12|Gw3
                resInfo.setRemoteLinkId(1010124);//10|10|R12|Gw4
                resInfo.setLogicalLinkId("511122");;
                linkinfolist.add(resInfo);

//                resnetel = new VirtualNetworkResourceInformation();
//                qos = new VirtualNetworkResourceInformationNetworkQoS();
//                qos.setQosName("delayI-Fr");
//                qos.setQosValue("5");
//                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
//                resnetel.setNetworkResourceTypeId("51103");
//                resnetel.setNetworkQoS(qos);
                
      
                netinfolist.add(resnetel);
            }

            //query compute capacity
            for (j = 0; j < netinfolist.size(); j++) {

                caprep = new CapacityInformation();
                caprep.setAllocatedCapacity("0");
                caprep.setAvailableCapacity("10000000");
                caprep.setReservedCapacity("0");
                caprep.setTotalCapacity("10000000");
                //Add element to compute list in the event
                NetworkResElem netel = new NetworkResElem();
                netel.setNetworkElem(netinfolist.get(j));
                netel.setCapacityElem(caprep);
                netel.setLinkinfo(linkinfolist.get(j));
                ev.setNetworkResElem(netel);
                //create element in map
                MapResources mapel = new MapResources(zonelist.get(i).getNfviPopId(),
                        zonelist.get(i).getZoneId(),
                        netinfolist.get(j).getNetworkResourceTypeId());
                ev.setNetworkMapElem(mapel);
            }//Loop on virtual network resources 
        } //Loop on ZoneList
        //send event
        SingletonEventBus.getBus().post(ev);
    }
}
