/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateReq;
import com.rl.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeVirtualMemory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class AllocateVIMComputeStub extends Thread {

    private DomainElem dominfo;
    private ComputeAllocateReq request;

    public AllocateVIMComputeStub(DomainElem val, ComputeAllocateReq req) {
        dominfo = val;
        request = req;
    }

    @Override
    public void run() {

        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
//        ApiClient capi = new ApiClient();
//        capi.setBasePath(basepath);
//        VirtualisedComputeResourcesApi api = new VirtualisedComputeResourcesApi(capi);

        VirtualCompute computeresponse = new VirtualCompute();
        ComputeAllocateVIMReply allvimrep;
        VirtualComputeVirtualCpu virtualCpu = new VirtualComputeVirtualCpu();
        VirtualComputeVirtualMemory virtualMem = new VirtualComputeVirtualMemory();
        List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> virtualNetworkInterface = new ArrayList<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface>();
        ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface interface_1 = new ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface();
        ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface interface_2 = new ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface();
        double clock = 2.2;
        long mem = 1024;
        //Virtual Cpu
        virtualCpu.setCpuArchitecture("x86_64");
        virtualCpu.setNumVirtualCpu(4);
        virtualCpu.setCpuClock(BigDecimal.valueOf(clock));
        //Virtual Memory
        virtualMem.setNumaEnabled(false);
        virtualMem.setVirtualMemSize(BigDecimal.valueOf(mem));

        //Virtual Network Interface 1
        interface_1.setBandwidth("1000");
        //String iplist1 = new (iplist1)
        List<String>iplist1 = new ArrayList<String>();
        iplist1.add("10.10.10.10");
        interface_1.setIpAddress(iplist1);
        interface_1.setMacAddress("MAC_ADD_1");
        //Virtual Network Interface 2
        interface_2.setBandwidth("100");
        List<String>iplist2 = new ArrayList<String>();
        iplist2.add("11.11.11.11");
        interface_2.setIpAddress(iplist2);
        interface_2.setMacAddress("MAC_ADD_2");

        virtualNetworkInterface.add(interface_1);
        virtualNetworkInterface.add(interface_2);

        computeresponse.setComputeId(Long.toString(request.getReqid()));
        computeresponse.setFlavourId(request.getComputereq().getComputeFlavourId());
        computeresponse.setComputeName(request.getComputereq().getComputeName());
        computeresponse.setHostId("127.0.0.1");
        computeresponse.setOperationalState("enable");
        computeresponse.setVirtualCpu(virtualCpu);
        computeresponse.setVirtualMemory(virtualMem);
        computeresponse.setVirtualDisks("Disk1");
        computeresponse.setVirtualNetworkInterface(virtualNetworkInterface);
        computeresponse.setZoneId(request.getComputereq().getLocationConstraints());

        //send event
        allvimrep = new ComputeAllocateVIMReply(request.getReqid(), request.getServid(),
                request.getDomid(), true, 0, null, computeresponse,request.getNfvipopid(), request.getComputereq());
//        allvimrep = new ComputeAllocateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), computeresponse, true, request.getComputeservid(),request.getNfvipopid());
//        allvimrep = new ComputeAllocateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), computeresponse, true, request.getComputeservid());
//        allvimrep = new ComputeAllocateVIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), computeresponse, true);
        SingletonEventBus.getBus().post(allvimrep);
    }
}



