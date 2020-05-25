package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateReply;
import com.rl.events.resourcemanagement.PhysicalAllocation.E2EPhysicalAllocateRequest;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateReply;
import com.rl.events.resourcemanagement.PhysicalTermination.E2EPhysicalTerminateRequest;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;

import javax.ws.rs.*;

import io.swagger.annotations.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/physical-resources")
@Api(description = "the physical-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T12:46:56.347Z")
public class PhysicalResourcesApi {
    
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public PhysicalResourcesApi() {
        
    }

    @DELETE
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete pnf in radio/compute domain", notes = "Delete PNF of Radio/NFVI PoP", response = PNFReply.class, tags={ "SOInterface",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = PNFReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void deleteAbstractPnf(@Suspended final AsyncResponse ar, @Valid PNFReply body) {
        //return Response.ok().entity("magic!").build();
        System.out.println("setAbstractPnf ----> terminate pnf request suspended");
        System.out.println("setAbstractPnf ----> Calling delete");
        reqid++;
        System.out.println("setAbstractPnf ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        long servId = -1;
        E2EPhysicalTerminateRequest request = new E2EPhysicalTerminateRequest(reqid, servId, body);
       //E2EComputeTerminateRequest request = new E2EComputeTerminateRequest(reqid, 10, ids); 
        
        SingletonEventBus.getBus().post(request);
    }

    @POST
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Set pnf status supported by radio/compute domain", notes = "Set PNF of Radio/NFVI PoP to start or stop", response = PNFReply.class, tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = PNFReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void setAbstractPnf(@Suspended final AsyncResponse ar, @Valid PNFRequest body) {
        //return Response.ok().entity("magic!").build();
        System.out.println("setAbstractPnf ----> allocate pnf request suspended");
        System.out.println("setAbstractPnf ----> Calling post");
        reqid++;
        System.out.println("setAbstractPnf ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        E2EPhysicalAllocateRequest request = new E2EPhysicalAllocateRequest();
        request.setReqid(reqid);
        
        List<MetaDataInner> metadata = new ArrayList();
        metadata = body.getMetaData();
        for (int i=0; i < metadata.size(); i++) {
            if (metadata.get(i).getKey().compareTo("ServiceId") == 0) {
                String val = metadata.get(i).getValue();
                request.setServid(val);
            }
        }         
        
        request.setRequest(body);
        SingletonEventBus.getBus().post(request);
    }
    
    
 ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_E2EPhysicalAllocateReply(E2EPhysicalAllocateReply ev) throws InterruptedException {
        System.out.println("allocatePNF ----> handle resource pnf allocate event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocatePNF ----> reqid = " + ev.getReqid());
        System.out.println("allocatePNF ----> request deblocked");
        com.rl.extinterface.nbi.swagger.model.PNFReply replist;
        if (ev.isOutcome()) {
            replist = ev.getPnfreply();
        } else {
            replist = new com.rl.extinterface.nbi.swagger.model.PNFReply();
        }
        Response resp;
        resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        System.out.println("allocatePNF ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_E2EPhysicalTerminateReply(E2EPhysicalTerminateReply ev) throws InterruptedException {
       //TODO:
        System.out.println("terminatePNF ----> handle terminate pnfe event");
        //AsyncResponse asyncResp = suspended.take();
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminatePNF ----> reqid = " + ev.getReqid());
        System.out.println("terminatePNF ----> request deblocked");
        PNFReply rep = new PNFReply();
        
        rep = ev.getPnfList();
        Response resp;
        if (rep == null) {
            resp = Response.ok(new String(""), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(rep, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("terminatePNF ----> response ok");
        asyncResp.resume(resp);
    }
      
}