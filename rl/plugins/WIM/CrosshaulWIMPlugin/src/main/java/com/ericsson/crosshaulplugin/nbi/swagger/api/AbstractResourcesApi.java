package com.ericsson.crosshaulplugin.nbi.swagger.api;

import com.ericsson.crosshaulplugin.nbi.swagger.model.InlineResponse2001;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/abstract-resources")
@Api(description = "the abstract-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T16:34:24.071Z")
public class AbstractResourcesApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity", notes = "Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity", response = InlineResponse2001.class, tags={ "abstractResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2001.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response collectMtpCloudNetworkResourceInformation() {
        return Response.ok().entity("magic!").build();
    }
}
