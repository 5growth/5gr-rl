/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateRadioReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateRadioReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateReq;
import com.rl.extinterface.nbi.swagger.model.MFRequest;
import com.rl.extinterface.nbi.swagger.model.MFRequestInner;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.RadioResourcesApi;
import io.swagger.client.api.VimComputeResourcesApi;
import io.swagger.client.api.VimNetworkResourcesApi;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author efabuba
 */
public class TerminateRadioComputeThread extends Thread {
    private List<DomainElem> dominfoList;
    private ComputeTerminateReq request;

    public TerminateRadioComputeThread(List<DomainElem> dominfoList, ComputeTerminateReq request) {
        this.dominfoList = dominfoList;
        this.request = request;
    }

    
    @Override
    public void run() {
        //START - Retrieve from DB the list of domains (domId) to call for compute resource termination 
        //   ArrayList <VIMAbstractElem> vimlist = new ArrayList();
        ComputeTerminateRadioReply termvimrep;
        ArrayList<Boolean> outcomeList = new ArrayList();
        ArrayList<Integer> errorcodeList = new ArrayList();
        ArrayList<String> errormsgList = new ArrayList();
        long size = dominfoList.size();
        
        System.out.println("enter terminate radio thread.");
        for (int i = 0; i < size; i++) {
            DomainElem dominfo = dominfoList.get(i);
            // List of identifiers of terminated VMs
            List<String> compid = new ArrayList();
          
            
       
            //Virtual Machine identifier for which the termination is to be requested to the specific Domain
             compid.add(request.getVmIdList().get(i));
            //compid.add(request.getComputeTermElem().getTerminateRequest().get(i));
            
            
            //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
            String basepath = null;
            if (dominfo.getName().contains("OpenStack") == true) {
                basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/v1";
            } else {
                basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
            }
            
            if (dominfo.getName().contains("OpenStack") == true) {
                ApiClient capi = new ApiClient();
                capi.setBasePath(basepath);
                capi.setConnectTimeout(600000);
                capi.setReadTimeout(600000);
                capi.setWriteTimeout(600000);
                VimComputeResourcesApi api = new VimComputeResourcesApi(capi);

                List<String> complist = new ArrayList();
                //List<ComputeIds> dummy = new ArrayList();
                
                //terminate MF if exists
                if(!request.getMfmatrix().get(i).isEmpty()) {
                    //remove mf
                    ArrayList<String> mflist = new ArrayList();
                    mflist = request.getMfmatrix().get(i);
                    RadioResourcesApi rapi = new RadioResourcesApi();
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
                        mfresp = rapi.deleteRadioMflist(mfreq);
                    } catch (ApiException e) {
                        System.out.println("ApiException inside deleteRadioMflist().");
                        System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                        outcomeList.add(Boolean.FALSE);
                        errorcodeList.add(e.getCode());
                        errormsgList.add(e.getMessage());
                        break;
                    }
                }
                
                
                //terminate VM
                try {

                    complist = api.terminateAbstractResources(compid);

                } catch (ApiException e) {
                    System.out.println("ApiException inside allocateCompute().");
                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                    outcomeList.add(Boolean.FALSE);
                    errorcodeList.add(e.getCode());
                    errormsgList.add(e.getMessage());
                }
                //terminate network
                VimNetworkResourcesApi vapi = new VimNetworkResourcesApi(capi);
                try {
                    //Filter nfviPopComputeInformationRequest = null;
                    complist = vapi.vIMterminateNetworks(complist);
                } catch (ApiException e) {
                    System.out.println("ApiException inside terminateNetworks() VIM.");
                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                    outcomeList.add(Boolean.FALSE);
                    errorcodeList.add(e.getCode());
                    errormsgList.add(e.getMessage());
                    break;
                }
                outcomeList.add(Boolean.TRUE);
                errorcodeList.add(0);
                errormsgList.add("");
            } else {
                ApiClient capi = new ApiClient();
                capi.setBasePath(basepath);
                VimComputeResourcesApi api = new VimComputeResourcesApi(capi);

                List<String> complist = new ArrayList();
                //List<ComputeIds> dummy = new ArrayList();

                
                //terminate MF if exists
                if(!request.getMfmatrix().get(i).isEmpty()) {
                    //remove mf
                    ArrayList<String> mflist = new ArrayList();
                    mflist = request.getMfmatrix().get(i);
                    RadioResourcesApi rapi = new RadioResourcesApi();
                    MFRequest mfresp = new MFRequest();
                    MFRequest mfreq = new MFRequest();
                    //format input mfrequest 
                    for (int j = 0; j < mflist.size(); i++) {
                        MFRequestInner mfelem = new MFRequestInner();
                        mfelem.setMfid(mflist.get(j));
                        mfelem.setStatus("DISABLED");
                        mfreq.add(mfelem);
                    }
                    try {
                        //Filter nfviPopComputeInformationRequest = null;
                        mfresp = rapi.deleteRadioMflist(mfreq);
                    } catch (ApiException e) {
                        System.out.println("ApiException inside deleteRadioMflist().");
                        System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                        outcomeList.add(Boolean.FALSE);
                        errorcodeList.add(e.getCode());
                        errormsgList.add(e.getMessage());
                        break;
                    }
                }
                
                
                //terminate VM
                try {

                    complist = api.terminateAbstractResources(compid);

                    outcomeList.add(Boolean.TRUE);
                    errorcodeList.add(0);
                    errormsgList.add("");

                } catch (ApiException e) {
                    System.out.println("ApiException inside allocateCompute().");
                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                    outcomeList.add(Boolean.FALSE);
                    errorcodeList.add(e.getCode());
                    errormsgList.add(e.getMessage());
                }
            }

        }

        //send event
        termvimrep = new ComputeTerminateRadioReply(request.getReqid(), request.getServid(), 
                request.getDomlist(), outcomeList, request.getPoplist(), 
                request.getComputeTermElem().getTerminateRequest(), errorcodeList, errormsgList);

        SingletonEventBus.getBus().post(termvimrep);
        
        
    }   
}
