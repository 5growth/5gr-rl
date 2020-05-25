/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi.IFA005Threads;

import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;

//import com.mtp.extinterface.nbi.swagger.model.PortData;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.VimNetworkResourcesApi;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author efabuba
 */
public class AllocateIntraPopVIMNetworkThread extends Thread {
    private DomainElem dominfo;
    private IntraPoPAllocateVIMRequest request;
    static long  subnetcounter = 1;
    public AllocateIntraPopVIMNetworkThread (DomainElem val, IntraPoPAllocateVIMRequest req) {
        dominfo = val;
        request = req;
    }
    
    
    private String VLANSelectionLogic() {
        String basepath = null;
        ArrayList<Long> vlans = new ArrayList<Long>();

        if (dominfo.getName().contains("OpenStack") == true) {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/v1";
        } else {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
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
            return null;
        }
        String vlanid = "";
        boolean found = false;
        for (int i = 0; (i < request.getIntrapopreq().getTypeSubnetData().getMetadata().size()) && (found == false); i++) {
            if (request.getIntrapopreq().getTypeSubnetData().getMetadata().get(i).getKey().compareTo("interpop_vlan") == 0) {
                found = true;
                vlanid = request.getIntrapopreq().getTypeSubnetData().getMetadata().get(i).getValue();
            }
        }
        if ((found == false) || (vlanid.compareTo("")== 0)) {
           System.out.println("NO VLAN tag is selected. Select the first free");
           vlanid = String.valueOf(vlanlist.get(0)); 
           //set metadata object
           boolean found2 = false;
           for (int i = 0; (i < request.getIntrapopreq().getTypeSubnetData().getMetadata().size()) && (found2 == false); i++) {
            if (request.getIntrapopreq().getTypeSubnetData().getMetadata().get(i).getKey().compareTo("interpop_vlan") == 0) {
                found2 = true;
                request.getIntrapopreq().getTypeSubnetData().getMetadata().get(i).setValue(vlanid);
            }
           }
           if (found2 == false) {
               //create new object in metadata
               MetaDataInner vlanobj = new MetaDataInner();
               vlanobj.setKey("interpop_vlan");
               vlanobj.setValue(vlanid);
               //add obj in request  to store vlan
               request.getIntrapopreq().getTypeSubnetData().getMetadata().add(vlanobj);
           }
        } else {
           System.out.println("VLAN tag found. value = " + vlanid); 
        } 
        return vlanid;
       
    }

    private AllocateNetworkResult AllocateIntraPopVIMNetworkPrivate(VimNetworkResourcesApi api) {
        AllocateNetworkResult initrep;
        AllocateNetworkRequest initreq = new AllocateNetworkRequest();
        boolean ip_floating_required = false;
        String external_network_id = null;
        api.getApiClient().setConnectTimeout(600000);
        api.getApiClient().setReadTimeout(600000);
        api.getApiClient().setWriteTimeout(600000);
        initreq.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
        initreq.setReservationId(request.getIntrapopreq().getNetworkResourceName());
        initreq.setNetworkResourceType("network");

        String vlanid = null;

        try {

            initrep = api.vIMallocateNetwork(initreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;

        }
        request.getIntrapopreq().setNetworkResourceName("subnet-" + request.getIntrapopreq().getNetworkResourceName());
        request.getIntrapopreq().setReservationId("mysubnet-" + request.getIntrapopreq().getNetworkResourceName());
        request.getIntrapopreq().getTypeSubnetData().setNetworkId(initrep.getNetworkData().getNetworkResourceId());
        //remove the abstract pop in the request

        AllocateNetworkRequest reqel = new AllocateNetworkRequest();
        reqel.setAffinityOrAntiAffinityConstraints(request.getIntrapopreq().getAffinityOrAntiAffinityConstraints());
        reqel.setLocationConstraints(request.getIntrapopreq().getLocationConstraints());
        reqel.setMetadata(request.getIntrapopreq().getMetadata());
        reqel.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
        reqel.setReservationId(request.getIntrapopreq().getReservationId());
        reqel.setResourceGroupId(request.getIntrapopreq().getResourceGroupId());
        reqel.setTypeNetworkData(request.getIntrapopreq().getTypeNetworkData());
        reqel.setTypeNetworkPortData(request.getIntrapopreq().getTypeNetworkPortData());
        reqel.setTypeSubnetData(request.getIntrapopreq().getTypeSubnetData());
        reqel.setNetworkResourceType("subnet");
        AllocateNetworkResult networkresponse = new AllocateNetworkResult();
        for (int i = 0; i < reqel.getMetadata().size(); i++) {
            if (reqel.getMetadata().get(i).getKey().compareTo("AbstractNvfiPoPId") == 0) {
                reqel.getMetadata().remove(i);
                break;
            }
        }
        try {
            networkresponse = api.vIMallocateNetwork(reqel);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;
        }
        //Set networkdata
        networkresponse.setNetworkData(initrep.getNetworkData());

        return networkresponse;
    }
    
    private AllocateNetworkResult AllocateIntraPopVIMNetworkVlan(VimNetworkResourcesApi api){
        AllocateNetworkResult initrep;
        AllocateNetworkRequest initreq = new AllocateNetworkRequest();
        boolean ip_floating_required = false;
        String external_network_id = null;
        api.getApiClient().setConnectTimeout(600000);
        api.getApiClient().setReadTimeout(600000);
        api.getApiClient().setWriteTimeout(600000);
        initreq.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
        initreq.setReservationId(request.getIntrapopreq().getNetworkResourceName());
        initreq.setNetworkResourceType("network");
        
        String vlanid = null;
        vlanid = VLANSelectionLogic();
        //retrieve vlan tag
        
        MetaDataInner data = new  MetaDataInner();
        
        data.setKey("interpop_vlan");
        data.setValue(vlanid);
        MetaData metadata = new MetaData();
        metadata.add(data);
        initreq.setMetadata(metadata);
        
        try {

            initrep = api.vIMallocateNetwork(initreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;

        }
        request.getIntrapopreq().setNetworkResourceName("subnet-" + request.getIntrapopreq().getNetworkResourceName());
        request.getIntrapopreq().setReservationId("mysubnet-" + request.getIntrapopreq().getNetworkResourceName());
        request.getIntrapopreq().getTypeSubnetData().setNetworkId(initrep.getNetworkData().getNetworkResourceId());
        //set CIDR and AddressPool if not present
        if ((request.getIntrapopreq().getTypeSubnetData().getCidr().compareTo("null") ==0) &&
            (request.getIntrapopreq().getTypeSubnetData().getAddressPool().isEmpty()==true)) {
            String cidr_str = new String("");
            cidr_str = "192.168." + subnetcounter + ".0/24";
            subnetcounter++;
            if (subnetcounter > 254) subnetcounter = 1; //avoid to overcome 255 in IP address
            request.getIntrapopreq().getTypeSubnetData().setCidr(cidr_str);
            
            List<Integer> pool = new ArrayList<Integer>();
            pool.add(1);
            request.getIntrapopreq().getTypeSubnetData().setAddressPool(pool);
        }
        //remove the abstract pop in the request
        
        AllocateNetworkRequest reqel = new AllocateNetworkRequest();
        reqel.setAffinityOrAntiAffinityConstraints(request.getIntrapopreq().getAffinityOrAntiAffinityConstraints()); 
        reqel.setLocationConstraints(request.getIntrapopreq().getLocationConstraints()); 
        reqel.setMetadata(request.getIntrapopreq().getMetadata()); 
        reqel.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName()); 
        reqel.setReservationId(request.getIntrapopreq().getReservationId()); 
        reqel.setResourceGroupId(request.getIntrapopreq().getResourceGroupId());
        reqel.setTypeNetworkData(request.getIntrapopreq().getTypeNetworkData());
        reqel.setTypeNetworkPortData(request.getIntrapopreq().getTypeNetworkPortData());
        reqel.setTypeSubnetData(request.getIntrapopreq().getTypeSubnetData());
        reqel.setNetworkResourceType("subnet");
        AllocateNetworkResult networkresponse = new AllocateNetworkResult();
        for (int i = 0; i < reqel.getMetadata().size(); i++) {
            if (reqel.getMetadata().get(i).getKey().compareTo("AbstractNvfiPoPId") == 0) {
                reqel.getMetadata().remove(i);
                break;
            }
        }
        try {
            networkresponse = api.vIMallocateNetwork(reqel);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;
        }
        //Set networkdata
        networkresponse.setNetworkData(initrep.getNetworkData());
        
    return networkresponse;
 }
 private AllocateNetworkResult AllocateIntraPopVIMNetworkVxlan(VimNetworkResourcesApi api){
        AllocateNetworkResult initrep;
        AllocateNetworkRequest initreq = new AllocateNetworkRequest();
        boolean ip_floating_required = false;
        String external_network_id = null;
        api.getApiClient().setConnectTimeout(600000);
        api.getApiClient().setReadTimeout(600000);
        api.getApiClient().setWriteTimeout(600000);
        
        
        initreq.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
        initreq.setReservationId(request.getIntrapopreq().getNetworkResourceName());
        initreq.setNetworkResourceType("network");
        try {

            initrep = api.vIMallocateNetwork(initreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;

        }
        request.getIntrapopreq().setNetworkResourceName("subnet-" + request.getIntrapopreq().getNetworkResourceName());
        request.getIntrapopreq().setReservationId("mysubnet-" + request.getIntrapopreq().getNetworkResourceName());
        request.getIntrapopreq().getTypeSubnetData().setNetworkId(initrep.getNetworkData().getNetworkResourceId());
        //remove the abstract pop in the request
        //set CIDR and Address Pool if not present
        if ((request.getIntrapopreq().getTypeSubnetData().getCidr().compareTo("null") ==0) &&
            (request.getIntrapopreq().getTypeSubnetData().getAddressPool().isEmpty()==true)) {
            String cidr_str = new String("");
            cidr_str = "192.168." + subnetcounter + ".0/24";
            subnetcounter++;
            if (subnetcounter > 254) subnetcounter = 1; //avoid to overcome 255 in IP address
            request.getIntrapopreq().getTypeSubnetData().setCidr(cidr_str);
            List<Integer> pool = new ArrayList<Integer>();
            pool.add(1);
            request.getIntrapopreq().getTypeSubnetData().setAddressPool(pool);
        }
        
        
        AllocateNetworkRequest reqel = new AllocateNetworkRequest();
        reqel.setAffinityOrAntiAffinityConstraints(request.getIntrapopreq().getAffinityOrAntiAffinityConstraints());
        reqel.setLocationConstraints(request.getIntrapopreq().getLocationConstraints());
        reqel.setMetadata(request.getIntrapopreq().getMetadata());
        reqel.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
        reqel.setReservationId(request.getIntrapopreq().getReservationId());
        reqel.setResourceGroupId(request.getIntrapopreq().getResourceGroupId());
        reqel.setTypeNetworkData(request.getIntrapopreq().getTypeNetworkData());
        reqel.setTypeNetworkPortData(request.getIntrapopreq().getTypeNetworkPortData());
        reqel.setTypeSubnetData(request.getIntrapopreq().getTypeSubnetData());
        reqel.setNetworkResourceType("subnet");
        AllocateNetworkResult networkresponse = new AllocateNetworkResult();
        for (int i = 0; i < reqel.getMetadata().size(); i++) {
            if (reqel.getMetadata().get(i).getKey().compareTo("AbstractNvfiPoPId") == 0) {
                reqel.getMetadata().remove(i);
                continue;
            }
            if (reqel.getMetadata().get(i).getKey().compareTo("floating_required") == 0) {
                if (reqel.getMetadata().get(i).getValue().compareTo("True") == 0) {
                    ip_floating_required = true;
                } else {
                    ip_floating_required = false;
                }
                continue;
            }
            if (reqel.getMetadata().get(i).getKey().compareTo("external-network-id") == 0) {
                external_network_id = reqel.getMetadata().get(i).getValue();
                continue;
            }
        }
        try {
            networkresponse = api.vIMallocateNetwork(reqel);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;
        }
        //Set networkdata
        networkresponse.setNetworkData(initrep.getNetworkData());

//TO BE TEST FOR VXLAN        
        
        if (ip_floating_required) {
            AllocateNetworkRequest netreq = new AllocateNetworkRequest();
            //format network resource data
            netreq.setNetworkResourceName("port-" + request.getIntrapopreq().getNetworkResourceName());
            netreq.setReservationId("myport-" + request.getIntrapopreq().getNetworkResourceName());
            netreq.setNetworkResourceType("network-port");
            //set port data
            //PortData portdata = new PortData();

            //portdata.setNetworkId(external_network_id);

            //create metadata values
            MetaData data = new MetaData();
            //set floating ip
            MetaDataInner datael = new MetaDataInner();
            datael.setKey("type");
            datael.setValue("floating_ip");
            data.add(datael);
            //set networkid
            datael = new MetaDataInner();
            datael.setKey("subnet_id");
            datael.setValue(networkresponse.getSubnetData().getResourceId());
            data.add(datael);

            //portdata.setMetadata(data);
            //netreq.setTypeNetworkPortData(portdata.getsegmentId());
            AllocateNetworkResult netresp;
            try {
                //Filter nfviPopComputeInformationRequest = null;
                netresp = api.vIMallocateNetwork(netreq);
            } catch (ApiException e) {
                System.out.println("ApiException inside vIMallocateNetwork().");
                System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
                return null;
            }
            //Set response in networkresponse port data
            networkresponse.setNetworkPortData(netresp.getNetworkPortData());        
    }
    return networkresponse;
 }
     
    
    private AllocateNetworkResult AllocateIntraPopVIMRouter(VimNetworkResourcesApi api) {
        AllocateNetworkResult initrep;
        AllocateNetworkRequest initreq = new  AllocateNetworkRequest();
        initreq.setNetworkResourceName(request.getIntrapopreq().getNetworkResourceName());
        initreq.setNetworkResourceType("router");
        List<MetaDataInner> metadatas = request.getIntrapopreq().getMetadata();
        List <String> excludeMetaData = Arrays.asList("ServiceId", "AbstractNvfiPoPId");
        List<MetaDataInner> newMetaDatas = metadatas.stream().filter(line -> !excludeMetaData.contains(line.getKey()))
                .collect(Collectors.toList());
//        metadatas.forEach(System.out::println);
        initreq.setMetadata(newMetaDatas);

        try {
            initrep = api.vIMallocateNetwork(initreq);
        } catch (ApiException e) {
            System.out.println("ApiException inside vIMallocateNetwork().");
            System.out.println("Val= " + e.getCode() + ";Message = " + e.getMessage());
            return null;
        }
        return initrep;
    }


    @Override
    public void run() {
        
        IntraPoPAllocateVIMReply allvimrep;

        //SET THE REQUEST IFA005 segmentType ATTRIBUTE PROVIDED BY THE WIM 
        //UNCOMMENT AFTER THE API BUGS ARE FIXED !!!!
        //request.getNetworkRequest().setTypeNetworkData().setSegmentType(request.getWimnetlist().get(0).setSegmentType(segmentType));
        //String basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/" + dominfo.getName();
        String basepath = null;
        if (dominfo.getName().contains("OpenStack") == true) {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort() + "/v1";
        } else {
            basepath = "http://" + dominfo.getIp() + ":" + dominfo.getPort();
        }
        ApiClient capi = new ApiClient();
        capi.setBasePath(basepath);
        VimNetworkResourcesApi api = new VimNetworkResourcesApi(capi);
        AllocateNetworkResult networkresponse = null;

        switch (request.getIntrapopreq().getNetworkResourceType()) {
            case ("subnet-vlan"):
                networkresponse = AllocateIntraPopVIMNetworkVlan(api);
                break;
            case ("subnet"):
                networkresponse = AllocateIntraPopVIMNetworkPrivate(api);
                break;
            case ("subnet-vxlan"):
                networkresponse = AllocateIntraPopVIMNetworkVxlan(api);
                break;
            case ("router"):
                networkresponse = AllocateIntraPopVIMRouter(api);
                break;
        }
        if (networkresponse == null) {
            System.out.println("Error subnet response void");
            return;
        }

        //send event
        allvimrep = new IntraPoPAllocateVIMReply(request.getReqid(), request.getServid(), request.getNfvipopid(),
                                    request.getVimid(), request.getIntrapopreq(), networkresponse);
        SingletonEventBus.getBus().post(allvimrep);
    }

}
