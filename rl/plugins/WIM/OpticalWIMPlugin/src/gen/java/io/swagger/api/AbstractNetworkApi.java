package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.AbstractNetworkApiService;
import io.swagger.api.factories.AbstractNetworkApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.InlineResponse200;

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

@Path("/abstract-network")


@io.swagger.annotations.Api(description = "the abstract-network API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-29T11:25:16.593Z")
public class AbstractNetworkApi  {
    private final AbstractNetworkApiService delegate;

    public AbstractNetworkApi(@Context ServletConfig servletContext) {
        AbstractNetworkApiService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("AbstractNetworkApi.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (AbstractNetworkApiService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (delegate == null) {
            delegate = AbstractNetworkApiServiceFactory.getAbstractNetworkApi();
        }

        this.delegate = delegate;
    }

    @GET


    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve aggregated WAN Connectivity", notes = "Retrieve aggregated WAN Connectivity", response = InlineResponse200.class, tags={ "WIMNetworkResources", })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation", response = InlineResponse200.class),

            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad request", response = Void.class),

            @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Void.class),

            @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Void.class) })
    public Response collectWimAbstractedInformation(@Context SecurityContext securityContext)
           throws NotFoundException {
        return delegate.collectWimAbstractedInformation(securityContext);
    }
}