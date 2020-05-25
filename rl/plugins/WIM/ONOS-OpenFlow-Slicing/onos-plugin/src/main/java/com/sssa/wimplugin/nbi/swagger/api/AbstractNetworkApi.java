package com.sssa.wimplugin.nbi.swagger.api;

import com.sssa.wimplugin.SingletonEventBus;
import com.sssa.wimplugin.events.abstraction.WIMAbstractionReply;
import com.sssa.wimplugin.events.abstraction.WIMAbstractionRequest;
import com.sssa.wimplugin.nbi.swagger.model.InlineResponse200;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-12-03T14:13:12.071Z")
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
        
        System.out.println("WimAbstractedInformation ----> Arrived GET Request for Abstract view");
        
        reqid++;
        System.out.println("WimAbstractedInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        
        System.out.println("WimAbstractedInformation ----> Process the GET Request for Abstract view");
        WIMAbstractionRequest request = new WIMAbstractionRequest(reqid);
        
        SingletonEventBus.getBus().post(request);
    }
    
    @Subscribe
    public void handle_WIMAbstractionReply(WIMAbstractionReply ev) throws InterruptedException {
        
        System.out.println("WimAbstractedInformation ----> handle WIM GET Abstract topology event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReq()));
        System.out.println("WimAbstractedInformation ----> reqid = " + ev.getReq());
        System.out.println("WimAbstractedInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResp(), MediaType.APPLICATION_JSON).build();
        
        System.out.println("WimAbstractedInformation ----> response sent");
        asyncResp.resume(resp);
    }
    
}
