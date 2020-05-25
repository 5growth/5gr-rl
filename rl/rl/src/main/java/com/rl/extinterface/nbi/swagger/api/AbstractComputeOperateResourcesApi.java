package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateDBReq;
import com.rl.events.resourcemanagement.ComputeAllocation.ComputeOperateRep;
import com.rl.extinterface.nbi.swagger.model.OperateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;


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

@Path("/abstract_compute_operate_resources")
@Api(description = "the abstract_compute_operate_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class AbstractComputeOperateResourcesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public AbstractComputeOperateResourcesApi() {
        //reqid = 0;

    }
    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = OperateComputeRequest.class, tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)
    })
    public void abstractOperateCompute(@Suspended final AsyncResponse ar, @Valid OperateComputeRequest body) {
        //return Response.ok().entity("magic!").build();
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("abstractOperateCompute ----> abstract operate compute request suspended");
        System.out.println("abstractOperateCompute ----> Calling post");
        reqid++;
        System.out.println("abstractOperateCompute ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        ComputeOperateDBReq request = new ComputeOperateDBReq(reqid,body);
        request.setReqid(reqid);
        
        SingletonEventBus.getBus().post(request);
    }
    
    ////////////////Guava Event Handlers//////////////////////////////////////// 
    //Subscribe Event
    @Subscribe
    public void handle_abstractOperateComputeReply(ComputeOperateRep ev) throws InterruptedException {
        System.out.println("handle_abstractOperateComputeReply ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("handle_abstractOperateComputeReply ----> reqid = " + ev.getReqid());
        System.out.println("handle_abstractOperateComputeReply ----> request deblocked");
        VirtualCompute replist;
        if (ev.isOutcome()) {
            replist = ev.getResponse();
        } else {
            replist = new VirtualCompute();
        }
        Response resp;
        resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        System.out.println("handle_abstractOperateComputeReply ----> response ok");
        asyncResp.resume(resp);
    }
}
