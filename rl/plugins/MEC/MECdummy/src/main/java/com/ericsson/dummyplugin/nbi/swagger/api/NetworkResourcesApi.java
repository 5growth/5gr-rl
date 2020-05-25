package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.AllocateNetworkResult;
import com.ericsson.dummyplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.dummyplugin.nbi.swagger.model.Filter;
import com.ericsson.dummyplugin.nbi.swagger.model.NfviPop;
import com.ericsson.dummyplugin.nbi.swagger.model.QueryNetworkCapacityRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualNetwork;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/network_resources")
@Api(description = "the network_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-28T11:44:09.884Z")
public class NetworkResourcesApi {

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
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = AllocateNetworkResult.class, tags={ "VIMNetworkResources", "SOInterface",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = AllocateNetworkResult.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "network already added", response = Void.class)
    })
    public Response vIMallocateNetwork(@Valid AllocateNetworkRequest params) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetwork.class, responseContainer = "List", tags={ "VIMNetworkResources", "SOInterface",  })
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
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "VIMNetworkResources", "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised network resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response vIMterminateNetworks(@QueryParam("networkResourceId") @NotNull   @ApiParam("Identifier of the virtualised network resource(s) to be terminated.")  List<String> networkResourceId) {
        return Response.ok().entity("magic!").build();
    }
}
