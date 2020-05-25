package com.ericsson.crosshaulplugin.nbi.swagger.api;

import com.ericsson.crosshaulplugin.SingletonEventBus;
import com.ericsson.crosshaulplugin.events.abstraction.WIMAbstractionReply;
import com.ericsson.crosshaulplugin.events.abstraction.WIMAbstractionRequest;
import com.ericsson.crosshaulplugin.nbi.swagger.model.InlineResponse200;
import com.google.common.eventbus.Subscribe;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/abstract-network")
@Api(description = "the abstract-network API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T16:34:24.071Z")
public class AbstractNetworkApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public AbstractNetworkApi() {
        //reqid = 0;

    }

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve aggregated WAN Connectivity", notes = "Retrieve aggregated WAN Connectivity", response = InlineResponse200.class, tags={ "WIMNetworkResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse200.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public void collectWimAbstractedInformation(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("collectWimAbstractedInformation ----> query WIM abstraction suspended");
        System.out.println("collectWimAbstractedInformation ----> Calling post");
        reqid++;
        System.out.println("collectWimAbstractedInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        WIMAbstractionRequest request = new WIMAbstractionRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    @Subscribe
    public void handle_WIMAbstractionReply(WIMAbstractionReply ev) throws InterruptedException {
        System.out.println("collectWimAbstractedInformation ----> handle WIM abstraction event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReq()));
        System.out.println("collectWimAbstractedInformation ----> reqid = " + ev.getReq());
        System.out.println("collectWimAbstractedInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResp(), MediaType.APPLICATION_JSON).build();
        System.out.println("collectWimAbstractedInformation ----> response ok");
        asyncResp.resume(resp);
    }
    
}
