package com.ericsson.dummyplugin.nbi.swagger.api;



import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;


@Path("/healthz")
@Api(description = "the healthz API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T16:03:20.445Z")
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
