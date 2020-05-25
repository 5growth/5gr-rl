package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.nbi.swagger.model.AllocateComputeRequest;
import com.ericsson.dummyplugin.nbi.swagger.model.VirtualCompute;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/abstract-compute-resources")
@Api(description = "the abstract-compute-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class AbstractComputeResourcesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Allocate abstract compute resources", notes = "", response = VirtualCompute.class, tags={ "SOInterface",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated abstracted virtualised compute resource.", response = VirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)
    })
    public Response allocateAbstractCompute(@Valid AllocateComputeRequest body) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Terminate abstract compute resources", notes = "", response = String.class, responseContainer = "List", tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised compute resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response terminateResources(@QueryParam("computeId") @NotNull   @ApiParam("Identifier(s) of the virtualised compute resource(s) to be terminated.")  List<String> computeId) {
        return Response.ok().entity("magic!").build();
    }
}
