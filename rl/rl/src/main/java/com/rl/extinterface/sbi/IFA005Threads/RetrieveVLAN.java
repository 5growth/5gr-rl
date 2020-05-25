/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkAllocation.VIMVLANReply;
import com.rl.events.resourcemanagement.NetworkAllocation.VIMVLANRequest;
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
public class RetrieveVLAN extends Thread {
    private List<DomainElem> dominfo;
    private VIMVLANRequest request;

 
    public RetrieveVLAN (List<DomainElem> val, VIMVLANRequest req) {
        dominfo = val;
        request = req;
    }
    
    @Override
    public void run() {
       //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
       //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
       String basepath = null; 
       ArrayList<Long> vlans = new ArrayList<Long>();
        for (int i = 0; i < dominfo.size(); i++) {
            if (dominfo.get(i).getName().contains("OpenStack") == true) {
                basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort() + "/v1";
            }else {
                basepath = "http://" + dominfo.get(i).getIp() + ":" + dominfo.get(i).getPort();
            } 
            ApiClient capi = new ApiClient();
            //set timeout to 10minutes as the default (10s) may be not enough 
            capi.setConnectTimeout(600000);
            capi.setReadTimeout(600000);
            capi.setWriteTimeout(600000);
            capi.setBasePath(basepath);
            VimNetworkResourcesApi api = new VimNetworkResourcesApi(capi);
            List<BigDecimal> vlanlist = new ArrayList<BigDecimal>();
            //TODO: prepare and call free_vlan
            try {
                //Filter nfviPopComputeInformationRequest = null;
                vlanlist = api.freeVlan();
            } catch (ApiException e) {
                System.out.println("ApiException inside freeVlan().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());

                return;
            }
            
            //select first vlan tag from list
            //search if a suggested VLAN is requested
            String intrapop = request.getIntrapoplinks().get(i);
            String[] intrapopinfo = intrapop.split(";");
            if (intrapopinfo[2] == "") {
                //no tag selected . select the first
                System.out.println("NO VLAN tag is selected");
                vlans.add(vlanlist.get(0).longValue());
            } else {
                //search the vlan from the free one if found select it otherwise the first
                vlans.add(Long.valueOf(intrapopinfo[2]));
            }

        }
        //insert vlan in arraylist
        //vlans.add(Long.valueOf(10+i));

            
        VIMVLANReply vlanrep = new VIMVLANReply(request.getLogicalLinkId(), vlans);   
        //send event
        SingletonEventBus.getBus().post(vlanrep);
    }  
}
