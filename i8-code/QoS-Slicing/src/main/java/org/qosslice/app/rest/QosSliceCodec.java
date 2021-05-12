package org.qosslice.app.rest;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.onosproject.codec.CodecContext;
import org.onosproject.codec.JsonCodec;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.net.meter.Band;
import org.onosproject.net.meter.Meter;
import org.qosslice.app.api.QosSliceData;
import org.qosslice.app.api.QueueConfig;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
* QoS Slice JSON codec.
*/
public final class QosSliceCodec extends JsonCodec<QosSliceData> {
    // JSON field names
    private static final String QOS_NAME = "qos-name";
    private static final String VPLS_NAME = "vpls-name";
    private static final String METER = "meter";
    private static final String QUEUE= "queue";
    private static final String BANDS= "bands";
    private static final String UNIT = "unit";

    private final Logger log = getLogger(getClass());

    @Override
    public ObjectNode encode(QosSliceData qosSliceData, CodecContext context){
        checkNotNull(qosSliceData, "QosSlice cannot be null");
        ObjectNode result = context.mapper().createObjectNode()
                .put(QOS_NAME,qosSliceData.getQosName())
                .put(VPLS_NAME,qosSliceData.getVplsName())
                .put(QUEUE, qosSliceData.getQueue().toString())
                .put(METER, qosSliceData.getMeter());
        
        result.put(UNIT,qosSliceData.getMeterUnit().toString());
        ArrayNode bands = context.mapper().createArrayNode();
        qosSliceData.getBands().forEach(band -> {
                ObjectNode bandJson = context.codec(Band.class).encode(band, context);
                bands.add(bandJson);
            });
            result.set(BANDS, bands);

        return result;
    }

    @Override
    public QosSliceData decode(ObjectNode json, CodecContext context){
        if (json == null || !json.isObject()) {
            return null;
        }

        String qosSliceName = json.findValue(QOS_NAME).asText();
        String vplsName = json.findValue(VPLS_NAME).asText();
        JsonNode queueJSON = json.findValue(QUEUE);
        Boolean meter = json.findValue(METER).asBoolean();
        Meter.Unit meterUnit = Meter.Unit.valueOf(json.findValue(UNIT).asText());
        QosSliceData qosSliceData = QosSliceData.of(qosSliceName,vplsName);
        // Process Queue
        try {
            long M_DELAY = queueJSON.findValue("M_DELAY").asLong();
            long T_DELAY = queueJSON.findValue("T_DELAY").asLong();
            long C_DELAY = queueJSON.findValue("C_DELAY").asLong();
            qosSliceData.addQueue(new QueueConfig(M_DELAY, T_DELAY, C_DELAY));
            log.info("QUEUE Configured {}", qosSliceData.getQueue());
        } catch (Exception e) {
            log.warn("Error parsing QueueConfig or missing Queue config");
            qosSliceData.setQueue(null);
        }
        if(meter){
            qosSliceData.setMeterUnit(meterUnit);
            qosSliceData.addMeter();
        }
        Collection<Band> bandList = new ArrayList<>();
        JsonNode bandJeson = json.findValue(BANDS);
        JsonCodec<Band> bandCodec = context.codec(Band.class);
        if (bandJeson != null) {
            IntStream.range(0, bandJeson.size())
                    .forEach(i -> bandList.add(
                            bandCodec.decode(get(bandJeson, i),
                                    context)));
        }
        bandList.forEach(qosSliceData::addBand);
        return qosSliceData;
    }
}
