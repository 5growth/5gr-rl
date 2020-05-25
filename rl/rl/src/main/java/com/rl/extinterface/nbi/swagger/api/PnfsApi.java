package com.rl.extinterface.nbi.swagger.api;

import com.rl.extinterface.nbi.swagger.model.InlineResponse2002;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.Valid;

@Path("/pnfs")
@Api(description = "the pnfs API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2020-04-08T12:46:56.347Z")
public class PnfsApi {

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve pnf list supported by radio domain", notes = "Retrieve PNF of Radio PoP", response = InlineResponse2002.class, tags={ "RadioResources", "VIMComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = InlineResponse2002.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response collectRadioPnflist() {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete pnf in radio/compute domain", notes = "Delete PNF of Radio/NFVI PoP", response = PNFReply.class, tags={ "RadioResources", "VIMComputeResources",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = PNFReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response deleteAbstractPn(@Valid PNFReply body) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Produces({ "application/json" })
    @ApiOperation(value = "Set pnf status supported by radio/compute domain", notes = "Set PNF of Radio/NFVI PoP to start or stop", response = PNFReply.class, tags={ "RadioResources", "VIMComputeResources" })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Element containing information of the physical network function.", response = PNFReply.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class)
    })
    public Response setNfviPnflist(@Valid PNFRequest body) {
        return Response.ok().entity("magic!").build();
    }
}
