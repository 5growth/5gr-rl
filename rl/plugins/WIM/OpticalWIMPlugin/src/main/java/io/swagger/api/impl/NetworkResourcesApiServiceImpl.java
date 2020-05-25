package io.swagger.api.impl;

import com.google.gson.Gson;
import io.swagger.api.*;
import io.swagger.api.constants.ConstantValues;
import io.swagger.api.intent.model.IntentModel;
import io.swagger.api.intent.model.Signal;
import io.swagger.api.intent.model.egressPoint;
import io.swagger.api.intent.model.ingressPoint;
import io.swagger.model.*;

import io.swagger.model.AllocateParameters;
import io.swagger.model.AllocateReply;
import io.swagger.model.Filter;
import io.swagger.model.NetworkIds;
import io.swagger.model.VirtualNetwork;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-11-29T11:25:16.593Z")
public class NetworkResourcesApiServiceImpl extends NetworkResourcesApiService {
    @Override
    public Response allocateNetwork(AllocateParameters params, SecurityContext securityContext) throws NotFoundException {



        Response response = null;
        try {
            String source="";
            switch (params.getIngressPointIPAddress()) {
                case "10.0.0.1":
                    source="netconf:10.10.255.12:2022";
                    break;
                case "10.0.0.2":
                    source="netconf:10.10.255.11:2022";
                    break;
                case "10.0.0.3":
                     source="netconf:10.10.255.13:2022";
                    break;
                case "10.0.0.4":
                    source="netconf:10.10.255.14:2022";
                    break;
                default:
                    source=params.getIngressPointIPAddress();
            }
            String sPort="";
            switch (params.getIngressPointPortAddress()) {
                case "1":
                    sPort="101";
                    break;
                case "2":
                    sPort="102";
                    break;
                case "3":
                    sPort="103";
                    break;
                case "4":
                    sPort="104";
                    break;
                default:
                    System.out.println("WARNING: stange port seelected, adding default port 101");
                    sPort="101";
            }

            String dest="";
            switch (params.getEgressPointIPAddress()) {
                case "10.0.0.1":
                    dest="netconf:10.10.255.12:2022";
                    break;
                case "10.0.0.2":
                    dest="netconf:10.10.255.11:2022";
                    break;
                case "10.0.0.3":
                    dest="netconf:10.10.255.13:2022";
                    break;
                case "10.0.0.4":
                    dest="netconf:10.10.255.14:2022";
                    break;
                default:
                    dest=params.getEgressPointIPAddress();
            }
            String dPort="";
            switch (params.getEgressPointPortAddress()) {
                case "5":
                    dPort="201";
                    break;
                case "6":
                    dPort="202";
                    break;
                case "7":
                    dPort="203";
                    break;
                case "8":
                    dPort="204";
                    break;
                default:
                    System.out.println("WARNING: stange port seelected, adding default port 101");
                    dPort="101";
            }



            IntentModel intentModel = new IntentModel();
            //intentModel.setAppId(params.getReservationId());
            intentModel.setAppId("org.onosproject.optical-rest");

            //source point
            ingressPoint ip = new ingressPoint();
            ip.setDevice(source);
            ip.setPort(sPort);
            intentModel.setIngressPoint(ip);
            //dest point
            egressPoint ep = new egressPoint();
            ep.setDevice(dest);
            ep.setPort(dPort);
            intentModel.setEgressPoint(ep);
            //signal
            /*
            "signal": {
                "channelSpacing": "CHL_50GHZ",
                "gridType": "DWDM",
                "spacingMultiplier": 12,
                "slotGranularity": 4
            },
            */
            Signal sign = new Signal();
            sign.setchannelSpacing("CHL_50GHZ");
            sign.setgridType("DWDM");
            sign.setslotGranularity(4);
            //random carrier
            int carrier = ThreadLocalRandom.current().nextInt(-50, 50);
            System.out.println("selected a carrier value of "+carrier);
            sign.setspacingMultiplier(carrier);
            intentModel.setSignal(sign);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(intentModel);
            System.out.println("Result : " + jsonInputString);

            String authString = ConstantValues.ONOS_USERNAME + ":" + ConstantValues.ONOS_PASSWORD;
            String authStringEnc = new String(Base64.encode(authString.getBytes()));

            Client client = ClientBuilder.newClient();
            System.out.println("calling ONOS API to allocate network resource. . .");

            String url = ConstantValues.CREATE_INTENTS_URL;
            response = client.target(url)
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .header(ConstantValues.AUTHORIZATION_HEADER_KEY, ConstantValues.AUTHORIZATION_HEADER_PREFIX + authStringEnc)
                    .post(Entity.entity(jsonInputString, MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return response;
    }
    @Override
    public Response queryNetworks(Filter networkQueryFilter, SecurityContext securityContext) throws NotFoundException {

        Response response = null;

        try {
            String authString = ConstantValues.ONOS_USERNAME + ":" + ConstantValues.ONOS_PASSWORD;
            String authStringEnc = new String(Base64.encode(authString.getBytes()));

            Client client = ClientBuilder.newClient();
            System.out.println("calling ONOS API . . .");

            System.out.println("calling ONOS API to query network resource. . .");
            String url = ConstantValues.QUERY_INTENTS_URL;
            response = client.target(url)
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .header(ConstantValues.AUTHORIZATION_HEADER_KEY, ConstantValues.AUTHORIZATION_HEADER_PREFIX + authStringEnc)
                    .get(Response.class);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return response;
    }
    @Override
    public Response terminateNetworks(String networkId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
