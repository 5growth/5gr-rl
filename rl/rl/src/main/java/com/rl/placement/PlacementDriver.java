/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.placement;

import com.google.common.eventbus.Subscribe;
import com.rl.DbConnectionPool.DbDomainDatasource;
import com.rl.SingletonEventBus;
import com.rl.abstraction.ResourceSelectionLogic;
import com.rl.events.placement.PAComputeCall;
import com.rl.events.placement.PAComputeRequest;
import com.rl.events.placement.PANetworkCall;
import com.rl.events.placement.PANetworkRequest;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputAbsWanTopo;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputComputeReqs;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputCpuResourceAttributes;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputEdges;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputInterWanLinks;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputMemoryResourceAttributes;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNetwLinkQoS;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPopReqs;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNfviPops;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputNodes;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputQosCons;
import com.rl.extinterface.pa.swagger.client.model.CompRouteInputResourceZoneAttributes;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author efabuba
 */
public class PlacementDriver {
    private HashMap<Long, String> exportermap;
    private HashMap<Long, String> alertmap;
    public PlacementDriver() {
        exportermap = new HashMap();
        alertmap = new HashMap();
    }

    
    
    //////////////////////EVENT-Handlers//////////////////////////////////////
    
    @Subscribe
    public void handle_PANetworkRequest (PANetworkRequest panetreq) {
        System.out.println("PlacementDriver --> Handle PANetworkRequest Event");
 
        ArrayList<CompRouteInputInterWanLinks> interWanLinks =new ArrayList<CompRouteInputInterWanLinks>();

        //Retrieve interwanlinks
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select srcDomId,dstDomId,srcGwId,dstGwId,srcGWIp,"
                    + "dstGwIp,delay,availableBandwidth from interdomainlink");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CompRouteInputInterWanLinks interwanlink = new CompRouteInputInterWanLinks();
                CompRouteInputNetwLinkQoS netwLinkQoS = new CompRouteInputNetwLinkQoS();
                interwanlink.setALinkId(Integer.valueOf(rs.getString("srcGWIp")));
                interwanlink.setZlinkId(Integer.valueOf(rs.getString("dstGwIp")));
                interwanlink.setAPEId(rs.getString("srcGwId"));
                interwanlink.setZPEId(rs.getString("dstGwId"));
                interwanlink.setAWimId(Integer.toString(rs.getInt("srcDomId")));
                interwanlink.setZWimId(Integer.toString(rs.getInt("dstDomId")));
                //Fill Network QoS
                netwLinkQoS.setLinkCost("metric");
                netwLinkQoS.setLinkCostValue(BigDecimal.ONE);
                netwLinkQoS.setLinkAvailBw("availableBandwidth");
                netwLinkQoS.setLinkAvailBwValue(BigDecimal.valueOf(rs.getLong("availableBandwidth")));
                netwLinkQoS.setLinkDelay("delay");
                netwLinkQoS.setLinkDelayValue(BigDecimal.valueOf(rs.getLong("delay")));
                interwanlink.setNetwLinkQoS(netwLinkQoS);
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan srcDomId: " + interwanlink.getAWimId() + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan dstDomId: " + interwanlink.getZWimId() + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan srcGwId: " + interwanlink.getAPEId()+ "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan dstGwId: " + interwanlink.getZPEId() + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan srcGWIp: " + interwanlink.getALinkId() + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan dstGwIp: " + interwanlink.getZlinkId() + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan delay: " + interwanlink.getNetwLinkQoS().getLinkDelayValue() + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "interwan availableBandwidth: " + interwanlink.getNetwLinkQoS().getLinkAvailBwValue() + "");
                interWanLinks.add(interwanlink);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
       
        //Retrieve domain list and put it in the WAN or NFVIPOP list
        ArrayList<Integer> wandomlist = new ArrayList<Integer>();
        ArrayList<Integer> popdomlist = new ArrayList<Integer>();
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId,type from domain");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                int domid = rs.getInt("domId");

                if (type.compareTo("T-WIM") == 0) {
                    wandomlist.add(domid);
                } else {
                    popdomlist.add(domid);
                }
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "domain type: " + type + "");
                System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                        + "domain domId: " + domid + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Retrieve intrawanlinks and intrawanpop
        ArrayList<CompRouteInputAbsWanTopo> absWanTopo = new ArrayList<CompRouteInputAbsWanTopo>();

        for (int i = 0; i < wandomlist.size(); i++) {
            CompRouteInputAbsWanTopo wantopo = new CompRouteInputAbsWanTopo();

            ArrayList<CompRouteInputNodes> wannodes = new ArrayList<CompRouteInputNodes>();
                        
            //get node wan
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select networkConnectivityEndpoint,vimId from nfvipop where domId=?");
                ps.setInt(1, wandomlist.get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    wantopo.setWimId(String.valueOf(rs.getInt("vimId")));
                    String netendps = rs.getString("networkConnectivityEndpoint");
                    //split string to obtains the nodes of wan
                    String[] parts = netendps.split(";");
                    for (int j = 0; j < parts.length; j++) {
                        CompRouteInputNodes node = new CompRouteInputNodes();
                        node.setNodeId(parts[j]);
                        wannodes.add(node);
                    }
                    wantopo.setNodes(wannodes);
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "wannode wimId: " + wantopo.getWimId() + "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "wannode networkConnectivityEndpoint: " + netendps + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            
            
            //get edge wan
            //get nfvipopid and zoneid
            int nfvipopid = -1;
            int zoneid = -1;
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select nfviPopId from nfvipop where domId=?");
                ps.setInt(1, wandomlist.get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    nfvipopid = rs.getInt("nfviPopId");

                    wantopo.setNodes(wannodes);
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "wanedge nfvipopid: " + nfvipopid + "");

                }
                rs.close();
                ps.close();


            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select resourceZoneId from nfvipop where nfviPopId=?");
                ps.setInt(1, nfvipopid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    zoneid = rs.getInt("resourceZoneId");

                    wantopo.setNodes(wannodes);
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "wanedge resourceZoneId: " + zoneid + "");

                }
                rs.close();
                ps.close();


            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //retrieve network edge resources for wim
            ArrayList<CompRouteInputEdges> wanedges = new ArrayList<CompRouteInputEdges>();
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select srcGwId,dstGwId,srcGWIp,"
                        + "dstGwIp,delay,availableCapacity,networkType from networkresources where resourceZoneId=?");
                ps.setInt(1, zoneid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    
                    CompRouteInputEdges edge = new CompRouteInputEdges();
                    CompRouteInputNetwLinkQoS netwLinkQoS = new CompRouteInputNetwLinkQoS();
                    edge.setALinkId(Integer.valueOf(rs.getString("srcGWIp")));
                    edge.setANodeId(rs.getString("srcGwId"));
                    edge.setZLinkId(Integer.valueOf(rs.getString("dstGwIp")));
                    edge.setZNodeId(rs.getString("dstGwId"));
                    edge.setNetworkLayer(rs.getString("networkType"));
                    //Fill Network QoS
                    netwLinkQoS.setLinkCost("metric");
                    netwLinkQoS.setLinkCostValue(BigDecimal.ONE);
                    netwLinkQoS.setLinkAvailBw("availableBandwidth");
                    netwLinkQoS.setLinkAvailBwValue(BigDecimal.valueOf(rs.getLong("availableCapacity")));
                    netwLinkQoS.setLinkDelay("delay");
                    netwLinkQoS.setLinkDelayValue(BigDecimal.valueOf(rs.getLong("delay")));
                    edge.setNetwLinkQoS(netwLinkQoS);

                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "edgewan srcGwId: " + edge.getANodeId() + "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "edgewan dstGwId: " + edge.getZNodeId() + "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "edgewan srcGWIp: " + edge.getALinkId() + "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "edgewan dstGwIp: " + edge.getZLinkId()+ "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "edgewan delay: " + edge.getNetwLinkQoS().getLinkDelayValue() + "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "edgewan availableBandwidth: " + edge.getNetwLinkQoS().getLinkAvailBwValue() + "");
                    wanedges.add(edge);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //add edgelist to wan topoligy
            wantopo.setEdges(wanedges);
            
            absWanTopo.add(wantopo);
        }
        //Retrieve nfvipop info
        ArrayList<CompRouteInputNfviPops> nfviPops = new ArrayList<CompRouteInputNfviPops>();
        for (int i = 0; i < popdomlist.size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select nfviPopId,vimid,networkConnectivityEndpoint from nfvipop where domId=?");
                ps.setInt(1, wandomlist.get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    CompRouteInputNfviPops pop = new CompRouteInputNfviPops();
                    pop.setNetGwIpAddress(rs.getString("networkConnectivityEndpoint"));
                    pop.setNfviPopId(String.valueOf(rs.getInt("nfviPopId")));
                    pop.setVimId(String.valueOf(rs.getInt("vimid")));

                    nfviPops.add(pop);
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "nfvipop networkConnectivityEndpoint: " + pop.getNetGwIpAddress() + "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "nfvipop nfvipopid: " + pop.getNfviPopId()+ "");
                    System.out.println("PlacementDriver.handle_PANetworkRequest ---> "
                            + "nfvipop vimid: " + pop.getVimId() + "");

                }
                rs.close();
                ps.close();


            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        //Set QoS constraint from request 
        CompRouteInputQosCons qosCons = new CompRouteInputQosCons();
        qosCons.setBandwidthCons("availableBandwidth");
        qosCons.setBandwidthConsValue(panetreq.getReqbw());
        qosCons.setDelayCons("delay");
        qosCons.setDelayConsValue(panetreq.getReqlatency());

        PANetworkCall netcall = new PANetworkCall(panetreq.getReqid(), panetreq.getServid(), 
                panetreq.getLogicalpathid(), interWanLinks, absWanTopo, qosCons, nfviPops, 
                panetreq.getNetworkreq(), panetreq.getPEsrc(), panetreq.getPEdst());
        System.out.println("PlacementDriver --> Post PANetworkCall Event");
        SingletonEventBus.getBus().post(netcall);
        
    }
    
    @Subscribe
    public void handle_PAComputeRequest (PAComputeRequest  pacompreq) {
        System.out.println("PlacementDriver --> Handle PAComputeRequest Event");
        
        CompRouteInputNfviPops nfvipops = new CompRouteInputNfviPops();
        //Set vimid and nfvipopid
        nfvipops.setNfviPopId(Long.toString(pacompreq.getNfvipopid()));
        nfvipops.setVimId(Long.toString(pacompreq.getDomid()));
        
        
        //Allocate  Resource Attributes
        ArrayList<CompRouteInputResourceZoneAttributes> resourceZoneAttributes = new ArrayList<CompRouteInputResourceZoneAttributes>(); 
        
        //Retrieve zoneid associated to nfvipop

        ArrayList<Integer> zonelist = new ArrayList<Integer>();
        ArrayList<String> zoneNamelist = new ArrayList<String>();
        ArrayList<String> zoneStatelist = new ArrayList<String>();
        Integer zoneId = null;
        String zoneName = null, zoneState = null;
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select zoneId,zoneName,zoneState from zoneid where nfviPopId=?");
            ps.setLong(1, pacompreq.getNfvipopid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                zoneId = rs.getInt("zoneId");
                zoneName = rs.getString("zoneName");
                zoneState = rs.getString("zoneState");
                System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                        + "zoneId: " + zoneId + "");
                System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                        + "zoneName: " + zoneName + "");
                System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                        + "zoneState: " + zoneState + "");
                zonelist.add(zoneId);
                zoneNamelist.add(zoneName);
                zoneStatelist.add(zoneState);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
         
        for (int i = 0; i < zonelist.size(); i++) {
            CompRouteInputResourceZoneAttributes zoneattr = new CompRouteInputResourceZoneAttributes();
            //set nome state zoneid
            zoneattr.setZoneId(Integer.toString(zonelist.get(i)));
            zoneattr.setZoneName(zoneNamelist.get(i));
            zoneattr.setZoneState(zoneStatelist.get(i));

            //Add element of ResourceAttributes
            CompRouteInputMemoryResourceAttributes memoryResourceAttributes = new CompRouteInputMemoryResourceAttributes();
            CompRouteInputCpuResourceAttributes cpuResourceAttributes = new CompRouteInputCpuResourceAttributes();
            
            //Add cpu attributes
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select availableCapacity,reservedCapacity,totalCapacity,allocatedCapacity from cpuresources where resourceZoneId=?");
                ps.setInt(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cpuResourceAttributes.setAllocatedCapacity(rs.getString("allocatedCapacity"));
                    cpuResourceAttributes.setAvailableCapacity(rs.getString("availableCapacity"));
                    cpuResourceAttributes.setReservedCapacity(rs.getString("reservedCapacity"));
                    cpuResourceAttributes.setTotalCapacity(rs.getString("totalCapacity"));
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "cpu allocatedCapacity: " + cpuResourceAttributes.getAllocatedCapacity() + "");
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "cpu availableCapacity: " + cpuResourceAttributes.getAvailableCapacity() + "");
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "cpu reservedCapacity: " + cpuResourceAttributes.getReservedCapacity() + "");
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "cpu totalCapacity: " + cpuResourceAttributes.getTotalCapacity() + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            
            //Add memory attributes
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select availableCapacity,reservedCapacity,totalCapacity,allocatedCapacity from memoryresources where resourceZoneId=?");
                ps.setLong(1, pacompreq.getNfvipopid());
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    memoryResourceAttributes.setAllocatedCapacity(rs.getString("allocatedCapacity"));
                    memoryResourceAttributes.setAvailableCapacity(rs.getString("availableCapacity"));
                    memoryResourceAttributes.setReservedCapacity(rs.getString("reservedCapacity"));
                    memoryResourceAttributes.setTotalCapacity(rs.getString("totalCapacity"));
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "mem allocatedCapacity: " + memoryResourceAttributes.getAllocatedCapacity() + "");
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "mem availableCapacity: " + memoryResourceAttributes.getAvailableCapacity() + "");
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "mem reservedCapacity: " + memoryResourceAttributes.getReservedCapacity() + "");
                    System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                            + "mem totalCapacity: " + memoryResourceAttributes.getTotalCapacity() + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            // N.B.: storage not used 
                       
            
            //Add element of ResourceAttributes
            zoneattr.setCpuResourceAttributes(cpuResourceAttributes);
            zoneattr.setMemoryResourceAttributes(memoryResourceAttributes);
            
            //Add in resource attributes list
            resourceZoneAttributes.add(zoneattr);
           
        } 
            
        //Add ResourceAttributes element in nfvipops
        nfvipops.setResourceZoneAttributes(resourceZoneAttributes);
        
        //Prepare popreq
        CompRouteInputNfviPopReqs popreq = new CompRouteInputNfviPopReqs();
        popreq.setNfviPopId(Long.toString(pacompreq.getNfvipopid()));
        popreq.setVimId(Long.toString(pacompreq.getDomid()));
        CompRouteInputComputeReqs computeReqs = new CompRouteInputComputeReqs();
        
        computeReqs.setCpuCons("numVirtualCpu");
        //retrieve cpu value and mem value from flavorid
        int flavourid = Integer.valueOf(pacompreq.getCompel().getAllocateRequest().getRequest().getComputeFlavourId());
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select numVirtualCpu from virtualcpu where computeFlavourId=?");
            ps.setLong(1, flavourid);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                computeReqs.cpuConsValue(BigDecimal.valueOf(Long.valueOf(rs.getString("numVirtualCpu"))));
                
                System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                        + "numVirtualCpu: " +  computeReqs.getCpuConsValue() + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //retrieve mem value from flavorid
        computeReqs.setMemoryCons("virtualMemSize");
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select virtualMemSize from virtualmemorydata where computeFlavourId=?");
            ps.setLong(1, flavourid);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                computeReqs.setDelayConsValue(BigDecimal.valueOf(Long.valueOf(rs.getString("virtualMemSize"))));
                
                System.out.println("PlacementDriver.handle_PAComputeRequest ---> "
                        + "virtualMemSize: " +  computeReqs.getDelayConsValue() + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        //Set compute rquest
        popreq.setComputeReqs(computeReqs);
        PAComputeCall compcall = new PAComputeCall(pacompreq.getReqid(), pacompreq.getServid(), 
                pacompreq.getDomid(), pacompreq.getNfvipopid(), nfvipops, popreq);
        System.out.println("PlacementDriver --> Post PAComputeCall Event");
        SingletonEventBus.getBus().post(compcall);
        
    }
    
}
