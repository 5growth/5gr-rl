package com.rl.extinterface.nbi.swagger.api;
//TO BE REMOVED
import com.rl.extinterface.nbi.swagger.model.InlineResponse2001;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/abstract-radio-coveragearea")
@Api(description = "the abstract-radio-coveragearea API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class AbstractRadioCoverageareaApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve Radio Coverage Area info for Radio PoP capable", notes = "Retrieve Radio Coverage Area info for Radio PoP capable", response = InlineResponse2001.class, tags={ "RadioResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2001.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response collectRadioCoverageareaInformation() {
        return Response.ok().entity("magic!").build();
    }
}
