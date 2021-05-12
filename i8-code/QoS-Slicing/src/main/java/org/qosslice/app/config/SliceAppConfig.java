package org.qosslice.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.config.Config;
import org.qosslice.app.api.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class SliceAppConfig extends Config<ApplicationId> {
    private static final String SLICE = "sliceList";
    private static final String NAME = "sliceName";
    private static final String VPLS = "vplName";
    private static final String METER = "meter";
    private static final String METER_UNIT = "meter_unit";
    private static final String BANDS = "bands";
    private static final String QOS = "qos";

    // Record update time when Store additional data update it
    private static final String UPDATE_TIME = "lastUpdateTime";
    private final Logger log = LoggerFactory.getLogger(getClass());


    /**
     * Returns a set of configured Slices.
     *
     * @return set of Slicess
     */

    public Set<SliceConfig> Slicess() {
        Set<SliceConfig> Slicess = Sets.newHashSet();
        JsonNode SliceNode = object.get(SLICE);
        if (SliceNode == null) {
            return Slicess;
        }

        SliceNode.forEach(jsonNode -> {
            String name = jsonNode.get(NAME).asText();
            String vpls = jsonNode.get(VPLS).asText();
            Boolean meter = jsonNode.get(METER).asBoolean();

            Set<String> bands = Sets.newHashSet();
            JsonNode  meterBands = jsonNode.path(BANDS);
            if (meterBands.toString().isEmpty()) {
                meterBands = ((ObjectNode) jsonNode).putArray(BANDS);
            }
            meterBands.forEach(bandNode -> bands.add(bandNode.asText()));

            String meterUnit = jsonNode.get(METER_UNIT).asText();

            JsonNode qosJSON = jsonNode.get(QOS);
            log.info(String.valueOf(qosJSON));
            QueueConfig qos = null;
            try {
                qos = new QueueConfig(qosJSON.get("M_DELAY").asLong(), qosJSON.get("T_DELAY").asLong(), qosJSON.get("C_DELAY").asLong());
            } catch (Exception ignored) {
            }

            Slicess.add(new SliceConfig(name,
                    vpls,
                    meter,
                    meterUnit,
                    bands,
                    qos));
        });
        return Slicess;
    }


    /**
     * Returns the Slice configuration given a Slice name.
     *
     * @param nameSlice the name of the Slice
     * @return the Slice configuration if it exists; null otherwise
     */
    public SliceConfig getSliceWithName(String nameSlice) {
        return Slicess().stream()
                .filter(slice -> slice.nameSlice().equals(nameSlice))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a Slice monitoring configuration.
     *
     * @param slice the name of the Slice
     */
    public void addSlice (SliceConfig slice) {

        ObjectNode sliceNode = JsonNodeFactory.instance.objectNode();

        sliceNode.put(NAME, slice.nameSlice());

        sliceNode.put(VPLS, slice.nameVpls());

        sliceNode.put(METER, slice.meter());

        ArrayNode bandsNode = sliceNode.putArray(BANDS);
        slice.bands().forEach(bandsNode::add);

        sliceNode.put(METER_UNIT, slice.meterUnit());

        if (slice.qos() != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                sliceNode.put(QOS, mapper.writeValueAsString(slice.qos()));
            } catch (JsonProcessingException ignored) {
            }
        } else {
            sliceNode.put(QOS, "null");
        }

        ArrayNode sliceArray = Slicess().isEmpty() ?
                initSliceConfiguration() : (ArrayNode) object.get(SLICE);
        sliceArray.add(sliceNode);
    }


    /**
     * Creates an empty Slice monitoring configuration.
     *
     * @return empty ArrayNode to store the Slice configuration
     */
    private ArrayNode initSliceConfiguration() {
        return object.putArray(SLICE);
    }

    /**
     * Sets the last time of update for the Slice information in the store.
     *
     * @param timestamp the update time
     */
    public void updateTime(long timestamp) {
        object.put(UPDATE_TIME, timestamp);
    }


    /**
     * Clears all Slice configurations.
     */
    public void clearSliceConfig() {
        if (object.get(SLICE) != null) {
            object.remove(SLICE);
        }
        initSliceConfiguration();
    }

}
