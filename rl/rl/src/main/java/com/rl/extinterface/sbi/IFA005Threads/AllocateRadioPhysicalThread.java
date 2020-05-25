/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateRadioReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateRadioReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateReq;
import com.rl.extinterface.nbi.swagger.model.MFRequest;
import com.rl.extinterface.nbi.swagger.model.MFRequestInner;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.RadioResourcesApi;

/**
 *
 * @author osboxes
 */
public class AllocateRadioPhysicalThread extends Thread {
    private DomainElem dominfo;
    private PhysicalAllocateReq request;

    public AllocateRadioPhysicalThread(DomainElem dominfo, PhysicalAllocateReq request) {
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
        RadioResourcesApi api = new RadioResourcesApi(capi);
 
        PhysicalAllocateRadioReply allradiorep;
            
            
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
            allradiorep = new PhysicalAllocateRadioReply(request.getReqid(), request.getServid(),
                    request.getDomid(), false, 0, "", null, request.getNfvipopid(),
                    request.getPnfreqreq());
            allradiorep.setErrorcode(e.getCode());
            allradiorep.setErrormsg(e.getMessage());
            SingletonEventBus.getBus().post(allradiorep);
            return;
        }

        //set mf if present
        if (!request.getMflist().isEmpty()) {
            //RadioResourcesApi rapi = new RadioResourcesApi();
            MFRequest mfresp = new MFRequest();
            MFRequest mfreq = new MFRequest();
            //format input mfrequest 
            for (int i = 0; i < request.getMflist().size(); i++) {
                MFRequestInner mfelem = new MFRequestInner();
                mfelem.setMfid(request.getMflist().get(i));
                mfelem.setStatus("ENABLED");
                mfreq.add(mfelem);
            }
            try {
                //Filter nfviPopComputeInformationRequest = null;
                mfresp = api.setRadioMflist(mfreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside setRadioMflist().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                allradiorep = new PhysicalAllocateRadioReply(request.getReqid(), request.getServid(),
                        request.getDomid(), false, 0, "", null, request.getNfvipopid(),
                        request.getPnfreqreq());
                allradiorep.setErrorcode(e.getCode());
                allradiorep.setErrormsg(e.getMessage());
                SingletonEventBus.getBus().post(allradiorep);
                return;
            }

        }
        
        //send event
        allradiorep = new PhysicalAllocateRadioReply(request.getReqid(), request.getServid(),
                request.getDomid(), true, 0, null, pnfresponse,
                request.getNfvipopid(), request.getPnfreqreq());
        SingletonEventBus.getBus().post(allradiorep);


    }
}
