package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateReply;
import com.rl.events.resourcemanagement.ComputeAllocation.E2EComputeAllocateRequest;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateReply;
import com.rl.events.resourcemanagement.ComputeTermination.E2EComputeTerminateRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;


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

@Path("/abstract-compute-resources")
@Api(description = "the abstract-compute-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class AbstractComputeResourcesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public AbstractComputeResourcesApi() {
        //reqid = 0;

    }
    
    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Allocate abstract compute resources", notes = "", response = VirtualCompute.class, tags={ "SOInterface",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated abstracted virtualised compute resource.", response = VirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)
    })
    public void allocateAbstractCompute(@Suspended final AsyncResponse ar, @Valid AllocateComputeRequest body) {
        //return Response.ok().entity("magic!").build();
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("allocateAbstractCompute ----> allocate abstract compute request suspended");
        System.out.println("allocateAbstractCompute ----> Calling post");
        reqid++;
        System.out.println("allocateAbstractCompute ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        E2EComputeAllocateRequest request = new E2EComputeAllocateRequest();
        request.setReqid(reqid);
        
        List<MetaDataInner> metadata = new ArrayList();
        metadata = body.getMetadata();
        for (int i=0; i < metadata.size(); i++) {
            if (metadata.get(i).getKey().compareTo("ServiceId") == 0) {
                String val = metadata.get(i).getValue();
                request.setServid(val);
            }
        }         
        
        request.setRequest(body);
        SingletonEventBus.getBus().post(request);
    }

    @DELETE
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Terminate abstract compute resources", notes = "", response = String.class, responseContainer = "List", tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised compute resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void terminateResources(@Suspended final AsyncResponse ar, @QueryParam("computeId") @NotNull   @ApiParam("Identifier(s) of the virtualised compute resource(s) to be terminated.")  List<String> computeId) {
        //return Response.ok().entity("magic!").build();
        System.out.println("terminateResources ----> terminate abstracted compute request suspended");
        System.out.println("terminateResources ----> Calling post");
        reqid++;
        System.out.println("terminateResources ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);       
       
       long servId = -1;
       E2EComputeTerminateRequest request = new E2EComputeTerminateRequest(reqid, servId, computeId);
       //E2EComputeTerminateRequest request = new E2EComputeTerminateRequest(reqid, 10, ids); 
        
        SingletonEventBus.getBus().post(request);
    }
    
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_E2EComputeAllocateReply(E2EComputeAllocateReply ev) throws InterruptedException {
        System.out.println("allocateCompute ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateCompute ----> reqid = " + ev.getReqid());
        System.out.println("allocateCompute ----> request deblocked");
        com.rl.extinterface.nbi.swagger.model.VirtualCompute replist;
        if (ev.isOutcome()) {
            replist = ev.getComputereply();
        } else {
            replist = new com.rl.extinterface.nbi.swagger.model.VirtualCompute();
        }
        Response resp;
        resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        System.out.println("terminateCompute ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2EComputeTerminateReply(E2EComputeTerminateReply ev) throws InterruptedException {
        System.out.println("terminateCompute ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateCompute ----> reqid = " + ev.getReqid());
        System.out.println("terminateCompute ----> request deblocked");
        List<String> replist = new ArrayList();
        
        replist = ev.getComputeIdList();
        Response resp;
        if (replist.isEmpty()) {
            resp = Response.ok(new String(""), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("terminateCompute ----> response ok");
        asyncResp.resume(resp);
    }
}
