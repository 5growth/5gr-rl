package com.ericsson.dummyplugin.nbi.swagger.api;



import com.ericsson.dummyplugin.nbi.swagger.model.InlineResponse2005;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;


@Path("/abstract-federated-resources")
@Api(description = "the abstract-federated-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T16:03:20.445Z")
public class AbstractFederatedResourcesApi {
    
    public AbstractFederatedResourcesApi() {
        //reqid = 0;

    }

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation", notes = "Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation", response = InlineResponse2005.class, tags={ "SOInterface" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2005.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response collectMtpFederatedCloudNetworkResourceInformation() {
        return Response.ok().entity("magic!").build();
    }
}
