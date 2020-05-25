package io.swagger.api.intent.service;

import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TestGetIntents {
    public static void main(String[] args) {
        try {
            String authString = "onos" + ":" + "rocks";
            String authStringEnc = new String(Base64.encode(authString.getBytes()));

            Client client = ClientBuilder.newClient();
            System.out.println("calling ONOS API . . .");
            String url = "http://193.205.83.72:8181/onos/lumentum-app/ECOC2018-demoApp/intents";
            Response response = client.target(url)
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + authStringEnc)
                    .get(Response.class);

            System.out.println("API response = " + response);
            String temp = response.readEntity(String.class);
            System.out.println("response Data = " + temp);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
