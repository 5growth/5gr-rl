/*
 * Copyright 2017-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.p4slicing.pipeconf;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.onlab.packet.DeserializationException;
import org.onlab.packet.EthType;
import org.onlab.packet.Ethernet;
import org.onlab.packet.VlanId;
import org.onlab.util.ImmutableByteSequence;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.DeviceId;
import org.onosproject.net.Port;
import org.onosproject.net.PortNumber;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.driver.AbstractHandlerBehaviour;
import org.onosproject.net.flow.DefaultTrafficTreatment;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.net.flow.TrafficTreatment;
import org.onosproject.net.flow.criteria.Criterion;
import org.onosproject.net.flow.instructions.Instruction;
import org.onosproject.net.flow.instructions.Instructions;
import org.onosproject.net.flow.instructions.Instructions.OutputInstruction;
import org.onosproject.net.flow.instructions.L2ModificationInstruction;
import org.onosproject.net.flow.instructions.L2ModificationInstruction.ModEtherInstruction;
import org.onosproject.net.flow.instructions.L2ModificationInstruction.ModVlanIdInstruction;
import org.onosproject.net.flow.instructions.L2ModificationInstruction.ModVlanHeaderInstruction;
import org.onosproject.net.packet.DefaultInboundPacket;
import org.onosproject.net.packet.InboundPacket;
import org.onosproject.net.packet.OutboundPacket;
import org.onosproject.net.pi.model.PiActionId;
import org.onosproject.net.pi.model.PiActionParamId;
import org.onosproject.net.pi.model.PiMatchFieldId;
import org.onosproject.net.pi.model.PiPacketMetadataId;
import org.onosproject.net.pi.model.PiPipelineInterpreter;
import org.onosproject.net.pi.model.PiTableId;
import org.onosproject.net.pi.runtime.PiAction;
import org.onosproject.net.pi.runtime.PiActionParam;
import org.onosproject.net.pi.runtime.PiPacketMetadata;
import org.onosproject.net.pi.runtime.PiPacketOperation;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.P4PipelineWebResource;
import rest.QueueModel;

import javax.validation.constraints.Null;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.onlab.util.ImmutableByteSequence.copyFrom;
import static org.onosproject.net.PortNumber.CONTROLLER;
import static org.onosproject.net.PortNumber.FLOOD;
import static org.onosproject.net.flow.instructions.Instruction.Type.OUTPUT;
//import static org.onosproject.net.flow.instructions.Instruction.Type.QUEUE;
import static org.onosproject.net.flow.instructions.L2ModificationInstruction.L2SubType.*;
import static org.onosproject.net.pi.model.PiPacketOperationType.PACKET_OUT;


/**
 * Implementation of a pipeline interpreter for the mytunnel.p4 program.
 */
public final class PipelineInterpreterImpl
        extends AbstractHandlerBehaviour
        implements PiPipelineInterpreter {

    private static final Logger log = LoggerFactory.getLogger(PipelineInterpreterImpl.class);
    private static final String DOT = ".";
    private static final String HDR = "hdr";
    private static final String C_INGRESS = "c_ingress";
    private static final String T_L2_FWD = "t_l2_fwd";
    private static final String EGRESS_PORT = "egress_port";
    private static final String INGRESS_PORT = "ingress_port";
    private static final String ETHERNET = "ethernet";
    private static final String VALUE = "value";
    private static final String STANDARD_METADATA = "standard_metadata";
    private static final String VLAN = "vlan";
    private static final int PORT_FIELD_BITWIDTH = 9;

    private static final PiMatchFieldId INGRESS_PORT_ID =
            PiMatchFieldId.of(STANDARD_METADATA + DOT + "ingress_port");
    private static final PiMatchFieldId ETH_DST_ID =
            PiMatchFieldId.of(HDR + DOT + ETHERNET + DOT + "dst_addr");
    private static final PiMatchFieldId ETH_SRC_ID =
            PiMatchFieldId.of(HDR + DOT + ETHERNET + DOT + "src_addr");
    private static final PiMatchFieldId ETH_TYPE_ID =
            PiMatchFieldId.of(HDR + DOT + "ether_type" + DOT + VALUE);
    private static final PiMatchFieldId VLAN_VID_ID =
            PiMatchFieldId.of(HDR + DOT + VLAN + DOT + "vid");

    private static final PiTableId TABLE_L2_FWD_ID =
            PiTableId.of(C_INGRESS + DOT + T_L2_FWD);

    private static final PiActionId ACT_ID_NOP =
            PiActionId.of("NoAction");                     //NoAction
    private static final PiActionId ACT_ID_SEND_TO_CPU =
            PiActionId.of(C_INGRESS + DOT + "send_to_cpu");//c_ingress.send_to_cpu
    private static final PiActionId ACT_ID_SET_EGRESS_PORT =
            PiActionId.of(C_INGRESS + DOT + "set_out_port");//c_ingress.set_out_port
    private static final PiActionId ACT_ID_SET_VLAN =
            PiActionId.of(C_INGRESS + DOT + "set_vlan");//c_ingress.set_vlan
    private static final PiActionId ACT_ID_POP_VLAN =
            PiActionId.of(C_INGRESS + DOT + "pop_vlan");//c_ingress.pop_vlan
    private static final PiActionId ACT_ID_PUSH_VLAN =
            PiActionId.of(C_INGRESS + DOT + "push_vlan");//c_ingress.push_vlan
    private static final PiActionId ACT_ID_PUSH_VLAN_METER =
            PiActionId.of(C_INGRESS + DOT + "push_vlan_meter");//c_ingress.push_vlan_meter
    private static final PiActionId ACT_ID_POP_VLAN_METER =
            PiActionId.of(C_INGRESS + DOT + "pop_vlan_meter");//c_ingress.pop_vlan_meter
    private static final PiActionId ACT_ID_SET_VLAN_METER =
            PiActionId.of(C_INGRESS + DOT + "set_vlan_meter");//c_ingress.set_vlan_meter
    private static final PiActionId ACT_ID_SET_VLAN_METER_QUEUE =
            PiActionId.of(C_INGRESS + DOT + "set_vlan_meter_queue");//c_ingress.set_vlan_meter_queue
    private static final PiActionId ACT_ID_POP_VLAN_METER_QUEUE =
            PiActionId.of(C_INGRESS + DOT + "pop_vlan_meter_queue");//c_ingress.pop_vlan_meter_queue
    private static final PiActionId ACT_ID_PUSH_VLAN_METER_QUEUE =
            PiActionId.of(C_INGRESS + DOT + "push_vlan_meter_queue");//c_ingress.push_vlan_meter_queue
    private static final PiActionId ACT_ID_SET_EGRESS_PORT_METERED =
            PiActionId.of(C_INGRESS + DOT + "set_out_port_metered");//c_ingress.set_out_port_metered
    private static final PiActionId ACT_ID_SET_EGRESS_PORT_METERED_QUEUE =
            PiActionId.of(C_INGRESS + DOT + "set_out_port_metered_queue");//c_ingress.set_out_port_metered_queue

    private static final PiActionParamId ACT_PARAM_ID_PORT =
            PiActionParamId.of("port");
    private static final PiActionParamId ACT_PARAM_ID_METER =
            PiActionParamId.of("meter_id");
    private static final PiActionParamId ACT_PARAM_ID_VLAN =
            PiActionParamId.of("vlan_vid");
    private static final PiActionParamId ACT_PARAM_T_DELAY =
            PiActionParamId.of("T_DELAY");
    private static final PiActionParamId ACT_PARAM_C_DELAY =
            PiActionParamId.of("C_DELAY");
    private static final PiActionParamId ACT_PARAM_M_DELAY =
            PiActionParamId.of("M_DELAY");

    private static final Map<Integer, PiTableId> TABLE_MAP =
            new ImmutableMap.Builder<Integer, PiTableId>()
                    .put(0, TABLE_L2_FWD_ID)
                    .build();

    private static final Map<Criterion.Type, PiMatchFieldId> CRITERION_MAP =
            ImmutableMap.<Criterion.Type, PiMatchFieldId>builder()
                    .put(Criterion.Type.IN_PORT, INGRESS_PORT_ID)
                    .put(Criterion.Type.ETH_DST, ETH_DST_ID)
                    .put(Criterion.Type.ETH_SRC, ETH_SRC_ID)
                    .put(Criterion.Type.ETH_TYPE, ETH_TYPE_ID)
                    .put(Criterion.Type.VLAN_VID, VLAN_VID_ID)
                    .build();

    @Override
    public Optional<PiMatchFieldId> mapCriterionType(Criterion.Type type) {
        return Optional.ofNullable(CRITERION_MAP.get(type));
    }

    @Override
    public Optional<PiTableId> mapFlowRuleTableId(int flowRuleTableId) {
        return Optional.ofNullable(TABLE_MAP.get(flowRuleTableId));
    }

    //Method to get a list of L2Instruction with a certain subtype
    public static List<L2ModificationInstruction> l2Instructions(
            TrafficTreatment treatment, L2ModificationInstruction.L2SubType subType) {
        return treatment.allInstructions().stream()
                .filter(i -> i.type().equals(Instruction.Type.L2MODIFICATION))
                .map(i -> (L2ModificationInstruction) i)
                .filter(i -> i.subtype().equals(subType))
                .collect(Collectors.toList());
    }

    private long mapToP4ID(long openflowId, long outPort) {
        try {
            String meterSTring = Long.toUnsignedString(openflowId);
            int pos = meterSTring.lastIndexOf("0" + Long.toUnsignedString(outPort));
            String sliceId = meterSTring.substring(0, pos);
            return 48L * (Long.parseUnsignedLong(sliceId) - 1) + outPort;
        } catch (Exception e) {
            log.error("Error mapping MeterId {} with Output Port {}", openflowId, outPort);
            return 1L;
        }
    }
    private long maptoSliceID(long openflowId, long outPort){
        try {
            String meterSTring = Long.toUnsignedString(openflowId);
            int pos = meterSTring.lastIndexOf("0" + Long.toUnsignedString(outPort));
            String sliceId = meterSTring.substring(0, pos);
            return Long.parseLong(sliceId);
        } catch (Exception e) {
            log.error("Error mapping MeterId {} with Output Port {}", openflowId, outPort);
            return 1L;
        }
    }

    @Override
    public PiAction mapTreatment(TrafficTreatment treatment, PiTableId piTableId)
            throws PiInterpreterException {

        log.info("tableId: {}, treatment: {}", piTableId.toString(), treatment.toString());

        //Get VLAN_ID Instruction
        final List<ModVlanIdInstruction> modVlanIdInst = l2Instructions(treatment, VLAN_ID)
                .stream().map(i -> (ModVlanIdInstruction) i).collect(Collectors.toList());
        //Get VLAN_POP Instruction
        final List<ModVlanHeaderInstruction> modVlanPopInst = l2Instructions(treatment, VLAN_POP)
                .stream().map(i -> (ModVlanHeaderInstruction) i).collect(Collectors.toList());

        final List<ModVlanHeaderInstruction> modVlanPushInst = l2Instructions(treatment, VLAN_PUSH)
                .stream().map(i -> (ModVlanHeaderInstruction) i).collect(Collectors.toList());

        if (piTableId != TABLE_L2_FWD_ID) {
            throw new PiInterpreterException(
                    "Can map treatments only for 't_l2_fwd' table");
        }

        if (treatment.allInstructions().size() == 0) {
            // 0 instructions means "NoAction"
            return PiAction.builder().withId(ACT_ID_NOP).build();
        }

       /*if() {
            Instructions.MetadataInstruction queue_metadata = treatment.writeMetadata();
            Long queue = queue_metadata.metadata();
        }*/

        // Get the first and only instruction.
        //Instruction instruction = treatment.allInstructions().get(0);
        final List<Instruction> InstructionList = treatment.allInstructions().stream()
                .filter(i -> i.type().equals(Instruction.Type.OUTPUT))
                .collect(Collectors.toList());
        if(InstructionList.isEmpty()){
            throw new PiInterpreterException(format(
                    "there is no output instruction"));
        }
        log.info("INSTRUCTION LIST: " + InstructionList.toString());
        Instruction instruction = InstructionList.get(0);


        if (instruction.type() != OUTPUT) {
            // We can map only instructions of type OUTPUT or QUEUE.
            throw new PiInterpreterException(format(
                    "Instruction of type '%s' not supported", instruction.type()));
        }
        OutputInstruction outInstruction = (OutputInstruction) instruction;
        PortNumber port = outInstruction.port();
        QueueModel queueModel = null;
        if(treatment.metered() != null){
            long sliceID = maptoSliceID(treatment.metered().meterId().id(), port.toLong());
            queueModel = P4PipelineWebResource.getMap().get(sliceID);
        }
        if (!port.isLogical()) {
            if(queueModel == null) {
                log.info("QUEUE FALSE");
                if ((treatment.metered() != null) && (!modVlanIdInst.isEmpty())) {
                    long mappedID = mapToP4ID(treatment.metered().meterId().id(), port.toLong());
                    return PiAction.builder()
                            .withId(ACT_ID_PUSH_VLAN_METER)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_VLAN, copyFrom(modVlanIdInst.get(0).vlanId().toShort())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_METER, mappedID))
                            .build();
                }//Push vlan with metter
                else if ((treatment.metered() != null) && (!modVlanPopInst.isEmpty())) {
                    long mappedID = mapToP4ID(treatment.metered().meterId().id(), port.toLong());
                    return PiAction.builder()
                            .withId(ACT_ID_POP_VLAN_METER)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_METER, mappedID))
                            .build();
                }//Pop vlan with metter
                else if ((treatment.metered() == null) && (!modVlanPopInst.isEmpty())) {
                    return PiAction.builder()
                            .withId(ACT_ID_POP_VLAN)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .build();
                }//Pop vlan
                else if ((treatment.metered() == null) && (!modVlanIdInst.isEmpty())) {
                    return PiAction.builder()
                            .withId(ACT_ID_PUSH_VLAN)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_VLAN, copyFrom(modVlanIdInst.get(0).vlanId().toShort())))
                            .build();
                }//Push vlan
                else if (treatment.metered() != null) {
                    long mappedID = mapToP4ID(treatment.metered().meterId().id(), port.toLong());
                    return PiAction.builder()
                            .withId(ACT_ID_SET_EGRESS_PORT_METERED)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_METER, mappedID))
                            .build();
                }//Set port with metter
                else {
                    return PiAction.builder()
                            .withId(ACT_ID_SET_EGRESS_PORT)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .build();
                }//Set outport
            }
            else{//case that queue exist
                log.info("QUEUE TRUE");
                long T_DELAY = queueModel.getT_DELAY();
                long C_DELAY = queueModel.getC_DELAY();
                long M_DELAY = queueModel.getM_DELAY();
                log.info("M_DELAY {}, T_DELAY {}, C_DELAY {}",M_DELAY,T_DELAY, C_DELAY);
                if (!modVlanIdInst.isEmpty()) {
                    long mappedID = mapToP4ID(treatment.metered().meterId().id(), port.toLong());
                    return PiAction.builder()
                            .withId(ACT_ID_PUSH_VLAN_METER_QUEUE)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_VLAN, copyFrom(modVlanIdInst.get(0).vlanId().toShort())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_METER, mappedID))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_T_DELAY, T_DELAY))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_C_DELAY, C_DELAY))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_M_DELAY, M_DELAY))
                            .build();
                }//push vlan with queue
                else if (!modVlanPopInst.isEmpty()) {
                    long mappedID = mapToP4ID(treatment.metered().meterId().id(), port.toLong());
                    return PiAction.builder()
                            .withId(ACT_ID_POP_VLAN_METER_QUEUE)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_METER, mappedID))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_T_DELAY, T_DELAY))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_C_DELAY, C_DELAY))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_M_DELAY, M_DELAY))
                            .build();
                }//Pop vlan with queue
                else{
                    long mappedID = mapToP4ID(treatment.metered().meterId().id(), port.toLong());
                    return PiAction.builder()
                            .withId(ACT_ID_SET_EGRESS_PORT_METERED_QUEUE)
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_PORT, copyFrom(port.toLong())))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_ID_METER, mappedID))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_T_DELAY, T_DELAY))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_C_DELAY, C_DELAY))
                            .withParameter(new PiActionParam(
                                    ACT_PARAM_M_DELAY, M_DELAY))
                            .build();
                }//Set port with queue
            }
        } else if (port.equals(CONTROLLER)) {
            return PiAction.builder()
                    .withId(ACT_ID_SEND_TO_CPU)//c_ingress.send_to_cpu
                    .build();
        } else {
            throw new PiInterpreterException(format(
                    "Output on logical port '%s' not supported", port));
        }
    }

    @Override
    public Collection<PiPacketOperation> mapOutboundPacket(OutboundPacket packet)
            throws PiInterpreterException {

        TrafficTreatment treatment = packet.treatment();

        // We support only packet-out with OUTPUT instructions.
        if (treatment.allInstructions().size() != 1 &&
                treatment.allInstructions().get(0).type() != OUTPUT) {
            throw new PiInterpreterException(
                    "Treatment not supported: " + treatment.toString());
        }

        Instruction instruction = treatment.allInstructions().get(0);
        PortNumber port = ((OutputInstruction) instruction).port();
        List<PiPacketOperation> piPacketOps = Lists.newArrayList();

        if (!port.isLogical()) {
            piPacketOps.add(createPiPacketOp(packet.data(), port.toLong()));
        } else if (port.equals(FLOOD)) {
            // Since mytunnel.p4 does not support flooding, we create a packet
            // operation for each switch port.
            DeviceService deviceService = handler().get(DeviceService.class);
            DeviceId deviceId = packet.sendThrough();
            for (Port p : deviceService.getPorts(deviceId)) {
                piPacketOps.add(createPiPacketOp(packet.data(), p.number().toLong()));
            }
        } else {
            throw new PiInterpreterException(format(
                    "Output on logical port '%s' not supported", port));
        }

        return piPacketOps;
    }

    @Override
    public InboundPacket mapInboundPacket(PiPacketOperation packetIn, DeviceId deviceId)
            throws PiInterpreterException {
        // We assume that the packet is ethernet, which is fine since mytunnel.p4
        // can deparse only ethernet packets.
        Ethernet ethPkt;

        try {
            ethPkt = Ethernet.deserializer().deserialize(
                    packetIn.data().asArray(), 0, packetIn.data().size());
        } catch (DeserializationException dex) {
            throw new PiInterpreterException(dex.getMessage());
        }

        // Returns the ingress port packet metadata.
        Optional<PiPacketMetadata> packetMetadata = packetIn.metadatas().stream()
                .filter(metadata -> metadata.id().toString().equals(INGRESS_PORT))
                .findFirst();

        if (packetMetadata.isPresent()) {
            short s = packetMetadata.get().value().asReadOnlyBuffer().getShort();
            ConnectPoint receivedFrom = new ConnectPoint(
                    deviceId, PortNumber.portNumber(s));
            return new DefaultInboundPacket(
                    receivedFrom, ethPkt, packetIn.data().asReadOnlyBuffer());
        } else {
            throw new PiInterpreterException(format(
                    "Missing metadata '%s' in packet-in received from '%s': %s",
                    INGRESS_PORT, deviceId, packetIn));
        }
    }

    private PiPacketOperation createPiPacketOp(ByteBuffer data, long portNumber)
            throws PiInterpreterException {
        PiPacketMetadata metadata = createPacketMetadata(portNumber);
        return PiPacketOperation.builder()
                .withType(PACKET_OUT)
                .withData(copyFrom(data))
                .withMetadatas(ImmutableList.of(metadata))
                .build();
    }

    private PiPacketMetadata createPacketMetadata(long portNumber)
            throws PiInterpreterException {
        try {
            return PiPacketMetadata.builder()
                    .withId(PiPacketMetadataId.of(EGRESS_PORT))
                    .withValue(copyFrom(portNumber).fit(PORT_FIELD_BITWIDTH))
                    .build();
        } catch (ImmutableByteSequence.ByteSequenceTrimException e) {
            throw new PiInterpreterException(format(
                    "Port number %d too big, %s", portNumber, e.getMessage()));
        }
    }

}
