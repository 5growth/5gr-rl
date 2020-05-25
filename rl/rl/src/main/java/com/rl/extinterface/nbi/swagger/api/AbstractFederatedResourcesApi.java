package com.rl.extinterface.nbi.swagger.api;

import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.federation.Advertisement.AdvertiseE2EFederationReply;
import com.rl.events.federation.Advertisement.AdvertiseE2EFederationRequest;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2005;


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

@Path("/abstract-federated-resources")
@Api(description = "the abstract-federated-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class AbstractFederatedResourcesApi {
        
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public AbstractFederatedResourcesApi() {
        //reqid = 0;

    }

    @GET
    @ManagedAsync
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve aggregated federated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity", notes = "Retrieve federated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity", response = InlineResponse2005.class, tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2005.class),
        @ApiResponse(code = 201, message = "federated Nfvi_PoPs not found", response = Void.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void collectMtpFederatedCloudNetworkResourceInformation(@Suspended final AsyncResponse ar) {
        //return Response.ok().entity("magic!").build();
        System.out.println("collectMtpFederatedCloudNetworkResourceInformation ----> retrieve federated resources request suspended");
        System.out.println("collectMtpFederatedCloudNetworkResourceInformation ----> Calling post");
        reqid++;
        System.out.println("collectMtpFederatedCloudNetworkResourceInformation ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        AdvertiseE2EFederationRequest request = new AdvertiseE2EFederationRequest(reqid);
        SingletonEventBus.getBus().post(request);
    }
    
    
  ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_AdvertiseE2EFederationReply(AdvertiseE2EFederationReply ev) throws InterruptedException {
        System.out.println("AdvertiseE2EFederationReply ----> handle advertise federated resources");
        //send response
        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("AdvertiseE2EFederationReply ----> reqid = " + ev.getReqid());
        System.out.println("AdvertiseE2EFederationReply ----> request deblocked");
        Response resp;
        
        //resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
        if (ev.getResponse().getNfviPops().isEmpty() ) {
               String message="Federated Nfvi-PoPs not found";
        resp=Response.status(Response.Status.NOT_FOUND).entity(message).build();
            }else if (ev.getResponse().getLogicalLinkInterNfviPops().isEmpty()) {
               String message="Logical-links connecting federated Nfvi-PoPs not found";
        resp=Response.status(Response.Status.NOT_FOUND).entity(message).build();
            } else {
            resp = Response.ok(ev.getResponse(), MediaType.APPLICATION_JSON).build();
            }
       
        System.out.println("federated resources advertisement ----> response ok");
        asyncResp.resume(resp);
    }   
}
