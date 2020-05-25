package com.rl.extinterface.nbi.swagger.api;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/healthz")
@Api(description = "the healthz API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-04-17T12:21:14.657Z")
public class HealthzApi {

    @GET
    @ApiOperation(value = "Healthcheck", notes = "<br/>", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Healthcheck", response = Void.class)
    })
    public Response healthzGet() {
        return Response.ok().entity("magic!").build();
    }
}
