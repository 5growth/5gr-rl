package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.SingletonEventBus;
import com.ericsson.dummyplugin.events.abstraction.RadioCoverageAreaReply;
import com.ericsson.dummyplugin.events.abstraction.RadioCoverageAreaRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2001;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class AbstractRadioCoverageareaApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    public AbstractRadioCoverageareaApi() {
        
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
        //return Response.ok().entity("Not Implemented!").build();
        System.out.println("queryCoverageArea ----> query coverage area suspended");
        System.out.println("queryCoverageArea ----> Calling post");
        reqid++;
        System.out.println("queryCoverageArea ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        RadioCoverageAreaRequest request = new RadioCoverageAreaRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }
    
    
             ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_RadioCoverageAreaReply(RadioCoverageAreaReply ev) throws InterruptedException {
        System.out.println("queryCoverageArea ----> handle query coverage area event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("queryCoverageArea ----> reqid = " + ev.getReqid());
        System.out.println("queryCoverageArea ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        System.out.println("queryCoverageArea ----> response ok");
        asyncResp.resume(resp);
    }
}
