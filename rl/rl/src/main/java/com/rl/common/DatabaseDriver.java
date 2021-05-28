/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common;

import java.util.*;
import com.google.common.eventbus.Subscribe;
import com.rl.events.abstraction.Creation.Parsedomainlist;
import com.rl.events.abstraction.Creation.DomainSubscriber;
import com.rl.common.objects.DomainElem;
import com.rl.SingletonEventBus;
import com.rl.events.abstraction.Advertisement.AdvertiseE2EAbstractionReply;
import com.rl.events.abstraction.Advertisement.AdvertiseE2EAbstractionRequest;
import com.rl.events.abstraction.Advertisement.ComputeCapacityReply;
import com.rl.events.abstraction.Advertisement.ComputeCapacityRequest;
import com.rl.events.abstraction.Advertisement.ComputeResourceInformationReply;
import com.rl.events.abstraction.Advertisement.ComputeResourceInformationRequest;
import com.rl.events.abstraction.Advertisement.ComputeResourceZoneReply;
import com.rl.events.abstraction.Advertisement.ComputeResourceZoneRequest;
import com.rl.events.abstraction.Advertisement.NetworkCapacityReply;
import com.rl.events.abstraction.Advertisement.NetworkCapacityRequest;
import com.rl.events.abstraction.Advertisement.NetworkResourceInformationReply;
import com.rl.events.abstraction.Advertisement.NetworkResourceInformationRequest;
import com.rl.events.abstraction.Advertisement.NfviPopAbstractionReply;
import com.rl.events.abstraction.Advertisement.NfviPopAbstractionRequest;
import com.rl.events.abstraction.Creation.ComputeE2EAbstractionReply;
import com.rl.events.abstraction.Creation.ComputeE2EAbstractionRequest;
import com.rl.events.abstraction.Creation.MECResAbstractionEvent;
import com.rl.events.abstraction.Creation.RadioResAbstractionEvent;
import com.rl.events.abstraction.Creation.StartServer;
import com.rl.events.abstraction.Creation.VIMResAbstractionEvent;
import com.rl.events.abstraction.Creation.WIMResAbstractionEvent;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQuery;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQuery;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQuery;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQuery;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQueryReply;
import com.rl.DbConnectionPool.DbAbstractionDatasource;
import com.rl.DbConnectionPool.DbAppDDatasource;
import com.rl.DbConnectionPool.DbDomainDatasource;
import com.rl.DbConnectionPool.DbFederationDatasource;
import com.rl.DbConnectionPool.DbServiceDatasource;
import com.rl.common.objects.NetworkEndpoints;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDIdReply;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDIdRequest;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDReply;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDRequest;
import com.rl.events.abstraction.Creation.DomainNumbers;
import com.rl.events.abstraction.Creation.ParseFederatedPopList;
import com.rl.events.federation.Advertisement.AdvertiseE2EFederationReply;
import com.rl.events.federation.Advertisement.AdvertiseE2EFederationRequest;
import com.rl.events.monitoring.MonConfig;
import com.rl.events.placement.PAComputeConfig;
import com.rl.events.placement.PANetworkConfig;
import com.rl.events.placement.PAComputeStatus;
import com.rl.events.placement.PANetworkStatus;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateMECReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateMECReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateDBReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateRep;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateVIMReply;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECQuery;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReq;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateDBQuery;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateDbReply;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateDbRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMRequest;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateDBQuery;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateDbReply;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateDbRequest;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMRequest;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateDBQuery;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateDBQueryOutcome;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateDBQueryReply;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateDBQuery;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateDBQueryOutcome;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateDBQueryReply;
import com.rl.extinterface.nbi.swagger.model.AddressData;
import com.rl.extinterface.nbi.swagger.model.AddressData.AddressTypeEnum;
import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequestAffinityOrAntiAffinityConstraints;
import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequestInterfaceData;
import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequestUserData;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResultNetworkDataNetworkQoS;
import com.rl.extinterface.nbi.swagger.model.AppD;
import com.rl.extinterface.nbi.swagger.model.AppExternalCpd;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.CategoryRef;
import com.rl.extinterface.nbi.swagger.model.ChangeAppInstanceStateOpConfig;
import com.rl.extinterface.nbi.swagger.model.CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning;
import com.rl.extinterface.nbi.swagger.model.DNSRuleDescriptor;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2005;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.InterfaceDescriptor;
import com.rl.extinterface.nbi.swagger.model.LatencyDescriptor;
import com.rl.extinterface.nbi.swagger.model.LocationInfo;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkInterNfviPops;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkInterNfviPopsInner;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkInterNfviPopsInnerLogicalLinks;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS;
import com.rl.extinterface.nbi.swagger.model.MECRegionInfo;
import com.rl.extinterface.nbi.swagger.model.MECRegionInfoMecRegionInfo;
import com.rl.extinterface.nbi.swagger.model.MECTrafficServiceCreationRequest;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.NfviPops;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInner;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributes;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesCovrageLocationInfo;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesCpuResourceAttributes;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesMemoryResourceAttributes;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesRadioCoverageAreas;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesResourceZoneAttributes;
import com.rl.extinterface.nbi.swagger.model.NfviPopsInnerNfviPopAttributesStorageResourceAttributes;
import com.rl.extinterface.nbi.swagger.model.PNFInfo;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import com.rl.extinterface.nbi.swagger.model.PNFlist;
import com.rl.extinterface.nbi.swagger.model.PNFlistInner;
import com.rl.extinterface.nbi.swagger.model.RequestAdditionalCapabilityData;
import com.rl.extinterface.nbi.swagger.model.ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface;
import com.rl.extinterface.nbi.swagger.model.ResourceZone;
import com.rl.extinterface.nbi.swagger.model.ServiceDependency;
import com.rl.extinterface.nbi.swagger.model.SwImageDescriptor;
import com.rl.extinterface.nbi.swagger.model.TerminateAppInstanceOpConfig;
import com.rl.extinterface.nbi.swagger.model.TrafficFilter;
import com.rl.extinterface.nbi.swagger.model.TrafficRuleDescriptor;
import com.rl.extinterface.nbi.swagger.model.TrafficRuleDescriptor.ActionEnum;
import com.rl.extinterface.nbi.swagger.model.TransportDependency;
import com.rl.extinterface.nbi.swagger.model.TransportDescriptor;
import com.rl.extinterface.nbi.swagger.model.TunnelInfo;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeDescription;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformation;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformationVirtualCPU;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeResourceInformationVirtualMemory;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeVirtualCpu;
import com.rl.extinterface.nbi.swagger.model.VirtualComputeVirtualMemory;
import com.rl.extinterface.nbi.swagger.model.VirtualCpuData;
import com.rl.extinterface.nbi.swagger.model.VirtualCpuPinningData;
import com.rl.extinterface.nbi.swagger.model.VirtualMemoryData;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkInterfaceRequirements;
import com.rl.extinterface.nbi.swagger.model.VirtualNetworkResourceInformation;
import com.rl.extinterface.nbi.swagger.model.VirtualStorageDescriptor;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class DatabaseDriver {

    private ArrayList<DomainElem> domains;
    private int domnum; //number of registered domain
    //private int currdomnum; //number of domain that reply  
    private long reqval;

    public DatabaseDriver() {
        domnum = 0;
        //currdomnum = 0;
        reqval = 0;
        domains = new ArrayList();
    }

    //////////////////Start Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ParseDomainList(Parsedomainlist domlist) {
        System.out.println("DatabaseDriver ---> Parse domlist from xml file");
        //Parse list of domain from xml file
        XMLDomainParser xmldom = new XMLDomainParser(domlist.getFilename());
        long portdom = 0;
        DomainElem el;
        DomainSubscriber domsub = new DomainSubscriber();
        while (portdom != -1) {
            el = xmldom.getDomainElem();
            System.out.println("DomainElem fields: type= " + el.getType()
                    + " ip= " + el.getIp() + " port= " + el.getPort() + " name= "
                    + el.getName() + " id= " + el.getId());

// Insert domainElem into Database
            if (el.getPort() != -1) {
                try {

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                    //ps.setInt(1, 1);
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO domain (domId,name,type,ip,port,mecAssociatedDomainID) VALUES(?,?,?,?,?,?)");
                    ps.setLong(1, el.getId());
                    ps.setString(2, el.getName());
                    ps.setString(3, el.getType());
                    ps.setString(4, el.getIp());
                    ps.setLong(5, el.getPort());
                    ps.setLong(6, el.getMecid());

                    ps.executeUpdate();
                    System.out.println("IP " + el.getIp() + "");

                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("DatabaseDriver ---> db exception: " + ex.getMessage());
                }

            }

            if (el.getPort() != -1) {
                domsub.setDomainElem(el);
                domains.add(el);
                domnum++;
            }

            portdom = el.getPort();
        }

        //Send Local PA algorithm configuration to local PA driver 
        PAComputeConfig comp_pa_config = new PAComputeConfig(xmldom.getPa_comp());
        PANetworkConfig net_pa_config = new PANetworkConfig(xmldom.getPa_net());
        System.out.println("DatabaseDriver ---> Post PAComputeConfig event ");
        SingletonEventBus.getBus().post(comp_pa_config);
        System.out.println("DatabaseDriver ---> Post PANetworkConfig event ");
        SingletonEventBus.getBus().post(net_pa_config);
        //Send Local PA status to Resource Selection Logic
        PAComputeStatus pa_comp_status = new PAComputeStatus(xmldom.getPa_comp().getStatus());
        PANetworkStatus pa_net_status = new PANetworkStatus(xmldom.getPa_net().getStatus());
        System.out.println("DatabaseDriver ---> Post PAComputeStatus event ");
        SingletonEventBus.getBus().post(pa_comp_status);
        System.out.println("DatabaseDriver ---> Post PANetworkStatus event ");
        SingletonEventBus.getBus().post(pa_net_status);

        //Send Monitoring Config to monitorin IF
        MonConfig mon_config = new MonConfig(xmldom.getMon());
        System.out.println("DatabaseDriver ---> Post MonConfig event ");
        SingletonEventBus.getBus().post(mon_config);

        //post Domain Numbers to E2EAbstraction Logic
        DomainNumbers domnumev = new DomainNumbers(domnum);
        System.out.println("DatabaseDriver ---> Post DomainNumber event ");
        SingletonEventBus.getBus().post(domnumev);
        //post DomainSubscriberEvent to RO
        System.out.println("DatabaseDriver ---> Post DomainSubscriber event ");
        //currdomnum = 0;
        SingletonEventBus.getBus().post(domsub);
    }

    @Subscribe
    public void handle_ParseFederatedPopList(ParseFederatedPopList domlist) {
        System.out.println("DatabaseDriver ---> Parse federated Pops list from xml file");
        //Parse list of domain from xml file
        Federation_XMLDomainParser xmldom = new Federation_XMLDomainParser(domlist.getFilename());
        String vimId = "0";
        NfviPop el;

        while (vimId != null) {
            el = xmldom.getPops();
            vimId = el.getVimId();
            System.out.println("DomainElem fields: vimId= " + el.getVimId()
                    + "federated nfviPopId= " + el.getNfviPopId() + " endpoints= " + el.getNetworkConnectivityEndpoint());

// Insert domainElem into Database
            if (el.getVimId() != null) {
                try {

                    java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                    //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                    //ps.setInt(1, 1);
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO nfvipop (nfviPopId, federatedVimId,networkConnectivityEndpoint) VALUES(?,?,?)");
                    ps.setString(1, el.getNfviPopId());

                    ps.setString(2, el.getVimId());
                    ps.setString(3, el.getNetworkConnectivityEndpoint());

                    ps.executeUpdate();
                    System.out.println("Federated Vim Id " + el.getVimId() + "");

                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("DatabaseDriver ---> db exception: " + ex.getMessage());
                }

            }

        }

    }

    @Subscribe
    public void handle_WIMResAbstractionEvent(WIMResAbstractionEvent wimev) {
        System.out.println("DatabaseDriver ---> Handle WIMResAbstractionEvent");
        //Store information in DB

        //INSERT records into NfviPoP table
        //START - Loop on nfviPoP list
        for (int i = 0; i < wimev.getPopList().size(); i++) {

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO nfvipop ("
                        + "nfviPopId, "
                        + "vimId, "
                        + "geographicalLocationInfo,"
                        + "networkConnectivityEndpoint,"
                        + "domId) "
                        + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, wimev.getPopList().get(i).getNfviPopId());
                ps.setString(2, wimev.getPopList().get(i).getVimId());
                ps.setString(3, wimev.getPopList().get(i).getGeographicalLocationInfo());
                ps.setString(4, wimev.getPopList().get(i).getNetworkConnectivityEndpoint());
                ps.setLong(5, wimev.getId());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_WIMResAbstractionEvent ---> nfvipop instance inserted into DB");
//            ResultSet rs = ps.getGeneratedKeys();
//
//            if (rs != null && rs.next()) {
//                networkServId = rs.getLong(1);
//                System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
//            }
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } //END - INSERT on NfviPop table

        //INSERT records into Zone table
        for (int i = 0; i < wimev.getZoneList().size(); i++) {

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO zoneid"
                        + "("
                        + "zoneId,"
                        + "zoneName,"
                        + "zoneState,"
                        + "zoneProperty,"
                        + "metadata)"
                        + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

//             PreparedStatement ps = conn.prepareStatement("INSERT INTO zoneid"
//                     + "(resourceZoneId,"
//                     + "zoneId,"
//                     + "zoneName,"
//                     + "zoneState,"
//                     + "zoneProperty)"
//                     + "VALUES(?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, wimev.getZoneList().get(i).getZoneId());
                ps.setString(2, wimev.getZoneList().get(i).getZoneName());
                ps.setString(3, wimev.getZoneList().get(i).getZoneState());
                ps.setString(4, wimev.getZoneList().get(i).getZoneProperty());
                ps.setString(5, null);
                //Metadata attribute is a List of struct so we need a metadata table to handle this info  
                //ps.setString(6, wimev.getZoneList().get(i).getMetadata());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_WIMResAbstractionEvent ---> zoneid instance inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

        //INSERT records into NetworkResources table
        for (int i = 0; i < wimev.getNetworkMapList().size(); i++) {
            long resourceZoneId = -1;
            //UPDATE ZONE table 
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT resourceZoneId, nfviPopId FROM  zoneid WHERE zoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, wimev.getNetworkMapList().get(i).getZoneId());

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    //availableBandwidth = rs.getLong("availableCapacity");     
                    resourceZoneId = rs.getInt("resourceZoneId");
                    rs.updateLong("nfviPopId", Long.valueOf(wimev.getNetworkMapList().get(i).getPopId()));
                    rs.updateRow();
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO networkresources\n"
                        + "(networkResId,"
                        + "srcGwId,\n"
                        + "dstGwId,\n"
                        + "srcGWIp,\n"
                        + "dstGwIp,\n"
                        + "delay,\n"
                        + "availableCapacity,\n"
                        + "reservedCapacity,\n"
                        + "totalCapacity,\n"
                        + "allocatedCapacity,\n"
                        + "networkResourceTypeId,\n"
                        + "networkType,\n"
                        + "bandwidth,\n"
                        + "resourceZoneId)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, wimev.getNetworkResList().get(i).getNetworkElem().getNetworkResourceTypeId());
                ps.setString(2, wimev.getNetworkResList().get(i).getLinkinfo().getSrcGwIpAddress());
                ps.setString(3, wimev.getNetworkResList().get(i).getLinkinfo().getDstGwIpAddress());
                ps.setString(4, String.valueOf(wimev.getNetworkResList().get(i).getLinkinfo().getLocalLinkId()));
                ps.setString(5, String.valueOf(wimev.getNetworkResList().get(i).getLinkinfo().getRemoteLinkId()));
                ps.setString(6, wimev.getNetworkResList().get(i).getNetworkElem().getNetworkQoS().get(0).getQosValue());
                ps.setString(7, wimev.getNetworkResList().get(i).getCapacityElem().getAvailableCapacity());
                ps.setString(8, wimev.getNetworkResList().get(i).getCapacityElem().getReservedCapacity());
                ps.setString(9, wimev.getNetworkResList().get(i).getCapacityElem().getTotalCapacity());
                ps.setString(10, wimev.getNetworkResList().get(i).getCapacityElem().getAllocatedCapacity());
                ps.setString(11, wimev.getNetworkResList().get(i).getNetworkElem().getNetworkResourceTypeId());
                ps.setString(12, wimev.getNetworkResList().get(i).getNetworkElem().getNetworkType());
                ps.setBigDecimal(13, wimev.getNetworkResList().get(i).getNetworkElem().getBandwidth());
                ps.setLong(14, resourceZoneId);

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_WIMResAbstractionEvent ---> NetworkService instance inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);

                System.out.println("" + ex.getMessage() + "");
            }

        }
        ComputeE2EAbstractionRequest e2ecomputeev = new ComputeE2EAbstractionRequest();
        System.out.println("DatabaseDriver ---> Post ComputeE2EAbstractionRequest Event");
        SingletonEventBus.getBus().post(e2ecomputeev);

    }

    @Subscribe
    public void handle_VIMResAbstractionEvent(VIMResAbstractionEvent vimev) {
        System.out.println("DatabaseDriver ---> Handle VIMResAbstractionEvent");

        //Store information in DB
        //INSERT records into NfviPoP table
        //START - Loop on nfviPoP list
        for (int i = 0; i < vimev.getPopList().size(); i++) {

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO nfvipop ("
                        + "nfviPopId, "
                        + "vimId, "
                        + "geographicalLocationInfo,"
                        + "networkConnectivityEndpoint,"
                        + "domId) "
                        + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, vimev.getPopList().get(i).getNfviPopId());
                ps.setString(2, vimev.getPopList().get(i).getVimId());
                ps.setString(3, vimev.getPopList().get(i).getGeographicalLocationInfo());
                ps.setString(4, vimev.getPopList().get(i).getNetworkConnectivityEndpoint());
                ps.setLong(5, vimev.getId());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_VIMResAbstractionEvent ---> NfviPoP instance inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } //END - INSERT on NfviPop table

        //INSERT records into Zone table
        for (int i = 0; i < vimev.getZoneList().size(); i++) {

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO zoneid"
                        + "("
                        + "zoneId,"
                        + "zoneName,"
                        + "zoneState,"
                        + "zoneProperty,"
                        + "metadata)"
                        + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, vimev.getZoneList().get(i).getZoneId());
                ps.setString(2, vimev.getZoneList().get(i).getZoneName());
                ps.setString(3, vimev.getZoneList().get(i).getZoneState());
                ps.setString(4, vimev.getZoneList().get(i).getZoneProperty());
                ps.setString(5, null);
                //Metadata attribute is a List of struct so we need a metadata table to handle this info  
                //ps.setString(6, wimev.getZoneList().get(i).getMetadata());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_handle_VIMResAbstractionEvent ---> Zone instance inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
//UNCOMMENT if MTP has to manage network reosurces of VIM 
//        //INSERT records into NetworkResources table
//        for (int i = 0; i < vimev.getNetworkResList().size(); i++) {
//
//            try {
//
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//
//                PreparedStatement ps = conn.prepareStatement("INSERT INTO networkresources"
//                        + "(networkResId,"
//                        + "srcGwId,"
//                        + "dstGwId,"
//                        + "srcGWIp,"
//                        + "dstGwIp,"
//                        + "delay,"
//                        + "availableCapacity,"
//                        + "reservedCapacity,"
//                        + "totalCapacity,"
//                        + "allocatedCapacity,"
//                        + "networkResourceTypeId,"
//                        + "networkType,"
//                        + "bandwidth)"
//                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                
//                String ss= vimev.getNetworkResList().get(i).getNetworkElem().getResInfo().getSrcGwId();
//                 System.out.println("TEST ss:"+ss+"");
//                
//                ps.setString(1, vimev.getNetworkResList().get(i).getNetworkElem().getNetworkResourceTypeId());
//                ps.setString(2, vimev.getNetworkResList().get(i).getNetworkElem().getResInfo().getSrcGwId());
//                
//
//                //ps.setString(2, "1.1.1.1");
//                ps.setString(3, vimev.getNetworkResList().get(i).getNetworkElem().getResInfo().getDstGwId());
//                //ps.setString(3, "1.1.1.1");
//                ps.setString(4, vimev.getNetworkResList().get(i).getNetworkElem().getResInfo().getSrcGWIp());
//                //ps.setString(4, "1.1.1.1");
//               ps.setString(5, vimev.getNetworkResList().get(i).getNetworkElem().getResInfo().getDstGwIp());
//               //ps.setString(5, "1.1.1.1");
//                ps.setString(6, vimev.getNetworkResList().get(i).getNetworkElem().getNetworkQoS().getQosValue());
//                ps.setString(7, vimev.getNetworkResList().get(i).getCapacityElem().getAvailableCapacity());
//                ps.setString(8, vimev.getNetworkResList().get(i).getCapacityElem().getReservedCapacity());
//                ps.setString(9, vimev.getNetworkResList().get(i).getCapacityElem().getTotalCapacity());
//                ps.setString(10, vimev.getNetworkResList().get(i).getCapacityElem().getAllocatedCapacity());
//                ps.setString(11, vimev.getNetworkResList().get(i).getNetworkElem().getNetworkResourceTypeId());
//                ps.setString(12, vimev.getNetworkResList().get(i).getNetworkElem().getNetworkType());
//                ps.setBigDecimal(13, vimev.getNetworkResList().get(i).getNetworkElem().getBandwidth());
//
//                ps.executeUpdate();
//                System.out.println("DatabaseDriver.handle_VIMResAbstractionEvent ---> NetworkService instance inserted into DB");
//
//                ps.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, null, ex);
//                  System.out.println("TEST TEST" + ex.getMessage()+ "");
//            }
//
//        }
//
//        //UPDATE ZONE and NETWORK_RESOURCES tables
//        for (int i = 0; i < vimev.getNetworkMapList().size(); i++) {
//            Long popId = vimev.getNetworkMapList().get(i).getPopId();
//            Long zoneId = vimev.getNetworkMapList().get(i).getZoneId();
//            Long resourceId = vimev.getNetworkMapList().get(i).getResourceId();
//
//            //UPDATE ZONE table 
//            try {
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement("SELECT resourceZoneId, nfviPopId FROM  zoneid WHERE resourceZoneId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//                ps.setLong(1, zoneId);
//
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    //availableBandwidth = rs.getLong("availableCapacity");     
//                    rs.updateLong("nfviPopId", popId);
//                    rs.updateRow();
//                }
//
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//
//            //UPDATE NETWORK_RESOURCES table 
//            try {
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement("SELECT networkResId, resourceZoneId FROM networkresources WHERE networkResId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//                ps.setLong(1, resourceId);
//
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    //availableBandwidth = rs.getLong("availableCapacity");     
//                    rs.updateLong("resourceZoneId", zoneId);
//                    rs.updateRow();
//                }
//
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//
//        }

        //INSERT records into MemoryResources and CpuResources tables
        for (int i = 0; i < vimev.getComputeResList().size(); i++) {
            long resourceZoneId = -1;
            //UPDATE ZONE table 
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT resourceZoneId, nfviPopId FROM  zoneid WHERE zoneId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, vimev.getCompMapList().get(i).getZoneId());

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    //availableBandwidth = rs.getLong("availableCapacity");     
                    resourceZoneId = rs.getInt("resourceZoneId");
                    rs.updateLong("nfviPopId", Long.valueOf(vimev.getCompMapList().get(i).getPopId()));
                    rs.updateRow();
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            if (vimev.getComputeResList().get(i).getComputeElem().getVirtualMemory() != null) {

                //INSERT record in into MemoryResources table
                try {

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO memoryresources"
                            + "(availableCapacity,"
                            + "reservedCapacity,"
                            + "totalCapacity,"
                            + "allocatedCapacity,"
                            + "virtualMemSize,"
                            + "virtualMemOversubscriptionPolicy,"
                            + "numaEnabled,"
                            + "computeResourceTypeId,"
                            + "accelerationCapability,"
                            + "resourceZoneId)"
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, vimev.getComputeResList().get(i).getCapacityElem().getAvailableCapacity());
                    ps.setString(2, vimev.getComputeResList().get(i).getCapacityElem().getReservedCapacity());
                    ps.setString(3, vimev.getComputeResList().get(i).getCapacityElem().getTotalCapacity());
                    ps.setString(4, vimev.getComputeResList().get(i).getCapacityElem().getAllocatedCapacity());
                    ps.setBigDecimal(5, vimev.getComputeResList().get(i).getComputeElem().getVirtualMemory().getVirtualMemSize());
                    ps.setString(6, vimev.getComputeResList().get(i).getComputeElem().getVirtualMemory().getVirtualMemOversubscriptionPolicy());
                    ps.setBoolean(7, vimev.getComputeResList().get(i).getComputeElem().getVirtualMemory().isNumaSupported());
                    ps.setString(8, vimev.getComputeResList().get(i).getComputeElem().getComputeResourceTypeId());
                    ps.setString(9, vimev.getComputeResList().get(i).getComputeElem().getAccelerationCapability());
                    ps.setLong(10, resourceZoneId);

                    ps.executeUpdate();
                    System.out.println("DatabaseDriver.handle_VIMResAbstractionEvent ---> memoryresources instance inserted into DB");

                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("" + ex.getMessage() + "");
                }

            } else {
                //INSERT record in into CpuResources table
                try {

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO cpuresources"
                            + "(availableCapacity,"
                            + "reservedCapacity,"
                            + "totalCapacity,"
                            + "allocatedCapacity,"
                            + "cpuArchitecture,"
                            + "numVirtualCpu,"
                            + "cpuClock,"
                            + "virtualCpuOversubscriptionPolicy,"
                            + "virtualCpuPinningSupported,"
                            + "computeResourceTypeId,"
                            + "accelerationCapability,"
                            + "resourceZoneId)"
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, vimev.getComputeResList().get(i).getCapacityElem().getAvailableCapacity());
                    ps.setString(2, vimev.getComputeResList().get(i).getCapacityElem().getReservedCapacity());
                    ps.setString(3, vimev.getComputeResList().get(i).getCapacityElem().getTotalCapacity());
                    ps.setString(4, vimev.getComputeResList().get(i).getCapacityElem().getAllocatedCapacity());
                    ps.setString(5, vimev.getComputeResList().get(i).getComputeElem().getVirtualCPU().getCpuArchitecture());
                    ps.setBigDecimal(6, vimev.getComputeResList().get(i).getComputeElem().getVirtualCPU().getNumVirtualCpu());
                    ps.setBigDecimal(7, vimev.getComputeResList().get(i).getComputeElem().getVirtualCPU().getCpuClock());
                    ps.setString(8, vimev.getComputeResList().get(i).getComputeElem().getVirtualCPU().getVirtualCpuOversubscriptionPolicy());
                    //ps.setBoolean(9, vimev.getComputeResList().get(i).getComputeElem().getVirtualCPU().isVirtualCpuPinningSupported());
                    ps.setBoolean(9, true);
                    ps.setString(10, vimev.getComputeResList().get(i).getComputeElem().getComputeResourceTypeId());
                    ps.setString(11, vimev.getComputeResList().get(i).getComputeElem().getAccelerationCapability());
                    ps.setLong(12, resourceZoneId);

                    ps.executeUpdate();
                    System.out.println("DatabaseDriver.handle_VIMResAbstractionEvent ---> cpuresources instance inserted into DB");

                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        ComputeE2EAbstractionRequest e2ecomputeev = new ComputeE2EAbstractionRequest();
        System.out.println("DatabaseDriver ---> Post ComputeE2EAbstractionRequest Event");
        SingletonEventBus.getBus().post(e2ecomputeev);
    }

    @Subscribe
    public void handle_MECResAbstractionEvent(MECResAbstractionEvent mecev) {
        System.out.println("DatabaseDriver ---> Handle MECResAbstractionEvent");

        for (int i = 0; i < mecev.getMecregionlist().size(); i++) {

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO mec_region_info ("
                        + "regionId,"
                        + "latitude,"
                        + "longitude,"
                        + "altitude,"
                        + "range_,"
                        + "domId) "
                        + "VALUES(?,?,?,?,?,?)");

                ps.setInt(1, Integer.valueOf(mecev.getMecregionlist().get(i).getMecRegionInfo().getRegionId()));
                ps.setString(2, String.valueOf(mecev.getMecregionlist().get(i).getMecRegionInfo().getLocationInfo().getLatitude()));
                ps.setString(3, String.valueOf(mecev.getMecregionlist().get(i).getMecRegionInfo().getLocationInfo().getLongitude()));
                ps.setString(4, String.valueOf(mecev.getMecregionlist().get(i).getMecRegionInfo().getLocationInfo().getAltitude()));
                ps.setString(5, String.valueOf(mecev.getMecregionlist().get(i).getMecRegionInfo().getLocationInfo().getRange()));
                ps.setLong(6, mecev.getId());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_MECResAbstractionEvent ---> mec region info instance inserted into DB");
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } //END - INSERT on MEC Region Info

        ComputeE2EAbstractionRequest e2ecomputeev = new ComputeE2EAbstractionRequest();
        System.out.println("DatabaseDriver ---> Post ComputeE2EAbstractionRequest Event");
        SingletonEventBus.getBus().post(e2ecomputeev);
    }

    @Subscribe
    public void handle_RadioResAbstractionEvent(RadioResAbstractionEvent radioev) {

        //Insert and fill DB with Radio Specific Info
        //Store information in DB
        //INSERT records into NfviPoP table
        //START - Loop on nfviPoP list
        for (int i = 0; i < radioev.getPoplist().size(); i++) {

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO nfvipop ("
                        + "nfviPopId, "
                        + "vimId, "
                        + "geographicalLocationInfo,"
                        + "networkConnectivityEndpoint,"
                        + "domId) "
                        + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, radioev.getPoplist().get(i).getNfviPopId());
                ps.setString(2, radioev.getPoplist().get(i).getVimId());
                ps.setString(3, radioev.getPoplist().get(i).getGeographicalLocationInfo());
                ps.setString(4, radioev.getPoplist().get(i).getNetworkConnectivityEndpoint());
                ps.setLong(5, radioev.getId());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> NfviPoP instance inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } //END - INSERT on NfviPop table

        //INSERT records into Zone table
        for (int i = 0; i < radioev.getZonelist().size(); i++) {

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO zoneid"
                        + "("
                        + "zoneId,"
                        + "zoneName,"
                        + "zoneState,"
                        + "zoneProperty,"
                        + "metadata)"
                        + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, radioev.getZonelist().get(i).getZoneId());
                ps.setString(2, radioev.getZonelist().get(i).getZoneName());
                ps.setString(3, radioev.getZonelist().get(i).getZoneState());
                ps.setString(4, radioev.getZonelist().get(i).getZoneProperty());
                ps.setString(5, null);
                //Metadata attribute is a List of struct so we need a metadata table to handle this info  
                //ps.setString(6, wimev.getZoneList().get(i).getMetadata());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> Zone instance inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        //INSERT records into MemoryResources and CpuResources tables
        for (int i = 0; i < radioev.getResinfolist().size(); i++) {
            long resourceZoneId = -1;
            //UPDATE ZONE table 
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT resourceZoneId, nfviPopId FROM  zoneid WHERE zoneId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, radioev.getCompresmap().get(i).getZoneId());

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    //availableBandwidth = rs.getLong("availableCapacity");     
                    resourceZoneId = rs.getInt("resourceZoneId");
                    rs.updateLong("nfviPopId", Long.valueOf(radioev.getCompresmap().get(i).getPopId()));
                    rs.updateRow();
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            if (radioev.getResinfolist().get(i).getComputeElem().getVirtualMemory() != null) {

                //INSERT record in into MemoryResources table
                try {

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO memoryresources"
                            + "(availableCapacity,"
                            + "reservedCapacity,"
                            + "totalCapacity,"
                            + "allocatedCapacity,"
                            + "virtualMemSize,"
                            + "virtualMemOversubscriptionPolicy,"
                            + "numaEnabled,"
                            + "computeResourceTypeId,"
                            + "accelerationCapability,"
                            + "resourceZoneId)"
                            + "VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, radioev.getResinfolist().get(i).getCapacityElem().getAvailableCapacity());
                    ps.setString(2, radioev.getResinfolist().get(i).getCapacityElem().getReservedCapacity());
                    ps.setString(3, radioev.getResinfolist().get(i).getCapacityElem().getTotalCapacity());
                    ps.setString(4, radioev.getResinfolist().get(i).getCapacityElem().getAllocatedCapacity());
                    ps.setBigDecimal(5, radioev.getResinfolist().get(i).getComputeElem().getVirtualMemory().getVirtualMemSize());
                    ps.setString(6, radioev.getResinfolist().get(i).getComputeElem().getVirtualMemory().getVirtualMemOversubscriptionPolicy());
                    ps.setBoolean(7, radioev.getResinfolist().get(i).getComputeElem().getVirtualMemory().isNumaSupported());
                    ps.setString(8, radioev.getResinfolist().get(i).getComputeElem().getComputeResourceTypeId());
                    ps.setString(9, radioev.getResinfolist().get(i).getComputeElem().getAccelerationCapability());
                    ps.setLong(10, resourceZoneId);

                    ps.executeUpdate();
                    System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> memoryresources instance inserted into DB");

                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("" + ex.getMessage() + "");
                }

            } else {
                //INSERT record in into CpuResources table
                try {

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO cpuresources"
                            + "(availableCapacity,"
                            + "reservedCapacity,"
                            + "totalCapacity,"
                            + "allocatedCapacity,"
                            + "cpuArchitecture,"
                            + "numVirtualCpu,"
                            + "cpuClock,"
                            + "virtualCpuOversubscriptionPolicy,"
                            + "virtualCpuPinningSupported,"
                            + "computeResourceTypeId,"
                            + "accelerationCapability,"
                            + "resourceZoneId)"
                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, radioev.getResinfolist().get(i).getCapacityElem().getAvailableCapacity());
                    ps.setString(2, radioev.getResinfolist().get(i).getCapacityElem().getReservedCapacity());
                    ps.setString(3, radioev.getResinfolist().get(i).getCapacityElem().getTotalCapacity());
                    ps.setString(4, radioev.getResinfolist().get(i).getCapacityElem().getAllocatedCapacity());
                    ps.setString(5, radioev.getResinfolist().get(i).getComputeElem().getVirtualCPU().getCpuArchitecture());
                    ps.setBigDecimal(6, radioev.getResinfolist().get(i).getComputeElem().getVirtualCPU().getNumVirtualCpu());
                    ps.setBigDecimal(7, radioev.getResinfolist().get(i).getComputeElem().getVirtualCPU().getCpuClock());
                    ps.setString(8, radioev.getResinfolist().get(i).getComputeElem().getVirtualCPU().getVirtualCpuOversubscriptionPolicy());
                    //ps.setBoolean(9, vimev.getComputeResList().get(i).getComputeElem().getVirtualCPU().isVirtualCpuPinningSupported());
                    ps.setBoolean(9, true);
                    ps.setString(10, radioev.getResinfolist().get(i).getComputeElem().getComputeResourceTypeId());
                    ps.setString(11, radioev.getResinfolist().get(i).getComputeElem().getAccelerationCapability());
                    ps.setLong(12, resourceZoneId);

                    ps.executeUpdate();
                    System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> cpuresources instance inserted into DB");

                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
        
        //insert radio coverage area
        for (int i = 0; i < radioev.getRadioinfolist().size(); i++) {

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO radio_coverage_area"
                        + "("
                        + "coverageAreaId,"
                        + "geographicalInfo,"
                        + "coverageAreaMinBandwidth,"
                        + "coverageAreaMaxBandwidth,"
                        + "coverageAreaDelay,"
                        + "latitude,"
                        + "longitude,"
                        + "altitude,"
                        + "range_,"
                        + "nfviPopId)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, radioev.getRadioinfolist().get(i).getCoverageAreaId());
                ps.setString(2, radioev.getRadioinfolist().get(i).getCoverageAreaGoegraphicalInfo());
                ps.setString(3, radioev.getRadioinfolist().get(i).getCoverageAreaMinBandwidth().toString());
                ps.setString(4, radioev.getRadioinfolist().get(i).getCoverageAreaMaxBandwidth().toString());
                ps.setString(5, radioev.getRadioinfolist().get(i).getCoverageAreaDelay().toString());
                ps.setString(6, radioev.getRadioinfolist().get(i).getLocationInfo().getLatitude().toString());
                ps.setString(7, radioev.getRadioinfolist().get(i).getLocationInfo().getLongitude().toString());
                ps.setString(8, radioev.getRadioinfolist().get(i).getLocationInfo().getAltitude().toString());
                ps.setString(9, radioev.getRadioinfolist().get(i).getLocationInfo().getRange().toString());
                ps.setString(10, radioev.getPoplist().get(0).getNfviPopId());
                //Metadata attribute is a List of struct so we need a metadata table to handle this info  
                //ps.setString(6, wimev.getZoneList().get(i).getMetadata());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> radio coverage area inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        //insert pnflist
        for (int i = 0; i < radioev.getPnflist().size(); i++) {

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO pnfs"
                        + "("
                        + "pnfid,"
                        + "pnfstatus,"
                        + "nfviPopId)"
                        + "VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, radioev.getPnflist().get(i).getPnfid());
                ps.setString(2, "STOP"); //suppose each PNF is ready to start
                ps.setString(3, radioev.getPoplist().get(0).getNfviPopId());
                //Metadata attribute is a List of struct so we need a metadata table to handle this info  
                //ps.setString(6, wimev.getZoneList().get(i).getMetadata());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> pnfs inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        //insert mflist
        for (int i = 0; i < radioev.getMflist().size(); i++) {
            
            for (int j = 0; j <  radioev.getMflist().get(i).getMflist().size(); j++)

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO mfs"
                        + "("
                        + "pnfid,"
                        + "vnfid,"
                        + "mfid,"
                        + "nfviPopId)"
                        + "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, radioev.getMflist().get(i).getPnfid());
                ps.setString(2, radioev.getMflist().get(i).getVnfid());
                ps.setString(3, radioev.getMflist().get(i).getMflist().get(j).getMfid());
                ps.setString(4, radioev.getPoplist().get(0).getNfviPopId());
                //Metadata attribute is a List of struct so we need a metadata table to handle this info  
                //ps.setString(6, wimev.getZoneList().get(i).getMetadata());

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_RadioResAbstractionEvent ---> radio coverage area inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("DatabaseDriver ---> Handle RadioResAbstractionEvent");
        ComputeE2EAbstractionRequest e2ecomputeev = new ComputeE2EAbstractionRequest();
        System.out.println("DatabaseDriver ---> Post ComputeE2EAbstractionRequest Event");
        SingletonEventBus.getBus().post(e2ecomputeev);
    }

    @Subscribe
    public void handle_ComputeE2EAbstractionReply(ComputeE2EAbstractionReply e2ecomprep) {
        System.out.println("DatabaseDriver ---> Handle ComputeE2EAbstractionReply Event");
        System.out.println("DatabaseDriver ---> DB filled...");
        StartServer servreq = new StartServer();
        SingletonEventBus.getBus().post(servreq);
        System.out.println("DatabaseDriver ---> Start Server http");
    }

    @Subscribe
    public void handle_OperateComputeDBRequest(ComputeOperateDBReq compoperatereq) {
        System.out.println("DatabaseDriver ---> Handle OperateComputeDBRequest Event");

        //START - Retrieve from DB the NfviPoP (PopId) and domid where the compute resource is deployed as well as the
        //resource id for the domain
        long domid = 0;
        long popid = 0;
        String computeDomId = "";
        String computeServId = compoperatereq.getOperateinfo().getId();
        try {
            //String computeServId=computeIdList.remove(0).getNetworkId();

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select domId, mtpdomdb.nfvipop.nfviPopId from mtpdomdb.nfvipop inner join mtpservdb.computeservices\n"
                    + "on mtpservdb.computeservices.computeServiceId=?\n"
                    + "where mtpservdb.computeservices.nfviPopId=mtpdomdb.nfvipop.nfviPopId");
            ps.setString(1, computeServId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                domid = rs.getLong("domId");
                popid = rs.getLong("nfviPopId");
                System.out.println("DatabaseDriver.handle_OperateComputeDBRequest ---> DomId: " + rs.getLong("domid") + "");
                System.out.println("DatabaseDriver.handle_OperateComputeDBRequest ---> PopId: " + rs.getLong("NfviPopId") + "");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        // Retrieve from DB the domain compute ID
        try {
            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT computeId FROM mtpservdb.virtualcompute where computeServiceId=?");
            ps.setString(1, computeServId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                computeDomId = rs.getString("computeId");
                System.out.println("DatabaseDriver.handle_OperateComputeDBRequest ---> VM ID: " + rs.getString("computeId") + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        System.out.println("DatabaseDriver ---> DB filled...");

        ComputeOperateReq compopreq = new ComputeOperateReq(compoperatereq.getReqid(), domid, compoperatereq.getOperateinfo());
        //set the compute id using the domain compute id
        compopreq.getOperateinfo().setId(computeDomId);

        SingletonEventBus.getBus().post(compopreq);
        System.out.println("DatabaseDriver ---> Send ComputeOperateReq event");
    }

    @Subscribe
    public void handle_OperateComputeVIMReply(ComputeOperateVIMReply compoperaterep) {
        System.out.println("DatabaseDriver ---> Handle OperateComputeVIMReply Event");
        if (compoperaterep.getErrcode() != 0) {
            ComputeOperateRep compopreq = new ComputeOperateRep(compoperaterep.getReqid(), compoperaterep.getResponse());
            compopreq.setOutcome(false);
            SingletonEventBus.getBus().post(compopreq);
            System.out.println("DatabaseDriver ---> Send ComputeOperateReq event failure");
            return;
        }
        //Retrieve from DB the ComputeServiceId related to the computeId  
        long computeServiceId = 0;
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT computeServiceId FROM virtualcompute where computeId=?");
            ps.setString(1, compoperaterep.getResponse().getComputeId());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                computeServiceId = rs.getLong("computeServiceId");
                System.out.println("DatabaseDriver.handle_OperateComputeVIMReply ---> ComputeServiceId: " + computeServiceId + "");
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //change operational state according the reply
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE virtualcompute SET operationalState=? where computeId=?");
            ps.setString(1, compoperaterep.getResponse().getOperationalState());
            ps.setLong(2, Long.valueOf(compoperaterep.getResponse().getComputeId()));
            java.sql.ResultSet rs = ps.executeQuery();
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        System.out.println("DatabaseDriver ---> DB filled...");
        ComputeOperateRep compopreq = new ComputeOperateRep(compoperaterep.getReqid(), compoperaterep.getResponse());
        compopreq.getResponse().setComputeId(Long.toString(computeServiceId));
        if (compoperaterep.getErrcode() != 0) {
            compopreq.setOutcome(false);
        }
        SingletonEventBus.getBus().post(compopreq);
        System.out.println("DatabaseDriver ---> Send ComputeOperateReq event");
    }

    //////////////////Advertisement Event Handlers///////////////////////////////
    @Subscribe
    public void handle_AdvertiseE2EAbstractionRequest(AdvertiseE2EAbstractionRequest e2eadvreq) {
        System.out.println("DatabaseDriver ---> Handle AdvertiseE2EAbstractionRequest Event");
        System.out.println("field event: (reqid) = ("
                + e2eadvreq.getReqId() + ")");

        //InlineResponse2001 resp = new InlineResponse2001();
        InlineResponse2005 resp = new InlineResponse2005();
        List<NfviPop> poplist = new ArrayList();
        List<NfviPopsInnerNfviPopAttributes> popattrlist = new ArrayList();

        //Retrieve NFVIPOP
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(rs.getString("abstrNfviPopId"));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("vimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));

                System.out.println("nfviPop.abstrNfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("nfviPop.VimId: " + nfviPop.getVimId() + "");
                System.out.println("nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                poplist.add(nfviPop);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        NfviPops popslist = new NfviPops();
        //retrieve zone and resources for each nfvipop
        for (int i = 0; i < poplist.size(); i++) {
            NfviPop popel = poplist.get(i);
            NfviPopsInnerNfviPopAttributes popattr = new NfviPopsInnerNfviPopAttributes();
            //fill nfvipop values
            popattr.setVimId(popel.getVimId());
            popattr.setNfviPopId(popel.getNfviPopId());
            popattr.setGeographicalLocationInfo(popel.getGeographicalLocationInfo());
            //fill connectivity endpoint
            List<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint> endplist = new ArrayList();
            String popstr = popel.getNetworkConnectivityEndpoint();
            String[] poptoken = popstr.split(";");
            for (int tok = 0; tok < poptoken.length; tok++) {
                NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint endpel = new NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint();
                endpel.setNetGwIpAddress(poptoken[tok]);
                endplist.add(endpel);
            }
            popattr.setNetworkConnectivityEndpoint(endplist);
            //retrieve MEC region if exist and fill pop attributes
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("Select * from mec_region_info where abstrNfviPopId=?");

                ps.setString(1, poplist.get(i).getNfviPopId());
                java.sql.ResultSet rs = ps.executeQuery();
                popattr.setMecCapable("disable");
                List<MECRegionInfo> meclist = new ArrayList();
                while (rs.next()) {
                    popattr.setMecCapable("enable");
                    MECRegionInfo mecel = new MECRegionInfo();
                    MECRegionInfoMecRegionInfo mecdata = new MECRegionInfoMecRegionInfo();
                    LocationInfo locinfo = new LocationInfo();
                    locinfo.setAltitude(new BigDecimal(rs.getString("altitude")));
                    locinfo.setLatitude(new BigDecimal(rs.getString("latitude")));
                    locinfo.setLongitude(new BigDecimal(rs.getString("longitude")));
                    locinfo.setRange(new BigDecimal(rs.getString("range_")));

                    mecdata.setRegionId(String.valueOf(rs.getLong("regionId")));
                    mecdata.setLocationInfo(locinfo);

                    mecel.setMecRegionInfo(mecdata);
                    System.out.println("locinfo.altitude: " + mecdata.getLocationInfo().getAltitude() + "");
                    System.out.println("locinfo.latitude: " + mecdata.getLocationInfo().getLatitude() + "");
                    System.out.println("locinfo.longitude: " + mecdata.getLocationInfo().getLongitude() + "");
                    System.out.println("locinfo.range: " + mecdata.getLocationInfo().getRange() + "");
                    System.out.println("mecdata.regionid: " + mecdata.getRegionId() + "");

                    meclist.add(mecel);
                    //nfviPop.add(blog);
                }
                popattr.setMecRegions(meclist);
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            //retrieve radio coverage area info
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("Select * from radio_coverage_area where abstrNfviPopId=?");

                ps.setString(1, poplist.get(i).getNfviPopId());
                java.sql.ResultSet rs = ps.executeQuery();
                popattr.setRadioCapable("disable");
                List<NfviPopsInnerNfviPopAttributesRadioCoverageAreas> radiocalist = new ArrayList();
                while (rs.next()) {
                    popattr.setRadioCapable("enable");
                    NfviPopsInnerNfviPopAttributesRadioCoverageAreas radiocael = new NfviPopsInnerNfviPopAttributesRadioCoverageAreas();

                    NfviPopsInnerNfviPopAttributesCovrageLocationInfo locinfo = new NfviPopsInnerNfviPopAttributesCovrageLocationInfo();
                    locinfo.setAltitude(new Float(rs.getString("altitude")));
                    locinfo.setLatitude(new Double(rs.getString("latitude")));
                    locinfo.setLongitude(new Double(rs.getString("longitude")));
                    locinfo.setRange(new Float(rs.getString("range_")));

                    radiocael.setCoverageAreaId(rs.getString("coverageAreaId"));
                    radiocael.setMinBandwidth(new BigDecimal(rs.getString("coverageAreaMinBandwidth")));
                    radiocael.setMaxBandwidth(new BigDecimal(rs.getString("coverageAreaMaxBandwidth")));
                    radiocael.setDelay(new BigDecimal(rs.getString("coverageAreaDelay")));
                    radiocael.setCovrageLocationInfo(locinfo); 

                    System.out.println("locinfo.altitude: " + locinfo.getAltitude() + "");
                    System.out.println("locinfo.latitude: " + locinfo.getLatitude() + "");
                    System.out.println("locinfo.longitude: " + locinfo.getLongitude() + "");
                    System.out.println("locinfo.range: " + locinfo.getRange() + "");
                    System.out.println("ca.id: " + radiocael.getCoverageAreaId() + "");
                    System.out.println("ca.minbw: " + radiocael.getMinBandwidth() + "");
                    System.out.println("ca.maxbw: " + radiocael.getMaxBandwidth() + "");
                    System.out.println("ca.delay: " + radiocael.getDelay() + "");


                    radiocalist.add(radiocael);
                    //nfviPop.add(blog);
                }
                popattr.setRadioCoverageAreas(radiocalist);
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            //retrieve pnf info
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("Select * from pnfs where abstrNfviPopId=?");

                ps.setString(1, poplist.get(i).getNfviPopId());
                java.sql.ResultSet rs = ps.executeQuery();
                PNFlist pnflist = new PNFlist();
                while (rs.next()) {
                    PNFInfo pnfdata = new PNFInfo();
                    pnfdata.setPnfid(rs.getString("pnfid"));
                    
                    MetaData metadata = new MetaData();
                    MetaDataInner data = new MetaDataInner();
                    data.setKey("status");
                    data.setValue(rs.getString("pnfstatus"));
                    metadata.add(data);
                    pnfdata.setMetadata(metadata);
                    System.out.println("pnf.id: " + pnfdata.getPnfid() + "");
                    System.out.println("pnf.status: " + data.getValue() + "");
                    PNFlistInner pnfel = new PNFlistInner();
                    pnfel.setPnfinfo(pnfdata);
                    pnflist.add(pnfel);
                    //nfviPop.add(blog);
                }
                popattr.setPnflist(pnflist);
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            //retrieve resource zone for nfvipop
            List<NfviPopsInnerNfviPopAttributesResourceZoneAttributes> zonelist = new ArrayList();

            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("Select * from abstrzoneid where abstrNfviPopId=?");

                ps.setString(1, poplist.get(i).getNfviPopId());
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    NfviPopsInnerNfviPopAttributesResourceZoneAttributes zonel = new NfviPopsInnerNfviPopAttributesResourceZoneAttributes();
                    zonel.setZoneId(rs.getString("abstrResourceZoneId"));
                    zonel.setZoneName(rs.getString("zoneName"));
                    zonel.setZoneProperty(rs.getString("zoneProperty"));
                    zonel.setZoneState(rs.getString("zoneState"));
                    //zonel.setMetadata(rs.getString("metadata"));

                    System.out.println("zoneid.zoneid: " + zonel.getZoneId() + "");
                    System.out.println("zoneid.zonename: " + zonel.getZoneName() + "");
                    System.out.println("zoneid.zoneproperty: " + zonel.getZoneProperty() + "");
                    System.out.println("zoneid.zonestate: " + zonel.getZoneState() + "");
                    System.out.println("zoneid.metadata: " + zonel.getMetadata() + "");

                    zonelist.add(zonel);
                    //nfviPop.add(blog);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            for (int j = 0; j < zonelist.size(); j++) {
                //retrieve cpu resources
                try {
                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("Select * from abstrcpuresources where abstrResourceZoneId=?");

                    ps.setString(1, zonelist.get(j).getZoneId());
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        NfviPopsInnerNfviPopAttributesCpuResourceAttributes cpuel = new NfviPopsInnerNfviPopAttributesCpuResourceAttributes();
                        cpuel.setAllocatedCapacity(rs.getString("allocatedCapacity"));
                        cpuel.setAvailableCapacity(rs.getString("availableCapacity"));
                        cpuel.setReservedCapacity(rs.getString("reservedCapacity"));
                        cpuel.setTotalCapacity(rs.getString("totalCapacity"));

                        System.out.println("cpu.allocatedCapacity: " + cpuel.getAllocatedCapacity() + "");
                        System.out.println("cpu.availableCapacity: " + cpuel.getAvailableCapacity() + "");
                        System.out.println("cpu.reservedCapacity: " + cpuel.getReservedCapacity() + "");
                        System.out.println("cpu.totalCapacity: " + cpuel.getTotalCapacity() + "");

                        zonelist.get(j).setCpuResourceAttributes(cpuel);
                        //nfviPop.add(blog);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                //retrieve memory resources
                try {
                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("Select * from abstrmemoryresources where abstrResourceZoneId=?");

                    ps.setString(1, zonelist.get(j).getZoneId());
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        NfviPopsInnerNfviPopAttributesMemoryResourceAttributes memel = new NfviPopsInnerNfviPopAttributesMemoryResourceAttributes();
                        memel.setAllocatedCapacity(rs.getString("allocatedCapacity"));
                        memel.setAvailableCapacity(rs.getString("availableCapacity"));
                        memel.setReservedCapacity(rs.getString("reservedCapacity"));
                        memel.setTotalCapacity(rs.getString("totalCapacity"));

                        System.out.println("mem.allocatedCapacity: " + memel.getAllocatedCapacity() + "");
                        System.out.println("mem.availableCapacity: " + memel.getAvailableCapacity() + "");
                        System.out.println("mem.reservedCapacity: " + memel.getReservedCapacity() + "");
                        System.out.println("mem.totalCapacity: " + memel.getTotalCapacity() + "");

                        zonelist.get(j).setMemoryResourceAttributes(memel);
                        //nfviPop.add(blog);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                //retrieve storage resources
//                try {
//                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
//
//                    PreparedStatement ps = conn.prepareStatement("Select * from abstrstorageresources where abstrResourceZoneId=?");
//
//                    ps.setString(1, zonelist.get(j).getZoneId());
//                    java.sql.ResultSet rs = ps.executeQuery();
//                    while (rs.next()) {
//                        NfviPopsInnerNfviPopAttributesStorageResourceAttributes storel = new NfviPopsInnerNfviPopAttributesStorageResourceAttributes();
//                        storel.setAllocatedCapacity(rs.getString("allocatedCapacity"));
//                        storel.setAvailableCapacity(rs.getString("availableCapacity"));
//                        storel.setReservedCapacity(rs.getString("reservedCapacity"));
//                        storel.setTotalCapacity(rs.getString("totalCapacity"));
//
//                        System.out.println("storage.allocatedCapacity: " + storel.getAllocatedCapacity() + "");
//                        System.out.println("storage.availableCapacity: " + storel.getAvailableCapacity() + "");
//                        System.out.println("storage.reservedCapacity: " + storel.getReservedCapacity() + "");
//                        System.out.println("storage.totalCapacity: " + storel.getTotalCapacity() + "");
//
//                        zonelist.get(j).setStorageResourceAttributes(storel);
//                        //nfviPop.add(blog);
//                    }
//                    rs.close();
//                    ps.close();
//
//                } catch (SQLException ex) {
//                    Logger.getLogger(DatabaseDriver.class
//                            .getName()).log(Level.SEVERE, null, ex);
//                }
                //set storage attribute manually
                NfviPopsInnerNfviPopAttributesStorageResourceAttributes storel = new NfviPopsInnerNfviPopAttributesStorageResourceAttributes();
                storel.setAllocatedCapacity("0");
                storel.setAvailableCapacity("1000");
                storel.setReservedCapacity("0");
                storel.setTotalCapacity("1000");

                zonelist.get(j).setStorageResourceAttributes(storel);
            } //ZONE FOR END
            //insert in popattributes
            popattr.setResourceZoneAttributes(zonelist);
            NfviPopsInner popinel = new NfviPopsInner();
            popinel.setNfviPopAttributes(popattr);

            popslist.add(popinel);

        } //NFVIPOP FOR END
        //add nfvipops in respones
        resp.setNfviPops(popslist);

        //Insert logical link info
        LogicalLinkInterNfviPops linklist = new LogicalLinkInterNfviPops();

        //retrieve logical link info
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from logicallink");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LogicalLinkInterNfviPopsInner linkpopel = new LogicalLinkInterNfviPopsInner();
                LogicalLinkInterNfviPopsInnerLogicalLinks linkinel = new LogicalLinkInterNfviPopsInnerLogicalLinks();
                LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS linkinqos = new LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS();

                //retrieve network qos
                linkinqos.setLinkCost("metric");
                linkinqos.setLinkCostValue(BigDecimal.ONE);
                linkinqos.setLinkDelay("delay");
                linkinqos.setLinkDelayValue(new BigDecimal(rs.getShort("delay")));
                linkinel.setNetworkQoS(linkinqos);

                //set inner element
                linkinel.setAvailableBandwidth(new BigDecimal(rs.getString("availableBandwidth")));
                linkinel.setDstGwIpAddress(rs.getString("dstRouterId"));
                linkinel.setInterNfviPopNetworkTopology("p2p");
                linkinel.setInterNfviPopNetworkType("L2VPN,L3VPN,VXLAN");
                try {
                    InetAddress locip = InetAddress.getByName(rs.getString("srcRouterIp"));
                    int locval = 0;
                    for (byte b : locip.getAddress()) {
                        locval = locval << 8 | (b & 0xFF);
                    }
                    linkinel.setLocalLinkId(locval);
                } catch (UnknownHostException e) {
                    System.out.println("error local link id" + "");
                }

                linkinel.setLogicalLinkId(rs.getString("logicalLinkId"));
                try {
                    InetAddress remip = InetAddress.getByName(rs.getString("dstRouterIp"));
                    int remval = 0;
                    for (byte b : remip.getAddress()) {
                        remval = remval << 8 | (b & 0xFF);
                    }
                    linkinel.setRemoteLinkId(remval);
                } catch (UnknownHostException e) {
                    System.out.println("error local link id" + "");
                }
                linkinel.setSrcGwIpAddress(rs.getString("srcRouterId"));
                linkinel.setTotalBandwidth(new BigDecimal(rs.getString("totalBandwidth")));
                linkinel.setNetworkLayer("IP/MPLS");

                System.out.println("link.delay: " + linkinqos.getLinkDelayValue() + "");
                System.out.println("link.availableBandwidth: " + linkinel.getAvailableBandwidth() + "");
                System.out.println("link.totalBandwidth: " + linkinel.getTotalBandwidth() + "");
                System.out.println("link.srcGwId: " + linkinel.getSrcGwIpAddress() + "");
                System.out.println("link.dstGwId: " + linkinel.getDstGwIpAddress() + "");
                System.out.println("link.localid: " + linkinel.getLocalLinkId() + "");
                System.out.println("link.remoteid: " + linkinel.getRemoteLinkId() + "");

                linkpopel.setLogicalLinks(linkinel);
                linklist.add(linkpopel);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //add linklist in response
        resp.setLogicalLinkInterNfviPops(linklist);

        AdvertiseE2EAbstractionReply e2eadvrep = new AdvertiseE2EAbstractionReply(e2eadvreq.getReqId(), resp);
        System.out.println("DatabaseDriver --->  Post AdvertiseE2EAbstractionReply Event");
        SingletonEventBus.getBus().post(e2eadvrep);
    }

    @Subscribe
    public void handle_AdvertiseAppDRequest(AdvertiseAppDRequest appdreq) {
        System.out.println("DatabaseDriver ---> Handle AdvertiseAppDRequest Event");

        List<AppD> appdlist = new ArrayList();
        List<Long> appdid = new ArrayList();
        //select all appd
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select * from appd");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppD appdelem = new AppD();
                appdelem.setAppDId(rs.getString("appDId"));
                appdelem.setAppDVersion(rs.getString("appDVersion"));
                appdelem.setAppInfoName(rs.getString("appInfoName"));
                appdelem.setAppName(rs.getString("appName"));
                appdelem.setAppProvider(rs.getString("appProvider"));
                appdelem.setAppSoftVersion(rs.getString("appSoftVersion"));
                appdelem.setAppDescription(rs.getString("appDescription"));
                List<String> mecversion = new ArrayList();
                mecversion.add(rs.getString("mecVersion"));
                appdelem.setMecVersion(mecversion);
                appdid.add(Long.valueOf(appdelem.getAppDId()));
                appdlist.add(appdelem);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        for (int k = 0; k < appdid.size(); k++) {
            long appdval = appdid.get(k);
            //set virtual Compute Descriptor
            VirtualComputeDescription computeelem = new VirtualComputeDescription();
            VirtualMemoryData memelem = new VirtualMemoryData();
            VirtualCpuData cpuelem = new VirtualCpuData();
            List<RequestAdditionalCapabilityData> capdatalist = new ArrayList();

            //retrieve VirtualCompute Description
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select virtualComputeDescId from "
                        + "virtualcomputedescriptor where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    computeelem.setVirtualComputeDescId(rs.getString("virtualComputeDescId"));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //retrieve cpu descriptor
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select cpuArchitecture,numVirtualCpu,virtualCpuClock,virtualCpuOversubscriptionPolicy from "
                        + "virtualcpu where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cpuelem.setCpuArchitecture(rs.getString("cpuArchitecture"));
                    cpuelem.setNumVirtualCpu(new BigDecimal(rs.getString("numVirtualCpu")));
                    cpuelem.setVirtualCpuClock(new BigDecimal(rs.getString("virtualCpuClock")));
                    cpuelem.setVirtualCpuOversubscriptionPolicy(rs.getString("virtualCpuOversubscriptionPolicy"));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //retrieve cpu 
            VirtualCpuPinningData cpupinning = new VirtualCpuPinningData();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select cpuPinningPolicy,cpuPinningMap from "
                        + "virtualcpupinning where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cpupinning.setCpuPinningMap(rs.getString("cpuPinningMap"));
                    cpupinning.setCpuPinningPolicy(VirtualCpuPinningData.CpuPinningPolicyEnum.fromValue(rs.getString("cpuPinningPolicy")));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            cpuelem.setVirtualCpuPinning(cpupinning);
            computeelem.setVirtualCpu(cpuelem);

            //retrieve memory descriptor
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select virtualMemSize,numaEnabled,virtualMemOversubscriptionPolicy from "
                        + "virtualmemory where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    memelem.setNumaEnabled(rs.getBoolean("numaEnabled"));
                    memelem.setVirtualMemOversubscriptionPolicy(rs.getString("virtualMemOversubscriptionPolicy"));
                    memelem.setVirtualMemSize(new BigDecimal(rs.getString("virtualMemSize")));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            computeelem.setVirtualMemory(memelem);

            //retrieve additional capability descriptor
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select requestedAdditionalCapabilityName,supportMandatory,"
                        + "minRequestedAdditionalCapabilityVersion,preferredRequestedAdditionalCapabilityVersion,targetPerformanceParameters from "
                        + "requestadditionalcapabilities where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    RequestAdditionalCapabilityData capdatael = new RequestAdditionalCapabilityData();
                    capdatael.setMinRequestedAdditionalCapabilityVersion(rs.getString("minRequestedAdditionalCapabilityVersion"));
                    capdatael.setPreferredRequestedAdditionalCapabilityVersion(rs.getString("preferredRequestedAdditionalCapabilityVersion"));
                    capdatael.setRequestedAdditionalCapabilityName(rs.getString("requestedAdditionalCapabilityName"));
                    capdatael.setSupportMandatory(rs.getBoolean("supportMandatory"));
                    capdatael.setTargetPerformanceParameters(rs.getString("targetPerformanceParameters"));
                    capdatalist.add(capdatael);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            computeelem.setRequestAdditionalCapabilities(capdatalist);
            //set the compute descriptor in appd
            appdlist.get(k).setVirtualComputeDescriptor(computeelem);

            //set SW Image Descriptor
            List<SwImageDescriptor> swImageDescriptor = new ArrayList();
            //retrieve additional capability descriptor
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select id,name,version,checksum_,containerFormat,"
                        + "diskFormat,minDisk,minRam,size_,swImage,operatingSystem,supportedVirtualizationEnvironment from "
                        + "swimagedescriptor where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    SwImageDescriptor swel = new SwImageDescriptor();
                    swel.setChecksum(rs.getString("checksum_"));
                    swel.setContainerFormat(rs.getString("containerFormat"));
                    swel.setDiskFormat(rs.getString("diskFormat"));
                    swel.setId(rs.getString("id"));
                    swel.setMinDisk(new BigDecimal(rs.getString("minDisk")));
                    swel.setMinRam(new BigDecimal(rs.getString("minRam")));
                    swel.setName(rs.getString("name"));
                    swel.setOperatingSystem(rs.getString("operatingSystem"));
                    swel.setSize(new BigDecimal(rs.getString("size_")));
                    List<String> suppvirt = new ArrayList();
                    String virtel = rs.getString("supportedVirtualizationEnvironment");
                    suppvirt.add(virtel);
                    swel.setSupportedVirtualizationEnvironment(suppvirt);
                    swel.setSwImage(rs.getString("swImage"));
                    swel.setVersion(rs.getString("version"));
                    swImageDescriptor.add(swel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setSwImageDescriptor(swImageDescriptor);

            //set Virtual Storage Descriptor
            List<VirtualStorageDescriptor> virtualStorageDescriptor = new ArrayList();
            //retrieve additional capability descriptor
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select id,typeOfStorage,sizeOfStorage,"
                        + "rdmaEnabled,swImageDesc from "
                        + "virtualstoragedescriptor where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    VirtualStorageDescriptor storel = new VirtualStorageDescriptor();
                    storel.setId(rs.getString("id"));
                    storel.setRdmaEnabled(rs.getBoolean("rdmaEnabled"));
                    storel.setSizeOfStorage(new BigDecimal(rs.getString("sizeOfStorage")));
                    storel.setSwImageDesc(rs.getString("swImageDesc"));
                    storel.setTypeOfStorage(rs.getString("typeOfStorage"));
                    virtualStorageDescriptor.add(storel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setVirtualStorageDescriptor(virtualStorageDescriptor);

            //set App External Cpd
            //retrieve App external cpd
            List<AppExternalCpd> appExtCpd = new ArrayList();
            List<Long> cpdlist = new ArrayList();
            //retrieve additional capability descriptor
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select cpdId,layerProtocol,cpRole,"
                        + "description from "
                        + "appextcpd where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AppExternalCpd extcpd = new AppExternalCpd();
                    extcpd.setCpRole(rs.getString("cpRole"));
                    extcpd.setCpdId(rs.getString("cpdId"));
                    extcpd.setDescription(rs.getString("description"));
                    extcpd.setLayerProtocol(AppExternalCpd.LayerProtocolEnum.fromValue(rs.getString("cpRole")));
                    cpdlist.add(Long.valueOf(extcpd.getCpdId()));
                    appExtCpd.add(extcpd);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            for (int j = 0; j < cpdlist.size(); j++) {
                //Retrieve address data
                List<AddressData> addressData = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select addressType,l2AddressData,l3AddressData from "
                            + "addressdata where cpdId=?");
                    ps.setLong(1, cpdlist.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        AddressData addrel = new AddressData();
                        addrel.setAddressType(AddressTypeEnum.fromValue(rs.getString("addressType")));
                        addrel.setL2AddressData(rs.getString("l2AddressData"));
                        addrel.setL3AddressData(rs.getString("l3AddressData"));
                        addressData.add(addrel);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appExtCpd.get(j).setAddressData(addressData);
                //retrieve virtual network interfacement requirement
                List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select name,description,supportMandatory,requirement from "
                            + "virtualnetworkinterfacerequirements where cpdId=?");
                    ps.setLong(1, cpdlist.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        VirtualNetworkInterfaceRequirements vnintf = new VirtualNetworkInterfaceRequirements();
                        vnintf.setDescription(rs.getString("description"));
                        vnintf.setName(rs.getString("name"));
                        vnintf.setRequirement(rs.getString("cpdId"));
                        vnintf.setSupportMandatory(rs.getBoolean("supportMandatory"));
                        virtualNetworkInterfaceRequirements.add(vnintf);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appExtCpd.get(j).setVirtualNetworkInterfaceRequirements(virtualNetworkInterfaceRequirements);
            }

            appdlist.get(k).setAppExtCpd(appExtCpd);

            //Set appservice required
            //Retrieve App service List required from AppD DB
            List<Long> servrequiredid = new ArrayList();
            List<ServiceDependency> appServiceRequired = new ArrayList();

            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select serviceRequiredId,serName,version,requestedPermissions from "
                        + "appservicerequired where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ServiceDependency servicerequire = new ServiceDependency();
                    long servreqel = rs.getLong("serviceRequiredId");
                    servicerequire.setSerName(rs.getString("serName"));
                    servicerequire.setVersion(rs.getString("version"));
                    servicerequire.setRequestedPermissions(rs.getString("requestedPermissions"));
                    appServiceRequired.add(servicerequire);
                    servrequiredid.add(servreqel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            for (int j = 0; j < servrequiredid.size(); j++) {
                //Retrieve ser Category
                CategoryRef catref = new CategoryRef();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select href,id,name,version from "
                            + "sercategory where serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        catref.setHref(rs.getString("href"));
                        catref.setId(rs.getString("id"));
                        catref.setName(rs.getString("name"));
                        catref.setVersion(rs.getString("version"));
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceRequired.get(j).setSerCategory(catref);
                //Retrieve transport dependencies
                List<TransportDependency> serTransportDependencies = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select sertransportdependencies.serializers,sertransportdependencies.labels,"
                            + "sertransport.transportType,sertransport.protocol,sertransport.version,"
                            + "sertransport.security from "
                            + "sertransportdependencies inner join  sertransport on "
                            + "sertransportdependencies.serTransportDependenciesId = sertransport.serTransportDependenciesId "
                            + "where sertransportdependencies.serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TransportDependency transpel = new TransportDependency();
                        transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                        List<String> labellist = new ArrayList();
                        labellist.add(rs.getString("labels"));
                        transpel.setLabels(labellist);
                        TransportDescriptor transport = new TransportDescriptor();
                        transport.setProtocol(rs.getString("protocol"));
                        transport.setSecurity(rs.getString("security"));
                        transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                        transport.setVersion(rs.getString("version"));
                        transpel.setTransport(transport);
                        serTransportDependencies.add(transpel);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceRequired.get(j).serTransportDependencies(serTransportDependencies);
            }
            appdlist.get(k).setAppServiceRequired(appServiceRequired);

            //Set appservice optional
            //Retrieve App service List required from AppD DB
            List<Long> servroptionalid = new ArrayList();
            List<ServiceDependency> appServiceOptional = new ArrayList();

            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select serviceRequiredId,serName,version,requestedPermissions from "
                        + "appserviceoptional where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ServiceDependency servicerequire = new ServiceDependency();
                    long servreqel = rs.getLong("serviceRequiredId");
                    servicerequire.setSerName(rs.getString("serName"));
                    servicerequire.setVersion(rs.getString("version"));
                    servicerequire.setRequestedPermissions(rs.getString("requestedPermissions"));
                    appServiceOptional.add(servicerequire);
                    servroptionalid.add(servreqel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            for (int j = 0; j < servroptionalid.size(); j++) {
                //Retrieve ser Category
                CategoryRef catref = new CategoryRef();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select href,id,name,version from "
                            + "sercategoryoptional where serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        catref.setHref(rs.getString("href"));
                        catref.setId(rs.getString("id"));
                        catref.setName(rs.getString("name"));
                        catref.setVersion(rs.getString("version"));
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceOptional.get(j).setSerCategory(catref);
                //Retrieve transport dependencies
                List<TransportDependency> serTransportDependencies = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select sertransportdependenciesoptional.serializers,sertransportdependenciesoptional.labels,"
                            + "transportdescriptoroptional.transportType,transportdescriptoroptional.protocol,transportdescriptoroptional.version,"
                            + "transportdescriptoroptional.security from "
                            + "sertransportdependenciesoptional inner join  transportdescriptoroptional on "
                            + "sertransportdependenciesoptional.serTransportDependenciesId = transportdescriptoroptional.serTransportDependenciesId "
                            + "where sertransportdependenciesoptional.serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TransportDependency transpel = new TransportDependency();
                        transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                        List<String> labellist = new ArrayList();
                        labellist.add(rs.getString("labels"));
                        transpel.setLabels(labellist);
                        TransportDescriptor transport = new TransportDescriptor();
                        transport.setProtocol(rs.getString("protocol"));
                        transport.setSecurity(rs.getString("security"));
                        transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                        transport.setVersion(rs.getString("version"));
                        transpel.setTransport(transport);
                        serTransportDependencies.add(transpel);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceOptional.get(j).serTransportDependencies(serTransportDependencies);
            }
            appdlist.get(k).setAppServiceOptional(appServiceOptional);

            //Set transport dependencies
            List<TransportDependency> serTransportDependencies = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select transportdependencies.serializers,transportdependencies.labels,"
                        + "transport.transportType,transport.protocol,transport.version,"
                        + "transport.security from "
                        + "transportdependencies inner join  transport on "
                        + "transportdependencies.TransportDependenciesId = transport.TransportDependenciesId "
                        + "where transportdependencies.appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TransportDependency transpel = new TransportDependency();
                    transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                    List<String> labellist = new ArrayList();
                    labellist.add(rs.getString("labels"));
                    transpel.setLabels(labellist);
                    TransportDescriptor transport = new TransportDescriptor();
                    transport.setProtocol(rs.getString("protocol"));
                    transport.setSecurity(rs.getString("security"));
                    transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                    transport.setVersion(rs.getString("version"));
                    transpel.setTransport(transport);
                    serTransportDependencies.add(transpel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setTransportDependencies(serTransportDependencies);

            //Set TrafficRuleDescriptor
            List<TrafficRuleDescriptor> appTrafficRule = new ArrayList();
            List<Long> traffruleid = new ArrayList();
            //retrieve traffic rules
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select trafficRuleId,filterType,priority,action from "
                        + "apptrafficrule where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TrafficRuleDescriptor traffrule = new TrafficRuleDescriptor();
                    long servreqel = rs.getLong("trafficRuleId");
                    traffrule.setFilterType(TrafficRuleDescriptor.FilterTypeEnum.fromValue(rs.getString("filterType")));
                    traffrule.setTrafficRuleId(rs.getString("trafficRuleId"));
                    traffrule.setAction(ActionEnum.fromValue(rs.getString("action")));
                    traffrule.setPriority(new BigDecimal(rs.getString("trafficRuleId")));
                    appTrafficRule.add(traffrule);
                    traffruleid.add(servreqel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            for (int j = 0; j < traffruleid.size(); j++) {
                //retrieve traffic filter
                List<TrafficFilter> trafficFilter = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select srcAddress,dstAddress,srcPort,dstPort,"
                            + "protocol,token,srcTunnelAddress,dstTunnelAddress,srcTunnelPort,dstTunnelPort,"
                            + "qci,dscp,tc from "
                            + "trafficfilter where trafficRuleId=?");
                    ps.setLong(1, traffruleid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TrafficFilter trafffilter = new TrafficFilter();
                        trafffilter.setDscp(new BigDecimal(rs.getString("dscp")));
                        trafffilter.setQci(new BigDecimal(rs.getString("qci")));
                        trafffilter.setTc(new BigDecimal(rs.getString("tc")));
                        List<String> strlist = new ArrayList();
                        strlist.add(rs.getString("dstAddress"));
                        trafffilter.setDstAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("dstPort"));
                        trafffilter.setDstPort(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("dstTunnelAddress"));
                        trafffilter.setDstTunnelAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("dstTunnelPort"));
                        trafffilter.setDstTunnelPort(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("protocol"));
                        trafffilter.setProtocol(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("token"));
                        trafffilter.setToken(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcAddress"));
                        trafffilter.setSrcAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcPort"));
                        trafffilter.setSrcPort(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcTunnelAddress"));
                        trafffilter.setSrcTunnelAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcTunnelPort"));
                        trafffilter.setSrcTunnelPort(strlist);
                        trafficFilter.add(trafffilter);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appTrafficRule.get(j).setTrafficFilter(trafficFilter);
                //retrieve traffic interfaces
                List<InterfaceDescriptor> dstInterface = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select dstinterface.interfaceType,dstinterface.srcMACAddress,"
                            + "dstinterface.dstMACAddress,dstinterface.dstIPAddress,tunnelinfo.tunnelType,"
                            + "tunnelinfo.tunnelDstAddress,tunnelinfo.tunnelSrcAddress,tunnelinfo.tunnelSpecificData from "
                            + "dstinterface inner join  tunnelinfo on "
                            + "dstinterface.dstInterfaceId = tunnelinfo.dstInterfaceId "
                            + "where dstinterface.trafficRuleId=?");
                    ps.setLong(1, traffruleid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        InterfaceDescriptor intf = new InterfaceDescriptor();
                        TunnelInfo tunnel = new TunnelInfo();
                        tunnel.setTunnelDstAddress(rs.getString("tunnelDstAddress"));
                        tunnel.setTunnelSpecificData(rs.getString("tunnelSpecificData"));
                        tunnel.setTunnelSrcAddress(rs.getString("tunnelSrcAddress"));
                        tunnel.setTunnelType(TunnelInfo.TunnelTypeEnum.fromValue(rs.getString("tunnelType")));
                        intf.setTunnelInfo(tunnel);
                        intf.setDstIPAddress(rs.getString("dstIPAddress"));
                        intf.setDstMACAddress(rs.getString("dstMACAddress"));
                        intf.setInterfaceType(InterfaceDescriptor.InterfaceTypeEnum.fromValue(rs.getString("interfaceType")));
                        intf.setSrcMACAddress(rs.getString("srcMACAddress"));
                        dstInterface.add(intf);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appTrafficRule.get(j).setDstInterface(dstInterface);
            }
            appdlist.get(k).setAppTrafficRule(appTrafficRule);

            //Set appDNSRule
            List<DNSRuleDescriptor> appDNSRule = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select dnsRuleId,domainName,ipAddressType,ipAddress,ttl from "
                        + "appdnsrule where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    DNSRuleDescriptor dnsrule = new DNSRuleDescriptor();
                    dnsrule.setDnsRuleId(String.valueOf(rs.getLong("dnsRuleId")));
                    dnsrule.setDomainName(rs.getString("domainName"));
                    dnsrule.setIpAddress(rs.getString("ipAddress"));
                    dnsrule.setIpAddressType(DNSRuleDescriptor.IpAddressTypeEnum.fromValue(rs.getString("ipAddressType")));
                    dnsrule.setTtl(new BigDecimal(rs.getString("ttl")));
                    appDNSRule.add(dnsrule);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setAppDNSRule(appDNSRule);

            //Set LatencyDescriptor
            LatencyDescriptor latency = new LatencyDescriptor();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select timeUnit,latency from "
                        + "applatency where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    latency.setTimeUnit(rs.getString("timeUnit"));
                    latency.setLatency(new BigDecimal(rs.getString("latency")));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setAppLatency(latency);

            //Set Terminate App Instance OpConfig
            TerminateAppInstanceOpConfig termopconf = new TerminateAppInstanceOpConfig();
            //retrieve TerminateAppInstanceOpConfig
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select minGracefulTerminationTimeout,macRecommendedGracefulTerminationTimeout from "
                        + "terminateappinstanceopconfig where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    termopconf.setMacRecommendedGracefulTerminationTimeout(new BigDecimal(rs.getString("macRecommendedGracefulTerminationTimeout")));
                    termopconf.setMinGracefulTerminationTimeout(new BigDecimal(rs.getString("minGracefulTerminationTimeout")));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setTerminateAppInstanceOpConfig(termopconf);

            //Set Change App Instance StateOpConfig
            ChangeAppInstanceStateOpConfig changeopconf = new ChangeAppInstanceStateOpConfig();
            //retrieve ChangeAppInstanceOpConfig
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select minGracefulTerminationTimeout,macRecommendedGracefulTerminationTimeout from "
                        + "changeappinstancestateopconfig where appDId=?");
                ps.setLong(1, appdval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    changeopconf.setMacRecommendedGracefulStopTimeout(new BigDecimal(rs.getString("macRecommendedGracefulTerminationTimeout")));
                    changeopconf.setMinGracefulStopTimeout(new BigDecimal(rs.getString("minGracefulTerminationTimeout")));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appdlist.get(k).setChangeAppInstanceStateOpConfig(changeopconf);
        }

        //Send event to SBI for MEC
        AdvertiseAppDReply appdlistrep = new AdvertiseAppDReply(appdreq.getReqid(), appdlist);
        System.out.println("DatabaseDriver --> Post AdvertiseAppDReply Event");
        SingletonEventBus.getBus().post(appdlistrep);
    }

    @Subscribe
    public void handle_AdvertiseAppDIdRequest(AdvertiseAppDIdRequest appdreq) {
        System.out.println("DatabaseDriver ---> Handle AdvertiseAppDIdRequest Event");

        AppD appdelem = new AppD();
        //select appd
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select * from appd where appDId");
            ps.setLong(1, Long.valueOf(appdreq.getAppid()));
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appdelem.setAppDId(rs.getString("appDId"));
                appdelem.setAppDVersion(rs.getString("appDVersion"));
                appdelem.setAppInfoName(rs.getString("appInfoName"));
                appdelem.setAppName(rs.getString("appName"));
                appdelem.setAppProvider(rs.getString("appProvider"));
                appdelem.setAppSoftVersion(rs.getString("appSoftVersion"));
                appdelem.setAppDescription(rs.getString("appDescription"));
                List<String> mecversion = new ArrayList();
                mecversion.add(rs.getString("mecVersion"));
                appdelem.setMecVersion(mecversion);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        long appdval = Long.valueOf(appdreq.getAppid());
        //set virtual Compute Descriptor
        VirtualComputeDescription computeelem = new VirtualComputeDescription();
        VirtualMemoryData memelem = new VirtualMemoryData();
        VirtualCpuData cpuelem = new VirtualCpuData();
        List<RequestAdditionalCapabilityData> capdatalist = new ArrayList();

        //retrieve VirtualCompute Description
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select virtualComputeDescId from "
                    + "virtualcomputedescriptor where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                computeelem.setVirtualComputeDescId(rs.getString("virtualComputeDescId"));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //retrieve cpu descriptor
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select cpuArchitecture,numVirtualCpu,virtualCpuClock,virtualCpuOversubscriptionPolicy from "
                    + "virtualcpu where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cpuelem.setCpuArchitecture(rs.getString("cpuArchitecture"));
                cpuelem.setNumVirtualCpu(new BigDecimal(rs.getString("numVirtualCpu")));
                cpuelem.setVirtualCpuClock(new BigDecimal(rs.getString("virtualCpuClock")));
                cpuelem.setVirtualCpuOversubscriptionPolicy(rs.getString("virtualCpuOversubscriptionPolicy"));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //retrieve cpu 
        VirtualCpuPinningData cpupinning = new VirtualCpuPinningData();
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select cpuPinningPolicy,cpuPinningMap from "
                    + "virtualcpupinning where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cpupinning.setCpuPinningMap(rs.getString("cpuPinningMap"));
                cpupinning.setCpuPinningPolicy(VirtualCpuPinningData.CpuPinningPolicyEnum.fromValue(rs.getString("numVirtualCpu")));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        cpuelem.setVirtualCpuPinning(cpupinning);
        computeelem.setVirtualCpu(cpuelem);

        //retrieve memory descriptor
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select virtualMemSize,numaEnabled,virtualMemOversubscriptionPolicy from "
                    + "virtualmemory where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                memelem.setNumaEnabled(rs.getBoolean("numaEnabled"));
                memelem.setVirtualMemOversubscriptionPolicy(rs.getString("virtualMemOversubscriptionPolicy"));
                memelem.setVirtualMemSize(new BigDecimal(rs.getString("virtualMemSize")));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        computeelem.setVirtualMemory(memelem);

        //retrieve additional capability descriptor
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select requestedAdditionalCapabilityName,supportMandatory,"
                    + "minRequestedAdditionalCapabilityVersion,preferredRequestedAdditionalCapabilityVersion,targetPerformanceParameters from "
                    + "requestadditionalcapabilities where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RequestAdditionalCapabilityData capdatael = new RequestAdditionalCapabilityData();
                capdatael.setMinRequestedAdditionalCapabilityVersion(rs.getString("minRequestedAdditionalCapabilityVersion"));
                capdatael.setPreferredRequestedAdditionalCapabilityVersion(rs.getString("preferredRequestedAdditionalCapabilityVersion"));
                capdatael.setRequestedAdditionalCapabilityName(rs.getString("requestedAdditionalCapabilityName"));
                capdatael.setSupportMandatory(rs.getBoolean("supportMandatory"));
                capdatael.setTargetPerformanceParameters(rs.getString("targetPerformanceParameters"));
                capdatalist.add(capdatael);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        computeelem.setRequestAdditionalCapabilities(capdatalist);
        //set the compute descriptor in appd
        appdelem.setVirtualComputeDescriptor(computeelem);

        //set SW Image Descriptor
        List<SwImageDescriptor> swImageDescriptor = new ArrayList();
        //retrieve additional capability descriptor
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select id,name,version,checksum_,containerFormat,"
                    + "diskFormat,minDisk,minRam,size,swImage,operatingSystem,supportedVirtualizationEnvironment from "
                    + "swimagedescriptor where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SwImageDescriptor swel = new SwImageDescriptor();
                swel.setChecksum(rs.getString("checksum_"));
                swel.setContainerFormat(rs.getString("containerFormat"));
                swel.setDiskFormat(rs.getString("diskFormat"));
                swel.setId(rs.getString("id"));
                swel.setMinDisk(new BigDecimal(rs.getString("minDisk")));
                swel.setMinRam(new BigDecimal(rs.getString("minRam")));
                swel.setName(rs.getString("name"));
                swel.setOperatingSystem(rs.getString("operatingSystem"));
                swel.setSize(new BigDecimal(rs.getString("size")));
                List<String> suppvirt = new ArrayList();
                String virtel = rs.getString("supportedVirtualizationEnvironment");
                suppvirt.add(virtel);
                swel.setSupportedVirtualizationEnvironment(suppvirt);
                swel.setSwImage(rs.getString("swImage"));
                swel.setVersion(rs.getString("version"));
                swImageDescriptor.add(swel);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setSwImageDescriptor(swImageDescriptor);

        //set Virtual Storage Descriptor
        List<VirtualStorageDescriptor> virtualStorageDescriptor = new ArrayList();
        //retrieve additional capability descriptor
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select id,typeOfStorage,sizeOfStorage,"
                    + "rdmaEnabled,swImageDesc from "
                    + "virtualstoragedescriptor where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VirtualStorageDescriptor storel = new VirtualStorageDescriptor();
                storel.setId(rs.getString("id"));
                storel.setRdmaEnabled(rs.getBoolean("rdmaEnabled"));
                storel.setSizeOfStorage(new BigDecimal(rs.getString("sizeOfStorage")));
                storel.setSwImageDesc(rs.getString("swImageDesc"));
                storel.setTypeOfStorage(rs.getString("typeOfStorage"));
                virtualStorageDescriptor.add(storel);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setVirtualStorageDescriptor(virtualStorageDescriptor);

        //set App External Cpd
        //retrieve App external cpd
        List<AppExternalCpd> appExtCpd = new ArrayList();
        List<Long> cpdlist = new ArrayList();
        //retrieve additional capability descriptor
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select cpdId,layerProtocol,cpRole,"
                    + "description from "
                    + "appextcpd where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppExternalCpd extcpd = new AppExternalCpd();
                extcpd.setCpRole(rs.getString("cpRole"));
                extcpd.setCpdId(rs.getString("cpdId"));
                extcpd.setDescription(rs.getString("description"));
                extcpd.setLayerProtocol(AppExternalCpd.LayerProtocolEnum.fromValue(rs.getString("cpRole")));
                cpdlist.add(Long.valueOf(extcpd.getCpdId()));
                appExtCpd.add(extcpd);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        for (int j = 0; j < cpdlist.size(); j++) {
            //Retrieve address data
            List<AddressData> addressData = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select addressType,l2AddressData,l3AddressData from "
                        + "addressdata where cpdId=?");
                ps.setLong(1, cpdlist.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AddressData addrel = new AddressData();
                    addrel.setAddressType(AddressTypeEnum.fromValue(rs.getString("addressType")));
                    addrel.setL2AddressData(rs.getString("l2AddressData"));
                    addrel.setL3AddressData(rs.getString("l3AddressData"));
                    addressData.add(addrel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appExtCpd.get(j).setAddressData(addressData);
            //retrieve virtual network interfacement requirement
            List<VirtualNetworkInterfaceRequirements> virtualNetworkInterfaceRequirements = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,description,supportMandatory,requirement from "
                        + "virtualnetworkinterfacerequirements where cpdId=?");
                ps.setLong(1, cpdlist.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    VirtualNetworkInterfaceRequirements vnintf = new VirtualNetworkInterfaceRequirements();
                    vnintf.setDescription(rs.getString("description"));
                    vnintf.setName(rs.getString("name"));
                    vnintf.setRequirement(rs.getString("cpdId"));
                    vnintf.setSupportMandatory(rs.getBoolean("supportMandatory"));
                    virtualNetworkInterfaceRequirements.add(vnintf);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appExtCpd.get(j).setVirtualNetworkInterfaceRequirements(virtualNetworkInterfaceRequirements);
        }

        appdelem.setAppExtCpd(appExtCpd);

        //Set appservice required
        //Retrieve App service List required from AppD DB
        List<Long> servrequiredid = new ArrayList();
        List<ServiceDependency> appServiceRequired = new ArrayList();

        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select serviceRequiredId,serName,version,requestedPermissions from "
                    + "appservicerequired where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServiceDependency servicerequire = new ServiceDependency();
                long servreqel = rs.getLong("serviceRequiredId");
                servicerequire.setSerName(rs.getString("serName"));
                servicerequire.setVersion(rs.getString("version"));
                servicerequire.setRequestedPermissions(rs.getString("requestedPermissions"));
                appServiceRequired.add(servicerequire);
                servrequiredid.add(servreqel);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        for (int j = 0; j < servrequiredid.size(); j++) {
            //Retrieve ser Category
            CategoryRef catref = new CategoryRef();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select href,id,name,version from "
                        + "sercategory where serviceRequiredId=?");
                ps.setLong(1, servrequiredid.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    catref.setHref(rs.getString("href"));
                    catref.setId(rs.getString("id"));
                    catref.setName(rs.getString("name"));
                    catref.setVersion(rs.getString("version"));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appServiceRequired.get(j).setSerCategory(catref);
            //Retrieve transport dependencies
            List<TransportDependency> serTransportDependencies = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select sertransportdependencies.serializers,sertransportdependencies.labels,"
                        + "sertransport.transportType,sertransport.protocol,sertransport.version,"
                        + "sertransport.security from "
                        + "sertransportdependencies inner join  sertransport on "
                        + "sertransportdependencies.serTransportDependenciesId = sertransport.serTransportDependenciesId "
                        + "where sertransportdependencies.serviceRequiredId=?");
                ps.setLong(1, servrequiredid.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TransportDependency transpel = new TransportDependency();
                    transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                    List<String> labellist = new ArrayList();
                    labellist.add(rs.getString("labels"));
                    transpel.setLabels(labellist);
                    TransportDescriptor transport = new TransportDescriptor();
                    transport.setProtocol(rs.getString("protocol"));
                    transport.setSecurity(rs.getString("security"));
                    transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                    transport.setVersion(rs.getString("version"));
                    transpel.setTransport(transport);
                    serTransportDependencies.add(transpel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appServiceRequired.get(j).serTransportDependencies(serTransportDependencies);
        }
        appdelem.setAppServiceRequired(appServiceRequired);

        //Set appservice optional
        //Retrieve App service List required from AppD DB
        List<Long> servroptionalid = new ArrayList();
        List<ServiceDependency> appServiceOptional = new ArrayList();

        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select serviceRequiredId,serName,version,requestedPermissions from "
                    + "appserviceoptional where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServiceDependency servicerequire = new ServiceDependency();
                long servreqel = rs.getLong("serviceRequiredId");
                servicerequire.setSerName(rs.getString("serName"));
                servicerequire.setVersion(rs.getString("version"));
                servicerequire.setRequestedPermissions(rs.getString("requestedPermissions"));
                appServiceOptional.add(servicerequire);
                servroptionalid.add(servreqel);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        for (int j = 0; j < servroptionalid.size(); j++) {
            //Retrieve ser Category
            CategoryRef catref = new CategoryRef();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select href,id,name,version from "
                        + "sercategoryoptional where serviceRequiredId=?");
                ps.setLong(1, servrequiredid.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    catref.setHref(rs.getString("href"));
                    catref.setId(rs.getString("id"));
                    catref.setName(rs.getString("name"));
                    catref.setVersion(rs.getString("version"));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appServiceOptional.get(j).setSerCategory(catref);
            //Retrieve transport dependencies
            List<TransportDependency> serTransportDependencies = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select sertransportdependenciesoptional.serializers,sertransportdependenciesoptional.labels,"
                        + "transportoptional.transportType,transportoptional.protocol,transportoptional.version,"
                        + "transportoptional.security from "
                        + "sertransportdependenciesoptional inner join  sertransportoptional on "
                        + "sertransportdependenciesoptional.serTransportDependenciesId = transportoptional.serTransportDependenciesId "
                        + "where transportdependenciesoptional.serviceRequiredId=?");
                ps.setLong(1, servrequiredid.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TransportDependency transpel = new TransportDependency();
                    transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                    List<String> labellist = new ArrayList();
                    labellist.add(rs.getString("labels"));
                    transpel.setLabels(labellist);
                    TransportDescriptor transport = new TransportDescriptor();
                    transport.setProtocol(rs.getString("protocol"));
                    transport.setSecurity(rs.getString("security"));
                    transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                    transport.setVersion(rs.getString("version"));
                    transpel.setTransport(transport);
                    serTransportDependencies.add(transpel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appServiceOptional.get(j).serTransportDependencies(serTransportDependencies);
        }
        appdelem.setAppServiceOptional(appServiceOptional);

        //Set transport dependencies
        List<TransportDependency> serTransportDependencies = new ArrayList();
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select transportdependencies.serializers,transportdependencies.labels,"
                    + "transport.transportType,transport.protocol,transport.version,"
                    + "transport.security from "
                    + "transportdependencies inner join  transport on "
                    + "transportdependencies.TransportDependenciesId = transport.TransportDependenciesId "
                    + "where transportdependencies.appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransportDependency transpel = new TransportDependency();
                transpel.serializers(TransportDependency.SerializersEnum.valueOf(rs.getString("serializers")));
                List<String> labellist = new ArrayList();
                labellist.add(rs.getString("labels"));
                transpel.setLabels(labellist);
                TransportDescriptor transport = new TransportDescriptor();
                transport.setProtocol(rs.getString("protocol"));
                transport.setSecurity(rs.getString("security"));
                transport.setType(TransportDescriptor.TypeEnum.valueOf(rs.getString("transportType")));
                transport.setVersion(rs.getString("version"));
                transpel.setTransport(transport);
                serTransportDependencies.add(transpel);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setTransportDependencies(serTransportDependencies);

        //Set TrafficRuleDescriptor
        List<TrafficRuleDescriptor> appTrafficRule = new ArrayList();
        List<Long> traffruleid = new ArrayList();
        //retrieve traffic rules
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select trafficRuleId,filterType,priority,action from "
                    + "apptrafficrule where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TrafficRuleDescriptor traffrule = new TrafficRuleDescriptor();
                long servreqel = rs.getLong("trafficRuleId");
                traffrule.setFilterType(TrafficRuleDescriptor.FilterTypeEnum.fromValue(rs.getString("filterType")));
                traffrule.setTrafficRuleId(rs.getString("trafficRuleId"));
                traffrule.setAction(ActionEnum.fromValue(rs.getString("action")));
                traffrule.setPriority(new BigDecimal(rs.getString("trafficRuleId")));
                appTrafficRule.add(traffrule);
                traffruleid.add(servreqel);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        for (int j = 0; j < traffruleid.size(); j++) {
            //retrieve traffic filter
            List<TrafficFilter> trafficFilter = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select srcAddress,dstAddress,srcPort,dstPort,"
                        + "protocol,token,srcTunnelAddress,dstTunnelAddress,srcTunnelPort,dstTunnelPort,"
                        + "qci,dscp,tc from "
                        + "trafficfilter where trafficRuleId=?");
                ps.setLong(1, traffruleid.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TrafficFilter trafffilter = new TrafficFilter();
                    trafffilter.setDscp(new BigDecimal(rs.getString("dscp")));
                    trafffilter.setQci(new BigDecimal(rs.getString("qci")));
                    trafffilter.setTc(new BigDecimal(rs.getString("tc")));
                    List<String> strlist = new ArrayList();
                    strlist.add(rs.getString("dstAddress"));
                    trafffilter.setDstAddress(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("dstPort"));
                    trafffilter.setDstPort(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("dstTunnelAddress"));
                    trafffilter.setDstTunnelAddress(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("dstTunnelPort"));
                    trafffilter.setDstTunnelPort(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("protocol"));
                    trafffilter.setProtocol(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("token"));
                    trafffilter.setToken(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("srcAddress"));
                    trafffilter.setSrcAddress(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("srcPort"));
                    trafffilter.setSrcPort(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("srcTunnelAddress"));
                    trafffilter.setSrcTunnelAddress(strlist);
                    strlist = new ArrayList();
                    strlist.add(rs.getString("srcTunnelPort"));
                    trafffilter.setSrcTunnelPort(strlist);
                    trafficFilter.add(trafffilter);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appTrafficRule.get(j).setTrafficFilter(trafficFilter);
            //retrieve traffic interfaces
            List<InterfaceDescriptor> dstInterface = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select dstinterface.interfaceType,dstinterface.srcMACAddress,"
                        + "dstinterface.dstMACAddress,dstinterface.dstIPAddress,tunnelinfo.tunnelType,"
                        + "tunnelinfo.tunnelDstAddress,tunnelinfo.tunnelSrcAddress,tunnelinfo.tunnelSpecificData from "
                        + "dstinterface inner join  tunnelinfo on "
                        + "dstinterface.dstInterfaceId = tunnelinfo.dstInterfaceId "
                        + "where dstinterface.trafficRuleId=?");
                ps.setLong(1, traffruleid.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    InterfaceDescriptor intf = new InterfaceDescriptor();
                    TunnelInfo tunnel = new TunnelInfo();
                    tunnel.setTunnelDstAddress(rs.getString("tunnelDstAddress"));
                    tunnel.setTunnelSpecificData(rs.getString("tunnelSpecificData"));
                    tunnel.setTunnelSrcAddress(rs.getString("tunnelSrcAddress"));
                    tunnel.setTunnelType(TunnelInfo.TunnelTypeEnum.fromValue(rs.getString("tunnelType")));
                    intf.setTunnelInfo(tunnel);
                    intf.setDstIPAddress(rs.getString("dstIPAddress"));
                    intf.setDstMACAddress(rs.getString("dstMACAddress"));
                    intf.setInterfaceType(InterfaceDescriptor.InterfaceTypeEnum.fromValue(rs.getString("interfaceType")));
                    intf.setSrcMACAddress(rs.getString("srcMACAddress"));
                    dstInterface.add(intf);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            appTrafficRule.get(j).setDstInterface(dstInterface);
        }
        appdelem.setAppTrafficRule(appTrafficRule);

        //Set appDNSRule
        List<DNSRuleDescriptor> appDNSRule = new ArrayList();
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select dnsRuleId,domainName,ipAddressType,ipAddress,ttl from "
                    + "appdnsrule where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DNSRuleDescriptor dnsrule = new DNSRuleDescriptor();
                dnsrule.setDnsRuleId(String.valueOf(rs.getLong("serviceRequiredId")));
                dnsrule.setDomainName(rs.getString("domainName"));
                dnsrule.setIpAddress(rs.getString("ipAddress"));
                dnsrule.setIpAddressType(DNSRuleDescriptor.IpAddressTypeEnum.fromValue(rs.getString("ipAddressType")));
                dnsrule.setTtl(new BigDecimal(rs.getString("ttl")));
                appDNSRule.add(dnsrule);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setAppDNSRule(appDNSRule);

        //Set LatencyDescriptor
        LatencyDescriptor latency = new LatencyDescriptor();
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select timeUnit,latency from "
                    + "applatency where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                latency.setTimeUnit(rs.getString("timeUnit"));
                latency.setLatency(new BigDecimal(rs.getString("latency")));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setAppLatency(latency);

        //Set Terminate App Instance OpConfig
        TerminateAppInstanceOpConfig termopconf = new TerminateAppInstanceOpConfig();
        //retrieve TerminateAppInstanceOpConfig
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select minGracefulTerminationTimeout,macRecommendedGracefulTerminationTimeout from "
                    + "terminateappinstanceopconfig where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                termopconf.setMacRecommendedGracefulTerminationTimeout(new BigDecimal(rs.getString("macRecommendedGracefulTerminationTimeout")));
                termopconf.setMinGracefulTerminationTimeout(new BigDecimal(rs.getString("minGracefulTerminationTimeout")));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setTerminateAppInstanceOpConfig(termopconf);

        //Set Change App Instance StateOpConfig
        ChangeAppInstanceStateOpConfig changeopconf = new ChangeAppInstanceStateOpConfig();
        //retrieve ChangeAppInstanceOpConfig
        try {
            java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select minGracefulTerminationTimeout,macRecommendedGracefulTerminationTimeout from "
                    + "changeappinstancestateopconfig where appDId=?");
            ps.setLong(1, appdval);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                changeopconf.setMacRecommendedGracefulStopTimeout(new BigDecimal(rs.getString("macRecommendedGracefulTerminationTimeout")));
                changeopconf.setMinGracefulStopTimeout(new BigDecimal(rs.getString("minGracefulTerminationTimeout")));
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        appdelem.setChangeAppInstanceStateOpConfig(changeopconf);

        AdvertiseAppDIdReply appdidelem = new AdvertiseAppDIdReply(appdreq.getReqid(), appdelem);
        System.out.println("DatabaseDriver --->  Post AdvertiseAppDIdReply Event");
        SingletonEventBus.getBus().post(appdidelem);
    }

    @Subscribe
    public void handle_NfviPopAbstractionRequest(NfviPopAbstractionRequest nfvipopreq) {
        System.out.println("DatabaseDriver ---> Handle NfviPopAbstractionRequest Event");
        System.out.println("field event: (reqid,filter) = ("
                + nfvipopreq.getReqId() + "," + nfvipopreq.getFilter() + ")");
        NfviPopAbstractionReply nfvipoprep = new NfviPopAbstractionReply(nfvipopreq.getReqId(), nfvipopreq.getComputeFlag(), nfvipopreq.getNetworkFlag());

        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(rs.getString("abstrNfviPopId"));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("vimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));

                System.out.println("nfviPop.abstrNfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("nfviPop.VimId: " + nfviPop.getVimId() + "");
                System.out.println("nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                nfvipoprep.addnfvipop(nfviPop);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("DatabaseDriver --->  Post NfviPopAbstractionReply Event");
        SingletonEventBus.getBus().post(nfvipoprep);

        /*
        //Now add two fixed entries
        NfviPopAbstractionReply nfvipoprep = new NfviPopAbstractionReply(nfvipopreq.getReqId(), nfvipopreq.getComputeFlag(), nfvipopreq.getNetworkFlag());
        NfviPop nfvipopel = new NfviPop();
        nfvipopel.geographicalLocationInfo("Spain");
        nfvipopel.nfviPopId("1");
        nfvipopel.networkConnectivityEndpoint("10.11.12.13");
        nfvipopel.vimId("11");
        nfvipoprep.addnfvipop(nfvipopel);
        nfvipopel = new NfviPop();
        nfvipopel.geographicalLocationInfo("Italy");
        nfvipopel.nfviPopId("2");
        nfvipopel.networkConnectivityEndpoint("20.21.22.23");
        nfvipopel.vimId("21");
        nfvipoprep.addnfvipop(nfvipopel);
 
        System.out.println("DatabaseDriver --->  Post NfviPopAbstractionReply Event");
        SingletonEventBus.getBus().post(nfvipoprep);
         */
    }

    @Subscribe
    public void handle_NetworkCapacityRequest(NetworkCapacityRequest capreq) {
        System.out.println("DatabaseDriver ---> Handle NetworkCapacityRequest Event");
        System.out.println("field event: (reqid) = ("
                + capreq.getReqId() + ")");
        //TODO: retrieve E2E abstraction info via DB query using JDBC driver
        //Now add fixed entries
        NetworkCapacityReply caprep = new NetworkCapacityReply(capreq.getReqId());
        CapacityInformation cap = new CapacityInformation();
        cap.setAllocatedCapacity("100000000");
        cap.setAvailableCapacity("900000000");
        cap.setReservedCapacity("0");
        cap.setTotalCapacity("1000000000");
        caprep.setCapacityInfo(cap);
        System.out.println("DatabaseDriver --->  Post NetworkCapacityReply Event");
        SingletonEventBus.getBus().post(capreq);
    }

    @Subscribe
    public void handle_NetworkResourceInformationRequest(NetworkResourceInformationRequest resinforeq) {
        System.out.println("DatabaseDriver ---> Handle NetworkResourceInformationRequest Event");
        System.out.println("field event: (reqid,filter) = ("
                + resinforeq.getReqId() + "," + resinforeq.getFilter() + ")");
        //TODO: retrieve E2E abstraction info via DB query using JDBC driver
        //Now add two fixed entries
        NetworkResourceInformationReply resinforep = new NetworkResourceInformationReply(resinforeq.getReqId());
        VirtualNetworkResourceInformation netres = new VirtualNetworkResourceInformation();
        AllocateNetworkResultNetworkDataNetworkQoS qos = new AllocateNetworkResultNetworkDataNetworkQoS();
        qos.setQosName("delay");
        qos.setQosValue("10");
        netres.setNetworkType("vxlan");
        netres.setNetworkResourceTypeId("1");
        List<AllocateNetworkResultNetworkDataNetworkQoS> qoslist = new ArrayList();
        qoslist.add(qos);
        netres.setNetworkQoS(qoslist);
        netres.setBandwidth(BigDecimal.valueOf(Long.valueOf("1250000")));
        resinforep.addnetresinfo(netres);

        netres = new VirtualNetworkResourceInformation();
        qos = new AllocateNetworkResultNetworkDataNetworkQoS();
        qoslist = new ArrayList();
        qos.setQosName("delay");
        qos.setQosValue("5");
        qoslist.add(qos);
        netres.setNetworkType("gre");
        netres.setNetworkResourceTypeId("2");
        netres.setNetworkQoS(qoslist);
        netres.setBandwidth(BigDecimal.valueOf(Long.valueOf("750000")));
        resinforep.addnetresinfo(netres);

        System.out.println("DatabaseDriver --->  Post NetworkResourceInformationReply Event");
        SingletonEventBus.getBus().post(resinforep);
    }

    @Subscribe
    public void handle_ComputeResourceZoneRequest(ComputeResourceZoneRequest zonereq) {
        System.out.println("DatabaseDriver ---> Handle ComputeResourceZoneRequest Event");
        System.out.println("field event: (reqid,filter) = ("
                + zonereq.getReqId() + "," + zonereq.getFilter() + ")");
        //TODO: retrieve E2E abstraction info via DB query using JDBC driver
        //Now add two fixed entries
        ComputeResourceZoneReply zonerep = new ComputeResourceZoneReply(zonereq.getReqId());
        ResourceZone zonel = new ResourceZone();

        zonel.setNfviPopId("11");
        zonel.setZoneId("1");
        zonel.setZoneName("Madrid");
        //zonel.setZoneProperty(zoneProperty);
        zonel.setZoneState("enabled");
        zonerep.addnetresinfo(zonel);
        zonel = new ResourceZone();
        zonel.setNfviPopId("11");
        zonel.setZoneId("2");
        zonel.setZoneName("Leganes");
        //zonel.setZoneProperty(zoneProperty);
        zonel.setZoneState("enabled");
        zonerep.addnetresinfo(zonel);
        System.out.println("DatabaseDriver --->  Post ComputeResourceZoneReply Event");
        SingletonEventBus.getBus().post(zonerep);
    }

    @Subscribe
    public void handle_ComputeResourceInformationRequest(ComputeResourceInformationRequest capreq) {
        System.out.println("DatabaseDriver ---> Handle ComputeResourceInformationRequest Event");
        System.out.println("field event: (reqid,filter) = ("
                + capreq.getReqId() + "," + capreq.getFilter() + ")");
        //TODO: retrieve E2E abstraction info via DB query using JDBC driver
        //Now add two fixed entries
        ComputeResourceInformationReply caprep = new ComputeResourceInformationReply(capreq.getReqId());

        VirtualComputeResourceInformation infoel = new VirtualComputeResourceInformation();
        VirtualComputeResourceInformationVirtualCPU cpuel = new VirtualComputeResourceInformationVirtualCPU();
        VirtualComputeResourceInformationVirtualMemory memel = new VirtualComputeResourceInformationVirtualMemory();
        double clock = 123.456;
        double numcpu = 2000;
        double memsize = 100.04;
        cpuel.setCpuArchitecture("x86_64");
        cpuel.setCpuClock(BigDecimal.valueOf(clock));
        cpuel.setNumVirtualCpu(BigDecimal.valueOf(numcpu));
        cpuel.setVirtualCpuPinningSupported(false);
        memel.setNumaSupported(true);
        memel.setVirtualMemSize(BigDecimal.valueOf(memsize));
        infoel.setComputeResourceTypeId("21");
        infoel.setVirtualCPU(cpuel);
        infoel.setVirtualMemory(memel);
        caprep.addnetresinfo(infoel);
        infoel = new VirtualComputeResourceInformation();
        cpuel = new VirtualComputeResourceInformationVirtualCPU();
        memel = new VirtualComputeResourceInformationVirtualMemory();
        clock = 345.897;
        numcpu = 1000;
        memsize = 200.90;
        cpuel.setCpuArchitecture("arm");
        cpuel.setCpuClock(BigDecimal.valueOf(clock));
        cpuel.setNumVirtualCpu(BigDecimal.valueOf(numcpu));
        cpuel.setVirtualCpuPinningSupported(true);
        caprep.addnetresinfo(infoel);
        memel.setNumaSupported(false);
        memel.setVirtualMemSize(BigDecimal.valueOf(memsize));
        infoel.setComputeResourceTypeId("22");
        infoel.setVirtualCPU(cpuel);
        infoel.setVirtualMemory(memel);
        caprep.addnetresinfo(infoel);
        System.out.println("DatabaseDriver --->  Post ComputeResourceInformationReply Event");
        SingletonEventBus.getBus().post(caprep);
    }

    @Subscribe
    public void handle_ComputeCapacityRequest(ComputeCapacityRequest capreq) {
        System.out.println("DatabaseDriver ---> Handle ComputeCapacityRequest Event");
        System.out.println("field event: (reqid) = ("
                + capreq.getReqId() + ")");
        //TODO: retrieve E2E abstraction info via DB query using JDBC driver
        //Now add two fixed entries
        ComputeCapacityReply caprep = new ComputeCapacityReply(capreq.getReqId());
        CapacityInformation cap = new CapacityInformation();
        cap.setAllocatedCapacity("100");
        cap.setAvailableCapacity("300");
        cap.setReservedCapacity("200");
        cap.setTotalCapacity("600");
        caprep.setCapacityInfo(cap);
        System.out.println("DatabaseDriver --->  Post ComputeCapacityReply Event");
        SingletonEventBus.getBus().post(capreq);
    }

    //////////////////Allocate Event Handlers///////////////////////////////
    @Subscribe
    public void handle_IntraPoPAllocateDbRequest(IntraPoPAllocateDbRequest dbquery) {
        System.out.println("DatabaseDriver --->  Handle IntraPoPAllocateDbRequest Event");

        // START - Insert servID into Service table
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
            ps.setString(1, dbquery.getServid());

            ps.executeUpdate();
            ps.close();

        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(ResourceSelectionLogic.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("handle_NetworkAllocateDBQuery ---> SQLIntegrityConstraintViolationException: ServiceId is already in the DB");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //retrieve domid from abstractnfvipopid 
        long domid = -1;
        System.out.println("DatabaseDriver.handle_IntraPoPAllocateDbRequest ---> AbstrNfviPopId: " + dbquery.getNfvipopid() + "");
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId from nfvipop where abstrNfviPopId=?");
            ps.setLong(1, dbquery.getNfvipopid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                domid = rs.getLong("domId");
                // vimlist.add(elementCount,vimElem);
                //nfviPopIdList.add(elementCount,rs.getString("NfviPopId"));

                System.out.println("DatabaseDriver.handle_IntraPoPAllocateDbRequest ---> domid: " + rs.getString("domId") + "");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //Allocate IntraPoPAllocateVIMRequest
        IntraPoPAllocateVIMRequest intrapopreq = new IntraPoPAllocateVIMRequest(dbquery.getReqid(), dbquery.getServid(),
                dbquery.getNfvipopid(), domid, dbquery.getIntrapopreq());
        System.out.println("DatabaseDriver --->  Post IntraPoPAllocateVIMRequest Event");
        SingletonEventBus.getBus().post(intrapopreq);

    }

    @Subscribe
    public void handle_IntraPoPAllocateVIMReply(IntraPoPAllocateVIMReply dbquery) {
        System.out.println("DatabaseDriver --->  Handle IntraPoPAllocateVIMReply Event");

        //Insert new intrapop service reply with the service id. Check E2ENetworkAllocate
        long networkServId = -1;
        long virtualnetworkId = -1;
        String providernet = "";
        String vlanid = "";

        //select if vn is a provider network
//        int size = dbquery.getIntrapopreq().getMetadata().size();
//        for (int i = 0; i < size; i++) {
//            if (dbquery.getIntrapopreq().getMetadata().get(i).getKey().compareTo("ip-floating-required") == 0) {
//                providernet = dbquery.getIntrapopreq().getMetadata().get(i).getValue();
//                break;
//            }
//        }
        if ((dbquery.getIntrapopreq().getNetworkResourceType().compareTo("subnet-vlan") == 0)) {
            providernet = "True";
            int size = dbquery.getIntrapopreq().getTypeSubnetData().getMetadata().size();
            for (int i = 0; i < size; i++) {
                if (dbquery.getIntrapopreq().getTypeSubnetData().getMetadata().get(i).getKey().compareTo("interpop_vlan") == 0) {
                    vlanid = dbquery.getIntrapopreq().getTypeSubnetData().getMetadata().get(i).getValue();
                    break;
                }
            }
            //insert vlanid in response as segmentationID
            MetaDataInner vlan_el = new MetaDataInner();
            vlan_el.setKey("SegmentationID");
            vlan_el.setValue(vlanid);
            dbquery.getIntrapoprep().getSubnetData().getMetadata().add(vlan_el);
        } else if ((dbquery.getIntrapopreq().getNetworkResourceType().compareTo("subnet-vxlan") == 0)) {
            providernet = "True";
            //store floating Ip in segment type
            int size = dbquery.getIntrapoprep().getNetworkPortData().getMetadata().size();
            for (int i = 0; i < size; i++) {
                if (dbquery.getIntrapoprep().getNetworkPortData().getMetadata().get(i).getKey().compareTo("floating_ip") == 0) {
                    vlanid = dbquery.getIntrapoprep().getNetworkPortData().getMetadata().get(i).getValue();
                    break;
                }
            }
            //insert vlanid in response as segmentationID
            MetaDataInner vlan_el = new MetaDataInner();
            vlan_el.setKey("SegmentationID");
            vlan_el.setValue(vlanid);
            dbquery.getIntrapoprep().getSubnetData().getMetadata().add(vlan_el);
        }

        // Insert record in networkservices table//
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservices "
                    + "(reqId,"
                    + "status,"
                    + "servId,"
                    + "provider)"
                    + "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, dbquery.getReqid());
            ps.setString(2, "enabled");
            ps.setString(3, dbquery.getServid());
            ps.setString(4, providernet);
            //ps.setLong(4, Long.valueOf(-1));
            //ps.setString(5,null);

            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> NetworkService instance inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                networkServId = rs.getLong(1);
                System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> netServId:" + networkServId + "");
            }

            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //END - Insert 
        //Insert network request service data
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

            // Insert  data into computeservicerequestdata table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservicerequestdata\n"
                    + "(locationConstraints,"
                    + "reservationId,"
                    + "affinityOrAntiAffinityConstraints,"
                    + "resourceGroupId,"
                    + "networkResourceType,"
                    + "networkResourceName,"
                    + "netServId)"
                    + "VALUES (?,?,?,?,?,?,?)");

            ps.setString(1, dbquery.getIntrapopreq().getLocationConstraints());
            ps.setString(2, dbquery.getIntrapopreq().getReservationId());
            ps.setString(3, dbquery.getIntrapopreq().getAffinityOrAntiAffinityConstraints());
            ps.setString(4, dbquery.getIntrapopreq().getResourceGroupId());
            ps.setString(5, dbquery.getIntrapopreq().getNetworkResourceType());
            ps.setString(6, dbquery.getIntrapopreq().getNetworkResourceName());
            ps.setLong(7, networkServId);
            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> AllocateNetworkRequest data inserted into DB");
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //END - Insert 

        //Insert network response in virtual network
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO mtpservdb.virtualnetwork\n"
                    + "(networkResourceName,"
                    + "segmentType,"
                    + "isShared,"
                    + "zoneId,"
                    + "networkResourceId,"
                    + "networkType,"
                    + "operationalState,"
                    + "sharingCriteria,"
                    + "bandwidth,"
                    + "nfviPopId,"
                    + "netServId,"
                    + "netResIdRef)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, dbquery.getIntrapoprep().getNetworkData().getNetworkResourceName());
            ps.setString(2, vlanid); //dbquery.getIntrapoprep().getNetworkData().getSegmentType()
            //ps.setBoolean(3, dbquery.getIntrapoprep().getNetworkData().isIsShared());
            if (dbquery.getIntrapoprep().getNetworkData().isIsShared() == null) {
                ps.setBoolean(3, false);
            } else {
                ps.setBoolean(3, dbquery.getIntrapoprep().getNetworkData().isIsShared());
            }

            ps.setString(4, dbquery.getIntrapoprep().getNetworkData().getZoneId());
            ps.setString(5, dbquery.getIntrapoprep().getNetworkData().getNetworkResourceId());
            ps.setString(6, dbquery.getIntrapoprep().getNetworkData().getNetworkType());
            ps.setString(7, dbquery.getIntrapoprep().getNetworkData().getOperationalState());
            ps.setString(8, dbquery.getIntrapoprep().getNetworkData().getSharingCriteria());
            ps.setBigDecimal(9, dbquery.getIntrapoprep().getNetworkData().getBandwidth());
//            ps.setString(1, "");
//            ps.setString(2, "");
//            ps.setBoolean(3, false);
//            ps.setInt(4, 0);
//            ps.setString(5, dbquery.getIntrapoprep().getSubnetData().getResourceId());
//            ps.setString(6, "");
//            ps.setString(7, "");
//            ps.setString(8, "");
//            ps.setBigDecimal(9, new BigDecimal(0));

            ps.setLong(10, dbquery.getNfvipopid());
            ps.setLong(11, networkServId);
            ps.setLong(12, Long.valueOf(0));

            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> VirtualNetwork instance inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                virtualnetworkId = rs.getLong(1);
                System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> netServId:" + networkServId + "");
            }
            rs.close();
            ps.close();
            //Insert entries in NETWORK_SUBSET table
            // TO DO - Fix this part after the VirtualNetwork class is compliant with IFA005 
            if (dbquery.getIntrapoprep().getSubnetData() != null) {
                PreparedStatement ps_subnet = conn.prepareStatement("INSERT INTO networksubnet"
                        + "(resourceId,ipVersion,"
                        + "gatewayIp,cidr,isDhcpEnabled,"
                        + "addressPool,"
                        + "virtualNetworkId) VALUES(?,?,?,?,?,?,?)");

                ps_subnet.setString(1, dbquery.getIntrapoprep().getSubnetData().getResourceId());
                ps_subnet.setString(2, dbquery.getIntrapoprep().getSubnetData().getIpVersion());
                ps_subnet.setString(3, dbquery.getIntrapoprep().getSubnetData().getGatewayIp());
                ps_subnet.setString(4, dbquery.getIntrapoprep().getSubnetData().getCidr());
                ps_subnet.setBoolean(5, dbquery.getIntrapoprep().getSubnetData().isIsDhcpEnabled());
                String AddressPool = "";

                for (int i = 0; i < dbquery.getIntrapoprep().getSubnetData().getAddressPool().size(); i++) {
                    AddressPool = AddressPool + ";" + dbquery.getIntrapoprep().getSubnetData().getAddressPool().get(i);
                }
                ps_subnet.setString(6, AddressPool);
                ps_subnet.setLong(7, virtualnetworkId);
                ps_subnet.executeUpdate();
                System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> Subnet entry inserted into DB");
                ps_subnet.close();
            }
//Insert entries in VIRTUAL_NETWORK_PORT table
            //TO DO - Complete after VIRTUAL_NETWORK_PORT class is fixed (related to IFA005) 
            if (dbquery.getIntrapoprep().getNetworkPortData() != null) {

                PreparedStatement ps_port = conn.prepareStatement("INSERT INTO virtualnetworkport "
                        + "(resourceId, portType,attachedResourceId,segmentId,bandwidth,virtualNetworkId)"
                        + "VALUES(?,?,?,?,?,?,?)");

                ps_port.setString(1, dbquery.getIntrapoprep().getNetworkPortData().getResourceId());
                ps_port.setString(2, dbquery.getIntrapoprep().getNetworkPortData().getPortType());
                ps_port.setString(3, dbquery.getIntrapoprep().getNetworkPortData().getAttachedResourceId());
                ps_port.setString(4, dbquery.getIntrapoprep().getNetworkPortData().getSegmentId());
                ps_port.setBigDecimal(5, dbquery.getIntrapoprep().getNetworkPortData().getBandwidth());
                ps_port.setLong(6, virtualnetworkId);
                ps_port.executeUpdate();
                System.out.println("DatabaseDriver.handle_IntraPoPAllocateVIMReply ---> VirtualNetworkPort entry inserted into DB");
                ps_port.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //Set intraPoPReq with the resource id
        if (dbquery.getIntrapoprep().getSubnetData() != null) {
            dbquery.getIntrapoprep().getSubnetData().setNetworkId(String.valueOf(networkServId));
        }

        if (dbquery.getIntrapoprep().getNetworkData() != null) {
            dbquery.getIntrapoprep().getNetworkData().setNetworkResourceId(String.valueOf(networkServId));

        }

        //Allocate IntraPoPAllocateVIMRequest
        IntraPoPAllocateDbReply intrapopreq = new IntraPoPAllocateDbReply(dbquery.getReqid(), dbquery.getServid(),
                dbquery.getNfvipopid(), dbquery.getIntrapoprep());
        System.out.println("DatabaseDriver --->  Post IntraPoPAllocateDbReply Event");
        SingletonEventBus.getBus().post(intrapopreq);
    }

    @Subscribe
    public void handle_ComputeAllocateDBQuery(ComputeAllocateDBQuery dbquery) {
        System.out.println("DatabaseDriver --->  Handle ComputeAllocateDBQuery Event");

        String abstrNfviPopId = null;
// START - Insert servID into Service table
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
            ps.setString(1, dbquery.getServid());

            ps.executeUpdate();
            ps.close();

        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(ResourceSelectionLogic.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("DatabaseDriver.handle_ComputeAllocateDBQuery ---> SQLIntegrityConstraintViolationException: ServiceId is already in the DB");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
// END - Insert

        List<MetaDataInner> metadata = dbquery.getComputereq().getMetadata();
        for (int i = 0; i < metadata.size(); i++) {
            if (metadata.get(i).getKey().compareTo("AbstractNfviPoPId") == 0) {
                abstrNfviPopId = metadata.get(i).getValue();
            }
        }

        //START - Retrieve from DB the list of nfviPopId and domId related to the abstrNfviPopId provided in the AllocateComputeRequest. 
        ArrayList<Long> vimlist = new ArrayList();
        ArrayList<Long> domlist = new ArrayList();
        System.out.println("DatabaseDriver.handle_ComputeAllocateDBQuery ---> AbstrNfviPopId: " + abstrNfviPopId + "");
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select nfviPopId, domId from nfvipop where abstrNfviPopId=?");
            ps.setString(1, abstrNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vimlist.add(rs.getLong("nfviPopId"));
                domlist.add(rs.getLong("domId"));
                // vimlist.add(elementCount,vimElem);
                //nfviPopIdList.add(elementCount,rs.getString("NfviPopId"));

                System.out.println("DatabaseDriver.handle_ComputeAllocateDBQuery ---> NfviPopId: " + rs.getString("NfviPopId") + "");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        ComputeAllocateDBQueryReply dbreply = new ComputeAllocateDBQueryReply(dbquery.getReqid(), dbquery.getServid(), domlist, vimlist);
        System.out.println("DatabaseDriver --->  Post ComputeAllocateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_ComputeAllocateDBQueryOutcome(ComputeAllocateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver --->  Handle ComputeAllocateDBQueryOutcome Event");
        //TODO update DB using JDBC raising exception if something is wrong

        if (dboutcome.isOutcome() == false) {
            //send API reply with void virtualcompute Instance

            E2EComputeAllocateInstanceOutcome allneginst = new E2EComputeAllocateInstanceOutcome(dboutcome.getReqid(),
                    dboutcome.getServid(), null, dboutcome.isOutcome());
            System.out.println("DatabaseDriver --> Post E2EComputeAllocateInstanceOutcome  Negative outcome Event");
            SingletonEventBus.getBus().post(allneginst);
            return;
        }
// START - INSERT ENTRY in ComputeServices Table
        Long computeServId = null;
        Long numVirtualCpu = null;
        Long storageSize = null;
        Long memorySize = null;
        String zoneId = null;
        //Long virtualcomputeId = null;

        if (dboutcome.isOutcome()) {

            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO computeservices (reqId,status,servId,nfviPopId) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setLong(1, dboutcome.getReqid());
                ps.setString(2, "enabled");
                ps.setString(3, dboutcome.getServid());
                ps.setLong(4, dboutcome.getNfvipopid());
                //ps.setString(5,null);

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> ComputeService instance inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    computeServId = rs.getLong(1);
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> computeServId:" + computeServId + "");
                }

                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

//END - Insert       
//        
            //START - INSERT AllocateComputeRequest into DB
            AllocateComputeRequest allocComputeReq = dboutcome.getComputerequest();
            String vnfid = "";
            
            List<MetaDataInner> metadata = allocComputeReq.getMetadata();
            for (int i = 0; i < metadata.size(); i++) {
                if (metadata.get(i).getKey().compareTo("VnfId") == 0) {
                    vnfid = metadata.get(i).getValue();
                }
            }

            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                // Insert  data into computeservicerequestdata table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO computeservicerequestdata (locationConstraints, reservationId, computeFlavourId,resourceGroupId, metadata,vcImageId,computeName,vnfid,computeServiceId) VALUES(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, allocComputeReq.getLocationConstraints());
                ps.setString(2, allocComputeReq.getReservationId());
                ps.setString(3, allocComputeReq.getComputeFlavourId());
                ps.setString(4, allocComputeReq.getResourceGroupId());
                ps.setObject(5, ""); //FABIO: metadata is an array not more a string
                ps.setString(6, allocComputeReq.getVcImageId());
                ps.setString(7, allocComputeReq.getComputeName());
                ps.setString(8, vnfid);
                ps.setLong(9, computeServId);

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> AllocateComputeRequest data inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            // Insert  data into virtualinterfacedata table
            List<AllocateComputeRequestInterfaceData> interfacelist = allocComputeReq.getInterfaceData();
            long size = interfacelist.size();
            for (int i = 0; i < size; i++) {

                AllocateComputeRequestInterfaceData interfacedata = interfacelist.get(i);
                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps_interfacedata = conn.prepareStatement("INSERT INTO  virtualinterfacedata "
                            + "(ipAddress, "
                            + "macAddress, "
                            + "computeServiceId) "
                            + "VALUES(?,?,?)");
                    ps_interfacedata.setString(1, interfacedata.getIpAddress());
                    ps_interfacedata.setString(2, interfacedata.getMacAddress());
                    ps_interfacedata.setLong(3, computeServId);
                    ps_interfacedata.executeUpdate();
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> VirtualInterfaceData record inserted into DB");
                    ps_interfacedata.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }

            // Insert  data into affinityorantiaffinity table
            List<AllocateComputeRequestAffinityOrAntiAffinityConstraints> constraintList = allocComputeReq.getAffinityOrAntiAffinityConstraints();
            size = constraintList.size();
            for (int i = 0; i < size; i++) {
                AllocateComputeRequestAffinityOrAntiAffinityConstraints constraint = constraintList.remove(0);
                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps_affinityConstraint = conn.prepareStatement("INSERT INTO affinityorantiaffinityconstraint"
                            + "(type,"
                            + "scope,"
                            + "affinityAntiAffinityResourceList,"
                            + "affinityAntiAffinityResourceGroup,"
                            + "computeServiceId) "
                            + "VALUES(?,?,?,?,?)");
                    ps_affinityConstraint.setString(1, constraint.getType());
                    ps_affinityConstraint.setString(2, constraint.getScope());
                    ps_affinityConstraint.setString(3, constraint.getAffinityAntiAffinityResourceList().getResource().get(0));
                    ps_affinityConstraint.setString(4, constraint.getAffinityAntiAffinityResourceGroup());
                    ps_affinityConstraint.setLong(5, computeServId);
                    ps_affinityConstraint.executeUpdate();
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> AffinityConstraints record inserted into DB");
                    ps_affinityConstraint.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }

            // Insert  data into userdata table
            AllocateComputeRequestUserData userData = allocComputeReq.getUserData();
            if (userData != null) {
                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps_userdata = conn.prepareStatement("INSERT INTO userdata "
                            + "(content,"
                            + "method,"
                            + "computeServiceId) "
                            + "VALUES(?,?,?)");
                    ps_userdata.setString(1, userData.getContent());
                    ps_userdata.setString(2, userData.getMethod());
                    ps_userdata.setLong(3, computeServId);

                    ps_userdata.executeUpdate();
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Userdata record inserted into DB");
                    ps_userdata.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }

//END - Insert
// START - Insert record in virtualcompute table
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                //Note that virtualComputeId is auto_generated. It must replace the computeId provided by the domain in the REST response. 
                PreparedStatement ps = conn.prepareStatement("INSERT INTO virtualcompute "
                        + "(computeId,"
                        + "zoneId,"
                        + "virtualDisks,"
                        + "vcImageId,"
                        + "flavourId,"
                        + "computeServiceId,"
                        + "computeName,"
                        + "operationalState,"
                        + "hostId) "
                        + "VALUES(?,?,?,?,?,?,?,?,?)");

                ps.setString(1, dboutcome.getComputereply().getComputeId());
                ps.setString(2, dboutcome.getComputereply().getZoneId());
                ps.setString(3, dboutcome.getComputereply().getVirtualDisks());
                ps.setString(4, dboutcome.getComputereply().getVcImageId());
                ps.setString(5, dboutcome.getComputereply().getFlavourId());
                ps.setLong(6, computeServId);
                ps.setString(7, dboutcome.getComputereply().getComputeName());
                ps.setString(8, dboutcome.getComputereply().getOperationalState());
                ps.setString(9, dboutcome.getComputereply().getHostId());
                //FIX the Exception
                // ps.setObject(10,dboutcome.getVIMElem().getAccelerationCapability());
                ps.executeUpdate();

                //ResultSet rs = ps.getGeneratedKeys();
//                if (rs != null && rs.next()) {
//                    virtualcomputeId = rs.getLong(1);
//                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> computeServId:" + computeServId + "");
//                }
                //virtualcomputeId = computeServId;
                //rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            // START - Insert record in VirtualCPU table 
            VirtualComputeVirtualCpu virtualCpu = dboutcome.getComputereply().getVirtualCpu();
            if (virtualCpu != null) {
                try {

                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps_virtualCpu = conn.prepareStatement("INSERT INTO virtualcpu "
                            + "(cpuArchitecture,"
                            + "numVirtualCpu,"
                            + "cpuClock,"
                            + "virtualCpuOversubscriptionPolicy, "
                            + "computeServiceId) "
                            + "VALUES(?,?,?,?,?)");

                    ps_virtualCpu.setString(1, virtualCpu.getCpuArchitecture());
                    ps_virtualCpu.setInt(2, virtualCpu.getNumVirtualCpu());
                    ps_virtualCpu.setBigDecimal(3, virtualCpu.getCpuClock());
                    ps_virtualCpu.setString(4, virtualCpu.getVirtualCpuOversubscriptionPolicy());
                    ps_virtualCpu.setLong(5, computeServId);

                    ps_virtualCpu.executeUpdate();
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> VirtualCpu entry inserted into DB");
                    ps_virtualCpu.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            // START - Insert record in VirtualCpuPinning table 
            CreateComputeResourceReservationRequestContainerFlavourVirtualCpuPinning pinning = dboutcome.getComputereply().getVirtualCpu().getVirtualCpuPinning();
            if (pinning != null) {
                if (pinning.getCpuPinningRules() != null && pinning.getCpuMap() != null && pinning.getCpuPinningPolicy() != null) {
                    try {
                        java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                        PreparedStatement ps_pinning = conn.prepareStatement("INSERT INTO virtualcpupinning "
                                + "(cpuPinningPolicy,"
                                + "cpuPinningRules,"
                                + "cpuMap,"
                                + "computeServiceId) "
                                + "VALUES(?,?,?,?)");

                        ps_pinning.setString(1, pinning.getCpuPinningPolicy());
                        ps_pinning.setString(2, pinning.getCpuPinningRules());
                        ps_pinning.setString(3, pinning.getCpuMap());
                        ps_pinning.setLong(4, computeServId);

//ps_virtualCpu.setString(5, virtualCpu.getVirtualCpuPinning());
                        ps_pinning.executeUpdate();
                        System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> VirtualCpuPinning entry inserted into DB");
                        ps_pinning.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseDriver.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }
            }

            // START - Insert record in VirtualNetworkInterface table
            List<ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface> networkInterfaceList = dboutcome.getComputereply().getVirtualNetworkInterface();

            if (networkInterfaceList != null) {
                size = networkInterfaceList.size();
                for (int i = 0; i < size; i++) {
                    ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface networkInterface = networkInterfaceList.get(i);
                    try {
                        java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                        PreparedStatement ps_networkInterface = conn.prepareStatement("INSERT INTO virtualnetworkinterface (resourceId,ownerId,networkId,networkPortId,ipAddress,typeVirtualNic,typeConfiguration,macAddress,bandwidth,accelerationCapability,operationalState,metadata,ComputeServiceId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps_networkInterface.setString(1, networkInterface.getResourceId());
                        ps_networkInterface.setString(2, networkInterface.getOwnerId());
                        ps_networkInterface.setString(3, networkInterface.getNetworkId());
                        ps_networkInterface.setString(4, networkInterface.getNetworkPortId());
                        ps_networkInterface.setString(5, networkInterface.getIpAddress().get(0));
                        ps_networkInterface.setString(6, networkInterface.getTypeVirtualNic());
                        ps_networkInterface.setString(7, networkInterface.getTypeConfiguration());
                        ps_networkInterface.setString(8, networkInterface.getMacAddress());
                        ps_networkInterface.setString(9, networkInterface.getBandwidth());
                        ps_networkInterface.setString(10, "");
                        ps_networkInterface.setString(11, networkInterface.getOperationalState());
                        ps_networkInterface.setString(12, "");
                        ps_networkInterface.setLong(13, computeServId);

                        ps_networkInterface.executeUpdate();
                        System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> VirtualNetworkInterface entry inserted into DB");
                        ps_networkInterface.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseDriver.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }

            }

            // START - Insert record in VirtualMemory table 
            VirtualComputeVirtualMemory memory = dboutcome.getComputereply().getVirtualMemory();

            if (memory != null) {
                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps_memory = conn.prepareStatement("INSERT INTO virtualmemory "
                            + "(virtualMemSize,"
                            + "virtualMemOversubscriptionPolicy,"
                            + "numaEnabled,"
                            + "computeServiceId) VALUES(?,?,?,?)");
                    ps_memory.setBigDecimal(1, memory.getVirtualMemSize());
                    ps_memory.setString(2, memory.getVirtualMemOversubscriptionPolicy());
                    ps_memory.setBoolean(3, memory.isNumaEnabled());
                    ps_memory.setLong(4, computeServId);

                    ps_memory.executeUpdate();
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> VirtualMemory entry inserted into DB");
                    ps_memory.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            // END - Insert
            // Retrieve from AllocateRaquest the computeFlavourId and the abstrResourceZoneId (mapped on IFA005 locationConstraints and computeFlavourId attributes, respectively)
            //NOTE that computeFlavourId and the ResourceZoneId may be obtained from VirtualCompute table
            String computeFlavourId = null;
            String abstrResourceZoneId = dboutcome.getComputereply().getZoneId();
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT computeFlavourId FROM computeflavour where flavourId=?");
                ps.setString(1, dboutcome.getComputerequest().getComputeFlavourId());
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    computeFlavourId = String.valueOf(rs.getLong("computeFlavourId"));
                    System.out.println("DatabaseDriver.computeFlavourId ---> computeFlavourId: " + numVirtualCpu + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//Amount of CPU released from the Capacity related to the couple (nfviPoP, zoneId)
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT numVirtualCpu FROM virtualcpu where computeFlavourId=?");
                ps.setString(1, computeFlavourId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    numVirtualCpu = rs.getLong("numVirtualCpu");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> numVirtualCpu: " + numVirtualCpu + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Amount of Memory released from the Capacity related to the couple (nfviPoP, zoneId)
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT virtualMemSize FROM `mtpdomdb`.`virtualmemorydata` where computeFlavourId=?");
                ps.setString(1, computeFlavourId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    memorySize = rs.getLong("virtualMemSize");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> virtualMemSize: " + memorySize + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Amount of Storage released from the Capacity related to the couple (nfviPoP, zoneId)
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT sizeOfStorage FROM virtualstoragedata where computeFlavourId=?");
                ps.setString(1, computeFlavourId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    storageSize = rs.getLong("sizeOfStorage");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> sizeOfStorage: " + storageSize + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Set the ZoneId. Assumption: 1 to 1 Mapping between ZoneId and AbstrZoneId
            zoneId = abstrResourceZoneId;

//Update memory capacity in Domain DB
            try {
                long availableMemCapacity;
                long allocatedMemCapacity;
                long currentAvailableMemCapacity;
                long currentAllocatedMemCapacity;
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                // INNER JOIN SELECT QUERY
                //PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity from memoryresources inner join zoneid on zoneid.NfviPopId=? where memoryresources.resourceZoneId=zoneid.resourceZoneId");
                //STANDARD SELECT QUERY
                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, resourceZoneId from memoryresources where memoryresources.resourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableMemCapacity = rs.getLong("availableCapacity");
                    allocatedMemCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableMemCapacity = availableMemCapacity - memorySize;
                    currentAllocatedMemCapacity = allocatedMemCapacity + memorySize;
                    rs.updateLong("availableCapacity", availableMemCapacity - memorySize);
                    rs.updateLong("allocatedCapacity", allocatedMemCapacity + memorySize);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> Memory capacity updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> Old available  memory capacity " + availableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> Current available  memory capacity " + currentAvailableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> Old allocated  memory capacity " + allocatedMemCapacity + "");
                    System.out.println("DatabaseDriver.handle__ComputeAllocateDBQueryOutcome ---> Current allocated memory capacity " + currentAllocatedMemCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update memory capacity in Abstraction DB
            try {
                long availableMemCapacity;
                long allocatedMemCapacity;
                long currentAvailableMemCapacity;
                long currentAllocatedMemCapacity;
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, abstrResourceZoneId from abstrmemoryresources where abstrResourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableMemCapacity = rs.getLong("availableCapacity");
                    allocatedMemCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableMemCapacity = availableMemCapacity - memorySize;
                    currentAllocatedMemCapacity = allocatedMemCapacity + memorySize;
                    rs.updateLong("availableCapacity", currentAvailableMemCapacity);
                    rs.updateLong("allocatedCapacity", currentAllocatedMemCapacity);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Memory capacity updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old available  memory capacity " + availableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current available  memory capacity " + currentAvailableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old allocated  memory capacity " + allocatedMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current allocated memory capacity " + currentAllocatedMemCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update storage capacity in Domain DB
            try {
                long availableStorageCapacity;
                long allocatedStorageCapacity;
                long currentAvailableStorageCapacity;
                long currentAllocatedStorageCapacity;
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, resourceZoneId from storageresources where resourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableStorageCapacity = rs.getLong("availableCapacity");
                    allocatedStorageCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableStorageCapacity = availableStorageCapacity - storageSize;
                    currentAllocatedStorageCapacity = allocatedStorageCapacity + storageSize;
                    rs.updateLong("availableCapacity", currentAvailableStorageCapacity);
                    rs.updateLong("allocatedCapacity", currentAllocatedStorageCapacity);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Memory capacity updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old available storage capacity " + availableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current available storage capacity " + currentAvailableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old allocated storage capacity " + allocatedStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current allocated storage capacity " + currentAllocatedStorageCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update storage capacity in Abstraction DB
            try {
                long availableStorageCapacity;
                long allocatedStorageCapacity;
                long currentAvailableStorageCapacity;
                long currentAllocatedStorageCapacity;
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, abstrResourceZoneId from abstrstorageresources where abstrResourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableStorageCapacity = rs.getLong("availableCapacity");
                    allocatedStorageCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableStorageCapacity = availableStorageCapacity - storageSize;
                    currentAllocatedStorageCapacity = allocatedStorageCapacity + storageSize;
                    rs.updateLong("availableCapacity", currentAvailableStorageCapacity);
                    rs.updateLong("allocatedCapacity", currentAllocatedStorageCapacity);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Memory capacity updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old available  storage capacity " + availableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current available  storage capacity " + currentAvailableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old allocated  storagecapacity " + allocatedStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current allocated storage capacity " + currentAllocatedStorageCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update CPU capacity in Domain DB
            try {
                long availableCpuCapacity;
                long allocatedCpuCapacity;
                long currentAvailableCpuCapacity;
                long currentAllocatedCpuCapacity;
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                // INNER JOIN SELECT QUERY
                //PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity from memoryresources inner join zoneid on zoneid.NfviPopId=? where memoryresources.resourceZoneId=zoneid.resourceZoneId");
                //STANDARD SELECT QUERY
                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, resourceZoneId from cpuresources where resourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableCpuCapacity = rs.getLong("availableCapacity");
                    allocatedCpuCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableCpuCapacity = availableCpuCapacity - numVirtualCpu;
                    currentAllocatedCpuCapacity = allocatedCpuCapacity + numVirtualCpu;
                    rs.updateLong("availableCapacity", availableCpuCapacity - numVirtualCpu);
                    rs.updateLong("allocatedCapacity", allocatedCpuCapacity + numVirtualCpu);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> CPU capacity updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old available cpu capacity " + availableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current available cpu capacity " + currentAvailableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old allocated cpu capacity " + allocatedCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current allocated cpu capacity " + currentAllocatedCpuCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update CPU capacity in Abstraction DB
            try {
                long availableCpuCapacity;
                long allocatedCpuCapacity;
                long currentAvailableCpuCapacity;
                long currentAllocatedCpuCapacity;
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, abstrResourceZoneId from abstrcpuresources where abstrResourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableCpuCapacity = rs.getLong("availableCapacity");
                    allocatedCpuCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableCpuCapacity = availableCpuCapacity - numVirtualCpu;
                    currentAllocatedCpuCapacity = allocatedCpuCapacity + numVirtualCpu;
                    rs.updateLong("availableCapacity", currentAvailableCpuCapacity);
                    rs.updateLong("allocatedCapacity", currentAllocatedCpuCapacity);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Memory capacity updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old available cpu capacity " + availableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current available cpu capacity " + currentAvailableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Old allocated cpu capacity " + allocatedCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> Current allocated cpu capacity " + currentAllocatedCpuCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//    // START - Update outcome attribute in computeServiceRequestData table
//          try {
//            
//    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//
//           
//            PreparedStatement ps = conn.prepareStatement("UPDATE computeservices SET outcome=? WHERE computeServiceId=?");
//            // PreparedStatement ps = conn.prepareStatement("UPDATE computeservices SET vnfName=? WHERE computeServiceId=?");
//            
//            ps.setBoolean(1,dboutcome.getOutcome());
//           //ps.setString(1,"test");
//            ps.setLong(2,dboutcome.getComputeserviceid());
//        
//            
//            ps.executeUpdate();
//
//            ps.close();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseDriver.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        // END - Update
//        
        }
        //update the virtualcompute with the virtualcomute id to provide to SO 
        dboutcome.getComputereply().setComputeId(Long.toString(computeServId));
        //Check if MEC domain should be configured
        if (dboutcome.getComputereply().getMecappID().equals("") == false) {
            //MEC domain has to create an APPD instance

            String mecdom = null;

            //Retrieve MEC domain id from VIM domain ID
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select mecAssociatedDomainID from domain where domId=?");
                ps.setLong(1, dboutcome.getDomid());
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    mecdom = rs.getString("mecAssociatedDomainID");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //retrieve data from appd using the appdid and fill with IP adrress from VirtualCompute
            MECTrafficServiceCreationRequest mecapp = new MECTrafficServiceCreationRequest();

            //Set request id
            mecapp.setRequestId(String.valueOf(dboutcome.getReqid()));
            //Set Region ID
            List<MetaDataInner> metadata = new ArrayList();
            metadata = dboutcome.getComputerequest().getMetadata();
            for (int i = 0; i < metadata.size(); i++) {
                if (metadata.get(i).getKey().compareTo("RegionId") == 0) {
                    mecapp.setRegionId(metadata.get(i).getValue());
                }
            }

            //Set appservice required
            //Retrieve App service List required from AppD DB
            List<Long> servrequiredid = new ArrayList();
            List<ServiceDependency> appServiceRequired = new ArrayList();

            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select serviceRequiredId,serName,version,requestedPermissions from "
                        + "appservicerequired where appDId=?");
                ps.setLong(1, Long.valueOf(dboutcome.getComputereply().getMecappID()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ServiceDependency servicerequire = new ServiceDependency();
                    long servreqel = rs.getLong("serviceRequiredId");
                    servicerequire.setSerName(rs.getString("serName"));
                    servicerequire.setVersion(rs.getString("version"));
                    servicerequire.setRequestedPermissions(rs.getString("requestedPermissions"));
                    appServiceRequired.add(servicerequire);
                    servrequiredid.add(servreqel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            for (int j = 0; j < servrequiredid.size(); j++) {
                //Retrieve ser Category
                CategoryRef catref = new CategoryRef();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select href,id,name,version from "
                            + "sercategory where serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        catref.setHref(rs.getString("href"));
                        catref.setId(rs.getString("id"));
                        catref.setName(rs.getString("name"));
                        catref.setVersion(rs.getString("version"));
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceRequired.get(j).setSerCategory(catref);
                //Retrieve transport dependencies
                List<TransportDependency> serTransportDependencies = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select sertransportdependencies.serializers,sertransportdependencies.labels,"
                            + "sertransport.transportType,sertransport.protocol,sertransport.version,"
                            + "sertransport.security from "
                            + "sertransportdependencies inner join  sertransport on "
                            + "sertransportdependencies.serTransportDependenciesId = sertransport.serTransportDependenciesId "
                            + "where sertransportdependencies.serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TransportDependency transpel = new TransportDependency();
                        transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                        List<String> labellist = new ArrayList();
                        labellist.add(rs.getString("labels"));
                        transpel.setLabels(labellist);
                        TransportDescriptor transport = new TransportDescriptor();
                        transport.setProtocol(rs.getString("protocol"));
                        transport.setSecurity(rs.getString("security"));
                        transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                        transport.setVersion(rs.getString("version"));
                        transpel.setTransport(transport);
                        serTransportDependencies.add(transpel);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceRequired.get(j).serTransportDependencies(serTransportDependencies);
            }
            mecapp.setAppServiceRequired(appServiceRequired);

            //Set appservice optional
            //Retrieve App service List required from AppD DB
            List<Long> servroptionalid = new ArrayList();
            List<ServiceDependency> appServiceOptional = new ArrayList();

            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select serviceRequiredId,serName,version,requestedPermissions from "
                        + "appserviceoptional where appDId=?");
                ps.setLong(1, Long.valueOf(dboutcome.getComputereply().getMecappID()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ServiceDependency servicerequire = new ServiceDependency();
                    long servreqel = rs.getLong("serviceRequiredId");
                    servicerequire.setSerName(rs.getString("serName"));
                    servicerequire.setVersion(rs.getString("version"));
                    servicerequire.setRequestedPermissions(rs.getString("requestedPermissions"));
                    appServiceOptional.add(servicerequire);
                    servroptionalid.add(servreqel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            for (int j = 0; j < servroptionalid.size(); j++) {
                //Retrieve ser Category
                CategoryRef catref = new CategoryRef();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select href,id,name,version from "
                            + "sercategoryoptional where serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        catref.setHref(rs.getString("href"));
                        catref.setId(rs.getString("id"));
                        catref.setName(rs.getString("name"));
                        catref.setVersion(rs.getString("version"));
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceOptional.get(j).setSerCategory(catref);
                //Retrieve transport dependencies
                List<TransportDependency> serTransportDependencies = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select sertransportdependenciesoptional.serializers,sertransportdependenciesoptional.labels,"
                            + "transportoptional.transportType,transportoptional.protocol,transportoptional.version,"
                            + "transportoptional.security from "
                            + "sertransportdependenciesoptional inner join  sertransportoptional on "
                            + "sertransportdependenciesoptional.serTransportDependenciesId = transportoptional.serTransportDependenciesId "
                            + "where transportdependenciesoptional.serviceRequiredId=?");
                    ps.setLong(1, servrequiredid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TransportDependency transpel = new TransportDependency();
                        transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                        List<String> labellist = new ArrayList();
                        labellist.add(rs.getString("labels"));
                        transpel.setLabels(labellist);
                        TransportDescriptor transport = new TransportDescriptor();
                        transport.setProtocol(rs.getString("protocol"));
                        transport.setSecurity(rs.getString("security"));
                        transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                        transport.setVersion(rs.getString("version"));
                        transpel.setTransport(transport);
                        serTransportDependencies.add(transpel);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appServiceOptional.get(j).serTransportDependencies(serTransportDependencies);
            }
            mecapp.setAppServiceOptional(appServiceOptional);

            //Set transport dependencies
            List<TransportDependency> serTransportDependencies = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select transportdependencies.serializers,transportdependencies.labels,"
                        + "transport.transportType,transport.protocol,transport.version,"
                        + "transport.security from "
                        + "transportdependencies inner join  transport on "
                        + "transportdependencies.TransportDependenciesId = transport.TransportDependenciesId "
                        + "where transportdependencies.appDId=?");
                ps.setLong(1, Long.valueOf(dboutcome.getComputereply().getMecappID()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TransportDependency transpel = new TransportDependency();
                    transpel.serializers(TransportDependency.SerializersEnum.fromValue(rs.getString("serializers")));
                    List<String> labellist = new ArrayList();
                    labellist.add(rs.getString("labels"));
                    transpel.setLabels(labellist);
                    TransportDescriptor transport = new TransportDescriptor();
                    transport.setProtocol(rs.getString("protocol"));
                    transport.setSecurity(rs.getString("security"));
                    transport.setType(TransportDescriptor.TypeEnum.fromValue(rs.getString("transportType")));
                    transport.setVersion(rs.getString("version"));
                    transpel.setTransport(transport);
                    serTransportDependencies.add(transpel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            mecapp.setTransportDependencies(serTransportDependencies);

            //Set TrafficRuleDescriptor
            List<TrafficRuleDescriptor> appTrafficRule = new ArrayList();
            List<Long> traffruleid = new ArrayList();
            //retrieve traffic rules
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select trafficRuleId,filterType,priority,action from "
                        + "apptrafficrule where appDId=?");
                ps.setLong(1, Long.valueOf(dboutcome.getComputereply().getMecappID()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TrafficRuleDescriptor traffrule = new TrafficRuleDescriptor();
                    long servreqel = rs.getLong("trafficRuleId");
                    traffrule.setFilterType(TrafficRuleDescriptor.FilterTypeEnum.fromValue(rs.getString("filterType")));
                    traffrule.setTrafficRuleId(rs.getString("trafficRuleId"));
                    traffrule.setAction(ActionEnum.fromValue(rs.getString("action")));
                    traffrule.setPriority(new BigDecimal(rs.getString("trafficRuleId")));
                    appTrafficRule.add(traffrule);
                    traffruleid.add(servreqel);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            for (int j = 0; j < traffruleid.size(); j++) {
                //retrieve traffic filter
                List<TrafficFilter> trafficFilter = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select srcAddress,dstAddress,srcPort,dstPort,"
                            + "protocol,token,srcTunnelAddress,dstTunnelAddress,srcTunnelPort,dstTunnelPort,"
                            + "qci,dscp,tc from "
                            + "trafficfilter where trafficRuleId=?");
                    ps.setLong(1, traffruleid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        TrafficFilter trafffilter = new TrafficFilter();
                        trafffilter.setDscp(new BigDecimal(rs.getString("dscp")));
                        trafffilter.setQci(new BigDecimal(rs.getString("qci")));
                        trafffilter.setTc(new BigDecimal(rs.getString("tc")));
                        List<String> strlist = new ArrayList();
                        strlist.add(rs.getString("dstAddress"));
                        trafffilter.setDstAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("dstPort"));
                        trafffilter.setDstPort(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("dstTunnelAddress"));
                        trafffilter.setDstTunnelAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("dstTunnelPort"));
                        trafffilter.setDstTunnelPort(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("protocol"));
                        trafffilter.setProtocol(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("token"));
                        trafffilter.setToken(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcAddress"));
                        trafffilter.setSrcAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcPort"));
                        trafffilter.setSrcPort(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcTunnelAddress"));
                        trafffilter.setSrcTunnelAddress(strlist);
                        strlist = new ArrayList();
                        strlist.add(rs.getString("srcTunnelPort"));
                        trafffilter.setSrcTunnelPort(strlist);
                        trafficFilter.add(trafffilter);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appTrafficRule.get(j).setTrafficFilter(trafficFilter);
                //retrieve traffic interfaces
                List<InterfaceDescriptor> dstInterface = new ArrayList();
                try {
                    java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select dstinterface.interfaceType,dstinterface.srcMACAddress,"
                            + "dstinterface.dstMACAddress,dstinterface.dstIPAddress,tunnelinfo.tunnelType,"
                            + "tunnelinfo.tunnelDstAddress,tunnelinfo.tunnelSrcAddress,tunnelinfo.tunnelSpecificData from "
                            + "dstinterface inner join  tunnelinfo on "
                            + "dstinterface.dstInterfaceId = tunnelinfo.dstInterfaceId "
                            + "where dstinterface.trafficRuleId=?");
                    ps.setLong(1, traffruleid.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        InterfaceDescriptor intf = new InterfaceDescriptor();
                        TunnelInfo tunnel = new TunnelInfo();
                        tunnel.setTunnelDstAddress(rs.getString("tunnelDstAddress"));
                        tunnel.setTunnelSpecificData(rs.getString("tunnelSpecificData"));
                        tunnel.setTunnelSrcAddress(rs.getString("tunnelSrcAddress"));
                        tunnel.setTunnelType(TunnelInfo.TunnelTypeEnum.fromValue(rs.getString("tunnelType")));
                        intf.setTunnelInfo(tunnel);
                        intf.setDstIPAddress(rs.getString("dstIPAddress"));
                        intf.setDstMACAddress(rs.getString("dstMACAddress"));
                        intf.setInterfaceType(InterfaceDescriptor.InterfaceTypeEnum.fromValue(rs.getString("interfaceType")));
                        intf.setSrcMACAddress(rs.getString("srcMACAddress"));
                        dstInterface.add(intf);
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                appTrafficRule.get(j).setDstInterface(dstInterface);
            }
            mecapp.setAppTrafficRule(appTrafficRule);

            //Set appDNSRule
            List<DNSRuleDescriptor> appDNSRule = new ArrayList();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select dnsRuleId,domainName,ipAddressType,ipAddress,ttl from "
                        + "appdnsrule where appDId=?");
                ps.setLong(1, Long.valueOf(dboutcome.getComputereply().getMecappID()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    DNSRuleDescriptor dnsrule = new DNSRuleDescriptor();
                    dnsrule.setDnsRuleId(String.valueOf(rs.getLong("serviceRequiredId")));
                    dnsrule.setDomainName(rs.getString("domainName"));
                    dnsrule.setIpAddress(rs.getString("ipAddress"));
                    dnsrule.setIpAddressType(DNSRuleDescriptor.IpAddressTypeEnum.fromValue(rs.getString("ipAddressType")));
                    dnsrule.setTtl(new BigDecimal(rs.getString("ttl")));
                    appDNSRule.add(dnsrule);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            mecapp.setAppDNSRule(appDNSRule);

            //Set LatencyDescriptor
            LatencyDescriptor latency = new LatencyDescriptor();
            try {
                java.sql.Connection conn = DbAppDDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select timeUnit,latency from "
                        + "applatency where appDId=?");
                ps.setLong(1, Long.valueOf(dboutcome.getComputereply().getMecappID()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    latency.setTimeUnit(rs.getString("timeUnit"));
                    latency.setLatency(new BigDecimal(rs.getString("latency")));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            mecapp.setAppLatency(latency);

            //Send event to SBI for MEC
            ComputeAllocateMECReq mecallreq = new ComputeAllocateMECReq(dboutcome.getReqid(), dboutcome.getServid(),
                    Long.valueOf(mecdom), mecapp, dboutcome.getComputereply());
            System.out.println("DatabaseDriver --> Post MECallocationRequest Event");
            SingletonEventBus.getBus().post(mecallreq);

        } else {
            E2EComputeAllocateInstanceOutcome allinst = new E2EComputeAllocateInstanceOutcome(dboutcome.getReqid(),
                    dboutcome.getServid(), dboutcome.getComputereply(), dboutcome.isOutcome());
            System.out.println("DatabaseDriver --> Post E2EComputeAllocateInstanceOutcome Event");
            SingletonEventBus.getBus().post(allinst);
        }
    }

    @Subscribe
    public void handle_PhysicalAllocateDBQuery(PhysicalAllocateDBQuery dbquery) {
        System.out.println("DatabaseDriver --->  Handle PhysicalAllocateDBQuery Event");

        String abstrNfviPopId = null;
// START - Insert servID into Service table
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
            ps.setString(1, dbquery.getServid());

            ps.executeUpdate();
            ps.close();

        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(ResourceSelectionLogic.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("DatabaseDriver.handle_PhysicalAllocateDBQuery ---> SQLIntegrityConstraintViolationException: ServiceId is already in the DB");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
// END - Insert

// Set to disabled the PNF status in abstract PNFs tables
        try {

            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE pnfs SET pnfstatus=? WHERE abstrNfviPopId=? AND pnfid=?");
            ps.setString(1, "DISABLED");
            ps.setLong(2, Long.valueOf(dbquery.getRequest().getPopId()));
            ps.setString(3, dbquery.getRequest().getPnfId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        abstrNfviPopId = dbquery.getRequest().getPopId();

        //START - Retrieve from DB the list of nfviPopId and domId related to the abstrNfviPopId provided in the AllocateComputeRequest. 
        ArrayList<Long> vimlist = new ArrayList();
        ArrayList<Long> domlist = new ArrayList();
        System.out.println("DatabaseDriver.handle_PhysicalAllocateDBQuery ---> AbstrNfviPopId: " + abstrNfviPopId + "");
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select nfviPopId, domId from nfvipop where abstrNfviPopId=?");
            ps.setString(1, abstrNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vimlist.add(rs.getLong("nfviPopId"));
                domlist.add(rs.getLong("domId"));
                // vimlist.add(elementCount,vimElem);
                //nfviPopIdList.add(elementCount,rs.getString("NfviPopId"));

                System.out.println("DatabaseDriver.handle_PhysicalAllocateDBQuery ---> NfviPopId: " + rs.getString("NfviPopId") + "");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        
        for (int i = 0; i < vimlist.size(); i++) {
            //set pnfs stus to disabled in domdb  
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE pnfs SET pnfstatus=? WHERE nfviPopId=? AND pnfid=?");
                ps.setString(1, "DISABLED");
                ps.setLong(2, vimlist.get(i));
                ps.setString(3, dbquery.getRequest().getPnfId());

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        PhysicalAllocateDBQueryReply dbreply = new PhysicalAllocateDBQueryReply(dbquery.getReqid(), dbquery.getServid(), domlist, vimlist);
        System.out.println("DatabaseDriver --->  Post PhysicalAllocateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }
    

    @Subscribe
    public void handle_PhysicalAllocateDBQueryOutcome(PhysicalAllocateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver --->  Handle PhysicalAllocateDBQueryOutcome Event");
        

        if (dboutcome.isOutcome() == false) {
            //send API reply with void virtualcompute Instance

            E2EPhysicalAllocateInstanceOutcome allneginst = new E2EPhysicalAllocateInstanceOutcome(dboutcome.getReqid(),
                    dboutcome.getServid(), null, dboutcome.isOutcome());
            System.out.println("DatabaseDriver --> Post E2EPhysicalAllocateInstanceOutcome  Negative outcome Event");
            SingletonEventBus.getBus().post(allneginst);
            return;
        }
        
// START - INSERT ENTRY in pnfServices Table
        Long physicalServiceId = null;
        //Long virtualcomputeId = null;

        if (dboutcome.isOutcome()) {

            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO physicalservices (reqId,status,servId,nfviPopId) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                ps.setLong(1, dboutcome.getReqid());
                ps.setString(2, "enabled");
                ps.setString(3, dboutcome.getServid());
                ps.setLong(4, dboutcome.getNfvipopid());
                //ps.setString(5,null);

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_PhysicalAllocateDBQueryOutcome ---> ComputeService instance inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    physicalServiceId = rs.getLong(1);
                    System.out.println("DatabaseDriver.handle_PhysicalAllocateDBQueryOutcome ---> computeServId:" + physicalServiceId + "");
                }

                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

//END - Insert       
//        
            //START - INSERT AllocatePnfRequest into DB
            PNFRequest pnfReq = dboutcome.getPnfrequest();

            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                // Insert  data into computeservicerequestdata table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO physicalservicerequestdata (pnfId,abstrpopId,metadata,physicalServiceId) VALUES(?,?,?,?)");
                ps.setString(1, pnfReq.getPnfId());
                ps.setString(2, pnfReq.getPopId());
                ps.setString(3, ""); //Supposed empty for now
                ps.setLong(4, physicalServiceId);

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_PhysicalAllocateDBQueryOutcome ---> AllocateComputeRequest data inserted into DB");

                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            
// START - Insert record in pnf resources table
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                //Note that virtualComputeId is auto_generated. It must replace the computeId provided by the domain in the REST response. 
                PreparedStatement ps = conn.prepareStatement("INSERT INTO physicalresources "
                        + "(physicalResourcesId,"
                        + "pnfId,"
                        + "metadata,"
                        + "physicalServiceId) "
                        + "VALUES(?,?,?,?)");

                ps.setString(1, dboutcome.getPnfreply().getPnfResId());
                ps.setString(2, dboutcome.getPnfrequest().getPnfId());
                ps.setString(3, "");
                ps.setLong(4, physicalServiceId);
                //FIX the Exception
                // ps.setObject(10,dboutcome.getVIMElem().getAccelerationCapability());
                ps.executeUpdate();

                //ResultSet rs = ps.getGeneratedKeys();
//                if (rs != null && rs.next()) {
//                    virtualcomputeId = rs.getLong(1);
//                    System.out.println("DatabaseDriver.handle_ComputeAllocateDBQueryOutcome ---> computeServId:" + computeServId + "");
//                }
                //virtualcomputeId = computeServId;
                //rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        //put the physicalResourceId to the physicalServiceId
        dboutcome.getPnfreply().setPnfResId(String.valueOf(physicalServiceId));
        E2EPhysicalAllocateInstanceOutcome allinst = new E2EPhysicalAllocateInstanceOutcome(dboutcome.getReqid(),
                    dboutcome.getServid(), dboutcome.getPnfreply(), dboutcome.isOutcome());
            System.out.println("DatabaseDriver --> Post E2EPhysicalAllocateInstanceOutcome Event");
            SingletonEventBus.getBus().post(allinst);

    }
    
    @Subscribe
    public void handle_ComputeAllocateMECReply(ComputeAllocateMECReply allmecrep) {
        System.out.println("ResouceOrchestration --> Handle handle_ComputeAllocateVIMReply Event");

        if (allmecrep.getMecreqid().equals("")) {
            E2EComputeAllocateInstanceOutcome allinst = new E2EComputeAllocateInstanceOutcome(allmecrep.getReqid(),
                    allmecrep.getServid(), allmecrep.getVmreq(), false);
            System.out.println("DatabaseDriver --> Post E2EComputeAllocateInstanceOutcome Event");
            SingletonEventBus.getBus().post(allinst);
        }
        //Create entry in mec service instances
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO mec_service_instances (appInstanceId,mecdomId,computeServiceId) VALUES(?,?,?)");
            ps.setString(1, allmecrep.getMecreqid());
            ps.setString(2, String.valueOf(allmecrep.getMecdomid()));
            ps.setString(3, allmecrep.getVmreq().getComputeId());

            ps.executeUpdate();
            ps.close();

        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(ResourceSelectionLogic.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("DatabaseDriver.handle_ComputeAllocateMECReply ---> SQLIntegrityConstraintViolationException: ServiceId is already in the DB");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        E2EComputeAllocateInstanceOutcome allinst = new E2EComputeAllocateInstanceOutcome(allmecrep.getReqid(),
                allmecrep.getServid(), allmecrep.getVmreq(), true);
        System.out.println("DatabaseDriver --> Post E2EComputeAllocateInstanceOutcome Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_ComputeTerminateMECReply(ComputeTerminateMECReply allmecrep) {
        System.out.println("DatabaseDriver --> Handle ComputeTerminateMECReply Event");
        //delete MEC instances from DB
        //Remove entry from ComputeServices
        for (int i = 0; i < allmecrep.getComputereq().size(); i++) {
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM mec_service_instances WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, Long.valueOf(allmecrep.getComputereq().get(i)));
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        //Send ComputeTerminateDBQuery
        ComputeTerminateDBQuery dbev = new ComputeTerminateDBQuery(allmecrep.getReqid(),
                allmecrep.getServid(), allmecrep.getComputereq());
        System.out.println("DatabaseDriver --> Post ComputeTerminateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);

    }

    @Subscribe
    public void handle_NetworkAllocateDBQuery(NetworkAllocateDBQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle NetworkAllocateDBQuery Event");
        long logicalPathId = dbquery.getLogicalpath();
        ArrayList<Long> interdomainLinks = new ArrayList();
        ArrayList<String> intraPopLinks = new ArrayList();
        ArrayList<Long> wanLinks = new ArrayList();
        ArrayList<Long> wimPopList = new ArrayList();
        ArrayList<Long> vimPopList = new ArrayList();
        ArrayList<Long> wimdomlist = new ArrayList();
        ArrayList<Long> vimdomlist = new ArrayList();
        ArrayList<String> wimNetworkType = new ArrayList();
        ArrayList<String> vimNetworkType = new ArrayList();
        long abstrSrcNfviPopId = -1;
        long abstrDestNfviPopId = -1;

        //TODO perform query in DB using JDBC
        // START - Insert servID into Service table
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
            ps.setString(1, dbquery.getServid());

            ps.executeUpdate();
            ps.close();

        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(ResourceSelectionLogic.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("handle_NetworkAllocateDBQuery ---> SQLIntegrityConstraintViolationException: ServiceId is already in the DB");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
// END - Insert

        //Get from DB the interdomainLinks 
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId  FROM logicalpath_interdomainlink where logicalPathId=?");
            ps.setLong(1, logicalPathId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                interdomainLinks.add(rs.getLong("interDomainLinkId"));

                System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getLong("InterDomainLinkId") + "");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Get from DB the  wanLinks, wimPopList and networkType
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT logicalpath_networkresources.networkResId, logicalpath_networkresources.logicalPathId, networkresources.networkType, zoneid.nfviPopId, domain.type, domain.domId from logicalpath_networkresources \n"
                    + "INNER JOIN networkresources ON networkresources.networkResId=logicalpath_networkresources.networkResId\n"
                    + " INNER JOIN zoneid ON  networkresources.resourceZoneId=zoneid.resourceZoneId \n"
                    + "INNER JOIN nfvipop ON zoneid.nfviPopId = nfvipop.nfviPopId\n"
                    + "INNER JOIN domain ON nfvipop.domId = domain.domId\n"
                    + "where logicalpath_networkresources.logicalPathId=?");

            ps.setLong(1, logicalPathId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String type = rs.getString("type");
//                if (type.equals("VIM")) {
//                    intraPopLinks.add(rs.getLong("networkResId"));
//                    vimPopList.add(rs.getLong("nfviPopId"));
//                    vimdomlist.add(rs.getLong("domId"));
//                    vimNetworkType.add(rs.getString("networkType"));
//                    System.out.println("handle_NetworkAllocateDBQuery ---> IntraPopLink - NetworkResId: " + rs.getLong("NetworkResId") + "");
//                }

                if (type.equals("T-WIM")) {
                    wanLinks.add(rs.getLong("networkResId"));
                    wimPopList.add(rs.getLong("nfviPopId"));
                    wimdomlist.add(rs.getLong("domId"));
                    wimNetworkType.add(rs.getString("networkType"));
                    System.out.println("handle_NetworkAllocateDBQuery ---> WanLink - NetworkResId: " + rs.getLong("NetworkResId") + "");
                }

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Get from logicalpath table the abstrSrcNfviPop and abstrDstNfviPop to request intraPop connectivities
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT abstrSrcNfviPopId, abstrDestNfviPopId  FROM logicalpath where logicalPathId=?");
            ps.setLong(1, logicalPathId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abstrSrcNfviPopId = rs.getLong("abstrSrcNfviPopId");
                abstrDestNfviPopId = rs.getLong("abstrDestNfviPopId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Get from DB the vimPoP and vimDomain related to the abstrSrcNfviPop 
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT nfvipop.nfviPopId, domain.domId from nfvipop INNER JOIN domain ON domain.domId=nfvipop.domId where nfvipop.abstrNfviPopId=?");

            ps.setLong(1, abstrSrcNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                vimPopList.add(rs.getLong("nfviPopId"));
                vimdomlist.add(rs.getLong("domId"));

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Get from DB the vimPoP and vimDomain related to the abstrDestNfviPop 
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT nfvipop.nfviPopId, domain.domId from nfvipop INNER JOIN domain ON domain.domId=nfvipop.domId where nfvipop.abstrNfviPopId=?");

            ps.setLong(1, abstrDestNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                vimPopList.add(rs.getLong("nfviPopId"));
                vimdomlist.add(rs.getLong("domId"));

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //select intraponetworkreference id for intrpoplink source
        try {

            String srcVnfIpAddress = "";
            MetaData metadata = dbquery.getNetworkreq().getMetaData();
            String refintra = "";

            //srcVnfIpAddress.compareTo(srcVnfIpAddress)
            for (int i = 0; i < metadata.size(); i++) {
                if (metadata.get(i).getKey().compareTo("srcVnfIpAddress") == 0) {
                    srcVnfIpAddress = metadata.get(i).getValue();

                }
            }

            // IPAddress address = new IPAddressString(srcVnfIpAddress).getAddress();
            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//            PreparedStatement ps = conn.prepareStatement("select virtualnetwork.networkResourceId,virtualnetwork.networkResourceName,virtualnetwork.segmentType"
//                    + " from virtualnetwork inner join networkservices on virtualnetwork.netServId=networkservices.netServId"
//                    + " where networkservices.servId =? AND networkservices.provider<>? AND virtualnetwork.NfviPopId=?");

            PreparedStatement ps = conn.prepareStatement("select networksubnet.cidr, networksubnet.gatewayIp, virtualnetwork.networkResourceId,virtualnetwork.networkResourceName,virtualnetwork.segmentType "
                    + "from networksubnet "
                    + "inner join virtualnetwork on virtualnetwork.virtualNetworkId=networksubnet.virtualnetworkId "
                    + "inner join networkservices on virtualnetwork.netServId=networkservices.netServId "
                    + "where networkservices.servId =? AND networkservices.provider<>? AND virtualnetwork.NfviPopId=?");

            ps.setString(1, dbquery.getServid());
            ps.setString(2, "");
            ps.setLong(3, abstrSrcNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                IPAddress cidr = new IPAddressString(rs.getString("cidr")).getAddress();

                if (cidr.contains(new IPAddressString(srcVnfIpAddress).getAddress())) {
                    refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                }
                //String refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                // intraPopLinks.add(refintra);
            }
            intraPopLinks.add(refintra);
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //select intraponetworkreference id for intrpoplink dest
        try {
            String dstVnfIpAddress = "";
            MetaData metadata = dbquery.getNetworkreq().getMetaData();
            String refintra = "";

            for (int i = 0; i < metadata.size(); i++) {
                if (metadata.get(i).getKey().compareTo("dstVnfIpAddress") == 0) {
                    dstVnfIpAddress = metadata.get(i).getValue();

                }
            }

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//            PreparedStatement ps = conn.prepareStatement("select virtualnetwork.networkResourceId,virtualnetwork.networkResourceName,virtualnetwork.segmentType"
//                    + " from virtualnetwork inner join networkservices on virtualnetwork.netServId=networkservices.netServId"
//                    + " where networkservices.servId =? AND networkservices.provider<>? AND virtualnetwork.NfviPopId=?");

            PreparedStatement ps = conn.prepareStatement("select networksubnet.cidr, networksubnet.gatewayIp, virtualnetwork.networkResourceId,virtualnetwork.networkResourceName,virtualnetwork.segmentType "
                    + "from networksubnet "
                    + "inner join virtualnetwork on virtualnetwork.virtualNetworkId=networksubnet.virtualnetworkId "
                    + "inner join networkservices on virtualnetwork.netServId=networkservices.netServId "
                    + "where networkservices.servId =? AND networkservices.provider<>? AND virtualnetwork.NfviPopId=?");

            ps.setString(1, dbquery.getServid());
            ps.setString(2, "");
            ps.setLong(3, abstrDestNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                IPAddress cidr = new IPAddressString(rs.getString("cidr")).getAddress();

                if (cidr.contains(new IPAddressString(dstVnfIpAddress).getAddress())) {
                    refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");

                }

                //String refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                //intraPopLinks.add(refintra);
            }
            intraPopLinks.add(refintra);
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //NetworkAllocateDBQueryReply dbreply = new NetworkAllocateDBQueryReply(dbquery.getReqId(), dbquery.getServId());
        NetworkAllocateDBQueryReply dbreply = new NetworkAllocateDBQueryReply(dbquery.getReqid(), dbquery.getServid(),
                logicalPathId, wimdomlist, vimdomlist, interdomainLinks, intraPopLinks, wanLinks, wimPopList, vimPopList, wimNetworkType,
                vimNetworkType);

        System.out.println("DatabaseDriver ---> Post NetworkAllocateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_NetworkAllocateDBQueryOutcome(NetworkAllocateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver ---> Handle NetworkAllocateDBQueryOutcome Event");

        if (dboutcome.isOutcome() == false) {
            //send API reply with void virtualcompute Instance

            E2ENetworkAllocateInstanceOutcome netneginst = new E2ENetworkAllocateInstanceOutcome(dboutcome.getReqid(),
                    dboutcome.getServid(), null, dboutcome.isOutcome(), dboutcome.getLogicalPathId());
            System.out.println("DatabaseDriver --> Post E2ENetworkAllocateInstanceOutcome  Negative outcome Event");
            SingletonEventBus.getBus().post(netneginst);
            return;
        }

        long networkServId = -1;
        long virtualnetworkId = -1;

        // Insert record in networkservices table//
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservices "
                    + "(reqId,"
                    + "status,"
                    + "servId,"
                    + "logicalPathId)"
                    + "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, dboutcome.getReqid());
            ps.setString(2, "enabled");
            ps.setString(3, dboutcome.getServid());
            ps.setLong(4, dboutcome.getLogicalPathId());
            //ps.setString(5,null);

            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> NetworkService instance inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                networkServId = rs.getLong(1);
                System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
            }

            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //END - Insert      
        //START - Insert virtualnetwork entries for WIM POPs 
        List<VirtualNetwork> wimlist = dboutcome.getWimnetlist();

        // while (computeIdList|=null){
        for (int i = 0; i < dboutcome.getWimnetlist().size(); i++) {

            try {
                //String computeServId=computeIdList.remove(0).getNetworkId();
                VirtualNetwork vn = wimlist.get(i);

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO mtpservdb.virtualnetwork\n"
                        + "(networkResourceName,"
                        + "segmentType,"
                        + "isShared,"
                        + "zoneId,"
                        + "networkResourceId,"
                        + "networkType,"
                        + "operationalState,"
                        + "sharingCriteria,"
                        + "bandwidth,"
                        + "nfviPopId,"
                        + "netServId,"
                        + "netResIdRef)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, vn.getNetworkResourceName());
                ps.setString(2, vn.getSegmentType());
                ps.setBoolean(3, vn.isIsShared());
                ps.setString(4, vn.getZoneId());
                ps.setString(5, vn.getNetworkResourceId());
                ps.setString(6, vn.getNetworkType());
                ps.setString(7, vn.getOperationalState());
                ps.setString(8, vn.getSharingCriteria());
                ps.setBigDecimal(9, vn.getBandwidth());
                ps.setLong(10, dboutcome.getWimPopList().get(i));
                ps.setLong(11, networkServId);
                ps.setLong(12, dboutcome.getWanLinks().get(i));

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetwork instance inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    virtualnetworkId = rs.getLong(1);
                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
                }
                rs.close();
                ps.close();
                //Insert entries in NETWORK_SUBSET table
                // TO DO - Fix this part after the VirtualNetwork class is compliant with IFA005 
                if (vn.getSubnet() != null) {
                    PreparedStatement ps_subnet = conn.prepareStatement("INSERT INTO networksubnet"
                            + "(resourceId,virtualNetworkId) VALUES(?,?)");
//UNCOMMENT after  NETWORK_SUBSET class is fixed
//                    ps_subnet.setString(1,vn.getSubnet());
//                    ps_subnet.setLong(2,virtualnetworkId);
                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Subnet entry inserted into DB");
                    ps_subnet.close();
                }
//Insert entries in VIRTUAL_NETWORK_PORT table
                //TO DO - Complete after VIRTUAL_NETWORK_PORT class is fixed (related to IFA005) 
                if (vn.getNetworkPort() != null) {

                    for (int j = 0; j < vn.getNetworkPort().size(); j++) {

                        PreparedStatement ps_port = conn.prepareStatement("INSERT INTO virtualnetworkport "
                                + "(resourceId, portType,attachedResourceId,segmentId,bandwidth,metadata,virtualNetworkId)"
                                + "VALUES(?,?,?,?,?,?,?)");

                        ps_port.setString(1, vn.getNetworkPort().get(j).getResourceId());
                        ps_port.setString(2, vn.getNetworkPort().get(j).getPortType());
                        ps_port.setString(3, vn.getNetworkPort().get(j).getAttachedResourceId());
                        //TO DO - Insert below attributes when VIRTUAL_NETWORK_PORT class is fixed (related to IFA005)                  
                        // ps_port.setString(4,vn.getNetworkPort().get(j).);
                        // ps_port.setString(5,vn.getNetworkPort().get(j).);
                        //  ps_port.setString(6,vn.getNetworkPort().get(j).);
                        ps_port.setLong(7, virtualnetworkId);
                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkPort entry inserted into DB");
                        ps_port.close();
                    }
                }
                //Insert entries in Network_QoS table
                //TO DO - Complete after VIRTUAL_NETWORK class is fixed (related to IFA005) 
                // UNCOMMENT after  VIRTUAL_NETWORK class is fixed 
//                if(vn.getNetworkQoS()!=null) {
//
//                     for (int j = 0; j < vn.getNetworkPort().size(); j++) {
//                    PreparedStatement ps_qos = conn.prepareStatement(" INSERT INTO supportednetworkqos\n" +
//"(qosName, qosValue, virtualnetworkId)\n" +
//"VALUES (?,?,?);");
//
//                    ps_qos.setString(1,vn.getNetworkQoS().get(j).); 
//                    ps_qos.setString(1,vn.getNetworkQoS().get(j).);
//                    ps_qos.setLong(3,virtualnetworkId);
//                    
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Network QoS entry inserted into DB");
//                    ps_qos.close();
//                     }      
//                }  
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        //START - Insert virtualnetwork entries for VIM POPs 
        List<VirtualNetwork> vimlist = dboutcome.getVimnetlist();

        // while (computeIdList!=null){
        for (int i = 0; i < dboutcome.getVimnetlist().size(); i++) {

            try {
                //String computeServId=computeIdList.remove(0).getNetworkId();
                VirtualNetwork vn = vimlist.get(i);

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO mtpservdb.virtualnetwork\n"
                        + "(networkResourceName,"
                        + "segmentType,"
                        + "isShared,"
                        + "zoneId,"
                        + "networkResourceId,"
                        + "networkType,"
                        + "operationalState,"
                        + "sharingCriteria,"
                        + "bandwidth,"
                        + "nfviPopId,"
                        + "netServId)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, vn.getNetworkResourceName());
                ps.setString(2, vn.getSegmentType());
                ps.setBoolean(3, vn.isIsShared());
                ps.setString(4, vn.getZoneId());
                ps.setString(5, vn.getNetworkResourceId());
                ps.setString(6, vn.getNetworkType());
                ps.setString(7, vn.getOperationalState());
                ps.setString(8, vn.getSharingCriteria());
                ps.setBigDecimal(9, vn.getBandwidth());
                ps.setLong(10, dboutcome.getVimPopList().get(i));
                ps.setLong(11, networkServId);

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetwork instance inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    virtualnetworkId = rs.getLong(1);
                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
                }
                rs.close();
                ps.close();
                //Insert entries in NETWORK_SUBSET table
                // TO DO - Fix this part after the VirtualNetwork class is compliant with IFA005 
                if (vn.getSubnet() != null) {

                    PreparedStatement ps_subnet = conn.prepareStatement("INSERT INTO networksubnet"
                            + "(resourceId,virtualNetworkId) VALUES(?,?)");
//UNCOMMENT after  NETWORK_SUBSET class is fixed
//                    ps_subnet.setString(1,vn.getSubnet());
//                    ps_subnet.setLong(2,virtualnetworkId);
                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Subnet entry inserted into DB");
                    ps_subnet.close();
                }
//Insert entries in VIRTUAL_NETWORK_PORT table
                //TO DO - Complete after VIRTUAL_NETWORK_PORT class is fixed (related to IFA005) 
                if (vn.getNetworkPort() != null) {

                    for (int j = 0; j < vn.getNetworkPort().size(); j++) {

                        PreparedStatement ps_port = conn.prepareStatement("INSERT INTO virtualnetworkport "
                                + "(resourceId, portType,attachedResourceId,segmentId,bandwidth,metadata,virtualNetworkId)"
                                + "VALUES (?,?,?,?,?,?,?)");

                        ps_port.setString(1, vn.getNetworkPort().get(j).getResourceId());
                        ps_port.setString(2, vn.getNetworkPort().get(j).getPortType());
                        ps_port.setString(3, vn.getNetworkPort().get(j).getAttachedResourceId());
                        //TO DO - Insert below attributes when VIRTUAL_NETWORK_PORT class is fixed (related to IFA005)                  
                        // ps_port.setString(4,vn.getNetworkPort().get(j).);
                        // ps_port.setString(5,vn.getNetworkPort().get(j).);
                        //  ps_port.setString(6,vn.getNetworkPort().get(j).);
                        ps_port.setLong(7, virtualnetworkId);
                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkPort entry inserted into DB");
                        ps_port.close();
                    }
                }
                //Insert entries in Network_QoS table
                //TO DO - Complete after VIRTUAL_NETWORK class is fixed (related to IFA005) 
                // UNCOMMENT after  VIRTUAL_NETWORK class is fixed 
//                if(vn.getNetworkQoS()!=null) {
//
//                     for (int j = 0; j < vn.getNetworkPort().size(); j++) {
//                    PreparedStatement ps_qos = conn.prepareStatement(" INSERT INTO supportednetworkqos\n" +
//"(qosName, qosValue, virtualnetworkId)\n" +
//"VALUES (?,?,?);");
//
//                    ps_qos.setString(1,vn.getNetworkQoS().get(j).); 
//                    ps_qos.setString(1,vn.getNetworkQoS().get(j).);
//                    ps_qos.setLong(3,virtualnetworkId);
//                    
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Network QoS entry inserted into DB");
//                    ps_qos.close();
//                     }      
//                }  
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        //  START - INSERT AllocateNetworkRequest into DB
        InterNfviPopConnectivityRequest allocNetworkReq = dboutcome.getNetworkRequest();

        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

            // Insert  data into computeservicerequestdata table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservicerequestdata\n"
                    + "(networkResourceType,"
                    + "netServId)"
                    + "VALUES (?,?)");

            ps.setString(1, allocNetworkReq.getNetworkLayer());
            ps.setLong(2, networkServId);
            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> AllocateNetworkRequest data inserted into DB");
            ps.close();

            // Insert  data into VIRTUAL_NETWORK_DATA table
            // if (networkdata!=null) {
            PreparedStatement ps_netdata = conn.prepareStatement("INSERT INTO virtualnetworkdata "
                    + "(bandwidth, "
                    + "networkType,"
                    + "netServId) VALUES(?,?,?)");

            ps_netdata.setLong(1, allocNetworkReq.getLogicalLinkPathList().get(0).getReqBandwidth().longValue());
            //ps_netdata.setLong(1, 5000000);
            ps_netdata.setString(2, allocNetworkReq.getInterNfviPopNetworkType());
            ps_netdata.setLong(3, networkServId);
            ps_netdata.executeUpdate();
            System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkData record inserted into DB");
            ps_netdata.close();
            //       }
// Insert  data into NETWORK_QoS table
            //List <Object> qosList = networkdata.getQos();

            //  if (qosList!=null) {
            //  for (int j = 0; j < qosList.size(); j++) {
            PreparedStatement ps_qos = conn.prepareStatement("INSERT INTO networkqos"
                    + "(qosName,"
                    + "qosValue,"
                    + "netServId)"
                    + "VALUES (?,?,?)");

            //TO DO - Add missing attributes
            ps_qos.setString(1, "delay");

            ps_qos.setBigDecimal(2, dboutcome.getNetworkRequest().getLogicalLinkPathList().get(0).getReqLatency());
            //ps_qos.setBigDecimal(2, new BigDecimal(10.0));
            ps_qos.setLong(3, networkServId);
            ps_qos.executeUpdate();
            System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> QoS entry inserted into DB");
            ps_qos.close();
            //   }
            // }
            //Insert  data into LAYER3_CONNECTIVITY_INFORMATION table
            // TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
            //UNCOMMENT after Allocate Virtualised Network Resource operation input parameters are fixed
//List <Object> layer3InfoList = networkdata.getLayer3();      
//                   if (layer3InfoList!=null) {
//
//                    for (int j = 0; j < layer3InfoList.size(); j++) {
//
//                        PreparedStatement ps_layer3 = conn.prepareStatement("INSERT INTO layer3connectivityinformation\n" +
//"(networkId, ipVersion, gatewayIp, cidr, isDhcpEnabled, addressPool, metadata, netServId)\n" +
//"VALUES(?,?,?,?,?,?,?,?)");
//        
//                        
//                        //TO DO - Add missing attributes
//                        ps_layer3.setString(1, layer3InfoList.get(j).getQosName());
//                      
//                        ps_layer3.setLong(8, virtualnetworkId);
//                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> QoS entry inserted into DB");
//                        ps_layer3.close();
//                    }
//                }
            // Insert  data into VIRTUAL_NETWORK_PORT_DATA table
            // TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
            //UNCOMMENT after Allocate Virtualised Network Resource operation input parameters are fixed
//                Object portdata = allocNetworkReq.getTypeNetworkPortData();
//                if (portdata!= null) {
//
//                        PreparedStatement ps_portdata = conn.prepareStatement("INSERT INTO virtualnetworkportdata "
//                                + "(portType, networkId, segmentId, bandwidth, metadata, netServId)\n" +
//                                  "VALUES(?,?,?,?,?,?)");
//   
//                        ps_portdata.setString(1, portdata.get));  
//                        ps_portdata.setLong(6, networkServId);
//                      
//                        ps_portdata.executeUpdate();
//                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkPortData record inserted into DB");
//                        ps_portdata.close();       
//                }
            // Insert  data into NETWORK_SUBNET_DATA table
            // TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
            //UNCOMMENT after Allocate Virtualised Network Resource operation input parameters are fixed
//                Object subnetData = allocNetworkReq.getTypeSubnetData();
//                if (subnetData != null) {
// 
//                    PreparedStatement ps_subnetData = conn.prepareStatement("INSERT INTO networksubnetdata\n" +
//"(networkId, ipVersion, gatewayIp, cidr, isDhcpEnabled, addressPool, metadata, netServId) \n" +
//"VALUES (?,?,?,?,?,?,?,?)");
//                    //TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
//                    ps_subnetData.setString(1, subnetData.get);
//                
//                    ps_subnetData.setLong(8, networkServId);
//
//                    ps_subnetData.executeUpdate();
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> NetworkSubnetData record inserted into DB");
//                    ps_subnetData.close();
//
//                }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
//END - Insert

//Amount of Bandwidth released from the Capacity
        long bandwidth = dboutcome.getNetworkRequest().getLogicalLinkPathList().get(0).getReqBandwidth().longValue();
//long  bandwidth = 5000000;

//Update Capacity in logicalpath table
        try {
            long availableBandwidth;
            long allocatedBandwidth;
            long currentAvailableBandwidth;
            long currentAllocatedBandwidth;

            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId, availableBandwidth, reservedBandwidth,"
                    + " totalBandwidth, allocatedBandwidth\n"
                    + "FROM logicalpath WHERE logicalPathId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ps.setLong(1, dboutcome.getLogicalPathId());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availableBandwidth = rs.getLong("availableBandwidth");
                allocatedBandwidth = rs.getLong("allocatedBandwidth");
                currentAvailableBandwidth = availableBandwidth - bandwidth;
                currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                rs.updateRow();

                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
//START - Update Capacity in Domain DB

//Update InterDomainLinks capacity
        for (int i = 0; i < dboutcome.getInterdomainLinks().size(); i++) {

            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long currentAvailableBandwidth;
                long currentAllocatedBandwidth;

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth, "
                        + "totalBandwidth, allocatedBandwidth\n"
                        + "FROM interdomainlink WHERE interDomainLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, dboutcome.getInterdomainLinks().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableBandwidth");
                    allocatedBandwidth = rs.getLong("allocatedBandwidth");
                    currentAvailableBandwidth = availableBandwidth - bandwidth;
                    currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                    rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                    rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

//        //Update IntraPopLinks capacity
//        for (int i = 0; i < dboutcome.getIntraPopLinks().size(); i++) {
//
//            try {
//                long availableBandwidth;
//                long allocatedBandwidth;
//                long currentAvailableBandwidth;
//                long currentAllocatedBandwidth;
//
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//
//                PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity, totalCapacity, "
//                        + "allocatedCapacity, networkResourceTypeId\n"
//                        + "FROM networkresources WHERE networkResId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//                ps.setLong(1, dboutcome.getIntraPopLinks().get(i));
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    availableBandwidth = rs.getLong("availableCapacity");
//                    allocatedBandwidth = rs.getLong("allocatedCapacity");
//                    currentAvailableBandwidth = availableBandwidth - bandwidth;
//                    currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
//                    rs.updateLong("availableCapacity", currentAvailableBandwidth);
//                    rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
//                    rs.updateRow();
//
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in DOMAIN_DB!");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
//                }
//
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//        }
        //Update WanLinks capacity
        for (int i = 0; i < dboutcome.getWanLinks().size(); i++) {

            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long currentAvailableBandwidth;
                long currentAllocatedBandwidth;

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity, totalCapacity, "
                        + "allocatedCapacity, networkResourceTypeId\n"
                        + "FROM networkresources WHERE networkResId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, dboutcome.getWanLinks().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableCapacity");
                    allocatedBandwidth = rs.getLong("allocatedCapacity");
                    currentAvailableBandwidth = availableBandwidth - bandwidth;
                    currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                    rs.updateLong("availableCapacity", currentAvailableBandwidth);
                    rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        //END - Update Capacity in Domain DB
//TO DO - Refine the criterion to select the VirtualNetwork instance from the wanLinks
//Select the first VirtualNetwork instance in wanLinks and update
//it with the virtualnetworkId (networkServId) to provide to SO 
        VirtualNetwork vn = dboutcome.getWimnetlist().get(0);
        vn.setNetworkResourceId(Long.toString(networkServId));

//        E2ENetworkAllocateInstanceOutcome netinst = new E2ENetworkAllocateInstanceOutcome(dboutcome.getReqid(),
//                dboutcome.getServid(), /*Inset VN*/ null, dboutcome.isOutcome(), dboutcome.getLogicalPathId());
        E2ENetworkAllocateInstanceOutcome netinst = new E2ENetworkAllocateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), vn, dboutcome.isOutcome(), dboutcome.getLogicalPathId());

        System.out.println("DatabaseDriver --> Post E2ENetworkAllocateInstanceOutcome Event");
        SingletonEventBus.getBus().post(netinst);

    }

    //////////////////Terminate Event Handlers///////////////////////////////
    @Subscribe
    public void handle_ComputeTerminateMECQuery(ComputeTerminateMECQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle ComputeTerminateMECQuery Event");

        //START - Retrieve from DB the list of mec domid  
        ArrayList<Long> mecdomlist = new ArrayList();
        ArrayList<String> mecreqlist = new ArrayList();

        List<String> computeIdList = dbquery.getComputereq();

        long size = dbquery.getComputereq().size();

        // while (computeIdList!=null){
        for (int i = 0; i < size; i++) {
            String computeServId = computeIdList.get(i);

            // Retrieve from DB the mec service instance to remove
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT appInstanceId,mecdomId FROM mtpservdb.mec_service_instances where computeServiceId=?");
                ps.setString(1, computeServId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mecdomlist.add(Long.valueOf(rs.getString("mecdomId")));
                    mecreqlist.add(rs.getString("appInstanceId"));
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> VM ID: " + rs.getString("computeId") + "");
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        if (mecdomlist.size() == 0) {
            //no MEC instance to remove. call the ComputeTerminateDBQuery 
            ComputeTerminateDBQuery dbev = new ComputeTerminateDBQuery(dbquery.getReqid(),
                    dbquery.getServid(), dbquery.getComputereq());
            System.out.println("DatabaseDriver --> Post ComputeTerminateDBQuery Event");
            SingletonEventBus.getBus().post(dbev);
        } else {
            //Call MEC terminate instance
            ComputeTerminateMECReq dbev = new ComputeTerminateMECReq(dbquery.getReqid(),
                    dbquery.getServid(), dbquery.getComputereq(), mecreqlist, mecdomlist);
            System.out.println("DatabaseDriver ---> Post ComputeTerminateMECReq Event");
            SingletonEventBus.getBus().post(dbev);
        }

    }

    @Subscribe
    public void handle_ComputeTerminateDBQuery(ComputeTerminateDBQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle ComputeTerminateDBQuery Event");

        //START - Retrieve from DB the list of NfviPoP (PopId) where the compute resources to terminate are deployed as well as the
        //list of domains (domId) to call for compute resource termination 
        ArrayList<Long> domlist = new ArrayList();
        ArrayList<Long> poplist = new ArrayList();
        // List of Virtual Machine identifiers for which the termination is to be requested 
        ArrayList<String> vmIdList = new ArrayList();

        List<String> computeIdList = dbquery.getComputereq();
        ArrayList<String> vnflist = new ArrayList();
        
        long size = dbquery.getComputereq().size();

        // while (computeIdList!=null){
        for (int i = 0; i < size; i++) {
            String computeServId = computeIdList.get(i);
            try {
                //String computeServId=computeIdList.remove(0).getNetworkId();

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("select domId, mtpdomdb.nfvipop.nfviPopId from mtpdomdb.nfvipop inner join mtpservdb.computeservices\n"
                        + "on mtpservdb.computeservices.computeServiceId=?\n"
                        + "where mtpservdb.computeservices.nfviPopId=mtpdomdb.nfvipop.nfviPopId");
                ps.setString(1, computeServId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    domlist.add(rs.getLong("domId"));
                    poplist.add(rs.getLong("nfviPopId"));
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> DomId: " + rs.getLong("domid") + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> PopId: " + rs.getLong("NfviPopId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            // Retrieve from DB the Virtual Machine ID
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT computeId FROM mtpservdb.virtualcompute where computeServiceId=?");
                ps.setString(1, computeServId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    vmIdList.add(rs.getString("computeId"));
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> VM ID: " + rs.getString("computeId") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            
            // Retrieve from DB the VNF ID
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT vnfId FROM mtpservdb.computeservicerequestdata where computeServiceId=?");
                ps.setString(1, computeServId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    vnflist.add(rs.getString("vnfId"));
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> VM ID: " + rs.getString("vnfId") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        //ComputeTerminateDBQueryReply dbreply = new ComputeTerminateDBQueryReply(dbquery.getReqId(), dbquery.getServId());
        ComputeTerminateDBQueryReply dbreply = new ComputeTerminateDBQueryReply(dbquery.getReqid(), dbquery.getServid(),
                domlist, poplist, vmIdList, vnflist);
        System.out.println("DatabaseDriver ---> Post ComputeTerminateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_ComputeTerminateDBQueryOutcome(ComputeTerminateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver ---> Handle ComputeTerminateDBQueryOutcome Event");
        //Remove entry from ComputeServices, VirtualCompute ComputeRequestData, AffinityOrAntiaffinityConstrain,
        // VirtualInterfaceData, Userdata, VirtualNetworkInterface, VirtualCpuand VirtualMemory tables if the 
        //corresponding outcome is TRUE

        //   ArrayList <VIMAbstractElem> vimlist = new ArrayList();
        for (int i = 0; i < dboutcome.getComputeIdList().size(); i++) {

            String computeId = dboutcome.getComputeIdList().get(i);
            long computeServiceId = -1;
            long computeFlavourId = -1;
            String computeFlavourStr = new String("");
            long abstrResourceZoneId = -1;
            long zoneId = -1;
            long numVirtualCpu = -1;
            long memorySize = -1;
            long storageSize = -1;
            //long abstrPopId = dboutcome.getPoplist().get(i);
            long abstrPopId = -1;
            long popId = dboutcome.getPoplist().get(i);

            // Retrieve from DB the abstrNfviPopId related to the nfviPopId
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT abstrNfviPopId FROM nfvipop where nfviPopId=?");
                ps.setLong(1, popId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    abstrPopId = rs.getLong("abstrNfviPopId");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> nfviPopId: " + popId + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> abstrNfviPopId: " + abstrPopId + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Retrieve from DB the ComputeServiceId related to the computeId  
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT computeServiceId FROM virtualcompute where computeId=?");
                ps.setString(1, computeId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    computeServiceId = rs.getLong("computeServiceId");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> ComputeServiceId: " + computeServiceId + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            // Retrieve from DB the computeFlavourId and the ResourceZoneId (mapped on IFA005 locationConstraints attribute)
            //from the ComputeServiceRequestData table. Note that the ResourceZoneId retrieved from ComputeServiceRequestData is an abstrResourceZoneId.
            //NOTE that computeFlavourId and the ResourceZoneId may be obtained from VirtualCompute table
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT flavourId,zoneId FROM virtualcompute where computeServiceId=?");
                ps.setLong(1, computeServiceId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    computeFlavourStr = rs.getString("flavourId");
                    abstrResourceZoneId = rs.getLong("zoneId");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> ComputeFlavourStr: " + computeFlavourStr + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> abstrResourceZoneId: " + abstrResourceZoneId + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //Select id from string of flavour
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT computeFlavourId FROM computeflavour where flavourId=?");
                ps.setString(1, computeFlavourStr);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    computeFlavourId = rs.getLong("computeFlavourId");

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> ComputeFlavourId: " + computeFlavourId + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Select zoneid from domain zoneid
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT ResourceZoneId FROM zoneid where zoneId=?");
                ps.setLong(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    zoneId = rs.getLong("ResourceZoneId");

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> ComputeFlavourId: " + computeFlavourId + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Set the ZoneId. Assumption: 1 to 1 Mapping between ZoneId and AbstrZoneId
            abstrResourceZoneId = zoneId;

            //Amount of CPU released from the Capacity related to the couple (nfviPoP, zoneId)
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT numVirtualCpu FROM virtualcpu where computeFlavourId=?");
                ps.setLong(1, computeFlavourId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    numVirtualCpu = rs.getLong("numVirtualCpu");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> numVirtualCpu: " + numVirtualCpu + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Amount of Memory released from the Capacity related to the couple (nfviPoP, zoneId)
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT virtualMemSize FROM `mtpdomdb`.`virtualmemorydata` where computeFlavourId=?");
                ps.setLong(1, computeFlavourId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    memorySize = rs.getLong("virtualMemSize");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> virtualMemSize: " + memorySize + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Amount of Storage released from the Capacity related to the couple (nfviPoP, zoneId)
            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT sizeOfStorage FROM virtualstoragedata where computeFlavourId=?");
                ps.setLong(1, computeFlavourId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    storageSize = storageSize + rs.getLong("sizeOfStorage");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> sizeOfStorage: " + storageSize + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update memory capacity in Domain DB
            try {
                long availableMemCapacity;
                long allocatedMemCapacity;
                long currentAvailableMemCapacity;
                long currentAllocatedMemCapacity;
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                // INNER JOIN SELECT QUERY
                //PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity from memoryresources inner join zoneid on zoneid.NfviPopId=? where memoryresources.resourceZoneId=zoneid.resourceZoneId");
                //STANDARD SELECT QUERY
                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, resourceZoneId from memoryresources where memoryresources.resourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableMemCapacity = rs.getLong("availableCapacity");
                    allocatedMemCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableMemCapacity = availableMemCapacity + memorySize;
                    currentAllocatedMemCapacity = allocatedMemCapacity - memorySize;
                    rs.updateLong("availableCapacity", availableMemCapacity + memorySize);
                    rs.updateLong("allocatedCapacity", allocatedMemCapacity - memorySize);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Memory capacity updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old available  memory capacity " + availableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current available  memory capacity " + currentAvailableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old allocated  memory capacity " + allocatedMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current allocated memory capacity " + currentAllocatedMemCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update memory capacity in Abstraction DB
            try {
                long availableMemCapacity;
                long allocatedMemCapacity;
                long currentAvailableMemCapacity;
                long currentAllocatedMemCapacity;
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, abstrResourceZoneId from abstrmemoryresources where abstrResourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableMemCapacity = rs.getLong("availableCapacity");
                    allocatedMemCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableMemCapacity = availableMemCapacity + memorySize;
                    currentAllocatedMemCapacity = allocatedMemCapacity - memorySize;
                    rs.updateLong("availableCapacity", availableMemCapacity + memorySize);
                    rs.updateLong("allocatedCapacity", allocatedMemCapacity - memorySize);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Memory capacity updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old available  memory capacity " + availableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current available  memory capacity " + currentAvailableMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old allocated  memory capacity " + allocatedMemCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current allocated memory capacity " + currentAllocatedMemCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update storage capacity in Domain DB
            try {
                long availableStorageCapacity;
                long allocatedStorageCapacity;
                long currentAvailableStorageCapacity;
                long currentAllocatedStorageCapacity;
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, resourceZoneId from storageresources where resourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableStorageCapacity = rs.getLong("availableCapacity");
                    allocatedStorageCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableStorageCapacity = availableStorageCapacity + storageSize;
                    currentAllocatedStorageCapacity = allocatedStorageCapacity - storageSize;
                    rs.updateLong("availableCapacity", availableStorageCapacity + storageSize);
                    rs.updateLong("allocatedCapacity", allocatedStorageCapacity - storageSize);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Memory capacity updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old available storage capacity " + availableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current available storage capacity " + currentAvailableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old allocated storage capacity " + allocatedStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current allocated storage capacity " + currentAllocatedStorageCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update storage capacity in Abstraction DB
            try {
                long availableStorageCapacity;
                long allocatedStorageCapacity;
                long currentAvailableStorageCapacity;
                long currentAllocatedStorageCapacity;
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, abstrResourceZoneId from abstrstorageresources where abstrResourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableStorageCapacity = rs.getLong("availableCapacity");
                    allocatedStorageCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableStorageCapacity = availableStorageCapacity + storageSize;
                    currentAllocatedStorageCapacity = allocatedStorageCapacity - storageSize;
                    rs.updateLong("availableCapacity", availableStorageCapacity + storageSize);
                    rs.updateLong("allocatedCapacity", allocatedStorageCapacity - storageSize);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Memory capacity updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old available  storage capacity " + availableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current available  storage capacity " + currentAvailableStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old allocated  storagecapacity " + allocatedStorageCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current allocated storage capacity " + currentAllocatedStorageCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update CPU capacity in Domain DB
            try {
                long availableCpuCapacity;
                long allocatedCpuCapacity;
                long currentAvailableCpuCapacity;
                long currentAllocatedCpuCapacity;
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                // INNER JOIN SELECT QUERY
                //PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity from memoryresources inner join zoneid on zoneid.NfviPopId=? where memoryresources.resourceZoneId=zoneid.resourceZoneId");
                //STANDARD SELECT QUERY
                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, resourceZoneId from cpuresources where resourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, zoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableCpuCapacity = rs.getLong("availableCapacity");
                    allocatedCpuCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableCpuCapacity = availableCpuCapacity + numVirtualCpu;
                    currentAllocatedCpuCapacity = allocatedCpuCapacity - numVirtualCpu;
                    rs.updateLong("availableCapacity", availableCpuCapacity + numVirtualCpu);
                    rs.updateLong("allocatedCapacity", allocatedCpuCapacity - numVirtualCpu);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> CPU capacity updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old available cpu capacity " + availableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current available cpu capacity " + currentAvailableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old allocated cpu capacity " + allocatedCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current allocated cpu capacity " + currentAllocatedCpuCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Update CPU capacity in Abstraction DB
            try {
                long availableCpuCapacity;
                long allocatedCpuCapacity;
                long currentAvailableCpuCapacity;
                long currentAllocatedCpuCapacity;
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("select availableCapacity,reservedCapacity,totalCapacity, allocatedCapacity, abstrResourceZoneId from abstrcpuresources where abstrResourceZoneId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, abstrResourceZoneId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableCpuCapacity = rs.getLong("availableCapacity");
                    allocatedCpuCapacity = rs.getLong("allocatedCapacity");
                    currentAvailableCpuCapacity = availableCpuCapacity + numVirtualCpu;
                    currentAllocatedCpuCapacity = allocatedCpuCapacity - numVirtualCpu;
                    rs.updateLong("availableCapacity", availableCpuCapacity + numVirtualCpu);
                    rs.updateLong("allocatedCapacity", allocatedCpuCapacity - numVirtualCpu);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Memory capacity updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old available cpu capacity " + availableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current available cpu capacity " + currentAvailableCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Old allocated cpu capacity " + allocatedCpuCapacity + "");
                    System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Current allocated cpu capacity " + currentAllocatedCpuCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
//

            //Remove entry from VirtualCPU //
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualcpu WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Entry removed from VirtualCPU table for ComputeServiceId:" + computeServiceId + "");
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from VirtualMemory
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualmemory WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Entry removed from VirtualMemory table for ComputeServiceId:" + computeServiceId + "");
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from VirtualNetworkInterface
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkinterface WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Entry removed from VirtualNetworkInterface table for ComputeServiceId:" + computeServiceId + "");
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from VirtualCompute
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualcompute WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> Entry removed from VirtualCompute table for ComputeServiceId:" + computeServiceId + "");
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from VirtualInterfaceData
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualinterfacedata WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from UserData
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM userdata WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from AffinityOrAntiafffinityConstraint
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM affinityorantiaffinityconstraint WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //Remove entry from ComputeServiceRequestData

            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM computeservicerequestdata WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove entry from ComputeServices
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM computeservices WHERE computeServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, computeServiceId);
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        E2EComputeTerminateInstanceOutcome allinstout = new E2EComputeTerminateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), dboutcome.getComputeIdList());
        System.out.println("ResouceOrchestration --> Post E2EComputeTerminateInstanceOutcome Event");

        SingletonEventBus.getBus().post(allinstout);

    }

    @Subscribe
    public void handle_PhysicalTerminateDBQuery(PhysicalTerminateDBQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle PhysicalTerminateDBQuery Event");

        //START - Retrieve from DB the list of NfviPoP (PopId) where the compute resources to terminate are deployed as well as the
        //list of domains (domId) to call for compute resource termination 
        long dom = -1;
        long pop = -1;
        long abstr_pop = -1;
        // List of Virtual Machine identifiers for which the termination is to be requested 
        String vmId = "";

        String pnfresid= dbquery.getPnfreq().getPnfResId();

        try {
            //String computeServId=computeIdList.remove(0).getNetworkId();

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select domId, mtpdomdb.nfvipop.nfviPopId, mtpdomdb.nfvipop.abstrNfviPopId from mtpdomdb.nfvipop inner join mtpservdb.physicalservices\n"
                    + "on mtpservdb.physicalservices.physicalServiceId=?\n"
                    + "where mtpservdb.physicalservices.nfviPopId=mtpdomdb.nfvipop.nfviPopId");
            ps.setString(1, pnfresid);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                dom = rs.getLong("domId");
                pop = rs.getLong("nfviPopId");
                abstr_pop = rs.getLong("abstrNfviPopId");
                System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQuery ---> DomId: " + rs.getLong("domid") + "");
                System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQuery ---> PopId: " + rs.getLong("NfviPopId") + "");
                System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQuery ---> AbstractPopId: " + rs.getLong("abstrNfviPopId") + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        // Retrieve from DB the physical resources ID
        try {
            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT physicalResourcesId FROM mtpservdb.physicalresources where physicalServiceId=?");
            ps.setString(1, pnfresid);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vmId=rs.getString("physicalResourcesId");
                System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> VM ID: " + rs.getString("physicalResourcesId") + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        //Retrieve pnfid from pnf Request
        String pnfid = "";
        try {
            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT pnfId FROM mtpservdb.physicalservicerequestdata where physicalServiceId=?");
            ps.setString(1, pnfresid);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pnfid = rs.getString("pnfId");
                System.out.println("DatabaseDriver.handle_ComputeTerminateDBQuery ---> PNF ID: " + pnfid + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //ComputeTerminateDBQueryReply dbreply = new ComputeTerminateDBQueryReply(dbquery.getReqId(), dbquery.getServId());
        PhysicalTerminateDBQueryReply dbreply = new PhysicalTerminateDBQueryReply(dbquery.getReqid(), dbquery.getServid(),
                dom, pop, abstr_pop, vmId, pnfid);
        System.out.println("DatabaseDriver ---> Post ComputeTerminateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_PhysicalTerminateDBQueryOutcome(PhysicalTerminateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver ---> Handle PhysicalTerminateDBQueryOutcome Event");
        
        
        if (dboutcome.getPnfresp() == null) {
            E2EPhysicalTerminateInstanceOutcome allinstout = new E2EPhysicalTerminateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), null);
            System.out.println("ResouceOrchestration --> Post E2EPhysicalTerminateInstanceOutcome Event");

            SingletonEventBus.getBus().post(allinstout);
            return;
        }
        
        long physicalserviceId = Long.valueOf(dboutcome.getPnfreq().getPnfResId());
        
        // Set to enabled the PNF status in abstract PNFs tables
        String pnfid = "";
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT pnfId FROM physicalservicerequestdata  WHERE physicalServiceId=? ");
            ps.setLong(1, physicalserviceId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pnfid = rs.getString("pnfId");
                System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQueryOutcome ---> pnfid: " + pnfid + "");
                
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {

            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE pnfs SET pnfstatus=? WHERE abstrNfviPopId=? AND pnfid=?");
            ps.setString(1, "ENABLED");
            ps.setLong(2, dboutcome.getAbstractpopid());
            ps.setString(3, pnfid);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        //set enable PNF in domain DB
        try {

            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE pnfs SET pnfstatus=? WHERE nfviPopId=? AND pnfid=?");
            ps.setString(1, "ENABLED");
            ps.setLong(2, dboutcome.getPopid());
            ps.setString(3, pnfid);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        //Remove entry from physicalresources the entry
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM physicalresources WHERE physicalServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ps.setLong(1, physicalserviceId);
            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQueryOutcome ---> Entry removed from physicalresources table for physicalServiceId:" + physicalserviceId + "");
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        //Remove entry from physicalservicerequestdata the entry
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM physicalservicerequestdata WHERE physicalServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ps.setLong(1, physicalserviceId);
            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQueryOutcome ---> Entry removed from physicalservicerequestdata table for physicalServiceId:" + physicalserviceId + "");
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
         
        //Remove entry from physicalservices the entry
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM physicalservices WHERE physicalServiceId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ps.setLong(1, physicalserviceId);
            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_PhysicalTerminateDBQueryOutcome ---> Entry removed from physicalservices table for physicalServiceId:" + physicalserviceId + "");
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        //format response event
        PNFReply resp = new PNFReply();
        resp.setPnfResId(dboutcome.getPnfreq().getPnfResId());
        resp.setMetaData(dboutcome.getPnfresp().getMetaData());
        E2EPhysicalTerminateInstanceOutcome allinstout = new E2EPhysicalTerminateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), resp);
        System.out.println("ResouceOrchestration --> Post E2EPhysicalTerminateInstanceOutcome Event");

        SingletonEventBus.getBus().post(allinstout);

    }
    
    @Subscribe
    public void handle_IntraPoPTerminateDbRequest(IntraPoPTerminateDbRequest dbquery) {
        System.out.println("DatabaseDriver ---> Handle IntraPoPTerminateDbRequest Event");
        // Retrieve list of domid and network resource id to terminate.
        List<Long> domlist = new ArrayList<Long>();
        List<String> domreslist = new ArrayList<String>();
        List<String> subnetreslist = new ArrayList<String>();
        List<String> portreslist = new ArrayList<String>();
        List<Long> nfvipoplist = new ArrayList<Long>();
        long virtualnetid = 0;

        for (int i = 0; i < dbquery.getNetworklist().size(); i++) {

            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT virtualnetworkId,networkResourceId,nfviPopId FROM virtualnetwork where netServId=?");
                ps.setString(1, dbquery.getNetworklist().get(i));

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    virtualnetid = rs.getInt("virtualnetworkId");
                    domreslist.add(rs.getString("networkResourceId"));
                    nfvipoplist.add(rs.getLong("nfviPopId"));
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //Select subnet
            String subnetresvalue = new String("");
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT resourceId FROM networksubnet where virtualnetworkId=?");
                ps.setLong(1, virtualnetid);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    subnetresvalue = rs.getString("resourceId");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            subnetreslist.add(subnetresvalue);

            //Select port
            String portresvalue = new String("");
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT resourceId FROM virtualnetworkport where virtualnetworkId=?");
                ps.setLong(1, virtualnetid);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    portresvalue = rs.getString("resourceId");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            portreslist.add(portresvalue);
        }

        //Get from DB the vimdomlist related to the vimPopList
        for (int j = 0; j < nfvipoplist.size(); j++) {

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT domId "
                        + "FROM nfvipop "
                        + "where abstrNfviPopId=?");
                ps.setLong(1, nfvipoplist.get(j));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    domlist.add(rs.getLong("domId"));

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

////HACK R2: Set domid = 1 and List String the same
//        for (int i = 0; i < dbquery.getNetworklist().size(); i++) {
//            domlist.add(Long.valueOf(1));
//        }
        //fomat event
        IntraPoPTerminateVIMRequest vimreq = new IntraPoPTerminateVIMRequest(dbquery.getReqid(),
                domlist, dbquery.getNetworklist(), subnetreslist, portreslist, domreslist);
        System.out.println("ResouceOrchestration --> Post IntraPoPTerminateVIMRequest Event");

        SingletonEventBus.getBus().post(vimreq);
    }

    @Subscribe
    public void handle_IntraPoPTerminateVIMReply(IntraPoPTerminateVIMReply dbquery) {
        System.out.println("DatabaseDriver ---> Handle IntraPoPTerminateVIMReply Event");

        //TODO R2: remove service request from DB
        for (int i = 0; i < dbquery.getNetreslist().size(); i++) {
            String netServId = null;
            List<Long> virtualnetworkIdList = new ArrayList();

            netServId = dbquery.getNetreslist().get(i);

            // Retrieve from DB all virtualnetworkId related to the netservid
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT virtualNetworkId FROM virtualnetwork WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQueryOutcome Event - VirtualNetworkId:" + rs.getLong("virtualNetworkId") + "");

                    virtualnetworkIdList.add(rs.getLong("virtualNetworkId"));
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from Network_QoS table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM supportednetworkqos WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();

                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from VIRTUAL_NETWORK_PORT table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkport WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from NETWORK_SUBSET table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM networksubnet WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from VirtualNetwork table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetwork WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));
                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from networkservicerequestdata table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservicerequestdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            // Remove record from networkservices table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservices WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        //Send event
        IntraPoPTerminateDbReply dbreply = new IntraPoPTerminateDbReply(dbquery.getReqid(), dbquery.getNetreslist());
        System.out.println("ResouceOrchestration --> Post IntraPoPTerminateDbReply Event");
        SingletonEventBus.getBus().post(dbreply);
    }

    @Subscribe
    public void handle_NetworkTerminateDBQuery(NetworkTerminateDBQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQuery Event");

        //START - Retrieve from DB the list of wanLinks, interDomainLinks and intraPopLinks related to the
        // logicalPathId to terminate
        List<String> netServIdList = dbquery.getNetServIdList();
        List<String> logicalPathIdList = new ArrayList();
        HashMap<Integer, ArrayList<Long>> interdomainLinksMap = new HashMap();
        HashMap<Integer, ArrayList<String>> intraPopLinksMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wanLinksMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wimPopListMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> vimPopListMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wimdomlistMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> vimdomlistMap = new HashMap();
        HashMap<Integer, ArrayList<String>> vimNetworkTypeMap = new HashMap();
        HashMap<Integer, ArrayList<String>> wimNetworkTypeMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wanResourceIdListMap = new HashMap();

        //Retrieve from DB the locicalPaths related to the netServIds
        for (int i = 0; i < dbquery.getNetServIdList().size(); i++) {
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId FROM networkservices where netServId=?");
                ps.setString(1, dbquery.getNetServIdList().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    // System.out.println("Test: " + rs.getInt("logicalPathId") + "");
                    logicalPathIdList.add(String.valueOf(rs.getInt("logicalPathId")));
                    System.out.println("handle_NetworkTerminateDBQuery ---> logicalPathId: " + rs.getInt("logicalPathId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        //START - Loop on logicalPaths
        for (int i = 0; i < logicalPathIdList.size(); i++) {
            ArrayList<Long> interdomainLinks = new ArrayList();
            ArrayList<String> intraPopLinks = new ArrayList();
            ArrayList<Long> wanLinks = new ArrayList();
            ArrayList<Long> wimPopList = new ArrayList();
            ArrayList<Long> vimPopList = new ArrayList();
            ArrayList<Long> wimdomlist = new ArrayList();
            ArrayList<Long> vimdomlist = new ArrayList();
            ArrayList<String> vimNetworkType = new ArrayList();
            ArrayList<String> wimNetworkType = new ArrayList();
            ArrayList<Long> wanResourceIdList = new ArrayList();

            String logicalPathId = logicalPathIdList.get(i);
            ArrayList<Integer> wimResourceIndex = new ArrayList();

            ////Get from DB the intraPopLinks, wanLinks, wimPopList, vimPopList, vimNetworkType and wimNetworkType related to the LogicalLinkId to terminate
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId FROM logicalpath_interdomainlink where logicalPathId=?");
                ps.setString(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    interdomainLinks.add(Long.valueOf(rs.getInt("interDomainLinkId")));

                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getInt("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Get from DB the intraPopLinks, wanLinks, wimPopList, vimPopList and networkType
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalpath_networkresources.networkResId, logicalpath_networkresources.logicalPathId, networkresources.networkType, zoneid.nfviPopId, domain.type, domain.domId from logicalpath_networkresources \n"
                        + "INNER JOIN networkresources ON networkresources.networkResId=logicalpath_networkresources.networkResId\n"
                        + " INNER JOIN zoneid ON  networkresources.resourceZoneId=zoneid.resourceZoneId \n"
                        + "INNER JOIN nfvipop ON zoneid.nfviPopId = nfvipop.nfviPopId\n"
                        + "INNER JOIN domain ON nfvipop.domId = domain.domId\n"
                        + "where logicalpath_networkresources.logicalPathId=?");

                ps.setString(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String type = rs.getString("type");
//                    if (type.equals("VIM")) {
//                        intraPopLinks.add(rs.getLong("networkResId"));
//                        vimPopList.add(rs.getLong("nfviPopId"));
//                        vimdomlist.add(rs.getLong("domId"));
//                        vimNetworkType.add(rs.getString("networkType"));
//                        System.out.println("handle_NetworkAllocateDBQuery ---> IntraPopLink - NetworkResId: " + rs.getLong("NetworkResId") + "");
//                    }

                    if (type.equals("T-WIM")) {
                        wanLinks.add(rs.getLong("networkResId"));
                        wimPopList.add(rs.getLong("nfviPopId"));
                        wimdomlist.add(rs.getLong("domId"));
                        wimNetworkType.add(rs.getString("networkType"));
                        System.out.println("handle_NetworkAllocateDBQuery ---> WanLink - NetworkResId: " + rs.getLong("NetworkResId") + "");

                    }

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Get from DB the networkResourceId of WAN resources
            for (int j = 0; j < wanLinks.size(); j++) {
                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT networkResourceId FROM virtualnetwork where netServId=? and netResIdRef=?");
                    ps.setString(1, dbquery.getNetServIdList().get(i));
                    ps.setLong(2, wanLinks.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        wanResourceIdList.add(rs.getLong("networkResourceId"));

                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Get from DB the intraPopLinks, vimPopList and networkType
            //Get All Virtual Network
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT networkResourceId, networkType, nfviPopId FROM virtualnetwork where netServId=? and netResIdRef is null");
                ps.setString(1, dbquery.getNetServIdList().get(i));

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    intraPopLinks.add(rs.getString("networkResourceId"));
                    vimPopList.add(rs.getLong("nfviPopId"));
                    vimNetworkType.add(rs.getString("networkType"));

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//            //Get from DB the vimdomlist related to the vimPopList
            for (int j = 0; j < vimPopList.size(); j++) {

                try {
                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT nfvipop.domId, domain.type "
                            + "FROM nfvipop "
                            + "INNER JOIN domain ON domain.domId=nfvipop.domId "
                            + "where nfviPopId=?");
                    ps.setLong(1, vimPopList.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        if (rs.getString("type").equals("VIM")) {

                            vimdomlist.add(rs.getLong("domId"));
                        }
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            interdomainLinksMap.put(i, interdomainLinks);
            intraPopLinksMap.put(i, intraPopLinks);
            wanLinksMap.put(i, wanLinks);
            wimPopListMap.put(i, wimPopList);
            vimPopListMap.put(i, vimPopList);
            wimdomlistMap.put(i, wimdomlist);
            vimdomlistMap.put(i, vimdomlist);
            vimNetworkTypeMap.put(i, vimNetworkType);
            wimNetworkTypeMap.put(i, wimNetworkType);
            wanResourceIdListMap.put(i, wanResourceIdList);

        }
        //END - Loop on logicalPaths

//        NetworkTerminateDBQueryReply dbreply = new NetworkTerminateDBQueryReply(dbquery.getReqId(), dbquery.getServId());        
        NetworkTerminateDBQueryReply dbreply = new NetworkTerminateDBQueryReply(dbquery.getReqid(), dbquery.getServid(),
                wimdomlistMap, vimdomlistMap, interdomainLinksMap, intraPopLinksMap, wanLinksMap, wimPopListMap, vimPopListMap,
                wimNetworkTypeMap, vimNetworkTypeMap, wanResourceIdListMap, netServIdList, logicalPathIdList);

        System.out.println("DatabaseDriver ---> Post NetworkTerminateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_NetworkTerminateDBQueryOutcome(NetworkTerminateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQueryOutcome Event");
        //TODO update DB using JDBC raising exception if somethiong is wrong

        //Loop on logicalPaths 
        for (int i = 0; i < dboutcome.getLogicalPathList().size(); i++) {
            String netServId = null;
            long bandwidth = 0;
            List<Long> virtualnetworkIdList = new ArrayList();

            netServId = dboutcome.getNetServIdList().get(i);

            // Retrieve from DB all virtualnetworkId related to the logicalPathId
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT virtualNetworkId FROM virtualnetwork WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQueryOutcome Event - VirtualNetworkId:" + rs.getLong("virtualNetworkId") + "");

                    virtualnetworkIdList.add(rs.getLong("virtualNetworkId"));
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Amount of Bandwidth released from the Capacity
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT bandwidth FROM virtualnetworkdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    bandwidth = rs.getLong("bandwidth");

                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from Network_QoS table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM supportednetworkqos WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();

                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from VIRTUAL_NETWORK_PORT table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkport WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from NETWORK_SUBSET table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM networksubnet WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from VirtualNetwork table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetwork WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));
                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from NetQoS table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkqos WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//Remove records from Layer3ConnectivityInformation table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM layer3connectivityinformation WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from VirtualNetworkData table 
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //Remove records from VirtualNetworkPortData table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkportdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from NetworkSubnetData table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networksubnetdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from networkservicerequestdata table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservicerequestdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

// Remove record from networkservices table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservices WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            /**
             * ****************************************************************************************
             */
            // UNCOMMENT WHEN IFA005 API bugs are fixed
            // bandwidth = 5000000;
            /**
             * ***************************************************************************************
             */
//Update Capacity in Abstraction DB
            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long currentAvailableBandwidth;
                long currentAllocatedBandwidth;

                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                //STANDARD SELECT QUERY
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId, availableBandwidth, reservedBandwidth,"
                        + " totalBandwidth, allocatedBandwidth\n"
                        + "FROM logicalpath WHERE logicalPathId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, dboutcome.getLogicalPathList().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableBandwidth");
                    allocatedBandwidth = rs.getLong("allocatedBandwidth");
                    currentAvailableBandwidth = availableBandwidth + bandwidth;
                    currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                    rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                    rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//Update Capacity in Domain DB 
            //Update InterDomainLink capacity
            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in InterDomainLink table!");
            for (int j = 0; j < dboutcome.getInterdomainLinksMap().get(i).size(); j++) {

                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long currentAvailableBandwidth;
                    long currentAllocatedBandwidth;

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth,"
                            + " totalBandwidth, allocatedBandwidth\n"
                            + "FROM interdomainlink WHERE interDomainLinkId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, dboutcome.getInterdomainLinksMap().get(i).get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableBandwidth");
                        allocatedBandwidth = rs.getLong("allocatedBandwidth");
                        currentAvailableBandwidth = availableBandwidth + bandwidth;
                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                        rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                        rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                        rs.updateRow();

                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

//            //Update IntraPopLinks capacity
//            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in NETWORKRESOURCES table!");
//            for (int j = 0; j < dboutcome.getIntraPopLinksMap().get(i).size(); j++) {
//
//                try {
//                    long availableBandwidth;
//                    long allocatedBandwidth;
//                    long currentAvailableBandwidth;
//                    long currentAllocatedBandwidth;
//
//                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
//
//                    PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity,"
//                            + " totalCapacity, allocatedCapacity\n"
//                            + "FROM networkresources WHERE networkResId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//                    ps.setLong(1, dboutcome.getIntraPopLinksMap().get(i).get(j));
//                    java.sql.ResultSet rs = ps.executeQuery();
//                    while (rs.next()) {
//                        availableBandwidth = rs.getLong("availableCapacity");
//                        allocatedBandwidth = rs.getLong("allocatedCapacity");
//                        currentAvailableBandwidth = availableBandwidth + bandwidth;
//                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
//                        rs.updateLong("availableCapacity", currentAvailableBandwidth);
//                        rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
//                        rs.updateRow();
//
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
//                    }
//
//                    rs.close();
//                    ps.close();
//
//                } catch (SQLException ex) {
//                    Logger.getLogger(DatabaseDriver.class
//                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                }
//
//            }
            //Update WanLinks capacity
            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in NETWORKRESOURCES table!");
            for (int j = 0; j < dboutcome.getWanLinksMap().get(i).size(); j++) {

                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long currentAvailableBandwidth;
                    long currentAllocatedBandwidth;

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity,"
                            + " totalCapacity, allocatedCapacity\n"
                            + "FROM networkresources WHERE networkResId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, dboutcome.getWanLinksMap().get(i).get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableCapacity");
                        allocatedBandwidth = rs.getLong("allocatedCapacity");
                        currentAvailableBandwidth = availableBandwidth + bandwidth;
                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                        rs.updateLong("availableCapacity", currentAvailableBandwidth);
                        rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
                        rs.updateRow();

                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

        } //END Loop on LogicalPaths

        E2ENetworkTerminateInstanceOutcome netinst = new E2ENetworkTerminateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), dboutcome.getNetServIdList(), dboutcome.getLogicalPathList());
        System.out.println("DatabaseDriver --> Post E2ENetworkTerminateInstanceOutcome Event");
        SingletonEventBus.getBus().post(netinst);

    }

    //////////////////Advertisement Federated Resources Event Handlers///////////////////////////////
    @Subscribe
    public void handle_AdvertiseE2EFederatedRequest(AdvertiseE2EFederationRequest e2eadvreq) {
        System.out.println("DatabaseDriver ---> Handle AdvertiseE2EFederatedRequest Event");
        System.out.println("field event: (reqid) = ("
                + e2eadvreq.getReqId() + ")");

        InlineResponse2005 resp = new InlineResponse2005();
        List<NfviPop> poplist = new ArrayList();
        List<NfviPopsInnerNfviPopAttributes> popattrlist = new ArrayList();

        //Retrieve NFVIPOP
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("Select * from nfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(rs.getString("nfviPopId"));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("federatedVimId"));
                nfviPop.setVimId(rs.getString("federatedVimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));

                System.out.println("nfviPop.nfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("nfviPop.FederatedVimId: " + nfviPop.getVimId() + "");
                System.out.println("nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                poplist.add(nfviPop);

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        NfviPops popslist = new NfviPops();
        //retrieve zone and resources for each nfvipop
        for (int i = 0; i < poplist.size(); i++) {
            NfviPop popel = poplist.get(i);
            NfviPopsInnerNfviPopAttributes popattr = new NfviPopsInnerNfviPopAttributes();
            //fill nfvipop values
            popattr.setVimId(popel.getVimId());
            popattr.setFederatedVimId(popel.getVimId());
            popattr.setNfviPopId(popel.getNfviPopId());
            popattr.setGeographicalLocationInfo(popel.getGeographicalLocationInfo());
            //fill connectivity endpoint
            List<NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint> endplist = new ArrayList();
            String popstr = popel.getNetworkConnectivityEndpoint();
            String[] poptoken = popstr.split(";");
            for (int tok = 0; tok < poptoken.length; tok++) {
                NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint endpel = new NfviPopsInnerNfviPopAttributesNetworkConnectivityEndpoint();
                endpel.setNetGwIpAddress(poptoken[tok]);
                endplist.add(endpel);
            }
            popattr.setNetworkConnectivityEndpoint(endplist);

            NfviPopsInner popinel = new NfviPopsInner();
            popinel.setNfviPopAttributes(popattr);

            popslist.add(popinel);

        } //NFVIPOP FOR END
        //add nfvipops in respones
        resp.setNfviPops(popslist);

        //Insert logical link info
        LogicalLinkInterNfviPops linklist = new LogicalLinkInterNfviPops();

        //retrieve logical link info
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT fed_logicallink.logicalLinkId, fed_logicallink.srcRouterId, fed_logicallink.dstRouterId, "
                    + "fed_logicallink.srcRouterIp, fed_logicallink.dstRouterIp, fed_logicallink.availableBandwidth, fed_logicallink.totalBandwidth, "
                    + "fed_logicallink.networkLayer, fed_logicallink.interNfviPopNetworkType, fed_logicallink.interNfviPopNetworkTopology, fed_logicallink.cost, fed_logicallink.delay \n"
                    + "FROM fed_logicallink");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LogicalLinkInterNfviPopsInner linkpopel = new LogicalLinkInterNfviPopsInner();
                LogicalLinkInterNfviPopsInnerLogicalLinks linkinel = new LogicalLinkInterNfviPopsInnerLogicalLinks();
                LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS linkinqos = new LogicalLinkInterNfviPopsInnerLogicalLinksNetworkQoS();

                //retrieve network qos
                linkinqos.setLinkCost("metric");
                linkinqos.setLinkCostValue(new BigDecimal(rs.getShort("cost")));
                linkinqos.setLinkDelay("delay");
                linkinqos.setLinkDelayValue(new BigDecimal(rs.getShort("delay")));
                linkinel.setNetworkQoS(linkinqos);

                //retrieve network qos
                linkinqos.setLinkCost("metric");
                linkinqos.setLinkCostValue(BigDecimal.ONE);
                linkinqos.setLinkDelay("delay");
                linkinqos.setLinkDelayValue(new BigDecimal(rs.getShort("delay")));
                linkinel.setNetworkQoS(linkinqos);

                //set inner element
                linkinel.setAvailableBandwidth(new BigDecimal(rs.getString("availableBandwidth")));
                linkinel.setDstGwIpAddress(rs.getString("dstRouterId"));
                linkinel.setInterNfviPopNetworkTopology(rs.getString("interNfviPopNetworkTopology"));
                linkinel.setInterNfviPopNetworkType(rs.getString("interNfviPopNetworkType"));

                try {
                    InetAddress locip = InetAddress.getByName(rs.getString("srcRouterIp"));
                    int locval = 0;
                    for (byte b : locip.getAddress()) {
                        locval = locval << 8 | (b & 0xFF);
                    }
                    linkinel.setLocalLinkId(locval);
                } catch (UnknownHostException e) {
                    System.out.println("error local link id" + "");
                }

                linkinel.setLogicalLinkId(rs.getString("logicalLinkId"));
                try {
                    InetAddress remip = InetAddress.getByName(rs.getString("dstRouterIp"));
                    int remval = 0;
                    for (byte b : remip.getAddress()) {
                        remval = remval << 8 | (b & 0xFF);
                    }
                    linkinel.setRemoteLinkId(remval);
                } catch (UnknownHostException e) {
                    System.out.println("error local link id" + "");
                }

                linkinel.setSrcGwIpAddress(rs.getString("srcRouterId"));
                linkinel.setTotalBandwidth(new BigDecimal(rs.getString("totalBandwidth")));
                linkinel.setNetworkLayer(rs.getString("networkLayer"));

                System.out.println("link.delay: " + linkinqos.getLinkDelayValue() + "");
                System.out.println("link.availableBandwidth: " + linkinel.getAvailableBandwidth() + "");
                System.out.println("link.totalBandwidth: " + linkinel.getTotalBandwidth() + "");
                System.out.println("link.srcGwId: " + linkinel.getSrcGwIpAddress() + "");
                System.out.println("link.dstGwId: " + linkinel.getDstGwIpAddress() + "");
                System.out.println("link.localid: " + linkinel.getLocalLinkId() + "");
                System.out.println("link.remoteid: " + linkinel.getRemoteLinkId() + "");

                linkpopel.setLogicalLinks(linkinel);
                linklist.add(linkpopel);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //add linklist in response
        resp.setLogicalLinkInterNfviPops(linklist);

        AdvertiseE2EFederationReply e2eadvrep = new AdvertiseE2EFederationReply(e2eadvreq.getReqId(), resp);
        System.out.println("DatabaseDriver --->  Post AdvertiseE2EFederationReply Event");
        SingletonEventBus.getBus().post(e2eadvrep);
    }

    @Subscribe
    public void handle_Fed_NetworkAllocateDBQuery(Fed_NetworkAllocateDBQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle NetworkAllocateDBQuery Event");
        long logicalPathId = dbquery.getLogicalpath();
        ArrayList<Long> interdomainLinks = new ArrayList();
        ArrayList<String> interdomainLinksType = new ArrayList(); //the value is "F" if fed_interdomainLink while is "S" for standard interdomain. This array is not used but may be useful for future exstensions  
        ArrayList<String> intraPopLinks = new ArrayList();
        ArrayList<Long> wanLinks = new ArrayList();
        ArrayList<Long> wimPopList = new ArrayList();
        ArrayList<Long> vimPopList = new ArrayList();
        ArrayList<Long> wimdomlist = new ArrayList();
        ArrayList<Long> vimdomlist = new ArrayList();
        ArrayList<String> wimNetworkType = new ArrayList();
        ArrayList<String> vimNetworkType = new ArrayList();
        long abstrSrcNfviPopId = -1;
        long abstrDestNfviPopId = -1;
        long srcNfviPopId = -1;
        long destNfviPopId = -1;

        ArrayList<String> networkConnectivityEndpointList = new ArrayList();
        boolean abstrSrcNfviPopOfLogicalPathIsFed = false;
        String srcRouterId = null;
        String dstRouterId = null;
        String fed_vlanid = null;
        ArrayList<NetworkEndpoints> flowRuleEndPointList = new ArrayList();;
        String ingrip = null;
        String egrip = null;
        String ingrport = null;
        String egrport = null;

        // Set the fed_vlanid 
        fed_vlanid = dbquery.getFed_vlanid();

        //TODO perform query in DB using JDBC
        // START - Insert servID into Service table
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
            ps.setString(1, dbquery.getServid());

            ps.executeUpdate();
            ps.close();

        } catch (SQLIntegrityConstraintViolationException ex) {
//            Logger.getLogger(ResourceSelectionLogic.class
//                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("handle_NetworkAllocateDBQuery ---> SQLIntegrityConstraintViolationException: ServiceId is already in the DB");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
// END - Insert

        //RETRIEVE all networkConnectivityEndpoints from nfvipop table in FEDERATION DB
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM nfvipop");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String networkConnectivityEndpoint = rs.getString("networkConnectivityEndpoint");
                String[] vals = networkConnectivityEndpoint.split(";");

                for (int i = 0; i < vals.length; i++) {

                    networkConnectivityEndpointList.add(vals[i]);
                }

                System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getString("networkConnectivityEndpoint") + "");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Check if the srcRouter of selected logicalPath is located in a federated domain
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT abstrSrcNfviPopId, abstrDestNfviPopId, srcRouterId, dstRouterId  FROM fed_logicalpath where logicalPathId=?");
            ps.setLong(1, logicalPathId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                abstrSrcNfviPopId = rs.getLong("abstrSrcNfviPopId");
                abstrDestNfviPopId = rs.getLong("abstrDestNfviPopId");
                srcRouterId = rs.getString("srcRouterId");
                dstRouterId = rs.getString("dstRouterId");

                if (networkConnectivityEndpointList.contains(srcRouterId)) {
                    abstrSrcNfviPopOfLogicalPathIsFed = true;
                } else {
                    abstrSrcNfviPopOfLogicalPathIsFed = false;
                }

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Retrieve from DB the srcNfviPopId and dstNfviPopId related to abstrSrcNfviPopId and  abstrdstNfviPopId, respectively
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT nfviPopId FROM mtpdomdb.nfvipop where abstrNfviPopId=?");
            ps.setLong(1, abstrSrcNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                srcNfviPopId = rs.getLong("nfviPopId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT nfviPopId FROM mtpdomdb.nfvipop where abstrNfviPopId=?");
            ps.setLong(1, abstrDestNfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                destNfviPopId = rs.getLong("nfviPopId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        if (abstrSrcNfviPopOfLogicalPathIsFed == false) {

            //Get from DB the interdomainLinks 
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId  FROM fed_logicalpath_interdomainlink where logicalPathId=?");
                ps.setLong(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    interdomainLinks.add(rs.getLong("interDomainLinkId"));
                    interdomainLinksType.add("S");
                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getLong("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //GET from DB the fed_interdomainLinks
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId  FROM fed_logicalpath_fed_interdomainlink where logicalPathId=?");
                ps.setLong(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    interdomainLinks.add(rs.getLong("interDomainLinkId"));
                    interdomainLinksType.add("F");
                    //  interdomainLinksToFed.add(true);

                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getLong("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            //GET from DB the fed_interdomainLinks
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId  FROM fed_logicalpath_fed_interdomainlink where logicalPathId=?");
                ps.setLong(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    interdomainLinks.add(rs.getLong("interDomainLinkId"));
                    interdomainLinksType.add("F");

                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getLong("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Get from DB the interdomainLinks 
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId  FROM fed_logicalpath_interdomainlink where logicalPathId=?");
                ps.setLong(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    interdomainLinks.add(rs.getLong("interDomainLinkId"));
                    interdomainLinksType.add("S");

                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getLong("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        //Get from DB the  wanLinks, wimPopList and networkType
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT fed_logicalpath_networkresources.networkResId, fed_logicalpath_networkresources.logicalPathId, mtpdomdb.networkresources.networkType, mtpdomdb.zoneid.nfviPopId, mtpdomdb.domain.type, mtpdomdb.domain.domId from fed_logicalpath_networkresources \n"
                    + "INNER JOIN mtpdomdb.networkresources ON mtpdomdb.networkresources.networkResId=fed_logicalpath_networkresources.networkResId\n"
                    + " INNER JOIN mtpdomdb.zoneid ON  mtpdomdb.networkresources.resourceZoneId=mtpdomdb.zoneid.resourceZoneId \n"
                    + "INNER JOIN mtpdomdb.nfvipop ON mtpdomdb.zoneid.nfviPopId = mtpdomdb.nfvipop.nfviPopId\n"
                    + "INNER JOIN mtpdomdb.domain ON mtpdomdb.nfvipop.domId = mtpdomdb.domain.domId\n"
                    + "where fed_logicalpath_networkresources.logicalPathId=?");

            ps.setLong(1, logicalPathId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String type = rs.getString("type");
//                if (type.equals("VIM")) {
//                    intraPopLinks.add(rs.getLong("networkResId"));
//                    vimPopList.add(rs.getLong("nfviPopId"));
//                    vimdomlist.add(rs.getLong("domId"));
//                    vimNetworkType.add(rs.getString("networkType"));
//                    System.out.println("handle_NetworkAllocateDBQuery ---> IntraPopLink - NetworkResId: " + rs.getLong("NetworkResId") + "");
//                }

                if (type.equals("T-WIM")) {
                    wanLinks.add(rs.getLong("networkResId"));
                    wimPopList.add(rs.getLong("nfviPopId"));
                    wimdomlist.add(rs.getLong("domId"));
                    wimNetworkType.add(rs.getString("networkType"));
                    System.out.println("handle_NetworkAllocateDBQuery ---> WanLink - NetworkResId: " + rs.getLong("NetworkResId") + "");
                }

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

//        //Get from logicalpath table the abstrSrcNfviPop and abstrDstNfviPop to request intraPop connectivities
//        try {
//            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
//            PreparedStatement ps = conn.prepareStatement("SELECT abstrSrcNfviPopId, abstrDestNfviPopId, srcRouterId, dstRouterId  FROM fed_logicalpath where logicalPathId=?");
//            ps.setLong(1, logicalPathId);
//            java.sql.ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                abstrSrcNfviPopId = rs.getLong("abstrSrcNfviPopId");
//                abstrDestNfviPopId = rs.getLong("abstrDestNfviPopId");
//                 srcRouterId = rs.getString("srcRouterId"); 
//                dstRouterId = rs.getString("dstRouterId");
//                
//                 if(networkConnectivityEndpointList.contains(srcRouterId)) {
//                //  abstrSrcNfviPopIsFed = true;
//               }
//               
//                if(networkConnectivityEndpointList.contains(dstRouterId)) {
//               //   abstrDstNfviPopIsFed = true;
//               }
//                
//
//            }
//            rs.close();
//            ps.close();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseDriver.class
//                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//        }
        if (abstrSrcNfviPopOfLogicalPathIsFed == false) {

            //Get from DB the vimPoP and vimDomain related to the abstrSrcNfviPop 
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT nfvipop.nfviPopId, domain.domId from nfvipop INNER JOIN domain ON domain.domId=nfvipop.domId where nfvipop.abstrNfviPopId=?");

                ps.setLong(1, abstrSrcNfviPopId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    vimPopList.add(rs.getLong("nfviPopId"));
                    vimdomlist.add(rs.getLong("domId"));

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //select intraponetworkreference id for intrpoplink source
            try {

                String srcVnfIpAddress = "";
                MetaData metadata = dbquery.getNetworkreq().getLogicalLinkPathList().get(0).getMetaData();
                String refintra = "";

                //srcVnfIpAddress.compareTo(srcVnfIpAddress)
                for (int i = 0; i < metadata.size(); i++) {
                    if (metadata.get(i).getKey().compareTo("srcVnfIpAddress") == 0) {
                        srcVnfIpAddress = metadata.get(i).getValue();

                    }
                }

                // IPAddress address = new IPAddressString(srcVnfIpAddress).getAddress();
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("select networksubnet.cidr, networksubnet.gatewayIp, virtualnetwork.networkResourceId,virtualnetwork.networkResourceName,virtualnetwork.segmentType,virtualnetwork.networkType "
                        + "from networksubnet "
                        + "inner join virtualnetwork on virtualnetwork.virtualNetworkId=networksubnet.virtualnetworkId "
                        + "inner join networkservices on virtualnetwork.netServId=networkservices.netServId "
                        + "where networkservices.servId =? AND networkservices.provider<>? AND virtualnetwork.NfviPopId=?");

                ps.setString(1, dbquery.getServid());
                ps.setString(2, "");
                ps.setLong(3, abstrSrcNfviPopId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    IPAddress cidr = new IPAddressString(rs.getString("cidr")).getAddress();

                    if (cidr.contains(new IPAddressString(srcVnfIpAddress).getAddress())) {
                        refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                        vimNetworkType.add(rs.getString("networkType"));
                    }
                    //String refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                    // intraPopLinks.add(refintra);

                }
                intraPopLinks.add(refintra);
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Add in the intraPopLinks the vlanid provided by Federation domain
            vimPopList.add(Long.valueOf(-1));
            vimdomlist.add(Long.valueOf(-1));
            String refintra = "fed" + ";" + "fed" + ";" + fed_vlanid;
            intraPopLinks.add(refintra);
            vimNetworkType.add("VLAN");
        } else {

            //Add in the intraPopLinks the vlanid provided by Federation domain
            vimPopList.add(Long.valueOf(-1));
            vimdomlist.add(Long.valueOf(-1));
            intraPopLinks.add("fed" + ";" + "fed" + ";" + fed_vlanid);
            vimNetworkType.add("VLAN");

            //Get from DB the vimPoP and vimDomain related to the abstrDestNfviPop 
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT nfvipop.nfviPopId, domain.domId from nfvipop INNER JOIN domain ON domain.domId=nfvipop.domId where nfvipop.abstrNfviPopId=?");

                ps.setLong(1, abstrDestNfviPopId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    vimPopList.add(rs.getLong("nfviPopId"));
                    vimdomlist.add(rs.getLong("domId"));

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //select intraponetworkreference id for intrpoplink dest
            try {

                String dstVnfIpAddress = "";
                MetaData metadata = dbquery.getNetworkreq().getLogicalLinkPathList().get(0).getMetaData();
                String refintra = "";

                for (int i = 0; i < metadata.size(); i++) {
                    if (metadata.get(i).getKey().compareTo("dstVnfIpAddress") == 0) {
                        dstVnfIpAddress = metadata.get(i).getValue();

                    }
                }
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("select networksubnet.cidr, networksubnet.gatewayIp, virtualnetwork.networkResourceId,virtualnetwork.networkResourceName,virtualnetwork.segmentType, virtualnetwork.networkType "
                        + "from networksubnet "
                        + "inner join virtualnetwork on virtualnetwork.virtualNetworkId=networksubnet.virtualnetworkId "
                        + "inner join networkservices on virtualnetwork.netServId=networkservices.netServId "
                        + "where networkservices.servId =? AND networkservices.provider<>? AND virtualnetwork.NfviPopId=?");

                ps.setString(1, dbquery.getServid());
                ps.setString(2, "");
                ps.setLong(3, abstrDestNfviPopId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    IPAddress cidr = new IPAddressString(rs.getString("cidr")).getAddress();

                    if (cidr.contains(new IPAddressString(dstVnfIpAddress).getAddress())) {
                        refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                        vimNetworkType.add(rs.getString("networkType"));
                    }

                    //String refintra = rs.getString("networkResourceId") + ";" + rs.getString("networkResourceName") + ";" + rs.getString("segmentType");
                    //intraPopLinks.add(refintra);
                }
                intraPopLinks.add(refintra);
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        //Retrieve the flowRuleEndPointList
        if (abstrSrcNfviPopOfLogicalPathIsFed == false) {
            try {
                //select src endpoint
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT dstGwId,dstGwIp  FROM interdomainlink where interDomainLinkId=?");
                ps.setLong(1, interdomainLinks.get(0));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    ingrip = rs.getString("dstGwId");
                    ingrport = rs.getString("dstGwIp");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            try {
                //select dst endpoint
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT srcGwId,srcGwIp  FROM fed_interdomainlink where interDomainLinkId=?");
                ps.setLong(1, interdomainLinks.get(1));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    egrip = rs.getString("srcGwId");
                    egrport = rs.getString("srcGwIp");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        } else {

            try {
                //select src endpoint
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT dstGwId,dstGwIp  FROM fed_interdomainlink where interDomainLinkId=?");
                ps.setLong(1, interdomainLinks.get(0));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    ingrip = rs.getString("dstGwId");
                    ingrport = rs.getString("dstGwIp");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            try {
                //select dst endpoint
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT srcGwId,srcGwIp  FROM interdomainlink where interDomainLinkId=?");
                ps.setLong(1, interdomainLinks.get(1));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    egrip = rs.getString("srcGwId");
                    egrport = rs.getString("srcGwIp");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        //create endpoint
        NetworkEndpoints endp = new NetworkEndpoints(ingrip, egrip, ingrport, egrport);
        flowRuleEndPointList.add(endp);

        //NetworkAllocateDBQueryReply dbreply = new NetworkAllocateDBQueryReply(dbquery.getReqId(), dbquery.getServId());
        Fed_NetworkAllocateDBQueryReply dbreply = new Fed_NetworkAllocateDBQueryReply(dbquery.getReqid(), dbquery.getServid(),
                logicalPathId, wimdomlist, vimdomlist, interdomainLinks, intraPopLinks, wanLinks, wimPopList, vimPopList, wimNetworkType,
                vimNetworkType, flowRuleEndPointList, abstrSrcNfviPopOfLogicalPathIsFed);

        System.out.println("DatabaseDriver ---> Post Fed_NetworkAllocateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_Fed_NetworkAllocateDBQueryOutcome(Fed_NetworkAllocateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver ---> Handle Fed_NetworkAllocateDBQueryOutcome Event");

        if (dboutcome.isOutcome() == false) {
            //send API reply with void virtualcompute Instance

            Fed_E2ENetworkAllocateInstanceOutcome netneginst = new Fed_E2ENetworkAllocateInstanceOutcome(dboutcome.getReqid(),
                    dboutcome.getServid(), null, dboutcome.isOutcome(), dboutcome.getLogicalPathId());
            System.out.println("DatabaseDriver --> Post fed_E2ENetworkAllocateInstanceOutcome  Negative outcome Event");
            SingletonEventBus.getBus().post(netneginst);
            return;
        }

        long networkServId = -1;
        long virtualnetworkId = -1;
        boolean abstrSrcNfviPopOfLogicalPathIsFed = dboutcome.isAbstrSrcNfviPopOfLogicalPathIsFed();
        // Insert record in networkservices table//
        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservices "
                    + "(reqId,"
                    + "status,"
                    + "servId,"
                    + "logicalPathId,"
                    + "federationIsRequired)"
                    + "VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, dboutcome.getReqid());
            ps.setString(2, "enabled");
            ps.setString(3, dboutcome.getServid());
            ps.setLong(4, dboutcome.getLogicalPathId());
            ps.setBoolean(5, true);
            //ps.setString(5,null);

            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_Fed_NetworkAllocateDBQueryOutcome ---> NetworkService instance inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                networkServId = rs.getLong(1);
                System.out.println("DatabaseDriver.handle_Fed_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
            }

            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //END - Insert      
        //START - Insert virtualnetwork entries for WIM POPs 
        List<VirtualNetwork> wimlist = dboutcome.getWimnetlist();

        // while (computeIdList|=null){
        for (int i = 0; i < dboutcome.getWimnetlist().size(); i++) {

            try {
                //String computeServId=computeIdList.remove(0).getNetworkId();
                VirtualNetwork vn = wimlist.get(i);

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO mtpservdb.virtualnetwork\n"
                        + "(networkResourceName,"
                        + "segmentType,"
                        + "isShared,"
                        + "zoneId,"
                        + "networkResourceId,"
                        + "networkType,"
                        + "operationalState,"
                        + "sharingCriteria,"
                        + "bandwidth,"
                        + "nfviPopId,"
                        + "netServId,"
                        + "netResIdRef)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, vn.getNetworkResourceName());
                ps.setString(2, vn.getSegmentType());
                ps.setBoolean(3, vn.isIsShared());
                ps.setString(4, vn.getZoneId());
                ps.setString(5, vn.getNetworkResourceId());
                ps.setString(6, vn.getNetworkType());
                ps.setString(7, vn.getOperationalState());
                ps.setString(8, vn.getSharingCriteria());
                ps.setBigDecimal(9, vn.getBandwidth());
                ps.setLong(10, dboutcome.getWimPopList().get(i));
                ps.setLong(11, networkServId);
                ps.setLong(12, dboutcome.getWanLinks().get(i));

                ps.executeUpdate();
                System.out.println("DatabaseDriver.handle_Fed_NetworkAllocateDBQueryOutcome ---> VirtualNetwork instance inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    virtualnetworkId = rs.getLong(1);
                    System.out.println("DatabaseDriver.handle_Fed_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
                }
                rs.close();
                ps.close();
                //Insert entries in NETWORK_SUBSET table
                // TO DO - Fix this part after the VirtualNetwork class is compliant with IFA005 
                if (vn.getSubnet() != null) {

                    for (int j = 0; i < vn.getSubnet().size(); j++) {

                        PreparedStatement ps_subnet = conn.prepareStatement("INSERT INTO networksubnet"
                                + "(resourceId,virtualNetworkId) VALUES(?,?)");
//UNCOMMENT after  NETWORK_SUBSET class is fixed

                        ps_subnet.setString(1, vn.getSubnet().get(j));
                        ps_subnet.setLong(2, virtualnetworkId);
                        ps_subnet.executeUpdate();
                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Subnet entry inserted into DB");
                        ps_subnet.close();

                    }
                }
//Insert entries in VIRTUAL_NETWORK_PORT table
                //TO DO - Complete after VIRTUAL_NETWORK_PORT class is fixed (related to IFA005) 
                if (vn.getNetworkPort() != null) {

                    for (int j = 0; j < vn.getNetworkPort().size(); j++) {

                        PreparedStatement ps_port = conn.prepareStatement("INSERT INTO virtualnetworkport "
                                + "(resourceId, portType,attachedResourceId,segmentId,bandwidth,metadata,virtualNetworkId)"
                                + "VALUES(?,?,?,?,?,?,?)");

                        ps_port.setString(1, vn.getNetworkPort().get(j).getResourceId());
                        ps_port.setString(2, vn.getNetworkPort().get(j).getPortType());
                        ps_port.setString(3, vn.getNetworkPort().get(j).getAttachedResourceId());
                        //TO DO - Insert below attributes when VIRTUAL_NETWORK_PORT class is fixed (related to IFA005)                  
                        ps_port.setInt(4, Integer.parseInt(vn.getNetworkPort().get(j).getSegmentId()));

                        ps_port.setBigDecimal(5, vn.getNetworkPort().get(j).getBandwidth());
                        String metadata = "";
                        for (int t = 0; t < vn.getNetworkPort().get(j).getMetadata().size(); t++) {
                            MetaDataInner metadataInner = vn.getNetworkPort().get(j).getMetadata().get(t);
                            if (metadata == "") {
                                metadata = metadataInner.getKey() + "=" + metadataInner.getValue();
                            } else {
                                metadata = metadata + ";" + metadataInner.getKey() + "=" + metadataInner.getValue();
                            }
                        }

                        ps_port.setString(6, metadata);
                        ps_port.setLong(7, virtualnetworkId);
                        ps_port.executeUpdate();
                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkPort entry inserted into DB");
                        ps_port.close();
                    }
                }
                //Insert entries in Network_QoS table
                //TO DO - Complete after VIRTUAL_NETWORK class is fixed (related to IFA005) 
                // UNCOMMENT after  VIRTUAL_NETWORK class is fixed 
//                if(vn.getNetworkQoS()!=null) {
//
//                     for (int j = 0; j < vn.getNetworkPort().size(); j++) {
//                    PreparedStatement ps_qos = conn.prepareStatement(" INSERT INTO supportednetworkqos\n" +
//"(qosName, qosValue, virtualnetworkId)\n" +
//"VALUES (?,?,?);");
//
//                    ps_qos.setString(1,vn.getNetworkQoS().get(j).); 
//                    ps_qos.setString(1,vn.getNetworkQoS().get(j).);
//                    ps_qos.setLong(3,virtualnetworkId);
//                    
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Network QoS entry inserted into DB");
//                    ps_qos.close();
//                     }      
//                }  
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
//        //START - Insert virtualnetwork entries for VIM POPs 
//        List<VirtualNetwork> vimlist = dboutcome.getVimnetlist();
//
//        // while (computeIdList!=null){
//        for (int i = 0; i < dboutcome.getVimnetlist().size(); i++) {
//
//            try {
//                //String computeServId=computeIdList.remove(0).getNetworkId();
//                VirtualNetwork vn = vimlist.get(i);
//
//                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement("INSERT INTO mtpservdb.virtualnetwork\n"
//                        + "(networkResourceName,"
//                        + "segmentType,"
//                        + "isShared,"
//                        + "zoneId,"
//                        + "networkResourceId,"
//                        + "networkType,"
//                        + "operationalState,"
//                        + "sharingCriteria,"
//                        + "bandwidth,"
//                        + "nfviPopId,"
//                        + "netServId)"
//                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                ps.setString(1, vn.getNetworkResourceName());
//                ps.setString(2, vn.getSegmentType());
//                ps.setBoolean(3, vn.isIsShared());
//                ps.setString(4, vn.getZoneId());
//                ps.setString(5, vn.getNetworkResourceId());
//                ps.setString(6, vn.getNetworkType());
//                ps.setString(7, vn.getOperationalState());
//                ps.setString(8, vn.getSharingCriteria());
//                ps.setBigDecimal(9, vn.getBandwidth());
//                ps.setLong(10, dboutcome.getVimPopList().get(i));
//                ps.setLong(11, networkServId);
//
//                ps.executeUpdate();
//                System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetwork instance inserted into DB");
//                ResultSet rs = ps.getGeneratedKeys();
//
//                if (rs != null && rs.next()) {
//                    virtualnetworkId = rs.getLong(1);
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> netServId:" + networkServId + "");
//                }
//                rs.close();
//                ps.close();
//                //Insert entries in NETWORK_SUBSET table
//                // TO DO - Fix this part after the VirtualNetwork class is compliant with IFA005 
//                if (vn.getSubnet() != null) {
//
//                    PreparedStatement ps_subnet = conn.prepareStatement("INSERT INTO networksubnet"
//                            + "(resourceId,virtualNetworkId) VALUES(?,?)");
////UNCOMMENT after  NETWORK_SUBSET class is fixed
////                    ps_subnet.setString(1,vn.getSubnet());
////                    ps_subnet.setLong(2,virtualnetworkId);
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Subnet entry inserted into DB");
//                    ps_subnet.close();
//                }
////Insert entries in VIRTUAL_NETWORK_PORT table
//                //TO DO - Complete after VIRTUAL_NETWORK_PORT class is fixed (related to IFA005) 
//                if (vn.getNetworkPort() != null) {
//
//                    for (int j = 0; j < vn.getNetworkPort().size(); j++) {
//
//                        PreparedStatement ps_port = conn.prepareStatement("INSERT INTO virtualnetworkport "
//                                + "(resourceId, portType,attachedResourceId,segmentId,bandwidth,metadata,virtualNetworkId)"
//                                + "VALUES (?,?,?,?,?,?,?)");
//
//                        ps_port.setString(1, vn.getNetworkPort().get(j).getResourceId());
//                        ps_port.setString(2, vn.getNetworkPort().get(j).getPortType());
//                        ps_port.setString(3, vn.getNetworkPort().get(j).getAttachedResourceId());
//                        //TO DO - Insert below attributes when VIRTUAL_NETWORK_PORT class is fixed (related to IFA005)                  
//                        // ps_port.setString(4,vn.getNetworkPort().get(j).);
//                        // ps_port.setString(5,vn.getNetworkPort().get(j).);
//                        //  ps_port.setString(6,vn.getNetworkPort().get(j).);
//                        ps_port.setLong(7, virtualnetworkId);
//                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkPort entry inserted into DB");
//                        ps_port.close();
//                    }
//                }
//                //Insert entries in Network_QoS table
//                //TO DO - Complete after VIRTUAL_NETWORK class is fixed (related to IFA005) 
//                // UNCOMMENT after  VIRTUAL_NETWORK class is fixed 
////                if(vn.getNetworkQoS()!=null) {
////
////                     for (int j = 0; j < vn.getNetworkPort().size(); j++) {
////                    PreparedStatement ps_qos = conn.prepareStatement(" INSERT INTO supportednetworkqos\n" +
////"(qosName, qosValue, virtualnetworkId)\n" +
////"VALUES (?,?,?);");
////
////                    ps_qos.setString(1,vn.getNetworkQoS().get(j).); 
////                    ps_qos.setString(1,vn.getNetworkQoS().get(j).);
////                    ps_qos.setLong(3,virtualnetworkId);
////                    
////                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> Network QoS entry inserted into DB");
////                    ps_qos.close();
////                     }      
////                }  
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//        }

        //  START - INSERT AllocateNetworkRequest into DB
        InterNfviPopConnectivityRequest allocNetworkReq = dboutcome.getNetworkRequest();

        try {

            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

            // Insert  data into computeservicerequestdata table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservicerequestdata\n"
                    + "(networkResourceType,"
                    + "netServId)"
                    + "VALUES (?,?)");

            ps.setString(1, allocNetworkReq.getNetworkLayer());
            ps.setLong(2, networkServId);
            ps.executeUpdate();
            System.out.println("DatabaseDriver.handle_Fed_NetworkAllocateDBQueryOutcome ---> AllocateNetworkRequest data inserted into DB");
            ps.close();

            // Insert  data into VIRTUAL_NETWORK_DATA table
            // if (networkdata!=null) {
            PreparedStatement ps_netdata = conn.prepareStatement("INSERT INTO virtualnetworkdata "
                    + "(bandwidth, "
                    + "networkType,"
                    + "netServId) VALUES(?,?,?)");

            ps_netdata.setLong(1, allocNetworkReq.getLogicalLinkPathList().get(0).getReqBandwidth().longValue());
            //ps_netdata.setLong(1, 5000000);
            ps_netdata.setString(2, allocNetworkReq.getInterNfviPopNetworkType());
            ps_netdata.setLong(3, networkServId);
            ps_netdata.executeUpdate();
            System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkData record inserted into DB");
            ps_netdata.close();
            //       }
// Insert  data into NETWORK_QoS table
            //List <Object> qosList = networkdata.getQos();

            //  if (qosList!=null) {
            //  for (int j = 0; j < qosList.size(); j++) {
            PreparedStatement ps_qos = conn.prepareStatement("INSERT INTO networkqos"
                    + "(qosName,"
                    + "qosValue,"
                    + "netServId)"
                    + "VALUES (?,?,?)");

            //TO DO - Add missing attributes
            ps_qos.setString(1, "delay");

            ps_qos.setBigDecimal(2, dboutcome.getNetworkRequest().getLogicalLinkPathList().get(0).getReqLatency());
            //ps_qos.setBigDecimal(2, new BigDecimal(10.0));
            ps_qos.setLong(3, networkServId);
            ps_qos.executeUpdate();
            System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> QoS entry inserted into DB");
            ps_qos.close();
            //   }
            // }
            //Insert  data into LAYER3_CONNECTIVITY_INFORMATION table
            // TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
            //UNCOMMENT after Allocate Virtualised Network Resource operation input parameters are fixed
//List <Object> layer3InfoList = networkdata.getLayer3();      
//                   if (layer3InfoList!=null) {
//
//                    for (int j = 0; j < layer3InfoList.size(); j++) {
//
//                        PreparedStatement ps_layer3 = conn.prepareStatement("INSERT INTO layer3connectivityinformation\n" +
//"(networkId, ipVersion, gatewayIp, cidr, isDhcpEnabled, addressPool, metadata, netServId)\n" +
//"VALUES(?,?,?,?,?,?,?,?)");
//        
//                        
//                        //TO DO - Add missing attributes
//                        ps_layer3.setString(1, layer3InfoList.get(j).getQosName());
//                      
//                        ps_layer3.setLong(8, virtualnetworkId);
//                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> QoS entry inserted into DB");
//                        ps_layer3.close();
//                    }
//                }
            // Insert  data into VIRTUAL_NETWORK_PORT_DATA table
            // TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
            //UNCOMMENT after Allocate Virtualised Network Resource operation input parameters are fixed
//                Object portdata = allocNetworkReq.getTypeNetworkPortData();
//                if (portdata!= null) {
//
//                        PreparedStatement ps_portdata = conn.prepareStatement("INSERT INTO virtualnetworkportdata "
//                                + "(portType, networkId, segmentId, bandwidth, metadata, netServId)\n" +
//                                  "VALUES(?,?,?,?,?,?)");
//   
//                        ps_portdata.setString(1, portdata.get));  
//                        ps_portdata.setLong(6, networkServId);
//                      
//                        ps_portdata.executeUpdate();
//                        System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> VirtualNetworkPortData record inserted into DB");
//                        ps_portdata.close();       
//                }
            // Insert  data into NETWORK_SUBNET_DATA table
            // TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
            //UNCOMMENT after Allocate Virtualised Network Resource operation input parameters are fixed
//                Object subnetData = allocNetworkReq.getTypeSubnetData();
//                if (subnetData != null) {
// 
//                    PreparedStatement ps_subnetData = conn.prepareStatement("INSERT INTO networksubnetdata\n" +
//"(networkId, ipVersion, gatewayIp, cidr, isDhcpEnabled, addressPool, metadata, netServId) \n" +
//"VALUES (?,?,?,?,?,?,?,?)");
//                    //TO DO - Complete this part after Allocate Virtualised Network Resource operation input parameters are fixed
//                    ps_subnetData.setString(1, subnetData.get);
//                
//                    ps_subnetData.setLong(8, networkServId);
//
//                    ps_subnetData.executeUpdate();
//                    System.out.println("DatabaseDriver.handle_NetworkAllocateDBQueryOutcome ---> NetworkSubnetData record inserted into DB");
//                    ps_subnetData.close();
//
//                }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
//END - Insert

//Amount of Bandwidth released from the Capacity
        long bandwidth = dboutcome.getNetworkRequest().getLogicalLinkPathList().get(0).getReqBandwidth().longValue();
//long  bandwidth = 5000000;

//Update Capacity in logicalpath table
        try {
            long availableBandwidth;
            long allocatedBandwidth;
            long currentAvailableBandwidth;
            long currentAllocatedBandwidth;

            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId, availableBandwidth, reservedBandwidth,"
                    + " totalBandwidth, allocatedBandwidth\n"
                    + "FROM fed_logicalpath WHERE logicalPathId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ps.setLong(1, dboutcome.getLogicalPathId());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availableBandwidth = rs.getLong("availableBandwidth");
                allocatedBandwidth = rs.getLong("allocatedBandwidth");
                currentAvailableBandwidth = availableBandwidth - bandwidth;
                currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                rs.updateRow();

                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
//START - Update Capacity in Domain DB

//Update InterDomainLinks capacity
        if (abstrSrcNfviPopOfLogicalPathIsFed == false) {
            for (int i = 0; i < dboutcome.getInterdomainLinks().size(); i++) {
                if (i == dboutcome.getInterdomainLinks().size() - 1) {
                    try {
                        long availableBandwidth;
                        long allocatedBandwidth;
                        long currentAvailableBandwidth;
                        long currentAllocatedBandwidth;

                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth, "
                                + "totalBandwidth, allocatedBandwidth\n"
                                + "FROM fed_interdomainlink WHERE interDomainLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, dboutcome.getInterdomainLinks().get(i));
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            availableBandwidth = rs.getLong("availableBandwidth");
                            allocatedBandwidth = rs.getLong("allocatedBandwidth");
                            currentAvailableBandwidth = availableBandwidth - bandwidth;
                            currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                            rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                            rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                            rs.updateRow();

                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseDriver.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                } else {
                    try {
                        long availableBandwidth;
                        long allocatedBandwidth;
                        long currentAvailableBandwidth;
                        long currentAllocatedBandwidth;

                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth, "
                                + "totalBandwidth, allocatedBandwidth\n"
                                + "FROM interdomainlink WHERE interDomainLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, dboutcome.getInterdomainLinks().get(i));
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            availableBandwidth = rs.getLong("availableBandwidth");
                            allocatedBandwidth = rs.getLong("allocatedBandwidth");
                            currentAvailableBandwidth = availableBandwidth - bandwidth;
                            currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                            rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                            rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                            rs.updateRow();

                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseDriver.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }

            }

        } else {

            for (int i = 0; i < dboutcome.getInterdomainLinks().size(); i++) {
                if (i == 0) {
                    try {
                        long availableBandwidth;
                        long allocatedBandwidth;
                        long currentAvailableBandwidth;
                        long currentAllocatedBandwidth;

                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth, "
                                + "totalBandwidth, allocatedBandwidth\n"
                                + "FROM fed_interdomainlink WHERE interDomainLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, dboutcome.getInterdomainLinks().get(i));
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            availableBandwidth = rs.getLong("availableBandwidth");
                            allocatedBandwidth = rs.getLong("allocatedBandwidth");
                            currentAvailableBandwidth = availableBandwidth - bandwidth;
                            currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                            rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                            rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                            rs.updateRow();

                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseDriver.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                } else {
                    try {
                        long availableBandwidth;
                        long allocatedBandwidth;
                        long currentAvailableBandwidth;
                        long currentAllocatedBandwidth;

                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth, "
                                + "totalBandwidth, allocatedBandwidth\n"
                                + "FROM interdomainlink WHERE interDomainLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, dboutcome.getInterdomainLinks().get(i));
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            availableBandwidth = rs.getLong("availableBandwidth");
                            allocatedBandwidth = rs.getLong("allocatedBandwidth");
                            currentAvailableBandwidth = availableBandwidth - bandwidth;
                            currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                            rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                            rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                            rs.updateRow();

                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                            System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(DatabaseDriver.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                }

            }

        }

//        //Update IntraPopLinks capacity
//        for (int i = 0; i < dboutcome.getIntraPopLinks().size(); i++) {
//
//            try {
//                long availableBandwidth;
//                long allocatedBandwidth;
//                long currentAvailableBandwidth;
//                long currentAllocatedBandwidth;
//
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//
//                PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity, totalCapacity, "
//                        + "allocatedCapacity, networkResourceTypeId\n"
//                        + "FROM networkresources WHERE networkResId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//                ps.setLong(1, dboutcome.getIntraPopLinks().get(i));
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    availableBandwidth = rs.getLong("availableCapacity");
//                    allocatedBandwidth = rs.getLong("allocatedCapacity");
//                    currentAvailableBandwidth = availableBandwidth - bandwidth;
//                    currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
//                    rs.updateLong("availableCapacity", currentAvailableBandwidth);
//                    rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
//                    rs.updateRow();
//
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in DOMAIN_DB!");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
//                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
//                }
//
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//        }
        //Update WanLinks capacity
        for (int i = 0; i < dboutcome.getWanLinks().size(); i++) {

            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long currentAvailableBandwidth;
                long currentAllocatedBandwidth;

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity, totalCapacity, "
                        + "allocatedCapacity, networkResourceTypeId\n"
                        + "FROM networkresources WHERE networkResId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, dboutcome.getWanLinks().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableCapacity");
                    allocatedBandwidth = rs.getLong("allocatedCapacity");
                    currentAvailableBandwidth = availableBandwidth - bandwidth;
                    currentAllocatedBandwidth = allocatedBandwidth + bandwidth;
                    rs.updateLong("availableCapacity", currentAvailableBandwidth);
                    rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Bandwidth updated in DOMAIN_DB!");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkAllocateDBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        //END - Update Capacity in Domain DB
//TO DO - Refine the criterion to select the VirtualNetwork instance from the wanLinks
//Select the first VirtualNetwork instance in wanLinks and update
//it with the virtualnetworkId (networkServId) to provide to SO 
        VirtualNetwork vn = dboutcome.getWimnetlist().get(0);
        vn.setNetworkResourceId(Long.toString(networkServId));

//        E2ENetworkAllocateInstanceOutcome netinst = new E2ENetworkAllocateInstanceOutcome(dboutcome.getReqid(),
//                dboutcome.getServid(), /*Inset VN*/ null, dboutcome.isOutcome(), dboutcome.getLogicalPathId());
        Fed_E2ENetworkAllocateInstanceOutcome netinst = new Fed_E2ENetworkAllocateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), vn, dboutcome.isOutcome(), dboutcome.getLogicalPathId());

        System.out.println("DatabaseDriver --> Post Fed_E2ENetworkAllocateInstanceOutcome Event");
        SingletonEventBus.getBus().post(netinst);

    }

    @Subscribe
    public void handle_Fed_NetworkTerminateDBQuery(Fed_NetworkTerminateDBQuery dbquery) {
        System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQuery Event");

        //START - Retrieve from DB the list of wanLinks, interDomainLinks and intraPopLinks related to the
        // logicalPathId to terminate
        List<String> netServIdList = dbquery.getNetServIdList();
        List<String> logicalPathIdList = new ArrayList();
        HashMap<Integer, ArrayList<Long>> interdomainLinksMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> fed_interdomainLinksMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> intraPopLinksMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wanLinksMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wimPopListMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> vimPopListMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wimdomlistMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> vimdomlistMap = new HashMap();
        HashMap<Integer, ArrayList<String>> vimNetworkTypeMap = new HashMap();
        HashMap<Integer, ArrayList<String>> wimNetworkTypeMap = new HashMap();
        HashMap<Integer, ArrayList<Long>> wanResourceIdListMap = new HashMap();

        //Retrieve from DB the logicalPaths related to the netServIds
        for (int i = 0; i < dbquery.getNetServIdList().size(); i++) {
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId FROM networkservices where netServId=?");
                ps.setString(1, dbquery.getNetServIdList().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    // System.out.println("Test: " + rs.getInt("logicalPathId") + "");
                    logicalPathIdList.add(String.valueOf(rs.getInt("logicalPathId")));
                    System.out.println("handle_NetworkTerminateDBQuery ---> logicalPathId: " + rs.getInt("logicalPathId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

        //START - Loop on logicalPaths
        for (int i = 0; i < logicalPathIdList.size(); i++) {
            ArrayList<Long> interdomainLinks = new ArrayList();
            ArrayList<Long> fed_interdomainLinks = new ArrayList();
            //ArrayList<Long> intraPopLinks = new ArrayList();
            ArrayList<Long> wanLinks = new ArrayList();
            ArrayList<Long> wimPopList = new ArrayList();
            //ArrayList<Long> vimPopList = new ArrayList();
            ArrayList<Long> wimdomlist = new ArrayList();
            // ArrayList<Long> vimdomlist = new ArrayList();
            //ArrayList<String> vimNetworkType = new ArrayList();
            ArrayList<String> wimNetworkType = new ArrayList();
            ArrayList<Long> wanResourceIdList = new ArrayList();

            String logicalPathId = logicalPathIdList.get(i);
            ArrayList<Integer> wimResourceIndex = new ArrayList();

            ////Get from DB the intraPopLinks, wanLinks, wimPopList, vimPopList, vimNetworkType and wimNetworkType related to the LogicalLinkId to terminate
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId FROM fed_logicalpath_interdomainlink where logicalPathId=?");
                ps.setString(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    interdomainLinks.add(Long.valueOf(rs.getInt("interDomainLinkId")));

                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getInt("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId FROM fed_logicalpath_fed_interdomainlink where logicalPathId=?");
                ps.setString(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    fed_interdomainLinks.add(Long.valueOf(rs.getInt("interDomainLinkId")));

                    System.out.println("handle_NetworkAllocateDBQuery ---> InterDomainLinkId: " + rs.getInt("InterDomainLinkId") + "");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Get from DB the wanLinks, wimPopList, and networkType
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT fed_logicalpath_networkresources.networkResId, fed_logicalpath_networkresources.logicalPathId, mtpdomdb.networkresources.networkType, mtpdomdb.zoneid.nfviPopId, mtpdomdb.domain.type, mtpdomdb.domain.domId from fed_logicalpath_networkresources \n"
                        + "INNER JOIN mtpdomdb.networkresources ON mtpdomdb.networkresources.networkResId=fed_logicalpath_networkresources.networkResId\n"
                        + " INNER JOIN mtpdomdb.zoneid ON  mtpdomdb.networkresources.resourceZoneId=mtpdomdb.zoneid.resourceZoneId \n"
                        + "INNER JOIN mtpdomdb.nfvipop ON mtpdomdb.zoneid.nfviPopId = mtpdomdb.nfvipop.nfviPopId\n"
                        + "INNER JOIN mtpdomdb.domain ON mtpdomdb.nfvipop.domId = mtpdomdb.domain.domId\n"
                        + "where fed_logicalpath_networkresources.logicalPathId=?");

                ps.setString(1, logicalPathId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String type = rs.getString("type");
//                    if (type.equals("VIM")) {
//                        intraPopLinks.add(rs.getLong("networkResId"));
//                        vimPopList.add(rs.getLong("nfviPopId"));
//                        vimdomlist.add(rs.getLong("domId"));
//                        vimNetworkType.add(rs.getString("networkType"));
//                        System.out.println("handle_NetworkAllocateDBQuery ---> IntraPopLink - NetworkResId: " + rs.getLong("NetworkResId") + "");
//                    }

                    if (type.equals("T-WIM")) {
                        wanLinks.add(rs.getLong("networkResId"));
                        wimPopList.add(rs.getLong("nfviPopId"));
                        wimdomlist.add(rs.getLong("domId"));
                        wimNetworkType.add(rs.getString("networkType"));
                        System.out.println("handle_NetworkAllocateDBQuery ---> WanLink - NetworkResId: " + rs.getLong("NetworkResId") + "");

                    }

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Get from DB the networkResourceId of WAN resources
            for (int j = 0; j < wanLinks.size(); j++) {
                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT networkResourceId FROM virtualnetwork where netServId=? and netResIdRef=?");
                    ps.setString(1, dbquery.getNetServIdList().get(i));
                    ps.setLong(2, wanLinks.get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        wanResourceIdList.add(rs.getLong("networkResourceId"));

                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

//            //Get from DB the intraPopLinks, vimPopList and networkType
//            //Get All Virtual Network
//            try {
//                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement("SELECT networkResourceId, networkType, nfviPopId FROM virtualnetwork where netServId=? and netResIdRef is null");
//                ps.setString(1, dbquery.getNetServIdList().get(i));
//
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//
//                    intraPopLinks.add(rs.getLong("networkResourceId"));
//                    vimPopList.add(rs.getLong("nfviPopId"));
//                    vimNetworkType.add(rs.getString("networkType"));
//
//                }
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//            //Get from DB the vimdomlist related to the vimPopList
//            for (int j = 0; j < vimPopList.size(); j++) {
//
//                try {
//                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//                    PreparedStatement ps = conn.prepareStatement("SELECT nfvipop.domId, domain.type "
//                            + "FROM nfvipop "
//                            + "INNER JOIN domain ON domain.domId=nfvipop.domId "
//                            + "where nfviPopId=?");
//                    ps.setLong(1, vimPopList.get(j));
//                    java.sql.ResultSet rs = ps.executeQuery();
//                    while (rs.next()) {
//
//                        if (rs.getString("type").equals("VIM")) {
//
//                            vimdomlist.add(rs.getLong("domId"));
//                        }
//                    }
//                    rs.close();
//                    ps.close();
//
//                } catch (SQLException ex) {
//                    Logger.getLogger(DatabaseDriver.class
//                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                }
//
//            }
            interdomainLinksMap.put(i, interdomainLinks);
            //intraPopLinksMap.put(i, intraPopLinks);
            wanLinksMap.put(i, wanLinks);
            wimPopListMap.put(i, wimPopList);
            //vimPopListMap.put(i, vimPopList);
            wimdomlistMap.put(i, wimdomlist);
            //vimdomlistMap.put(i, vimdomlist);
            //vimNetworkTypeMap.put(i, vimNetworkType);
            wimNetworkTypeMap.put(i, wimNetworkType);
            wanResourceIdListMap.put(i, wanResourceIdList);
            fed_interdomainLinksMap.put(i, fed_interdomainLinks);
        }
        //END - Loop on logicalPaths

//        NetworkTerminateDBQueryReply dbreply = new NetworkTerminateDBQueryReply(dbquery.getReqId(), dbquery.getServId());        
        Fed_NetworkTerminateDBQueryReply dbreply = new Fed_NetworkTerminateDBQueryReply(dbquery.getReqid(), dbquery.getServid(),
                wimdomlistMap, vimdomlistMap, interdomainLinksMap, fed_interdomainLinksMap, intraPopLinksMap, wanLinksMap, wimPopListMap, vimPopListMap,
                wimNetworkTypeMap, vimNetworkTypeMap, wanResourceIdListMap, netServIdList, logicalPathIdList);

        System.out.println("DatabaseDriver ---> Post NetworkTerminateDBQueryReply Event");
        SingletonEventBus.getBus().post(dbreply);

    }

    @Subscribe
    public void handle_Fed_NetworkTerminateDBQueryOutcome(Fed_NetworkTerminateDBQueryOutcome dboutcome) {
        System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQueryOutcome Event");
        //TODO update DB using JDBC raising exception if somethiong is wrong

        //Loop on logicalPaths 
        for (int i = 0; i < dboutcome.getLogicalPathList().size(); i++) {
            String netServId = null;
            long bandwidth = 0;
            List<Long> virtualnetworkIdList = new ArrayList();

            netServId = dboutcome.getNetServIdList().get(i);

            // Retrieve from DB all virtualnetworkId related to the logicalPathId
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT virtualNetworkId FROM virtualnetwork WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    System.out.println("DatabaseDriver ---> Handle NetworkTerminateDBQueryOutcome Event - VirtualNetworkId:" + rs.getLong("virtualNetworkId") + "");

                    virtualnetworkIdList.add(rs.getLong("virtualNetworkId"));
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Amount of Bandwidth released from the Capacity
            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT bandwidth FROM virtualnetworkdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    bandwidth = rs.getLong("bandwidth");

                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from Network_QoS table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM supportednetworkqos WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();

                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from VIRTUAL_NETWORK_PORT table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkport WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from NETWORK_SUBSET table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM networksubnet WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from VirtualNetwork table
            for (int j = 0; j < virtualnetworkIdList.size(); j++) {

                try {
                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetwork WHERE virtualNetworkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, virtualnetworkIdList.get(j));
                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Remove records from NetQoS table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkqos WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//Remove records from Layer3ConnectivityInformation table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM layer3connectivityinformation WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from VirtualNetworkData table 
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //Remove records from VirtualNetworkPortData table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM virtualnetworkportdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from NetworkSubnetData table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networksubnetdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Remove records from networkservicerequestdata table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservicerequestdata WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

// Remove record from networkservices table
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservices WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, netServId);

                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            /**
             * ****************************************************************************************
             */
            // UNCOMMENT WHEN IFA005 API bugs are fixed
            // bandwidth = 5000000;
            /**
             * ***************************************************************************************
             */
//Update Capacity in Abstraction DB
            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long currentAvailableBandwidth;
                long currentAllocatedBandwidth;

                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                //STANDARD SELECT QUERY
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId, availableBandwidth, reservedBandwidth,"
                        + " totalBandwidth, allocatedBandwidth\n"
                        + "FROM fed_logicalpath WHERE logicalPathId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, dboutcome.getLogicalPathList().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableBandwidth");
                    allocatedBandwidth = rs.getLong("allocatedBandwidth");
                    currentAvailableBandwidth = availableBandwidth + bandwidth;
                    currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                    rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                    rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                    rs.updateRow();

                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                    System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

//Update Capacity in Domain DB 
            //Update InterDomainLink capacity
            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in InterDomainLink table!");
            for (int j = 0; j < dboutcome.getInterdomainLinksMap().get(i).size(); j++) {

                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long currentAvailableBandwidth;
                    long currentAllocatedBandwidth;

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth,"
                            + " totalBandwidth, allocatedBandwidth\n"
                            + "FROM interdomainlink WHERE interDomainLinkId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, dboutcome.getInterdomainLinksMap().get(i).get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableBandwidth");
                        allocatedBandwidth = rs.getLong("allocatedBandwidth");
                        currentAvailableBandwidth = availableBandwidth + bandwidth;
                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                        rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                        rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                        rs.updateRow();

                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //Update fed_InterDomainLink capacity
            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in InterDomainLink table!");
            for (int j = 0; j < dboutcome.getFed_interdomainLinksMap().get(i).size(); j++) {

                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long currentAvailableBandwidth;
                    long currentAllocatedBandwidth;

                    java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("SELECT interDomainLinkId, availableBandwidth, reservedBandwidth,"
                            + " totalBandwidth, allocatedBandwidth\n"
                            + "FROM fed_interdomainlink WHERE interDomainLinkId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, dboutcome.getFed_interdomainLinksMap().get(i).get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableBandwidth");
                        allocatedBandwidth = rs.getLong("allocatedBandwidth");
                        currentAvailableBandwidth = availableBandwidth + bandwidth;
                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                        rs.updateLong("availableBandwidth", currentAvailableBandwidth);
                        rs.updateLong("allocatedBandwidth", currentAllocatedBandwidth);
                        rs.updateRow();

                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

//            //Update IntraPopLinks capacity
//            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in NETWORKRESOURCES table!");
//            for (int j = 0; j < dboutcome.getIntraPopLinksMap().get(i).size(); j++) {
//
//                try {
//                    long availableBandwidth;
//                    long allocatedBandwidth;
//                    long currentAvailableBandwidth;
//                    long currentAllocatedBandwidth;
//
//                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
//
//                    PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity,"
//                            + " totalCapacity, allocatedCapacity\n"
//                            + "FROM networkresources WHERE networkResId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//                    ps.setLong(1, dboutcome.getIntraPopLinksMap().get(i).get(j));
//                    java.sql.ResultSet rs = ps.executeQuery();
//                    while (rs.next()) {
//                        availableBandwidth = rs.getLong("availableCapacity");
//                        allocatedBandwidth = rs.getLong("allocatedCapacity");
//                        currentAvailableBandwidth = availableBandwidth + bandwidth;
//                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
//                        rs.updateLong("availableCapacity", currentAvailableBandwidth);
//                        rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
//                        rs.updateRow();
//
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
//                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
//                    }
//
//                    rs.close();
//                    ps.close();
//
//                } catch (SQLException ex) {
//                    Logger.getLogger(DatabaseDriver.class
//                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//                }
//
//            }
            //Update WanLinks capacity
            System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Update capacity in NETWORKRESOURCES table!");
            for (int j = 0; j < dboutcome.getWanLinksMap().get(i).size(); j++) {

                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long currentAvailableBandwidth;
                    long currentAllocatedBandwidth;

                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("SELECT networkResId, availableCapacity, reservedCapacity,"
                            + " totalCapacity, allocatedCapacity\n"
                            + "FROM networkresources WHERE networkResId=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ps.setLong(1, dboutcome.getWanLinksMap().get(i).get(j));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableCapacity");
                        allocatedBandwidth = rs.getLong("allocatedCapacity");
                        currentAvailableBandwidth = availableBandwidth + bandwidth;
                        currentAllocatedBandwidth = allocatedBandwidth - bandwidth;
                        rs.updateLong("availableCapacity", currentAvailableBandwidth);
                        rs.updateLong("allocatedCapacity", currentAllocatedBandwidth);
                        rs.updateRow();

                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Bandwidth updated in Abstraction_DB!");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old available bandwidth " + availableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Current available bandwidth" + currentAvailableBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateDBQueryOutcome ---> Old allocated bandwidth " + allocatedBandwidth + "");
                        System.out.println("DatabaseDriver.handle__NetworkTerminateBQueryOutcome ---> Current allocated bandwidth " + currentAllocatedBandwidth + "");
                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

        } //END Loop on LogicalPaths

        Fed_E2ENetworkTerminateInstanceOutcome netinst = new Fed_E2ENetworkTerminateInstanceOutcome(dboutcome.getReqid(),
                dboutcome.getServid(), dboutcome.getNetServIdList(), dboutcome.getLogicalPathList());
        System.out.println("DatabaseDriver --> Post E2ENetworkTerminateInstanceOutcome Event");
        SingletonEventBus.getBus().post(netinst);

    }

}
