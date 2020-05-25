package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.MECAbstractionReply;
import com.ericsson.dummyplugin.events.abstraction.MECAbstractionRequest;
import com.ericsson.dummyplugin.events.allocate.AppdAllocateReply;
import com.ericsson.dummyplugin.events.allocate.AppdAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.AppdTerminateReply;
import com.ericsson.dummyplugin.events.terminate.AppdTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse200;
import com.ericsson.dummyplugin.nbi.swagger.model.MECTrafficServiceCreationRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.MECTrafficServiceCreationResponse;
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

@Path("/service")
@Api(description = "the service API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-28T11:44:09.884Z")
public class ServiceApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public ServiceApi() {
        //reqid = 0;

    }
    
    @GET
    @ManagedAsync
    @Path("/regions")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of all covered regions.", notes = "Retrieve a list of MEC regions, reporting their identifiers and location information.", response = InlineResponse200.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of regions.", response = InlineResponse200.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
    })
    public void serviceRegionsGet(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        //return Response.ok().entity("magic!").build();
        System.out.println("serviceRegionsGet ----> query MEC region suspended");
        System.out.println("serviceRegionsGet ----> Calling post");
        reqid++;
        System.out.println("serviceRegionsGet ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        MECAbstractionRequest request = new MECAbstractionRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Path("/regions/{RegionId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of MEC service requests for the given region.", notes = "Retrieve a list of MEC service requests for the given region.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List", tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of MEC service requests.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Region not found.", response = Void.class)
    })
    public Response serviceRegionsRegionIdGet(@PathParam("RegionId") @ApiParam("Region identifier.") String regionId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/requests")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of MEC service requests.", notes = "Retrieve a list of MEC service requests.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List", tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of MEC service requests.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List")
    })
    public Response serviceRequestsGet() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @ManagedAsync
    @Path("/requests")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create MEC service rules.", notes = "Create MEC service rules.", response = MECTrafficServiceCreationResponse.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "MEC service created successfully.", response = MECTrafficServiceCreationResponse.class),
        @ApiResponse(code = 401, message = "MEC service creation failed.", response = Void.class)
    })
    public void serviceRequestsPost(@Suspended final AsyncResponse ar, @Valid MECTrafficServiceCreationRequest mecTrafficServiceCreationRequest) {
        //return Response.ok().entity("magic!").build();
        System.out.println("serviceRegionsGet ----> query MEC region suspended");
        System.out.println("serviceRegionsGet ----> Calling post");
        reqid++;
        System.out.println("serviceRegionsGet ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        AppdAllocateRequest request = new AppdAllocateRequest(reqid, mecTrafficServiceCreationRequest);
        SingletonEventBus.getBus().post(request);
    }

    @DELETE
    @ManagedAsync
    @Path("/requests/{RequestId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete service.", notes = "Delete service identified by the given request ID.", response = Void.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "MEC service deleted successfully.", response = Void.class),
        @ApiResponse(code = 404, message = "Service not found.", response = Void.class)
    })
    public void serviceRequestsRequestIdDelete(@Suspended final AsyncResponse ar, @PathParam("RequestId") @ApiParam("Request identifier.") String requestId) {
        //return Response.ok().entity("magic!").build();
        System.out.println("serviceRegionsGet ----> query MEC region suspended");
        System.out.println("serviceRegionsGet ----> Calling post");
        reqid++;
        System.out.println("serviceRegionsGet ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        AppdTerminateRequest request = new AppdTerminateRequest(reqid, requestId);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Path("/requests/{RequestId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve information about a MEC service request.", notes = "Retrieve information about a MEC service request.", response = MECTrafficServiceCreationRequest.class, tags={ "MECResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "MEC service request information.", response = MECTrafficServiceCreationRequest.class),
        @ApiResponse(code = 404, message = "Service not found.", response = Void.class)
    })
    public Response serviceRequestsRequestIdGet(@PathParam("RequestId") @ApiParam("Request identifier.") String requestId) {
        return Response.ok().entity("magic!").build();
    }
    
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_MECAbstractionReply(MECAbstractionReply ev) throws InterruptedException {
        System.out.println("MECAbstractionReply ----> handle mec region info event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReq()));
        System.out.println("MECAbstractionReply ----> reqid = " + ev.getReq());
        System.out.println("MECAbstractionReply ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResp(), MediaType.APPLICATION_JSON).build();
        System.out.println("MECAbstractionReply ----> response ok");
        asyncResp.resume(resp);
    }
    
    @Subscribe
    public void handle_AppdAllocateReply(AppdAllocateReply ev) throws InterruptedException {
        System.out.println("AppdAllocateReply ----> handle appd service allocate event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("AppdAllocateReply ----> reqid = " + ev.getReqid());
        System.out.println("AppdAllocateReply ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getRequest(), MediaType.APPLICATION_JSON).build();
        System.out.println("AppdAllocateReply ----> response ok");
        asyncResp.resume(resp);
    }
    
    @Subscribe
    public void handle_AppdTerminateReply(AppdTerminateReply ev) throws InterruptedException {
        System.out.println("AppdTerminateReply ----> handle appd service terminate event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("RadioAbstractionReply ----> reqid = " + ev.getReqid());
        System.out.println("RadioAbstractionReply ----> request deblocked");
        Response resp;
        resp = Response.ok().build();
        System.out.println("RadioAbstractionReply ----> response ok");
        asyncResp.resume(resp);
    }
}
