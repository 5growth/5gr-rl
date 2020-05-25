/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.dummyplugin.sbi;

import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.ComputeCapacityReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeCapacityRequest;
import com.ericsson.dummyplugin.events.abstraction.ComputeResourceInformationReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeResourceInformationRequest;
import com.ericsson.dummyplugin.events.abstraction.ComputeZoneReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeZoneRequest;
import com.ericsson.dummyplugin.events.abstraction.NfviPopAbstractionReply;
import com.ericsson.dummyplugin.events.abstraction.NfviPopAbstractionRequest;
import com.ericsson.dummyplugin.events.abstraction.Parsedomainlist;
import com.ericsson.dummyplugin.events.abstraction.StartServer;
import com.ericsson.dummyplugin.events.allocate.ComputeAllocateReply;
import com.ericsson.dummyplugin.events.allocate.ComputeAllocateRequest;
import com.ericsson.dummyplugin.events.allocate.ComputeOperateReply;
import com.ericsson.dummyplugin.events.allocate.ComputeOperateRequest;
import com.ericsson.dummyplugin.events.allocate.FreeVlanReply;
import com.ericsson.dummyplugin.events.allocate.FreeVlanRequest;
import com.ericsson.dummyplugin.events.allocate.VirtualNetworkAllocateReply;
import com.ericsson.dummyplugin.events.allocate.VirtualNetworkAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.ComputeTerminateReply;
import com.ericsson.dummyplugin.events.terminate.ComputeTerminateRequest;
import com.ericsson.dummyplugin.events.terminate.VirtualNetworkTerminateReply;
import com.ericsson.dummyplugin.events.terminate.VirtualNetworkTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResult;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResultNetworkData;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResultNetworkPortData;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResultSubnetData;
import com.ericsson.dummyplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.dummyplugin.nbi.swagger.model.MetaDataInner;
import com.ericsson.dummyplugin.nbi.swagger.model.NfviPop;
import com.ericsson.dummyplugin.nbi.swagger.model.ResourceZone;
import com.ericsson.dummyplugin.nbi.swagger.model.VIMAllocateComputeRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.VIMVirtualCompute;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformation;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeVirtualMemory;
import com.ericsson.dummyplugin.sbi.objects.ComputeResource;
import com.ericsson.dummyplugin.sbi.objects.NFVIPop;
import com.ericsson.dummyplugin.sbi.objects.NetworkService;
import com.ericsson.dummyplugin.sbi.objects.Zone;
import com.google.common.eventbus.Subscribe;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author efabuba
 */
public class XenIF {
    private List<NFVIPop> poplist;
    private List<Zone> zonelist;
    private HashMap<String, ComputeResource> compreslist;
    private HashMap<String, String> nettypemap;
    private HashMap<String, String> netidmap;
    private HashMap<String, String> netportmap;
    private HashMap<String, String> netvlanmap;
    private HashMap<String, VIMVirtualCompute> compservlist;
    private List<Boolean> vlanlist;
    private int netservcounter;
    private int compservcounter;
    
    public XenIF() {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        compreslist = new HashMap();
        nettypemap = new HashMap();
        netidmap = new HashMap();
        netportmap = new HashMap();
        netvlanmap = new HashMap();
        compservlist = new HashMap();
        vlanlist = new ArrayList();
        netservcounter = 1;
        compservcounter = 1;
        //Set vlanid from 10 to 100
        for (int i = 0; i < 100; i++) {
            vlanlist.add(Boolean.TRUE);
        }
    }
    
    
    //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        System.out.println("CrossHaulIF ---> Parse domlist from xml file");
        //TODO: Parse list of domain from xml file
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        
        poplist = xmldom.getPoplist();
        zonelist = xmldom.getZonelist();
        compreslist = xmldom.getReslist();
        StartServer startserv = new StartServer();
        SingletonEventBus.getBus().post(startserv);
    }
    

        @Subscribe
    public void handle_FreeVlanRequest(FreeVlanRequest vlanreq) {
        System.out.println("XenIF ---> free vlan request" );
        List<BigDecimal> freevlans = new ArrayList<BigDecimal>();
        
        for (int i = 0; i < vlanlist.size(); i++) {
            if (vlanlist.get(i) == true) {
                BigDecimal vlanel = new BigDecimal(i);
                freevlans.add(vlanel);
            }
        }
        FreeVlanReply vlanrep = new FreeVlanReply(vlanreq.getReqid(),
                    freevlans);
        System.out.println("XenIF ---> post ComputeAllocateReply");
        SingletonEventBus.getBus().post(vlanrep);
    }
    
    @Subscribe
    public void handle_ComputeAllocateRequest(ComputeAllocateRequest servallreq) {
        System.out.println("XenIF ---> allocate compute service request" );
        VIMAllocateComputeRequest compreq = servallreq.getRequest();
        String servid = new String("");
        List<MetaDataInner> inputdata = servallreq.getRequest().getMetadata();
        for (int i = 0; i < inputdata.size(); i++) {
            if(inputdata.get(i).getKey().compareTo("ServiceId") == 0) {
                servid = inputdata.get(i).getValue();
            }
        }
        
         
        VIMVirtualCompute compel = new VIMVirtualCompute();
        compel.setComputeId(Integer.toString(compservcounter));
        compel.setComputeName("XenCompService" + Integer.toString(netservcounter));
        compel.setFlavourId(compreq.getComputeFlavourId());
        compel.setHostId("XENHost");
        compel.setOperationalState("enable");
        compel.setVcImageId(compreq.getVcImageId());
        compel.setVirtualDisks("1");
        ComputeResource cpuel = new ComputeResource();
        ComputeResource memel = new ComputeResource();
        //retrieve virtual CPU from resources
        for (ComputeResource value : compreslist.values()) {
            if (value.getType().compareTo("cpu") == 0) {
                cpuel = value;
            } else {
                memel = value;
            }
                
        }
        VirtualComputeVirtualCpu virtualCpu = new VirtualComputeVirtualCpu();
        virtualCpu.setCpuArchitecture(cpuel.getArchitecture());
        virtualCpu.setCpuClock(new BigDecimal(cpuel.getCpuclock()));
        virtualCpu.setNumVirtualCpu(Integer.parseInt(cpuel.getNumvcpu())); //TODO: Check in POC
        virtualCpu.setVirtualCpuOversubscriptionPolicy(cpuel.getSubscr_policy());
        compel.setVirtualCpu(virtualCpu);
        VirtualComputeVirtualMemory virtualMemory = new VirtualComputeVirtualMemory();
        
        virtualMemory.setNumaEnabled(memel.isNumaSupported());
        virtualMemory.setVirtualMemOversubscriptionPolicy(memel.getSubscr_policy());
        virtualMemory.setVirtualMemSize(new BigDecimal(memel.getMemsize()));
        compel.setVirtualMemory(virtualMemory);
        //insert in service table
        compservlist.put(Integer.toString(compservcounter), compel);  
        
        compservcounter++;
        ComputeAllocateReply servallrep = new ComputeAllocateReply(servallreq.getReqId(),
                    compel);
        System.out.println("XenIF ---> post ComputeAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
    }
    
    @Subscribe
    public void handle_ComputeTerminateRequest(ComputeTerminateRequest servtermreq) {
        System.out.println("XenIF ---> terminate compute service request" );
        List<String> resplist = new ArrayList();
        for (int i = 0; i < servtermreq.getRequest().size(); i++) {
            String key = servtermreq.getRequest().get(i);
            VIMVirtualCompute servel = compservlist.get(key);
            if (servel != null) {
                compservlist.remove(key);
                resplist.add(key);
            }
        }
        
        ComputeTerminateReply servtermrep = new ComputeTerminateReply(servtermreq.getReqId(), resplist);
        System.out.println("XenIF ---> post ComputeTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }
    
     @Subscribe
    public void handle_ComputeOperateRequest(ComputeOperateRequest compreq) {
        System.out.println("XenIF ---> compute operate request" );
 
            
        VIMVirtualCompute servel = compservlist.get(compreq.getRequest().getId());
         if (servel != null) {
            servel.setOperationalState(compreq.getRequest().getOperationStatus());
            ComputeOperateReply comprep = new ComputeOperateReply(compreq.getReqid(), servel);
            System.out.println("XenIF ---> post ComputeOperateReply");
            SingletonEventBus.getBus().post(comprep);
         } else {
            ComputeOperateReply comprep = new ComputeOperateReply(compreq.getReqid(), new VIMVirtualCompute());
            System.out.println("XenIF ---> post ComputeTerminateReply");
            SingletonEventBus.getBus().post(comprep);
         }
   }
   
    public AllocateNetworkResult AllocateNetworkData(AllocateNetworkRequest netdatareq) {
        AllocateNetworkResult resp = new AllocateNetworkResult();
        boolean vlanflag = false;
        String vlanid = ""; 
        //check if the network is for a vlan allocation or not
        for (int i = 0; (i < netdatareq.getMetadata().size()) && (vlanflag == false); i++) {
            if (netdatareq.getMetadata().get(i).getKey().compareTo("interpop_vlan") == 0) {
              vlanflag = true;
              vlanid = netdatareq.getMetadata().get(i).getValue();
            }                
        }
        //create identifiers for subnet and network 
        String netid = "datanet" + netservcounter;
        netservcounter++;
        String subnetid = "subnet" + netservcounter;
        netservcounter++;
        
        if (vlanflag == true) {
            System.out.println("XenIF ---> network for vlan allocation");
            //create vlan type entry
            nettypemap.put(subnetid, "VLAN");
            //create netid  entry
            netidmap.put(subnetid, netid);
            //create vlan entry
            netvlanmap.put(subnetid, vlanid);
            //set vlanid to false in freevlan list
            //vlanlist.put(vlanid, false);
            //format response
            AllocateNetworkResultNetworkData respnetdata = new AllocateNetworkResultNetworkData();
            respnetdata.setNetworkResourceName(netdatareq.getNetworkResourceName());
            respnetdata.setNetworkResourceId(netid);
            respnetdata.segmentType("");
            respnetdata.setBandwidth(BigDecimal.ONE);
            respnetdata.setIsShared(true);
            respnetdata.setNetworkType("network");
            respnetdata.setOperationalState("");
            respnetdata.setSegmentType("");
            respnetdata.setSharingCriteria("");
            respnetdata.setZoneId("");
            resp.setNetworkData(respnetdata);
            
        } else {
            System.out.println("XenIF ---> network for vxlan or private allocation");
            System.out.println("XenIF ---> network for vlan allocation");
            //create vxlan type entry. Change if it is request an IP in the next subnet request
            nettypemap.put(subnetid, "PRIVATE");
            //create netid  entry
            netidmap.put(subnetid, netid);
            
            //format response
            AllocateNetworkResultNetworkData respnetdata = new AllocateNetworkResultNetworkData();
            respnetdata.setNetworkResourceName(netdatareq.getNetworkResourceName());
            respnetdata.setNetworkResourceId(netid);
            resp.setNetworkData(respnetdata);
        }
        return resp;
    } 
    
    public AllocateNetworkResult AllocateSubnetData(AllocateNetworkRequest netdatareq) {
        AllocateNetworkResult resp = new AllocateNetworkResult();
        //search subnetid from netid
        String subnetid = "";
        String nettype = "";
        for (Map.Entry<String, String> entry : netidmap.entrySet()) {
            if (entry.getValue().compareTo(netdatareq.getTypeSubnetData().getNetworkId())==0) {
                subnetid = entry.getKey();
                break;
            }
        }
        if (subnetid.compareTo("") ==0) {
            System.out.println("XenIF ---> error subnet id not found for netid " + netdatareq.getTypeSubnetData().getNetworkId());
            return resp;
        }
        for (Map.Entry<String, String> entry : nettypemap.entrySet()) {
            if (entry.getKey().compareTo(subnetid)==0) {
                nettype =entry.getValue();
                break;
            }
        }
        if (nettype.compareTo("VLAN") == 0) {
            //format subnet request
            AllocateNetworkResultSubnetData subnetdata = new AllocateNetworkResultSubnetData();
            //copy subnet data
            subnetdata.setAddressPool(netdatareq.getTypeSubnetData().getAddressPool());
            subnetdata.setCidr(netdatareq.getTypeSubnetData().getCidr());
            subnetdata.setGatewayIp(netdatareq.getTypeSubnetData().getGatewayIp());
            subnetdata.setIpVersion(netdatareq.getTypeSubnetData().getIpVersion());
            subnetdata.setIsDhcpEnabled(netdatareq.getTypeSubnetData().isIsDhcpEnabled());
            subnetdata.setResourceId(netdatareq.getTypeSubnetData().getResourceId());
            subnetdata.setOperationalState("enabled");
            //set subnetid
            subnetdata.setNetworkId(subnetid);
            resp.setSubnetData(subnetdata);
            
        } else {
            boolean vxlanflag = false;

            //check if it is present the floating_required field in metadata
            for (int i = 0; (i < netdatareq.getMetadata().size()) && (vxlanflag == false); i++) {
                if (netdatareq.getMetadata().get(i).getKey().compareTo("floating_required") == 0) {
                    vxlanflag = true;
                    //change nettypemap to vlan
                    nettypemap.put(subnetid, "VXLAN");
                }
            }

            //format subnet request
            AllocateNetworkResultSubnetData subnetdata = new AllocateNetworkResultSubnetData();
            //copy subnet data
            subnetdata.setAddressPool(netdatareq.getTypeSubnetData().getAddressPool());
            subnetdata.setCidr(netdatareq.getTypeSubnetData().getCidr());
            subnetdata.setGatewayIp(netdatareq.getTypeSubnetData().getGatewayIp());
            subnetdata.setIpVersion(netdatareq.getTypeSubnetData().getIpVersion());
            subnetdata.setIsDhcpEnabled(netdatareq.getTypeSubnetData().isIsDhcpEnabled());
            subnetdata.setResourceId(netdatareq.getTypeSubnetData().getResourceId());
            subnetdata.setOperationalState("enabled");
            //set subnetid
            subnetdata.setNetworkId(subnetid);
            resp.setSubnetData(subnetdata);
        }
        return resp;
    } 
    
    public AllocateNetworkResult AllocatePortData(AllocateNetworkRequest netdatareq) {
        AllocateNetworkResult resp = new AllocateNetworkResult();
        //only happen for VXLAN
        //set port entry
        String portid = "port" + netservcounter;
        netservcounter++;
        //search subnet_id in metadata
        boolean subnetflag = false;
        String subnetid = "";
        
        for (int i = 0; (i < netdatareq.getMetadata().size()) && (subnetflag == false); i++) {
            if (netdatareq.getMetadata().get(i).getKey().compareTo("subnet_id") ==0) {
                subnetflag = true;
                subnetid = netdatareq.getMetadata().get(i).getValue();
            }
        }
        if (subnetflag == false) {
            System.out.println("XenIF ---> error subnet id not found in metadata");
            return resp;
        }
        netportmap.put(subnetid , portid);
        
        //Create metadata entry for floating ip
        String floatingip = "10.10.10.10";
        
        List<MetaDataInner> metadata = new ArrayList();
        MetaDataInner ipentry = new MetaDataInner();
        ipentry.setKey("floating_ip");
        ipentry.setValue(floatingip);
        metadata.add(ipentry);
        
        //set port data
        AllocateNetworkResultNetworkPortData portdata = new AllocateNetworkResultNetworkPortData();
        portdata.setNetworkId(portid);
        portdata.setOperationalState("enabled");
        //set floating ip in metadata
        portdata.setMetadata(metadata);
        resp.setNetworkPortData(portdata);
        return resp;
    } 
    
    @Subscribe
    public void handle_VirtualNetworkAllocateRequest(VirtualNetworkAllocateRequest servallreq) {
        System.out.println("XenIF ---> allocate network service request" );
        AllocateNetworkResult resp = new AllocateNetworkResult();
        switch (servallreq.getE2ewimelem().getNetworkResourceType()) {
            case "network" :
                resp = AllocateNetworkData(servallreq.getE2ewimelem());
                break;
            case "subnet" :
                resp = AllocateSubnetData(servallreq.getE2ewimelem());
                break;
            case "port" :
                resp = AllocatePortData(servallreq.getE2ewimelem());
                break;
            //understand if request is for a VLAN or not
        }    
        
        VirtualNetworkAllocateReply servallrep = new VirtualNetworkAllocateReply(servallreq.getReqid(),
                    resp);
        System.out.println("XenIF ---> post VirtualNetworkAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
        
    }
    
    @Subscribe
    public void handle_VirtualNetworkTerminateRequest(VirtualNetworkTerminateRequest servtermreq) {
        System.out.println("XenIF ---> terminate network service request" );
        List<String> resplist = new ArrayList<String>();
        for ( int i = 0; i < servtermreq.getE2EWIMElem().size(); i++) {
            if (servtermreq.getE2EWIMElem().get(i).contains("datanet") == true) {
                //remove entry nettypemap, netidmap and vlanmap if vlan connetion is set
                String subnetid = "";
                //find subnetid
                for (Map.Entry<String, String> entry : netidmap.entrySet()) {
                    if (entry.getValue().compareTo(servtermreq.getE2EWIMElem().get(i)) == 0) {
                        subnetid = entry.getValue();
                        break;
                    }
                }
                if (subnetid.compareTo("") == 0) {
                    System.out.println("XenIF ---> error subnet not found for delete datanet");
                    return;
                } 
                String nettype = nettypemap.get(subnetid);
                if (nettype == null) {
                    System.out.println("XenIF ---> error nettype not found for delete datanet");
                    return;
                }
                if (nettype.compareTo("VLAN") == 0) {
                    //remove
                    System.out.println("XenIF ---> remove entry for vlan");
                    nettypemap.remove(subnetid);
                    netidmap.remove(subnetid);
                    netvlanmap.remove(subnetid);
                } else {
                   nettypemap.remove(subnetid);
                   netidmap.remove(subnetid); 
                }
                
                
            } else if (servtermreq.getE2EWIMElem().get(i).contains("subnet") == true) {
                //nothing to do
            } else if (servtermreq.getE2EWIMElem().get(i).contains("port") == true) {
                String subnetid = "";
                //find subnetid
                for (Map.Entry<String, String> entry : netportmap.entrySet()) {
                    if (entry.getValue().compareTo(servtermreq.getE2EWIMElem().get(i)) == 0) {
                        subnetid = entry.getValue();
                        break;
                    }
                }
                if (subnetid.compareTo("") == 0) {
                    System.out.println("XenIF ---> error subnet not found for delete port");
                    return;
                }
                netportmap.remove(subnetid);
            } else {
                System.out.println("XenIF ---> no datanet, subnet, port to remove. Nothing to do" );
            }

            resplist.add(servtermreq.getE2EWIMElem().get(i));
        }
        
        VirtualNetworkTerminateReply servtermrep = new VirtualNetworkTerminateReply(servtermreq.getReqId(),resplist);
        System.out.println("XenIF ---> post VirtualNetworkTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }    
    
    
    @Subscribe
    public void handle_ComputeCapacityRequest(ComputeCapacityRequest compcapreq) {
        System.out.println("XenIF ---> retrieve compute resources capacity for resid = " + compcapreq.getCapacityRequest().getComputeResourceTypeId());
        
        ComputeResource el = compreslist.get(compcapreq.getCapacityRequest().getComputeResourceTypeId());
        
        if (el == null) {
            System.out.println("XenIF ---> no compute resource found for resid = " + compcapreq.getCapacityRequest().getComputeResourceTypeId());
            ComputeCapacityReply compcaprep = new ComputeCapacityReply(compcapreq.getReqId(),new CapacityInformation());
            System.out.println("XenIF ---> post NetworkCapacityReply");
            SingletonEventBus.getBus().post(compcaprep);
            return;
        }
        
        CapacityInformation compcapel = new CapacityInformation();
        
        compcapel.setAllocatedCapacity(el.getAllocated());
        compcapel.setAvailableCapacity(el.getAvailable());
        compcapel.setReservedCapacity(el.getReserved());
        compcapel.setTotalCapacity(el.getTotal());
        
        
        ComputeCapacityReply compcaprep = new ComputeCapacityReply(compcapreq.getReqId(),compcapel);
        System.out.println("XenIF ---> post NetworkCapacityReply");
        SingletonEventBus.getBus().post(compcaprep);
    }
    
    @Subscribe
    public void handle_ComputeResourceInformationRequest(ComputeResourceInformationRequest compresreq) {
        System.out.println("XenIF ---> retrieve network resources");
        List<VirtualComputeResourceInformation> compresreqlist = new ArrayList(); 
        
        for (ComputeResource value : compreslist.values()) {
            VirtualComputeResourceInformation el = new VirtualComputeResourceInformation();
            el.setAccelerationCapability(value.getAcc_capability());
            el.setComputeResourceTypeId(value.getId());
            if (value.getType().compareTo("cpu") == 0 ) {
                VirtualComputeResourceInformationVirtualCPU virtualCPU = new VirtualComputeResourceInformationVirtualCPU();
                virtualCPU.setCpuArchitecture(value.getArchitecture());
                virtualCPU.setCpuClock(new BigDecimal(value.getCpuclock()));
                virtualCPU.setNumVirtualCpu(new BigDecimal(value.getNumvcpu()));
                virtualCPU.setVirtualCpuOversubscriptionPolicy(value.getSubscr_policy());
                virtualCPU.setVirtualCpuPinningSupported(value.isPinningSupported());
                el.setVirtualCPU(virtualCPU);
            } else if (value.getType().compareTo("mem") == 0) {
                VirtualComputeResourceInformationVirtualMemory virtualMemory = new VirtualComputeResourceInformationVirtualMemory();
                virtualMemory.setNumaSupported(value.isNumaSupported());
                virtualMemory.setVirtualMemOversubscriptionPolicy(value.getSubscr_policy());
                virtualMemory.setVirtualMemSize(new BigDecimal(value.getMemsize()));
                el.setVirtualMemory(virtualMemory);
            }
            compresreqlist.add(el);
        }
        ComputeResourceInformationReply netzonerep = new ComputeResourceInformationReply(compresreq.getReqId(),compresreqlist);
        System.out.println("XenIF ---> post NetworkResourceInformationReply");
        SingletonEventBus.getBus().post(netzonerep);
    }
    
    @Subscribe
    public void handle_ComputeZoneRequest(ComputeZoneRequest netzonereq) {
        System.out.println("XenIF ---> retrieve zone list");
        List<ResourceZone> zonereplist = new ArrayList(); 
        for (int i = 0; i < zonelist.size(); i++) {
            ResourceZone el = new ResourceZone();
            el.setNfviPopId(zonelist.get(i).getPop());
            el.setZoneId(zonelist.get(i).getId());
            el.setZoneName(zonelist.get(i).getName());
            el.setZoneProperty(zonelist.get(i).getProperty());
            el.setZoneState(zonelist.get(i).getStatus());
            zonereplist.add(el);
        }
        ComputeZoneReply netzonerep = new ComputeZoneReply(netzonereq.getReqid(),zonereplist);
        System.out.println("XenIF ---> post NetworkZoneReply");
        SingletonEventBus.getBus().post(netzonerep);
    }
    
    @Subscribe
    public void handle_NfviPopAbstractionRequest(NfviPopAbstractionRequest nfvipopreq) {
        System.out.println("XenIF ---> retrieve nfvipop list");
        List<NfviPop> popreplist = new ArrayList(); 
        for (int i = 0; i < poplist.size(); i++) {
            NfviPop el = new NfviPop();
            el.setGeographicalLocationInfo(poplist.get(i).getLocation());
            el.setNetworkConnectivityEndpoint(poplist.get(i).getEndpoints());
            el.setNfviPopId(poplist.get(i).getPop());
            el.setVimId(poplist.get(i).getVim());
            popreplist.add(el);
        }
        NfviPopAbstractionReply nfvipoprep = new NfviPopAbstractionReply(nfvipopreq.getReqId(),popreplist);
        System.out.println("XenIF ---> post NfviPopAbstractionReply");
        SingletonEventBus.getBus().post(nfvipoprep);
        
    }
}
