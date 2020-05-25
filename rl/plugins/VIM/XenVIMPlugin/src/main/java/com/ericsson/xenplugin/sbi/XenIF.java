/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.xenplugin.sbi;

import com.ericsson.xenplugin.SingletonEventBus;
import com.ericsson.xenplugin.events.abstraction.ComputeCapacityReply;
import com.ericsson.xenplugin.events.abstraction.ComputeCapacityRequest;
import com.ericsson.xenplugin.events.abstraction.ComputeResourceInformationReply;
import com.ericsson.xenplugin.events.abstraction.ComputeResourceInformationRequest;
import com.ericsson.xenplugin.events.abstraction.ComputeZoneReply;
import com.ericsson.xenplugin.events.abstraction.ComputeZoneRequest;
import com.ericsson.xenplugin.events.abstraction.NfviPopAbstractionReply;
import com.ericsson.xenplugin.events.abstraction.NfviPopAbstractionRequest;
import com.ericsson.xenplugin.events.abstraction.Parsedomainlist;
import com.ericsson.xenplugin.events.abstraction.StartServer;
import com.ericsson.xenplugin.events.allocate.ComputeAllocateReply;
import com.ericsson.xenplugin.events.allocate.ComputeAllocateRequest;
import com.ericsson.xenplugin.events.allocate.VirtualNetworkAllocateReply;
import com.ericsson.xenplugin.events.allocate.VirtualNetworkAllocateRequest;
import com.ericsson.xenplugin.events.terminate.ComputeTerminateReply;
import com.ericsson.xenplugin.events.terminate.ComputeTerminateRequest;
import com.ericsson.xenplugin.events.terminate.VirtualNetworkTerminateReply;
import com.ericsson.xenplugin.events.terminate.VirtualNetworkTerminateRequest;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkRequest;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkResult;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkResultNetworkData;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkResultNetworkPortData;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkResultSubnetData;
import com.ericsson.xenplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.xenplugin.nbi.swagger.model.MetaDataInner;
import com.ericsson.xenplugin.nbi.swagger.model.NfviPop;
import com.ericsson.xenplugin.nbi.swagger.model.ResourceZone;
import com.ericsson.xenplugin.nbi.swagger.model.VIMAllocateComputeRequest;
import com.ericsson.xenplugin.nbi.swagger.model.VIMVirtualCompute;
import com.ericsson.xenplugin.nbi.swagger.model.VIMVirtualComputeVirtualNetworkInterface;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeResourceInformation;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualComputeVirtualMemory;
import com.ericsson.xenplugin.sbi.objects.ComputeResource;
import com.ericsson.xenplugin.sbi.objects.ComputeService;
import com.ericsson.xenplugin.sbi.objects.NFVIPop;
import com.ericsson.xenplugin.sbi.objects.NetworkService;
import com.ericsson.xenplugin.sbi.objects.XenService;
import com.ericsson.xenplugin.sbi.objects.Zone;
import com.google.common.eventbus.Subscribe;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xensource.xenapi.*;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author efabuba
 */
public class XenIF {
    private List<NFVIPop> poplist;
    private List<Zone> zonelist;
    private HashMap<String, ComputeResource> compreslist;
    private HashMap<String, XenService> xenreslist;
    private HashMap<String, NetworkService> netservlist;
    private HashMap<String, ComputeService> compservlist;
    private int netservcounter;
    private int compservcounter;
    //XEN variables


    
    
    public XenIF() {
        poplist = new ArrayList();
        zonelist = new ArrayList();
        compreslist = new HashMap();
        xenreslist = new HashMap();
        netservlist = new HashMap();
	compservlist = new HashMap();
        netservcounter = 1;
        compservcounter = 1;

      
//        /* Connect to XEN DC */
//        try {
//            connection = new Connection(new URL("http://" + MASTER));
//        } catch(MalformedURLException e) {
//            System.out.println("XenIF ---> error malformed URL");
//            System.out.println("XenIF ---> error message" + e.getMessage());
//            System.exit(-1);
//        }
//        try {
//            Session.loginWithPassword(connection, USERNAME, PASSWORD, APIVersion.latest().toString());
//
//        } catch(BadServerResponse e) {
//            System.out.println("XenIF ---> error Bad Server Response");
//            System.out.println("XenIF ---> error message" + e.getMessage());
//            System.exit(-1);
//        }
//        System.out.println("XenIF ---> Logging in to " + MASTER + " as a " + USERNAME + " with the password " + PASSWORD);
//        // Some data are created when downloading the VM images.
//        //As it is assumed that the download is already perform such data are retrieved 
//        //Data retrieved are VM, host, Storage Repository
//        mapVmPool = VM.getAllRecords(connection);
//        mapHostPool = Host.getAllRecords(connection);
//        mapSrPool = SR.getAllRecords(connection);
        System.out.println("XenIF ---> Retrieve all VM, Host, SR data");
    }
    
    
    //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) throws Exception {
        Connection conn;
        System.out.println("XenIF ---> Parse domlist from xml file");
        //TODO: Parse list of domain from xml file
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());

        poplist = xmldom.getPoplist();
        zonelist = xmldom.getZonelist();
        compreslist = xmldom.getReslist();
        xenreslist = xmldom.getXeninfolist();
        /* Connect to XEN DC for each zone */
        for (XenService value : xenreslist.values()) {
            conn = new Connection(new URL("http://" + value.getMaster()));

            Session.loginWithPassword(conn, value.getUsername(), value.getPassword(), APIVersion.latest().toString());

            System.out.println("XenIF ---> Logging in to " + value.getMaster() + " as a " + value.getUsername() + " with the password " + value.getPassword());
            value.setConnection(conn);
            // Some data are created when downloading the VM images.
            //As it is assumed that the download is already perform such data are retrieved 
            //Data retrieved are VM, host, Storage Repository
            value.setMapVmPool(VM.getAllRecords(conn));
            value.setMapHostPool(Host.getAllRecords(conn));
            value.setMapSrPool(SR.getAllRecords(conn));
            value.setMapPifPool(PIF.getAllRecords(conn));
        }

        System.out.println(
                "XenIF ---> Retrieve all VM, Host, SR data");
        StartServer startserv = new StartServer();

        SingletonEventBus.getBus().post(startserv);
    }
    

    @Subscribe
    public void handle_ComputeAllocateRequest(ComputeAllocateRequest servallreq) throws Exception {
        System.out.println("XenIF ---> allocate compute service request" );
        VIMAllocateComputeRequest compreq = servallreq.getRequest();
        String servid = new String("");
        List<MetaDataInner> inputdata = servallreq.getRequest().getMetadata();
        for (int i = 0; i < inputdata.size(); i++) {
            if(inputdata.get(i).getKey().compareTo("ServiceId") == 0) {
                servid = inputdata.get(i).getValue();
            }
        }
        
        //Take Label VM from VCImageID field
        XenService xenel = xenreslist.get(0);
        if (xenel == null) {
            System.out.println("XenIF ---> No resource pool for zoneid= " + servallreq.getRequest().getLocationConstraints());
            VIMVirtualCompute compel = new VIMVirtualCompute();
            ComputeAllocateReply servallrep = new ComputeAllocateReply(servallreq.getReqId(),
                    compel);
            System.out.println("XenIF ---> post ComputeAllocateReply");
            SingletonEventBus.getBus().post(servallrep);
            return;
        }
        String vmlabel = servallreq.getRequest().getVcImageId();
        String ipAddress = "";
        boolean found = false;
        for (Map.Entry<VM, VM.Record> e : xenel.getMapVmPool().entrySet()) {
            if (e.getValue().nameLabel.equals(vmlabel)) {
                found = true;
                //String mobileName = vmlabel + String.valueOf(compservcounter);
                //VM vmCopy;
                //VM.Record vmCopyRecord;
                //vmCopy = e.getKey().createClone(xenel.getConnection(), mobileName); // create a clone of the VM target
                //vmCopyRecord = vmCopy.getRecord(xenel.getConnection());

  
                /* Start Vm */
                e.getKey().start(xenel.getConnection(), false, false);

                /* get IP address of VIF associated */
                
                //Waiting about 15 seconds to start the VM
                for (int i = 15; i >= 0; i--) {
                    System.out.print("Waiting for: " + i + " seconds\r");
                    Thread.sleep(1000);   // //Pause for 1 second [long milliseconds]
                }
                VMGuestMetrics metrics = e.getKey().getGuestMetrics(xenel.getConnection());
                VMGuestMetrics.Record metricsRecord = metrics.getRecord(xenel.getConnection());
                Map<String, String> networkIpMap = metricsRecord.networks;
                for (Map.Entry<String, String> e2 : networkIpMap.entrySet()) {
                    if (e2.getKey().equals("0/ip")) {
                        System.out.println("Key: " + e2.getKey() + " Value: " + e2.getValue());
                        ipAddress = e2.getValue();
                    }
                }
                System.out.println("[HypervisorManager:startVm]VM start. Ip addrees: " + ipAddress);

                /* Add the new VM to map */
               //xenel.getMapVmPool().put(vmCopy, vmCopyRecord); // add the VM to the map
               ComputeService el =  new ComputeService(Integer.toString(compservcounter), vmlabel, servallreq.getRequest().getLocationConstraints());
               compservlist.put(Integer.toString(compservcounter), el); 
            }
        }
        if (found == false) {
            System.out.println("XenIF ---> No WM with label= " + servallreq.getRequest().getVcImageId());
            VIMVirtualCompute compel = new VIMVirtualCompute();
            ComputeAllocateReply servallrep = new ComputeAllocateReply(servallreq.getReqId(),
                    compel);
            System.out.println("XenIF ---> post ComputeAllocateReply");
            SingletonEventBus.getBus().post(servallrep);
            return;
        }
        //TODO: Chack VM and VIF object to built the virtualnetworkinterfaces object in response
        //set virtualnetworkinterface
        List<VIMVirtualComputeVirtualNetworkInterface> vintflist = new ArrayList<VIMVirtualComputeVirtualNetworkInterface>();
        VIMVirtualComputeVirtualNetworkInterface vintf = new VIMVirtualComputeVirtualNetworkInterface();
        List <String> iplist = new ArrayList<String>();
        iplist.add(ipAddress);
        vintf.setIpAddress(iplist);
        
        VIMVirtualCompute compel = new VIMVirtualCompute();
        compel.setVirtualNetworkInterface(vintflist);
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
        
        compservcounter++;
        ComputeAllocateReply servallrep = new ComputeAllocateReply(servallreq.getReqId(),
                    compel);
        System.out.println("XenIF ---> post ComputeAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
    }
    
    @Subscribe
    public void handle_ComputeTerminateRequest(ComputeTerminateRequest servtermreq) throws Exception {
        System.out.println("XenIF ---> terminate compute service request" );
        List<String> resplist = new ArrayList();
        for (int i = 0; i < servtermreq.getRequest().size(); i++) {
            String key = servtermreq.getRequest().get(i);
            ComputeService servel = compservlist.get(key);
            if (servel != null) {
                String vmlabel = servel.getVmlabel();
                String zoneid = servel.getZoneid();
                XenService xenel = xenreslist.get(zoneid);
                if (xenel == null) {
                    System.out.println("XenIF ---> No resource pool for service zoneid= " + zoneid);
                    ComputeTerminateReply servtermrep = new ComputeTerminateReply(servtermreq.getReqId(), new ArrayList());
                    System.out.println("XenIF ---> post ComputeTerminateReply");
                    SingletonEventBus.getBus().post(servtermrep);
                    return;
                }
                /* Select VM target */
                VM vmTemp = null;
		VM.Record vmRecordTemp = null; 
                boolean found  = false;
                for (Map.Entry<VM, VM.Record> e : xenel.getMapVmPool().entrySet()) {
                    if (e.getValue().nameLabel.equals(vmlabel)) {
                        vmTemp = e.getKey();
                        vmRecordTemp = e.getValue();
                        found = true;
                        System.out.println("XenIF ---> Vm found: " + e.getValue().nameLabel);
                        break;
                    }
                }
                if (found == false) {
                    System.out.println("XenIF ---> No VM found for label= " + vmlabel);
                    ComputeTerminateReply servtermrep = new ComputeTerminateReply(servtermreq.getReqId(), new ArrayList());
                    System.out.println("XenIF ---> post ComputeTerminateReply");
                    SingletonEventBus.getBus().post(servtermrep);
                    return;
		}
                
		vmTemp.shutdown(xenel.getConnection());
		/* Destroy associated VDI */
//		Set <VBD> setVbdTemp = vmRecordTemp.VBDs;
//		//System.out.println("Size: " + setVbdTemp.size() );						
//		for (VBD vdbel : setVbdTemp ) {
//			VBD.Record vbdRecordTemp = vdbel.getRecord(xenel.getConnection());
//			if (vbdRecordTemp.empty == false) {
//				VDI vdiTemp = vbdRecordTemp.VDI;
//				System.out.println("XenIF ---> Destroy VDI: " + vdiTemp.getRecord(xenel.getConnection()).nameLabel);
//				vdiTemp.destroy(xenel.getConnection());
//				break;
//			}
//		}
		/* Deleted associated VDI */
                //vmTemp.suspend(xenel.getConnection());
		//vmTemp.destroy(xenel.getConnection());
		
		//xenel.getMapVmPool().remove(vmTemp);
                compservlist.remove(key);
                resplist.add(key);
            }
            resplist.add(key);
        }
        
        ComputeTerminateReply servtermrep = new ComputeTerminateReply(servtermreq.getReqId(), resplist);
        System.out.println("XenIF ---> post ComputeTerminateReply");
        SingletonEventBus.getBus().post(servtermrep);
    }
   
    
    public AllocateNetworkResult allocateNetData(AllocateNetworkRequest netreq) {
        System.out.println("XenIF ---> allocate network data " );
        AllocateNetworkResult resp = new AllocateNetworkResult();
        String datanetid = "datanet" + netservcounter;
        String subnetid = "subnet" + netservcounter;
        netservcounter++;
        NetworkService servel = new NetworkService("",datanetid,subnetid,"","","");
        netservlist.put(datanetid, servel);
        
        //formate response
        AllocateNetworkResultNetworkData netdata = new AllocateNetworkResultNetworkData();
        netdata.setNetworkResourceId(datanetid);
        netdata.setNetworkResourceName(netreq.getNetworkResourceName());
        netdata.setNetworkType(netreq.getNetworkResourceType());
        return resp;
    }
    
    public AllocateNetworkResult allocateSubnetData(AllocateNetworkRequest subnetreq) {
        System.out.println("XenIF ---> allocate subnet data " );
        AllocateNetworkResult resp = new AllocateNetworkResult();
        boolean vxlanflag = false, serviceflag = false;
        //retrieve the networkservie element
        NetworkService el = netservlist.get(subnetreq.getTypeSubnetData().getNetworkId());
        if (el == null) {
           System.out.println("XenIF ---> error retrieve service network element for subnet data" );
           return resp;
        }
        
        //check if it is present the floating_required field in metadata
        for (int i = 0; (i < subnetreq.getMetadata().size()) && (serviceflag == false); i++) {
            if (subnetreq.getMetadata().get(i).getKey().compareTo("ServiceId") == 0) {
                serviceflag = true;
                
                el.setServiceid(subnetreq.getMetadata().get(i).getValue());
            }
        }
        if (serviceflag == false) {
            System.out.println("XenIF ---> error retrieve service id for subnet data" );
           return resp;
        }
        //check if it is present the floating_required field in metadata
        for (int i = 0; (i < subnetreq.getMetadata().size()) && (vxlanflag == false); i++) {
            if (subnetreq.getMetadata().get(i).getKey().compareTo("floating_required") == 0) {
                vxlanflag = true;
                //change nettypemap to vlan
                //get PIF pool
                for (Map.Entry<PIF, PIF.Record> e : xenreslist.get(0).getMapPifPool().entrySet()) {
                    el.setPIFref(e.getValue().IP);
                }
                String portid = "port" + netservcounter;
                netservcounter++;
                el.setPortid(portid);
            }
        }
        if (vxlanflag == false) {
            System.out.println("XenIF ---> error retrieve floating IP for subnet data" );
           return resp;
        }

        
        //format subnet request
        AllocateNetworkResultSubnetData subnetdata = new AllocateNetworkResultSubnetData();
        //copy subnet data
        subnetdata.setAddressPool(subnetreq.getTypeSubnetData().getAddressPool());
        subnetdata.setCidr(subnetreq.getTypeSubnetData().getCidr());
        subnetdata.setGatewayIp(subnetreq.getTypeSubnetData().getGatewayIp());
        subnetdata.setIpVersion(subnetreq.getTypeSubnetData().getIpVersion());
        subnetdata.setIsDhcpEnabled(subnetreq.getTypeSubnetData().isIsDhcpEnabled());
        subnetdata.setResourceId(subnetreq.getTypeSubnetData().getResourceId());
        subnetdata.setOperationalState("enabled");
        //set subnetid
        subnetdata.setNetworkId(el.getSubnetid());
        resp.setSubnetData(subnetdata);
        //insert modified element in network service map
        netservlist.put(subnetreq.getTypeSubnetData().getNetworkId(), el);
        return resp;
    }
    
    public AllocateNetworkResult allocatePortData(AllocateNetworkRequest portreq) {
        System.out.println("XenIF ---> allocate port data " );
        AllocateNetworkResult resp = new AllocateNetworkResult();
        String subnetid = "";
        String ip = "";
        String portid = "";
        boolean subnetflag = false;
        //retrieve subnet_id
        for (int i = 0; (i < portreq.getMetadata().size()) && (subnetflag == false); i++) {
            if (portreq.getMetadata().get(i).getKey().compareTo("subnet_id") ==0) {
                subnetflag = true;
                subnetid = portreq.getMetadata().get(i).getValue();
            }
        }
        if (subnetflag == false) {
            System.out.println("XenIF ---> error subnet id not found in port data");
            return resp;
        }
        
        for (Map.Entry<String, NetworkService> entry : netservlist.entrySet()) {
            if (entry.getValue().getSubnetid().compareTo(subnetid) == 0) {
                NetworkService el = entry.getValue();
                ip = el.getPIFref();
                portid = el.getPortid();
                break;
            }
        }
        if (portid.compareTo("") == 0) {
           System.out.println("XenIF ---> error port id not found in port data");
            return resp; 
        }
        //format port data
        List<MetaDataInner> metadata = new ArrayList();
        MetaDataInner ipentry = new MetaDataInner();
        ipentry.setKey("floating_ip");
        ipentry.setValue(ip);
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
        //retrieve ServiceId, networktype and segment id from metadata
        switch (servallreq.getE2ewimelem().getNetworkResourceType()) {
            case "data" :
                resp = allocateNetData(servallreq.getE2ewimelem());
                break;
            case "subnet":
                resp = allocateSubnetData(servallreq.getE2ewimelem());
                break;
            case "port":
                resp = allocatePortData(servallreq.getE2ewimelem());
                break;
        }
        VirtualNetworkAllocateReply servallrep = new VirtualNetworkAllocateReply(servallreq.getReqid(),
                     resp);
        System.out.println("XenIF ---> post VirtualNetworkAllocateReply");
        SingletonEventBus.getBus().post(servallrep);
    }
    
    @Subscribe
    public void handle_VirtualNetworkTerminateRequest(VirtualNetworkTerminateRequest servtermreq) {
        System.out.println("XenIF ---> terminate network service request" );
        
        List<String> resplist = new ArrayList();
        for (int i = 0; i < servtermreq.getE2EWIMElem().size(); i++) {
            String key = servtermreq.getE2EWIMElem().get(i);
            NetworkService servel = netservlist.get(key);
            if (servel != null) {
                
                netservlist.remove(key);
            }
            resplist.add(key);
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
