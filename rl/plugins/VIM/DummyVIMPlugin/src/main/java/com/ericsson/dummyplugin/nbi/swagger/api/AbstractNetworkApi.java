package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2004;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;




@Path("/abstract-network")
@Api(description = "the abstract-network API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class AbstractNetworkApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve aggregated WAN Connectivity", notes = "Retrieve aggregated WAN Connectivity", response = InlineResponse2004.class, tags={ "WIMNetworkResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2004.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response collectWimAbstractedInformation() {
        return Response.ok().entity("magic!").build();
    }
}
