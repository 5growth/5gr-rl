package com.sssa.wimplugin.nbi.swagger.api;

import com.sssa.wimplugin.nbi.swagger.model.CreateComputeResourceQuotaRequest;
import com.sssa.wimplugin.nbi.swagger.model.CreateNetworkResourceQuotaRequest;
import com.sssa.wimplugin.nbi.swagger.model.ResourceGroupIds;
import com.sssa.wimplugin.nbi.swagger.model.VirtualComputeQuota;
import com.sssa.wimplugin.nbi.swagger.model.VirtualNetworkQuota;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/quotas")
@Api(description = "the quotas API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T16:34:24.071Z")
public class QuotasApi {

    @POST
    @Path("/compute_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeQuota.class, tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the quota resource.", response = VirtualComputeQuota.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response createComputeQuota(@Valid CreateComputeResourceQuotaRequest body) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/network_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetworkQuota.class, tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the quota resource.", response = VirtualNetworkQuota.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "", response = Void.class) })
    public Response createNetworkQuota(@Valid CreateNetworkResourceQuotaRequest body) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/compute_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeQuota.class, responseContainer = "List", tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the quota resource. The cardinality can be 0 if no matching quota exists.", response = VirtualComputeQuota.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeQuota() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/network_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualNetworkQuota.class, responseContainer = "List", tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the quota resource(s) matching the filter. The cardinality can be 0 if no matching quota exists.", response = VirtualNetworkQuota.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkQuota() {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/compute_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "virtualisedResourceQuota",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateComputeQuota(@QueryParam("resourceGroupId") @NotNull   @ApiParam()  List<String> resourceGroupId) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/network_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ResourceGroupIds.class, tags={ "virtualisedResourceQuota" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Unique identifier of the \"infrastructure resource group\", logical grouping of virtual resources assigned to a tenant within an Infrastructure Domain.", response = ResourceGroupIds.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateNetworkQuota(@QueryParam("resourceGroupId") @NotNull   @ApiParam()  List<String> resourceGroupId) {
        return Response.ok().entity("magic!").build();
    }
}
