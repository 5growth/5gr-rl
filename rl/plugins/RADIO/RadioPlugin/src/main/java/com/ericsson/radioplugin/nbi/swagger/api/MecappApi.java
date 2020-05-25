package com.ericsson.radioplugin.nbi.swagger.api;

import com.ericsson.radioplugin.nbi.swagger.model.AppD;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/mecapp")
@Api(description = "the mecapp API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-05-28T11:44:09.884Z")
public class MecappApi {

    @GET
    @Path("/onboard/{AppDId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve information about a specific application package.", notes = "Retrieve information about a specific application package.", response = AppD.class, tags={ "SOInterface",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Application package information.", response = AppD.class),
        @ApiResponse(code = 404, message = "Application package not onboarded.", response = Void.class)
    })
    public Response mecappOnboardAppDIdGet(@PathParam("AppDId") @ApiParam("Application package identifier.") String appDId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/onboard")
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve a list of onboarded application packages.", notes = "Retrieve a list of onboarded application packages.", response = AppD.class, responseContainer = "List", tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of onboarded application packages", response = AppD.class, responseContainer = "List")
    })
    public Response mecappOnboardGet() {
        return Response.ok().entity("magic!").build();
    }
}
