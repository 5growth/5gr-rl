/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.extinterface.sbi;

import com.rl.events.resourcemanagement.VirtualQueueAssignment.QosPerformanceIsolationReq;
import com.rl.extinterface.sbi.IFA005Threads.QueryWIMThread;
import com.rl.extinterface.sbi.IFA005Threads.QueryMECThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateVIMComputeThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateVIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateVIMComputeThread;
import com.rl.extinterface.sbi.IFA005Threads.QueryVIMThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateWIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateWIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.QueryRadioThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateVIMNetworkThread;
import com.google.common.eventbus.Subscribe;
import com.rl.DbConnectionPool.DbDomainDatasource;
import com.rl.SingletonEventBus;
import com.rl.common.objects.DomainElem;
import com.rl.common.objects.NetworkEndpoints;
import com.rl.events.abstraction.Creation.DomainSubscriber;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateMECReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateMECReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateVIMReply;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeAllocateReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateVIMReply;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateMECReq;
import com.rl.events.resourcemanagement.ComputeTermination.ComputeTerminateReq;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_NetworkAllocateWIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateVIMRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateVIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReply;
import com.rl.events.resourcemanagement.NetworkAllocation.NetworkAllocateWIMReq;
import com.rl.events.resourcemanagement.NetworkAllocation.VIMVLANRequest;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateWIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_NetworkTerminateWIMReq;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateVIMRequest;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateVIMReq;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReply;
import com.rl.events.resourcemanagement.NetworkTermination.NetworkTerminateWIMReq;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateReq;
import com.rl.events.resourcemanagement.PhysicalAllocation.PhysicalAllocateVIMReply;
import com.rl.events.resourcemanagement.PhysicalTermination.PhysicalTerminateReq;
import com.rl.extinterface.sbi.IFA005Threads.AllocateIntraPopVIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateMECThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateRadioComputeThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateRadioPhysicalThread;
import com.rl.extinterface.sbi.IFA005Threads.AllocateVIMPhysicalThread;
import com.rl.extinterface.sbi.IFA005Threads.Fed_AllocateWIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.Fed_TerminateWIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.OperateVIMComputeThread;
import com.rl.extinterface.sbi.IFA005Threads.RetrieveVLAN;
import com.rl.extinterface.sbi.IFA005Threads.TerminateIntraPoPVIMNetworkThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateMECThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateRadioComputeThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateRadioPhysicalThread;
import com.rl.extinterface.sbi.IFA005Threads.TerminateVIMPhysicalThread;
import com.rl.extinterface.sbi.StubThreads.AllocateIntraPopVIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.AllocateMECThreadStub;
import com.rl.extinterface.sbi.StubThreads.AllocateVIMComputeStub;
import com.rl.extinterface.sbi.StubThreads.AllocateVIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.AllocateWIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.Fed_AllocateWIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.Fed_TerminateWIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.QueryMECStub;
import com.rl.extinterface.sbi.StubThreads.QueryRadioStub;
import com.rl.extinterface.sbi.StubThreads.QueryVIMStub;
import com.rl.extinterface.sbi.StubThreads.QueryWIMStub;
import com.rl.extinterface.sbi.StubThreads.TerminateIntraPoPVIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.TerminateMECThreadStub;
import com.rl.extinterface.sbi.StubThreads.TerminateVIMComputeStub;
import com.rl.extinterface.sbi.StubThreads.TerminateVIMNetworkStub;
import com.rl.extinterface.sbi.StubThreads.TerminateWIMNetworkStub;
import com.rl.extinterface.sbi.P4SlicerThreads.AssignQosQueueThread;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class SBIIF {

    //private HashMap<Long, DomainElem> dommap;
    private boolean stubmode;

    public SBIIF() {
        //dommap = new HashMap();
        stubmode = false;
    }

    public SBIIF(boolean stuben) {
        //dommap = new HashMap();
        stubmode = stuben;
    }

    public void setStubFlag(boolean val) {
        stubmode = val;
    }

    public boolean getStubFlag() {
        return stubmode;
    }
 
   
    @Subscribe
    public void handle_DomainSubscribeList(DomainSubscriber domlist) {
        System.out.println("SBIIF --> handle domainsubscriberEvent");
        DomainElem el;
        long portdom = 0;
        while (portdom != -1) {
            el = domlist.getDomainElem();
            System.out.println("DomainElem fields: type= " + el.getType()
                    + " ip= " + el.getIp() + " port= " + el.getPort() + " name= "
                    + el.getName() + " id= " + el.getId());

            /*
             * open HTTP connection towards domains and send IFA005 Query
             * and send IFA005 query to retrieve abstraction data
             */
            if (el.getType().equals("T-WIM")) {
                System.out.println("SBIIF --> Call WIM Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryWIMStub thr = new QueryWIMStub(el);
                    thr.start();
                } else {
                    QueryWIMThread thr = new QueryWIMThread(el);
                    thr.start();
                }

                //WIMResAbstractionEvent wabstrev = new WIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(wabstrev);
            } else if (el.getType().equals("VIM")) {
                System.out.println("SBIIF --> Call VIM Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryVIMStub thr = new QueryVIMStub(el);
                    thr.start();
                } else {
                    QueryVIMThread thr = new QueryVIMThread(el);
                    thr.start();
                }
                //VIMResAbstractionEvent vabstrev = new VIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(vabstrev);                
            } else if (el.getType().equals("MEC")) {
                System.out.println("SBIIF --> Call MEC Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryMECStub thr = new QueryMECStub(el);
                    thr.start();
                } else {
                    QueryMECThread thr = new QueryMECThread(el);
                    thr.start();
                }
                //VIMResAbstractionEvent vabstrev = new VIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(vabstrev);                
            } else if (el.getType().equals("RADIO")) {
                System.out.println("SBIIF --> Call Radio Query Thread: type= " + el.getType());
                if (stubmode) {
                    QueryRadioStub thr = new QueryRadioStub(el);
                    thr.start();
                } else {
                    QueryRadioThread thr = new QueryRadioThread(el);
                    thr.start();
                }
                //VIMResAbstractionEvent vabstrev = new VIMResAbstractionEvent(el.getId());
                //SingletonEventBus.getBus().post(vabstrev);                
            } else {
                System.out.println("SBIIF --> Unknown Domain: type= " + el.getType());
            }
            //dommap.put(el.getId(), el);
            portdom = el.getPort();

            // 
        }

    }

    
    @Subscribe
    public void handle_ComputeAllocateReq(ComputeAllocateReq allvimreq) {
        System.out.println("SBIIF --> Handle ComputeAllocateVIMReq Event");

        //Retrieve Domain el from domid using domainlist.xml
        //DomainElem el = dommap.get(allvimreq.getDomId());
        //START - Retrieve Domain el from domid using database connection
        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select domId,name,type,ip,port from domain where domId=?");
            ps.setLong(1, allvimreq.getDomid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = rs.getLong("domId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

        // END - Retrieve
        if (el == null) {
            ComputeAllocateVIMReply allvimrep = new ComputeAllocateVIMReply(allvimreq.getReqid(),
                    allvimreq.getServid(), allvimreq.getDomid(),
                    false, 0, "", null, allvimreq.getNfvipopid(), allvimreq.getComputereq());
            allvimrep.setErrorcode(-1);
            allvimrep.setErrormsg("Domid= " + allvimreq.getDomid() + " not present");

            SingletonEventBus.getBus().post(allvimrep);
        } else {
            if (stubmode) {
                AllocateVIMComputeStub thr = new AllocateVIMComputeStub(el, allvimreq);
                thr.start();
            } else {
                if (typeval.equals("RADIO")) {
                    AllocateRadioComputeThread thr = new AllocateRadioComputeThread(el, allvimreq);
                    thr.start(); 
                } else {
                    AllocateVIMComputeThread thr = new AllocateVIMComputeThread(el, allvimreq);
                    thr.start(); 
                }
                
            }
        }
  
        //R0.1 Just trigger the outcome (loopback)  
//        ComputeAllocateVIMReply allvimrep = new ComputeAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }
    
 @Subscribe
    public void handle_PhysicalAllocateReq(PhysicalAllocateReq allvimreq) {
        System.out.println("SBIIF --> Handle PhysicalAllocateReq Event");

        //Retrieve Domain el from domid using domainlist.xml
        //DomainElem el = dommap.get(allvimreq.getDomId());
        //START - Retrieve Domain el from domid using database connection
        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("select domId,name,type,ip,port from domain where domId=?");
            ps.setLong(1, allvimreq.getDomid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = rs.getLong("domId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

        // END - Retrieve
        if (el == null) {
            PhysicalAllocateVIMReply allvimrep = new PhysicalAllocateVIMReply(allvimreq.getReqid(),
                    allvimreq.getServid(), allvimreq.getDomid(),
                    false, 0, "", null, allvimreq.getNfvipopid(), allvimreq.getPnfreqreq());
            allvimrep.setErrorcode(-1);
            allvimrep.setErrormsg("Domid= " + allvimreq.getDomid() + " not present");

            SingletonEventBus.getBus().post(allvimrep);
        } else {
            if (stubmode) {
                //nothing to do
            } else {
                if (typeval.equals("RADIO")) {
                    AllocateRadioPhysicalThread thr = new AllocateRadioPhysicalThread(el, allvimreq);
                    thr.start(); 
                } else {
                    AllocateVIMPhysicalThread thr = new AllocateVIMPhysicalThread(el, allvimreq);
                    thr.start(); 
                }
                
            }
        }
  
        //R0.1 Just trigger the outcome (loopback)  
//        ComputeAllocateVIMReply allvimrep = new ComputeAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }   
    
      
    
   @Subscribe
    public void handle_ComputeOperateVIMReq(ComputeOperateReq opcompreq) {
        System.out.println("SBIIF --> Handle ComputeoperateVIMReq Event");
        
        //TODO R2: Call VIM Change operate thread

        //START - Retrieve Domain el from domid using database connection
        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
            ps.setLong(1, opcompreq.getDomid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = rs.getLong("domId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
       // END - Retrieve
        if (el == null) {
            ComputeOperateVIMReply opvimrep = new ComputeOperateVIMReply(opcompreq.getReqid(),
                    null, 0, null);
            opvimrep.setErrcode(-1);
            opvimrep.setErrmsg("Domid= " + opcompreq.getDomid() + " not present");

            SingletonEventBus.getBus().post(opvimrep);
        } else {
                OperateVIMComputeThread thr = new OperateVIMComputeThread(el, opcompreq);
                thr.start();
        }
    }
    
  @Subscribe
    public void handle_ComputeAllocateMECReq(ComputeAllocateMECReq allmecreq) {
        System.out.println("SBIIF --> Handle ComputeAllocateVIMReq Event");
        

        //Retrieve Domain el from domid using domainlist.xml
        //DomainElem el = dommap.get(allvimreq.getDomId());
        //START - Retrieve Domain el from domid using database connection
        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
            ps.setLong(1, allmecreq.getMecdomid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = rs.getLong("domId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

        // END - Retrieve
        if (el == null) {
            ComputeAllocateMECReply allmecrep = new ComputeAllocateMECReply(allmecreq.getReqid(),allmecreq.getServid(),
                    allmecreq.getMecdomid(),"", allmecreq.getVmreq());


            SingletonEventBus.getBus().post(allmecrep);
        } else {
            if (stubmode) {
                AllocateMECThreadStub thr = new AllocateMECThreadStub(el, allmecreq);
                thr.start();
            } else {
                AllocateMECThread thr = new AllocateMECThread(el, allmecreq);
                thr.start();
            }
        }
  
        //R0.1 Just trigger the outcome (loopback)  
//        ComputeAllocateVIMReply allvimrep = new ComputeAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }
    
    @Subscribe
    public void handle_IntraPoPAllocateVIMRequest(IntraPoPAllocateVIMRequest allvimreq) {
        System.out.println("SBIIF --> Handle IntraPoPAllocateVIMRequest Event");

        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
            ps.setLong(1, allvimreq.getVimid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = allvimreq.getVimid();

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

        if (stubmode) {
            AllocateIntraPopVIMNetworkStub thr = new AllocateIntraPopVIMNetworkStub(el, allvimreq);
            thr.start();
        } else {
            AllocateIntraPopVIMNetworkThread thr = new AllocateIntraPopVIMNetworkThread(el, allvimreq);
            thr.start();
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkAllocateVIMReply allvimrep = new NetworkAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_NetworkAllocateVIMReq(NetworkAllocateVIMReq allvimreq) {
        System.out.println("SBIIF --> Handle NetworkAllocateVIMReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(allvimreq.getDomId());
                //retrieve Domain el from domid
        //DomainElem el = dommap.get(allwimreq.getDomId());
        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = allvimreq.getVimdomlist();
        long size = domList.size();

        for (int i = 0; i < size; i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, allvimreq.getVimdomlist().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = allvimreq.getVimdomlist().get(i);

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        }
        
        if (domElemList == null) {
            NetworkAllocateVIMReply allvimrep = new NetworkAllocateVIMReply(allvimreq.getReqId(),
                    allvimreq.getServId(), false, 0, null, null, -1);
            allvimrep.setErrorcode(-1);
            allvimrep.setErrormsg("A VIM DomId is not present during the allocation of LogicalLinkId: " + allvimreq.getLogicalPathId() + "");
            SingletonEventBus.getBus().post(allvimrep);
        } else {
            if (stubmode) {
                AllocateVIMNetworkStub thr = new AllocateVIMNetworkStub(domElemList, allvimreq);
                thr.start();
            } else {
                AllocateVIMNetworkThread thr = new AllocateVIMNetworkThread(domElemList, allvimreq);
                thr.start();
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkAllocateVIMReply allvimrep = new NetworkAllocateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkAllocateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_VIMVLANRequest(VIMVLANRequest vlanreq) {
        System.out.println("SBIIF --> Handle VIMVLANReques Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(allvimreq.getDomId());
                //retrieve Domain el from domid
        //DomainElem el = dommap.get(allwimreq.getDomId());
        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = vlanreq.getVimdomlist();
        long size = domList.size();

        for (int i = 0; i < size; i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, vlanreq.getVimdomlist().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = vlanreq.getVimdomlist().get(i);

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        }
        
        if (domElemList == null) {
            System.out.println("SBIIF --> Error, no domain selected for vlan");
            return;
        } else {
            if (stubmode) {
            } else {
                RetrieveVLAN thr = new RetrieveVLAN(domElemList, vlanreq);
                thr.start();
            }
        }
    }
    
    @Subscribe
    public void handle_NetworkAllocateWIMReq(NetworkAllocateWIMReq allwimreq) throws InterruptedException {
        System.out.println("SBIIF --> Handle NetworkAllocateWIMReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(allwimreq.getDomId());
        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = allwimreq.getWimdomlist();
        long size = domList.size();

        for (int i = 0; i < size; i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, allwimreq.getWimdomlist().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = allwimreq.getWimdomlist().get(i);

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        }

//        if (el == null) {
        if (domElemList == null) {
            NetworkAllocateWIMReply allwimrep = new NetworkAllocateWIMReply(allwimreq.getReqid(),
                    allwimreq.getServid(), false, 0, null, null, -1);
           
            
            allwimrep.setErrorcode(-1);
            //allwimrep.setErrorMsg("Domid= " + allwimreq.getDomId() + " not present");
            allwimrep.setErrormsg("A WIM DomId is not present during the allocation of LogicalLinkId: " + allwimreq.getLogicalPathId() + "");
            SingletonEventBus.getBus().post(allwimrep);
        } else {

            if (stubmode) {
                //AllocateWIMNetworkStub thr = new AllocateWIMNetworkStub(el, allwimreq);
                AllocateWIMNetworkStub thr = new AllocateWIMNetworkStub(domElemList, allwimreq);
                thr.start();
            } else {
                List <NetworkEndpoints> endpointlist = new ArrayList();
                //retrieve endpoints for each domain
                for (int i = 0; i < (allwimreq.getInterdomainLinks().size()-1); i++) {//last interdomainlinks take only the egress point 
                    String ingrip =new String();
                    String egrip = new String();
                    String ingrport = new String();
                    String egrport = new String();
                    try {
                        //select src endpoint
                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT dstGwId,dstGwIp  FROM interdomainlink where interDomainLinkId=?");
                        ps.setLong(1, allwimreq.getInterdomainLinks().get(i));
                        java.sql.ResultSet rs = ps.executeQuery();
                        //Select the path with minimum available bandwidth
                        while (rs.next()) {
                            ingrip = rs.getString("dstGwId");
                            ingrport = rs.getString("dstGwIp");
                        }
                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(SBIIF.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }

                    try {
                        //select dst endpoint
                        java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                        PreparedStatement ps = conn.prepareStatement("SELECT srcGwId,srcGwIp  FROM interdomainlink where interDomainLinkId=?");
                        ps.setLong(1, allwimreq.getInterdomainLinks().get(i+1));
                        java.sql.ResultSet rs = ps.executeQuery();
                        //Select the path with minimum available bandwidth
                        while (rs.next()) {
                            egrip = rs.getString("srcGwId");
                            egrport = rs.getString("srcGwIp");
                        }
                        rs.close();
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(SBIIF.class
                                .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    }
                    
                    //create endpoint
                    NetworkEndpoints endp = new NetworkEndpoints(ingrip, egrip, ingrport, egrport);
                    endpointlist.add(endp);
                }

                //AllocateWIMNetworkThread thr = new AllocateWIMNetworkThread(el, allwimreq);
                
                
                AllocateWIMNetworkThread thr = new AllocateWIMNetworkThread(domElemList, allwimreq, endpointlist);
                thr.start();
                Thread.sleep(3000);
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkAllocateWIMReply allwimrep = new NetworkAllocateWIMReply(allwimreq.getReqId(),
//                                                    allwimreq.getServId(), allwimreq.getDomId(),
//                                                    allwimreq.getWIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkAllocateWIMReply Event");
//        SingletonEventBus.getBus().post(allwimrep);
    }

    @Subscribe
    public void handle_ComputeTerminateReq(ComputeTerminateReq termvimreq) {
        System.out.println("SBIIF --> Handle ComputeTerminateReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(termvimreq.getDomId());

        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = termvimreq.getDomlist();
        long size = domList.size();

        // while (!computeIdList.isEmpty()){
        for (int i = 0; i < size; i++) {

            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;
            long domId = domList.get(i);

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
                ps.setLong(1, domId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = rs.getLong("domId");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

            domElemList.add(el);

            // END - Retrieve
        }


        if (stubmode) {
            //TerminateVIMComputeStub thr = new TerminateVIMComputeStub(el, termvimreq);
            TerminateVIMComputeStub thr = new TerminateVIMComputeStub(domElemList, termvimreq);
            thr.start();
        } else {
            //TerminateVIMComputeThread thr = new TerminateVIMComputeThread(el, termvimreq);
            if (domElemList.get(0).getType().equals("RADIO")) {
                TerminateRadioComputeThread thr = new TerminateRadioComputeThread(domElemList, termvimreq);
                thr.start();
            } else {
                TerminateVIMComputeThread thr = new TerminateVIMComputeThread(domElemList, termvimreq);
                thr.start();
            }
            
        }

        //R0.1 Just trigger the outcome (loopback)
//        ComputeTerminateVIMReply allvimrep = new ComputeTerminateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeTerminateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }
    
  @Subscribe
    public void handle_PhysicalTerminateReq(PhysicalTerminateReq termvimreq) {
      System.out.println("SBIIF --> Handle PhysicalTerminateReq Event");
      //retrieve Domain el from domid
      //DomainElem el = dommap.get(termvimreq.getDomId());

      //START - For each domid retrieve from DB the domain info
      String typeval = null;
      String nameval = null;
      String ipval = null;
      Long portval = null;
      Long idval = null;
      long domId = termvimreq.getDomid();

      try {

          java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
          PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
          ps.setLong(1, domId);
          java.sql.ResultSet rs = ps.executeQuery();
          while (rs.next()) {

              typeval = rs.getString("type");
              nameval = rs.getString("name");
              ipval = rs.getString("ip");
              portval = rs.getLong("port");
              idval = rs.getLong("domId");

          }
          rs.close();
          ps.close();

      } catch (SQLException ex) {
          Logger.getLogger(SBIIF.class
                  .getName()).log(Level.SEVERE, ex.getMessage(), ex);
      }

      DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);


        if (stubmode) {
            //nothing to do
        } else {
            //TerminateVIMComputeThread thr = new TerminateVIMComputeThread(el, termvimreq);
            if (typeval.equals("RADIO")) {
                TerminateRadioPhysicalThread thr = new TerminateRadioPhysicalThread(el, termvimreq);
                thr.start();
            } else {
                TerminateVIMPhysicalThread thr = new TerminateVIMPhysicalThread(el, termvimreq);
                thr.start();
            }
            
        }

        //R0.1 Just trigger the outcome (loopback)
//        ComputeTerminateVIMReply allvimrep = new ComputeTerminateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeTerminateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }
    
  @Subscribe
    public void handle_ComputeTerminateMECReq(ComputeTerminateMECReq termvimreq) {
        System.out.println("SBIIF --> Handle ComputeTerminateVIMReq Event");
        
 //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        List<Long> domList = termvimreq.getMecdom();
        long size = domList.size();

        // while (!computeIdList.isEmpty()){
        for (int i = 0; i < size; i++) {

            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;
            long domId = domList.get(i);

            try {

                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
                ps.setLong(1, domId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = rs.getLong("domId");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);

            domElemList.add(el);

            // END - Retrieve
        }


        if (stubmode) {
            //TerminateVIMComputeStub thr = new TerminateVIMComputeStub(el, termvimreq);
            TerminateMECThreadStub thr = new TerminateMECThreadStub(domElemList, termvimreq);
            thr.start();
        } else {
            //TerminateVIMComputeThread thr = new TerminateVIMComputeThread(el, termvimreq);
            TerminateMECThread thr = new TerminateMECThread(domElemList, termvimreq);
            thr.start();
        }

        //R0.1 Just trigger the outcome (loopback)
//        ComputeTerminateVIMReply allvimrep = new ComputeTerminateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post ComputeTerminateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_IntraPoPTerminateVIMRequest(IntraPoPTerminateVIMRequest termvimreq) {
        System.out.println("SBIIF --> Handle IntraPoPTerminateVIMRequest Event");
        
        ArrayList<DomainElem> domElemList = new ArrayList();
        for (int i = 0; i < termvimreq.getDomlist().size(); i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, termvimreq.getDomlist().get(i));

                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = termvimreq.getDomlist().get(i);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);

        } //END - Loop on domaIds

        if (stubmode) {
            TerminateIntraPoPVIMNetworkStub thr = new TerminateIntraPoPVIMNetworkStub(domElemList, termvimreq);
            thr.start();
            
            
        } else {
            TerminateIntraPoPVIMNetworkThread thr = new TerminateIntraPoPVIMNetworkThread(domElemList, termvimreq);
            thr.start();
        }
}

    
    
    
    @Subscribe
    public void handle_NetworkTerminateVIMReq(NetworkTerminateVIMReq termvimreq) {
        System.out.println("SBIIF --> Handle NetworkTerminateVIMReq Event");

        //Retrieve Domain el 
         //DomainElem el = dommap.get(termwimreq.getDomId());
          
        //Retrieve Domain el from domid
        HashMap<Integer,List<DomainElem>> elMap= new HashMap();
         //Loop on logicalLinks       
        for (int j = 0; j < termvimreq.getVimdomlistMap().size(); j++) {            
        ArrayList<Long> domList = termvimreq.getVimdomlistMap().get(j); 
            ArrayList<DomainElem> domElemList = new ArrayList();
        
        
        
        

        //Loop on domainIds
        for (int i = 0; i < termvimreq.getVimdomlistMap().get(j).size(); i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, termvimreq.getVimdomlistMap().get(j).get(i));
                
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = termvimreq.getVimdomlistMap().get(j).get(i);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        } //END - Loop on domaIds
        
        elMap.put(j, domElemList);
        
        }//END - Loop on logicalLinks

        //TO DO - Fix this part inside the if block
        if (elMap == null) {
         
            NetworkTerminateVIMReply termvimrep = new NetworkTerminateVIMReply();
            termvimrep.setReqid(termvimreq.getReqid());
            termvimrep.setServid(termvimreq.getServid());
            SingletonEventBus.getBus().post(termvimrep);
        } else {
            if (stubmode) {
                TerminateVIMNetworkStub thr = new TerminateVIMNetworkStub(elMap, termvimreq);
                thr.start();
            } else {

                
                TerminateVIMNetworkThread thr = new TerminateVIMNetworkThread(elMap, termvimreq);
                thr.start();
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkTerminateVIMReply allvimrep = new NetworkTerminateVIMReply(allvimreq.getReqId(),
//                                                    allvimreq.getServId(), allvimreq.getDomId(),
//                                                    allvimreq.getVIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkTerminateVIMReply Event");
//        SingletonEventBus.getBus().post(allvimrep);
    }

    @Subscribe
    public void handle_NetworkTerminateWIMReq(NetworkTerminateWIMReq termwimreq) {
        System.out.println("SBIIF --> Handle NetworkTerminateWIMReq Event");
        //retrieve Domain el from DB
             //Retrieve Domain el from domid
        HashMap<Integer,List<DomainElem>> elMap= new HashMap();
         //Loop on logicalLinks       
        for (int j = 0; j < termwimreq.getWimdomlistMap().size(); j++) {            
        ArrayList<Long> domList = termwimreq.getWimdomlistMap().get(j); 
            ArrayList<DomainElem> domElemList = new ArrayList();
        
        
        
        

        //Loop on domainIds
        for (int i = 0; i < termwimreq.getWimdomlistMap().get(j).size(); i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, termwimreq.getWimdomlistMap().get(j).get(i));
                
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = termwimreq.getWimdomlistMap().get(j).get(i);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
           

        } //END - Loop on domaIds
        
        elMap.put(j, domElemList);
        
        }//END - Loop on logicalLinks
        
        //TO DO - Fix this part inside the if block
        if (elMap == null) {
//            NetworkTerminateWIMReply termwimrep = new NetworkTerminateWIMReply(termwimreq.getReqId(),
//                    termwimreq.getServId(), termwimreq.getDomId(), false);
//            termwimrep.setErrorCode(-1);
//            termwimrep.setErrorMsg("Domid= " + termwimreq.getDomId() + " not present");

//UNCOMMENT below line after fixing the if block
 NetworkTerminateWIMReply termwimrep = new NetworkTerminateWIMReply();
            SingletonEventBus.getBus().post(termwimrep);
        } else {
            if (stubmode) {
                TerminateWIMNetworkStub thr = new TerminateWIMNetworkStub(elMap, termwimreq);
                thr.start();
            } else {
                TerminateWIMNetworkThread thr = new TerminateWIMNetworkThread(elMap, termwimreq);
                thr.start();
            }
        }
        //R0.1 Just trigger the outcome (loopback)
//        NetworkTerminateWIMReply allwimrep = new NetworkTerminateWIMReply(allwimreq.getReqId(),
//                                                    allwimreq.getServId(), allwimreq.getDomId(),
//                                                    allwimreq.getWIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkTerminateWIMReply Event");
//        SingletonEventBus.getBus().post(allwimrep);
    }

 @Subscribe
    public void handle_Fed_NetworkAllocateWIMReq(Fed_NetworkAllocateWIMReq allwimreq) throws InterruptedException {
        System.out.println("SBIIF --> Handle NetworkAllocateWIMReq Event");
        //retrieve Domain el from domid
        //DomainElem el = dommap.get(allwimreq.getDomId());
        //START - For each domid retrieve from DB the domain info
        ArrayList<DomainElem> domElemList = new ArrayList();
        ArrayList<Long> domList = allwimreq.getWimdomlist();
        long size = domList.size();

        for (int i = 0; i < size; i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, allwimreq.getWimdomlist().get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = allwimreq.getWimdomlist().get(i);

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
            // END - Retrieve

        }

//        if (el == null) {
        if (domElemList == null) {
            Fed_NetworkAllocateWIMReply allwimrep = new Fed_NetworkAllocateWIMReply(allwimreq.getReqid(),
                    allwimreq.getServid(), false, 0, null, null, -1, null);
           
            
            allwimrep.setErrorcode(-1);
            //allwimrep.setErrorMsg("Domid= " + allwimreq.getDomId() + " not present");
            allwimrep.setErrormsg("A WIM DomId is not present during the allocation of LogicalLinkId: " + allwimreq.getLogicalPathId() + "");
            SingletonEventBus.getBus().post(allwimrep);
        } else {

            if (stubmode) {
                //AllocateWIMNetworkStub thr = new AllocateWIMNetworkStub(el, allwimreq);
                Fed_AllocateWIMNetworkStub thr = new Fed_AllocateWIMNetworkStub(domElemList, allwimreq);
                thr.start();
            } else {
                //AllocateWIMNetworkThread thr = new AllocateWIMNetworkThread(el, allwimreq);
                Fed_AllocateWIMNetworkThread thr = new Fed_AllocateWIMNetworkThread(domElemList, allwimreq);
                thr.start();
                 Thread.sleep(3000);
            }
        }

        //R0.1 Just trigger the outcome (loopback)
//        NetworkAllocateWIMReply allwimrep = new NetworkAllocateWIMReply(allwimreq.getReqId(),
//                                                    allwimreq.getServId(), allwimreq.getDomId(),
//                                                    allwimreq.getWIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkAllocateWIMReply Event");
//        SingletonEventBus.getBus().post(allwimrep);
    }
    
    
    
//@Subscribe
//    public void handle_Fed_NetworkAllocateVIMReq(Fed_NetworkAllocateVIMReq allvimreq) {
//        System.out.println("SBIIF --> Handle NetworkAllocateVIMReq Event");
//        //retrieve Domain el from domid
//        //DomainElem el = dommap.get(allvimreq.getDomId());
//                //retrieve Domain el from domid
//        //DomainElem el = dommap.get(allwimreq.getDomId());
//        //START - For each domid retrieve from DB the domain info
//        ArrayList<DomainElem> domElemList = new ArrayList();
//        ArrayList<Long> domList = allvimreq.getVimdomlist();
//        long size = domList.size();
//
//        for (int i = 0; i < size; i++) {
//            //START - Retrieve Domain el from domid using database connection
//            String typeval = null;
//            String nameval = null;
//            String ipval = null;
//            Long portval = null;
//            Long idval = null;
//
//            try {
//                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
//                ps.setLong(1, allvimreq.getVimdomlist().get(i));
//                java.sql.ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//
//                    typeval = rs.getString("type");
//                    nameval = rs.getString("name");
//                    ipval = rs.getString("ip");
//                    portval = rs.getLong("port");
//                    idval = allvimreq.getVimdomlist().get(i);
//
//                }
//                rs.close();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(SBIIF.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//
//            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
//            domElemList.add(el);
//            // END - Retrieve
//
//        }
//        
//        if (domElemList == null) {
//            Fed_NetworkAllocateVIMReply allvimrep = new Fed_NetworkAllocateVIMReply(allvimreq.getReqId(),
//                    allvimreq.getServId(), false, 0, null, null, -1);
//            allvimrep.setErrorcode(-1);
//            allvimrep.setErrormsg("A VIM DomId is not present during the allocation of LogicalLinkId: " + allvimreq.getLogicalPathId() + "");
//            SingletonEventBus.getBus().post(allvimrep);
//        } else {
//            if (stubmode) {
//                Fed_AllocateVIMNetworkStub thr = new Fed_AllocateVIMNetworkStub(domElemList, allvimreq);
//                thr.start();
//            } else {
//                Fed_AllocateVIMNetworkThread thr = new Fed_AllocateVIMNetworkThread(domElemList, allvimreq);
//                thr.start();
//            }
//        }
//
//        //R0.1 Just trigger the outcome (loopback)
////        NetworkAllocateVIMReply allvimrep = new NetworkAllocateVIMReply(allvimreq.getReqId(),
////                                                    allvimreq.getServId(), allvimreq.getDomId(),
////                                                    allvimreq.getVIMElem(),true);
////        System.out.println("SBIIF --> Post NetworkAllocateVIMReply Event");
////        SingletonEventBus.getBus().post(allvimrep);
//    }

     @Subscribe
    public void handle_Fed_NetworkTerminateWIMReq(Fed_NetworkTerminateWIMReq termwimreq) {
        System.out.println("SBIIF --> Handle Fed_NetworkTerminateWIMReq Event");
        //retrieve Domain el from DB
             //Retrieve Domain el from domid
        HashMap<Integer,List<DomainElem>> elMap= new HashMap();
         //Loop on logicalLinks       
        for (int j = 0; j < termwimreq.getWimdomlistMap().size(); j++) {            
        ArrayList<Long> domList = termwimreq.getWimdomlistMap().get(j); 
            ArrayList<DomainElem> domElemList = new ArrayList();
        
        
        
        

        //Loop on domainIds
        for (int i = 0; i < termwimreq.getWimdomlistMap().get(j).size(); i++) {
            //START - Retrieve Domain el from domid using database connection
            String typeval = null;
            String nameval = null;
            String ipval = null;
            Long portval = null;
            Long idval = null;

            try {
                java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("Select name,type,ip,port from domain where domId=?");
                ps.setLong(1, termwimreq.getWimdomlistMap().get(j).get(i));
                
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    typeval = rs.getString("type");
                    nameval = rs.getString("name");
                    ipval = rs.getString("ip");
                    portval = rs.getLong("port");
                    idval = termwimreq.getWimdomlistMap().get(j).get(i);
                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(SBIIF.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
            domElemList.add(el);
           

        } //END - Loop on domaIds
        
        elMap.put(j, domElemList);
        
        }//END - Loop on logicalLinks
        
        //TO DO - Fix this part inside the if block
        if (elMap == null) {
//            NetworkTerminateWIMReply termwimrep = new NetworkTerminateWIMReply(termwimreq.getReqId(),
//                    termwimreq.getServId(), termwimreq.getDomId(), false);
//            termwimrep.setErrorCode(-1);
//            termwimrep.setErrorMsg("Domid= " + termwimreq.getDomId() + " not present");

//UNCOMMENT below line after fixing the if block
 Fed_NetworkTerminateWIMReply termwimrep = new Fed_NetworkTerminateWIMReply();
            SingletonEventBus.getBus().post(termwimrep);
        } else {
            if (stubmode) {
                Fed_TerminateWIMNetworkStub thr = new Fed_TerminateWIMNetworkStub(elMap, termwimreq);
                thr.start();
            } else {
                Fed_TerminateWIMNetworkThread thr = new Fed_TerminateWIMNetworkThread(elMap, termwimreq);
                thr.start();
            }
        }
        //R0.1 Just trigger the outcome (loopback)
//        NetworkTerminateWIMReply allwimrep = new NetworkTerminateWIMReply(allwimreq.getReqId(),
//                                                    allwimreq.getServId(), allwimreq.getDomId(),
//                                                    allwimreq.getWIMElem(),true);
//        System.out.println("SBIIF --> Post NetworkTerminateWIMReply Event");
//        SingletonEventBus.getBus().post(allwimrep);
    }

    @Subscribe
    public void handle_QoSPerfomanceIsolationReq(QosPerformanceIsolationReq qosPerfReq) {
        System.out.println("SBIIF --> QoSPerfomanceIsolationReq Event");

        //START - Retrieve Domain el from domid using database connection
        String typeval = null;
        String nameval = null;
        String ipval = null;
        Long portval = null;
        Long idval = null;

        try {
            java.sql.Connection conn = DbDomainDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("Select domId,name,type,ip,port from domain where domId=?");
            ps.setLong(1, qosPerfReq.getDomid());
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                typeval = rs.getString("type");
                nameval = rs.getString("name");
                ipval = rs.getString("ip");
                portval = rs.getLong("port");
                idval = rs.getLong("domId");

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(SBIIF.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomainElem el = new DomainElem(typeval, nameval, ipval, portval, idval);
        // END - Retrieve
        if (el == null) {
            //TODO: handle error reply
        } else {
            AssignQosQueueThread thr = new AssignQosQueueThread(el, qosPerfReq);
            thr.start();
        }
    }
    
}
