/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rl.common.objects;

import com.rl.DbConnectionPool.DbFederationDatasource;
import com.rl.DbConnectionPool.DbServiceDatasource;
import com.rl.common.DatabaseDriver;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezimbgi
 */
public class Fed_E2ENetworkTerminateRequest_Validator {
    
//private boolean federated_resources;
    private  List<String> networkId;
   

    public Fed_E2ENetworkTerminateRequest_Validator() {
        // this.federated_resources = false;
        this.networkId = null;
    }

    public Fed_E2ENetworkTerminateRequest_Validator(List<String> networkId) {
        //this.federated_resources = false;
        this.networkId = networkId;
    }

    public boolean checkIfFederationResIsRequired() {

        boolean federation_required = true;
        String a = networkId.get(0);
        boolean federationIsRequired; 

        
        
        
       for (int i = 0; i < networkId.size(); i++) {

        //RETRIEVE all networkConnectivityEndpoints from nfvipop table in FEDERATION DB
        try {
            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT netServId, federationIsRequired FROM networkservices where netServId=?");
             ps.setString(1, networkId.get(0));
            
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

            federationIsRequired = rs.getBoolean("federationIsRequired");
                
if (federationIsRequired != true){
federation_required = false;
return federation_required;
}
  

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }  
        
       }        
        return federation_required;
    }    
    
    
}
