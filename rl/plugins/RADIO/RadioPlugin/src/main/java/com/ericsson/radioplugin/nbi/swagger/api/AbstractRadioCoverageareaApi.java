package com.ericsson.radioplugin.nbi.swagger.api;

import com.ericsson.radioplugin.SingletonEventBus;
import com.ericsson.radioplugin.events.abstraction.RadioAbstractionReply;
import com.ericsson.radioplugin.events.abstraction.RadioAbstractionRequest;
import com.ericsson.radioplugin.nbi.swagger.model.InlineResponse2001;
import com.google.common.eventbus.Subscribe;

import javax.ws.rs.*;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;

@Path("/abstract-radio-coveragearea")
@Api(description = "the abstract-radio-coveragearea API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-28T11:44:09.884Z")
public class AbstractRadioCoverageareaApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public AbstractRadioCoverageareaApi() {
        //reqid = 0;

    }
    
    @GET
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve Radio Coverage Area info for Radio PoP capable", notes = "Retrieve Radio Coverage Area info for Radio PoP capable", response = InlineResponse2001.class, tags={ "RadioResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2001.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void collectRadioCoverageareaInformation(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("collectRadioCoverageareaInformation ----> query Wradio coverage areas suspended");
        System.out.println("collectRadioCoverageareaInformation ----> Calling post");
        reqid++;
        System.out.println("collectRadioCoverageareaInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        RadioAbstractionRequest request = new RadioAbstractionRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_RadioAbstractionReply(RadioAbstractionReply ev) throws InterruptedException {
        System.out.println("RadioAbstractionReply ----> handle radio coverage area info allocate event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReq()));
        System.out.println("RadioAbstractionReply ----> reqid = " + ev.getReq());
        System.out.println("RadioAbstractionReply ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResp(), MediaType.APPLICATION_JSON).build();
        System.out.println("RadioAbstractionReply ----> response ok");
        asyncResp.resume(resp);
    }
}
