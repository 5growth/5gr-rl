/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateRadioReply;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateReq;
import com.rl.extinterface.nbi.swagger.model.MFRequest;
import com.rl.extinterface.nbi.swagger.model.MFRequestInner;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.RadioResourcesApi;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author osboxes
 */
public class TerminateRadioPhysicalThread extends Thread {
    private DomainElem dom;
    private PhysicalTerminateReq request;

    public TerminateRadioPhysicalThread(DomainElem dom, PhysicalTerminateReq request) {
        this.dom = dom;
        this.request = request;
    }
    
    @Override
    public void run() {

        //START - Retrieve from DB the list of domains (domId) to call for compute resource termination 
        //   ArrayList <VIMAbstractElem> vimlist = new ArrayList();
        PhysicalTerminateRadioReply termvimrep;

        
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
        

        //terminate MF if exists
        if (!request.getMflist().isEmpty()) {
            //remove mf
            List<String> mflist = new ArrayList();
            mflist = request.getMflist();
            //RadioResourcesApi rapi = new RadioResourcesApi();
            MFRequest mfresp = new MFRequest();
            MFRequest mfreq = new MFRequest();
            //format input mfrequest 
            for (int j = 0; j < mflist.size(); j++) {
                MFRequestInner mfelem = new MFRequestInner();
                mfelem.setMfid(mflist.get(j));
                mfelem.setStatus("DISABLED");
                mfreq.add(mfelem);
            }
            try {
                //Filter nfviPopComputeInformationRequest = null;
                mfresp = api.deleteRadioMflist(mfreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside deleteRadioMflist().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                termvimrep = new PhysicalTerminateRadioReply(request.getReqid(), request.getServid(),
                        request.getDomid(), request.getPopid(), request.getAbstractpopid(),
                        request.getPnfreq(), null, request.getVmid());
                SingletonEventBus.getBus().post(termvimrep);
                return;
            }
        }
        
        //remove PNF
        try {

            resp = api.deleteAbstractPn(req);


        } catch (ApiException e) {
            System.out.println("ApiException inside deleteAbstractPn().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            termvimrep = new PhysicalTerminateRadioReply(request.getReqid(), request.getServid(),
                request.getDomid(), request.getPopid(), request.getAbstractpopid(),
                request.getPnfreq(), null, request.getVmid());
            SingletonEventBus.getBus().post(termvimrep);
            return;
        }

        //send event
        termvimrep = new PhysicalTerminateRadioReply(request.getReqid(), request.getServid(),
                request.getDomid(), request.getPopid(), request.getAbstractpopid(),
                request.getPnfreq(), resp, request.getVmid());

        SingletonEventBus.getBus().post(termvimrep);
    }
    
}
