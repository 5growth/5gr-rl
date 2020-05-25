/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author efabuba
 */
public class AllocateVIMNetworkStub extends Thread {
    private ArrayList<DomainElem> dominfo;
    private NetworkAllocateVIMReq request;
    public AllocateVIMNetworkStub (ArrayList<DomainElem> val, NetworkAllocateVIMReq req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {

        NetworkAllocateVIMReply allvimrep;
        ArrayList<VirtualNetwork> vimnetlist = new ArrayList();

        long size = request.getVimdomlist().size();

        Random r = new Random(); 
        int x = r.nextInt(5000);
        long randomNumber = Long.valueOf(x);
        
        
        for (int i = 0; i < size; i++) {
            long j= i+randomNumber;
            VirtualNetwork vn = new VirtualNetwork();
            //UNCOMMENT below row after AllocateParameters class is full compliant with IFA005
            // vn.setBandwidth(request.getNetworkRequest().getTypeNetworkData().getBandwidth); 
            vn.setBandwidth(BigDecimal.valueOf(5000000));
            vn.setIsShared(Boolean.FALSE);
            vn.setNetworkPort(null);
            vn.setNetworkQoS(null);
            vn.setNetworkResourceId("" + j + "");
            vn.setNetworkResourceName("Name" + j + "");
            vn.setNetworkType("l2-vpn");
            vn.setOperationalState("enabled");
            // SET THE IFA005 segmentType ATTRIBUTE PROVIDED BY THE WIM 
            vn.setSegmentType(request.getWimnetlist().get(0).getSegmentType());
            vn.setSharingCriteria(null);
            vn.setSubnet(null);
            vn.setZoneId(null);

            vimnetlist.add(vn);

        }

        //send event with string OK
//        allwimrep = new NetworkAllocateWIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), networkresponse, true);
        allvimrep = new NetworkAllocateVIMReply(request.getReqId(), request.getServId(), true,
                0, null, vimnetlist, request.getLogicalPathId());

        SingletonEventBus.getBus().post(allvimrep);
    }
}
