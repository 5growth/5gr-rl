package com.ericsson.xenplugin.nbi.swagger.api;




import com.ericsson.xenplugin.SingletonEventBus;
import com.ericsson.xenplugin.events.allocate.VirtualNetworkAllocateReply;
import com.ericsson.xenplugin.events.allocate.VirtualNetworkAllocateRequest;
import com.ericsson.xenplugin.events.terminate.VirtualNetworkTerminateReply;
import com.ericsson.xenplugin.events.terminate.VirtualNetworkTerminateRequest;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkRequest;
import com.ericsson.xenplugin.nbi.swagger.model.AllocateNetworkResult;
import com.ericsson.xenplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.xenplugin.nbi.swagger.model.Filter;
import com.ericsson.xenplugin.nbi.swagger.model.NfviPop;
import com.ericsson.xenplugin.nbi.swagger.model.QueryNetworkCapacityRequest;
import com.ericsson.xenplugin.nbi.swagger.model.VLANInfo;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualNetwork;
import com.google.common.eventbus.Subscribe;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.math.BigDecimal;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;


//TODO: Handle IntraPoP API
@Path("/network_resources")
@Api(description = "the network_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-27T12:43:31.935Z")
public class NetworkResourcesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public NetworkResourcesApi() {
        //reqid = 0;

    }
    @GET
    @Path("/free_vlan")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve free vlan tag from VIM domain", notes = "Retrieve free vlan tag from VIM domain", response = BigDecimal.class, responseContainer = "List", tags={ "VIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = VLANInfo.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response freeVlan() {
        return Response.ok().entity("Not Implemented!").build();
    }
    
    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = AllocateNetworkResult.class, tags={ "virtualisedNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = AllocateNetworkResult.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "network already added", response = Void.class) })
    public void vIMallocateNetwork(@Suspended final AsyncResponse ar, @Valid AllocateNetworkRequest params) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("allocateVirtualNetwork ----> query nfvipop suspended");
        System.out.println("allocateVirtualNetwork ----> Calling post");
        reqid++;
        System.out.println("allocateVirtualNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        VirtualNetworkAllocateRequest request = new VirtualNetworkAllocateRequest(reqid, params);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Path("/nfvi-pop-network-information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags={ "virtualisedNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNFVIPoPNetworkInformation(@Valid Filter nfviPopNetworkInformationRequest) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/capacities")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags={ "virtualisedNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkCapacity(@Valid QueryNetworkCapacityRequest queryNetworkCapacityRequest) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetwork.class, responseContainer = "List", tags={ "virtualisedNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual network resource(s) matching the filter. The cardinality can be 0 if no matching network resources exist.", response = VirtualNetwork.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response vIMqueryNetworks(@Valid Filter networkQueryFilter) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "virtualisedNetworkResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised network resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void vIMterminateNetworks(@Suspended final AsyncResponse ar, @QueryParam("networkResourceId") @NotNull   @ApiParam("Identifier of the virtualised network resource(s) to be terminated.")  List<String> networkResourceId) {
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("terminateVirtualNetwork ----> query nfvipop suspended");
        System.out.println("terminateVirtualNetwork ----> Calling post");
        reqid++;
        System.out.println("terminateVirtualNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        VirtualNetworkTerminateRequest request = new VirtualNetworkTerminateRequest(reqid, networkResourceId);
        SingletonEventBus.getBus().post(request);
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_VirtualNetworkAllocateReply(VirtualNetworkAllocateReply ev) throws InterruptedException {
        System.out.println("allocateVirtualNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("allocateVirtualNetwork ----> reqid = " + ev.getReqId());
        System.out.println("allocateVirtualNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getE2EWIMElem(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocateVirtualNetwork ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_VirtualNetworkTerminateReply(VirtualNetworkTerminateReply ev) throws InterruptedException {
        System.out.println("terminateVirtualNetwork ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqId()));
        System.out.println("terminateVirtualNetwork ----> reqid = " + ev.getReqId());
        System.out.println("terminateVirtualNetwork ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getE2EWIMElem(), MediaType.APPLICATION_JSON).build();
        System.out.println("terminateVirtualNetwork ----> response ok");
        asyncResp.resume(resp);
    }
}
