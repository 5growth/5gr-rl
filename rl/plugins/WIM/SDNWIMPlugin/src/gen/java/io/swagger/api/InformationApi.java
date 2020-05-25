package io.swagger.api;

import io.swagger.api.factories.InformationApiServiceFactory;

import io.swagger.annotations.ApiParam;

import io.swagger.model.Filter;
import io.swagger.model.VirtualNetworkResourceInformation;
import java.util.HashMap;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

import javax.ws.rs.QueryParam;

import io.swagger.api.impl.InformationApiServiceImpl;

@Path("/information")


@io.swagger.annotations.Api(description = "the information API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public class InformationApi  {
    
   private final InformationApiService delegate;
 

   public InformationApi(@Context ServletConfig servletContext) {
       
      InformationApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("InformationApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (InformationApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = InformationApiServiceFactory.getInformationApi();
      }

      this.delegate = delegate;
   }
 
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "", response = VirtualNetworkResourceInformation.class, responseContainer = "List", tags={ "virtualisedNetworkResourcesInformation", })
    
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Virtualised network resource information in the VIM that satisfies the query condition.", response = VirtualNetworkResourceInformation.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    
    public Response queryNetworkInformation(@ApiParam(value = "Filter defining the information of consumable virtualised resources on which the query applies." ,required=true) Filter informationQueryFilter
,@Context SecurityContext securityContext,@QueryParam("nfvipopid") String nfvipopid, @QueryParam("zoneid") String zoneid)
            
    throws NotFoundException {
        
        //System.out.println("Parameters: "+nfvipopid+"  "+zoneid);
        
         InformationApiServiceImpl.nfviPopId = nfvipopid;
         InformationApiServiceImpl.zoneId = zoneid;
        
        return delegate.queryNetworkInformation(informationQueryFilter,securityContext);
    }
}
