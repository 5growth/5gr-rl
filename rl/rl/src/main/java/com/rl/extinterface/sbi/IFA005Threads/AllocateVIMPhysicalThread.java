/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateRadioReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateReq;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateVIMReply;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimComputeResourcesApi;

/**
 *
 * @author osboxes
 */
public class AllocateVIMPhysicalThread extends Thread {
    private DomainElem dominfo;
    private PhysicalAllocateReq request;

    public AllocateVIMPhysicalThread(DomainElem dominfo, PhysicalAllocateReq request) {
        this.dominfo = dominfo;
        this.request = request;
    }
    
    @Override
    public void run() {
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = null;
        basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        
        ApiClient capi = new ApiClient();
        //set timeout to 10minutes as the default (10s) may be not enough 
        capi.setConnectTimeout(1200000);
        capi.setReadTimeout(1200000);
        capi.setWriteTimeout(1200000);
        capi.setBasePath(basepath);
        VimComputeResourcesApi api = new VimComputeResourcesApi(capi);
 
        PhysicalAllocateVIMReply allvimrep;
            
            
        PNFReply pnfresponse = new PNFReply();
        
        PNFRequest pnfreq = new PNFRequest();
        
        pnfreq.setMetaData(request.getPnfreqreq().getMetaData());
        pnfreq.setPnfId(request.getPnfreqreq().getPnfId());
        pnfreq.setPopId(String.valueOf(request.getNfvipopid()));
        try {
            //Filter nfviPopComputeInformationRequest = null;
            pnfresponse = api.setNfviPnflist(pnfreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside etNfviPnflist().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            allvimrep = new PhysicalAllocateVIMReply(request.getReqid(), request.getServid(),
                    request.getDomid(), false, 0, "", null, request.getNfvipopid(),
                    request.getPnfreqreq());
            allvimrep.setErrorcode(e.getCode());
            allvimrep.setErrormsg(e.getMessage());
            SingletonEventBus.getBus().post(allvimrep);
            return;
        }

        
        //send event
        allvimrep = new PhysicalAllocateVIMReply(request.getReqid(), request.getServid(),
                request.getDomid(), true, 0, null, pnfresponse,
                request.getNfvipopid(), request.getPnfreqreq());
        SingletonEventBus.getBus().post(allvimrep);
    }
}
