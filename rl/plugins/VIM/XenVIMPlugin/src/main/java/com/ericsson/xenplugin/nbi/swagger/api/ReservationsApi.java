package com.ericsson.xenplugin.nbi.swagger.api;

import com.ericsson.xenplugin.nbi.swagger.model.CreateComputeResourceReservationRequest;
import com.ericsson.xenplugin.nbi.swagger.model.CreateNetworkResourceReservationRequest;
import com.ericsson.xenplugin.nbi.swagger.model.ReservedVirtualCompute;
import com.ericsson.xenplugin.nbi.swagger.model.ReservedVirtualNetwork;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;

@Path("/reservations")
@Api(description = "the reservations API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-27T12:43:31.935Z")
public class ReservationsApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public ReservationsApi() {
        //reqid = 0;

    }
    
    @POST
    @Path("/compute_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualCompute.class, tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the reserved resource.", response = ReservedVirtualCompute.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "", response = Void.class) })
    public Response createComputeReservation(@Valid CreateComputeResourceReservationRequest body) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @POST
    @Path("/network_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualNetwork.class, tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information about the reserved resource.", response = ReservedVirtualNetwork.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "", response = Void.class) })
    public Response createNetworkReservation(@Valid CreateNetworkResourceReservationRequest body) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/compute_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualCompute.class, responseContainer = "List", tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the reserved resource. Cardinality is 0 if the query did not return any result.", response = ReservedVirtualCompute.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeReservation() {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/network_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = ReservedVirtualNetwork.class, responseContainer = "List", tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Element containing information about the reserved resource. Cardinality is 0 if the query did not return any result.", response = ReservedVirtualNetwork.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkReservation() {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/compute_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "virtualisedResourceReservation",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier of the resource reservation(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateComputeReservation(@QueryParam("reservationId") @NotNull   @ApiParam("Identifier of the resource reservation(s) to terminate.")  List<String> reservationId) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/network_resources")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, responseContainer = "List", tags={ "virtualisedResourceReservation" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Identifier of the resource reservation(s) successfully terminated.", response = String.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateNetworkReservation(@QueryParam("reservationId") @NotNull   @ApiParam("Identifier of the resource reservation(s) to terminate.")  List<String> reservationId) {
        return Response.ok().entity("Not Implemented!").build();
    }
}
