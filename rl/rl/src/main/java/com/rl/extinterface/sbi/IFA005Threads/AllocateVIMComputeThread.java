/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateReq;
import com.rl.extinterface.nbi.swagger.model.VIMAllocateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.VIMAllocateComputeRequestInterfaceData;
import com.rl.extinterface.nbi.swagger.model.VIMVirtualCompute;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimComputeResourcesApi;
import io.swagger.client.api.VimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author efabuba
 */
public class AllocateVIMComputeThread extends Thread {
    private DomainElem dominfo;
    private ComputeAllocateReq request;
    public AllocateVIMComputeThread (DomainElem val, ComputeAllocateReq req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {
        
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = null;
        if (dominfo.getName().contains("OpenStack") == true) {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/v1";
        } else {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        }
        ApiClient capi = new ApiClient();
        //set timeout to 10minutes as the default (10s) may be not enough 
        capi.setConnectTimeout(1200000);
        capi.setReadTimeout(1200000);
        capi.setWriteTimeout(1200000);
        capi.setBasePath(basepath);
        VimComputeResourcesApi api = new VimComputeResourcesApi(capi);
        VimNetworkResourcesApi napi = new VimNetworkResourcesApi(capi);
        ComputeAllocateVIMReply allvimrep;
            
        if (dominfo.getName().contains("OpenStack") == true) {
            //set VM
            VirtualCompute computeresponse = new VirtualCompute();
            VIMVirtualCompute vimcomputeresp;
            VIMAllocateComputeRequest vimallocreq = new VIMAllocateComputeRequest();
            List<VIMAllocateComputeRequestInterfaceData> intflist = new ArrayList();
            for (int j = 0; j < request.getComputereq().getInterfaceData().size(); j++) {
                VIMAllocateComputeRequestInterfaceData intf = new VIMAllocateComputeRequestInterfaceData();
                intf.setIpAddress(request.getComputereq().getInterfaceData().get(j).getIpAddress());
                intf.setMacAddress(request.getComputereq().getInterfaceData().get(j).getMacAddress());
                intflist.add(intf);
            }
            //Convert AllocateRequest to VIMAllocateRequest
            vimallocreq.setAffinityOrAntiAffinityConstraints(request.getComputereq().getAffinityOrAntiAffinityConstraints());
            vimallocreq.setComputeFlavourId(request.getComputereq().getComputeFlavourId());
            vimallocreq.setComputeName(request.getComputereq().getComputeName());
            vimallocreq.setInterfaceData(intflist);
            vimallocreq.setLocationConstraints(request.getComputereq().getLocationConstraints());
            vimallocreq.setMetadata(request.getComputereq().getMetadata());
            vimallocreq.setReservationId(request.getComputereq().getReservationId());
            vimallocreq.setResourceGroupId(request.getComputereq().getResourceGroupId());
            vimallocreq.setUserData(request.getComputereq().getUserData());
            vimallocreq.setVcImageId(request.getComputereq().getVcImageId());
            
            //remove abstract nfvipopid from metadata
            //select entry in metadata
            int nfvipopindex = -1;
            for (int i = 0; i < vimallocreq.getMetadata().size(); i++) {
                if (vimallocreq.getMetadata().get(i).getKey().compareTo("AbstractNfviPopId") == 0) {
                    nfvipopindex = i;
                    break;
                }
            }
            if (nfvipopindex != -1) {
                vimallocreq.getMetadata().remove(nfvipopindex);
            }
            
            try {
                //Filter nfviPopComputeInformationRequest = null;
                vimcomputeresp = api.allocateCompute(vimallocreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside allocateCompute().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                allvimrep = new ComputeAllocateVIMReply(request.getReqid(), request.getServid(),
                        request.getDomid(), false, 0, "", null, request.getNfvipopid(),
                        request.getComputereq());
                allvimrep.setErrorcode(e.getCode());
                allvimrep.setErrormsg(e.getMessage());
                SingletonEventBus.getBus().post(allvimrep);
                return;
            }

            //Copy VIMComputeResponse with VirtualCompute
            computeresponse.setAccelerationCapability(vimcomputeresp.getAccelerationCapability());
            computeresponse.setComputeId(vimcomputeresp.getComputeId());
            computeresponse.setComputeName(vimcomputeresp.getComputeName());
            computeresponse.setFlavourId(vimcomputeresp.getFlavourId());
            computeresponse.setHostId(vimcomputeresp.getHostId());
            computeresponse.setOperationalState(vimcomputeresp.getOperationalState());
            computeresponse.setVcImageId(vimcomputeresp.getVcImageId());
            computeresponse.setVirtualCpu(vimcomputeresp.getVirtualCpu());
            computeresponse.setVirtualDisks(vimcomputeresp.getVirtualDisks());
            computeresponse.setVirtualMemory(vimcomputeresp.getVirtualMemory());
            computeresponse.setVirtualNetworkInterface(vimcomputeresp.getVirtualNetworkInterface());
            computeresponse.setZoneId(vimcomputeresp.getZoneId());
            computeresponse.setMecappID(request.getComputereq().getMecAppDId());
            //send event
            allvimrep = new ComputeAllocateVIMReply(request.getReqid(), request.getServid(),
                    request.getDomid(), true, 0, null, computeresponse,
                    request.getNfvipopid(), request.getComputereq());
            SingletonEventBus.getBus().post(allvimrep);
        } else {
            
            VirtualCompute computeresponse = new VirtualCompute();
            VIMVirtualCompute vimcomputeresp;
            VIMAllocateComputeRequest vimallocreq = new VIMAllocateComputeRequest();
            List<VIMAllocateComputeRequestInterfaceData> intflist = new ArrayList();
            for (int j = 0; j < request.getComputereq().getInterfaceData().size(); j++) {
                VIMAllocateComputeRequestInterfaceData intf = new VIMAllocateComputeRequestInterfaceData();
                intf.setIpAddress(request.getComputereq().getInterfaceData().get(j).getIpAddress());
                intf.setMacAddress(request.getComputereq().getInterfaceData().get(j).getMacAddress());
                intflist.add(intf);
            }

            //Convert AllocateRequest to VIMAllocateRequest
            vimallocreq.setAffinityOrAntiAffinityConstraints(request.getComputereq().getAffinityOrAntiAffinityConstraints());
            vimallocreq.setComputeFlavourId(request.getComputereq().getComputeFlavourId());
            vimallocreq.setComputeName(request.getComputereq().getComputeName());
            vimallocreq.setInterfaceData(intflist);
            vimallocreq.setLocationConstraints(request.getComputereq().getLocationConstraints());
            vimallocreq.setMetadata(request.getComputereq().getMetadata());
            vimallocreq.setReservationId(request.getComputereq().getReservationId());
            vimallocreq.setResourceGroupId(request.getComputereq().getResourceGroupId());
            vimallocreq.setUserData(request.getComputereq().getUserData());
            vimallocreq.setVcImageId(request.getComputereq().getVcImageId());

            try {
                //Filter nfviPopComputeInformationRequest = null;
                vimcomputeresp = api.allocateCompute(vimallocreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside allocateCompute().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                allvimrep = new ComputeAllocateVIMReply(request.getReqid(), request.getServid(),
                        request.getDomid(), false, 0, "", null, request.getNfvipopid(),
                        request.getComputereq());
                allvimrep.setErrorcode(e.getCode());
                allvimrep.setErrormsg(e.getMessage());
                SingletonEventBus.getBus().post(allvimrep);
                return;
            }

            //Copy VIMComputeResponse with VirtualCompute
            computeresponse.setAccelerationCapability(vimcomputeresp.getAccelerationCapability());
            computeresponse.setComputeId(vimcomputeresp.getComputeId());
            computeresponse.setComputeName(vimcomputeresp.getComputeName());
            computeresponse.setFlavourId(vimcomputeresp.getFlavourId());
            computeresponse.setHostId(vimcomputeresp.getHostId());
            computeresponse.setOperationalState(vimcomputeresp.getOperationalState());
            computeresponse.setVcImageId(vimcomputeresp.getVcImageId());
            computeresponse.setVirtualCpu(vimcomputeresp.getVirtualCpu());
            computeresponse.setVirtualDisks(vimcomputeresp.getVirtualDisks());
            computeresponse.setVirtualMemory(vimcomputeresp.getVirtualMemory());
            computeresponse.setVirtualNetworkInterface(vimcomputeresp.getVirtualNetworkInterface());
            computeresponse.setZoneId(vimcomputeresp.getZoneId());
            computeresponse.setMecappID(request.getComputereq().getMecAppDId());
            //send event
            allvimrep = new ComputeAllocateVIMReply(request.getReqid(), request.getServid(),
                    request.getDomid(), true, 0, null, computeresponse,
                    request.getNfvipopid(), request.getComputereq());
            SingletonEventBus.getBus().post(allvimrep);
        }
    }
}
