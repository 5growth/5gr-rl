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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ezimbgi
 */
public class Fed_E2ENetworkAllocateRequest_Validator {

    //private boolean federated_resources;
    private InterNfviPopConnectivityRequest body;

    public Fed_E2ENetworkAllocateRequest_Validator() {
        // this.federated_resources = false;
        this.body = null;
    }

    public Fed_E2ENetworkAllocateRequest_Validator(InterNfviPopConnectivityRequest body) {
        //this.federated_resources = false;
        this.body = body;
    }

    public boolean checkIfFederationResIsRequired() {

        boolean federation_required = false;
        ArrayList<String> networkConnectivityEndpointList = new ArrayList();

        String dstGwIpAddress = body.getLogicalLinkPathList().get(0).getLogicalLinkAttributes().getDstGwIpAddress();
        String srcGwIpAddress = body.getLogicalLinkPathList().get(0).getLogicalLinkAttributes().getSrcGwIpAddress();

        //RETRIEVE all networkConnectivityEndpoints from nfvipop table in FEDERATION DB
        try {
            java.sql.Connection conn = DbFederationDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM nfvipop");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String networkConnectivityEndpoint = rs.getString("networkConnectivityEndpoint");
                String[] vals = networkConnectivityEndpoint.split(";");

                for (int i = 0; i < vals.length; i++) {

                    networkConnectivityEndpointList.add(vals[i]);
                }

           

            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        //Check if srcRouter or dstRouter is located in a federated domain
        if (networkConnectivityEndpointList.contains(srcGwIpAddress)) {
            federation_required = true;
        }

        if (networkConnectivityEndpointList.contains(dstGwIpAddress)) {
            federation_required = true;
        }

        return federation_required;
    }

    public boolean checkIfIntraPopExist() {
         boolean intraPopAdminNetIsCreated = false;
         String servid = "";
         
           for (int i = 0; i < body.getMetaData().size(); i++) {
            if (body.getMetaData().get(i).getKey().compareTo("ServiceId") == 0) {
                servid = body.getMetaData().get(i).getValue();
            }
        }
         
        
           try {
            java.sql.Connection conn = DbServiceDatasource.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT provider FROM networkservices WHERE servId = ?");
            ps.setString(1, servid);
            java.sql.ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {
                 System.out.println(rs.getBoolean("provider"));
                if (rs.getBoolean("provider")){
                intraPopAdminNetIsCreated = true;
                }
                System.out.println("Fed_E2ENetworkAllocateRequest_Validator.checkIfIntraPopExist ---> Intrapop of type Admin: " + rs.getString("provider") + "");
            }
            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseDriver.class
                    .getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
           
           
           
         
        


        return intraPopAdminNetIsCreated;
    }

}
