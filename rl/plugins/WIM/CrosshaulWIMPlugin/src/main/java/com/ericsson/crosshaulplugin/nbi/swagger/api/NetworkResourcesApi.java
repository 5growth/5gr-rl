package com.ericsson.crosshaulplugin.nbi.swagger.api;



import com.ericsson.crosshaulplugin.SingletonEventBus;
import com.ericsson.crosshaulplugin.events.allocate.ServiceNetworkAllocateReply;
import com.ericsson.crosshaulplugin.events.allocate.ServiceNetworkAllocateRequest;
import com.ericsson.crosshaulplugin.events.terminate.ServiceNetworkTerminateReply;
import com.ericsson.crosshaulplugin.events.terminate.ServiceNetworkTerminateRequest;
import com.ericsson.crosshaulplugin.nbi.swagger.model.AllocateParameters;
import com.ericsson.crosshaulplugin.nbi.swagger.model.AllocateReply;
import com.ericsson.crosshaulplugin.nbi.swagger.model.Filter;
import com.ericsson.crosshaulplugin.nbi.swagger.model.NetworkIds;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualNetwork;
import com.google.common.eventbus.Subscribe;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/network-resources")
@Api(description = "the network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T22:38:05.247Z")
public class NetworkResourcesApi {
    
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public NetworkResourcesApi() {
        //reqid = 0;

    }

    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Allocate WAN Connectivity", notes = "Allocate WAN Connectivity", response = AllocateReply.class, tags={ "WIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = AllocateReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class) })
    public void allocateWimAbstractedInformation(@Suspended final AsyncResponse ar, @Valid AllocateParameters params) {
        //return Response.ok().entity("magic!").build();
        System.out.println("allocateWimAbstractedInformation ----> query WAN allocated suspended");
        System.out.println("allocateWimAbstractedInformation ----> Calling post");
        reqid++;
        System.out.println("allocateWimAbstractedInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ServiceNetworkAllocateRequest request = new ServiceNetworkAllocateRequest(reqid, params);
        SingletonEventBus.getBus().post(request);
    }

    @DELETE
    @ManagedAsync
    @Path("/{networkId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete WAN Connectivity", notes = "Delete WAN Connectivity", response = NetworkIds.class, responseContainer = "List", tags={ "WIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised network resource(s) successfully terminated.", response = NetworkIds.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void deleteWimAbstractedInformation(@Suspended final AsyncResponse ar, @PathParam("networkId") @ApiParam("Identifier of the virtualised network resource(s) to be terminated.") String networkId) {
        //return Response.ok().entity("magic!").build();
        System.out.println("deleteWimAbstractedInformation ----> query WAN delete suspended");
        System.out.println("deleteWimAbstractedInformation ----> Calling post");
        reqid++;
        System.out.println("deleteWimAbstractedInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ServiceNetworkTerminateRequest request = new ServiceNetworkTerminateRequest(reqid, networkId);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve WAN Connectivity", notes = "Retrieve WAN Connectivity", response = VirtualNetwork.class, responseContainer = "List", tags={ "WIMNetworkResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual network resource(s) matching the filter. The cardinality can be 0 if no matching network resources exist.", response = VirtualNetwork.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response retrieveWimAbstractedInformation(@Valid Filter networkQueryFilter) {
        return Response.ok().entity("magic!").build();
    }
    
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_ServiceNetworkAllocateReply(ServiceNetworkAllocateReply ev) throws InterruptedException {
        System.out.println("allocateWimAbstractedInformation ----> handle WAN allocate event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateWimAbstractedInformation ----> reqid = " + ev.getReqid());
        System.out.println("allocateWimAbstractedInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getRequest(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocateWimAbstractedInformation ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_ServiceNetworkTerminateReply(ServiceNetworkTerminateReply ev) throws InterruptedException {
        System.out.println("deleteWimAbstractedInformation ----> handle WAN delete event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("deleteWimAbstractedInformation ----> reqid = " + ev.getReqid());
        System.out.println("deleteWimAbstractedInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getNetlist(), MediaType.APPLICATION_JSON).build();
        System.out.println("deleteWimAbstractedInformation ----> response ok");
        asyncResp.resume(resp);
    }
    
}
