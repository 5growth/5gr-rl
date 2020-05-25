/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimNetworkResourcesApi;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efabuba
 */
public class AllocateVIMNetworkThread extends Thread {
    private List<DomainElem> dominfo;
    private NetworkAllocateVIMReq request;
    public AllocateVIMNetworkThread (List<DomainElem> val, NetworkAllocateVIMReq req) {
        dominfo = val;
        request = req;
    }
    
    private VirtualNetwork AllocateVLAN(VimNetworkResourcesApi api, int index) {

        AllocateNetworkResult initrep;
        AllocateNetworkRequest initreq = new AllocateNetworkRequest();
        //take the vlan
        long vlanid = request.getVlans().get(index);
        String intrapop = request.getIntraPopLinks().get(index);
        String[] vals = intrapop.split(";");
        VirtualNetwork netel = new VirtualNetwork();
        if (vlanid == Long.valueOf(vals[2])) {
            //nothing to do send reply
            netel.setNetworkType("dummy");
            netel.setNetworkResourceId("dummy");
            netel.setBandwidth(BigDecimal.ZERO);
            netel.setIsShared(Boolean.TRUE);
            netel.setNetworkResourceName("dummy");
            netel.setOperationalState("enabled");
            netel.setSegmentType("VLAN");
            netel.setSharingCriteria("");
        } else {
            //TODO: Update VLAN
            initreq.setNetworkResourceName(vals[1]);
            initreq.setNetworkResourceType("network");
            initreq.setReservationId("my-" + vals[1]);
            //initreq.setTypeNetworkData(vals[0]);

            List<MetaDataInner> metadatas = new ArrayList<MetaDataInner>();
            MetaDataInner data = new MetaDataInner();

            data.setKey("interpop_vlan");
            data.setValue(String.valueOf(vlanid));
            metadatas.add(data);

            initreq.setMetadata(metadatas);

            try {
                initrep = api.vIMallocateNetwork(initreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside vIMallocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return null;
            }
            //Copy network data in virtual network

            netel.setBandwidth(initrep.getNetworkData().getBandwidth());
            netel.setIsShared(initrep.getNetworkData().isIsShared());
            netel.setNetworkPort(initrep.getNetworkData().getNetworkPort());
            netel.setNetworkQoS(initrep.getNetworkData().getNetworkQoS());
            netel.setNetworkResourceId(initrep.getNetworkData().getNetworkResourceId());
            netel.setNetworkResourceName(initrep.getNetworkData().getNetworkResourceName());
            netel.setNetworkType(initrep.getNetworkData().getNetworkType());
            netel.setOperationalState(initrep.getNetworkData().getOperationalState());
            netel.setSegmentType(initrep.getNetworkData().getSegmentType());
            netel.setSharingCriteria(initrep.getNetworkData().getSharingCriteria());

            netel.setSubnet(initrep.getNetworkData().getSubnet());
        }
        return netel;
    }
    
    private VirtualNetwork AllocateVXLAN(VimNetworkResourcesApi api, int index) {
        //nothing to do send a dummy reply
        VirtualNetwork netel = new VirtualNetwork();
        netel.setNetworkType("dummy");
        netel.setNetworkResourceId("dummy");
        netel.setBandwidth(BigDecimal.ZERO);
        netel.setIsShared(Boolean.TRUE);
        netel.setNetworkResourceName("dummy");
        netel.setOperationalState("enabled");
        netel.setSegmentType("VXLAN");
        netel.setSharingCriteria("");
        
        //TODO: For xen send a start of VNF 
        return netel;
    } 
    
    
    @Override
    public void run() {
        
        NetworkAllocateVIMReply allvimrep;
        ArrayList<VirtualNetwork> vimnetlist = new ArrayList();
 
              

        for (int i = 0; i < request.getVimdomlist().size(); i++) {
            //String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/" + dominfo.get(i).getName();
            String basepath = null;
            if (dominfo.get(i).getName().contains("OpenStack") == true) {
                basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/v1";
            } else {
                basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort();
            }

            //String basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort();
            ApiClient capi = new ApiClient();
            capi.setConnectTimeout(600000);
            capi.setReadTimeout(600000);
            capi.setWriteTimeout(600000);
            capi.setBasePath(basepath);
            VimNetworkResourcesApi api = new VimNetworkResourcesApi(capi);

            AllocateNetworkResult networkresponse;
            List<MetaDataInner> datalist = new ArrayList();
            AllocateNetworkRequest inputreq = new AllocateNetworkRequest();
            String servid = new String("");
            //Retrieve service id from metadata    
            for (int j = 0; j < request.getNetworkRequest().getMetaData().size(); j++) {
                if (request.getNetworkRequest().getMetaData().get(j).getKey().compareTo("ServiceId") == 0) {
                    servid = request.getNetworkRequest().getMetaData().get(j).getValue();
                }
            }
            //retrieve wan link. 
            //As each link is has to be associated to two VIM domain take , 
            //just take the quotient of i

            VirtualNetwork netel = new VirtualNetwork();
            switch (request.getNetworkRequest().getNetworkLayer()) {
                case ("VLAN"):
                    netel = AllocateVLAN(api, i);
                    break;
                case ("VXLAN"):
                    netel = AllocateVXLAN(api, i);
                    break;
            }
            //Copy network data in virtual network
//                VirtualNetwork netel = new VirtualNetwork();
//                netel.setNetworkResourceId("123456");
            vimnetlist.add(netel);
                
//            } else {
//                int wimpos = i / 2;
//                inputreq.setNetworkResourceType("network");
//                MetaDataInner data = new MetaDataInner();
//                data.setKey("ServiceId");
//                data.setValue(servid);
//                datalist.add(data);
//                data = new MetaDataInner();
//                data.setKey("NetworkType");
//                data.setValue(request.getWimnetlist().get(wimpos).getNetworkType());
//                datalist.add(data);
//                data = new MetaDataInner();
//                data.setKey("SegmentType");
//                data.setValue(request.getWimnetlist().get(wimpos).getSegmentType());
//                datalist.add(data);
//                inputreq.setMetadata(datalist);
//
//                //Add 
//                try {
//
//                    networkresponse = api.vIMallocateNetwork(inputreq);
//                } catch (ApiException e) {
//                    System.out.println("ApiException inside allocateNetwork VIM().");
//                    System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
//                    allvimrep = new NetworkAllocateVIMReply(request.getReqid(), request.getServid(),
//                            false, 0, "", null, request.getLogicalPathId());
//                    allvimrep.setErrorcode(e.getCode());
//                    allvimrep.setErrormsg(e.getMessage());
//                    SingletonEventBus.getBus().post(allvimrep);
//                    return;
//
//                }
//                //Copy network data in virtual network
//                VirtualNetwork netel = new VirtualNetwork();
//
//                netel.setBandwidth(networkresponse.getNetworkData().getBandwidth());
//                netel.setIsShared(networkresponse.getNetworkData().isIsShared());
//                netel.setNetworkPort(networkresponse.getNetworkData().getNetworkPort());
//                netel.setNetworkQoS(networkresponse.getNetworkData().getNetworkQoS());
//                netel.setNetworkResourceId(networkresponse.getNetworkData().getNetworkResourceId());
//                netel.setNetworkResourceName(networkresponse.getNetworkData().getNetworkResourceName());
//                netel.setNetworkType(networkresponse.getNetworkData().getNetworkType());
//                netel.setOperationalState(networkresponse.getNetworkData().getOperationalState());
//                netel.setSegmentType(networkresponse.getNetworkData().getSegmentType());
//                netel.setSharingCriteria(networkresponse.getNetworkData().getSharingCriteria());
//                netel.setSubnet(networkresponse.getNetworkData().getSubnet());
//                vimnetlist.add(netel);
//            }
        }
        //send event
        allvimrep = new NetworkAllocateVIMReply(request.getReqId(), request.getServId(),
                true, 0, "", vimnetlist, request.getLogicalPathId());
        SingletonEventBus.getBus().post(allvimrep);
    }
}
