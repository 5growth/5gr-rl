/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.ComputeResElem;
import com.rl.common.objects.NetworkResElem;
import com.rl.common.objects.DomainElem;
import com.rl.common.objects.MapResources;
import com.rl.events.abstraction.Creation.VIMResAbstractionEvent;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.QueryComputeCapacityRequest;
import com.rl.extinterface.nbi.swagger.model.QueryNetworkCapacityRequest;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class QueryVIMStub extends Thread {
    private DomainElem dominfo;
    private final long dom1 = 1;
    private final long dom2 = 2;
    private final long dom3 = 3;
    private final long dom4 = 4;
    private final long dom5 = 5;
    
    public  QueryVIMStub (DomainElem val) {
        dominfo = val;
    }
    
    
    @Override
    public void run() {
        
        List<NfviPop> poplist = new ArrayList(); 
        List<ResourceZone> zonelist= new ArrayList();
        List<VirtualComputeResourceInformation> resinfolist = new ArrayList();
        List<VirtualNetworkResourceInformation> netinfolist = new ArrayList();
        int count, i, j;
        String tmpzone, tmppop;
        QueryComputeCapacityRequest reqcap = new QueryComputeCapacityRequest();
        QueryNetworkCapacityRequest reqnetcap = new QueryNetworkCapacityRequest();
        CapacityInformation caprep = new CapacityInformation();
        
        VIMResAbstractionEvent ev = new VIMResAbstractionEvent(dominfo.getId());
        
        if (dominfo.getId() == dom1)  { 
            NfviPop popel = new NfviPop();
            popel.setGeographicalLocationInfo("Spain");
            popel.setNetworkConnectivityEndpoint("16842753;16908289"); //DOM1|GW1|0|1;DOM1|GW2|0|1  ("1.1.0.1;1.2.0.1") 
            popel.setNfviPopId("11");//Dom1|Pop1
            popel.setVimId("11");
            poplist.add(popel);
        } else if (dominfo.getId() == dom2)  {
            NfviPop popel = new NfviPop();
            popel.setGeographicalLocationInfo("Italy");
            popel.setNetworkConnectivityEndpoint("33619969;33685505");//DOM2|GW1|0|1;DOM2|GW2|0|1 ("2.1.0.1;2.2.0.1")
            popel.setNfviPopId("21");//Dom2|Pop1
            popel.setVimId("21");
            poplist.add(popel);
        } else if (dominfo.getId() == dom3) {
            NfviPop popel = new NfviPop();
            popel.setGeographicalLocationInfo("France");
            popel.setNetworkConnectivityEndpoint("50397185;50462721");//DOM3|GW1|0|1;DOM3|GW2|0|1 ("3.1.0.1;3.2.0.1")
            popel.setNfviPopId("31");//Dom3|Pop1
            popel.setVimId("31");
            poplist.add(popel);
        } else if (dominfo.getId() == dom4) {
            NfviPop popel = new NfviPop();
            popel.setGeographicalLocationInfo("Germany");
            popel.setNetworkConnectivityEndpoint("67174401;67239937");//DOM4|GW1|0|1;DOM4|GW2|0|1 ("4.1.0.1;4.2.0.1")
            popel.setNfviPopId("41");//Dom4|Pop1
            popel.setVimId("41");
            poplist.add(popel);
        }
        else{
            
              }
        //add nfvipop to event
        ev.setPopList(poplist);
        
        if (dominfo.getId() == dom1)  { 
            ResourceZone zonel = new ResourceZone();
            zonel.setNfviPopId("11");//Dom1|Pop1
            zonel.setZoneId("111");//Dom1|Pop1|Zone1
            zonel.setZoneName("Zone1");
            zonel.setZoneProperty("Power");
            zonel.setZoneState("enable");
            zonelist.add(zonel);
        } else if (dominfo.getId() == dom2)  {
            ResourceZone zonel = new ResourceZone();
            zonel.setNfviPopId("21");//Dom2|Pop1
            zonel.setZoneId("211");//Dom2|Pop1|Zone1
            zonel.setZoneName("Zone2");
            zonel.setZoneProperty("Power2");
            zonel.setZoneState("enable");
            zonelist.add(zonel);
        } else if (dominfo.getId() == dom3) {
            ResourceZone zonel = new ResourceZone();
            zonel.setNfviPopId("31");
            zonel.setZoneId("311");
            zonel.setZoneName("Zone3");
            zonel.setZoneProperty("Power3");
            zonel.setZoneState("enable");
            zonelist.add(zonel);
        } else if (dominfo.getId() == dom4) {
            ResourceZone zonel = new ResourceZone();
            zonel.setNfviPopId("41");
            zonel.setZoneId("411");
            zonel.setZoneName("Zone4");
            zonel.setZoneProperty("Power4");
            zonel.setZoneState("enable");
            zonelist.add(zonel);
        }
        
        //add zoneid to event
        ev.setZoneList(zonelist);
        //Retrieve resource information for each zonid
        for (i = 0; i < zonelist.size(); i++ ) {
            tmpzone = zonelist.get(i).getZoneId();
            tmppop = zonelist.get(i).getNfviPopId();
            //Retrieve compute info
            

            if (dominfo.getId() == dom1) {
                VirtualComputeResourceInformation rescompel1 = new VirtualComputeResourceInformation();
                VirtualComputeResourceInformationVirtualCPU virtualCPU = new VirtualComputeResourceInformationVirtualCPU();
                VirtualComputeResourceInformationVirtualMemory virtualMem = new VirtualComputeResourceInformationVirtualMemory();
                
                virtualCPU.setCpuArchitecture("x86_64");
                virtualCPU.setCpuClock(BigDecimal.valueOf(2.2));
                virtualCPU.setNumVirtualCpu(BigDecimal.valueOf(2000));
                
                virtualMem.setNumaSupported(false);
                virtualMem.setVirtualMemSize(BigDecimal.valueOf(1000000));
                
                rescompel1.setComputeResourceTypeId("1111");//Dom1|Pop1|Zone1|Resource1
                rescompel1.setVirtualCPU(virtualCPU);
                resinfolist.add(rescompel1);
                
                VirtualComputeResourceInformation rescompel2 = new VirtualComputeResourceInformation();
                rescompel2.setComputeResourceTypeId("1112");//Dom1|Pop1|Zone1|Resource2
                rescompel2.setVirtualMemory(virtualMem);
                resinfolist.add(rescompel2);
                
            } else if (dominfo.getId() == dom2) {
                VirtualComputeResourceInformation rescompel = new VirtualComputeResourceInformation();
                VirtualComputeResourceInformationVirtualCPU virtualCPU = new VirtualComputeResourceInformationVirtualCPU();
                VirtualComputeResourceInformationVirtualMemory virtualMem = new VirtualComputeResourceInformationVirtualMemory();
                virtualCPU.setCpuArchitecture("x86_64");
                virtualCPU.setCpuClock(BigDecimal.valueOf(2.2));
                virtualCPU.setNumVirtualCpu(BigDecimal.valueOf(2000));
                virtualMem.setNumaSupported(false);
                virtualMem.setVirtualMemSize(BigDecimal.valueOf(1000000));
                rescompel.setComputeResourceTypeId("2111");//Dom2|Pop1|Zone1|Resource1
                rescompel.setVirtualCPU(virtualCPU);
                resinfolist.add(rescompel);
                rescompel = new VirtualComputeResourceInformation();
                rescompel.setComputeResourceTypeId("2112");//Dom2|Pop1|Zone1|Resource2
                rescompel.setVirtualMemory(virtualMem);
                resinfolist.add(rescompel);
            } else if (dominfo.getId() == dom3) {
                VirtualComputeResourceInformation rescompel = new VirtualComputeResourceInformation();
                VirtualComputeResourceInformationVirtualCPU virtualCPU = new VirtualComputeResourceInformationVirtualCPU();
                VirtualComputeResourceInformationVirtualMemory virtualMem = new VirtualComputeResourceInformationVirtualMemory();
                virtualCPU.setCpuArchitecture("x86_64");
                virtualCPU.setCpuClock(BigDecimal.valueOf(2.2));
                virtualCPU.setNumVirtualCpu(BigDecimal.valueOf(2000));
                virtualMem.setNumaSupported(false);
                virtualMem.setVirtualMemSize(BigDecimal.valueOf(1000000));
                rescompel.setComputeResourceTypeId("3111");//Dom3|Pop1|Zone1|Resource2
                rescompel.setVirtualCPU(virtualCPU);
                resinfolist.add(rescompel);
                rescompel = new VirtualComputeResourceInformation();
                rescompel.setComputeResourceTypeId("3112");//Dom3|Pop1|Zone1|Resource2
                rescompel.setVirtualMemory(virtualMem);
                resinfolist.add(rescompel);
            }else if (dominfo.getId() == dom4) {
                VirtualComputeResourceInformation rescompel = new VirtualComputeResourceInformation();
                VirtualComputeResourceInformationVirtualCPU virtualCPU = new VirtualComputeResourceInformationVirtualCPU();
                VirtualComputeResourceInformationVirtualMemory virtualMem = new VirtualComputeResourceInformationVirtualMemory();
                virtualCPU.setCpuArchitecture("x86_64");
                virtualCPU.setCpuClock(BigDecimal.valueOf(2.2));
                virtualCPU.setNumVirtualCpu(BigDecimal.valueOf(2000));
                virtualMem.setNumaSupported(false);
                virtualMem.setVirtualMemSize(BigDecimal.valueOf(1000000));
                rescompel.setComputeResourceTypeId("4111");//Dom4|Pop1|Zone1|Resource2
                rescompel.setVirtualCPU(virtualCPU);
                resinfolist.add(rescompel);
                rescompel = new VirtualComputeResourceInformation();
                rescompel.setComputeResourceTypeId("4112");//Dom4|Pop1|Zone1|Resource2
                rescompel.setVirtualMemory(virtualMem);
                resinfolist.add(rescompel);
            }
            
            //query compute capacity
            for (j = 0; j < resinfolist.size(); j++) {
                if (resinfolist.get(j).getVirtualCPU() != null) {
                    if (dominfo.getId() == dom1) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("2000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("2000");
                    } else if (dominfo.getId() == dom2) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("4000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("4000");
                    } else if (dominfo.getId() == dom3) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("2000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("2000");
                    }else if (dominfo.getId() == dom4) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("4000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("4000");
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
                    if (dominfo.getId() == dom1) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("1000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("1000000");
                    } else if (dominfo.getId() == dom2) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("2000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("2000000");
                    } else if (dominfo.getId() == dom3) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("1000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("1000000");
                    } else if (dominfo.getId() == dom4) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("2000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("2000000");
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
            
            //Retrieve network info
            if (dominfo.getId() == dom1) {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List<AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delay");
                qos.setQosValue("0");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("1111");//Dom1|Pop1|Zone1|Resource1
                resnetel.setNetworkQoS(qoslist);
                netinfolist.add(resnetel);
            } else if (dominfo.getId() == dom2) {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List<AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delay");
                qos.setQosValue("0");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("2111");//Dom2|Pop1|Zone1|Resource1
                resnetel.setNetworkQoS(qoslist);
                netinfolist.add(resnetel);
            } else if (dominfo.getId() == dom3) {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List<AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delay");
                qos.setQosValue("0");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("3111");
                resnetel.setNetworkQoS(qoslist);
                netinfolist.add(resnetel);
            }
            else if (dominfo.getId() == dom4) {
                VirtualNetworkResourceInformation resnetel = new VirtualNetworkResourceInformation();
                AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
                List<AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
                qos.setQosName("delay");
                qos.setQosValue("0");
                qoslist.add(qos);
                resnetel.setBandwidth(BigDecimal.valueOf(10000000));
                resnetel.setNetworkResourceTypeId("4111");
                resnetel.setNetworkQoS(qoslist);
                netinfolist.add(resnetel);
            }
            //query compute capacity
            for (j = 0; j < netinfolist.size(); j++) {
                
                //ask capacity for network
                 if (dominfo.getId() == dom1) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("10000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("10000000");
                    } else if (dominfo.getId() == dom2) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("20000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("2000000");
                    } else if (dominfo.getId() == dom3) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("10000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("10000000");
                    }else if (dominfo.getId() == dom4) {
                        caprep = new CapacityInformation();
                        caprep.setAllocatedCapacity("0");
                        caprep.setAvailableCapacity("20000000");
                        caprep.setReservedCapacity("0");
                        caprep.setTotalCapacity("20000000");
                    }
                //Add element to compute list in the event
                NetworkResElem netel = new NetworkResElem();
                netel.setNetworkElem(netinfolist.get(i));
                netel.setCapacityElem(caprep);
                ev.setNetworkResElem(netel);
                //create element in map
                MapResources mapel = new MapResources(zonelist.get(i).getNfviPopId(),
                        zonelist.get(i).getZoneId(),
                        netinfolist.get(j).getNetworkResourceTypeId());
                ev.setNetworkMapElem(mapel); 
            }    
        }
        //send event
        SingletonEventBus.getBus().post(ev);
    }    
}
