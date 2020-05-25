package com.rl.extinterface.nbi.swagger.api;
//TO BE REMOVED
import com.google.common.eventbus.Subscribe;
import com.rl.SingletonEventBus;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateDbReply;
import com.rl.events.resourcemanagement.NetworkAllocation.IntraPoPAllocateDbRequest;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateDbReply;
import com.rl.events.resourcemanagement.NetworkTermination.IntraPoPTerminateDbRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.Filter;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.QueryNetworkCapacityRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.math.BigDecimal;
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

@Path("/network_resources")
@Api(description = "the network_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class NetworkResourcesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public NetworkResourcesApi() {
        //reqid = 0;

    }

    @GET
    @Path("/free_vlan")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve free vlan tag from VIM domain", notes = "Retrieve free vlan tag from VIM domain", response = BigDecimal.class, responseContainer = "List", tags={ "VIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = BigDecimal.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response freeVlan() {
        return Response.ok().entity("magic!").build();
    }
    
    @GET
    @Path("/nfvi-pop-network-information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags={ "VIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response queryNFVIPoPNetworkInformation(@Valid Filter nfviPopNetworkInformationRequest) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/capacities")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags={ "VIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response queryNetworkCapacity(@Valid QueryNetworkCapacityRequest queryNetworkCapacityRequest) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = AllocateNetworkResult.class, tags={ "VIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = AllocateNetworkResult.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "network already added", response = Void.class)
    })
    public void vIMallocateNetwork(@Suspended final AsyncResponse ar, @Valid AllocateNetworkRequest params) {
        //return Response.ok().entity("magic!").build();
        System.out.println("vIMallocateNetwork ----> allocate network intrapop request suspended");
        System.out.println("vIMallocateNetwork ----> Calling post");
        reqid++;
        System.out.println("vIMallocateNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
        
        String servid = "";
        long nfvipop = -1;
        List<MetaDataInner> metadata = params.getMetadata();
        for (int i=0; i < metadata.size(); i++) {
            if (metadata.get(i).getKey().compareTo("ServiceId") == 0) {
                servid = metadata.get(i).getValue();
            }
            if (metadata.get(i).getKey().compareTo("AbstractNfviPoPId") == 0) {
                nfvipop = Long.parseLong(metadata.get(i).getValue());
            }
        }         
        
        IntraPoPAllocateDbRequest request = new IntraPoPAllocateDbRequest(reqid, servid, nfvipop, params);
        SingletonEventBus.getBus().post(request);
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetwork.class, responseContainer = "List", tags={ "VIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual network resource(s) matching the filter. The cardinality can be 0 if no matching network resources exist.", response = VirtualNetwork.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response vIMqueryNetworks(@Valid Filter networkQueryFilter) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @ManagedAsync
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "VIMNetworkResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised network resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public void vIMterminateNetworks(@Suspended final AsyncResponse ar, @QueryParam("networkResourceId") @NotNull   @ApiParam("Identifier of the virtualised network resource(s) to be terminated.")  List<String> networkResourceId) {
        //return Response.ok().entity("magic!").build();
        System.out.println("vIMallocateNetwork ----> terminate network intrapop request suspended");
        System.out.println("vIMallocateNetwork ----> Calling post");
        reqid++;
        System.out.println("vIMallocateNetwork ----> reqid = " + Long.toString(reqid));
        suspended.put(Long.toString(reqid), ar);
   
        
        IntraPoPTerminateDbRequest request = new IntraPoPTerminateDbRequest(reqid, networkResourceId);
        SingletonEventBus.getBus().post(request);
    }
    
    ////////////////Guava Event Handlers////////////////////////////////////////
    //Subscribe Event
    @Subscribe
    public void handle_IntraPoPAllocateDbReply(IntraPoPAllocateDbReply ev) throws InterruptedException {
        System.out.println("IntraPoPAllocate ----> handle resource zone compute event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("IntraPoPAllocate ----> reqid = " + ev.getReqid());
        System.out.println("IntraPoPAllocate ----> request deblocked");
        com.rl.extinterface.nbi.swagger.model.VirtualCompute replist;
//        if (ev.isOutcome()) {
//            replist = ev.getComputereply();
//        } else {
//            replist = new com.mtp.extinterface.nbi.swagger.model.VirtualCompute();
//        }
        
        Response resp;
        resp = Response.ok(ev.getIntrapoprep(), MediaType.APPLICATION_JSON).build();
        System.out.println("IntraPoPAllocate ----> response ok");
        asyncResp.resume(resp);
    }

    //Subscribe Event
    @Subscribe
    public void handle_IntraPoPTerminateDbReply(IntraPoPTerminateDbReply ev) throws InterruptedException {
        System.out.println("IntraPoPTerminate ----> handle IntraPoPTerminateDbReply event");
        //AsyncResponse asyncResp = suspended.take();

        AsyncResponse asyncResp = suspended.remove(Long.toString(ev.getReqid()));
        System.out.println("IntraPoPTerminate ----> reqid = " + ev.getReqid());
        System.out.println("IntraPoPTerminate ----> request deblocked");
        List<String> replist = new ArrayList();
        
        replist = ev.getNetreslist();
        Response resp;
        if (replist.isEmpty()) {
            resp = Response.ok(new String(""), MediaType.APPLICATION_JSON).build();
        } else {
            resp = Response.ok(replist, MediaType.APPLICATION_JSON).build();
        }
        System.out.println("IntraPoPTerminate ----> response ok");
        asyncResp.resume(resp);
    }
    
}
