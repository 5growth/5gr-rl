package com.ericsson.xenplugin.nbi.swagger.api;


import com.ericsson.xenplugin.SingletonEventBus;
import com.ericsson.xenplugin.nbi.swagger.model.OperateComputeRequest;
import com.ericsson.xenplugin.nbi.swagger.model.VIMVirtualCompute;
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


//TODO manage the API

@Path("/compute_operate_resources")
@Api(description = "the compute_operate_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class ComputeOperateResourcesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public ComputeOperateResourcesApi() {
        //reqid = 0;

    }
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VIMVirtualCompute.class, tags={ "VIMComputeResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VIMVirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)
    })
    public Response operateCompute( @Valid OperateComputeRequest body) {
        return Response.ok().entity("magic!").build();
   }
    
    
//         ////////////////Guava Event Handlers////////////////////////////////////////
//    //Subscribe Event
//    @Subscribe
//    public void handle_ComputeOperateReply(ComputeOperateReply ev) throws InterruptedException {
//        System.out.println("ComputeOperate ----> handle  compute operate event");
//        //AsyncResponse asyncResp = suspended.take();
//
//        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
//        System.out.println("ComputeOperate ----> reqid = " + ev.getReqid());
//        System.out.println("ComputeOperate ----> request deblocked");
//        Response resp;
//        resp = Response.ok(ev.getReply(), MediaType.APPLICATION_JSON).build();
//        System.out.println("ComputeOperate ----> response ok");
//        asyncResp.resume(resp);
//    }
    
    
}
