/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.StubThreads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkData;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultSubnetData;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

/**
 *
 * @author efabuba
 */
public class AllocateIntraPopVIMNetworkStub extends Thread {
    private DomainElem dominfo;
    private IntraPoPAllocateVIMRequest request;
   
    public AllocateIntraPopVIMNetworkStub (DomainElem val, IntraPoPAllocateVIMRequest req) {
        dominfo = val;
        request = req;
    }
    
    
    @Override
    public void run() {

        IntraPoPAllocateVIMReply allvimrep;
        AllocateNetworkResult networkresponse = new AllocateNetworkResult();

       // long size = request.getVimdomlist().size();

        Random r = new Random(); 
        int x = r.nextInt(5000);
        long randomNumber = Long.valueOf(x);
        
        
       // for (int i = 0; i < size; i++) {
            long j = randomNumber;
         
            VirtualNetwork VN=new VirtualNetwork();
            VN.segmentType("pippo");
            
            
            String str=Long.toString(j);   
           // networkresponse.getNetworkData().setNetworkResourceId(str);
           AllocateNetworkResultNetworkData netdata = new AllocateNetworkResultNetworkData();
           netdata.setNetworkResourceId(str);  
           netdata.setSegmentType(str);
             netdata.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
              netdata.setNetworkType(request.getIntrapopreq().getNetworkResourceType());
              netdata.setIsShared(Boolean.FALSE);
              
              
              AllocateNetworkResultSubnetData subnet = new AllocateNetworkResultSubnetData();
              
              
            subnet.setCidr(request.getIntrapopreq().getTypeSubnetData().getCidr());
            
            
              subnet.setGatewayIp(request.getIntrapopreq().getTypeSubnetData().getGatewayIp()); 
                subnet.setIpVersion(request.getIntrapopreq().getTypeSubnetData().getIpVersion()); 
               subnet.setNetworkId(str); 
              subnet.setResourceId(str + str); 
              subnet.setIsDhcpEnabled(Boolean.TRUE);
              
              
              
               List<Integer> addressPool = new ArrayList<Integer>();
             
               addressPool.add(2);
               addressPool.add(100);
               
              subnet.setAddressPool(addressPool);
              
              
              
              networkresponse.setNetworkData(netdata);
               networkresponse.setSubnetData(subnet);
//            VirtualNetwork vn = new VirtualNetwork();
//            //UNCOMMENT below row after AllocateParameters class is full compliant with IFA005
//            // vn.setBandwidth(request.getNetworkRequest().getTypeNetworkData().getBandwidth); 
//            vn.setBandwidth(BigDecimal.valueOf(5000000));
//            vn.setIsShared(Boolean.FALSE);
//            vn.setNetworkPort(null);
//            vn.setNetworkQoS(null);
//            vn.setNetworkResourceId("" + j + "");
//            vn.setNetworkResourceName("Name" + j + "");
//            vn.setNetworkType("l2-vpn");
//            vn.setOperationalState("enabled");
//            // SET THE IFA005 segmentType ATTRIBUTE PROVIDED BY THE WIM 
//            vn.setSegmentType(request.getWimnetlist().get(0).getSegmentType());
//            vn.setSharingCriteria(null);
//            vn.setSubnet(null);
//            vn.setZoneId(null);

         
            //Set the segmentType=vlanid
                    

        //}

        //send event with string OK
//        allwimrep = new NetworkAllocateWIMReply( request.getReqId(),request.getServId(), 
//                        request.getDomId(), networkresponse, true);

       // allvimrep = new NetworkAllocateVIMReply(request.getReqId(), request.getServId(), true,
        //        0, null, vimnetlist, request.getLogicalPathId());

       // SingletonEventBus.getBus().post(allvimrep);
        
        
         allvimrep = new IntraPoPAllocateVIMReply(request.getReqid(), request.getServid(), request.getNfvipopid(),
                                    request.getVimid(), request.getIntrapopreq(), networkresponse);
        SingletonEventBus.getBus().post(allvimrep);
        
        
        
        
    }
}
