/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateMECReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateMECReq;
import com.rl.extinterface.nbi.swagger.model.MECTrafficServiceCreationResponse;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.MecResourcesApi;



/**
 *
 * @author efabuba
 */
public class AllocateMECThread extends Thread  {
    private DomainElem dominfo;
    private ComputeAllocateMECReq request;

 
    public AllocateMECThread (DomainElem val, ComputeAllocateMECReq req) {
        dominfo = val;
        request = req;
    }
    
    @Override
    public void run() {
       //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        MecResourcesApi api = new MecResourcesApi(capi);
        
        MECTrafficServiceCreationResponse mecresponse = new MECTrafficServiceCreationResponse();

        //set appDNs and filter rules with IP address and MAC addrss from virtual compute
        for (int j = 0 ; j < request.getMecreq().getAppDNSRule().size(); j++) {
            request.getMecreq().getAppDNSRule().get(j).setIpAddress(request.getVmreq().getVirtualNetworkInterface().get(0).getIpAddress().get(0));
        }
        for (int i = 0; i < request.getMecreq().getAppTrafficRule().size(); i++) {
            for (int j = 0; j < request.getMecreq().getAppTrafficRule().get(i).getDstInterface().size(); j++) {
                request.getMecreq().getAppTrafficRule().get(i).getDstInterface().get(j).setDstIPAddress(request.getVmreq().getVirtualNetworkInterface().get(0).getIpAddress().get(0));
                request.getMecreq().getAppTrafficRule().get(i).getDstInterface().get(j).setDstMACAddress(request.getVmreq().getVirtualNetworkInterface().get(0).getMacAddress());
            }
        }
        
        try {
            //Filter nfviPopComputeInformationRequest = null;
            mecresponse = api.serviceRequestsPost(request.getMecreq());
        } catch (ApiException e) {
            System.out.println("ApiException inside AllocateMECThread()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            ComputeAllocateMECReply ev = new ComputeAllocateMECReply(request.getReqid(), request.getServid(),
                        request.getMecdomid(), "", request.getVmreq());
            SingletonEventBus.getBus().post(ev);
            return;
        }
        
        
        ComputeAllocateMECReply ev = new ComputeAllocateMECReply(request.getReqid(), request.getServid(),
                        request.getMecdomid(), mecresponse.getRequestId(), request.getVmreq());
        //TODO: prepare event to collect info
        //send event
        SingletonEventBus.getBus().post(ev);
    }  
}
