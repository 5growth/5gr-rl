package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.ComputeResourcesApiService;
import io.swagger.api.factories.ComputeResourcesApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.AllocateComputeRequest;
import io.swagger.model.CapacityInformation;
import io.swagger.model.ComputeIds;
import io.swagger.model.Filter;
import java.util.List;
import io.swagger.model.NfviPop;
import io.swagger.model.QueryComputeCapacityRequest;
import io.swagger.model.ResourceZone;
import io.swagger.model.VirtualCompute;
import io.swagger.model.VirtualComputeResourceInformation;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/compute-resources")


@io.swagger.annotations.Api(description = "the compute-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class ComputeResourcesApi  {
   private final ComputeResourcesApiService delegate;

   public ComputeResourcesApi(@Context ServletConfig servletContext) {
      ComputeResourcesApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("ComputeResourcesApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (ComputeResourcesApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = ComputeResourcesApiServiceFactory.getComputeResourcesApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = VirtualCompute.class, tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Element containing information of the newly instantiated virtualised compute resource.", response = VirtualCompute.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 409, message = "software image already added", response = Void.class) })
    public Response allocateCompute(@ApiParam(value = "" ,required=true) AllocateComputeRequest body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.allocateCompute(body,securityContext);
    }
    @GET
    @Path("/capacities")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeCapacity(@ApiParam(value = "" ,required=true) QueryComputeCapacityRequest queryComputeCapacityRequest
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryComputeCapacity(queryComputeCapacityRequest,securityContext);
    }
    @GET
    @Path("/information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = VirtualComputeResourceInformation.class, responseContainer = "List", tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Virtualised compute resource information in the VIM that satisfies the query condition.", response = VirtualComputeResourceInformation.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeInformation(@ApiParam(value = "Filter defining the information of consumable virtualised resources on which the query applies." ,required=true) Filter informationQueryFilter
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryComputeInformation(informationQueryFilter,securityContext);
    }
    @GET
    @Path("/resource-zones")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = ResourceZone.class, responseContainer = "List", tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The filtered information that has been retrieved about the Resource Zone. The cardinality can be 0 if no matching information exist.", response = ResourceZone.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryComputeResourceZone(@ApiParam(value = "Input filter for selecting information to query. For instance, based on identifier of the Resource Zone, identifier of the NFVI-PoP, properties of the Resource Zone, or other meta-data." ,required=true) Filter queryComputeResourceZoneRequest
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryComputeResourceZone(queryComputeResourceZoneRequest,securityContext);
    }
    @GET
    @Path("/nfvi-pop-compute-information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNFVIPoPComputeInformation(@ApiParam(value = "Input filter for selecting information to query." ,required=true) Filter nfviPopComputeInformationRequest
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryNFVIPoPComputeInformation(nfviPopComputeInformationRequest,securityContext);
    }
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = VirtualCompute.class, responseContainer = "List", tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Element containing information about the virtual compute resource(s) matching the filter. The cardinality can be 0 if no matching compute resources exist.", response = VirtualCompute.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryResources(@ApiParam(value = "Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers." ,required=true) Filter computeQueryFilter
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryResources(computeQueryFilter,securityContext);
    }
    @DELETE
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = ComputeIds.class, responseContainer = "List", tags={ "virtualisedComputeResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Identifier(s) of the virtualised compute resource(s) successfully terminated.", response = ComputeIds.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateResources(@ApiParam(value = "Identifier(s) of the virtualised compute resource(s) to be terminated." ,required=true) List<ComputeIds> ids
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.terminateResources(ids,securityContext);
    }
}
