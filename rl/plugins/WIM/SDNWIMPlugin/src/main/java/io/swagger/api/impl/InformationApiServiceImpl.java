package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.model.*;

import io.swagger.model.Filter;
import io.swagger.model.VirtualNetworkResourceInformation;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-02T11:54:01.688+01:00")

public class InformationApiServiceImpl extends InformationApiService {
    
      
   public static String nfviPopId = "";
   public static String zoneId = "";
    
    @Override
    public Response queryNetworkInformation(Filter informationQueryFilter, SecurityContext securityContext) throws NotFoundException {
        
       
        System.out.println("Arrived NetworkResourcesInfo Query");

        System.out.println("nfvipop: "+nfviPopId);
        System.out.println("zoneId: "+zoneId);
       
        if(nfviPopId.equals("1"))//check the Id of the nfviPop
        {
        
        //returns the list of network resources
        
        //each resource describes a unidirectional link connecting two endpoints <-> virtual link id
        
        
        JSONArray NFVPopParams = new JSONArray();
        
        JSONObject params1 = new JSONObject();
        params1.put("networkResourceTypeId", "id1");//id1 is the id of one unidirectional logical link => this id will be used as a parameter for the Query Capacity
        params1.put("bandwidth", "80");
        params1.put("networkType", "gre");//vlan, gre, vxlan..
        
        NFVPopParams.add(params1);
        
        JSONObject params2 = new JSONObject();
        params2.put("networkResourceTypeId", "id2");
        params2.put("bandwidth", "100");
        params2.put("networkType", "gre"); //vlan, gre, vxlan..
        
        NFVPopParams.add(params2);
        
        String jsonResponse = NFVPopParams.toString();
        
        System.out.println("Response to send: "+jsonResponse);
        
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, jsonResponse)).build();
        
        }
        
        else
        {
        
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "False NFVIPoPId")).build();
        }
  
    }
}
