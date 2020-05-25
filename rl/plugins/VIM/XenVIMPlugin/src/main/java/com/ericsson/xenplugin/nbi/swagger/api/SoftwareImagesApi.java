package com.ericsson.xenplugin.nbi.swagger.api;

import com.ericsson.xenplugin.nbi.swagger.model.SoftwareImageAddQuery;
import com.ericsson.xenplugin.nbi.swagger.model.SoftwareImageInformation;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;
import javax.ws.rs.container.AsyncResponse;

@Path("/software_images")
@Api(description = "the software_images API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2018-11-27T12:43:31.935Z")
public class SoftwareImagesApi {
    private static Map<String, AsyncResponse> suspended = new ConcurrentHashMap();
    private static long reqid = 0;

    public SoftwareImagesApi() {
        //reqid = 0;

    }
    
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = SoftwareImageInformation.class, tags={ "softwareImages",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Metadata about the Software Image that has been added.", response = SoftwareImageInformation.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 409, message = "software image already added", response = Void.class) })
    public Response addSoftwareImage(@Valid SoftwareImageAddQuery body) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = String.class, tags={ "softwareImages",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The identifier of the software image successfully deleted.", response = String.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not found", response = Void.class) })
    public Response deleteSoftwareImage(@PathParam("id") @ApiParam("The identifier of the software image to be deleted.") String id) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = SoftwareImageInformation.class, tags={ "softwareImages",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The information of the software image matching the query.", response = SoftwareImageInformation.class),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response querySoftwareImage(@PathParam("id") @ApiParam("The identifier of the software image to be queried.") String id) {
        return Response.ok().entity("Not Implemented!").build();
    }

    @GET
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "", response = SoftwareImageInformation.class, responseContainer = "List", tags={ "softwareImages" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "The information of all software images matching the query.", response = SoftwareImageInformation.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = Void.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response querySoftwareImages() {
        return Response.ok().entity("Not Implemented!").build();
    }
}
