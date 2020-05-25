/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.pa.PAthreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.LocalPAConfig;
import com.rl.events.placement.PANetworkCall;
import com.rl.events.placement.PANetworkReply;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInput;
import com.rl.extinterface.pa.swagger.client.model.CompRouteOutputInterWanLinks;
import com.rl.extinterface.pa.swagger.client.model.InlineResponse200;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.InterNfviPopCompRouteApi;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class PANetworkRequestThread extends Thread{
    private LocalPAConfig painfo;
    private PANetworkCall callreq;
    public PANetworkRequestThread (LocalPAConfig val, PANetworkCall req) {
        painfo = val;
        callreq = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + painfo.getIp() + ":" + painfo.getPort() + "/" + painfo.getName();
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        InterNfviPopCompRouteApi api = new InterNfviPopCompRouteApi(capi);
        
        CompRouteInput routeinfo = new CompRouteInput();
        InlineResponse200 response;
        
        //set route input according the pa network call
        routeinfo.absWanTopo(callreq.getAbsWanTopo());
        routeinfo.setDstPEId(callreq.getPEdst());
        routeinfo.setInterWanLinks(callreq.getInterWanLinks());
        routeinfo.setNfviPops(callreq.getNfviPops());
        routeinfo.setPaId(BigDecimal.valueOf(callreq.getReqid()));
        routeinfo.setQosCons(callreq.getQosCons());
        routeinfo.setSrcPEId(callreq.getPEsrc());
   
        PANetworkReply netrep;
        //Retrieve nfvipop query, no filter
        try {
            //Filter nfviPopComputeInformationRequest = null;
            response = api.compRouteInterNfviPop(Long.toString(callreq.getReqid()), routeinfo);
        } catch (ApiException e) {
            System.out.println("ApiException inside compRouteInterNfviPop()."); 
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return;
        }
        //send event
        netrep = new PANetworkReply(callreq.getReqid(), callreq.getServid(), callreq.getLogicallinkid(),
            response.getCompRouteOutput().getInterNfviConnectivityId(), response.getCompRouteOutput().getReqBw(),
            response.getCompRouteOutput().getInterWanLinks(), response.getCompRouteOutput().getWanPaths(),
            response.getCompRouteOutput().getNfviPopResp());
        SingletonEventBus.getBus().post(netrep);  
    }
}
