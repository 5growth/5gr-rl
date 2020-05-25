/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReq;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.MecResourcesApi;
import java.util.List;



/**
 *
 * @author efabuba
 */
public class TerminateMECThread extends Thread {
    private List<DomainElem> dominfolist;
    private ComputeTerminateMECReq request;
    public TerminateMECThread (List<DomainElem> val, ComputeTerminateMECReq req) {
        dominfolist = val;
        request = req;
    }

    
    @Override
    public void run() {
                
        for (int i = 0; i <dominfolist.size(); i++) {
            DomainElem dominfo = dominfolist.get(i);
            String mecreq = request.getMecreq().get(i);
            
            //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
            String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
            ApiClient capi = new ApiClient();
            capi.setBasePath(basepath);
            MecResourcesApi api = new MecResourcesApi(capi);
            //List<ComputeIds> dummy = new ArrayList();

            //TEST TEST
            try {

                api.serviceRequestsRequestIdDelete(mecreq);

            } catch (ApiException e) {
                System.out.println("ApiException inside allocateCompute().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            }
        }

        ComputeTerminateMECReply ev = new ComputeTerminateMECReply(request.getReqid(),request.getServid(),
                    request.getComputereq(),request.getMecreq());
        //send event
        SingletonEventBus.getBus().post(ev);
    }   
}
