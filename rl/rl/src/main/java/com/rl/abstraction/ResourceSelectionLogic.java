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
import com.rl.common.objects.ComputeAllocateElem;
import com.rl.common.objects.ComputeTerminateElem;
import com.rl.common.objects.Fed_NetworkAllocateElem;
import com.rl.common.objects.Fed_NetworkTerminateElem;
import com.rl.common.objects.NetworkAllocateElem;
import com.rl.common.objects.NetworkTerminateElem;
import com.rl.events.placement.PAComputeReply;
import com.rl.events.placement.PAComputeRequest;
import com.rl.events.placement.PAComputeStatus;
import com.rl.events.placement.PANetworkReply;
import com.rl.events.placement.PANetworkRequest;
import com.rl.events.placement.PANetworkStatus;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQuery;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateDBQueryReply;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstance;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateReply;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateDBQueryReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECQuery;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstance;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateReply;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstance;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateReply;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateInstance;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateReply;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateDBQuery;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQuery;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstance;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateReply;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateRequest;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateInstance;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateReply;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateRequest;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateDBQuery;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateDBQueryReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQuery;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateDBQueryReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateInstance;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateInstanceOutcome;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateRequest;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateDBQuery;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateDBQueryReply;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateInstance;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateInstanceOutcome;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateReply;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateRequest;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateDBQuery;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateDBQueryReply;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkPathList;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkPathListInner;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceSelectionLogic {

    private HashMap<Long, ComputeAllocateElem> compallmap;
    private HashMap<Long, NetworkAllocateElem> netallmap;
    private HashMap<Long, ComputeTerminateElem> comptermmap;
    private HashMap<Long, NetworkTerminateElem> nettermmap;
    private boolean pacompstatus;
    private boolean panetstatus;
    private long panetpathcount;
    private HashMap<Long, Fed_NetworkAllocateElem> fed_netallmap;
    private HashMap<Long, Fed_NetworkTerminateElem> fed_nettermmap;
    private long fedvlancounter; 
    private HashMap<Long, PNFRequest> pnfallreqmap;
    private HashMap<Long, PNFReply> pnftermreqmap;

    
    
    public ResourceSelectionLogic() {
        compallmap = new HashMap();
        netallmap = new HashMap();
        comptermmap = new HashMap();
        nettermmap = new HashMap();
        pacompstatus = false;
        panetstatus = false;
        panetpathcount = 1000; //From 1000 to on are the computed logical paths from pa network
        fed_netallmap = new HashMap();
        fed_nettermmap = new HashMap();
        fedvlancounter = 100;
        pnfallreqmap = new HashMap();
        pnftermreqmap = new HashMap();
    }
    
    @Subscribe
    public void handle_PAComputeStatus(PAComputeStatus pacompst) {
        System.out.println("ResourceSelectionLogic --> Handle PAComputeStatus Event");
        String compstring = new String ("enable");
        if (pacompst.getStatus().compareTo(compstring) == 0) {
            pacompstatus = true;
        } else {
            pacompstatus = false;
        }
    }
    
    @Subscribe
    public void handle_PANetworkStatus(PANetworkStatus panetst) {
        System.out.println("ResourceSelectionLogic --> Handle PANetworkStatus Event");
        String compstring = new String ("enable");
        if (panetst.getStatus().compareTo(compstring) == 0) {
            panetstatus = true;
        } else {
            panetstatus = false;
        }
    }
    
    @Subscribe
    public void handle_E2EComputeAllocateRequest(E2EComputeAllocateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2EComputeAllocateRequest Event");
        //insert element in hasmap of pening allocate req
        ComputeAllocateElem compel = new ComputeAllocateElem();
        compel.setAllocateRequest(allreqev);
        compallmap.put(allreqev.getReqid(), compel);
        

//// START - Insert servID into Service table
//        try {
//            
//    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO service (servId) VALUES(?)");
//            ps.setLong(1,allreqev.getServId());
//
//            ps.executeUpdate();
//            ps.close();
//
//        } catch (SQLIntegrityConstraintViolationException ex) {
////            Logger.getLogger(ResourceSelectionLogic.class
////                    .getName()).log(Level.SEVERE, null, ex);
//             System.out.println("ResourceSelectionLogic.handle_E2EComputeAllocateRequest ---> SQLIntegrityConstraintViolationException: Duplicate entry");
//        }
//     catch (SQLException ex) {
//          Logger.getLogger(ResourceSelectionLogic.class
//                   .getName()).log(Level.SEVERE, null, ex);
//      }
//// END - Insert
        //Prepare and perform the query
        ComputeAllocateDBQuery dbev = new ComputeAllocateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getRequest());
        //post event
        System.out.println("ResourceSelectionLogic --> Post ComputeAllocateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_ComputeAllocateDBQueryReply(ComputeAllocateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle ComputeAllocateDBQueryReply Event");

     
        // Select a nfviPopId from the list
        // Selection Criterion: retrieve the first elementn of the list 
        // Upgrade the selection criteria if needed 
        ArrayList<Long> vimlist = dbreply.getPoplist(); //Retrieve the first element (nfviPopId) of the list 
        
        long nfviPopId = vimlist.get(0);
        // START - Retrieve from DB the DomId related to the nfviPoPId
        Integer domId = null;
        System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> selected "
                + "nfviPopId: " + nfviPopId + "");
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId from nfvipop where nfviPopId=?");
            ps.setLong(1, nfviPopId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                domId = rs.getInt("domId");
                System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> "
                        + "domId: " + domId + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //END - Retrieve
        ComputeAllocateElem compAllocElem = compallmap.get(dbreply.getReqid());
        if (pacompstatus == true ) {
            PAComputeRequest compreq = new PAComputeRequest(dbreply.getReqid(),
                dbreply.getServid(), domId, nfviPopId, compAllocElem);
            System.out.println("ResourceSelectionLogic --> Post PAComputeRequest Event");
            SingletonEventBus.getBus().post(compreq);
            return;
        }
        //Retrieve MF to put in the instance
        List<String> mflist = new ArrayList();
        String vnfid = "";
        List<MetaDataInner> metadata = compAllocElem.getAllocateRequest().getRequest().getMetadata();
        for (int i = 0; i < metadata.size(); i++) {
            if (metadata.get(i).getKey().compareTo("VnfId") == 0) {
                vnfid = metadata.get(i).getValue();
            }
        }
        
        if (!vnfid.equals("")) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select mfid from mfs where nfviPopId=? and vnfid=?");
                ps.setLong(1, nfviPopId);
                ps.setString(2, vnfid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mflist.add(rs.getString("mfid"));
                    System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> "
                            + "mfid: " + rs.getString("mfid") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //END - Retrieve
        }
        E2EComputeAllocateInstance allinst = new E2EComputeAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), domId, nfviPopId, mflist, compAllocElem);
        System.out.println("ResourceSelectionLogic --> Post E2EComputeAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_PAComputeReply(PAComputeReply pacomprep) {
        System.out.println("ResourceSelectionLogic --->  Handle PAComputeReply Event");
        //Retrieve ComputeAllocateElem instance  from compallmap using the reqId
        ComputeAllocateElem compAllocElem = compallmap.get(pacomprep.getReqid());
        List<String> mflist = new ArrayList();
        String vnfid = "";
        List<MetaDataInner> metadata = compAllocElem.getAllocateRequest().getRequest().getMetadata();
        for (int i = 0; i < metadata.size(); i++) {
            if (metadata.get(i).getKey().compareTo("VnfId") == 0) {
                vnfid = metadata.get(i).getValue();
            }
        }
        
        if (!vnfid.equals("")) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select mfid from mfs where nfviPopId=? and vnfid=?");
                ps.setLong(1, pacomprep.getNfvipopid());
                ps.setString(2, vnfid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mflist.add(rs.getString("mfid"));
                    System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> "
                            + "mfid: " + rs.getString("mfid") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //END - Retrieve
        }
        E2EComputeAllocateInstance allinst = new E2EComputeAllocateInstance(pacomprep.getReqid(),
                pacomprep.getServid(), pacomprep.getDomid(), pacomprep.getNfvipopid(), mflist, compAllocElem);
        System.out.println("ResourceSelectionLogic --> Post E2EComputeAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);
    }
    @Subscribe
    public void handle_E2EComputeAllocateInstanceOutcome(E2EComputeAllocateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2EComputeAllocateInstanceOutcome Event");

        E2EComputeAllocateReply allrepout = new E2EComputeAllocateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getComputereply(), allinstout.isOutcome());
        System.out.println("ResourceSelectionLogic --> Post E2EComputeAllocateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }

    @Subscribe
    public void handle_E2EPhysicalAllocateRequest(E2EPhysicalAllocateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2EPhysicalAllocateRequest Event");
        //insert element in hasmap of pending allocate req
        pnfallreqmap.put(allreqev.getReqid(), allreqev.getRequest());
 
        //Prepare and perform the query
        PhysicalAllocateDBQuery dbev = new PhysicalAllocateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getRequest());
        //post event
        System.out.println("ResourceSelectionLogic --> Post PhysicalAllocateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }
    
    @Subscribe
    public void handle_PhysicalAllocateDBQueryReply(PhysicalAllocateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle PhysicalAllocateDBQueryReply Event");

     
        // Select a nfviPopId from the list
        // Selection Criterion: retrieve the first elementn of the list 
        // Upgrade the selection criteria if needed 
        ArrayList<Long> vimlist = dbreply.getPoplist(); //Retrieve the first element (nfviPopId) of the list 
        
        long nfviPopId = vimlist.get(0);
        // START - Retrieve from DB the DomId related to the nfviPoPId
        long domId = dbreply.getDomlist().get(0);
        PNFRequest pnfAllocElem = pnfallreqmap.get(dbreply.getReqid());
        //Retrieve ComputeAllocateElem instance  from compallmap using the reqId
        //Retrieve MF to put in the instance
        List<String> mflist = new ArrayList();
        String pnfid = "";
        pnfid = pnfAllocElem.getPnfId();
        
        if (!pnfid.equals("")) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select mfid from mfs where nfviPopId=? and pnfid=?");
                ps.setLong(1, nfviPopId);
                ps.setString(2, pnfid);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    mflist.add(rs.getString("mfid"));
                    System.out.println("ResourceSelectionLogic.handle_ComputeAllocateDBQueryReply ---> "
                            + "mfid: " + rs.getString("mfid") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //END - Retrieve
        }
        
        
        E2EPhysicalAllocateInstance allinst = new E2EPhysicalAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), domId, nfviPopId, pnfAllocElem, mflist);
        System.out.println("ResourceSelectionLogic --> Post E2EPhysicalAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2EPhysicalAllocateInstanceOutcome(E2EPhysicalAllocateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2EPhysicalAllocateInstanceOutcome Event");

        E2EPhysicalAllocateReply allrepout = new E2EPhysicalAllocateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getComputereply(), allinstout.isOutcome());
        System.out.println("ResourceSelectionLogic --> Post E2EPhysicalAllocateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }
    
    @Subscribe
    public void handle_E2ENetworkAllocateRequest(E2ENetworkAllocateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkAllocateRequest Event");
     

        //insert element in hasmap of pening allocate req
        NetworkAllocateElem netel = new NetworkAllocateElem();
        netel.setAllocateRequest(allreqev);
        netallmap.put(allreqev.getReqid(), netel);
        
        
        for (int i = 0; i < allreqev.getNetworkreq().getLogicalLinkPathList().size(); i++) {

        long physicalpath = -1, logicallink = -1;
        float availablebw = -1; 
            
            
            
            //Get the logicalLinkId from dbquery
            logicallink = Long.parseLong(allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getLogicalLinkId());
            allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqBandwidth();
            System.out.println("handle_E2ENetworkAllocateRequest ----> LogicalLinkId:" + logicallink + "");
            if (pacompstatus == true) {
                //Prepare and perform the query
                PANetworkRequest panetreq = new PANetworkRequest(allreqev.getReqid(),
                        allreqev.getServid(), allreqev.getNetworkreq(), logicallink, 
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqBandwidth(),
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqLatency(),
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getSrcGwIpAddress(),
                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getDstGwIpAddress());
                //post event
                System.out.println("ResourceSelectionLogic --> Post PANetworkRequest Event");
                SingletonEventBus.getBus().post(panetreq);
                continue;
            }        
            //rerieve from DB logical paths related to the logicalLinkId 
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId,availableBandwidth  FROM logicalpath where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    if (availablebw == -1) {
                        //first element is selected
                        physicalpath = rs.getLong("logicalPathId");
                        availablebw = rs.getFloat("availableBandwidth");
                    } else {
                        //check if tmp_available bw is bigger and select it
                        float tmp_availablebw = rs.getFloat("availableBandwidth");
                        //TODO: insert control on bw request when IFA005 is fixed
                        //if ( /*(tmp_availablebw >= allreqev.getE2EWIMElem().) &&*/ 
                        //        (tmp_availablebw > availablebw)) {
                        if (tmp_availablebw > availablebw) {
                            //select this as candidate
                            physicalpath = rs.getLong("logicalPathId");
                            availablebw = tmp_availablebw;
                        }
                    }
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> physicalpath: " + physicalpath + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            //Prepare and perform the query
//            NetworkAllocateDBQuery dbev = new NetworkAllocateDBQuery(allreqev.getReqid(),
//                    allreqev.getServid(), allreqev.getNetworkreq(), physicalpath);
            NetworkAllocateDBQuery dbev = new NetworkAllocateDBQuery(allreqev.getReqid(),
                    allreqev.getServid(), allreqev.getNetworkreq().getLogicalLinkPathList().get(i) , physicalpath);
            
            //post event
            System.out.println("ResourceSelectionLogic --> Post NetworkAllocateDBQuery Event");
            SingletonEventBus.getBus().post(dbev);
        }
    }
    
    @Subscribe
    public void handle_PANetworkReply(PANetworkReply panetrep) {
        System.out.println("ResourceSelectionLogic --->  Handle PANetworkReply Event");
        long logicalpathid = -1; //key used to insert the phisicalpath
        Integer AbstrSrcPoPId = -1, AbstrDstPoPId = -1;
        String srcid = null, dstid = null;
        //Select abstract src and dst popid, src and dst

        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT abstrSrcNfviPopId,abstrDestNfviPopId,"
                    + "srcRouterId,dstRouterId  FROM logicallink where logicalLinkId=?");
            ps.setLong(1, panetrep.getLogicalpathid());
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with minimum available bandwidth
            while (rs.next()) {
               AbstrSrcPoPId = rs.getInt("abstrSrcNfviPopId");
               AbstrDstPoPId = rs.getInt("abstrDestNfviPopId");
               srcid = rs.getString("srcRouterId");
               dstid = rs.getString("dstRouterId");
            }
            System.out.println("handle_PANetworkReply ---> AbstrSrcPoPId: " + AbstrSrcPoPId + "");
            System.out.println("handle_PANetworkReply ---> AbstrDstPoPId: " + AbstrDstPoPId + "");
            System.out.println("handle_PANetworkReply ---> srcid: " + srcid + "");
            System.out.println("handle_PANetworkReply ---> dstid: " + dstid + "");

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //retrieve interdomain link id delay and bw
        ArrayList<Integer> interdomainkeys = new ArrayList<Integer>();
        ArrayList<Integer> wanlinkkeys = new ArrayList<Integer>();
        ArrayList<Float> delayvals = new ArrayList<Float>();
        ArrayList<Float> bwvals = new ArrayList<Float>();
        
        for (int i =0; i < panetrep.getInterWanLinks().size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select interDomainLinkId,delay,availableBandwidth from interdomainlink "
                        + "where srcDomId=? AND dstDomId=? AND srcGwId=? AND dstGwId=? AND srcGWIp=? AND dstGwIp=?");
                ps.setInt(1, Integer.valueOf(panetrep.getInterWanLinks().get(i).getAWimId()));
                ps.setInt(2, Integer.valueOf(panetrep.getInterWanLinks().get(i).getZWimId()));
                ps.setString(3, panetrep.getInterWanLinks().get(i).getAPEId());
                ps.setString(4, panetrep.getInterWanLinks().get(i).getZPEId());
                ps.setString(5, String.valueOf(panetrep.getInterWanLinks().get(i).getALinkId()));
                ps.setString(6, String.valueOf(panetrep.getInterWanLinks().get(i).getZLinkId()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    interdomainkeys.add(rs.getInt("interDomainLinkId"));
                    delayvals.add(Float.valueOf(rs.getString("delay")));
                    bwvals.add(Float.valueOf(rs.getString("availableBandwidth")));
                    System.out.println("handle_PANetworkReply ---> "
                            + "interdomainkeys: " + rs.getInt("interDomainLinkId") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "delayvals: " + rs.getString("delay") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "bwvals: " + rs.getString("availableBandwidth") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        //retrieve wan paths link id, delay and bw
        for (int i =0; i < panetrep.getWanPaths().size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select networkResId,delay,availableCapacity from networkresources "
                        + "where  srcGwId=? AND dstGwId=? AND srcGWIp=? AND dstGwIp=?");
                ps.setString(1, panetrep.getWanPaths().get(i).getWanPathElements().get(0).getANodeId());
                ps.setString(2, panetrep.getWanPaths().get(i).getWanPathElements().get(0).getZNodeId());
                ps.setString(3, String.valueOf(panetrep.getWanPaths().get(i).getWanPathElements().get(0).getALinkId()));
                ps.setString(4, String.valueOf(panetrep.getWanPaths().get(i).getWanPathElements().get(0).getZLinkId()));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    wanlinkkeys.add(rs.getInt("networkResId"));
                    delayvals.add(Float.valueOf(rs.getString("delay")));
                    bwvals.add(Float.valueOf(rs.getString("availableCapacity")));
                    System.out.println("handle_PANetworkReply ---> "
                            + "wanlinkkeys: " + rs.getInt("networkResId") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "delayvals: " + rs.getString("delay") + "");
                    System.out.println("handle_PANetworkReply ---> "
                            + "bwvals: " + rs.getString("availableCapacity") + "");
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        //compute delay as sum of values in delayvals
        double latencyval;
        latencyval = 0.0;
        for (int i = 0; i < delayvals.size(); i++) {
            latencyval += delayvals.get(i);
        }
        double bwval;
        bwval = bwvals.get(0); //set to first value
        for (int i = 1; i < delayvals.size(); i++) { //not check first value
            if (bwvals.get(i) <bwval) {
                bwval = bwvals.get(i);
            }
        }
        
        //Insert new logicalpath associated to the logicalpathid
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

            // Insert  data into abstract logicallink table
            PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath"
                    + "(abstrSrcNfviPopId,abstrDestNfviPopId,srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay,"
                    + "availableBandwidth,reservedBandwidth,totalBandwidth,allocatedBandwidth,LogicalLinkId)"
                    + "VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, AbstrSrcPoPId);
            ps.setLong(2, AbstrDstPoPId);
            ps.setString(3, srcid);
            ps.setString(4, dstid);
            ps.setString(5, Long.toString(panetpathcount));
            ps.setString(6, Long.toString(panetpathcount));
            ps.setString(7, Double.toString(latencyval));
            ps.setString(8, Double.toString(bwval));
            ps.setString(8, Double.toString(0.0));
            ps.setString(10, Double.toString(0.0));
            ps.setString(11, Double.toString(bwval));
            ps.setLong(12, panetrep.getLogicalpathid());
            ps.executeUpdate();
            //increment logicalPathCount;
            panetpathcount++;
            System.out.println("handle_PANetworkReply ---> Abstract logicalpath resources data inserted into DB");
            ResultSet rs = ps.getGeneratedKeys();

            if (rs != null && rs.next()) {
                logicalpathid = rs.getLong(1);
                System.out.println("handle_PANetworkReply ---> logicalpathid:" + logicalpathid + "");
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(E2EAbstractionLogic.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //create association logicalpathid and iterdomain links
        for (int i = 0; i < interdomainkeys.size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                // Insert  data into abstract logicallink table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath_interdomainlink"
                        + "(logicalPathId,interDomainLinkId)"
                        + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, logicalpathid);
                ps.setLong(2, interdomainkeys.get(i));

                ps.executeUpdate();
                System.out.println("handle_PANetworkReply ---> interdomain resources data inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    System.out.println("handle_PANetworkReply ---> interdomain association key:" + rs.getLong(1) + "");
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //create association logical path link and wan links
        for (int i = 0; i < wanlinkkeys.size(); i++) {
            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();

                // Insert  data into abstract logicallink table
                PreparedStatement ps = conn.prepareStatement("INSERT INTO logicalpath_networkresources"
                        + "(logicalPathId,networkResId)"
                        + "VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, logicalpathid);
                ps.setLong(2, wanlinkkeys.get(i));

                ps.executeUpdate();
                System.out.println("handle_PANetworkReply ---> netresources resources data inserted into DB");
                ResultSet rs = ps.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    System.out.println("handle_PANetworkReply ---> netresources association key:" + rs.getLong(1) + "");
                }
                rs.close();
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(E2EAbstractionLogic.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Call NetworkAllocateDBQuery
        NetworkAllocateElem elem = netallmap.get(panetrep.getReqid());
        int size = elem.getAllocateRequest().getNetworkreq().getLogicalLinkPathList().size(); 
       
       for (int i = 0; i < wanlinkkeys.size(); i++) {
        
        
//        NetworkAllocateDBQuery dbev = new NetworkAllocateDBQuery(panetrep.getReqid(),
//               panetrep.getServid(), 
//                netallmap.get(panetrep.getReqid()).getAllocateRequest().getNetworkreq(), 
//                logicalpathid);
        
         NetworkAllocateDBQuery dbev = new NetworkAllocateDBQuery(panetrep.getReqid(),
               panetrep.getServid(), 
                elem.getAllocateRequest().getNetworkreq().getLogicalLinkPathList().get(i), 
                logicalpathid);
        
       //post event
       System.out.println("ResourceSelectionLogic --> Post NetworkAllocateDBQuery Event");
       SingletonEventBus.getBus().post(dbev);
       }
    }

    
    @Subscribe
    public void handle_NetworkAllocateDBQueryReply(NetworkAllocateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle NetworkAllocateDBQueryReply Event");

        E2ENetworkAllocateInstance allinst = new E2ENetworkAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getLogicalLPathId(), dbreply.getWimdomlist(), 
                dbreply.getVimdomlist(), dbreply.getInterdomainLinks(), dbreply.getIntraPopLinks(),
                dbreply.getWanLinks(), dbreply.getWimPopList(), dbreply.getVimPopList(), 
                netallmap.get(dbreply.getReqid()).getAllocateRequest().getNetworkreq(),
                dbreply.getWimNetworkType(), dbreply.getVimNetworkType());

        System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2ENetworkAllocateInstanceOutcome(E2ENetworkAllocateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkAllocateInstanceOutcome Event");
        
        if (allinstout.isOutcome() == false ) {
            //send negative response
            E2ENetworkAllocateReply allrepout = new E2ENetworkAllocateReply(allinstout.getReqid(),
                    allinstout.getServid(), null, allinstout.isOutcome());
            System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateReply Event");
            SingletonEventBus.getBus().post(allrepout);
            return;
        }
        
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1, ll_availablebw = -1;
        //retrieve from abstract DB the logical link id from the logical paths, 
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId  FROM logicalpath where logicalPathId=?");
            ps.setLong(1, allinstout.getLogicalpath());
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                logicallink = rs.getLong("logicalLinkId");  
            }
            System.out.println("handle_E2ENetworkAllocateRequest ---> logicallink: " + logicallink + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //retrieve from abstract DB the available bw from logicallink
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicallink where logicalLinkId=?");
            ps.setLong(1, logicallink);
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                ll_availablebw = rs.getLong("availableBandwidth");
            }
            System.out.println("handle_E2ENetworkAllocateRequest ---> ll_availablebw: " + ll_availablebw + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //get the maximum available bw from logical path associated to the same logicallink id        
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicalpath where logicalLinkId=?");
            ps.setLong(1, logicallink);
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                
                if (availablebw == -1) {
                    //first element is selected
                    availablebw = rs.getFloat("availableBandwidth");
                } else {
                    //check if available bw is greater and select it
                    float tmp_availablebw = rs.getFloat("availableBandwidth");
                    if (tmp_availablebw > availablebw) {
                        //select this as candidate
                        availablebw = tmp_availablebw;
                    }
                }   
            }
            System.out.println("handle_E2ENetworkAllocateRequest ---> availablebw: " + availablebw + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //check if aavailable bw is the same as represented in the logical link. 
        //If not change the bandwidth capacity values in logical link in DB 
        if (availablebw != ll_availablebw) {
            //change bandwidth capacity in logical link
            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long totalBandwidth;

                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "logicalLinkId, "
                        + "availableBandwidth,"
                        + "totalBandwidth,"
                        + "allocatedBandwidth "
                        + "FROM logicallink WHERE logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableBandwidth");
                    totalBandwidth = rs.getLong("totalBandwidth");
                    allocatedBandwidth = rs.getLong("allocatedBandwidth");
                    rs.updateString("availableBandwidth", Float.toString(availablebw));
                    rs.updateString("allocatedBandwidth", Float.toString(totalBandwidth-availablebw));
                    rs.updateRow();

                    System.out.println("handle_E2ENetworkAllocateRequest ---> Bandwidth updated in Abstract logical link table!");
                    System.out.println("handle_E2ENetworkAllocateRequest ---> new available bandwidth " + availablebw + "");
                    
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
        
        E2ENetworkAllocateReply allrepout = new E2ENetworkAllocateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getVn(), allinstout.isOutcome());
        System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }

    @Subscribe
    public void handle_E2EComputeTerminateRequest(E2EComputeTerminateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2EComputeTerminateRequest Event");
        //insert element in hasmap of pening allocate req
        ComputeTerminateElem compel = new ComputeTerminateElem();
        compel.setTerminateRequest(allreqev.getComputereq());
        comptermmap.put(allreqev.getReqid(), compel);

        //Prepare and perform the query
//        ComputeTerminateDBQuery dbev = new ComputeTerminateDBQuery(allreqev.getReqid(),
//                allreqev.getServid(), allreqev.getComputereq());
//        System.out.println("ResourceSelectionLogic --> Post ComputeTerminateDBQuery Event");      
//        SingletonEventBus.getBus().post(dbev);
          
          ComputeTerminateMECQuery dbev = new ComputeTerminateMECQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getComputereq());
        //post event
        System.out.println("ResourceSelectionLogic --> Post ComputeTerminateMECQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_ComputeTerminateDBQueryReply(ComputeTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle ComputeTerminateDBQueryReply Event");
     
        ArrayList<ArrayList<String>> mfmatrix = new ArrayList();
        for (int i = 0; i < dbreply.getVnfIdList().size(); i++) {
            
            if (dbreply.getVnfIdList().get(i).equals("")) {
                ArrayList<String> mfmatrixel = new ArrayList();
                mfmatrix.add(mfmatrixel);
            } else {
                ArrayList<String> mfmatrixel = new ArrayList();
                try {
                    java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select mfid from mfs where nfviPopId=? and vnfid=?");
                    ps.setLong(1, dbreply.getPoplist().get(i));
                    ps.setString(2, dbreply.getVnfIdList().get(i));
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        mfmatrixel.add(rs.getString("mfid"));
                        System.out.println("ResourceSelectionLogic.handle_ComputeTerminateDBQueryReply ---> "
                                + "mfid: " + rs.getString("mfid") + "");
                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ResourceSelectionLogic.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
                //END - Retrieve
                
                mfmatrix.add(mfmatrixel);
            }
        }
        
        
        
        ComputeTerminateElem compTermElem = comptermmap.get(dbreply.getReqid());
        E2EComputeTerminateInstance allinst = new E2EComputeTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getDomlist(), dbreply.getPoplist(), 
                compTermElem, dbreply.getVmIdList(), mfmatrix);

        System.out.println("ResourceSelectionLogic --> Post E2EComputeTerminateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2EComputeTerminateInstanceOutcome(E2EComputeTerminateInstanceOutcome allinstout) {
       System.out.println("ResourceSelectionLogic --> Handle E2EComputeTerminateInstanceOutcome Event");
        
       E2EComputeTerminateReply allrepout = new E2EComputeTerminateReply(allinstout.getReqid(),
               allinstout.getServid(), allinstout.getComputeIdList());

       System.out.println("ResourceSelectionLogic --> Post E2EComputeTerminateReply Event");
       SingletonEventBus.getBus().post(allrepout);
    }
    
    @Subscribe
    public void handle_E2EPhysicalTerminateRequest(E2EPhysicalTerminateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2EPhysicalTerminateRequest Event");
        //insert element in hasmap of pening allocate req
        pnftermreqmap.put(allreqev.getReqid(), allreqev.getPnfreq());

        //Prepare and perform the query
//        ComputeTerminateDBQuery dbev = new ComputeTerminateDBQuery(allreqev.getReqid(),
//                allreqev.getServid(), allreqev.getComputereq());
//        System.out.println("ResourceSelectionLogic --> Post ComputeTerminateDBQuery Event");      
//        SingletonEventBus.getBus().post(dbev);
          
          PhysicalTerminateDBQuery dbev = new PhysicalTerminateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getPnfreq());
        //post event
        System.out.println("ResourceSelectionLogic --> Post PhysicalTerminateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_PhysicalTerminateDBQueryReply(PhysicalTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle PhysicalTerminateDBQueryReply Event");

        PNFReply pnfreq = pnftermreqmap.get(dbreply.getReqid());
        List<String> mflist = new ArrayList();
        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select mfid from mfs where nfviPopId=? and pnfid=?");
            ps.setLong(1, dbreply.getPopid());
            ps.setString(2, dbreply.getPnfid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mflist.add(rs.getString("mfid"));
                System.out.println("ResourceSelectionLogic.handle_ComputeTerminateDBQueryReply ---> "
                        + "mfid: " + rs.getString("mfid") + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        E2EPhysicalTerminateInstance allinst = new E2EPhysicalTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getDomid(), dbreply.getPopid(), dbreply.getAbstractpopid(),
                pnfreq, dbreply.getVmid(), mflist);

        System.out.println("ResourceSelectionLogic --> Post E2EComputeTerminateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2EPhysicalTerminateInstanceOutcome(E2EPhysicalTerminateInstanceOutcome allinstout) {
       System.out.println("ResourceSelectionLogic --> Handle E2EPhysicalTerminateInstanceOutcome Event");
        
       E2EPhysicalTerminateReply allrepout = new E2EPhysicalTerminateReply(allinstout.getReqid(),
               allinstout.getServid(), allinstout.getPnfresp());

       System.out.println("ResourceSelectionLogic --> Post E2EComputeTerminateReply Event");
       SingletonEventBus.getBus().post(allrepout);
    }

    @Subscribe
    public void handle_E2ENetworkTerminateRequest(E2ENetworkTerminateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkTerminateRequest Event");
        //insert element in hasmap of pening allocate req
        NetworkTerminateElem compel = new NetworkTerminateElem();
        compel.setTerminateRequest(allreqev.getNetServIdList());
        nettermmap.put(allreqev.getReqid(), compel);
        //Prepare and perform the query
        NetworkTerminateDBQuery dbev = new NetworkTerminateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getNetServIdList());
        //post event
        System.out.println("ResourceSelectionLogic --> Post NetworkTerminateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }

    @Subscribe
    public void handle_NetworkTerminateDBQueryReply(NetworkTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle NetworkTerminateDBQueryReply Event");
        E2ENetworkTerminateInstance allinst = new E2ENetworkTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getWimdomlistMap(), dbreply.getVimdomlistMap(), dbreply.getInterdomainLinksMap(),
                dbreply.getIntraPopLinksMap(), dbreply.getWanLinksMap(), dbreply.getWimPopListMap(), dbreply.getVimPopListMap(),
                dbreply.getWimNetworkTypeMap(), dbreply.getVimNetworkTypeMap(), dbreply.getWanResourceIdListMap(),
                dbreply.getNetServIdList(), dbreply.getLogicalPathList());

        System.out.println("ResourceSelectionLogic --> Post E2ENetworkTerminateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }

    @Subscribe
    public void handle_E2ENetworkTerminateInstanceOutcome(E2ENetworkTerminateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkTerminateInstanceOutcome Event");
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1, ll_availablebw = -1;
        //retrieve from abstract DB the logical link id from the logical paths,
        for (int i = 0; i < allinstout.getLogicalPathList().size(); i++) {
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId  FROM logicalpath where logicalPathId=?");
                ps.setLong(1, Long.valueOf(allinstout.getLogicalPathList().get(i)));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {
                    logicallink = rs.getLong("logicalLinkId");
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> logicallink: " + logicallink + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //retrieve from abstract DB the available bw from logicallink
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicallink where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {
                    ll_availablebw = rs.getLong("availableBandwidth");
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> ll_availablebw: " + ll_availablebw + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //get the maximum available bw from logical path associated to the same logicallink id        
            try {
                java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM logicalpath where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {

                    if (availablebw == -1) {
                        //first element is selected
                        availablebw = rs.getFloat("availableBandwidth");
                    } else {
                        //check if available bw is greater and select it
                        float tmp_availablebw = rs.getFloat("availableBandwidth");
                        if (tmp_availablebw > availablebw) {
                            //select this as candidate
                            availablebw = tmp_availablebw;
                        }
                    }
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> availablebw: " + availablebw + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //check if aavailable bw is the same as represented in the logical link. 
            //If not change the bandwidth capacity values in logical link in DB 
            if (availablebw != ll_availablebw) {
                //change bandwidth capacity in logical link
                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long totalBandwidth;

                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();

                 

                    PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "logicalLinkId, "
                        + "availableBandwidth,"
                        + "totalBandwidth,"
                        + "allocatedBandwidth "
                        + "FROM logicallink WHERE logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    
                    
                    
                    
                    ps.setLong(1, logicallink);
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableBandwidth");
                        totalBandwidth = rs.getLong("totalBandwidth");
                        allocatedBandwidth = rs.getLong("allocatedBandwidth");
                        rs.updateString("availableBandwidth", Float.toString(availablebw));
                        rs.updateString("allocatedBandwidth", Float.toString(totalBandwidth - availablebw));
                        rs.updateRow();

                        System.out.println("handle_E2ENetworkAllocateRequest ---> Bandwidth updated in Abstract logical link table!");
                        System.out.println("handle_E2ENetworkAllocateRequest ---> new available bandwidth " + availablebw + "");

                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ResourceSelectionLogic.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }

        E2ENetworkTerminateReply allrepout = new E2ENetworkTerminateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getNetServIdList());
        System.out.println("ResourceSelectionLogic --> Post E2ENetworkTerminateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }
    
    
    @Subscribe
    public void handle_Fed_E2ENetworkAllocateRequest(Fed_E2ENetworkAllocateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle Fed_E2ENetworkAllocateRequest Event");
      

        //insert element in hasmap of pending allocate req
        Fed_NetworkAllocateElem netel = new Fed_NetworkAllocateElem();
        netel.setAllocateRequest(allreqev);
        fed_netallmap.put(allreqev.getReqid(), netel);
        
        
        for (int i = 0; i < allreqev.getNetworkreq().getLogicalLinkPathList().size(); i++) {
  long physicalpath = -1, logicallink = -1;
        float availablebw = -1;
        String fed_vlanid=null;
            //Get the logicalLinkId from dbquery
            logicallink = Long.parseLong(allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getLogicalLinkId());
            allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqBandwidth();
            System.out.println("handle_E2ENetworkAllocateRequest ----> LogicalLinkId:" + logicallink + "");
//            if (pacompstatus == true) {
//                //Prepare and perform the query
//                PANetworkRequest panetreq = new PANetworkRequest(allreqev.getReqid(),
//                        allreqev.getServid(), allreqev.getNetworkreq(), logicallink, 
//                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqBandwidth(),
//                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getReqLatency(),
//                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getSrcGwIpAddress(),
//                        allreqev.getNetworkreq().getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getDstGwIpAddress());
//                //post event
//                System.out.println("ResourceSelectionLogic --> Post PANetworkRequest Event");
//                SingletonEventBus.getBus().post(panetreq);
//                continue;
//            }        
            //rerieve from DB logical paths related to the logicalLinkId 
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalPathId,availableBandwidth  FROM fed_logicalpath where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with minimum available bandwidth
                while (rs.next()) {
                    if (availablebw == -1) {
                        //first element is selected
                        physicalpath = rs.getLong("logicalPathId");
                        availablebw = rs.getFloat("availableBandwidth");
                    } else {
                        //check if tmp_available bw is bigger and select it
                        float tmp_availablebw = rs.getFloat("availableBandwidth");
                        //TODO: insert control on bw request when IFA005 is fixed
                        //if ( /*(tmp_availablebw >= allreqev.getE2EWIMElem().) &&*/ 
                        //        (tmp_availablebw > availablebw)) {
                        if (tmp_availablebw > availablebw) {
                            //select this as candidate
                            physicalpath = rs.getLong("logicalPathId");
                            availablebw = tmp_availablebw;
                        }
                    }
                }
                System.out.println("handle_Fed_E2ENetworkAllocateRequest ---> physicalpath: " + physicalpath + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            
            //Get from request the vlan related to the current logicalLink
              int size = allreqev.getNetworkreq().getMetaData().size();
            
                   
            for (int j = 0; j < size; j++) {
                if (allreqev.getNetworkreq().getMetaData().get(j).getKey().compareTo("vlanId") == 0) {
                    fed_vlanid = allreqev.getNetworkreq().getMetaData().get(j).getValue();
                    break;
                }
            }
            if (fed_vlanid == null) {
                //no vlan federated is passed select one from the pool
                fed_vlanid = String.valueOf(fedvlancounter);
                fedvlancounter++;
            }
            
            
           
           InterNfviPopConnectivityRequest currentReq = new InterNfviPopConnectivityRequest();
          
           
           
                 LogicalLinkPathListInner innerAttributes = new LogicalLinkPathListInner();
                 innerAttributes=allreqev.getNetworkreq().getLogicalLinkPathList().get(i);
           LogicalLinkPathList currentPath = new LogicalLinkPathList();
           currentPath.add(innerAttributes);
                 
                 
           
           currentReq.setInterNfviPopNetworkType(allreqev.getNetworkreq().getInterNfviPopNetworkType());
           currentReq.setLogicalLinkPathList(currentPath);
           currentReq.setMetaData(allreqev.getNetworkreq().getMetaData());
           currentReq.setNetworkLayer(allreqev.getNetworkreq().getNetworkLayer());
  
//            Fed_NetworkAllocateDBQuery dbev = new Fed_NetworkAllocateDBQuery(allreqev.getReqid(),
//                    allreqev.getServid(), allreqev.getNetworkreq(), physicalpath, fed_vlanid);

 Fed_NetworkAllocateDBQuery dbev = new Fed_NetworkAllocateDBQuery(allreqev.getReqid(),
                    allreqev.getServid(), currentReq, physicalpath, fed_vlanid);


            //post event
            System.out.println("ResourceSelectionLogic --> Post NetworkAllocateDBQuery Event");
            SingletonEventBus.getBus().post(dbev);
        }
    }
    
     @Subscribe
    public void handle_Fed_NetworkAllocateDBQueryReply(Fed_NetworkAllocateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle NetworkAllocateDBQueryReply Event");

        Fed_E2ENetworkAllocateInstance allinst = new Fed_E2ENetworkAllocateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getLogicalLPathId(), dbreply.getWimdomlist(), 
                dbreply.getVimdomlist(), dbreply.getInterdomainLinks(), dbreply.getIntraPopLinks(),
                dbreply.getWanLinks(), dbreply.getWimPopList(), dbreply.getVimPopList(), 
                fed_netallmap.get(dbreply.getReqid()).getAllocateRequest().getNetworkreq(),
                dbreply.getWimNetworkType(), dbreply.getVimNetworkType(), dbreply.getFlowRuleEndPointList(), dbreply.isAbstrSrcNfviPopOfLogicalPathIsFed());

        System.out.println("ResourceSelectionLogic --> Post E2ENetworkAllocateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }
    
    
        
 @Subscribe
    public void handle_Fed_E2ENetworkAllocateInstanceOutcome(Fed_E2ENetworkAllocateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkAllocateInstanceOutcome Event");
        
        if (allinstout.isOutcome() == false ) {
            //send negative response
            Fed_E2ENetworkAllocateReply allrepout = new Fed_E2ENetworkAllocateReply(allinstout.getReqid(),
                    allinstout.getServid(), null, allinstout.isOutcome());
            System.out.println("ResourceSelectionLogic --> Post Fed_E2ENetworkAllocateReply Event");
            SingletonEventBus.getBus().post(allrepout);
            return;
        }
        
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1, ll_availablebw = -1;
        //retrieve from federation DB the logical link id from the logical paths, 
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId  FROM fed_logicalpath where logicalPathId=?");
            ps.setLong(1, allinstout.getLogicalpath());
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                logicallink = rs.getLong("logicalLinkId");  
            }
            System.out.println("handle_Fed_E2ENetworkAllocateRequest ---> logicallink: " + logicallink + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //retrieve from federation DB the available bw from logicallink
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM fed_logicallink where logicalLinkId=?");
            ps.setLong(1, logicallink);
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                ll_availablebw = rs.getLong("availableBandwidth");
            }
            System.out.println("handle_Fed_E2ENetworkAllocateRequest ---> ll_availablebw: " + ll_availablebw + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //get the maximum available bw from logical paths associated to the same logicallink id        
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM fed_logicalpath where logicalLinkId=?");
            ps.setLong(1, logicallink);
            java.sql.ResultSet rs = ps.executeQuery();
            //Select the path with maximum available bandwidth
            while (rs.next()) {
                
                if (availablebw == -1) {
                    //first element is selected
                    availablebw = rs.getFloat("availableBandwidth");
                } else {
                    //check if available bw is greater and select it
                    float tmp_availablebw = rs.getFloat("availableBandwidth");
                    if (tmp_availablebw > availablebw) {
                        //select this as candidate
                        availablebw = tmp_availablebw;
                    }
                }   
            }
            System.out.println("handle_Fed_E2ENetworkAllocateRequest ---> availablebw: " + availablebw + "");
            
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(ResourceSelectionLogic.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        //check if aavailable bw is the same as represented in the logical link. 
        //If not change the bandwidth capacity values in logical link in DB 
        if (availablebw != ll_availablebw) {
            //change bandwidth capacity in logical link
            try {
                long availableBandwidth;
                long allocatedBandwidth;
                long totalBandwidth;

                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "logicalLinkId, "
                        + "availableBandwidth,"
                        + "totalBandwidth,"
                        + "allocatedBandwidth "
                        + "FROM fed_logicallink WHERE logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    availableBandwidth = rs.getLong("availableBandwidth");
                    totalBandwidth = rs.getLong("totalBandwidth");
                    allocatedBandwidth = rs.getLong("allocatedBandwidth");
                    rs.updateString("availableBandwidth", Float.toString(availablebw));
                    rs.updateString("allocatedBandwidth", Float.toString(totalBandwidth-availablebw));
                    rs.updateRow();

                    System.out.println("handle_Fed_E2ENetworkAllocateRequest ---> Bandwidth updated in Abstract logical link table!");
                    System.out.println("handle_Fed_E2ENetworkAllocateRequest ---> new available bandwidth " + availablebw + "");
                    
                }

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } 
        
        Fed_E2ENetworkAllocateReply allrepout = new Fed_E2ENetworkAllocateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getVn(), allinstout.isOutcome());
        System.out.println("ResourceSelectionLogic --> Post Fed_E2ENetworkAllocateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    } 
    
 @Subscribe
    public void handle_Fed_E2ENetworkTerminateRequest(Fed_E2ENetworkTerminateRequest allreqev) {
        System.out.println("ResourceSelectionLogic --> Handle Fed_E2ENetworkTerminateRequest Event");
        //insert element in hasmap of pening allocate req
        Fed_NetworkTerminateElem compel = new Fed_NetworkTerminateElem();
        compel.setTerminateRequest(allreqev.getNetServIdList());
        fed_nettermmap.put(allreqev.getReqid(), compel);
        //Prepare and perform the query
        Fed_NetworkTerminateDBQuery dbev = new Fed_NetworkTerminateDBQuery(allreqev.getReqid(),
                allreqev.getServid(), allreqev.getNetServIdList());
        //post event
        System.out.println("ResourceSelectionLogic --> Post Fed_NetworkTerminateDBQuery Event");
        SingletonEventBus.getBus().post(dbev);
    }
    
 @Subscribe
    public void handle_Fed_NetworkTerminateDBQueryReply(Fed_NetworkTerminateDBQueryReply dbreply) {
        System.out.println("ResourceSelectionLogic --> Handle NetworkTerminateDBQueryReply Event");
        Fed_E2ENetworkTerminateInstance allinst = new Fed_E2ENetworkTerminateInstance(dbreply.getReqid(),
                dbreply.getServid(), dbreply.getWimdomlistMap(), dbreply.getVimdomlistMap(), dbreply.getInterdomainLinksMap(),
                dbreply.getFed_interdomainLinksMap(), dbreply.getIntraPopLinksMap(), dbreply.getWanLinksMap(), 
                dbreply.getWimPopListMap(), dbreply.getVimPopListMap(), dbreply.getWimNetworkTypeMap(), 
                dbreply.getVimNetworkTypeMap(), dbreply.getWanResourceIdListMap(),
                dbreply.getNetServIdList(), dbreply.getLogicalPathList());

        System.out.println("ResourceSelectionLogic --> Post E2ENetworkTerminateInstance Event");
        SingletonEventBus.getBus().post(allinst);

    }
    
    
    
    
    
    
    @Subscribe
    public void handle_Fed_E2ENetworkTerminateInstanceOutcome(Fed_E2ENetworkTerminateInstanceOutcome allinstout) {
        System.out.println("ResourceSelectionLogic --> Handle E2ENetworkTerminateInstanceOutcome Event");
        long physicalpath = -1, logicallink = -1;
        float availablebw = -1, ll_availablebw = -1;
        //retrieve from abstract DB the logical link id from the logical paths,
        for (int i = 0; i < allinstout.getLogicalPathList().size(); i++) {
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId  FROM fed_logicalpath where logicalPathId=?");
                ps.setLong(1, Long.valueOf(allinstout.getLogicalPathList().get(i)));
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {
                    logicallink = rs.getLong("logicalLinkId");
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> logicallink: " + logicallink + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //retrieve from abstract DB the available bw from logicallink
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM fed_logicallink where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {
                    ll_availablebw = rs.getLong("availableBandwidth");
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> ll_availablebw: " + ll_availablebw + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //get the maximum available bw from logical path associated to the same logicallink id        
            try {
                java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT availableBandwidth  FROM fed_logicalpath where logicalLinkId=?");
                ps.setLong(1, logicallink);
                java.sql.ResultSet rs = ps.executeQuery();
                //Select the path with maximum available bandwidth
                while (rs.next()) {

                    if (availablebw == -1) {
                        //first element is selected
                        availablebw = rs.getFloat("availableBandwidth");
                    } else {
                        //check if available bw is greater and select it
                        float tmp_availablebw = rs.getFloat("availableBandwidth");
                        if (tmp_availablebw > availablebw) {
                            //select this as candidate
                            availablebw = tmp_availablebw;
                        }
                    }
                }
                System.out.println("handle_E2ENetworkAllocateRequest ---> availablebw: " + availablebw + "");

                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(ResourceSelectionLogic.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            //check if aavailable bw is the same as represented in the logical link. 
            //If not change the bandwidth capacity values in logical link in DB 
            if (availablebw != ll_availablebw) {
                //change bandwidth capacity in logical link
                try {
                    long availableBandwidth;
                    long allocatedBandwidth;
                    long totalBandwidth;

                    java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();

                 

                    PreparedStatement ps = conn.prepareStatement("SELECT "
                        + "logicalLinkId, "
                        + "availableBandwidth,"
                        + "totalBandwidth,"
                        + "allocatedBandwidth "
                        + "FROM fed_logicallink WHERE logicalLinkId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    
                    
                    
                    
                    ps.setLong(1, logicallink);
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        availableBandwidth = rs.getLong("availableBandwidth");
                        totalBandwidth = rs.getLong("totalBandwidth");
                        allocatedBandwidth = rs.getLong("allocatedBandwidth");
                        rs.updateString("availableBandwidth", Float.toString(availablebw));
                        rs.updateString("allocatedBandwidth", Float.toString(totalBandwidth - availablebw));
                        rs.updateRow();

                        System.out.println("handle_E2ENetworkAllocateRequest ---> Bandwidth updated in Abstract logical link table!");
                        System.out.println("handle_E2ENetworkAllocateRequest ---> new available bandwidth " + availablebw + "");

                    }

                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(ResourceSelectionLogic.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }

        Fed_E2ENetworkTerminateReply allrepout = new Fed_E2ENetworkTerminateReply(allinstout.getReqid(),
                allinstout.getServid(), allinstout.getNetServIdList());
        System.out.println("ResourceSelectionLogic --> Post E2ENetworkTerminateReply Event");
        SingletonEventBus.getBus().post(allrepout);
    }
    
    
}
