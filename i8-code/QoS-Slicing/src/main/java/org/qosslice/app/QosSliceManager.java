/*
 * Copyright 2019-present Open Networking Foundation
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
package org.qosslice.app;

import com.google.common.collect.ImmutableSet;
import org.onosproject.app.ApplicationAdminService;
import org.onosproject.cfg.ComponentConfigService;
import org.onosproject.cluster.ClusterService;
import org.onosproject.codec.CodecService;
import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.Device;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.flow.*;
import org.onosproject.net.flow.criteria.PiCriterion;
import org.onosproject.net.intent.Intent;
import org.onosproject.net.intent.IntentEvent;
import org.onosproject.net.intent.IntentListener;
import org.onosproject.net.intent.IntentService;
import org.onosproject.net.meter.Band;
import org.onosproject.net.meter.Meter;
import org.onosproject.net.pi.model.PiActionId;
import org.onosproject.net.pi.model.PiMatchFieldId;
import org.onosproject.net.pi.model.PiTableId;
import org.onosproject.net.pi.runtime.PiAction;
import org.onosproject.net.pi.service.PiPipeconfWatchdogEvent;
import org.onosproject.net.pi.service.PiPipeconfWatchdogListener;
import org.onosproject.net.pi.service.PiPipeconfWatchdogService;
import org.onosproject.store.StoreDelegate;
import org.onosproject.vpls.VplsManager;
import org.onosproject.vpls.api.Vpls;
import org.osgi.service.component.annotations.*;
import org.qosslice.app.api.*;
import org.qosslice.app.rest.QosSliceCodec;
import org.qosslice.app.store.QosSliceStoreEvent;
import org.qosslice.app.utility.QueueUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Application to create performance management over network Slices.
 */
@Component(immediate = true, service = {QosSlice.class})

public class QosSliceManager implements QosSlice {

    public static final String QOSSLICE_APP = "org.qosslicing.app";
    public static final String PREFIX_UNICAST = "uni";
    private static final String SEPARATOR = "-";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String UNSUPPORTED_STORE_EVENT_TYPE =
            "Unsupported store event type {}.";
    private ApplicationId onosforwarding;
    private ApplicationId appId;
    private ApplicationId appIdvpls;
    private final boolean deactivate_onos_app = true;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ComponentConfigService cfgService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected IntentService intentService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected QosSliceStore qoSSlicingStore;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ApplicationAdminService applicationAdminService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected OperationService operationService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected Vpls vpls;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CodecService codecService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    private FlowRuleService flowRuleService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    private DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    private PiPipeconfWatchdogService piPipeconfWatchdogService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    private ClusterService clusterService;

    private StoreDelegate<QosSliceStoreEvent> sliceStoreDelegate;

    private IntentListener sliceIntentListener;

    private P4SwitchListener p4SwitchListener;

    @Activate
    protected void activate() {

        appId = coreService.registerApplication("org.qosslice.app");
        onosforwarding = coreService.getAppId("org.onosproject.fwd");
        appIdvpls = coreService.registerApplication(VplsManager.VPLS_APP);
        sliceStoreDelegate = new OperationHandler();
        sliceIntentListener = new SliceIntentListener();
        p4SwitchListener = new P4SwitchListener();
        piPipeconfWatchdogService.addListener(p4SwitchListener);
        intentService.addListener(sliceIntentListener);
        qoSSlicingStore.setDelegate(sliceStoreDelegate);
        codecService.registerCodec(QosSliceData.class, new QosSliceCodec());

        if (deactivate_onos_app) {
            try {
                applicationAdminService.deactivate(onosforwarding);
                log.info("### Deactivating Onos Reactive Forwarding App ###");
            } catch (NullPointerException ne) {
                log.info(ne.getMessage());
            }
        }
    }

    @Deactivate
    protected void deactivate() {
        cfgService.unregisterProperties(getClass(), false);
        qoSSlicingStore.unsetDelegate(sliceStoreDelegate);
        intentService.removeListener(sliceIntentListener);
        piPipeconfWatchdogService.removeListener(p4SwitchListener);
        codecService.unregisterCodec(QosSliceData.class);
        log.info("Stopped");
    }

    @Override
    public void createSliceMonitoring(String qossliceName, String vplsName) {
        requireNonNull(vplsName);
        requireNonNull(qossliceName);
        QosSliceData qoSData = QosSliceData.of(qossliceName,vplsName);
        qoSSlicingStore.addSlice(qoSData);
    }

    @Override
    public void createSliceMonitoring(QosSliceData qosSliceData) {
        requireNonNull(qosSliceData);
        requireNonNull(qosSliceData.getQosName());
        requireNonNull(qosSliceData.getVplsName());
        qoSSlicingStore.addSlice(QosSliceData.of(qosSliceData));
        if(qosSliceData.getQueue() != null){
            this.addQueue(qosSliceData);
        }
        if(qosSliceData.getMeter()){
            this.addMeter(qosSliceData);
        }
    }

    @Override
    public void addBand(QosSliceData qoSData, Map<Meter.Unit,Band> meteringData) {
        requireNonNull(qoSData);
        requireNonNull(meteringData);
        //log.info("dentro add band");
        QosSliceData newData = QosSliceData.of(qoSData);
        newData.setMeterUnit(meteringData.keySet().iterator().next());
        newData.addBand(meteringData.values().iterator().next());
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
    }

    @Override
    public void addMeter(QosSliceData qoSData) {
        requireNonNull(qoSData);
        //log.info("dentro add meter");
        QosSliceData newData = QosSliceData.of(qoSData);
        newData.addMeter();
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
    }

    @Override
    public void addQueue(QosSliceData qoSData) {
        requireNonNull(qoSData);
        QosSliceData newData = QosSliceData.of(qoSData);
        String IP = "localhost";
        try {
            IP = this.clusterService.getLocalNode().ip().getIp4Address().toString();
        } catch(Exception ignored) {
        }
        QueueUtility.createQueue(newData.getVplsName(), newData.getQueue(), IP);
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
    }

    @Override
    public void removeBands(QosSliceData qoSData) {
        requireNonNull(qoSData);
        //log.info("dentro remove Bands");
        QosSliceData newData = QosSliceData.of(qoSData);
        newData.removeBands();
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
    }

    @Override
    public Band removeBand(QosSliceData qoSData, Band delBand) {
        requireNonNull(qoSData);
        requireNonNull(delBand);
        QosSliceData newData = QosSliceData.of(qoSData);
        newData.removeBand(delBand);
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
        return delBand;
    }

    @Override
    public void removeMeter(QosSliceData qoSData) {
        requireNonNull(qoSData);
        //log.info("dentro remove meter");
        QosSliceData newData = QosSliceData.of(qoSData);
        newData.removeMeter();
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
    }

    @Override
    public void removeQos(QosSliceData qoSData) {
        requireNonNull(qoSData);
        QosSliceData newData = QosSliceData.of(qoSData);
        newData.removeQueue();
        String IP = "localhost";
        try {
            IP = this.clusterService.getLocalNode().ip().getIp4Address().toString();
        } catch(Exception ignored) {
        }
        QueueUtility.deleteQueue(newData.getVplsName(), IP);
        updateVplsStatus(newData, QosSliceData.State.UPDATING);
    }

    @Override
    public QosSliceData removeSlice(QosSliceData qoSData) {
        requireNonNull(qoSData);
        QosSliceData newData = QosSliceData.of(qoSData);
        if (newData.getQueue() != null) {
            this.removeQos(newData);
        }
        if (newData.getMeter()) {
            this.removeMeter(newData);
        }
        newData.state(QosSliceData.State.REMOVING);
        qoSSlicingStore.removeSlice(qoSData);
        return qoSData;
    }

    @Override
    public void removeAllSlice() {
        Set<QosSliceData> allVplses = ImmutableSet.copyOf(qoSSlicingStore.getAllSlice());
        allVplses.forEach(this::removeSlice);
    }

    @Override
    public Collection<QosSliceData> getAllSlice() {
        return ImmutableSet.copyOf(qoSSlicingStore.getAllSlice());
    }

    @Override
    public QosSliceData getVpls(String vplsName) {
        requireNonNull(vplsName);
        return null;
    }

    @Override
    public QosSliceData getSlice(String sliceName) {
        requireNonNull(sliceName);
        return qoSSlicingStore.getSlice(sliceName);
    }

    /**
     * Updates QosSlice status to the store.
     *
     * @param qoSData the QosSlice
     * @param state the new state to the QosSlice
     */
    private void updateVplsStatus(QosSliceData qoSData, QosSliceData.State state) {
        qoSData.state(state);
        qoSSlicingStore.updateSlice(qoSData);
    }

    class P4SwitchListener implements PiPipeconfWatchdogListener {

        @Override
        public void event(PiPipeconfWatchdogEvent event) {
            if (event.type() == PiPipeconfWatchdogEvent.Type.PIPELINE_READY) {
                // Check if Device is P4 Switch
                Device device = deviceService.getDevice(event.subject());
                if (device == null) {
                    log.error("Device {} not found", event.subject().toString());
                    return;
                }
                boolean p4Switch = "p4runtime".equalsIgnoreCase(device.annotations().value("protocol"));
                if (!p4Switch) return;

                PiTableId meterTableId = PiTableId.of("c_ingress.m_filter");

                PiMatchFieldId meterTagMatchFieldId = PiMatchFieldId.of("meta.meter_tag");

                PiCriterion piCriterion = PiCriterion.builder()
                        .matchExact(meterTagMatchFieldId, 0)
                        .build();

                PiActionId meterFilterActionId = PiActionId.of("c_ingress.set_ecn");
                PiAction piAction = PiAction.builder()
                        .withId(meterFilterActionId)
                        .build();

                FlowRule rule = DefaultFlowRule.builder()
                        .forDevice(event.subject())
                        .forTable(meterTableId)
                        .fromApp(appId)
                        .withPriority(65535) // Max priority
                        .makePermanent()
                        .withSelector(DefaultTrafficSelector.builder().matchPi(piCriterion).build())
                        .withTreatment(DefaultTrafficTreatment.builder().piTableAction(piAction).build())
                        .build();

                log.info("Inserting Meter Filter rule on P4 switch {}", event.subject());
                flowRuleService.applyFlowRules(rule);
            }
        }
    }

    /**
     * A listener for intent events.
     * Updates a QosSlice if an interface added or removed.
     */
    class SliceIntentListener implements IntentListener {
        @Override
        public void event(IntentEvent event) {
            if (event.type().equals(IntentEvent.Type.INSTALL_REQ)) {
                Intent intent = event.subject();
                if (intent.key().toString().split(SEPARATOR)[2].equals(PREFIX_UNICAST)
                        && intent.appId().equals(appIdvpls)){
                    String vplsName = intent.key().toString().split(SEPARATOR)[0]+
                            SEPARATOR+
                            intent.key().toString().split(SEPARATOR)[1];
                    QosSliceData qoSData = qoSSlicingStore.getAllSlice().stream()
                                    .filter(s -> s.getVplsName().equals(vplsName))
                                    .findFirst()
                                    .orElse(null);
                    if (qoSData == null ) {
                                return;
                    }
                    updateVplsStatus(qoSData, QosSliceData.State.UPDATING);
                }
            }
            else if (event.type().equals(IntentEvent.Type.WITHDRAWN)){
                Intent intent = event.subject();
                if (intent.key().toString().split(SEPARATOR)[2].equals(PREFIX_UNICAST)
                        && intent.appId().equals(appIdvpls)){
                    String vplsName = intent.key().toString().split(SEPARATOR)[0]+SEPARATOR+
                            intent.key().toString().split(SEPARATOR)[1];
                    if ( vpls.getVpls(vplsName) != null) {
                        log.info("dentro if remove return");
                        return;
                    }
                    QosSliceData qoSData = qoSSlicingStore.getAllSlice().stream()
                            .filter(s -> s.getVplsName().equals(vplsName))
                            .findFirst()
                            .orElse(null);
                    if (qoSData == null ) {
                        return;
                    }
                    //log.info("dentro altro if remove rimozione");
                    updateVplsStatus(qoSData, QosSliceData.State.REMOVING);
                    qoSSlicingStore.removeSlice(qoSData);
                }
            }
        }
    }

    /**
     * Store delegate for QosSlice store.
     * Handles QosSlice store event and generate QosSlice operation according to event
     * type.
     */
    class OperationHandler implements StoreDelegate<QosSliceStoreEvent> {

        @Override
        public void notify(QosSliceStoreEvent event) {
            QosSliceOperation sliceOperation;
            QosSliceOperation.Operation op;
            QosSliceData qoSData = event.subject();
            switch (event.type()) {
                case ADD:
                    op = QosSliceOperation.Operation.ADD;
                    break;
                case REMOVE:
                    op = QosSliceOperation.Operation.REMOVE;
                    break;
                case UPDATE:
                    if (qoSData.state() == QosSliceData.State.FAILED ||
                            qoSData.state() == QosSliceData.State.ADDED ||
                            qoSData.state() == QosSliceData.State.REMOVED) {
                        // Update the state only. Nothing to do if it is updated
                        // to ADDED, REMOVED or FAILED
                        op = null;
                    } else {
                        op = QosSliceOperation.Operation.UPDATE;
                    }
                    break;
                default:
                    log.warn(UNSUPPORTED_STORE_EVENT_TYPE, event.type());
                    return;
            }
            if (op != null) {
                sliceOperation= QosSliceOperation.of(qoSData, op);
                operationService.submit(sliceOperation);
            }
        }
    }
}


