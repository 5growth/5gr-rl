package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.AllocateParameters;
import io.swagger.model.CapacityInformation;
import io.swagger.model.Filter;
import java.util.List;
import io.swagger.model.NetworkIds;
import io.swagger.model.NfviPop;
import io.swagger.model.QueryNetworkCapacityRequest;
import io.swagger.model.VirtualNetwork;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")
public abstract class NetworkResourcesApiService {
    public abstract Response allocateNetwork(AllocateParameters params,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryNFVIPoPNetworkInformation(Filter nfviPopNetworkInformationRequest,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryNetworkCapacity(QueryNetworkCapacityRequest queryNetworkCapacityRequest,SecurityContext securityContext) throws NotFoundException;
    public abstract Response queryNetworks(Filter networkQueryFilter,SecurityContext securityContext) throws NotFoundException;
    public abstract Response terminateNetworks(List<NetworkIds> ids,SecurityContext securityContext) throws NotFoundException;
}
