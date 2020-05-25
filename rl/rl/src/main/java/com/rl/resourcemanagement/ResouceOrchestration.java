/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.resourcemanagement;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.common.objects.PendingFedNetworkAllocateRequest;
import com.rl.common.objects.PendingFedNetworkTerminateRequest;
import com.rl.common.objects.PendingNetworkAllocateRequest;
import com.rl.common.objects.PendingNetworkTerminateRequest;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateRadioReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateReq;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstance;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateRadioReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateVIMReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateReq;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstance;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstance;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateInstance;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateWIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.VIMVLANReply;
import com.rl.events.resourcemanagement.NetworkAllocation.VIMVLANRequest;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstance;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateInstance;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateWIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateWIMReq;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReq;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReq;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateInstance;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateRadioReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateReq;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateVIMReply;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateInstance;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateRadioReply;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateReq;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateVIMReply;
import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import com.rl.resourcemanagement.qosperfomanceisolation.model.AssignQosQueueRequest;
import com.rl.events.resourcemanagement.VirtualQueueAssignment.QosPerformanceIsolationReq;
import com.rl.events.resourcemanagement.ApplyPerformanceIsolation.ApplyPerformanceIsolationReq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author user
 */
public class ResouceOrchestration {

    private HashMap<Long, PendingNetworkAllocateRequest> pendingNetAllocReq;
    private HashMap<Long, PendingNetworkTerminateRequest> pendingNetTermReq;
    private HashMap<Integer, PendingFedNetworkAllocateRequest> pendingFedNetAllocReq;
     private HashMap<Long, PendingFedNetworkTerminateRequest> pendingFedNetTermReq;
     private Integer pendingFedNetAllocReqId;
     
     
     

    public ResouceOrchestration() {
        pendingNetAllocReq = new HashMap();
        pendingNetTermReq = new HashMap();
        pendingFedNetAllocReq = new HashMap();
         pendingFedNetTermReq = new HashMap();
         pendingFedNetAllocReqId = 0;
    }

    @Subscribe
    public void handle_E2EComputeAllocateInstance(E2EComputeAllocateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2EComputeAllocateInstance Event");
        //TODO: orchestrate allocate operation

        //Get the AllocateComputeRequest instance and put it inside ComputeAllocateVIMReq
        AllocateComputeRequest computeReq = allinst.getCompAllocElem().getAllocateRequest().getRequest();

//  FROM io.swagger.client.model TO com.mtp.extinterface.nbi.swagger.model
////Get the AllocateComputeRequest instance and put it inside ComputeAllocateVIMReq
//        com.mtp.extinterface.nbi.swagger.model.AllocateComputeRequest computeReq = allinst.getCompAllocElem().getAllocateRequest().getE2EVIMElem();
//        // 
//        List<CreateComputeResourceReservationRequestAntiAffinityConstraint> constraints = (List<CreateComputeResourceReservationRequestAntiAffinityConstraint>) (Object) computeReq.getAffinityOrAntiAffinityConstraints();
//        //
//        String locationConstraints = computeReq.getLocationConstraints();
//        //
//        String reservationId = computeReq.getReservationId();
//        //
//        String computeFlavourId = computeReq.getComputeFlavourId();
//        //
//        String resourceGroupId = computeReq.getResourceGroupId();
//        //
//        String vcImageId = computeReq.getVcImageId();
//        //
//        String computeName = computeReq.getComputeName();
//        //
//        Object metadata = computeReq.getMetadata();
//        //
//        List<AllocateComputeRequestInterfaceData> interfaceData = (List<AllocateComputeRequestInterfaceData>) (Object) computeReq.getInterfaceData();
//        //    
//        AllocateComputeRequestUserData userData = (AllocateComputeRequestUserData) (Object) computeReq.getUserData();
//
//        AllocateComputeRequest allocateComputeReq = new AllocateComputeRequest();
//        allocateComputeReq.setLocationConstraints(locationConstraints);
//        allocateComputeReq.setReservationId(reservationId);
//        allocateComputeReq.setInterfaceData(interfaceData);
//        allocateComputeReq.setAffinityOrAntiAffinityConstraints(constraints);
//        allocateComputeReq.setUserData(userData);
//        allocateComputeReq.setComputeFlavourId(computeFlavourId);
//        allocateComputeReq.setResourceGroupId(resourceGroupId);
//        allocateComputeReq.setMetadata(metadata);
//        allocateComputeReq.setVcImageId(vcImageId);
//        allocateComputeReq.setComputeName(computeName);
        ComputeAllocateReq allvimreq = new ComputeAllocateReq(allinst.getReqid(),
                allinst.getServid(), allinst.getDomid(),
                computeReq, allinst.getNfvipopid(), allinst.getMflist());

//        ComputeAllocateVIMReq allvimreq = new ComputeAllocateVIMReq(allinst.getReqId(),
//                allinst.getServId(), allinst.getDomId(),
//                new AllocateComputeRequest());
        System.out.println("ResouceOrchestration --> Post ComputeAllocateVIMReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_ComputeAllocateVIMReply(ComputeAllocateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_ComputeAllocateVIMReply Event");
      
        
//        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                vimElem, allvimrep.getOutcome(), allvimrep.getComputeservid(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.isOutcome(),
                allvimrep.getComputereply(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        
        System.out.println("ResouceOrchestration --> Post ComputeAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
//        E2EComputeAllocateInstanceOutcome allinstout = new E2EComputeAllocateInstanceOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());

    }
    
    @Subscribe
    public void handle_ComputeAllocateRadioReply(ComputeAllocateRadioReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_ComputeAllocateRadioReply Event");
      
        
//        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                vimElem, allvimrep.getOutcome(), allvimrep.getComputeservid(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.isOutcome(),
                allvimrep.getComputereply(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        
        System.out.println("ResouceOrchestration --> Post ComputeAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
//        E2EComputeAllocateInstanceOutcome allinstout = new E2EComputeAllocateInstanceOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());

    }
    
    @Subscribe
    public void handle_E2EPhysicalAllocateInstance(E2EPhysicalAllocateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2EPhysicalAllocateInstance Event");

        //Get the AllocateComputeRequest instance and put it inside ComputeAllocateVIMReq
        PNFRequest computeReq = allinst.getRequest();

        PhysicalAllocateReq allvimreq = new PhysicalAllocateReq(allinst.getReqid(),
                allinst.getServid(), allinst.getDomid(),
                computeReq, allinst.getNfvipopid(), allinst.getMflist());

//        ComputeAllocateVIMReq allvimreq = new ComputeAllocateVIMReq(allinst.getReqId(),
//                allinst.getServId(), allinst.getDomId(),
//                new AllocateComputeRequest());
        System.out.println("ResouceOrchestration --> Post PhysicalAllocateReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_PhysicalAllocateVIMReply(PhysicalAllocateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_PhysicalAllocateVIMReply Event");
      
        
//        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                vimElem, allvimrep.getOutcome(), allvimrep.getComputeservid(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        PhysicalAllocateDBQueryOutcome dboutcome = new PhysicalAllocateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.isOutcome(),
                allvimrep.getPnfreply(), allvimrep.getNfvipopid(), allvimrep.getPnfrequest());
        
        System.out.println("ResouceOrchestration --> Post PhysicalAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
//        E2EComputeAllocateInstanceOutcome allinstout = new E2EComputeAllocateInstanceOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());

    }
    
    @Subscribe
    public void handle_PhysicalAllocateRadioReply(PhysicalAllocateRadioReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_PhysicalAllocateRadioReply Event");
      
        
//        ComputeAllocateDBQueryOutcome dboutcome = new ComputeAllocateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                vimElem, allvimrep.getOutcome(), allvimrep.getComputeservid(), allvimrep.getNfvipopid(), allvimrep.getAllocatecomputerequest());
        PhysicalAllocateDBQueryOutcome dboutcome = new PhysicalAllocateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.isOutcome(),
                allvimrep.getPnfreply(), allvimrep.getNfvipopid(), allvimrep.getPnfrequest());
        
        System.out.println("ResouceOrchestration --> Post PhysicalAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
//        E2EComputeAllocateInstanceOutcome allinstout = new E2EComputeAllocateInstanceOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());

    }
    
    @Subscribe
    public void handle_VIMVLANReply(VIMVLANReply vlanrep) {
        System.out.println("ResouceOrchestration --> Handle VIMVLANReply Event");
        //retrieve request from pendingreq
        PendingNetworkAllocateRequest pendingel = pendingNetAllocReq.get(vlanrep.getLogicalLinkId());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + vlanrep.getLogicalLinkId());
            //TODO: Send API response
            return;
        }
        pendingel.setVlanlist(vlanrep.getVlans());
        NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(pendingel.getReqid(),
                pendingel.getServid(), pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(),
                pendingel.getInterdomainLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(),
                pendingel.getWimNetworkType(), vlanrep.getVlans(), new ArrayList<String>());
        System.out.println("ResouceOrchestration --> Post NetworkAllocateWIMReq Event");
        SingletonEventBus.getBus().post(allwimreq);
    }

    @Subscribe
    public void handle_E2ENetworkAllocateInstance(E2ENetworkAllocateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2ENetworkAllocateInstance Event");
//        NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(allinst.getReqId(),
//                allinst.getServId(), 3,
//                new AllocateParameters());

        PendingNetworkAllocateRequest pendingRequest = new PendingNetworkAllocateRequest(allinst.getReqid(), allinst.getServid(), allinst.getLogicalPathId(),
                allinst.getNetworkRequest(), allinst.getWimdomlist(), allinst.getVimdomlist(),
                allinst.getInterdomainLinks(), allinst.getIntraPopLinks(), allinst.getWanLinks(),
                allinst.getWimPopList(), allinst.getVimPopList(), allinst.getWimNetworkType(), allinst.getVimNetworkType(), new ArrayList<Long>());

        pendingNetAllocReq.put(allinst.getLogicalPathId(), pendingRequest);
        
        if (allinst.getNetworkRequest().getNetworkLayer().compareTo("VLAN") ==0) {
            //select VLAN event
            VIMVLANRequest vlanreq = new VIMVLANRequest(allinst.getLogicalPathId(), allinst.getNetworkRequest(), allinst.getVimdomlist(), allinst.getIntraPopLinks());
            SingletonEventBus.getBus().post(vlanreq);
            return;
        } else if (allinst.getNetworkRequest().getNetworkLayer().compareTo("VXLAN") ==0) {
            ArrayList<String> ips = new ArrayList<String>();
            //retrieve ip list ans put in array
            for (int i = 0; i < allinst.getIntraPopLinks().size(); i++) {
                String intrapopval = allinst.getIntraPopLinks().get(i);
                String[] vals = intrapopval.split(";");
                ips.add(vals[2]);
            }
            NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getLogicalPathId(), allinst.getNetworkRequest(), allinst.getWimdomlist(),
                allinst.getInterdomainLinks(), allinst.getWanLinks(), allinst.getWimPopList(),
                allinst.getWimNetworkType(), new ArrayList<Long>(), ips);
            SingletonEventBus.getBus().post(allwimreq);
            return;
        }
        //default case
        NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getLogicalPathId(), allinst.getNetworkRequest(), allinst.getWimdomlist(),
                allinst.getInterdomainLinks(), allinst.getWanLinks(), allinst.getWimPopList(),
                allinst.getWimNetworkType(), new ArrayList<Long>(), new ArrayList<String>());

        System.out.println("ResouceOrchestration --> Post NetworkAllocateWIMReq Event");

        SingletonEventBus.getBus().post(allwimreq);
    }

    @Subscribe
    public void handle_NetworkAllocateWIMReply(NetworkAllocateWIMReply allwimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkAllocateWIMReply Event");
        //retrieve request from pendingreq
        PendingNetworkAllocateRequest pendingel = pendingNetAllocReq.get(allwimrep.getLogicallinkid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allwimrep.getLogicallinkid());
            //TODO: Send API response
            return;
        }
        if (allwimrep.isOutcome()) {
            pendingel.setWimnetlist(allwimrep.getWimnetlist());

            NetworkAllocateVIMReq allvimreq = new NetworkAllocateVIMReq(pendingel.getReqid(), pendingel.getServid(),
                    pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
                    pendingel.getIntraPopLinks(), pendingel.getVimPopList(), pendingel.getVimNetworkType(), allwimrep.getWimnetlist(),
                    pendingel.getVlanlist());

            System.out.println("ResouceOrchestration --> Post NetworkAllocateVIMReq Event");
            SingletonEventBus.getBus().post(allvimreq);
        } else {

            NetworkAllocateDBQueryOutcome dbres = new NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
                    false, pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
                    pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
                    pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), null, null);
          

            //remove request from pending
            pendingNetAllocReq.remove(allwimrep.getLogicallinkid());
        }

    }

    @Subscribe
    public void handle_NetworkAllocateVIMReply(NetworkAllocateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle handle_NetworkAllocateVIMReply Event");
        PendingNetworkAllocateRequest pendingel = pendingNetAllocReq.get(allvimrep.getLogicallinkid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allvimrep.getReqid());
            //TODO: Send API response
            return;
        }

        NetworkAllocateDBQueryOutcome dbres = new NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
                allvimrep.isOutcome(), pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
                pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
                pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), pendingel.getWimnetlist(), allvimrep.getVimnetlist());
        


//remove request from pending
        pendingNetAllocReq.remove(allvimrep.getLogicallinkid());
        
         System.out.println("ResouceOrchestration --> Post NetworkAllocateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dbres);
        
        

    }

    @Subscribe
    public void handle_E2EComputeTerminateInstance(E2EComputeTerminateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2EComputeTerminateInstance Event");
        ComputeTerminateReq allvimreq = new ComputeTerminateReq(allinst.getReqid(),
                allinst.getServid(), allinst.getDomlist(), allinst.getPoplist(), allinst.getComputeTermElem(), allinst.getVmIdList(), allinst.getMfmatrix());
//        ComputeTerminateVIMReq allvimreq = new ComputeTerminateVIMReq(allinst.getReqId(),
//                allinst.getServId(), allinst.getDomId());
        System.out.println("ResouceOrchestration --> Post ComputeTerminateVIMReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_ComputeTerminateVIMReply(ComputeTerminateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle ComputeTerminateVIMReply Event");
      
        //Remove negative response from the list of computeId 
        for (int i = 0; i < allvimrep.getOutcomeList().size(); i++) {
            if (allvimrep.getOutcomeList().get(i) == false) {
                allvimrep.getComputeIdList().remove(i);
                allvimrep.getDomIdList().remove(i);
                allvimrep.getErrorcodeList().remove(i);
                allvimrep.getErrormsgList().remove(i);
                allvimrep.getOutcomeList().remove(i);
                allvimrep.getPoplist().remove(i);

            }
        }

        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomIdList(), allvimrep.getOutcomeList(),
                allvimrep.getErrorcodeList(), allvimrep.getErrormsgList(), allvimrep.getPoplist(),
                allvimrep.getComputeIdList());

//        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());
//        
        System.out.println("ResouceOrchestration --> Post ComputeTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
    }
    
    
    @Subscribe
    public void handle_ComputeTerminateRadioReply(ComputeTerminateRadioReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle ComputeTerminateRadioReply Event");
      
        //Remove negative response from the list of computeId 
        for (int i = 0; i < allvimrep.getOutcomeList().size(); i++) {
            if (allvimrep.getOutcomeList().get(i) == false) {
                allvimrep.getComputeIdList().remove(i);
                allvimrep.getDomIdList().remove(i);
                allvimrep.getErrorcodeList().remove(i);
                allvimrep.getErrormsgList().remove(i);
                allvimrep.getOutcomeList().remove(i);
                allvimrep.getPoplist().remove(i);

            }
        }

        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomIdList(), allvimrep.getOutcomeList(),
                allvimrep.getErrorcodeList(), allvimrep.getErrormsgList(), allvimrep.getPoplist(),
                allvimrep.getComputeIdList());

//        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());
//        
        System.out.println("ResouceOrchestration --> Post ComputeTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
    }
    
    @Subscribe
    public void handle_E2EPhysicalTerminateInstance(E2EPhysicalTerminateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2EPhysicalTerminateInstance Event");
        
        PhysicalTerminateReq allvimreq = new PhysicalTerminateReq(allinst.getReqid(),
                allinst.getServid(), allinst.getDomid(), allinst.getPopid(), allinst.getAbstractpopid(),
                allinst.getPnfreq(), allinst.getVmid(), allinst.getMflist());
//        ComputeTerminateVIMReq allvimreq = new ComputeTerminateVIMReq(allinst.getReqId(),
//                allinst.getServId(), allinst.getDomId());
        System.out.println("ResouceOrchestration --> Post PhysicalTerminateReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_PhysicalTerminateVIMReply(PhysicalTerminateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle PhysicalTerminateVIMReply Event");
      
    

        PhysicalTerminateDBQueryOutcome dboutcome = new PhysicalTerminateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.getPopid(),
                allvimrep.getAbstractpopid(), allvimrep.getPnfreq(), allvimrep.getPnfresp(),
                allvimrep.getVmid());

//        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());
//        
        System.out.println("ResouceOrchestration --> Post PhysicalTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
    }
    
    
    @Subscribe
    public void handle_PhysicalTerminateRadioReply(PhysicalTerminateRadioReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle PhysicalTerminateRadioReply Event");
      
        PhysicalTerminateDBQueryOutcome dboutcome = new PhysicalTerminateDBQueryOutcome(allvimrep.getReqid(),
                allvimrep.getServid(), allvimrep.getDomid(), allvimrep.getPopid(),
                allvimrep.getAbstractpopid(), allvimrep.getPnfreq(), allvimrep.getPnfresp(),
                allvimrep.getVmid());

//        ComputeTerminateDBQueryOutcome dboutcome = new ComputeTerminateDBQueryOutcome(allvimrep.getReqId(),
//                allvimrep.getServId(), allvimrep.getDomId(),
//                new VIMAbstractElem(), allvimrep.getOutcome());
//        
        System.out.println("ResouceOrchestration --> Post PhysicalTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);
    }
    
    @Subscribe
    public void handle_E2ENetworkTerminateInstance(E2ENetworkTerminateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2ENetworkTerminateInstance Event");
        //TODO: orchestrate terminate operation

        PendingNetworkTerminateRequest pendingReq = new PendingNetworkTerminateRequest(allinst.getReqid(), allinst.getServid(),
                allinst.getWimdomlistMap(), allinst.getVimdomlistMap(), allinst.getInterdomainLinksMap(),
                allinst.getIntraPopLinksMap(), allinst.getWanLinksMap(), allinst.getWimPopListMap(), allinst.getVimPopListMap(),
                allinst.getWimNetworkTypeMap(), allinst.getVimNetworkTypeMap(), allinst.getWanResourceIdListMap(), allinst.getNetServIdList(), allinst.getLocicalPathList());
        pendingNetTermReq.put(allinst.getReqid(), pendingReq);

        NetworkTerminateVIMReq allvimreq = new NetworkTerminateVIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getVimdomlistMap(), allinst.getInterdomainLinksMap(),
                allinst.getIntraPopLinksMap(), allinst.getVimPopListMap(), allinst.getVimNetworkTypeMap(), allinst.getLocicalPathList());

        
        System.out.println("ResouceOrchestration --> Post NetworkTerminateVIMReq Event");
        SingletonEventBus.getBus().post(allvimreq);
    }

    @Subscribe
    public void handle_NetworkTerminateWIMReply(NetworkTerminateWIMReply allwimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkTerminateWIMReply Event");

        PendingNetworkTerminateRequest pendingel = pendingNetTermReq.get(allwimrep.getReqid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allwimrep.getReqid());
            //TODO: Send API response
            return;
        }

        for (int i = 0; i < pendingel.getLocicalLinkList().size(); i++) {
            boolean fail = false;
            for (int j = 0; (fail == false) && (j < pendingel.getLocicalLinkList().size()); j++) {

                fail = allwimrep.getOutcomeListMap().get(i).get(j);

            }
            if (fail == false) {
                pendingel.getLocicalLinkList().remove(i);
                pendingel.getInterdomainLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getVimNetworkTypeMap().remove(i);
                pendingel.getVimPopListMap().remove(i);
                pendingel.getVimdomlistMap().remove(i);
                pendingel.getWanLinksMap().remove(i);
                pendingel.getWimNetworkTypeMap().remove(i);
                pendingel.getWimPopListMap().remove(i);
                pendingel.getWimdomlistMap().remove(i);
                pendingel.getNetServIdList().remove(i);
            }

        }

       
        
         NetworkTerminateDBQueryOutcome dboutcome = new NetworkTerminateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(), 
         pendingel.getWimdomlistMap(), pendingel.getVimdomlistMap(), pendingel.getInterdomainLinksMap(), pendingel.getIntraPopLinksMap(), 
         pendingel.getWanLinksMap(), pendingel.getWimPopListMap(), pendingel.getVimPopListMap(), pendingel.getWimNetworkTypeMap(), 
         pendingel.getVimNetworkTypeMap(),pendingel.getWanResourceIdListMap() , pendingel.getNetServIdList(), pendingel.getLocicalLinkList());
        System.out.println("ResouceOrchestration --> Post NetworkTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);

        
        
        
        //MOVE BELOW EVENT INSIDE DATABASEDRIVER handle_NetworkTerminateDbQueryOutcome
//        E2ENetworkTerminateInstanceOutcome allinstout = new E2ENetworkTerminateInstanceOutcome(allwimrep.getReqid(),
//                allwimrep.getServid(), new VIMAbstractElem(), allwimrep.getOutcome());
//         E2ENetworkTerminateInstanceOutcome allinstout = new E2ENetworkTerminateInstanceOutcome();
//        System.out.println("ResouceOrchestration --> Post E2ENetworkAllocateInstanceOutcome Event");
//        SingletonEventBus.getBus().post(allinstout);
    }

    @Subscribe
    public void handle_NetworkTerminateVIMReply(NetworkTerminateVIMReply allvimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkterminateVIMReply Event");

        PendingNetworkTerminateRequest pendingel = pendingNetTermReq.get(allvimrep.getReqid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allvimrep.getReqid());
            //TODO: Send API response
            return;
        }

        for (int i = 0; i < pendingel.getLocicalLinkList().size(); i++) {
            boolean fail = false;
            for (int j = 0; (fail == false) && (j < pendingel.getLocicalLinkList().size()); j++) {

                fail = allvimrep.getOutcomeListMap().get(i).get(j);

            }
            if (fail == false) {
                pendingel.getLocicalLinkList().remove(i);
                pendingel.getInterdomainLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getVimNetworkTypeMap().remove(i);
                pendingel.getVimPopListMap().remove(i);
                pendingel.getVimdomlistMap().remove(i);
                pendingel.getWanLinksMap().remove(i);
                pendingel.getWimNetworkTypeMap().remove(i);
                pendingel.getWimPopListMap().remove(i);
                pendingel.getWimdomlistMap().remove(i);
                pendingel.getNetServIdList().remove(i);
            }

        }

        NetworkTerminateWIMReq allwimreq = new NetworkTerminateWIMReq(allvimrep.getReqid(),
                allvimrep.getServid(), pendingel.getWimdomlistMap(), pendingel.getInterdomainLinksMap(), pendingel.getWanLinksMap(),
                pendingel.getWimPopListMap(), pendingel.getWimNetworkTypeMap(), pendingel.getWanResourceIdListMap());

        System.out.println("ResouceOrchestration --> Post NetworkTerminateWIMReq Event");
        SingletonEventBus.getBus().post(allwimreq);

    }
    
  @Subscribe
    public void handle_Fed_E2ENetworkAllocateInstance(Fed_E2ENetworkAllocateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle Fed_NetworkAllocateWIMReq Event");
//        NetworkAllocateWIMReq allwimreq = new NetworkAllocateWIMReq(allinst.getReqId(),
//                allinst.getServId(), 3,
//                new AllocateParameters());

        PendingFedNetworkAllocateRequest pendingRequest = new PendingFedNetworkAllocateRequest(allinst.getReqid(), allinst.getServid(), allinst.getLogicalPathId(),
                allinst.getNetworkRequest(), allinst.getWimdomlist(), allinst.getVimdomlist(),
                allinst.getInterdomainLinks(), allinst.getIntraPopLinks(),  allinst.getWanLinks(),
                allinst.getWimPopList(), allinst.getVimPopList(), allinst.getWimNetworkType(), allinst.getVimNetworkType(), allinst.getFlowRuleEndPointList(), allinst.isAbstrSrcNfviPopOfLogicalPathIsFed());

        
         pendingFedNetAllocReqId++;
         
        
        //pendingFedNetAllocReq.put(allinst.getLogicalPathId(), pendingRequest);
          pendingFedNetAllocReq.put(pendingFedNetAllocReqId, pendingRequest);
        
        
        
        
        Fed_NetworkAllocateWIMReq allwimreq = new Fed_NetworkAllocateWIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getLogicalPathId(), allinst.getNetworkRequest(), allinst.getWimdomlist(),
                allinst.getInterdomainLinks(), allinst.getIntraPopLinks(), allinst.getWanLinks(), allinst.getWimPopList(),
                allinst.getWimNetworkType(), allinst.getFlowRuleEndPointList(), allinst.isAbstrSrcNfviPopOfLogicalPathIsFed(),pendingFedNetAllocReqId);

        System.out.println("ResouceOrchestration --> Post Fed_NetworkAllocateWIMReq Event");

        SingletonEventBus.getBus().post(allwimreq);
    }

 @Subscribe
    public void handle_Fed_NetworkAllocateWIMReply(Fed_NetworkAllocateWIMReply allwimrep) {
        System.out.println("ResouceOrchestration --> Handle Fed_NetworkAllocateWIMReply Event");
        //retrieve request from pendingreq
        PendingFedNetworkAllocateRequest pendingel = pendingFedNetAllocReq.get(allwimrep.getPendingFedNetAllocReqId());
//        if (pendingel == null) {
//            System.out.println("ResouceOrchestration --> No pending request found with key= " + allwimrep.getLogicallinkid());
//            //TODO: Send API response
//            return;
//        }
//        if (allwimrep.isOutcome()) {
//            pendingel.setWimnetlist(allwimrep.getWimnetlist());
//
//            Fed_NetworkAllocateVIMReq allvimreq = new Fed_NetworkAllocateVIMReq(pendingel.getReqid(), pendingel.getServid(),
//                    pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
//                    pendingel.getIntraPopLinks(), pendingel.getVimPopList(), pendingel.getVimNetworkType(), allwimrep.getWimnetlist(),
//                    pendingel.getVlanlist());
//
//            System.out.println("ResouceOrchestration --> Post NetworkAllocateVIMReq Event");
//            SingletonEventBus.getBus().post(allvimreq);
//        } else {
//
//            Fed_NetworkAllocateDBQueryOutcome dbres = new Fed_NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
//                    false, pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
//                    pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
//                    pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), null, null);
//          
//
//            //remove request from pending
//            pendingFedNetAllocReq.remove(allwimrep.getLogicallinkid());
//        }


// Fed_NetworkAllocateDBQueryOutcome dbres = new Fed_NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
//                  allwimrep.isOutcome(), pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
//                   pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
//                    pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), allwimrep.getWimnetlist());
       
  Fed_NetworkAllocateDBQueryOutcome dbres = new Fed_NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
                  allwimrep.isOutcome(), pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), 
          pendingel.getInterdomainLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(),
                    pendingel.getWimNetworkType(),pendingel.isAbstrSrcNfviPopOfLogicalPathIsFed(), allwimrep.getWimnetlist());
 

 
           //remove request from pending
            pendingFedNetAllocReq.remove(allwimrep.getPendingFedNetAllocReqId());
 System.out.println("ResouceOrchestration --> Post Fed_NetworkAllocateDBQueryOutcom Event");
         SingletonEventBus.getBus().post(dbres );

    }
    
    
     @Subscribe
    public void handle_Fed_E2ENetworkTerminateInstance(Fed_E2ENetworkTerminateInstance allinst) {
        System.out.println("ResouceOrchestration --> Handle E2ENetworkTerminateInstance Event");
        //TODO: orchestrate terminate operation

        PendingFedNetworkTerminateRequest pendingReq = new PendingFedNetworkTerminateRequest(allinst.getReqid(), allinst.getServid(),
                allinst.getWimdomlistMap(), allinst.getVimdomlistMap(), allinst.getInterdomainLinksMap(), allinst.getFed_interdomainLinksMap(),
                allinst.getIntraPopLinksMap(), allinst.getWanLinksMap(), allinst.getWimPopListMap(), allinst.getVimPopListMap(),
                allinst.getWimNetworkTypeMap(), allinst.getVimNetworkTypeMap(), allinst.getWanResourceIdListMap(), allinst.getNetServIdList(), allinst.getLocicalPathList());
        pendingFedNetTermReq.put(allinst.getReqid(), pendingReq);
        
        

//        NetworkTerminateVIMReq allvimreq = new NetworkTerminateVIMReq(allinst.getReqid(),
//                allinst.getServid(), allinst.getVimdomlistMap(), allinst.getInterdomainLinksMap(),
//                allinst.getIntraPopLinksMap(), allinst.getVimPopListMap(), allinst.getVimNetworkTypeMap(), allinst.getLocicalPathList());

        

 Fed_NetworkTerminateWIMReq allwimreq = new Fed_NetworkTerminateWIMReq(allinst.getReqid(),
                allinst.getServid(), allinst.getWimdomlistMap(), allinst.getInterdomainLinksMap(), allinst.getWanLinksMap(),
                allinst.getWimPopListMap(), allinst.getWimNetworkTypeMap(), allinst.getWanResourceIdListMap());



        //System.out.println("ResouceOrchestration --> Post NetworkTerminateVIMReq Event");
        //SingletonEventBus.getBus().post(allvimreq);
         System.out.println("ResouceOrchestration --> Post Fed_NetworkTerminateWIMReq Event");
         SingletonEventBus.getBus().post(allwimreq);
    }
    
    
    
//    @Subscribe
//    public void handle_Fed_NetworkAllocateVIMReply(Fed_NetworkAllocateVIMReply allvimrep) {
//        System.out.println("ResouceOrchestration --> Handle handle_NetworkAllocateVIMReply Event");
//        PendingNetworkAllocateRequest pendingel = pendingFedNetAllocReq.get(allvimrep.getLogicallinkid());
//        if (pendingel == null) {
//            System.out.println("ResouceOrchestration --> No pending request found with key= " + allvimrep.getReqid());
//            //TODO: Send API response
//            return;
//        }
//
//        Fed_NetworkAllocateDBQueryOutcome dbres = new Fed_NetworkAllocateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(),
//                allvimrep.isOutcome(), pendingel.getLogicalLinkId(), pendingel.getNetworkRequest(), pendingel.getWimdomlist(), pendingel.getVimdomlist(), pendingel.getInterdomainLinks(),
//                pendingel.getIntraPopLinks(), pendingel.getWanLinks(), pendingel.getWimPopList(), pendingel.getVimPopList(),
//                pendingel.getWimNetworkType(), pendingel.getVimNetworkType(), pendingel.getWimnetlist(), allvimrep.getVimnetlist());
//        
//
//
////remove request from pending
//        pendingFedNetAllocReq.remove(allvimrep.getLogicallinkid());
//        
//         System.out.println("ResouceOrchestration --> Post NetworkAllocateDBQueryOutcome Event");
//        SingletonEventBus.getBus().post(dbres);
//        
//        
//
//    }
 
    
  @Subscribe
    public void handle_Fed_NetworkTerminateWIMReply(Fed_NetworkTerminateWIMReply allwimrep) {
        System.out.println("ResouceOrchestration --> Handle NetworkTerminateWIMReply Event");

        PendingFedNetworkTerminateRequest pendingel = pendingFedNetTermReq.get(allwimrep.getReqid());
        if (pendingel == null) {
            System.out.println("ResouceOrchestration --> No pending request found with key= " + allwimrep.getReqid());
            //TODO: Send API response
            return;
        }

        for (int i = 0; i < pendingel.getLocicalLinkList().size(); i++) {
            boolean fail = false;
            for (int j = 0; (fail == false) && (j < pendingel.getLocicalLinkList().size()); j++) {

                fail = allwimrep.getOutcomeListMap().get(i).get(j);

            }
            if (fail == false) {
                pendingel.getLocicalLinkList().remove(i);
                pendingel.getInterdomainLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getIntraPopLinksMap().remove(i);
                pendingel.getVimNetworkTypeMap().remove(i);
                pendingel.getVimPopListMap().remove(i);
                pendingel.getVimdomlistMap().remove(i);
                pendingel.getWanLinksMap().remove(i);
                pendingel.getWimNetworkTypeMap().remove(i);
                pendingel.getWimPopListMap().remove(i);
                pendingel.getWimdomlistMap().remove(i);
                pendingel.getNetServIdList().remove(i);
            }

        }

   
        
         Fed_NetworkTerminateDBQueryOutcome dboutcome = new Fed_NetworkTerminateDBQueryOutcome(pendingel.getReqid(), pendingel.getServid(), 
         pendingel.getWimdomlistMap(), pendingel.getVimdomlistMap(), pendingel.getInterdomainLinksMap(), pendingel.getFed_interdomainLinksMap(), pendingel.getIntraPopLinksMap(), 
         pendingel.getWanLinksMap(), pendingel.getWimPopListMap(), pendingel.getVimPopListMap(), pendingel.getWimNetworkTypeMap(), 
         pendingel.getVimNetworkTypeMap(),pendingel.getWanResourceIdListMap() , pendingel.getNetServIdList(), pendingel.getLocicalLinkList());
        System.out.println("ResouceOrchestration --> Post NetworkTerminateDBQueryOutcome Event");
        SingletonEventBus.getBus().post(dboutcome);

        
        
        
        //MOVE BELOW EVENT INSIDE DATABASEDRIVER handle_NetworkTerminateDbQueryOutcome
//        E2ENetworkTerminateInstanceOutcome allinstout = new E2ENetworkTerminateInstanceOutcome(allwimrep.getReqid(),
//                allwimrep.getServid(), new VIMAbstractElem(), allwimrep.getOutcome());
//         E2ENetworkTerminateInstanceOutcome allinstout = new E2ENetworkTerminateInstanceOutcome();
//        System.out.println("ResouceOrchestration --> Post E2ENetworkAllocateInstanceOutcome Event");
//        SingletonEventBus.getBus().post(allinstout);
    }

    @Subscribe
    public void handle_ApplyPerfomanceIsolationRequest(ApplyPerformanceIsolationReq applyPerformanceIsolationReq) {
        System.out.println("ResouceOrchestration --> Handle handle_ApplyPerfomanceIsolationRequest Event");

        //Steps to perform according to current workflow:
        //Retrieve local physical topology
        //TODO: Retrieve switch Identifiers from the abstraction engine
        List<String> switchIdentifiers = new ArrayList<>();
        switchIdentifiers.add("device:bmv2:s3");
        switchIdentifiers.add("device:bmv2:s4");
        switchIdentifiers.add("device:bmv2:s5");
        switchIdentifiers.add("device:bmv2:s6");
        switchIdentifiers.add("device:bmv2:s8");
        //Retrieve QoS capabilities
        //TODO
        //Execute Performance Isolation Smart algorithm with input above
        //TODO
        //Get output from the Performance Isolation algorithm and select policy
        //TODO: The policy parameter will be extended to be the QoS policy chosen by the Performance Isolation Algorithm
        String policy = "rate-limiting";

        //Apply QoS Request:
        AssignQosQueueRequest qosQueueRequest = new AssignQosQueueRequest();
        qosQueueRequest.setSliceId(applyPerformanceIsolationReq.getSlareq().getSliceId());
        qosQueueRequest.setSrcEndpoint(applyPerformanceIsolationReq.getSlareq().getSrcEndpoint());
        qosQueueRequest.setDstEndpoint(applyPerformanceIsolationReq.getSlareq().getDstEndpoint());
        qosQueueRequest.setMaxCapacity(applyPerformanceIsolationReq.getSlareq().getMaxCapacity());
        qosQueueRequest.setSwitchIdentifiers(switchIdentifiers);
        qosQueueRequest.setPolicy(policy);

        QosPerformanceIsolationReq qosPerformanceIsolationReq = new QosPerformanceIsolationReq(applyPerformanceIsolationReq.getReqid(),
                applyPerformanceIsolationReq.getServid(), applyPerformanceIsolationReq.getDomid(), qosQueueRequest,
                applyPerformanceIsolationReq.getNfvipopid());

        System.out.println("ResouceOrchestration --> Post QosPerformanceIsolationRequest Event");
        SingletonEventBus.getBus().post(qosPerformanceIsolationReq);

    }
    
    
}
