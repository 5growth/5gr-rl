package com.sssa.wimplugin.nbi.swagger.api;

import com.sssa.wimplugin.SingletonEventBus;
import com.sssa.wimplugin.events.allocate.ServiceNetworkAllocateReply;
import com.sssa.wimplugin.events.allocate.ServiceNetworkAllocateRequest;
import com.sssa.wimplugin.events.terminate.ServiceNetworkTerminateReply;
import com.sssa.wimplugin.events.terminate.ServiceNetworkTerminateRequest;
import com.sssa.wimplugin.nbi.swagger.model.AllocateParameters;
import com.sssa.wimplugin.nbi.swagger.model.AllocateReply;
import com.sssa.wimplugin.nbi.swagger.model.Filter;
import com.sssa.wimplugin.nbi.swagger.model.NetworkIds;
import com.sssa.wimplugin.nbi.swagger.model.VirtualNetwork;
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

@Path("/network-resources")
@Api(description = "the network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-12-03T14:13:12.071Z")
public class NetworkResourcesApi {
    
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;
    
    private static long virtualnetworkid = 0;

    public NetworkResourcesApi() {
        //reqid = 0;

    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_ServiceNetworkAllocateReply(ServiceNetworkAllocateReply ev) throws InterruptedException {
        
        System.out.println("allocateWimAbstractedInformation ----> handle WAN allocate event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        if(ev.getAllocated())
            
        {
        System.out.println("allocateWimAbstractedInformation ----> reqid = " + ev.getReqid());
        System.out.println("allocateWimAbstractedInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getRequest(), MediaType.APPLICATION_JSON).build();
        System.out.println("allocateWimAbstractedInformation ----> response ok");
        asyncResp.resume(resp);
        }
        else
        {
        Response resp;
        resp = Response.serverError().build();
        System.out.println("allocateWimAbstractedInformation ----> response error");
        asyncResp.resume(resp);
        }
    }

    //Subscribe Event
    @Subscribe
    public void handle_ServiceNetworkTerminateReply(ServiceNetworkTerminateReply ev) throws InterruptedException {
        System.out.println("deleteWimAbstractedInformation ----> handle WAN delete event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("deleteWimAbstractedInformation ----> reqid = " + ev.getReqid());
        System.out.println("deleteWimAbstractedInformation ----> request deblocked");
        Response resp;
        resp = Response.ok(ev.getNetlist(), MediaType.APPLICATION_JSON).build();
        System.out.println("deleteWimAbstractedInformation ----> response ok");
        asyncResp.resume(resp);
    }
    
}
