package com.ericsson.dummyplugin.nbi.swagger.api;



import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.MFlistReply;
import com.ericsson.dummyplugin.events.abstraction.MFlistRequest;
import com.ericsson.dummyplugin.events.allocate.MFAllocateReply;
import com.ericsson.dummyplugin.events.allocate.MFAllocateRequest;
import com.ericsson.dummyplugin.events.terminate.MFTerminateReply;
import com.ericsson.dummyplugin.events.terminate.MFTerminateRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2003;
import com.ericsson.dummyplugin.nbi.swagger.model.MFRequest;
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

@Path("/mfs")
@Api(description = "the mfs API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T16:03:20.445Z")
public class MfsApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public MfsApi() {
    }
    
    

    @GET
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve MF list supported by radio domain", notes = "Retrieve MF(MO) of Radio PoP", response = InlineResponse2003.class, tags={ "RadioResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2003.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void collectRadioMflist(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("queryMF ----> query mf suspended");
        System.out.println("queryMF ----> Calling post");
        reqid++;
        System.out.println("queryMF ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        MFlistRequest request = new MFlistRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }

    @DELETE
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "delete MF status supported by radio domain", notes = "Delete MF of Radio PoP to start or stop", response = MFRequest.class, tags={ "RadioResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = MFRequest.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void deleteRadioMflist(@Suspended final AsyncResponse ar, @Valid MFRequest body) {
        //return Response.ok().entity("magic!").build();
        System.out.println("terminateMF ----> terminate mf suspended");
        System.out.println("terminateMF ----> Calling post");
        reqid++;
        System.out.println("terminateMF ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        MFTerminateRequest request = new MFTerminateRequest(reqid, body);
        SingletonEventBus.getBus().post(request);
    }

    @POST
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Set MF status supported by radio domain", notes = "Set MF of Radio PoP to start or stop", response = MFRequest.class, tags={ "RadioResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = MFRequest.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void setRadioMflist(@Suspended final AsyncResponse ar, @Valid MFRequest body) {
        //return Response.ok().entity("magic!").build();
        System.out.println("allocateMF ----> allocate mf suspended");
        System.out.println("allocateMF ----> Calling post");
        reqid++;
        System.out.println("allocateMF ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        MFAllocateRequest request = new MFAllocateRequest(reqid, body);
        SingletonEventBus.getBus().post(request);
    }
    
    
    
                 ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_MFlistReply(MFlistReply ev) throws InterruptedException {
        System.out.println("queryMF ----> handle query mf event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("queryMF ----> reqid = " + ev.getReqid());
        System.out.println("queryMF ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("queryMF ----> response ok");
        asyncResp.resume(resp);
    }
    
    @Subscribe
    public void handle_MFAllocateReply(MFAllocateReply ev) throws InterruptedException {
        System.out.println("allocateMF ----> handle allocate mf event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("allocateMF ----> reqid = " + ev.getReqid());
        System.out.println("allocateMF ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocateMF ----> response ok");
        asyncResp.resume(resp);
    }
    
    @Subscribe
    public void handle_MFTerminateReply(MFTerminateReply ev) throws InterruptedException {
        System.out.println("terminateMF ----> handle terminate mf event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("terminateMF ----> reqid = " + ev.getReqid());
        System.out.println("terminateMF ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("terminateMF ----> response ok");
        asyncResp.resume(resp);
    }
    
}
