package com.rl.extinterface.nbi.swagger.api;



import com.rl.extinterface.nbi.swagger.model.AssignQoSList;
import com.rl.extinterface.nbi.swagger.model.AssignQoSRequest;
import com.rl.extinterface.nbi.swagger.model.AssignQoSResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.Valid;

@Path("/onos")
@Api(description = "the onos API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-11-16T09:15:03.371Z")
public class OnosApi {

    @POST
    @Path("/v1/dpe/slice")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = AssignQoSResponse.class, tags={ "WIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the created QoS queue.", response = AssignQoSResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class)
    })
    public Response addQoSQueue(@Valid AssignQoSRequest params) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/v1/dpe/slice/{networkId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = AssignQoSResponse.class, tags={ "WIMNetworkResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Identifier of the terminated QoS queue.", response = AssignQoSResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "flavour already added", response = Void.class)
    })
    public Response deleteQoSQueue(@PathParam("networkId") @ApiParam("Identifier of the virtualised network resource(s) to be terminated.") String networkId) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/v1/dpe/slice")
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = AssignQoSList.class, tags={ "WIMNetworkResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of the QoS queues.", response = AssignQoSList.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response queryQoSQueue() {
        return Response.ok().entity("magic!").build();
    }
}
