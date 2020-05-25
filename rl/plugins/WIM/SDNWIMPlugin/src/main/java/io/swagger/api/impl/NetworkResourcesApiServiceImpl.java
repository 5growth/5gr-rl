package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")

public class NetworkResourcesApiServiceImpl extends NetworkResourcesApiService {
    
    @Override
    public Response allocateNetwork(AllocateParameters params, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic9!")).build();
    }
    
    
    @Override
    public Response queryNFVIPoPNetworkInformation(Filter nfviPopNetworkInformationRequest, SecurityContext securityContext) throws NotFoundException {
        
        System.out.println("Arrived NFVIPoPNetworkInformation Query");
        
        JSONArray listNFVPop = new JSONArray();
        
        JSONObject pop = new JSONObject();
        pop.put("geograficalLocationInfo", "1");//zoneId = 1
        pop.put("wimId", "SSSA-Wim");
        pop.put("networkConnectivityEndPoint", "string");
        pop.put("nfviPopId", "PoP5");
        
        listNFVPop.add(pop);
        
        String jsonResponse = listNFVPop.toString();
        
        System.out.println("Response to send: "+jsonResponse);
        
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, jsonResponse)).build();
    }
    
    
    @Override
    public Response queryNetworkCapacity(QueryNetworkCapacityRequest queryNetworkCapacityRequest, SecurityContext securityContext) throws NotFoundException {
        
        
        System.out.println("Arrived NetworkCapacities Query");
        
        
        
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic11!")).build();
    }
    
    
    @Override
    public Response queryNetworks(Filter networkQueryFilter, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic12!")).build();
    }
    
    
    @Override
    public Response terminateNetworks(List<NetworkIds> ids, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic13!")).build();
    }
}
