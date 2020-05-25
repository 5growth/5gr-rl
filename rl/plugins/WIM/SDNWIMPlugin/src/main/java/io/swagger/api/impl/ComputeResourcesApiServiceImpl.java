package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

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

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")


public class ComputeResourcesApiServiceImpl extends ComputeResourcesApiService {
    
    @Override
    public Response allocateCompute(AllocateComputeRequest body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic2!")).build();
    }
    
    @Override
    public Response queryComputeCapacity(QueryComputeCapacityRequest queryComputeCapacityRequest, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic3!")).build();
    }
    
    @Override
    public Response queryComputeInformation(Filter informationQueryFilter, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic4!")).build();
    }
    
    @Override
    public Response queryComputeResourceZone(Filter queryComputeResourceZoneRequest, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic5!")).build();
    }
    
    @Override
    public Response queryNFVIPoPComputeInformation(Filter nfviPopComputeInformationRequest, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic6!")).build();
    }
    
    @Override
    public Response queryResources(Filter computeQueryFilter, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic7!")).build();
    }
    
    @Override
    public Response terminateResources(List<ComputeIds> ids, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic8!")).build();
    }
}
