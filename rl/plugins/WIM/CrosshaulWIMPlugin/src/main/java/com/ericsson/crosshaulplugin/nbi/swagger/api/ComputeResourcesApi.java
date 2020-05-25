package com.ericsson.crosshaulplugin.nbi.swagger.api;

import com.ericsson.crosshaulplugin.nbi.swagger.model.AllocateComputeRequest;
import com.ericsson.crosshaulplugin.nbi.swagger.model.CapacityInformation;
import com.ericsson.crosshaulplugin.nbi.swagger.model.NfviPop;
import com.ericsson.crosshaulplugin.nbi.swagger.model.ResourceZone;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualCompute;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualComputeFlavour;
import com.ericsson.crosshaulplugin.nbi.swagger.model.VirtualComputeResourceInformation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/compute_resources")
@Api(description = "the compute_resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-28T16:34:24.071Z")
public class ComputeResourcesApi {

    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualCompute.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class) })
    public Response allocateCompute(@Valid AllocateComputeRequest body) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/flavours")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = String.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class) })
    public Response createFlavour(@Valid VirtualComputeFlavour flavour) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/flavours/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = Void.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "No output parameters", response = Void.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response deleteFlavours(@PathParam("id") @ApiParam("Identifier of the Compute Flavour to be deleted.") String id) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/capacities")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeCapacity(@QueryParam("ComputeResourceTypeId") @NotNull    String computeResourceTypeId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeResourceInformation.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Virtualised compute resource information in the VIM that satisfies the query condition.", response = VirtualComputeResourceInformation.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeInformation(@QueryParam("zoneId") @NotNull   @ApiParam("Filter defining the information of consumable virtualised resources on which the query applies.")  String zoneId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/resource_zones")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ResourceZone.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved about the Resource Zone. The cardinality can be 0 if no matching information exist.", response = ResourceZone.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeResourceZone() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/flavours")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualComputeFlavour.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualComputeFlavour.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryFlavors() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/nfvi_pop_compute_information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNFVIPoPComputeInformation() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = VirtualCompute.class, responseContainer = "List", tags={ "virtualisedComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualCompute.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryResources() {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "virtualisedComputeResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier(s) of the virtualised compute resource(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateAbstractResources(@QueryParam("computeId") @NotNull   @ApiParam("Identifier(s) of the abstract compute resource(s) to be terminated.")  List<String> computeId) {
        return Response.ok().entity("magic!").build();
    }
}
