package com.ericsson.dummyplugin.nbi.swagger.api;



import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.ComputeCapacityReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeCapacityRequest;
import com.ericsson.dummyplugin.events.abstraction.ComputeResourceInformationReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeResourceInformationRequest;
import com.ericsson.dummyplugin.events.abstraction.ComputeZoneReply;
import com.ericsson.dummyplugin.events.abstraction.ComputeZoneRequest;
import com.ericsson.dummyplugin.events.abstraction.NfviPopAbstractionReply;
import com.ericsson.dummyplugin.events.abstraction.NfviPopAbstractionRequest;
import com.ericsson.dummyplugin.events.allocate.ComputeAllocateReply;
import com.ericsson.dummyplugin.events.allocate.ComputeAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.ComputeTerminateReply;
import com.ericsson.dummyplugin.events.terminate.ComputeTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.dummyplugin.nbi.swagger.model.NfviPop;
import com.ericsson.dummyplugin.nbi.swagger.model.QueryComputeCapacityRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.ResourceZone;
import com.ericsson.dummyplugin.nbi.swagger.model.VIMAllocateComputeRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.VIMVirtualCompute;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualCompute;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeFlavour;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualComputeResourceInformation;
import com.google.common.eventbus.Subscribe;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;


@Path("/compute_resources")
@Api(description = "the compute_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-27T12:43:31.935Z")
public class ComputeResourcesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public ComputeResourcesApi() {
        //reqid = 0;

    }

    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VIMVirtualCompute.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class) })
    public void allocateCompute(@Suspended final AsyncResponse ar, @Valid VIMAllocateComputeRequest body) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("allocateCompute ----> query nfvipop suspended");
        System.out.println("allocateCompute ----> Calling post");
        reqid++;
        System.out.println("allocateCompute ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeAllocateRequest request = new ComputeAllocateRequest(reqid, body);
        SingletonEventBus.getBus().post(request);
    }

    @POST
    @Path("/flavours")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = String.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class) })
    public Response createFlavour(@Valid VirtualComputeFlavour flavour) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/flavours/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = Void.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "No output parameters", response = Void.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response deleteFlavours(@PathParam("id") @ApiParam("Identifier of the Compute Flavour to be deleted.") String id) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @ManagedAsync
    @Path("/capacities")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void queryComputeCapacity(@Suspended final AsyncResponse ar, @QueryParam("ComputeResourceTypeId") @NotNull    String computeResourceTypeId) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryComputeCapacity ----> query nfvipop suspended");
        System.out.println("queryComputeCapacity ----> Calling post");
        reqid++;
        System.out.println("queryComputeCapacity ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        QueryComputeCapacityRequest capreq = new QueryComputeCapacityRequest();
        capreq.setComputeResourceTypeId(computeResourceTypeId);
        ComputeCapacityRequest request = new ComputeCapacityRequest(reqid, capreq);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @ManagedAsync
    @Path("/information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeResourceInformation.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Virtualised compute resource information in the VIM that satisfies the query condition.", response = VirtualComputeResourceInformation.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void queryComputeInformation(@Suspended final AsyncResponse ar, @QueryParam("zoneId") @NotNull   @ApiParam("Filter defining the information of consumable virtualised resources on which the query applies.")  String zoneId) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryComputeInformation ----> query nfvipop suspended");
        System.out.println("queryComputeInformation ----> Calling post");
        reqid++;
        System.out.println("queryComputeInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeResourceInformationRequest request = new ComputeResourceInformationRequest(reqid, zoneId);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @ManagedAsync
    @Path("/resource_zones")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ResourceZone.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved about the Resource Zone. The cardinality can be 0 if no matching information exist.", response = ResourceZone.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void queryComputeResourceZone(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryComputeResourceZone ----> query nfvipop suspended");
        System.out.println("queryComputeResourceZone ----> Calling post");
        reqid++;
        System.out.println("queryComputeResourceZone ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeZoneRequest request = new ComputeZoneRequest(reqid, new String(""));
        SingletonEventBus.getBus().post(request);
        
    }

    @GET
    @Path("/flavours")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeFlavour.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualComputeFlavour.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryFlavors() {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @ManagedAsync
    @Path("/nfvi_pop_compute_information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void queryNFVIPoPComputeInformation(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("Not Implemented!").build();
        //return Response.ok().entity("magic!").build();
                //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryNFVIPoPComputeInformation ----> query nfvipop suspended");
        System.out.println("queryNFVIPoPComputeInformation ----> Calling post");
        reqid++;
        System.out.println("queryNFVIPoPComputeInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        NfviPopAbstractionRequest request = new NfviPopAbstractionRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VIMVirtualCompute.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualCompute.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryResources() {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "virtualisedComputeResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised compute resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void terminateAbstractResources(@Suspended final AsyncResponse ar, @QueryParam("computeId") @NotNull   @ApiParam("Identifier(s) of the abstract compute resource(s) to be terminated.")  List<String> computeId) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("terminateAbstractResources ----> query nfvipop suspended");
        System.out.println("terminateAbstractResources ----> Calling post");
        reqid++;
        System.out.println("terminateAbstractResources ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeTerminateRequest request = new ComputeTerminateRequest(reqid, computeId);
        SingletonEventBus.getBus().post(request);
    }
    
    
         ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_ComputeAllocateReply(ComputeAllocateReply ev) throws InterruptedException {
        System.out.println("allocateCompute ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("allocateCompute ----> reqid = " + ev.getReqId());
        System.out.println("allocateCompute ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getReply(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocateCompute ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_ComputeTerminateReply(ComputeTerminateReply ev) throws InterruptedException {
        System.out.println("terminateAbstractResources ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("terminateAbstractResources ----> reqid = " + ev.getReqId());
        System.out.println("terminateAbstractResources ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getReply(), MediaType.APPLICATION_JSON).build();
        System.out.println("terminateAbstractResources ----> response ok");
        asyncResp.resume(resp);
    }
    
    //Subscribe Event
    @Subscribe
    public void handle_ComputeCapacityReply(ComputeCapacityReply ev) throws InterruptedException {
        System.out.println("queryComputeCapacity ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryComputeCapacity reqid = " + ev.getReqId());
        System.out.println("queryComputeCapacity ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getCapacityInfo(), MediaType.APPLICATION_JSON).build();
        System.out.println("queryComputeCapacity ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_ComputeResourceInformationReply(ComputeResourceInformationReply ev) throws InterruptedException {
        System.out.println("queryComputeInformation ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryComputeInformation ----> reqid = " + ev.getReqId());
        System.out.println("queryComputeInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getcompresinfolist(), MediaType.APPLICATION_JSON).build();

        System.out.println("queryComputeInformation ----> response ok");
        asyncResp.resume(resp);
    }
        //Subscribe Event
    @Subscribe
    public void handle_ComputeZoneReply(ComputeZoneReply ev) throws InterruptedException {
        System.out.println("queryComputeResourceZone ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("queryComputeResourceZone ----> reqid = " + ev.getReqid());
        System.out.println("queryComputeResourceZone ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getZonelist(), MediaType.APPLICATION_JSON).build();
        System.out.println("queryComputeResourceZone ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_NfviPopAbstractionReply(NfviPopAbstractionReply ev) throws InterruptedException {
        System.out.println("queryNFVIPoPComputeInformation ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("queryNFVIPoPComputeInformation ----> reqid = " + ev.getReqId());
        System.out.println("queryNFVIPoPComputeInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getnfvipoplist(), MediaType.APPLICATION_JSON).build();

        System.out.println("queryNFVIPoPComputeInformation ----> response ok");
        asyncResp.resume(resp);
    }
    
}


