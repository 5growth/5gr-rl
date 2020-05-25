package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDIdReply;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDIdRequest;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDReply;
import com.rl.events.abstraction.Advertisement.AdvertiseAppDRequest;
import com.rl.extinterface.nbi.swagger.model.AppD;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/mecapp")
@Api(description = "the mecapp API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class MecappApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public MecappApi() {
        //reqid = 0;

    }
    @GET
    @ManagedAsync
    @Path("/onboard/{AppDId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve information about a specific application package.", notes = "Retrieve information about a specific application package.", response = AppD.class, tags={ "SOInterface",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Application package information.", response = AppD.class),
        @ApiResponse(code = 404, message = "Application package not onboarded.", response = Void.class)
    })
    public void mecappOnboardAppDIdGet(@Suspended final AsyncResponse ar, @PathParam("AppDId") @ApiParam("Application package identifier.") String appDId) {
        //return Response.ok().entity("magic!").build();
        System.out.println("mecappOnboardAppDIdGet ----> retrieve appd ID request rsuspended");
        System.out.println("mecappOnboardAppDIdGet ----> Calling post");
        reqid++;
        System.out.println("mecappOnboardAppDIdGet ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        AdvertiseAppDIdRequest req = new  AdvertiseAppDIdRequest(reqid, appDId);
        SingletonEventBus.getBus().post(req);
    }

    @GET
    @ManagedAsync
    @Path("/onboard")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of onboarded application packages.", notes = "Retrieve a list of onboarded application packages.", response = AppD.class, responseContainer = "List", tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of onboarded application packages", response = AppD.class, responseContainer = "List")
    })
    public void mecappOnboardGet(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("mecappOnboardGet ----> retrieve appd request suspended");
        System.out.println("mecappOnboardGet ----> Calling post");
        reqid++;
        System.out.println("mecappOnboardGet ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        AdvertiseAppDRequest req = new AdvertiseAppDRequest(reqid);
        SingletonEventBus.getBus().post(req);
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_AdvertiseAppDIdReply(AdvertiseAppDIdReply ev) throws InterruptedException {
        System.out.println("AdvertiseAppDIdReply ----> handle resource appd id event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("AdvertiseAppDIdReply ----> reqid = " + ev.getReqid());
        System.out.println("AdvertiseAppDIdReply ----> request deblocked");
        
        
        Response resp;
        resp = Response.ok(ev.getAppd(), MediaType.APPLICATION_JSON).build();
        System.out.println("AdvertiseAppDIdReply ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_AdvertiseAppDReply(AdvertiseAppDReply ev) throws InterruptedException {
        System.out.println("AdvertiseAppDReply ----> handle resource appd list event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("AdvertiseAppDReply ----> reqid = " + ev.getReqid());
        System.out.println("AdvertiseAppDReply ----> request deblocked");
        
        Response resp;
        resp = Response.ok(ev.getAppdlist(), MediaType.APPLICATION_JSON).build();

        System.out.println("AdvertiseAppDReply ----> response ok");
        asyncResp.resume(resp);
    }
}
