package com.ericsson.xenplugin.nbi.swagger.api;



import com.ericsson.xenplugin.nbi.swagger.model.Filter;
import com.ericsson.xenplugin.nbi.swagger.model.VirtualNetworkResourceInformation;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;

@Path("/information")
@Api(description = "the information API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-27T12:43:31.935Z")
public class InformationApi {
    
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public InformationApi() {
        //reqid = 0;

    }
    
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
        return Response.ok().entity("Not Implemented!").build();
    }
}
