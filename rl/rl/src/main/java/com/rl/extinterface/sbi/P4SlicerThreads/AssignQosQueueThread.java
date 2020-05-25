/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.P4SlicerThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.VirtualQueueAssignment.QosPerformanceIsolationReply;
import com.rl.events.resourcemanagement.VirtualQueueAssignment.QosPerformanceIsolationReq;
import com.rl.extinterface.nbi.swagger.model.AssignQosQueueOnosRequest;
import com.rl.extinterface.nbi.swagger.model.AssignQosQueueOnosResponse;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.QosQueueApi;


/**
 *
 * @author efabuba
 */
public class AssignQosQueueThread extends Thread {
    private DomainElem dominfo;
    private QosPerformanceIsolationReq request;
    public AssignQosQueueThread(DomainElem val, QosPerformanceIsolationReq req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {
        
        String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        ApiClient capi = new ApiClient();
        //set timeout to 10minutes as the default (10s) may be not enough 
        capi.setConnectTimeout(1200000);
        capi.setReadTimeout(1200000);
        capi.setWriteTimeout(1200000);
        capi.setBasePath(basepath);
        capi.setUsername("onos");
        capi.setPassword("rocks");
        //TODO: Extend Client api
        QosQueueApi api = new QosQueueApi(capi);
        QosPerformanceIsolationReply qosperfrep;

        AssignQosQueueOnosResponse qosQueueOnosResponse;
        //Convert AssignQosQueueRequest to AssignQosQueueOnosRequest
        AssignQosQueueOnosRequest assignQosQueueOnosRequest = new AssignQosQueueOnosRequest();
        assignQosQueueOnosRequest.setSrcEndpoint(request.getQosQueuereq().getSrcEndpoint());
        assignQosQueueOnosRequest.setDstEndpoint(request.getQosQueuereq().getDstEndpoint());
        assignQosQueueOnosRequest.setMaxCapacity(request.getQosQueuereq().getMaxCapacity());
        assignQosQueueOnosRequest.setPolicy(request.getQosQueuereq().getPolicy());
        assignQosQueueOnosRequest.setSwitchIdentifiers(request.getQosQueuereq().getSwitchIdentifiers());
        assignQosQueueOnosRequest.setSliceId(request.getQosQueuereq().getSliceId());

        try {
            //Send Request
            qosQueueOnosResponse = api.assignQueue(assignQosQueueOnosRequest);
        } catch (ApiException e) {
            System.out.println("ApiException inside allocateCompute().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            //TODO: Handle error response
            return;
        }

        //send event
        qosperfrep= new QosPerformanceIsolationReply(request.getReqid(), request.getServid(),
                request.getDomid(), request.getQosQueuereq(), request.getNfvipopid());
        SingletonEventBus.getBus().post(qosperfrep);

    }
}
