/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author efabuba
 */
public class AllocateWIMNetworkStub extends Thread {
    //private DomainElem dominfo;
    private List<DomainElem> dominfo;
    private NetworkAllocateWIMReq request;
    
    
//    public AllocateWIMNetworkStub (DomainElem val, NetworkAllocateWIMReq req) {
//        dominfo = val;
//        request = req;
//    }
    
    public AllocateWIMNetworkStub (List<DomainElem> val, NetworkAllocateWIMReq req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {
                
       
        NetworkAllocateWIMReply allwimrep;
        ArrayList<VirtualNetwork> wimnetlist = new ArrayList(); 
        
      
        long size=request.getWimdomlist().size();
       
        Random r = new Random(); 
        int x = r.nextInt(5000);
        long randomNumber = Long.valueOf(x);
        
        
        for (int i = 0; i < size; i++) {
        
            
            
        long j= i+randomNumber;
        VirtualNetwork vn=new VirtualNetwork();
        //UNCOMMENT below row after AllocateParameters class is full compliant with IFA005
       // vn.setBandwidth(request.getNetworkRequest().getTypeNetworkData().getBandwidth); 
       vn.setBandwidth(BigDecimal.valueOf(5000000));
       vn.setIsShared(Boolean.FALSE);
       vn.setNetworkPort(null);
       vn.setNetworkQoS(null);
       vn.setNetworkResourceId(""+j+"");
       vn.setNetworkResourceName("Name"+j+"");
       vn.setNetworkType("l2-vpn");
       vn.setOperationalState("enabled");
       vn.setSegmentType(""+j+"");
       vn.setSharingCriteria(null);
       vn.setSubnet(null);
       vn.setZoneId(null);
       
       wimnetlist.add(vn);
        
        }
 
        
        
        //send event with string OK
//        allwimrep = new NetworkAllocateWIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), networkresponse, true);
        allwimrep = new NetworkAllocateWIMReply(request.getReqid(), request.getServid(), true,
                0, null, wimnetlist, request.getLogicalPathId());

        SingletonEventBus.getBus().post(allwimrep);
    }
}
