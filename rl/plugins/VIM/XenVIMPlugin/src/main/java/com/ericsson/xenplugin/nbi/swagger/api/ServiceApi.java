package com.ericsson.xenplugin.nbi.swagger.api;

import com.ericsson.xenplugin.nbi.swagger.model.InlineResponse200;
import com.ericsson.xenplugin.nbi.swagger.model.MECTrafficServiceCreationRequest;
import com.ericsson.xenplugin.nbi.swagger.model.MECTrafficServiceCreationResponse;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.Valid;

@Path("/service")
@Api(description = "the service API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-09-18T08:35:55.687Z")
public class ServiceApi {

    @GET
    @Path("/regions")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of all covered regions.", notes = "Retrieve a list of MEC regions, reporting their identifiers and location information.", response = InlineResponse200.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of regions.", response = InlineResponse200.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class)
    })
    public Response serviceRegionsGet() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/regions/{RegionId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of MEC service requests for the given region.", notes = "Retrieve a list of MEC service requests for the given region.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List", tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of MEC service requests.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Region not found.", response = Void.class)
    })
    public Response serviceRegionsRegionIdGet(@PathParam("RegionId") @ApiParam("Region identifier.") String regionId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/requests")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of MEC service requests.", notes = "Retrieve a list of MEC service requests.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List", tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of MEC service requests.", response = MECTrafficServiceCreationRequest.class, responseContainer = "List")
    })
    public Response serviceRequestsGet() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/requests")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create MEC service rules.", notes = "Create MEC service rules.", response = MECTrafficServiceCreationResponse.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "MEC service created successfully.", response = MECTrafficServiceCreationResponse.class),
        @ApiResponse(code = 401, message = "MEC service creation failed.", response = Void.class)
    })
    public Response serviceRequestsPost(@Valid MECTrafficServiceCreationRequest mecTrafficServiceCreationRequest) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/requests/{RequestId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete service.", notes = "Delete service identified by the given request ID.", response = Void.class, tags={ "MECResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "MEC service deleted successfully.", response = Void.class),
        @ApiResponse(code = 404, message = "Service not found.", response = Void.class)
    })
    public Response serviceRequestsRequestIdDelete(@PathParam("RequestId") @ApiParam("Request identifier.") String requestId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/requests/{RequestId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve information about a MEC service request.", notes = "Retrieve information about a MEC service request.", response = MECTrafficServiceCreationRequest.class, tags={ "MECResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "MEC service request information.", response = MECTrafficServiceCreationRequest.class),
        @ApiResponse(code = 404, message = "Service not found.", response = Void.class)
    })
    public Response serviceRequestsRequestIdGet(@PathParam("RequestId") @ApiParam("Request identifier.") String requestId) {
        return Response.ok().entity("magic!").build();
    }
}
