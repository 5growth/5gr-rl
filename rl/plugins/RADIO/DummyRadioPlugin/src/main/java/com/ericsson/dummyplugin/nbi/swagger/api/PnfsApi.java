package com.ericsson.dummyplugin.nbi.swagger.api;



import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.PNFlistReply;
import com.ericsson.dummyplugin.events.abstraction.PNFlistRequest;
import com.ericsson.dummyplugin.events.allocate.PNFAllocateReply;
import com.ericsson.dummyplugin.events.allocate.PNFAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.PNFTerminateReply;
import com.ericsson.dummyplugin.events.terminate.PNFTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2002;
import com.ericsson.dummyplugin.nbi.swagger.model.PNFReply;
import com.ericsson.dummyplugin.nbi.swagger.model.PNFRequest;
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

@Path("/pnfs")
@Api(description = "the pnfs API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T16:03:20.445Z")
public class PnfsApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public PnfsApi() {
    }
    
    

    @GET
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve pnf list supported by radio domain", notes = "Retrieve PNF of Radio PoP", response = InlineResponse2002.class, tags={ "RadioResources", "VIMComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2002.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void collectRadioPnflist(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("queryPNF ----> query pnf suspended");
        System.out.println("queryPNF ----> Calling post");
        reqid++;
        System.out.println("queryPNF ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        PNFlistRequest request = new PNFlistRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }

    @DELETE
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete pnf in radio/compute domain", notes = "Delete PNF of Radio/NFVI PoP", response = PNFReply.class, tags={ "RadioResources", "VIMComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = PNFReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void deleteAbstractPn(@Suspended final AsyncResponse ar, @Valid PNFReply body) {
        //return Response.ok().entity("magic!").build();
        
        System.out.println("terminateatePNF ----> terminate pnf suspended");
        System.out.println("terminateatePNF ----> Calling post");
        reqid++;
        System.out.println("terminateatePNF ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        PNFTerminateRequest request = new PNFTerminateRequest(reqid, body);
        SingletonEventBus.getBus().post(request);
    }

    @POST
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Set pnf status supported by radio/compute domain", notes = "Set PNF of Radio/NFVI PoP to start or stop", response = PNFReply.class, tags={ "RadioResources", "VIMComputeResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = PNFReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void setNfviPnflist(@Suspended final AsyncResponse ar, @Valid PNFRequest body) {
        //return Response.ok().entity("magic!").build();
        
        System.out.println("allocatePNF ----> allocate pnf suspended");
        System.out.println("allocatePNF ----> Calling post");
        reqid++;
        System.out.println("allocatePNF ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        PNFAllocateRequest request = new PNFAllocateRequest(reqid, body);
        SingletonEventBus.getBus().post(request);
    }
    
    
                 ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_PNFlistReply(PNFlistReply ev) throws InterruptedException {
        System.out.println("queryPNF ----> handle query pnf event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReq()));
        System.out.println("queryPNF ----> reqid = " + ev.getReq());
        System.out.println("queryPNF ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("queryPNF ----> response ok");
        asyncResp.resume(resp);
    }
    
    @Subscribe
    public void handle_PNFAllocateReply(PNFAllocateReply ev) throws InterruptedException {
        System.out.println("allocatePNF ----> handle allocate pnf event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocatePNF ----> reqid = " + ev.getReqid());
        System.out.println("allocatePNF ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocatePNF ----> response ok");
        asyncResp.resume(resp);
    }
    
    @Subscribe
    public void handle_PNFTerminateReply(PNFTerminateReply ev) throws InterruptedException {
        System.out.println("terminateatePNF ----> handle terminate pnf event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateatePNF ----> reqid = " + ev.getReqid());
        System.out.println("terminateatePNF ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("terminateatePNF ----> response ok");
        asyncResp.resume(resp);
    }
    
}
