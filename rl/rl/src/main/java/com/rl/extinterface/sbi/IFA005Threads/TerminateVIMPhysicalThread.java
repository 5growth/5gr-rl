/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateReq;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateVIMReply;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.RadioResourcesApi;

/**
 *
 * @author osboxes
 */
public class TerminateVIMPhysicalThread extends Thread {
    private DomainElem dom;
    private PhysicalTerminateReq request;

    public TerminateVIMPhysicalThread(DomainElem dom, PhysicalTerminateReq request) {
        this.dom = dom;
        this.request = request;
    }
    
    @Override
    public void run() {

        //START - Retrieve from DB the list of domains (domId) to call for compute resource termination 
        //   ArrayList <VIMAbstractElem> vimlist = new ArrayList();
        PhysicalTerminateVIMReply termvimrep;

        
        String basepath = null;
        basepath = "http://" + dom.getIp() + ":" + dom.getPort();


        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        RadioResourcesApi api = new RadioResourcesApi(capi);

        PNFReply resp = new PNFReply();
        PNFReply req = new PNFReply();
        //List<ComputeIds> dummy = new ArrayList();
        req.setPnfResId(request.getVmid());
        req.setMetaData(request.getPnfreq().getMetaData());

        //TEST TEST
        try {

            resp = api.deleteAbstractPn(req);


        } catch (ApiException e) {
            System.out.println("ApiException inside allocateCompute().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            termvimrep = new PhysicalTerminateVIMReply(request.getReqid(), request.getServid(),
                request.getDomid(), request.getPopid(), request.getAbstractpopid(),
                request.getPnfreq(), null, request.getVmid());
            SingletonEventBus.getBus().post(termvimrep);
            return;
        }

        //send event
        termvimrep = new PhysicalTerminateVIMReply(request.getReqid(), request.getServid(),
                request.getDomid(), request.getPopid(), request.getAbstractpopid(),
                request.getPnfreq(), resp, request.getVmid());

        SingletonEventBus.getBus().post(termvimrep);
    }
    
}

