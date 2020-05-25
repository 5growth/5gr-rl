package com.ericsson.dummyplugin.nbi.swagger.api;

import com.ericsson.dummyplugin.nbi.swagger.model.OperateComputeRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.Valid;

@Path("/abstract_compute_operate_resources")
@Api(description = "the abstract_compute_operate_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-22T09:15:25.142Z")
public class AbstractComputeOperateResourcesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = OperateComputeRequest.class, tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = OperateComputeRequest.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)
    })
    public Response abstractOperateCompute(@Valid OperateComputeRequest body) {
        return Response.ok().entity("magic!").build();
    }
}
