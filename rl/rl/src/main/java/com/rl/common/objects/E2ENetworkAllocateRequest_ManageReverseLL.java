/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import com.rl.DbConnectionPool.DbAbstractionDatasource;
import com.rl.DbConnectionPool.DbFederationDatasource;
import com.rl.common.DatabaseDriver;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkAttributes;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkPathList;
import com.rl.extinterface.nbi.swagger.model.LogicalLinkPathListInner;
import com.rl.extinterface.nbi.swagger.model.MetaData;
import com.rl.extinterface.nbi.swagger.model.MetaDataInner;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezimbgi
 */
public class E2ENetworkAllocateRequest_ManageReverseLL {
    //private boolean federated_resources;
    private InterNfviPopConnectivityRequest body;
    private InterNfviPopConnectivityRequest modified_body;
    private boolean federated_resources;

    public E2ENetworkAllocateRequest_ManageReverseLL() {
         this.body = null;
         this.modified_body = null;
    }

    public E2ENetworkAllocateRequest_ManageReverseLL(InterNfviPopConnectivityRequest body, boolean federated_resources) {
        this.body = body;
        this.federated_resources=federated_resources;
        this.modified_body = null;
    }

    public InterNfviPopConnectivityRequest getModified_body() {
        return modified_body;
    }

    public void setModified_body(InterNfviPopConnectivityRequest modified_body) {
        this.modified_body = modified_body;
    }
    
    
    
    
    
     public void computeModifiedBody() {

         
        InterNfviPopConnectivityRequest request = new InterNfviPopConnectivityRequest(); 
          LogicalLinkPathList pathList= new LogicalLinkPathList(); 
          
          
        MetaData metadata = body.getMetaData();
         String networkType = body.getInterNfviPopNetworkType();
           String networkLayer = body.getNetworkLayer(); 
         
         for (int i = 0; i < body.getLogicalLinkPathList().size(); i++) {
 
                String llId;
                String srcRouterId="";
                String dstRouterId="";
                String srcRouterIp="";
                String dstRouterIp="";
                String delay="";
                String reverselogicalLinkId="";
                
               String dstGwIpAddress;
               String srcGwIpAddress;
               Integer localLinkId;
               Integer remoteLinkId;
               MetaData metadata_pathList;
               BigDecimal reqBandwidth;
              BigDecimal reqLatency;
              String srcVnfIpAddress="";
               String dstVnfIpAddress="";
               String  srcVnfMacAddress="";
                String dstVnfMacAddress="";
                String networkName="";
               
               
              
              
        llId = body.getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getLogicalLinkId();
        dstGwIpAddress = body.getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getDstGwIpAddress();
srcGwIpAddress = body.getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getSrcGwIpAddress();
        localLinkId = body.getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getLocalLinkId();
      remoteLinkId = body.getLogicalLinkPathList().get(i).getLogicalLinkAttributes().getRemoteLinkId();   
      reqBandwidth = body.getLogicalLinkPathList().get(i).getReqBandwidth();
        reqLatency = body.getLogicalLinkPathList().get(i).getReqLatency();
        
        
         metadata_pathList = body.getLogicalLinkPathList().get(i).getMetaData();
         
         for (int m = 0; m < body.getLogicalLinkPathList().get(i).getMetaData().size(); m++) {
            if (body.getLogicalLinkPathList().get(i).getMetaData().get(m).getKey().compareTo("srcVnfIpAddress") == 0) {
                srcVnfIpAddress = body.getLogicalLinkPathList().get(i).getMetaData().get(m).getValue();
            }
             if (body.getLogicalLinkPathList().get(i).getMetaData().get(m).getKey().compareTo("dstVnfIpAddress") == 0) {
                dstVnfIpAddress = body.getLogicalLinkPathList().get(i).getMetaData().get(m).getValue();
            }
            if (body.getLogicalLinkPathList().get(i).getMetaData().get(m).getKey().compareTo("srcVnfMacAddress") == 0) {
                srcVnfMacAddress = body.getLogicalLinkPathList().get(i).getMetaData().get(m).getValue();
            }
             if (body.getLogicalLinkPathList().get(i).getMetaData().get(m).getKey().compareTo("dstVnfMacAddress") == 0) {
                dstVnfMacAddress = body.getLogicalLinkPathList().get(i).getMetaData().get(m).getValue();
            }
               if (body.getLogicalLinkPathList().get(i).getMetaData().get(m).getKey().compareTo("networkName") == 0) {
                networkName = body.getLogicalLinkPathList().get(i).getMetaData().get(m).getValue();
            }
        }
        MetaDataInner metadata_srcVnf = new MetaDataInner();
        metadata_srcVnf.setKey("srcVnfIpAddress");
        metadata_srcVnf.setValue(dstVnfIpAddress);
        
       MetaDataInner metadata_dstVnf = new MetaDataInner();
        metadata_dstVnf.setKey("dstVnfIpAddress");
        metadata_dstVnf.setValue(srcVnfIpAddress);
        
         MetaDataInner metadata_srcMac = new MetaDataInner();
        metadata_srcMac.setKey("srcVnfMacAddress");
        metadata_srcMac.setValue(dstVnfMacAddress);
        
             MetaDataInner metadata_dstMac = new MetaDataInner();
        metadata_dstMac.setKey("dstVnfMacAddress");
        metadata_dstMac.setValue(srcVnfMacAddress);
        
             MetaDataInner metadata_networkName = new MetaDataInner();
        metadata_networkName.setKey("networkName");
        metadata_networkName.setValue(networkName);

        
        MetaData metadata_pathList_mod = new MetaData();
         metadata_pathList_mod.add(metadata_srcVnf);
           metadata_pathList_mod.add(metadata_dstVnf);
           metadata_pathList_mod.add(metadata_srcMac);
           metadata_pathList_mod.add(metadata_dstMac);
           metadata_pathList_mod.add(metadata_networkName);
           

        if (federated_resources){
        
        //RETRIEVE all networkConnectivityEndpoints from nfvipop table in FEDERATION DB
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay FROM fed_logicallink where logicalLinkId=?");
             ps.setString(1, llId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                srcRouterId = rs.getString("srcRouterId");
                dstRouterId = rs.getString("dstRouterId");
                srcRouterIp = rs.getString("srcRouterIp");
                dstRouterIp = rs.getString("dstRouterIp");
                delay = rs.getString("delay");

               
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }  
        
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId FROM fed_logicallink where srcRouterId=? AND dstRouterId=? AND srcRouterIp=? AND dstRouterIp=? AND delay=?");
             ps.setString(1, dstRouterId);
              ps.setString(2, srcRouterId);
                ps.setString(3, dstRouterIp);
                 ps.setString(4, srcRouterIp);
              ps.setString(5, delay);
              
              
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                 reverselogicalLinkId = rs.getString("logicalLinkId");
               
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }  
        
       
      
      
      
       LogicalLinkAttributes logicalLinkAttributes = new LogicalLinkAttributes();
       
       logicalLinkAttributes.setSrcGwIpAddress(dstGwIpAddress);
       logicalLinkAttributes.setDstGwIpAddress(srcGwIpAddress);
       logicalLinkAttributes.setLocalLinkId(remoteLinkId);
       logicalLinkAttributes.setRemoteLinkId(localLinkId);
        logicalLinkAttributes.setLogicalLinkId(reverselogicalLinkId);
        
      LogicalLinkPathListInner pathListInner =  new LogicalLinkPathListInner();
     pathListInner.setReqBandwidth(reqBandwidth);
     pathListInner.setReqLatency(reqLatency);
     pathListInner.setMetaData(metadata_pathList_mod);
     pathListInner.setLogicalLinkAttributes(logicalLinkAttributes);
     
     
    pathList.add(pathListInner);
        
        
        //body.getLogicalLinkPathList().add(pathListInner); 
        
        
        
        }else{
        //RETRIEVE all networkConnectivityEndpoints from nfvipop table in FEDERATION DB
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT srcRouterId,dstRouterId,srcRouterIp,dstRouterIp,delay FROM logicallink where logicalLinkId=?");
             ps.setString(1, llId);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                srcRouterId = rs.getString("srcRouterId");
                dstRouterId = rs.getString("dstRouterId");
                srcRouterIp = rs.getString("srcRouterIp");
                dstRouterIp = rs.getString("dstRouterIp");
                delay = rs.getString("delay");

               
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }  
        
        try {
            java.sql.Connection conn = DbAbstractionDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT logicalLinkId FROM logicallink where srcRouterId=? AND dstRouterId=? AND srcRouterIp=? AND dstRouterIp=? AND delay=?");
             ps.setString(1, dstRouterId);
              ps.setString(2, srcRouterId);
                ps.setString(3, dstRouterIp);
                 ps.setString(4, srcRouterIp);
              ps.setString(5, delay);
              
              
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                 reverselogicalLinkId = rs.getString("logicalLinkId");
               
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }  
        
       
      
      
      
       LogicalLinkAttributes logicalLinkAttributes = new LogicalLinkAttributes();
       
       logicalLinkAttributes.setSrcGwIpAddress(dstGwIpAddress);
       logicalLinkAttributes.setDstGwIpAddress(srcGwIpAddress);
       logicalLinkAttributes.setLocalLinkId(remoteLinkId);
       logicalLinkAttributes.setRemoteLinkId(localLinkId);
        logicalLinkAttributes.setLogicalLinkId(reverselogicalLinkId);
        
      LogicalLinkPathListInner pathListInner =  new LogicalLinkPathListInner();
     pathListInner.setReqBandwidth(reqBandwidth);
     pathListInner.setReqLatency(reqLatency);
     pathListInner.setMetaData(metadata_pathList_mod);
     pathListInner.setLogicalLinkAttributes(logicalLinkAttributes);
     
     
    pathList.add(pathListInner);
   // body.getLogicalLinkPathList().add(pathListInner);    
    
        }
        
        
    }
      
         
       for (int m = 0; m < pathList.size(); m++) {
           body.getLogicalLinkPathList().add(pathList.get(m));
       }
     
      
      
           System.out.println("Body modified!!!");
      
         
    
     }
   
     
     
     
     
     
     
     
     
     
     
}
