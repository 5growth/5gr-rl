package org.qosslice.app.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.onlab.util.Bandwidth;
import org.onosproject.net.Device;
import org.qosslice.app.api.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.onosproject.net.behaviour.*;


import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

public class QueueUtility {

    private static final Logger log = LoggerFactory.getLogger(
            QueueUtility.class);

    private QueueUtility() {
        // Utility classes should not have a public or default constructor.
    }

    private static String basicAuth(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public static void createQueue(String vplsname, QueueConfig queue, String nodeIP) {
        try {
            var values = new HashMap<String, Long>() {{
                put("M_DELAY", queue.getM_DELAY());
                put("T_DELAY", queue.getT_DELAY());
                put("C_DELAY", queue.getC_DELAY());
                put("queue_id", Long.valueOf(vplsname.split("-")[1]));
            }};
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(values);
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + nodeIP + ":8181/onos/p4-pipeconf/queue"))
                    .setHeader("User-Agent", "QoS-Slicing")
                    .header("Content-Type", "application/json")
                    .header("Authorization", basicAuth("onos", "rocks"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            log.info("Request CREATE QUEUE {}: {}", vplsname, response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Exception Request CREATE QUEUE {}:", vplsname, e);
        }
    }

    public static void getQueue(String vplsname, String nodeIP) {
        try {
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + nodeIP + ":8181/onos/p4-pipeconf/" + Long.valueOf(vplsname.split("-")[1])))
                    .setHeader("User-Agent", "QoS-Slicing")
                    .header("Authorization", basicAuth("onos", "rocks"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Request GET QUEUE {}: {}", vplsname, response.body());
        } catch (InterruptedException | IOException e) {
            log.error("Exception Request GET QUEUE {}:", vplsname, e);
        }
    }

    public static void getQueues(String nodeIP) {
        try {
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + nodeIP + ":8181/onos/p4-pipeconf/queue"))
                    .setHeader("User-Agent", "QoS-Slicing")
                    .header("Authorization", basicAuth("onos", "rocks"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            log.info("Request GET QUEUES: {}", response.body());
        } catch (InterruptedException | IOException e) {
            log.error("Exception Request GET QUEUES:", e);
        }
    }


    public static void deleteQueue(String vplsname, String nodeIP) {
        try {
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + nodeIP + ":8181/onos/p4-pipeconf/" + Long.valueOf(vplsname.split("-")[1])))
                    .setHeader("User-Agent", "QoS-Slicing")
                    .header("Authorization", basicAuth("onos", "rocks"))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            log.info("Request DELETE QUEUE {}: {}", vplsname, response.body());
        } catch (InterruptedException | IOException e) {
            log.error("Exception Request DELETE QUEUE {}:", vplsname, e);
        }
    }

    public static void checkQos(List<Device> devices){

        log.info("dentro chrckQos");

        devices.forEach(device -> {



            if(device.is(QueueConfigBehaviour.class)){
                log.info("if");
                QueueConfigBehaviour qg = device.as(QueueConfigBehaviour.class);
                log.info("Obtain all queues configured on a device."+qg.getQueues().toString());
            }
            else{log.info(" else "+device.is(QueueConfigBehaviour.class));}
        });
    }

    public static QueueDescription installQueue(String queueName){

        log.info("dentro installQueue");

        QueueDescription queue = DefaultQueueDescription.builder()
                .queueId(QueueId.queueId(queueName))
                //.burst() capire meglio
                .dscp(1)
                .maxRate(Bandwidth.bps(200))
                .minRate(Bandwidth.bps(100))
                .priority(1L)
                //.type(QueueDescription.Type.PRIORITY) capire meglio
                .build();

        log.info("QueueDescription BUILDATA"+ queue.toString());

        return queue;


    }




}
