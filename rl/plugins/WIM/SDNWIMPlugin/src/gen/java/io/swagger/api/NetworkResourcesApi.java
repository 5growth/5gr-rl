package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.NetworkResourcesApiService;
import io.swagger.api.factories.NetworkResourcesApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.AllocateParameters;
import io.swagger.model.CapacityInformation;
import io.swagger.model.Filter;
import java.util.List;
import io.swagger.model.NetworkIds;
import io.swagger.model.NfviPop;
import io.swagger.model.QueryNetworkCapacityRequest;
import io.swagger.model.VirtualNetwork;

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

@Path("/network-resources")


@io.swagger.annotations.Api(description = "the network-resources API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class NetworkResourcesApi  {
   private final NetworkResourcesApiService delegate;

   public NetworkResourcesApi(@Context ServletConfig servletContext) {
      NetworkResourcesApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("NetworkResourcesApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (NetworkResourcesApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = NetworkResourcesApiServiceFactory.getNetworkResourcesApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = String.class, tags={ "virtualisedNetworkResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "Identifier of the created Compute Flavour.", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 409, message = "flavour already added", response = Void.class) })
    public Response allocateNetwork(@ApiParam(value = "" ,required=true) AllocateParameters params
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.allocateNetwork(params,securityContext);
    }
    @GET
    @Path("/nfvi-pop-network-information")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = NfviPop.class, responseContainer = "List", tags={ "virtualisedNetworkResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The filtered information that has been retrieved. The cardinality can be 0 if no matching information exist.", response = NfviPop.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNFVIPoPNetworkInformation(@ApiParam(value = "Input filter for selecting information to query." ,required=true) Filter nfviPopNetworkInformationRequest
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryNFVIPoPNetworkInformation(nfviPopNetworkInformationRequest,securityContext);
    }
    @GET
    @Path("/capacities")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = CapacityInformation.class, tags={ "virtualisedNetworkResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The capacity during the requested time period. The scope is according to parameter zoneId of the request during the time interval.", response = CapacityInformation.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworkCapacity(@ApiParam(value = "" ,required=true) QueryNetworkCapacityRequest queryNetworkCapacityRequest
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryNetworkCapacity(queryNetworkCapacityRequest,securityContext);
    }
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = VirtualNetwork.class, responseContainer = "List", tags={ "virtualisedNetworkResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Element containing information about the virtual network resource(s) matching the filter. The cardinality can be 0 if no matching network resources exist.", response = VirtualNetwork.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response queryNetworks(@ApiParam(value = "Query filter based on e.g. name, identifier, meta-data information or status information, expressing the type of information to be retrieved. It can also be used to specify one or more resources to be queried by providing their identifiers." ,required=true) Filter networkQueryFilter
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.queryNetworks(networkQueryFilter,securityContext);
    }
    @DELETE
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = NetworkIds.class, responseContainer = "List", tags={ "virtualisedNetworkResources", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Identifier(s) of the virtualised network resource(s) successfully terminated.", response = NetworkIds.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response terminateNetworks(@ApiParam(value = "Identifier of the virtualised network resource(s) to be terminated." ,required=true) List<NetworkIds> ids
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.terminateNetworks(ids,securityContext);
    }
}
