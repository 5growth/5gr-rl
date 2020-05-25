/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateVIMReply;
import com.rl.extinterface.nbi.swagger.model.VIMVirtualCompute;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimComputeResourcesApi;

/**
 *
 * @author efabuba
 */
public class OperateVIMComputeThread extends Thread {
    private DomainElem dominfo;
    private ComputeOperateReq request;
    public OperateVIMComputeThread (DomainElem val, ComputeOperateReq req) {
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
        capi.setBasePath(basepath);
        VimComputeResourcesApi api = new VimComputeResourcesApi(capi);
        
        VirtualCompute computeresponse = new VirtualCompute();
        VIMVirtualCompute vimcomputeresp;
        ComputeOperateVIMReply opearatevimrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            vimcomputeresp = api.operateCompute(request.getOperateinfo());
        } catch (ApiException e) {
            System.out.println("ApiException inside operateCompute()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            opearatevimrep = new ComputeOperateVIMReply( request.getReqid(), null, 0, null);
            opearatevimrep.setErrcode(e.getCode());
            opearatevimrep.setErrmsg(e.getMessage());
            SingletonEventBus.getBus().post(opearatevimrep);
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
        
        //send event
         opearatevimrep = new ComputeOperateVIMReply( request.getReqid(),computeresponse, 
                        0, null);
        SingletonEventBus.getBus().post(opearatevimrep);
    }
}
