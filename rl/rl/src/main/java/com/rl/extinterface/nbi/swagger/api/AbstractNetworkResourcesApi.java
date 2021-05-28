package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.DbConnectionPool.DbAbstractionDatasource;
import com.rl.DbConnectionPool.DbDomainDatasource;
import com.rl.DbConnectionPool.DbFederationDatasource;
import com.rl.DbConnectionPool.DbServiceDatasource;
import com.rl.SingletonEventBus;
import com.rl.common.DatabaseDriver;
import com.rl.common.objects.E2ENetworkAllocateRequest_ManageReverseLL;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateReply;
import com.rl.events.resourcemanagement.NetworkAllocation.E2ENetworkAllocateRequest;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateRequest;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateReply;
import com.rl.events.resourcemanagement.NetworkTermination.E2ENetworkTerminateRequest;
import com.rl.extinterface.nbi.swagger.model.InlineResponse201;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.ArrayList;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;
import com.rl.common.objects.Fed_E2ENetworkAllocateRequest_Validator;
import com.rl.common.objects.Fed_E2ENetworkTerminateRequest_Validator;
import com.rl.events.resourcemanagement.NetworkAllocation.Fed_E2ENetworkAllocateReply;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateReply;
import com.rl.events.resourcemanagement.NetworkTermination.Fed_E2ENetworkTerminateRequest;
import com.rl.extinterface.nbi.swagger.model.DeleteInterNfviPopConnectivityRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/abstract-network-resources")
@Api(description = "the abstract-network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class AbstractNetworkResourcesApi {

    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static Map<String, Integer> allocationreq = new ConcurrentHashMap();
    private static Map<String, Integer> allocationcurrentreq = new ConcurrentHashMap();
    private static Map<String, List<InlineResponse201>> allocationreply = new ConcurrentHashMap();
    private static long reqid = 0;
    private static Map<String, List<String>> logical_links_from_so = new ConcurrentHashMap();

    public AbstractNetworkResourcesApi() {
        //reqid = 0;
    }

    @POST
    @ManagedAsync
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "Create inter-NFVI-PoP connectivity", notes = "", response = InlineResponse201.class, responseContainer = "List", tags = {"SOInterface",})
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successful operation", response = Object.class, responseContainer = "List")
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void createInterNfviPoPConnectivity(@Suspended final AsyncResponse ar, @Valid InterNfviPopConnectivityRequest body) {
        //return Response.ok().entity("magic!").build();

        // The Flag is true when an endpoint of the inter_Nfvi_PoP_connectivity is located in a Federated domain 
        boolean federated_resources;
        boolean intraPopAdminNetIsCreated;
        InterNfviPopConnectivityRequest mod_body;

        Fed_E2ENetworkAllocateRequest_Validator validator = new Fed_E2ENetworkAllocateRequest_Validator(body);

        federated_resources = validator.checkIfFederationResIsRequired();

        if (federated_resources == true) {
            intraPopAdminNetIsCreated = validator.checkIfIntraPopExist();
            if (intraPopAdminNetIsCreated == false) {
                String message = "IntraPop Admin network not found";
                //  AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
                //System.out.println("allocateNetwork ----> reqid = " + ev.getReqid());
                //System.out.println("allocateNetwork ----> request deblocked");
                Response resp;
                AsyncResponse asyncResp;
                resp = Response.status(Response.Status.NOT_FOUND).entity(message).build();
                ar.resume(resp);
                return;
            }
        }

        String servid = "";
        System.out.println("createInterNfviPoPConnectivity ----> allocate abstract network request suspended");
        System.out.println("createInterNfviPoPConnectivity ----> Calling post");
        reqid++;
        System.out.println("createInterNfviPoPConnectivity ----> reqid = " + Long.toString(reqid));

        //Check if auto management of Reverse LL is enabled
        String autoManagement = System.getProperty("AUTO_MANAGEMENT_LL");
        if ((autoManagement != null) && autoManagement.equals("yes")) {
            //logical_links_from_so
            List<String> logicalLinkIdList = extractListOfLogicalLinks(body);
            logical_links_from_so.put(Long.toString(reqid), logicalLinkIdList);
            E2ENetworkAllocateRequest_ManageReverseLL reverseLogicallingManagement = new E2ENetworkAllocateRequest_ManageReverseLL(body, federated_resources);
            reverseLogicallingManagement.computeModifiedBody();
        }

        suspended.put(Long.toString(reqid), ar);
        allocationreq.put(Long.toString(reqid), body.getLogicalLinkPathList().size());
        allocationcurrentreq.put(Long.toString(reqid), 0);
        allocationreply.put(Long.toString(reqid), new ArrayList());
        for (int i = 0; i < body.getMetaData().size(); i++) {
            if (body.getMetaData().get(i).getKey().compareTo("ServiceId") == 0) {
                servid = body.getMetaData().get(i).getValue();
            }
        }
        //   E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid,10, params);

        if (federated_resources == true) {
            Fed_E2ENetworkAllocateRequest request = new Fed_E2ENetworkAllocateRequest(reqid, servid, body);
            SingletonEventBus.getBus().post(request);
        } else {
            E2ENetworkAllocateRequest request = new E2ENetworkAllocateRequest(reqid, servid, body);
            SingletonEventBus.getBus().post(request);
        }

    }

    @DELETE
    @ManagedAsync
    @Consumes({"application/json"})
    @ApiOperation(value = "Delete inter-NFVI-PoP connectivity", notes = "", response = Void.class, tags = {"SOInterface"})
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Successful operation", response = Void.class)
        ,
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
        ,
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class)
        ,
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void deleteInterNfviPoPConnectivity(@Suspended final AsyncResponse ar, @Valid DeleteInterNfviPopConnectivityRequest body) {
        //return Response.ok().entity("magic!").build();

        // The Flag is true when an endpoint of the inter_Nfvi_PoP_connectivity is located in a Federated domain 
        boolean federated_resources;
        List<String> networkId = new ArrayList();

        for (int i = 0; i < body.getInterNfviPopConnnectivityIdList().size(); i++) {
            networkId.add(body.getInterNfviPopConnnectivityIdList().get(i).getInterNfviPopConnnectivityId());
        }

        Fed_E2ENetworkTerminateRequest_Validator validator = new Fed_E2ENetworkTerminateRequest_Validator(networkId);
        federated_resources = validator.checkIfFederationResIsRequired();

        //Check if auto management of Reverse LL is enabled
        String autoManagement = System.getProperty("AUTO_MANAGEMENT_LL");
        //If autoManagement of reverse LLs is enabled the IDs of reverse LLs is added to networkId
        if ((autoManagement != null) && autoManagement.equals("yes")) {
            //logical_links_from_so
            addReverseInterNfviPopConnnectivityId(networkId);

        }

        System.out.println("terminateNetwork ----> allocate compute request suspended");
        System.out.println("terminateNetwork ----> Calling post");
        reqid++;
        System.out.println("terminateNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);

        if (federated_resources == true) {
            Fed_E2ENetworkTerminateRequest request = new Fed_E2ENetworkTerminateRequest();
            request.setReqid(reqid);
            request.setServid(-1);
            request.setNetServIdList(networkId);

            SingletonEventBus.getBus().post(request);
        } else {
            E2ENetworkTerminateRequest request = new E2ENetworkTerminateRequest();
            request.setReqid(reqid);
            request.setServid(-1);
            request.setNetServIdList(networkId);

            SingletonEventBus.getBus().post(request);
        }

    }

////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_E2ENetworkAllocateReply(E2ENetworkAllocateReply ev) throws InterruptedException {
        System.out.println("allocateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();
        int val = allocationcurrentreq.get(Long.toString(ev.getReqid()));
        int num = allocationreq.get(Long.toString(ev.getReqid()));
        List<InlineResponse201> replylist = allocationreply.get(Long.toString(ev.getReqid()));
        InlineResponse201 el = new InlineResponse201();
        el.setInterNfviPopConnnectivityId(ev.getVn().getNetworkResourceId());
        el.setInterNfviPopNetworkSegmentType(ev.getVn().getNetworkType());
        replylist.add(el);
        val += 1; //increment the value 
        if (val != num) {
            System.out.println("allocateNetwork ----> Wait other response");
            allocationcurrentreq.put(Long.toString(ev.getReqid()), val);
            allocationreply.put(Long.toString(ev.getReqid()), replylist);
            return;
        }

        //Check if auto management of Reverse LL is enabled
        String autoManagement = System.getProperty("AUTO_MANAGEMENT_LL");
        if ((autoManagement != null) && autoManagement.equals("yes")) {
            //TO DO ......
            //logical_links_from_so.put(Long.toString(reqid), logicalLinkIdList);
            List<String> ll_list = logical_links_from_so.get(Long.toString(ev.getReqid()));
            //Remove not requested resource identifiers;
            remove_not_requested_resource_id(ll_list, replylist, false);
            logical_links_from_so.remove(Long.toString(ev.getReqid()));
        }

        allocationreq.remove(Long.toString(ev.getReqid()));
        allocationcurrentreq.remove(Long.toString(ev.getReqid()));
        allocationreply.remove(Long.toString(ev.getReqid()));
        //send response
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateNetwork ----> reqid = " + ev.getReqid());
        System.out.println("allocateNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok(replylist, MediaType.APPLICATION_JSON).build();
        System.out.println("allocateNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    @Subscribe
    public void handle_Fed_E2ENetworkAllocateReply(Fed_E2ENetworkAllocateReply ev) throws InterruptedException {
        System.out.println("allocateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();
        int val = allocationcurrentreq.get(Long.toString(ev.getReqid()));
        int num = allocationreq.get(Long.toString(ev.getReqid()));
        List<InlineResponse201> replylist = allocationreply.get(Long.toString(ev.getReqid()));
        InlineResponse201 el = new InlineResponse201();
        el.setInterNfviPopConnnectivityId(ev.getVn().getNetworkResourceId());
        el.setInterNfviPopNetworkSegmentType(ev.getVn().getNetworkType());
        replylist.add(el);
        val += 1; //increment the value 
        if (val != num) {
            System.out.println("allocateNetwork ----> Wait other response");
            allocationcurrentreq.put(Long.toString(ev.getReqid()), val);
            allocationreply.put(Long.toString(ev.getReqid()), replylist);
            return;
        }

        //Check if auto management of Reverse LL is enabled
        String autoManagement = System.getProperty("AUTO_MANAGEMENT_LL");
        if (autoManagement.equals("yes")) {
            //TO DO ......
            //logical_links_from_so.put(Long.toString(reqid), logicalLinkIdList);
            List<String> ll_list = logical_links_from_so.get(Long.toString(ev.getReqid()));
            //Remove not requested resource identifiers;
            remove_not_requested_resource_id(ll_list, replylist, true);
            logical_links_from_so.remove(Long.toString(ev.getReqid()));
        }

        allocationreq.remove(Long.toString(ev.getReqid()));
        allocationcurrentreq.remove(Long.toString(ev.getReqid()));
        allocationreply.remove(Long.toString(ev.getReqid()));
        //send response
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateNetwork ----> reqid = " + ev.getReqid());
        System.out.println("allocateNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok(replylist, MediaType.APPLICATION_JSON).build();
        System.out.println("allocateNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2ENetworkTerminateReply(E2ENetworkTerminateReply ev) throws InterruptedException {
        System.out.println("terminateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateNetwork ----> reqid = " + ev.getReqid());
        System.out.println("terminateNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok().build();
        System.out.println("terminateNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    @Subscribe
    public void handle_Fed_E2ENetworkTerminateReply(Fed_E2ENetworkTerminateReply ev) throws InterruptedException {
        System.out.println("terminateNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateNetwork ----> reqid = " + ev.getReqid());
        System.out.println("terminateNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok().build();
        System.out.println("terminateNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    // No @Subscribe METHODS
    public List<String> extractListOfLogicalLinks(InterNfviPopConnectivityRequest body) {
        System.out.println("createInterNfviPoPConnectivity ----> extract LLs");
        List<String> list = new ArrayList();
        //AsyncResponse asyncResp = suspended.take();
        for (int i = 0; i < body.getLogicalLinkPathList().size(); i++) {
            // networkId.add(body.getInterNfviPopConnnectivityIdList().get(i).getInterNfviPopConnnectivityId());
            list.add(body.getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getLogicalLinkId());
        }
        return list;
    }

    public void remove_not_requested_resource_id(List<String> ll, List<InlineResponse201> replylist, boolean federated_resources) {

        List<InlineResponse201> removedInterNfviPopConnnectivityId = new ArrayList();

        for (int i = 0; i < replylist.size(); i++) {

            String interNfviPopConnnectivityId = replylist.get(i).getInterNfviPopConnnectivityId();
            String logicalPathId = "";
            String logicalLinkId = "";

            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("select logicalPathId from networkservices where netServId=?");
                ps.setString(1, interNfviPopConnnectivityId);
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    logicalPathId = rs.getString("logicalPathId");

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

            if (federated_resources) {
                try {
                    java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId FROM fed_logicalpath where logicalPathId=?");
                    ps.setString(1, logicalPathId);
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        logicalLinkId = rs.getString("logicalLinkId");

                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            } else {

                try {
                    java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
                    PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId FROM logicalpath where logicalPathId=?");
                    ps.setString(1, logicalPathId);
                    java.sql.ResultSet rs = ps.executeQuery();
                    while (rs.next()) {

                        logicalLinkId = rs.getString("logicalLinkId");

                    }
                    rs.close();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            if (!ll.contains(logicalLinkId)) {
                removedInterNfviPopConnnectivityId.add(replylist.get(i));
                replylist.remove(i);
                i--;
            }

        }

        // Add relations between replylist and removedInterNfviPopConnnectivityId in database
        for (int i = 0; i < replylist.size(); i++) {
            String netServId = replylist.get(i).getInterNfviPopConnnectivityId();

            for (int j = 0; j < removedInterNfviPopConnnectivityId.size(); j++) {

                String removed_netServId = removedInterNfviPopConnnectivityId.get(j).getInterNfviPopConnnectivityId();

                try {

                    java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();

                    PreparedStatement ps = conn.prepareStatement("INSERT INTO networkservices_reverse_networkservices (netServId,reverse_netServId) VALUES(?,?)");
                    ps.setString(1, netServId);
                    ps.setString(2, removed_netServId);

                    ps.executeUpdate();
                    ps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DatabaseDriver.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("DatabaseDriver ---> db exception: " + ex.getMessage());
                }

            }

        }

    }

    public void addReverseInterNfviPopConnnectivityId(List<String> networkId) {
        System.out.println("deleteInterNfviPoPConnectivity ----> addReverseInterNfviPopConnnectivityId");
        List<String> reverse_networkId=new ArrayList();
     
        
        //AsyncResponse asyncResp = suspended.take();
        for (int i = 0; i < networkId.size(); i++) {
            String reverse_netServId;
            try {
                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT reverse_netServId FROM networkservices_reverse_networkservices where netServId=?");
                ps.setString(1, networkId.get(i));
                java.sql.ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    reverse_netServId = rs.getString("reverse_netServId");

                    if (!networkId.contains(reverse_netServId)) {

                        networkId.add(reverse_netServId);
                        reverse_networkId.add(reverse_netServId);
                        
                        System.out.println("addReverseInterNfviPopConnnectivityId ----> ReverseInterNfviPopConnnectivityId:" + reverse_netServId);
                        i++;

                    }

                }
                rs.close();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

//        for (int i = 0; i < networkId.size(); i++) {
//// Remove entries from networkservices_reverse_networkservices
//
//            try {
//
//                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
//                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservices_reverse_networkservices WHERE netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//                ps.setString(1, networkId.get(i));
//                ps.executeUpdate();
//                ps.close();
//
//            } catch (SQLException ex) {
//                Logger.getLogger(DatabaseDriver.class
//                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//        }





  for (int i = 0; i < reverse_networkId.size(); i++) {
// Remove entries from networkservices_reverse_networkservices

            try {

                java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM networkservices_reverse_networkservices WHERE reverse_netServId=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ps.setString(1, reverse_networkId.get(i));
                ps.executeUpdate();
                ps.close();

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseDriver.class
                        .getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

    }

}
