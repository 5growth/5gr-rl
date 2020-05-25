package com.sssa.wimplugin.nbi.swagger.api;

import com.sssa.wimplugin.nbi.swagger.model.DeleteInterNfviPopConnectivityRequest;
import com.sssa.wimplugin.nbi.swagger.model.InlineResponse201;
import com.sssa.wimplugin.nbi.swagger.model.InterNfviPopConnectivityRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/abstract-network-resources")
@Api(description = "the abstract-network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T16:34:24.071Z")
public class AbstractNetworkResourcesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create inter-NFVI-PoP connectivity", notes = "", response = InlineResponse201.class, tags={ "abstractResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful operation", response = InlineResponse201.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response createInterNfviPoPConnectivity(@Valid InterNfviPopConnectivityRequest body) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Consumes({ "application/json" })
    @ApiOperation(value = "Delete inter-NFVI-PoP connectivity", notes = "", response = Void.class, tags={ "abstractResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response deleteInterNfviPoPConnectivity(@Valid DeleteInterNfviPopConnectivityRequest body) {
        return Response.ok().entity("magic!").build();
    }
}
