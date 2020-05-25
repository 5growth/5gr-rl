package io.swagger.api.intent.service;

import com.google.gson.Gson;
import io.swagger.api.intent.model.IntentModel;
import io.swagger.api.intent.model.egressPoint;
import io.swagger.api.intent.model.ingressPoint;
import org.glassfish.jersey.internal.util.Base64;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class TestCreateIntents {
    public static void main(String[] args){

        IntentModel intentModel = new IntentModel();
        intentModel.setAppId("org.onosproject.ovsdb");
        ingressPoint ip = new ingressPoint();
        ip.setDevice("netconf:10.10.255.13:2022");
        ip.setPort("101");
        intentModel.setIngressPoint(ip);

        egressPoint ep = new egressPoint();
        ep.setDevice("netconf:10.10.255.16:2022");
        ep.setPort("201");
        intentModel.setEgressPoint(ep);
        Gson gson = new Gson();

        String jsonInString = gson.toJson(intentModel);
        System.out.println("Result : " + jsonInString);

        String authString = "onos" + ":" + "rocks";
        String authStringEnc = new String(Base64.encode(authString.getBytes()));

        Client client = ClientBuilder.newClient();
        System.out.println("calling ONOS API . . .");
        String url = "http://193.205.83.72:8181/onos/optical/intents";
        Response response = client.target(url)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + authStringEnc)
                .post(Entity.entity(jsonInString, MediaType.APPLICATION_JSON));

        System.out.println("API response = " + response);
        String temp = response.readEntity(String.class);
        System.out.println("response Data = " + temp);

    }
}
