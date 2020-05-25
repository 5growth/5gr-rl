package com.ericsson.crosshaulplugin.nbi.swagger.api;

import com.ericsson.crosshaulplugin.nbi.swagger.model.Filter;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualNetworkResourceInformation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/information")
@Api(description = "the information API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T16:34:24.071Z")
public class InformationApi {

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetworkResourceInformation.class, responseContainer = "List", tags={ "virtualisedNetworkResourcesInformation" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Virtualised network resource information in the VIM that satisfies the query condition.", response = VirtualNetworkResourceInformation.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkInformation(@Valid Filter informationQueryFilter) {
        return Response.ok().entity("magic!").build();
    }
}
