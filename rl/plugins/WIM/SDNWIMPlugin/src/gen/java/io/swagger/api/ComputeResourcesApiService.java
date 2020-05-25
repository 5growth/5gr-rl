package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public abstract class ComputeResourcesApiService {
    public abstract Response allocateCompute(AllocateComputeRequest body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryComputeCapacity(QueryComputeCapacityRequest queryComputeCapacityRequest,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryComputeInformation(Filter informationQueryFilter,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryComputeResourceZone(Filter queryComputeResourceZoneRequest,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryNFVIPoPComputeInformation(Filter nfviPopComputeInformationRequest,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryResources(Filter computeQueryFilter,SecurityContext securityContext) throws NotFoundException;
    public abstract Response terminateResources(List<ComputeIds> ids,SecurityContext securityContext) throws NotFoundException;
}
