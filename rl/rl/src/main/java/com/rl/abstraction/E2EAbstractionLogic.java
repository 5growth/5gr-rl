/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.abstraction;

import com.google.common.eventbus.Subscribe;
import com.rl.DbConnectionPool.DbAbstractionDatasource;
import com.rl.DbConnectionPool.DbDomainDatasource;
import com.rl.DbConnectionPool.DbFederationDatasource;
import com.rl.SingletonEventBus;
import com.rl.events.abstraction.Creation.ComputeE2EAbstractionReply;
import com.rl.events.abstraction.Creation.ComputeE2EAbstractionRequest;
import com.rl.events.abstraction.Creation.DomainNumbers;
import com.rl.extinterface.nbi.swagger.model.LocationInfo;
import com.rl.extinterface.nbi.swagger.model.MECRegionInfoMecRegionInfo;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.PNFInfo;
import com.rl.extinterface.nbi.swagger.model.RadioCoverageAreaListInnerRadioCoverageAreaInfo;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

public class E2EAbstractionLogic {

    private long reqval;
    private long domnum;
    private long currdom;

    public E2EAbstractionLogic() {
        reqval = 0;
        currdom = 0;
        domnum = 0;
    }
    
    @Subscribe
    public void handle_DomainNumbers(DomainNumbers domnumev) {
        System.out.println("E2EAbstractionLogic--> Handle DomainNumbers Event");
        domnum = domnumev.getDomnum();
        System.out.println("E2EAbstractionLogic--> domnum= " + domnum);
    }
    
    
    @Subscribe
    public void handle_ComputeE2EAbstractionRequest(ComputeE2EAbstractionRequest e2ecompev) {
        System.out.println("E2EAbstractionLogic--> Handle ComputeE2EAbstractionRequest Event");
        currdom++;

        if (currdom == domnum) {
            System.out.println("E2EAbstractionLogic--> Start E2E abstraction");
            //Compute virtual pop
            this.computevirtualPop();

            //compute logical link
            this.computeLogicalLink();
			
            /***********************************
             * COMPUTE FEDERATED LOGICAL LINK
             ***************************************/
             this.computeFederatedLogicalLink();
            
            
            
            ComputeE2EAbstractionReply e2ecomputerep = new ComputeE2EAbstractionReply();
            System.out.println("E2EAbstractionLogic--> Post ComputeE2EAbstractionReply Event");
            SingletonEventBus.getBus().post(e2ecomputerep);
        }
    }

    public void computevirtualPop() {
        
        //TODO: Check with radio info
        ArrayList<NfviPop> dompoplist = new ArrayList();
        ArrayList<Long> domid_nfvipop = new ArrayList();
        //ArrayList <ResourceZone> domzonelist = new ArrayList();
        long abstrdomid = -1, abstrpopid = -1;
        long reszoneid = -1;
        String zoneid = null;
        String zonename = null, zonestate = null, zoneproperty = null, metadata = null;
        long abstrzoneid = -1;
        String totCapacity = null;
        String allocatedCapacity = null;
        String reservedCapacity = null;
        String availableCapacity = null;
        String domtype = null;
        String associatedmecdom = null;

        //retrieve domain nfvipop
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM nfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(Long.toString(rs.getLong("nfviPopId")));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("vimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));
                domid_nfvipop.add(rs.getLong("domId"));

                System.out.println("E2EAbstractionLogic--> nfviPop.abstrNfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.VimId: " + nfviPop.getVimId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                dompoplist.add(nfviPop);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //Create abstract domain element
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            // Insert  data into computeservicerequestdata table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrdomain"
                    + "(tenantName,tenantId) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "SO_abstractview");
            ps.setLong(2, 10);

            ps.executeUpdate();
            System.out.println("E2EAbstractionLogic--> Abstract domain data inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                abstrdomid = rs.getLong(1);
                System.out.println("E2EAbstractionLogic--> abstrdomid:" + abstrdomid + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println("abstract domain table insert" +ex.getMessage()+ "");
        }
        //compute abstract DB for each nfvipop
        for (int i = 0; i < dompoplist.size(); i++) {
            //compute abstract NFVIPoP and insert into abstract NFVIPOP table
            NfviPop popel = dompoplist.get(i);
            long domval = domid_nfvipop.get(i);
            //check if domain type is VIM else pass the next pop
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("SELECT type,mecAssociatedDomainID FROM domain WHERE domId=?");
                ps.setLong(1, domval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    domtype = rs.getString("type");
                    associatedmecdom = rs.getString("mecAssociatedDomainID");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            if ((!domtype.equals("VIM")) && (!domtype.equals("RADIO"))) {
                continue;
            }

            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                // Insert  data into abstract nfvipop table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrnfvipop"
                        + "(abstrNfviPopId, vimId,geographicalLocationInfo,networkConnectivityEndpoint,abstrDomId) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
               
                
                ps.setString(1, dompoplist.get(i).getNfviPopId());
                ps.setLong(2, Long.parseLong(dompoplist.get(i).getVimId()));
                ps.setString(3, popel.getGeographicalLocationInfo());
                ps.setString(4, popel.getNetworkConnectivityEndpoint());
                ps.setLong(5, abstrdomid);

                ps.executeUpdate();
                System.out.println("E2EAbstractionLogic--> Abstract nfvipop data inserted into DB");
               // ResultSet rs = ps.getGeneratedKeys();

               // if (rs != null && rs.next()) {
               //     abstrpopid = rs.getLong(1);
               //     System.out.println("E2EAbstractionLogic--> abstrdomid:" + abstrpopid + "");
               // }
               // rs.close();
               abstrpopid = Long.parseLong(dompoplist.get(i).getNfviPopId());
              
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            //insert the abstractpopid into domain nfvipop table
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT nfviPopId,abstrNfviPopId"
                        + " FROM nfvipop where nfviPopId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, Long.valueOf(popel.getNfviPopId()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    rs.updateLong("abstrNfviPopId", abstrpopid);
                    rs.updateRow();

                    System.out.println("E2EAbstractionLogic--> Update abstract db in nfvipop!");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //if a associated MEC domain is associated to VIM domain fill the MEC region Info
            int assmec = Integer.valueOf(associatedmecdom);
            if( assmec != -1) {
                //Retrieve MEC region Info from dom db 
                ArrayList<MECRegionInfoMecRegionInfo> regionlist = new ArrayList();
                long regionid = -1;
                String latitude = null;
                String longitude = null;
                String altitude = null;
                String range = null;               
                
                try {
                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT regionId,latitude,longitude,altitude,range_"
                            + " from mec_region_info where domId=?");

                    ps.setLong(1, assmec);

                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        regionid = rs.getLong("regionId");
                        latitude = rs.getString("latitude");
                        longitude = rs.getString("longitude");
                        altitude = rs.getString("altitude");
                        range = rs.getString("range_");
                        
                        MECRegionInfoMecRegionInfo regionel = new MECRegionInfoMecRegionInfo();
                        LocationInfo locinfo = new LocationInfo();
                        locinfo.setLatitude(new BigDecimal(altitude));
                        locinfo.setAltitude(new BigDecimal(latitude));
                        locinfo.setLongitude(new BigDecimal(longitude));
                        locinfo.setRange(new BigDecimal(range));
                        regionel.setRegionId(String.valueOf(regionid));
                        regionel.setLocationInfo(locinfo);
                        regionlist.add(regionel);
                    }

                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(E2EAbstractionLogic.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                
                //Insert MEC region info
                for (int j = 0; j < regionlist.size(); j++) {
                    try {
                        java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO mec_region_info"
                                + "(regionId,latitude,longitude,altitude,range_,abstrNfviPopId) VALUES(?,?,?,?,?,?)");
                        ps.setLong(1, Long.valueOf(regionlist.get(j).getRegionId()));
                        ps.setString(2, String.valueOf(regionlist.get(j).getLocationInfo().getLatitude()));
                        ps.setString(3, String.valueOf(regionlist.get(j).getLocationInfo().getLongitude()));
                        ps.setString(4, String.valueOf(regionlist.get(j).getLocationInfo().getAltitude()));
                        ps.setString(5, String.valueOf(regionlist.get(j).getLocationInfo().getRange()));
                        ps.setLong(6,abstrpopid );

                        ps.executeUpdate();
                        System.out.println("E2EAbstractionLogic--> Abstract mec region info data inserted into abstract DB");
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            //Fill radio specific info with pnflist, radio coverage area
            if (domtype.equals("RADIO")) {
                //retrieve radio coverage area 
                ArrayList<RadioCoverageAreaListInnerRadioCoverageAreaInfo> regionlist = new ArrayList();
                String caid = null;
                String geographicalinfo = null;
                String caminbw =null;
                String camaxbw = null;
                String cadelay = null;
                String latitude = null;
                String longitude = null;
                String altitude = null;
                String range = null;               
                
                try {
                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT coverageAreaId,geographicalInfo,coverageAreaMinBandwidth,coverageAreaMaxBandwidth,"
                            + "coverageAreaDelay,latitude,longitude,altitude,range_"
                            + " from radio_coverage_area where nfviPopId=?");
                    
                    ps.setLong(1, Long.valueOf(popel.getNfviPopId()));

                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        caid = rs.getString("coverageAreaId");
                        geographicalinfo = rs.getString("geographicalInfo");
                        caminbw = rs.getString("coverageAreaMinBandwidth");
                        camaxbw = rs.getString("coverageAreaMaxBandwidth");
                        cadelay = rs.getString("coverageAreaDelay");
                        latitude = rs.getString("latitude");
                        longitude = rs.getString("longitude");
                        altitude = rs.getString("altitude");
                        range = rs.getString("range_");
                        
                        RadioCoverageAreaListInnerRadioCoverageAreaInfo regionel = new RadioCoverageAreaListInnerRadioCoverageAreaInfo();
                        LocationInfo locinfo = new LocationInfo();
                        locinfo.setLatitude(new BigDecimal(altitude));
                        locinfo.setAltitude(new BigDecimal(latitude));
                        locinfo.setLongitude(new BigDecimal(longitude));
                        locinfo.setRange(new BigDecimal(range));
                        regionel.setCoverageAreaId(caid);
                        regionel.setCoverageAreaDelay(new BigDecimal(cadelay));
                        regionel.setCoverageAreaGoegraphicalInfo(geographicalinfo);
                        regionel.setCoverageAreaMaxBandwidth(new BigDecimal(camaxbw));
                        regionel.setCoverageAreaMinBandwidth(new BigDecimal(caminbw));
                        regionel.setLocationInfo(locinfo);
                        regionlist.add(regionel);
                    }

                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(E2EAbstractionLogic.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                
                //Insert radio coverage area info
                for (int j = 0; j < regionlist.size(); j++) {
                    try {
                        java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO radio_coverage_area"
                                + "(coverageAreaId,geographicalInfo,coverageAreaMinBandwidth,coverageAreaMaxBandwidth,coverageAreaDelay,"
                                + "latitude,longitude,altitude,range_,abstrNfviPopId) VALUES(?,?,?,?,?,?,?,?,?,?)");
                        ps.setString(1, regionlist.get(j).getCoverageAreaId());
                        ps.setString(2, regionlist.get(j).getCoverageAreaGoegraphicalInfo());
                        ps.setString(3, regionlist.get(j).getCoverageAreaMinBandwidth().toString());
                        ps.setString(4, regionlist.get(j).getCoverageAreaMaxBandwidth().toString());
                        ps.setString(5, regionlist.get(j).getCoverageAreaDelay().toString());
                        ps.setString(6, String.valueOf(regionlist.get(j).getLocationInfo().getLatitude()));
                        ps.setString(7, String.valueOf(regionlist.get(j).getLocationInfo().getLongitude()));
                        ps.setString(8, String.valueOf(regionlist.get(j).getLocationInfo().getAltitude()));
                        ps.setString(9, String.valueOf(regionlist.get(j).getLocationInfo().getRange()));
                        ps.setLong(10,abstrpopid );

                        ps.executeUpdate();
                        System.out.println("E2EAbstractionLogic--> Abstract radio coverage area data inserted into abstract DB");
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //retrieve pnflist
                ArrayList<String> pnfids = new ArrayList();
                ArrayList<String> pnfstats = new ArrayList();
                String pnfid = null;
                String pnfstat = null;
                try {
                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT pnfid,pnfstatus"
                            + " from pnfs where nfviPopId=?");
                    
                    ps.setLong(1, Long.valueOf(popel.getNfviPopId()));

                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        pnfid = rs.getString("pnfid");
                        pnfstat = rs.getString("pnfstatus");
                        
                        pnfids.add(pnfid);
                        pnfstats.add(pnfstat);
                    }

                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(E2EAbstractionLogic.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                
                //Insert pnf info
                for (int j = 0; j < pnfids.size(); j++) {
                    try {
                        java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO pnfs"
                                + "(pnfid,pnfstatus,abstrNfviPopId) VALUES(?,?,?)");
                        ps.setString(1, pnfids.get(j));
                        ps.setString(2, pnfstats.get(j));
                        ps.setLong(3,abstrpopid );

                        ps.executeUpdate();
                        System.out.println("E2EAbstractionLogic--> Abstract pnf data inserted into abstract DB");
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
                
            //retrieve zoneid associated to the resource popid

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT resourceZoneId,zoneId,zoneName,zoneState,zoneProperty,metadata "
                        + "FROM zoneid WHERE nfviPopId=?");

                ps.setLong(1, Long.valueOf(popel.getNfviPopId()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    reszoneid = rs.getLong("resourceZoneId");
                    zoneid = rs.getString("zoneId");
                    zonename = rs.getString("zoneName");
                    zonestate = rs.getString("zoneState");
                    zoneproperty = rs.getString("zoneProperty");
                    metadata = rs.getString("metadata");

                    System.out.println("E2EAbstractionLogic--> zoneid.resourceZoneId " + reszoneid + "");
                    System.out.println("E2EAbstractionLogic--> zoneid.zoneId " + zoneid + "");
                    System.out.println("E2EAbstractionLogic--> zoneid.zoneName " + zonename + "");
                    System.out.println("E2EAbstractionLogic--> zoneid.zoneState " + zonestate + "");
                    System.out.println("E2EAbstractionLogic--> zoneid.zoneProperty " + zoneproperty + "");
                    System.out.println("E2EAbstractionLogic--> zoneid.metadata " + metadata + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //insert zoneid in abstract db
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                // Insert  data into abstract nfvipop table
//                PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrzoneid"
//                        + "(abstrZoneId,zoneName,zoneState,zoneProperty,metadata,abstrNfviPopId) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                ps.setLong(1, zoneid);
//                ps.setString(2, zonename);
//                ps.setString(3, zonestate);
//                ps.setString(4, zoneproperty);
//                ps.setString(5, metadata);
//                ps.setLong(6, abstrpopid);
                
                
                
                PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrzoneid"
                        + "(abstrResourceZoneId, abstrZoneId,zoneName,zoneState,zoneProperty,metadata,abstrNfviPopId) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, reszoneid);
                ps.setLong(2, reszoneid);
                ps.setString(3, zonename);
                ps.setString(4, zonestate);
                ps.setString(5, zoneproperty);
                ps.setString(6, metadata);
                ps.setLong(7, abstrpopid);

                ps.executeUpdate();
                System.out.println("E2EAbstractionLogic--> Abstract zoneid data inserted into DB");
//                ResultSet rs = ps.getGeneratedKeys();
//
//                if (rs != null && rs.next()) {
                abstrzoneid = reszoneid;
//                    abstrzoneid = rs.getLong(1);
//                    System.out.println("E2EAbstractionLogic--> abstrdomid:" + abstrpopid + "");
//                }
//                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

//UNCOMMENT when storage resources are available            
//            //retrieve storage from domain DB for the zoneid
//            try {
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//
//                PreparedStatement ps = conn.prepareStatement("select totalCapacity "
//                        + "from storageresources where resourceZoneId=?");
//
//                ps.setLong(1, reszoneid);
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    totCapacity = rs.getString("totalCapacity");
//                    System.out.println("E2EAbstractionLogic--> storageresources.totalCapacity " + totCapacity + "");
//                }
//
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(E2EAbstractionLogic.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//            //create storage resource in abstract db
//            try {
//                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
//
//                // Insert  data into abstract nfvipop table
//                PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrstorageresources"
//                        + "(availableCapacity,reservedCapacity,totalCapacity,allocatedCapacity,abstrResourceZoneId) VALUES(?,?,?,?,?)"/*, Statement.RETURN_GENERATED_KEYS*/);
//                ps.setString(1, totCapacity);
//                ps.setString(2, "0");
//                ps.setString(3, totCapacity);
//                ps.setString(4, "0");
//                ps.setLong(5, abstrzoneid);
//
//                ps.executeUpdate();
//                System.out.println("E2EAbstractionLogic--> Abstract storage resources data inserted into DB");
//                //ResultSet rs = ps.getGeneratedKeys();
//
////                if (rs != null && rs.next()) {
////                    abstrzoneid = rs.getLong(1);
////                    System.out.println("E2EAbstractionLogic--> abstrdomid:" + abstrpopid + "");
////                }
//                //rs.close();
//                ps.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(E2EAbstractionLogic.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
            //retrieve memory from domain DB for the zoneid
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT availableCapacity, reservedCapacity, allocatedCapacity, totalCapacity "
                        + "FROM memoryresources WHERE resourceZoneId=?");

                ps.setLong(1, reszoneid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    totCapacity = rs.getString("totalCapacity");
                    allocatedCapacity = rs.getString("allocatedCapacity");
                    reservedCapacity = rs.getString("reservedCapacity");
                    availableCapacity = rs.getString("availableCapacity");
        
                    System.out.println("E2EAbstractionLogic--> memoryresources.totalCapacity " + totCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //create memory resource in abstract db
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                // Insert  data into abstract nfvipop table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrmemoryresources"
                        + "(availableCapacity,reservedCapacity,totalCapacity,allocatedCapacity,abstrResourceZoneId) VALUES(?,?,?,?,?)"/*, Statement.RETURN_GENERATED_KEYS*/);
                ps.setString(1, availableCapacity);
                ps.setString(2, reservedCapacity);
                ps.setString(3, totCapacity);
                ps.setString(4, allocatedCapacity);
                ps.setLong(5, abstrzoneid);

                ps.executeUpdate();
                System.out.println("E2EAbstractionLogic--> Abstract memory resources data inserted into DB");
                //ResultSet rs = ps.getGeneratedKeys();

//                if (rs != null && rs.next()) {
//                    abstrzoneid = rs.getLong(1);
//                    System.out.println("E2EAbstractionLogic--> abstrdomid:" + abstrpopid + "");
//                }
                //rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            
    // RESET CAPACITY VALUES
                    totCapacity = null;
                    allocatedCapacity = null;
                    reservedCapacity = null;
                    availableCapacity = null;
            
            
            //retrieve cpu from domain DB for the zoneid
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT availableCapacity, reservedCapacity, allocatedCapacity, totalCapacity "
                        + "from cpuresources WHERE resourceZoneId=?");

                ps.setLong(1, reszoneid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    totCapacity = rs.getString("totalCapacity");
                    allocatedCapacity = rs.getString("allocatedCapacity");
                    reservedCapacity = rs.getString("reservedCapacity");
                    availableCapacity = rs.getString("availableCapacity");
                    
                    System.out.println("E2EAbstractionLogic--> cpuresources.totalCapacity " + totCapacity + "");
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //create cpu resource in abstract db
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                // Insert  data into abstract nfvipop table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO abstrcpuresources"
                        + "(availableCapacity,reservedCapacity,totalCapacity,allocatedCapacity,abstrResourceZoneId) VALUES(?,?,?,?,?)"/*, Statement.RETURN_GENERATED_KEYS*/);
                ps.setString(1, availableCapacity);
                ps.setString(2, reservedCapacity);
                ps.setString(3, totCapacity);
                ps.setString(4, allocatedCapacity);
                ps.setLong(5, abstrzoneid);

                ps.executeUpdate();
                System.out.println("E2EAbstractionLogic--> Abstract cpu resources data inserted into DB");
                //ResultSet rs = ps.getGeneratedKeys();

//                if (rs != null && rs.next()) {
//                    abstrzoneid = rs.getLong(1);
//                    System.out.println("E2EAbstractionLogic--> abstrdomid:" + abstrpopid + "");
//                }
                //rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } //end for nfvipop
    }

    public void computeLogicalLink() {
        
        //TODO R2: Insert abstraction of Radio and MEC 
        
        DirectedWeightedMultigraph graph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        ArrayList<NfviPop> domPopList = new ArrayList();
        ArrayList<Long> domid_nfvipop = new ArrayList();
        HashMap<DefaultWeightedEdge, TableReference> graphDbMap = new HashMap();
        ArrayList<String> logicalPathEndp = new ArrayList();
        ArrayList<Long> llEndpDomId = new ArrayList();
        String domtype = null;
        long domid = -1;
        long logicalLinkCount = 1;
        long logicallinkid = -1, logicalpathid = -1;
        //retrieve domain nfvipop
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from nfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(Long.toString(rs.getLong("nfviPopId")));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("vimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));
                domid_nfvipop.add(rs.getLong("domId"));
                System.out.println("E2EAbstractionLogic--> nfviPop.abstrNfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.VimId: " + nfviPop.getVimId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                domPopList.add(nfviPop);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //build vertex from gw in connectiivity endpoints. Supposed that are represented as string separated by ";"
        for (int i = 0; i < domPopList.size(); i++) {
            NfviPop popel = domPopList.get(i);
            Long domval = domid_nfvipop.get(i);

            //select domain type
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("SELECT type,domId FROM domain WHERE domId=?");
                ps.setLong(1, domval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    domtype = rs.getString("type");
                    domid = rs.getLong("domId");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            String connEdp = popel.getNetworkConnectivityEndpoint();
            String[] gwList = connEdp.split(";");

            for (int j = 0; j < gwList.length; j++) {
                graph.addVertex(gwList[j]);

                //if domain type is VIM insert the string in the logical link endpoints
                if (domtype.equals("VIM") || domtype.equals("RADIO")) {
                    logicalPathEndp.add(gwList[j]);
                    llEndpDomId.add(domid);
                }
            }
        }

        //retrieve networkresources, build edge and the hasmap rereference for the edge
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from networkresources");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //build edge
                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                graph.addEdge(rs.getString("srcGwId"), rs.getString("dstGwId"), edge);
                
                graph.setEdgeWeight(edge, rs.getDouble("delay"));
                

                //build reference to dbgraphmap
                //build table reference
                 TableReference dbref = new TableReference(-1, rs.getLong("networkResId"),-1);
                //add in dbgraphmap
                graphDbMap.put(edge, dbref);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //retrieve interdomainlink, build edge and the hasmap rereference for the edge
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from interdomainlink");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //build edge
                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                graph.addEdge(rs.getString("srcGwId"), rs.getString("dstGwId"), edge);
                graph.setEdgeWeight(edge, rs.getDouble("delay"));

                //build reference to dbgraphmap
                //build table reference
                TableReference dbref = new TableReference(rs.getLong("interDomainLinkId"), -1, -1);
                //add in dbgraphmap
                graphDbMap.put(edge, dbref);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        
        //compute all possible path for the endpoints of logical link
        //BUild graph completed. Compute all possible paths
        //AllDirectedPaths<String, DefaultWeightedEdge> allDirectedPaths = new AllDirectedPaths<>(graph);
        KShortestSimplePaths<String,DefaultWeightedEdge> kshortpaths = new KShortestSimplePaths(graph);
        
        for (int i = 0; i < logicalPathEndp.size(); i++) {
            String src = logicalPathEndp.get(i);
            long src_domid = llEndpDomId.get(i);
            for (int j = 0; j < logicalPathEndp.size(); j++) {
                String dst = logicalPathEndp.get(j);
                long dst_domid = llEndpDomId.get(j);
                if ((i == j) || (src_domid == dst_domid)) {
                    //no compute from vertex to vertex itself or from vertex of same domain id
                    continue;
                }
                //compute up to 2 logical paths
                //List<GraphPath<String, DefaultWeightedEdge>> paths = allDirectedPaths.getAllPaths(src, dst, true, 20);
                List<GraphPath<String, DefaultWeightedEdge>> paths = kshortpaths.getPaths(src, dst, 3);    
                //order the paths accoridng their weigth
                HashMap<Double, List<GraphPath<String, DefaultWeightedEdge>>> orderedpaths = new HashMap();
                for (int pathit = 0; pathit < paths.size(); pathit++) {
                    GraphPath<String, DefaultWeightedEdge> pathel = paths.get(pathit);
                    double tmp_delay = pathel.getWeight();
                    if (orderedpaths.get(tmp_delay) == null) {
                        //create new Value in the hashmap
                        List<GraphPath<String, DefaultWeightedEdge>> orderedpathlist = new ArrayList();
                        orderedpathlist.add(pathel);
                        orderedpaths.put(tmp_delay, orderedpathlist);
                    } else {
                        orderedpaths.get(tmp_delay).add(pathel);
                    }
                } //END FOR pathit
                //build logical link and physical path in DB
                Iterator<HashMap.Entry<Double, List<GraphPath<String, DefaultWeightedEdge>>>> it = orderedpaths.entrySet().iterator();
                while (it.hasNext()) {
                    //build logical link
                    HashMap.Entry<Double, List<GraphPath<String, DefaultWeightedEdge>>> ordpath = it.next();

                    //find source and destination abstract popid
                    long abstrSrcPopId = -1, abstrDstPopId = -1;
                    try {
                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("Select abstrNfviPopId from nfvipop where domId=?");
                        //query on src
                        ps.setLong(1, src_domid);
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            abstrSrcPopId = rs.getLong("abstrNfviPopId");
                        }
                        //query on dst
                        ps.setLong(1, dst_domid);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            abstrDstPopId = rs.getLong("abstrNfviPopId");
                        }
                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    //insert logical path in abstract DB
                    try {
                        java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                        // Insert  data into abstract logicallink table
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO logicallink"
                                + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, abstrSrcPopId);
                        ps.setLong(2, abstrDstPopId);
                        ps.setString(3, src);
                        ps.setString(4, dst);
                        ps.setString(5, Long.toString(logicalLinkCount));
                        ps.setString(6, Long.toString(logicalLinkCount));
                        ps.setString(7, Double.toString(ordpath.getKey()));
                        ps.executeUpdate();
                        //increment counter on logical link                        
                        logicalLinkCount++;
                        System.out.println("E2EAbstractionLogic--> Abstract logical link resources data inserted into DB");
                        ResultSet rs = ps.getGeneratedKeys();

                        if (rs != null && rs.next()) {
                            logicallinkid = rs.getLong(1);
                            System.out.println("E2EAbstractionLogic--> logicallinkid:" + logicallinkid + "");
                        }
                        rs.close();
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    float logicalLinkBw = Float.MAX_VALUE;
                    long logicalPathCount = 1;
					String srcGwIp = null;
                    String dstGwIp = null;					
                   



				   for (int count = 0; count < ordpath.getValue().size(); count++) {
                        //insert logicalpaths
                        GraphPath<String, DefaultWeightedEdge> graphpath = ordpath.getValue().get(count);
                        try {
                            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                            // Insert  data into abstract logicallink table
                            PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath"
                                    + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay,LogicalLinkId)"
                                    + "VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                            ps.setLong(1, abstrSrcPopId);
                            ps.setLong(2, abstrDstPopId);
                            ps.setString(3, src);
                            ps.setString(4, dst);
                            ps.setString(5, Long.toString(logicalPathCount));
                            ps.setString(6, Long.toString(logicalPathCount));
                            ps.setString(7, Double.toString(ordpath.getKey()));
                            ps.setLong(8, logicallinkid);
                            ps.executeUpdate();
                            //increment logicalPathCount;
                            logicalPathCount++;
                            System.out.println("E2EAbstractionLogic--> Abstract logicalpath resources data inserted into DB");
                            ResultSet rs = ps.getGeneratedKeys();

                            if (rs != null && rs.next()) {
                                logicalpathid = rs.getLong(1);
                                System.out.println("E2EAbstractionLogic--> logicalpathid:" + logicalpathid + "");
                            }
                            rs.close();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(E2EAbstractionLogic.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        //create association to interdomain link and network resources
                        float logicalPathBw = Float.MAX_VALUE;
                        List<DefaultWeightedEdge> edgelist = graphpath.getEdgeList();

                        int interDomainLinkCount = 0;                  													 
                        for (int edgecount = 0; edgecount < edgelist.size(); edgecount++) {
                            TableReference tableel = graphDbMap.get(edgelist.get(edgecount));
                            if (tableel != null) {
                                if (tableel.getInterdomainLinkId() != -1) {								
    
                                    if (interDomainLinkCount==0){
                                    
                                     //Retrieve the srcGwIp of logicalLink/logicalPaths
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select srcGwIp from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            srcGwIp = rs.getString("srcGwIp");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    } 
                                    interDomainLinkCount++;
                                    
                                    }else{
                                    //Retrieve the srcGwIp of logicalLink/logicalPaths
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select dstGwIp from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            dstGwIp = rs.getString("dstGwIp");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    } 
                                    
                                    interDomainLinkCount++;
                                    }
                                    
                                    
                                    
                                    
                                    //insert element in table association of interdoimain 
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath_interdomainlink"
                                                + "(logicalPathId,interDomainLinkId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getInterdomainLinkId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> interdomain resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> interdomain association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableBandwidth from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableBandwidth");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }
                                } else if (tableel.getNetworkResourcesId() != -1) {
                                    //insert element in table association of network
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath_networkresources"
                                                + "(logicalPathId,networkResId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getNetworkResourcesId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> netresources resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> netresources association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableCapacity from networkresources where networkResId=?");
                                        ps.setLong(1, tableel.getNetworkResourcesId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableCapacity");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }
                                }
                            }

                        }
                        //set bw for logical path
                        try {
                            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                             PreparedStatement ps = conn.prepareStatement("select logicalPathId,availableBandwidth,reservedBandwidth,"
                                    + "totalBandwidth,allocatedBandwidth, srcRouterIp, dstRouterIp"
                                    + " from logicalpath where logicalPathId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                           
						   ps.setLong(1, logicalpathid);
                            java.sql.ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                rs.updateString("availableBandwidth", Float.toString(logicalPathBw));
                                rs.updateString("reservedBandwidth", Float.toString(0));
                                rs.updateString("totalBandwidth", Float.toString(logicalPathBw));
                                rs.updateString("allocatedBandwidth", Float.toString(0));
                                rs.updateString("srcRouterIp", srcGwIp);
                                rs.updateString("dstRouterIp", dstGwIp);
                                
                                rs.updateRow();

                                System.out.println("E2EAbstractionLogic--> Update bw in logicalpath!");
                            }

                            rs.close();
                            ps.close();

                        } catch (SQLException ex) {
                            Logger.getLogger(E2EAbstractionLogic.class
                                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                        //compute bandwidth for logical link
                        if (logicalPathBw < logicalLinkBw) {
                            logicalLinkBw = logicalPathBw;
                        }
                    } //End FOR count 

                    //update bandwidth in logical link
                    try {
                        java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                           PreparedStatement ps = conn.prepareStatement("select logicalLinkId,availableBandwidth,reservedBandwidth,"
                                + "totalBandwidth,allocatedBandwidth, srcRouterIp, dstRouterIp"
                                + " from logicallink where logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, logicallinkid);
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            rs.updateString("availableBandwidth", Float.toString(logicalLinkBw));
                            rs.updateString("reservedBandwidth", Float.toString(0));
                            rs.updateString("totalBandwidth", Float.toString(logicalLinkBw));
                            rs.updateString("allocatedBandwidth", Float.toString(0));

                              rs.updateString("srcRouterIp", srcGwIp);
                              rs.updateString("dstRouterIp", dstGwIp);
                            rs.updateRow();

                            System.out.println("E2EAbstractionLogic--> Update bw in logicallink!");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                } //END iterator
            } //END FOR j
        } //END FOR i
    }

   
    
       public void computeFederatedLogicalLink() {
        
        //TODO R2: Insert abstraction of Radio and MEC 
        
        DirectedWeightedMultigraph graph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        ArrayList<NfviPop> popList = new ArrayList();
		ArrayList<NfviPop> fed_popList = new ArrayList();
       ArrayList<Long> domid_nfvipop = new ArrayList();
        HashMap<DefaultWeightedEdge, TableReference> graphDbMap = new HashMap();
        
        
		ArrayList<String> logicalPathEndp = new ArrayList();
        ArrayList<Long> llEndpDomId = new ArrayList();
		 ArrayList<String>  llEndpPopId = new ArrayList();
                  
                 
		 
		 ArrayList<String> fed_logicalPathEndp = new ArrayList();
       // ArrayList<Long> fed_llEndpDomId = new ArrayList();
		 ArrayList<String>  fed_llEndpPopId = new ArrayList();
		 
        String domtype = null;
        long domid = -1;
        long logicalLinkCount = 1;
        long logicallinkid = -1, logicalpathid = -1;
		/*********************************************************
        //RETRIEVE FEDERATED NFVI_POPs
		**********************************************************/      
	  try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

            //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
            //ps.setInt(1, 1);
            PreparedStatement ps = conn.prepareStatement("Select * from nfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(Long.toString(rs.getLong("nfviPopId")));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("federatedVimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));
                //domid_nfvipop.add(rs.getLong("domId"));
                System.out.println("E2EAbstractionLogic--> nfviPop.abstrNfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.VimId: " + nfviPop.getVimId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                fed_popList.add(nfviPop);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
		
		
		/*********************************************************
        //RETRIEVE STANDARD NFVI_POPs
		**********************************************************/      
	  try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

            PreparedStatement ps = conn.prepareStatement("Select * from nfvipop");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NfviPop nfviPop = new NfviPop();
                nfviPop.setNfviPopId(Long.toString(rs.getLong("nfviPopId")));
                nfviPop.setGeographicalLocationInfo(rs.getString("geographicalLocationInfo"));
                nfviPop.setVimId(rs.getString("vimId"));
                nfviPop.setNetworkConnectivityEndpoint(rs.getString("networkConnectivityEndpoint"));
                domid_nfvipop.add(rs.getLong("domId"));
                System.out.println("E2EAbstractionLogic--> nfviPop.abstrNfviPopId: " + nfviPop.getNfviPopId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.GeographicalLocationInfo: " + nfviPop.getGeographicalLocationInfo() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.VimId: " + nfviPop.getVimId() + "");
                System.out.println("E2EAbstractionLogic--> nfviPop.NetworkConnectivityEndpoint: " + nfviPop.getNetworkConnectivityEndpoint() + "");

                popList.add(nfviPop);
                //nfviPop.add(blog);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
		
		
		
		
		
		
		/*********************************************
		popList: List of nfvi-PoPs 
		domid_nfvipop: List of DomainIdentifier (domid) related to popList.   
		
			********************************************/
		
		/***************************************************************************
        //build vertex from gw in connectiivity endpoints. Supposed that are represented as string separated by ";"
         ******************************************************************************/
        for (int i = 0; i < popList.size(); i++) {
            NfviPop popel = popList.get(i);
            Long domval = domid_nfvipop.get(i);

            //select domain type
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

              
                PreparedStatement ps = conn.prepareStatement("SELECT type,domId FROM domain WHERE domId=?");
                ps.setLong(1, domval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    domtype = rs.getString("type");
                    domid = rs.getLong("domId");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            String connEdp = popel.getNetworkConnectivityEndpoint();
            String[] gwList = connEdp.split(";");

            for (int j = 0; j < gwList.length; j++) {
                graph.addVertex(gwList[j]);

                //if domain type is VIM insert the string in the logical link endpoints
                if (domtype.equals("VIM") || domtype.equals("RADIO")) {
                    logicalPathEndp.add(gwList[j]);
                    llEndpDomId.add(domid);
		    llEndpPopId.add(popel.getNfviPopId());
                }
            }
        }
		/************************************************************************
		 BUILD vertex from GW in connectivity endpoints belonging to FEDERATED nfvi-PoPs. Supposed that are represented as string separated by ";"
        *****************************************************************************/
		for (int i = 0; i < fed_popList.size(); i++) {
            NfviPop popel = fed_popList.get(i);
            //Long domval = domid_nfvipop.get(i);

            //select domain type
        /*     try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                //PreparedStatement ps = conn.prepareStatement("Select * from abstrnfvipop where AbstrNfviPopId=?");
                //ps.setInt(1, 1);
                PreparedStatement ps = conn.prepareStatement("SELECT type,domId FROM domain WHERE domId=?");
                ps.setLong(1, domval);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    domtype = rs.getString("type");
                    domid = rs.getLong("domId");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            } */

            String connEdp = popel.getNetworkConnectivityEndpoint();
            String[] gwList = connEdp.split(";");

            for (int j = 0; j < gwList.length; j++) {
                graph.addVertex(gwList[j]);

                
               
                    fed_logicalPathEndp.add(gwList[j]);
                    //fed_llEndpDomId.add(domid);
		    fed_llEndpPopId.add(popel.getNfviPopId());
            
            }
        }
		
		
		/**********************************************************************
        //Retrieve networkresources (WAN connectivities), build edge and the hashmap rereference for the edge
        ************************************************************************/
		try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

        
            PreparedStatement ps = conn.prepareStatement("Select * from networkresources");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //build edge
                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                graph.addEdge(rs.getString("srcGwId"), rs.getString("dstGwId"), edge);
                
                graph.setEdgeWeight(edge, rs.getDouble("delay"));
                

                //build reference to dbgraphmap
                //build table reference
                TableReference dbref = new TableReference(-1, rs.getLong("networkResId"), -1);
                
                
                
                //add in dbgraphmap
                graphDbMap.put(edge, dbref);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
	/**********************************************************************
        //Retrieve interdomainlink, build edge and the hashmap rereference for the edge
        ************************************************************************/
      
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

        
            PreparedStatement ps = conn.prepareStatement("Select * from interdomainlink");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //build edge
                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                graph.addEdge(rs.getString("srcGwId"), rs.getString("dstGwId"), edge);
                graph.setEdgeWeight(edge, rs.getDouble("delay"));

                //build reference to dbgraphmap
                //build table reference
                TableReference dbref = new TableReference(rs.getLong("interDomainLinkId"), -1, -1);
                //add in dbgraphmap
                graphDbMap.put(edge, dbref);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }catch (IllegalArgumentException ex) {
            Logger.getLogger(E2EAbstractionLogic.class.getName()).log(Level.SEVERE, null, ex);
            return;
            
        }
        
        /**********************************************************************
        //Retrieve federeted interdomainlink, build edge and the hashmap rereference for the edge
        ************************************************************************/
      
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

        
            PreparedStatement ps = conn.prepareStatement("Select * from fed_interdomainlink");

            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //build edge
                DefaultWeightedEdge edge = new DefaultWeightedEdge();
                graph.addEdge(rs.getString("srcGwId"), rs.getString("dstGwId"), edge);
                graph.setEdgeWeight(edge, rs.getDouble("delay"));

                //build reference to dbgraphmap
                //build table reference
                TableReference dbref = new TableReference(-1, -1, rs.getLong("interDomainLinkId"));
                //add in dbgraphmap
                graphDbMap.put(edge, dbref);
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }catch (IllegalArgumentException ex) {
            Logger.getLogger(E2EAbstractionLogic.class.getName()).log(Level.SEVERE, null, ex);
            return;
            
        }
          
        
        //compute all possible path for the endpoints of logical link
        //BUild graph completed. Compute all possible paths
        //AllDirectedPaths<String, DefaultWeightedEdge> allDirectedPaths = new AllDirectedPaths<>(graph);
        KShortestSimplePaths<String,DefaultWeightedEdge> kshortpaths = new KShortestSimplePaths(graph);
        
        for (int i = 0; i < fed_logicalPathEndp.size(); i++) {
            String src = fed_logicalPathEndp.get(i);
            long abstrSrcPopId = Long.parseLong(fed_llEndpPopId.get(i));
           // long src_domid = llEndpDomId.get(i);
            for (int j = 0; j < logicalPathEndp.size(); j++) {
                String dst = logicalPathEndp.get(j);
                 long abstrDstPopId = Long.parseLong(llEndpPopId.get(j));
             //   long dst_domid = llEndpDomId.get(j);
//                if ((i == j) || (src_domid == dst_domid)) {
//                    //no compute from vertex to vertex itself or from vertex of same domain id
//                    continue;
//                }


                //compute up to 2 logical paths
                //List<GraphPath<String, DefaultWeightedEdge>> paths = allDirectedPaths.getAllPaths(src, dst, true, 20);
                //Returns a list of the 3 shortest simple paths in increasing order of weight.
                List<GraphPath<String, DefaultWeightedEdge>> paths = kshortpaths.getPaths(src, dst, 3);    
                //order the paths accoridng their weigth
                HashMap<Double, List<GraphPath<String, DefaultWeightedEdge>>> orderedpaths = new HashMap();
                for (int pathit = 0; pathit < paths.size(); pathit++) {
                    GraphPath<String, DefaultWeightedEdge> pathel = paths.get(pathit);
                    double tmp_delay = pathel.getWeight();
                    if (orderedpaths.get(tmp_delay) == null) {
                        //create new Value in the hashmap
                        List<GraphPath<String, DefaultWeightedEdge>> orderedpathlist = new ArrayList();
                        orderedpathlist.add(pathel);
                        orderedpaths.put(tmp_delay, orderedpathlist); //Put into HashMap where the key is the weigth
                    } else {
                        orderedpaths.get(tmp_delay).add(pathel); //Put into List with same weight
                    }
                } //END FOR pathit
                //build logical link and physical path in DB
                Iterator<HashMap.Entry<Double, List<GraphPath<String, DefaultWeightedEdge>>>> it = orderedpaths.entrySet().iterator();
                while (it.hasNext()) {
                    //build logical link
                    HashMap.Entry<Double, List<GraphPath<String, DefaultWeightedEdge>>> ordpath = it.next();

                    //find source and destination abstract popid
                  //  long abstrSrcPopId = -1, abstrDstPopId = -1;
//                    try {
//                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//
//                        PreparedStatement ps = conn.prepareStatement("Select abstrNfviPopId from nfvipop where domId=?");
//                        //query on src
//                        ps.setLong(1, src_domid);
//                        java.sql.ResultSet rs = ps.executeQuery();
//                        while (rs.next()) {
//                            abstrSrcPopId = rs.getLong("abstrNfviPopId");
//                        }
//                        //query on dst
//                        ps.setLong(1, dst_domid);
//                        rs = ps.executeQuery();
//                        while (rs.next()) {
//                            abstrDstPopId = rs.getLong("abstrNfviPopId");
//                        }
//                        rs.close();
//                        ps.close();
//
//                    } catch (SQLException ex) {
//                        Logger.getLogger(E2EAbstractionLogic.class
//                                .getName()).log(Level.SEVERE, null, ex);
//                    }


                    //insert logical path in abstract DB
                    try {
                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                        // Insert  data into abstract logicallink table
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicallink"
                                + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, abstrSrcPopId);
                        ps.setLong(2, abstrDstPopId);
                        ps.setString(3, src);
                        ps.setString(4, dst);
                        ps.setString(5, Long.toString(logicalLinkCount));
                        ps.setString(6, Long.toString(logicalLinkCount));
                        ps.setString(7, Double.toString(ordpath.getKey()));
                        ps.executeUpdate();
                        //increment counter on logical link                        
                        logicalLinkCount++;
                        System.out.println("E2EAbstractionLogic--> Abstract logical link resources data inserted into DB");
                        ResultSet rs = ps.getGeneratedKeys();

                        if (rs != null && rs.next()) {
                            logicallinkid = rs.getLong(1);
                            System.out.println("E2EAbstractionLogic--> logicallinkid:" + logicallinkid + "");
                        }
                        rs.close();
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    float logicalLinkBw = Float.MAX_VALUE;
                    long logicalPathCount = 1;
                    String srcGwIp = null;
                    String dstGwIp = null;
                        
                    for (int count = 0; count < ordpath.getValue().size(); count++) {
                        //insert logicalpaths
                        GraphPath<String, DefaultWeightedEdge> graphpath = ordpath.getValue().get(count);
                        try {
                            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                            // Insert  data into abstract logicallink table
                            PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath"
                                    + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay,LogicalLinkId)"
                                    + "VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                            ps.setLong(1, abstrSrcPopId);
                            ps.setLong(2, abstrDstPopId);
                            ps.setString(3, src);
                            ps.setString(4, dst);
                            ps.setString(5, Long.toString(logicalPathCount));
                            ps.setString(6, Long.toString(logicalPathCount));
                            ps.setString(7, Double.toString(ordpath.getKey()));
                            ps.setLong(8, logicallinkid);
                            ps.executeUpdate();
                            //increment logicalPathCount;
                            logicalPathCount++;
                            System.out.println("E2EAbstractionLogic--> Abstract logicalpath resources data inserted into DB");
                            ResultSet rs = ps.getGeneratedKeys();

                            if (rs != null && rs.next()) {
                                logicalpathid = rs.getLong(1);
                                System.out.println("E2EAbstractionLogic--> logicalpathid:" + logicalpathid + "");
                            }
                            rs.close();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(E2EAbstractionLogic.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        //create association to interdomain link and network resources
                        float logicalPathBw = Float.MAX_VALUE;
                        List<DefaultWeightedEdge> edgelist = graphpath.getEdgeList();
                       
                        
                        for (int edgecount = 0; edgecount < edgelist.size(); edgecount++) {
                            TableReference tableel = graphDbMap.get(edgelist.get(edgecount));
                            if (tableel != null) {                                                                       
                                if (tableel.getInterdomainLinkId() != -1) {
                                    //insert element in table association of interdoimain 
                                    
                                    
                                    
                                    // Retrieve the dstGwIp of logicalPath/logicalLink 
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select dstGwIp from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            dstGwIp = rs.getString("dstGwIp");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }                              
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath_interdomainlink"
                                                + "(logicalPathId,interDomainLinkId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getInterdomainLinkId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> interdomain resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> interdomain association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableBandwidth from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableBandwidth");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }
                                } else if (tableel.getFed_interdomainLinkId() != -1) {
                                    // Retrieve the srcGwIp of logicalPath/logicalLink 
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select srcGwIp from fed_interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getFed_interdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            srcGwIp = rs.getString("srcGwIp");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //insert element in table association of interdoimain 
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath_fed_interdomainlink"
                                                + "(logicalPathId,interDomainLinkId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getFed_interdomainLinkId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> interdomain resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> interdomain association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableBandwidth from fed_interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getFed_interdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableBandwidth");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }  
                                    
                                    
                                } else if (tableel.getNetworkResourcesId() != -1) {
                                    //insert element in table association of network
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath_networkresources"
                                                + "(logicalPathId,networkResId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getNetworkResourcesId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> netresources resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> netresources association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableCapacity from networkresources where networkResId=?");
                                        ps.setLong(1, tableel.getNetworkResourcesId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableCapacity");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }
                                }
                            }

                        }
                        //set bw for logical path
                        try {
                            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

//                            PreparedStatement ps = conn.prepareStatement("select logicalPathId,availableBandwidth,reservedBandwidth,totalBandwidth,allocatedBandwidth"
//                                    + " from fed_logicalpath where logicalPathId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                                 PreparedStatement ps = conn.prepareStatement("select logicalPathId,availableBandwidth,reservedBandwidth,"
                                         + "totalBandwidth,allocatedBandwidth, srcRouterIp, dstRouterIp"
                                    + " from fed_logicalpath where logicalPathId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);



                            ps.setLong(1, logicalpathid);
                            java.sql.ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                rs.updateString("availableBandwidth", Float.toString(logicalPathBw));
                                rs.updateString("reservedBandwidth", Float.toString(0));
                                rs.updateString("totalBandwidth", Float.toString(logicalPathBw));
                                rs.updateString("allocatedBandwidth", Float.toString(0));
                                
                                rs.updateString("srcRouterIp", srcGwIp);
                                rs.updateString("dstRouterIp", dstGwIp);
                                
                                rs.updateRow();

                                System.out.println("E2EAbstractionLogic--> Update bw in logicalpath!");
                            }

                            rs.close();
                            ps.close();

                        } catch (SQLException ex) {
                            Logger.getLogger(E2EAbstractionLogic.class
                                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                        //compute bandwidth for logical link
                        if (logicalPathBw < logicalLinkBw) {
                            logicalLinkBw = logicalPathBw;
                        }
                    } //End FOR count 

                    //update bandwidth in logical link
                    try {
                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("select logicalLinkId,availableBandwidth,reservedBandwidth,"
                                + "totalBandwidth,allocatedBandwidth, srcRouterIp, dstRouterIp"
                                + " from fed_logicallink where logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, logicallinkid);
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            rs.updateString("availableBandwidth", Float.toString(logicalLinkBw));
                            rs.updateString("reservedBandwidth", Float.toString(0));
                            rs.updateString("totalBandwidth", Float.toString(logicalLinkBw));
                            rs.updateString("allocatedBandwidth", Float.toString(0));
                            
                            rs.updateString("srcRouterIp", srcGwIp);
                            rs.updateString("dstRouterIp", dstGwIp);
                            
                            rs.updateRow();

                            System.out.println("E2EAbstractionLogic--> Update bw in logicallink!");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                } //END iterator
            } //END FOR j
        } //END FOR i
        
        
   /*********************************************************************************************
        Compute all logicalPaths/LogicalLinks from standard nfvi-PoPs to federated nfvi-PoP
    ***********************************************************************************************/             
          for (int i = 0; i < logicalPathEndp.size(); i++) {
            String src = logicalPathEndp.get(i);
            long abstrSrcPopId = Long.parseLong(llEndpPopId.get(i));
           // long src_domid = llEndpDomId.get(i);
            for (int j = 0; j < fed_logicalPathEndp.size(); j++) {
                String dst = fed_logicalPathEndp.get(j);
                 long abstrDstPopId = Long.parseLong(fed_llEndpPopId.get(j));        
                 
                 //compute up to 2 logical paths
                //List<GraphPath<String, DefaultWeightedEdge>> paths = allDirectedPaths.getAllPaths(src, dst, true, 20);
                //Returns a list of the 3 shortest simple paths in increasing order of weight.
                List<GraphPath<String, DefaultWeightedEdge>> paths = kshortpaths.getPaths(src, dst, 3);    
                //order the paths accoridng their weigth
                HashMap<Double, List<GraphPath<String, DefaultWeightedEdge>>> orderedpaths = new HashMap();
                for (int pathit = 0; pathit < paths.size(); pathit++) {
                    GraphPath<String, DefaultWeightedEdge> pathel = paths.get(pathit);
                    double tmp_delay = pathel.getWeight();
                    if (orderedpaths.get(tmp_delay) == null) {
                        //create new Value in the hashmap
                        List<GraphPath<String, DefaultWeightedEdge>> orderedpathlist = new ArrayList();
                        orderedpathlist.add(pathel);
                        orderedpaths.put(tmp_delay, orderedpathlist); //Put into HashMap where the key is the weigth
                    } else {
                        orderedpaths.get(tmp_delay).add(pathel); //Put into List with same weight
                    }
                } //END FOR pathit
                //build logical link and physical path in DB
                Iterator<HashMap.Entry<Double, List<GraphPath<String, DefaultWeightedEdge>>>> it = orderedpaths.entrySet().iterator();
                while (it.hasNext()) {
                    //build logical link
                    HashMap.Entry<Double, List<GraphPath<String, DefaultWeightedEdge>>> ordpath = it.next();

                    //find source and destination abstract popid
                  //  long abstrSrcPopId = -1, abstrDstPopId = -1;
//                    try {
//                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//
//                        PreparedStatement ps = conn.prepareStatement("Select abstrNfviPopId from nfvipop where domId=?");
//                        //query on src
//                        ps.setLong(1, src_domid);
//                        java.sql.ResultSet rs = ps.executeQuery();
//                        while (rs.next()) {
//                            abstrSrcPopId = rs.getLong("abstrNfviPopId");
//                        }
//                        //query on dst
//                        ps.setLong(1, dst_domid);
//                        rs = ps.executeQuery();
//                        while (rs.next()) {
//                            abstrDstPopId = rs.getLong("abstrNfviPopId");
//                        }
//                        rs.close();
//                        ps.close();
//
//                    } catch (SQLException ex) {
//                        Logger.getLogger(E2EAbstractionLogic.class
//                                .getName()).log(Level.SEVERE, null, ex);
//                    }


                    //insert logical path in abstract DB
                    try {
                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                        // Insert  data into abstract logicallink table
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicallink"
                                + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, abstrSrcPopId);
                        ps.setLong(2, abstrDstPopId);
                        ps.setString(3, src);
                        ps.setString(4, dst);
                        ps.setString(5, Long.toString(logicalLinkCount));
                        ps.setString(6, Long.toString(logicalLinkCount));
                        ps.setString(7, Double.toString(ordpath.getKey()));
                        ps.executeUpdate();
                        //increment counter on logical link                        
                        logicalLinkCount++;
                        System.out.println("E2EAbstractionLogic--> Abstract logical link resources data inserted into DB");
                        ResultSet rs = ps.getGeneratedKeys();

                        if (rs != null && rs.next()) {
                            logicallinkid = rs.getLong(1);
                            System.out.println("E2EAbstractionLogic--> logicallinkid:" + logicallinkid + "");
                        }
                        rs.close();
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    float logicalLinkBw = Float.MAX_VALUE;
                    long logicalPathCount = 1;
                    String srcGwIp = null;
                    String dstGwIp = null;
                    
                    
                    for (int count = 0; count < ordpath.getValue().size(); count++) {
                        //insert logicalpaths
                        GraphPath<String, DefaultWeightedEdge> graphpath = ordpath.getValue().get(count);
                        try {
                            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                            // Insert  data into abstract logicallink table
                            PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath"
                                    + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay,LogicalLinkId)"
                                    + "VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                            ps.setLong(1, abstrSrcPopId);
                            ps.setLong(2, abstrDstPopId);
                            ps.setString(3, src);
                            ps.setString(4, dst);
                            ps.setString(5, Long.toString(logicalPathCount));
                            ps.setString(6, Long.toString(logicalPathCount));
                            ps.setString(7, Double.toString(ordpath.getKey()));
                            ps.setLong(8, logicallinkid);
                            ps.executeUpdate();
                            //increment logicalPathCount;
                            logicalPathCount++;
                            System.out.println("E2EAbstractionLogic--> Abstract logicalpath resources data inserted into DB");
                            ResultSet rs = ps.getGeneratedKeys();

                            if (rs != null && rs.next()) {
                                logicalpathid = rs.getLong(1);
                                System.out.println("E2EAbstractionLogic--> logicalpathid:" + logicalpathid + "");
                            }
                            rs.close();
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(E2EAbstractionLogic.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        //create association to interdomain link and network resources
                        float logicalPathBw = Float.MAX_VALUE;
                        List<DefaultWeightedEdge> edgelist = graphpath.getEdgeList();

                        for (int edgecount = 0; edgecount < edgelist.size(); edgecount++) {
                            TableReference tableel = graphDbMap.get(edgelist.get(edgecount));
                            if (tableel != null) {                                                                       
                                if (tableel.getInterdomainLinkId() != -1) {
                                    
                                    //Retrieve the srcGwIp of logicalLink/logicalPaths
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select srcGwIp from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            srcGwIp = rs.getString("srcGwIp");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    } 

                                    //insert element in table association of interdoimain 
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath_interdomainlink"
                                                + "(logicalPathId,interDomainLinkId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getInterdomainLinkId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> interdomain resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> interdomain association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableBandwidth from interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getInterdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableBandwidth");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }
                                } else if (tableel.getFed_interdomainLinkId() != -1) {
                                    // Retrieve the dstGwIp of logicalPath/logicalLink 
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select dstGwIp from fed_interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getFed_interdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            dstGwIp = rs.getString("dstGwIp");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //insert element in table association of interdoimain 
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath_fed_interdomainlink"
                                                + "(logicalPathId,interDomainLinkId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getFed_interdomainLinkId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> interdomain resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> interdomain association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableBandwidth from fed_interdomainlink where interDomainLinkId=?");
                                        ps.setLong(1, tableel.getFed_interdomainLinkId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableBandwidth");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }  
                                    
                                    
                                } else if (tableel.getNetworkResourcesId() != -1) {
                                    //insert element in table association of network
                                    try {
                                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                                        // Insert  data into abstract logicallink table
                                        PreparedStatement ps = conn.prepareStatement("INSERT INTO fed_logicalpath_networkresources"
                                                + "(logicalPathId,networkResId)"
                                                + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                                        ps.setLong(1, logicalpathid);
                                        ps.setLong(2, tableel.getNetworkResourcesId());

                                        ps.executeUpdate();
                                        System.out.println("E2EAbstractionLogic--> netresources resources data inserted into DB");
                                        ResultSet rs = ps.getGeneratedKeys();

                                        if (rs != null && rs.next()) {
                                            System.out.println("E2EAbstractionLogic--> netresources association key:" + rs.getLong(1) + "");
                                        }
                                        rs.close();
                                        ps.close();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //compute bw
                                    float freeBw = -1;
                                    //retrieve available bw from DB for the interdomain link
                                    try {
                                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                                        PreparedStatement ps = conn.prepareStatement("Select availableCapacity from networkresources where networkResId=?");
                                        ps.setLong(1, tableel.getNetworkResourcesId());
                                        java.sql.ResultSet rs = ps.executeQuery();
                                        while (rs.next()) {
                                            freeBw = rs.getFloat("availableCapacity");
                                        }
                                        rs.close();
                                        ps.close();

                                    } catch (SQLException ex) {
                                        Logger.getLogger(E2EAbstractionLogic.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if ((freeBw != -1) && (freeBw < logicalPathBw)) {
                                        logicalPathBw = freeBw;
                                    }
                                }
                            }

                        }
                        //set bw for logical path
                        try {
                            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                            PreparedStatement ps = conn.prepareStatement("select logicalPathId,availableBandwidth,reservedBandwidth,"
                                    + "totalBandwidth,allocatedBandwidth, srcRouterIp, dstRouterIp"
                                    + " from fed_logicalpath where logicalPathId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                            ps.setLong(1, logicalpathid);
                            java.sql.ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                rs.updateString("availableBandwidth", Float.toString(logicalPathBw));
                                rs.updateString("reservedBandwidth", Float.toString(0));
                                rs.updateString("totalBandwidth", Float.toString(logicalPathBw));
                                rs.updateString("allocatedBandwidth", Float.toString(0));
                                
                                
                                rs.updateString("srcRouterIp", srcGwIp);
                                rs.updateString("dstRouterIp", dstGwIp);
                                
                                rs.updateRow();

                                System.out.println("E2EAbstractionLogic--> Update bw in logicalpath!");
                            }

                            rs.close();
                            ps.close();

                        } catch (SQLException ex) {
                            Logger.getLogger(E2EAbstractionLogic.class
                                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        }
                        //compute bandwidth for logical link
                        if (logicalPathBw < logicalLinkBw) {
                            logicalLinkBw = logicalPathBw;
                        }
                    } //End FOR count 

                    //update bandwidth in logical link
                    try {
                        java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                        PreparedStatement ps = conn.prepareStatement("select logicalLinkId,availableBandwidth,reservedBandwidth,"
                                + "totalBandwidth,allocatedBandwidth, srcRouterIp, dstRouterIp "
                                + " from fed_logicallink where logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ps.setLong(1, logicallinkid);
                        java.sql.ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            rs.updateString("availableBandwidth", Float.toString(logicalLinkBw));
                            rs.updateString("reservedBandwidth", Float.toString(0));
                            rs.updateString("totalBandwidth", Float.toString(logicalLinkBw));
                            rs.updateString("allocatedBandwidth", Float.toString(0));
                            
                            rs.updateString("srcRouterIp", srcGwIp);
                            rs.updateString("dstRouterIp", dstGwIp);
                            
                            rs.updateRow();

                            System.out.println("E2EAbstractionLogic--> Update bw in logicallink!");
                        }

                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(E2EAbstractionLogic.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                } //END iterator
            } //END FOR j
        } //END FOR i
   
//   System.out.println("END");     
    }    
}
