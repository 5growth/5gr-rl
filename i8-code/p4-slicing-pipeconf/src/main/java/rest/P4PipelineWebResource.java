package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.p4slicing.pipeconf.PipelineInterpreterImpl;
import org.slf4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.onosproject.rest.AbstractWebResource;

import static org.onlab.util.Tools.readTreeFromStream;
import static org.slf4j.LoggerFactory.getLogger;

@Path("")
public class P4PipelineWebResource extends AbstractWebResource {

    private static final Logger log = getLogger(P4PipelineWebResource.class);

    public static Map<Long, QueueModel> map = new HashMap<Long, QueueModel>();

    public static Map<Long, QueueModel> getMap() {
        return map;
    }

    @POST
    @Path("queue")
    public Response postQueue(InputStream stream){
        try {
            ObjectNode jsonTree = readTreeFromStream(mapper(), stream);
            log.error(jsonTree.toString());
/*            ObjectMapper mapper = new ObjectMapper();
            ObjectNode jsonTree = mapper().readTree(stream);*/
            long T_DELAY = jsonTree.path("T_DELAY").asLong();
            long M_DELAY = jsonTree.path("M_DELAY").asLong();
            long C_DELAY = jsonTree.path("C_DELAY").asLong();
            long Queue_ID = jsonTree.path("queue_id").asLong();

            QueueModel queue = new QueueModel(Queue_ID,T_DELAY,M_DELAY,C_DELAY);

            map.put(Queue_ID, queue);
            return Response.ok().build();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{QueueID}")
    public Response getQosSlice(@PathParam("QueueID") long QueueID) {
        ObjectMapper mapper = new ObjectMapper();

        QueueModel queue = map.get(QueueID);
        if (queue == null)
            return Response.status(404).entity("Queue with ID: " +QueueID+ "not found").build();
        //ArrayNode arrayNode = mapper.createArrayNode();

        ObjectNode node = mapper.createObjectNode();
        node.put("QueueID", QueueID);
        node.put("T_DELAY", queue.getT_DELAY());
        node.put("M_DELAY", queue.getM_DELAY());
        node.put("C_DELAY", queue.getC_DELAY());

        //arrayNode.add(node);

        return Response.ok(node.toString(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    @DELETE
    @Path("{QueueID}")
    public Response deleteQosSlice(@PathParam("QueueID") long QueueID) {
        if (!map.containsKey(QueueID))
            return Response.status(404).entity("Queue with ID: " +QueueID+ "not found").build();
        map.remove(QueueID);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQosSlice(){
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        if (map.isEmpty()) {
            return Response.status(404).entity("Queue BBDD is empty").build();
        }
        for(Map.Entry<Long, QueueModel> entry : map.entrySet()){
            QueueModel queue = entry.getValue();
            ObjectNode node = mapper.createObjectNode();
            node.put("QueueID", entry.getKey());
            node.put("T_DELAY", queue.getT_DELAY());
            node.put("M_DELAY", queue.getM_DELAY());
            node.put("C_DELAY", queue.getC_DELAY());
            arrayNode.add(node);
        }
        return Response.ok(arrayNode.toString(), MediaType.APPLICATION_JSON_TYPE).build();
    }
}