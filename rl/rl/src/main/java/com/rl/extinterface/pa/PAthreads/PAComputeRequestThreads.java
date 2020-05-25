/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.pa.PAthreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.LocalPAConfig;
import com.rl.events.placement.PAComputeCall;
import com.rl.events.placement.PAComputeReply;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInput;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPopReqs;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPops;
import com.rl.extinterface.pa.swagger.client.model.InlineResponse200;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.InterNfviPopCompRouteApi;
import java.util.ArrayList;

/**
 *
 * @author efabuba
 */
public class PAComputeRequestThreads extends Thread {
    private LocalPAConfig painfo;
    private PAComputeCall callreq;
    public PAComputeRequestThreads (LocalPAConfig val, PAComputeCall req) {
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
        
        //set route input according the pa compute call
        ArrayList<CompRouteInputNfviPops> popslist = new ArrayList<CompRouteInputNfviPops>();
        popslist.add(callreq.getPoplist());
        
        ArrayList<CompRouteInputNfviPopReqs> popsreqlist = new ArrayList<CompRouteInputNfviPopReqs>();
        popsreqlist.add(callreq.getPopreq());

        routeinfo.setNfviPops(popslist);
        routeinfo.setNfviPopReqs(popsreqlist);
        

        PAComputeReply comprep;
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
        long nfvipopid = Long.valueOf(response.getCompRouteOutput().getNfviPopResp().get(0).getNfviPopId()); 
        long vimid = Long.valueOf(response.getCompRouteOutput().getNfviPopResp().get(0).getVimId());
        long zoneid = Long.valueOf(response.getCompRouteOutput().getNfviPopResp().get(0).getZoneAtts().get(0).getZoneId());
        comprep = new PAComputeReply(callreq.getReqid(), callreq.getServid(), nfvipopid, vimid, zoneid);
        SingletonEventBus.getBus().post(comprep);
    }
    
}
