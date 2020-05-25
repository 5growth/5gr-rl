package com.rl.extinterface.nbi.swagger.api;

import com.rl.extinterface.nbi.swagger.model.InlineResponse2003;
import com.rl.extinterface.nbi.swagger.model.MFRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.Valid;

@Path("/mfs")
@Api(description = "the mfs API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T12:46:56.347Z")
public class MfsApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve MF list supported by radio domain", notes = "Retrieve MF(MO) of Radio PoP", response = InlineResponse2003.class, tags={ "RadioResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2003.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response collectRadioMflist() {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Produces({ "application/json" })
    @ApiOperation(value = "delete MF status supported by radio domain", notes = "Delete MF of Radio PoP to start or stop", response = MFRequest.class, tags={ "RadioResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = MFRequest.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response deleteRadioMflist(@Valid MFRequest body) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Produces({ "application/json" })
    @ApiOperation(value = "Set MF status supported by radio domain", notes = "Set MF of Radio PoP to start or stop", response = MFRequest.class, tags={ "RadioResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = MFRequest.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response setRadioMflist(@Valid MFRequest body) {
        return Response.ok().entity("magic!").build();
    }
}
