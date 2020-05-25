package com.ericsson.radioplugin.nbi.swagger.api;

import com.ericsson.radioplugin.nbi.swagger.model.OperateComputeRequest;
import com.ericsson.radioplugin.nbi.swagger.model.VIMVirtualCompute;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/compute_operate_resources")
@Api(description = "the compute_operate_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-28T11:44:09.884Z")
public class ComputeOperateResourcesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VIMVirtualCompute.class, tags={ "VIMComputeResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VIMVirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class)
    })
    public Response operateCompute(@Valid OperateComputeRequest body) {
        return Response.ok().entity("magic!").build();
    }
}
