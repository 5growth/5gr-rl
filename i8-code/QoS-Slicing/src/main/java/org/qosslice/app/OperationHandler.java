package org.qosslice.app;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import org.onosproject.net.pi.model.PiMeterId;
import org.onosproject.net.pi.model.PiMeterModel;
import org.onosproject.net.pi.model.PiPipeconf;
import org.onosproject.net.pi.runtime.PiMeterBand;
import org.onosproject.net.pi.runtime.PiMeterCellConfig;
import org.onosproject.net.pi.runtime.PiMeterCellId;
import org.onosproject.net.pi.service.PiPipeconfService;
import org.onosproject.p4runtime.api.P4RuntimeClient;
import org.onosproject.p4runtime.api.P4RuntimeController;
import org.qosslice.app.api.*;
import org.onlab.packet.MacAddress;
import org.onosproject.cluster.*;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.*;
import org.onosproject.net.behaviour.QueueDescription;
import org.onosproject.net.flow.*;
import org.onosproject.net.flow.criteria.Criterion;
import org.onosproject.net.flow.instructions.Instruction;
import org.onosproject.net.host.HostService;
import org.onosproject.net.intent.*;
import org.onosproject.net.intent.util.IntentFilter;
import org.onosproject.net.intf.Interface;
import org.onosproject.net.meter.*;
import org.onosproject.vpls.VplsManager;
import org.onosproject.vpls.api.*;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.onosproject.core.CoreService;
import org.onlab.util.Tools;
import org.onosproject.vpls.api.VplsData;
import org.onosproject.net.device.*;

import org.onosproject.net.behaviour.QueueConfigBehaviour;
import org.onosproject.net.behaviour.QosConfigBehaviour;
import org.onosproject.net.behaviour.*;
import org.onlab.util.Bandwidth;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


import static org.onlab.util.BoundedThreadPool.newFixedThreadPool;
import static org.onlab.util.Tools.groupedThreads;
import static org.slf4j.LoggerFactory.getLogger;

import org.qosslice.app.utility.FlowRuleUtility;
import org.qosslice.app.utility.Intent.IntentUtility;
import org.qosslice.app.utility.MeterUtility;



@Component(immediate = true, service = OperationService.class)
public final class OperationHandler implements OperationService {

    private static final int NUM_THREADS = 4;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected LeadershipService leadershipService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected ClusterService clusterService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected CoreService coreService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected IntentService intentService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected HostService hostService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected FlowRuleService flowRuleService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected MeterService meterService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected DeviceService deviceService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected Vpls vpls;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    protected QosSliceStore qoSSlicingStore;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    private PiPipeconfService piPipeconfService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY)
    P4RuntimeController p4RuntimeController;

    private final Logger log = getLogger(getClass());
    protected Map<String, Deque<QosSliceOperation>> pendingSliceOperations;
    protected final Map<String, QosSliceOperation> runningOperations = Maps.newHashMap();
    protected ScheduledExecutorService schedulerExecutor;
    protected ExecutorService workerExecutor;
    protected ApplicationId appId, appIdvpls;
    protected boolean isLeader;
    protected NodeId localNodeId;
    protected LeadershipEventListener leadershipEventListener;
    protected Map<DeviceId, Set<MeterId>> devMetersDB = Maps.newHashMap();
    protected Map<String, List<FlowRule>> sliceFlowDB = Maps.newHashMap();
    protected Map<String, Set<MeterId>> sliceMeterIdDB = Maps.newHashMap();
    private static final String SEPARATOR = "-";

    private static final String P4_METER_NAME = "c_ingress.my_meter";


    @Activate
    public void activate() {
        appId = coreService.registerApplication(QosSliceManager.QOSSLICE_APP);
        appIdvpls = coreService.registerApplication(VplsManager.VPLS_APP);
        localNodeId = clusterService.getLocalNode().id();
        leadershipEventListener = new InternalLeadershipListener();
        leadershipService.addListener(leadershipEventListener);
        leadershipService.runForLeadership(appId.name());
        pendingSliceOperations = Maps.newConcurrentMap();


        // Thread pool for QosSliceOperationExecutor
        workerExecutor = newFixedThreadPool(NUM_THREADS,
                groupedThreads("QoS_Sclice",
                        "worker-%d",
                        log));
        // A single thread pool for QosSliceOperationScheduler
        schedulerExecutor = Executors.newScheduledThreadPool(1,
                groupedThreads("QoS_Sclice",
                        "scheduler-%d",
                        log));
        // Start the scheduler
        schedulerExecutor.scheduleAtFixedRate(new OperationScheduler(),
                0,
                500,
                TimeUnit.MILLISECONDS);

    }

    @Deactivate
    public void deactivate() {
        pendingSliceOperations.clear();
        devMetersDB.clear();
        sliceFlowDB.clear();
        runningOperations.clear();
    }

    @Override
    public void submit(QosSliceOperation qoSSlicingOperation) {
        addOperation(qoSSlicingOperation);
    }

    /**
     * Adds a Slice operation to the queue of pending operations.
     *
     * @param qoSSlicingOperation the Slice operation to add
     */
    private void addOperation(QosSliceOperation qoSSlicingOperation) {

        QosSliceData qoSData = qoSSlicingOperation.vpls();

        pendingSliceOperations.compute(qoSData.getQosName(), (name, opQueue) -> {
            opQueue = opQueue == null ? Queues.newArrayDeque() : opQueue;
            // If the operation already exist in queue, ignore it.
            if (opQueue.contains(qoSSlicingOperation)) {
                return opQueue;
            }
            opQueue.add(qoSSlicingOperation);
            return opQueue;
        });
    }

    /**
     * Optimizes the Slice operation queue and return a single Slice operation to
     * execute.
     *
     * @param operations the queue to be optimized
     * @return optimized Slice operation from the queue
     */
    protected static QosSliceOperation getOptimizedSliceOperation(Deque<QosSliceOperation> operations) {

        if (operations.isEmpty()) {
            return null;
        }
        // no need to optimize if the queue contains only one operation
        if (operations.size() == 1) {
            return operations.getFirst();
        }
        final QosSliceOperation firstOperation = operations.peekFirst();
        final QosSliceOperation lastOperation = operations.peekLast();
        final QosSliceOperation.Operation firstOp = firstOperation.op();
        final QosSliceOperation.Operation lastOp = lastOperation.op();

        if (firstOp.equals(QosSliceOperation.Operation.REMOVE)) {
            if (lastOp.equals(QosSliceOperation.Operation.REMOVE)) {
                // case 1: both first and last operation are REMOVE; do remove
                return firstOperation;
            } else if (lastOp.equals(VplsOperation.Operation.ADD)) {
                // case 2: if first is REMOVE, and last is ADD; do update
                return QosSliceOperation.of(lastOperation.vpls(),
                        QosSliceOperation.Operation.UPDATE);
            } else {
                // case 3: first is REMOVE, last is UPDATE; do update
                return lastOperation;
            }
        } else if (firstOp.equals(VplsOperation.Operation.ADD)) {
            if (lastOp.equals(VplsOperation.Operation.REMOVE)) {
                // case 4: first is ADD, last is REMOVE; nothing to do
                return null;
            } else if (lastOp.equals(VplsOperation.Operation.ADD)) {
                // case 5: both first and last are ADD, do add
                return QosSliceOperation.of(lastOperation.vpls(),
                        QosSliceOperation.Operation.ADD);
            } else {
                // case 6: first is ADD and last is update, do add
                return QosSliceOperation.of(lastOperation.vpls(),
                        QosSliceOperation.Operation.ADD);
            }
        } else {
            if (lastOp.equals(QosSliceOperation.Operation.REMOVE)) {
                // case 7: last is remove, do remove
                return lastOperation;
            } else if (lastOp.equals(VplsOperation.Operation.ADD)) {
                // case 8: do update only
                return QosSliceOperation.of(lastOperation.vpls(),
                        QosSliceOperation.Operation.UPDATE);
            } else {
                // case 9: from UPDATE to UPDATE
                // only need last UPDATE operation
                return QosSliceOperation.of(lastOperation.vpls(),
                        QosSliceOperation.Operation.UPDATE);
            }
        }

    }


    /**
     * Scheduler for Slice operation.
     * Processes a batch of Slice operations in a period.
     */
    class OperationScheduler implements Runnable {
        private static final String UNKNOWN_STATE =
                "Unknown state {} for success consumer";
        private static final String OP_EXEC_ERR =
                "Error when executing Slice operation {}, error: {}";

        /**
         * Process a batch of Slice operations.
         */
        @Override
        public void run() {
            Set<String> vplsNames = pendingSliceOperations.keySet();

            vplsNames.forEach(vplsName -> {

                QosSliceOperation operation;
                synchronized (runningOperations) {
                    // Only one operation for a QosSlice at the same time
                    if (runningOperations.containsKey(vplsName)) {
                        return;
                    }
                    Deque<QosSliceOperation> operations = pendingSliceOperations.remove(vplsName);

                    operation = getOptimizedSliceOperation(operations);

                    if (operation == null) {
                        // Nothing to do, this only happened when we add a VPLS
                        // and remove it before batch operations been processed.
                        return;
                    }
                    runningOperations.put(vplsName, operation);

                }

                SliceOperationExecutor operationExecutor =
                        new SliceOperationExecutor(operation);
                operationExecutor.setConsumers(
                        (sliceOperation) -> {
                            // Success consumer
                            QosSliceData qoSData = sliceOperation.vpls();
                            //log.debug("QoSSlice operation success: {}", sliceOperation);
                            switch (qoSData.state()) {
                                case ADDING:
                                case UPDATING:
                                    qoSData.state(QosSliceData.State.ADDED);
                                    qoSSlicingStore.updateSlice(qoSData);
                                    break;
                                case REMOVING:
                                    // The QoSSlice information does not exists in
                                    // store. No need to update the store.
                                    break;
                                default:
                                    log.warn(UNKNOWN_STATE, qoSData.state());
                                    qoSData.state(QosSliceData.State.FAILED);
                                    break;
                            }
                            runningOperations.remove(vplsName);
                        },
                        (OperationException) -> {
                            // Error consumer
                            QosSliceOperation qoSSlicingOperation =
                                    OperationException.qosSliceOperation();
                            log.warn(OP_EXEC_ERR,
                                    qoSSlicingOperation.toString(),
                                    OperationException.getMessage());
                            QosSliceData qoSData = qoSSlicingOperation.vpls();
                            qoSData.state(QosSliceData.State.FAILED);
                            qoSSlicingStore.updateSlice(qoSData);
                            runningOperations.remove(vplsName);
                        });

                log.debug("Applying operation: {}", operation);
                workerExecutor.execute(operationExecutor);
            });
        }
    }

    /**
     * Direction for Intent installation.
     */
    private enum Direction {
        ADD,
        REMOVE
    }

    /**
     * Slice operation executor.
     * Installs, updates or removes Intents according to the given Slice operation.
     */
    class SliceOperationExecutor implements Runnable {
        private static final String UNKNOWN_OP = "Unknown operation.";
        private static final String UNKNOWN_INTENT_DIR = "Unknown Intent install direction.";
        private static final int OPERATION_TIMEOUT = 10;
        private QosSliceOperation qoSSlicingOperation;
        private Consumer<QosSliceOperation> successConsumer;
        private Consumer<OperationException> errorConsumer;
        private OperationException error;

        public SliceOperationExecutor(QosSliceOperation qoSSlicingOperation) {
            this.qoSSlicingOperation = qoSSlicingOperation;
            this.error = null;
        }

        /**
         * Sets success consumer and error consumer for this executor.
         *
         * @param successConsumer the success consumer
         */
        public void setConsumers(Consumer<QosSliceOperation> successConsumer,
                                 Consumer<OperationException> errorConsumer) {

            this.successConsumer = successConsumer;
            this.errorConsumer = errorConsumer;

        }

        private Set<Host> hostsFromVpls(){

            VplsData vplsData = vpls.getVpls(qoSSlicingOperation.vpls().getVplsName());

            Set<Interface> interfaces = vplsData.interfaces();

            return interfaces.stream()
                    .map(this::hostsFromInterface)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }


        private Set<Host> hostsFromInterface(Interface iface) {
            return hostService.getConnectedHosts(iface.connectPoint())
                    .stream()
                    .filter(host -> host.vlan().equals(iface.vlan()))
                    .collect(Collectors.toSet());
        }

        /**
         * Applies Intents synchronously with a specific direction.
         *
         * @param intents   the Intents
         * @param direction the direction
         */
        private void applyIntentsSync(Set<Intent> intents, Direction direction) {

            Set<Key> pendingIntentKeys = intents.stream()
                    .map(Intent::key).collect(Collectors.toSet());

            IntentCompleter completer;

            switch (direction) {
                case ADD:
                    completer = new IntentCompleter(pendingIntentKeys,
                            IntentEvent.Type.INSTALLED);
                    intentService.addListener(completer);
                    intents.forEach(intentService::submit);
                    break;
                case REMOVE:
                    completer = new IntentCompleter(pendingIntentKeys,
                            IntentEvent.Type.WITHDRAWN);
                    intentService.addListener(completer);
                    intents.forEach(intentService::withdraw);
                    break;
                default:
                    this.error = new OperationException(this.qoSSlicingOperation, UNKNOWN_INTENT_DIR);
                    return;
            }
            try {
                // Wait until Intent operation completed
                completer.complete();
            } catch (OperationException e) {
                this.error = e;
            } finally {
                intentService.removeListener(completer);
            }
        }


        @Override
        public void run() {
            switch (qoSSlicingOperation.op()) {
                case REMOVE:
                    removeSliceIntents();
                    break;
                case UPDATE:
                    updateSliceIntents();
                    break;
                default:
                    break;
            }

            if (this.error != null) {
                errorConsumer.accept(this.error);
            } else {
                successConsumer.accept(qoSSlicingOperation);
            }
        }

        private Map<DeviceId, Set<MeterId>> selectMeterDev(){
            //log.info("dentro selectMeterDev()");

            Map<DeviceId, Set<MeterId>> select = Maps.newHashMap();

            //log.info("select"+select.toString());

            devMetersDB.forEach((deviceId, meterIds) -> {

                sliceMeterIdDB.get(qoSSlicingOperation.vpls().getQosName()).forEach(meterId -> {

                    if(meterIds.contains(meterId)){

                        if(select.containsKey(deviceId)){

                        select.get(deviceId).add(meterId);}

                        else{
                            Set<MeterId> mid = new HashSet<>();
                            mid.add(meterId);
                            select.put(deviceId, mid);

                        }

                    }

                });
            });

            return select;
        }


        /**
         * Retrieves installed Intents from IntentService which related to
         * specific QoSSlice.
         *
         * @return the Intents which related to the QoSSlice
         */
        private Set<Intent> getCurrentIntents(String vplsName) {

            return Tools.stream(intentService.getIntents())
                    .filter(intent -> intent.key().toString().startsWith(vplsName))
                    .collect(Collectors.toSet());
        }

        /**
         * Updates Intents of the QoSSlice.
         */
        private void updateSliceIntents() {

            // check which part we need to update
            // if we update host only, we don't need to reinstall
            // every Intents

            //log.info("***************** UPDATE SLICE INTENT *****************");
            IntentFilter intentFilter = new IntentFilter(intentService, flowRuleService);
            //Set<Intent> targetUniIntents= Sets.newHashSet();
            List<FlowRule> flowRules = new ArrayList<>();
            QosSliceData qoSData = qoSSlicingOperation.vpls();
            Set<Intent> currentIntents = getCurrentIntents(qoSSlicingOperation.vpls().getVplsName());

            // Compares unicast Intents
            Set<Intent> currentUniIntents = currentIntents.stream()
                    .filter(intent -> intent instanceof MultiPointToSinglePointIntent)
                    .collect(Collectors.toSet());

            if (!currentUniIntents.isEmpty()) {

                if (qoSData.getMeter()) {
                   // log.info("############### Dentro case installMeter ################");
                    installMeter(currentUniIntents, intentFilter, sliceFlowDB.get(qoSData.getQosName()));
                }
                else  {
                    //log.info("############### Dentro case RemoveMeter ################");
                }

                try {
                    if (sliceFlowDB.containsKey(qoSData.getQosName())) {
                        sliceFlowDB.get(qoSData.getQosName()).forEach(flowRule -> flowRuleService.removeFlowRules(flowRule));
                        sliceFlowDB.remove(qoSData.getQosName());
                    }
                } catch (IllegalArgumentException e) {
                    log.error(e.toString());
                }

                /*
                if (qoSData.getQueue() != null) {

                    //log.info("############### Dentro case Queue ###############");
                    flowRules.addAll(installFlowQos(currentUniIntents, intentFilter));
                    flowRules.forEach(flowRule -> flowRuleService.applyFlowRules(flowRule));
                    if (sliceFlowDB.containsKey(qoSData.getQosName())) {
                        sliceFlowDB.get(qoSData.getQosName()).addAll(flowRules);

                    } else {
                        sliceFlowDB.put(qoSData.getQosName(), flowRules);
                    }
                } else {
                    try {
                        if (sliceFlowDB.containsKey(qoSData.getQosName())) {
                            //log.info("##################### DENTRO REMOVE QOS #####################");

                            sliceFlowDB.get(qoSData.getQosName()).forEach(flowRule -> flowRuleService.removeFlowRules(flowRule));
                            sliceFlowDB.remove(qoSData.getQosName());
                           // log.info("SliceFLOWDB" + sliceFlowDB.toString());

                        }
                    } catch (IllegalArgumentException e) {
                        //log.info(" Slice Flow Qos DB not inizialize ");
                    }
                }
                 */

                Set<Intent> targetUniIntents = IntentUtility.buildUniIntents(vpls.getVpls(
                        qoSSlicingOperation.vpls().getVplsName()),
                        qoSSlicingOperation.vpls(),
                        hostsFromVpls(),
                        appId);

                applyIntentsSync(currentUniIntents, Direction.REMOVE);
                applyIntentsSync(targetUniIntents, Direction.ADD);

                if(!qoSData.getMeter()){

                    removeMeter();
                }
            }
        }

        //TODO forse non serve
        private Set<DeviceId> deviceFromIntent(Set<Intent> Intents, IntentFilter intentFilter) {

            //List<DeviceId> deviceIds = new ArrayList<>();
            Set<DeviceId> deviceIds = Sets.newHashSet();

            Intents.stream().map(Intent::key).forEach(key -> {

                intentFilter.readIntentFlows(intentService.getInstallableIntents(key)).
                        forEach(flowEntries1 -> flowEntries1.forEach(flowEntry -> {

                            if (!deviceIds.contains(flowEntry.deviceId())) {

                                deviceIds.add(flowEntry.deviceId());

                            }
                        }));

            });

            return deviceIds;
        }

        private List<FlowRule> installFlowQos(Set<Intent> currentMtoS, IntentFilter intentFilter) {

            HashMap<DeviceId, HashMap<List<MacAddress>, List<PortNumber>>> devToMacsPorts = new HashMap<DeviceId, HashMap<List<MacAddress>, List<PortNumber>>>();
            List<FlowRule> flowQos = new ArrayList<>();

            currentMtoS.stream().map(Intent::key).forEach(key -> {

                intentFilter.readIntentFlows(intentService.getInstallableIntents(key)).
                        forEach(flowEntries1 -> flowEntries1.forEach(flowEntry -> {

                            //log.info("FLOW VALUE"+flowEntry.toString());

                            if (!devToMacsPorts.containsKey(flowEntry.deviceId())) {

                                flowEntry.treatment().immediate().forEach(instruction -> {

                                    if (instruction.type().equals(Instruction.Type.OUTPUT)) {

                                        List<PortNumber> ports = new ArrayList<>();
                                        List<MacAddress> adds = new ArrayList<>();
                                        HashMap<List<MacAddress>, List<PortNumber>> Macport = new HashMap<>();

                                        ports.add(PortNumber.fromString(instruction.toString().substring(7)));

                                        flowEntry.selector().criteria().stream().forEach(criterion -> {

                                            if (criterion.type().equals(Criterion.Type.ETH_DST)) {
                                                adds.add(MacAddress.valueOf(criterion.toString().substring(8)));


                                            }
                                        });
                                        Macport.put(adds, ports);

                                        devToMacsPorts.put(flowEntry.deviceId(), Macport);

                                    }
                                });

                            } else {
                                flowEntry.treatment().immediate().forEach(instruction -> {

                                    flowEntry.selector().criteria().stream().forEach(criterion -> {

                                        if (instruction.type().equals(Instruction.Type.OUTPUT)) {

                                            if (criterion.type().equals(Criterion.Type.ETH_DST)) {
                                                List<PortNumber> ports = new ArrayList<>();
                                                List<MacAddress> adds = new ArrayList<>();
                                                ports.add(PortNumber.fromString(instruction.toString().substring(7)));
                                                adds.add(MacAddress.valueOf(criterion.toString().substring(8)));

                                                devToMacsPorts.get(flowEntry.deviceId()).put(adds, ports);

                                                // log.info("else"+devToport.toString());
                                            }
                                        }
                                    });
                                });

                            }

                        }));

            });

            flowQos = FlowRuleUtility.createFlowRule(devToMacsPorts, appId);
            flowQos.forEach(flowRule -> flowRuleService.applyFlowRules(flowRule));
            //log.info("Flow Rule" + flowQos.toString());
            return flowQos;

        }

        private Long extractP4DeviceId(URI uri) {
            if (uri == null) {
                return null;
            }
            final String DEVICE_ID_PARAM = "device_id=";
            String[] segments = uri.getRawQuery().split("&");
            try {
                for (String s : segments) {
                    if (s.startsWith(DEVICE_ID_PARAM)) {
                        return Long.parseUnsignedLong(
                                URLDecoder.decode(
                                        s.substring(DEVICE_ID_PARAM.length()), StandardCharsets.UTF_8));
                    }
                }
            } catch (NumberFormatException e) {
                log.error("Invalid P4Runtime-internal device_id in URI {}: {}",
                        uri, e.toString());
            }
            log.error("Missing P4Runtime-internal device_id in URI {}", uri);
            return null;
        }

        private long mapToP4ID(long openflowId, long outPort) {
            try {
                String meterSTring = Long.toUnsignedString(openflowId);
                int pos = meterSTring.lastIndexOf("0" + Long.toUnsignedString(outPort));
                String sliceId = meterSTring.substring(0, pos);
                System.out.println(sliceId);
                return 48L * (Long.parseUnsignedLong(sliceId) - 1) + outPort;
            } catch (Exception e) {
                log.error("Error mapping MeterId {} with Output Port {}", openflowId, outPort);
                return 1L;
            }
        }

        public boolean installP4Meter(DeviceId deviceId, int index, long rate, long burst) {
            try {
                P4RuntimeClient client = p4RuntimeController.get(deviceId);
                PiMeterBand band = new PiMeterBand(rate, burst);
                PiMeterCellConfig piMeterCellConfig = PiMeterCellConfig.builder()
                        .withMeterBand(band)
                        .withMeterBand(band)
                        .withMeterCellId(PiMeterCellId.ofIndirect(PiMeterId.of(P4_METER_NAME), index))
                        .build();

                String mgmtUri = deviceService.getDevice(deviceId).annotations().value("managementAddress");
                if (mgmtUri == null || mgmtUri.isEmpty()) {
                    log.error("No Management URI for P4 Device {}", deviceId.toString());
                    return false;
                }
                long p4DeviceId = extractP4DeviceId(URI.create(mgmtUri));
                PiPipeconf pipeconf = piPipeconfService.getPipeconf(deviceId).orElse(null);
                if (pipeconf == null) {
                    log.error("No Pipeconf for Device {}", deviceId.toString());
                    return false;
                }
                final boolean result = client.write(p4DeviceId, pipeconf)
                        .modify(piMeterCellConfig).submitSync().isSuccess();
                return result;
            } catch (Exception e) {
                log.error("Error installing P4 Meter in DeviceId: {}", deviceId);
                log.error(e.getMessage());
            }
            return false;
        }

        private PiMeterModel retrievePiMeter(DeviceId deviceId) throws Exception {
            PiPipeconf piPipeconf = piPipeconfService.getPipeconf(deviceId).orElse(null);
            if (piPipeconf == null) {
                log.error("No Pipeconf for Device {}", deviceId.toString());
                return null;
            }
            return piPipeconf.pipelineModel().meter(PiMeterId.of(P4_METER_NAME)).orElse(null);
        }

        private void createP4Meter(DeviceId deviceId, MeterId meterId, long outPort, long rate, long burst) {
            long p4MeterId = mapToP4ID(meterId.id(), outPort);
            try {
                int meter_id = (int) p4MeterId;
                if (meter_id < 0) {
                    log.error("MeterIndex {} not allowed, cant be lower than 0", meter_id);
                    return;
                }
                // TODO meter_name as param, also read (once it works) meter name to check if it exists
                PiMeterModel meter = retrievePiMeter(deviceId);
                if (meter == null) {
                    log.error("Meter {} not found in DeviceId {}", P4_METER_NAME, deviceId.toString());
                    return;
                }
                if (meter_id > meter.size() - 1) {
                    log.error("MeterIndex {} not allowed, can't be higher than {}", meter_id, meter.size() - 1);
                    return;
                }
                final boolean result = installP4Meter(deviceId, meter_id, rate, burst);

                if (result) {
                    log.info("Created P4 Meter {} in DeviceId {}", meter_id, deviceId.toString());
                } else {
                    log.error("Error creating P4 Meter {} in Device {}", meter_id, deviceId.toString());
                }
            } catch (Exception e) {
                log.error("Error creating P4 Meter {} in DeviceId {}: " + e.getMessage(), meterId.toString(), deviceId.toString());
            }
        }

        private void installMeter(Set<Intent> currentMtoS, IntentFilter intentFilter, List<FlowRule> qosFlow) {

            HashMap<DeviceId, Set<PortNumber>> devToport = new HashMap<>();

            if (qosFlow == null || qosFlow.isEmpty()) {
                currentMtoS.stream().map(Intent::key).forEach(key -> {

                    intentFilter.readIntentFlows(intentService.getInstallableIntents(key)).
                            forEach(flowEntries1 -> flowEntries1.forEach(flowEntry -> {

                                devToport.putAll(FlowRuleUtility.devToPort(devToport, flowEntry));
                            }));
                });

            } else {
                //log.info("DENTRO ELSONE NUOVO");
                qosFlow.forEach(flowRule -> {
                    devToport.putAll(FlowRuleUtility.devToPort(devToport, flowRule));
                });
            }
            Set<MeterId> mids = Sets.newHashSet();
            devToport.keySet().forEach(deviceId -> {

                // Check if Device is P4 Switch
                Device device = deviceService.getDevice(deviceId);
                if (device == null) {
                    log.error("Device {} not found", deviceId.toString());
                    return;
                }
                boolean p4Switch = "p4runtime".equalsIgnoreCase(device.annotations().value("protocol"));
                MeterRequest request = MeterUtility.buildRequestMeter(
                        qoSSlicingOperation.vpls(),
                        deviceId,
                        appId);
                if (p4Switch && request.unit() == Meter.Unit.KB_PER_SEC) {
                    log.error("Error creating P4 Meter in Device {}", deviceId.toString(), new Exception("Rate Limiting by other unit than packets is not supported atm in P4 switches"));
                    return;
                }
                devToport.get(deviceId).forEach(portNumber -> {
                    MeterId meterId = ID_Meter.buildMeterId(MeterId.meterId(Long.parseLong(qoSSlicingOperation.vpls().getVplsName().split(SEPARATOR)[1])), portNumber);
                    devMetersDB.computeIfAbsent(deviceId, s -> Sets.newHashSet()).add(meterId);
                    mids.add(meterId);
                    if (p4Switch) {
                        Band band = request.bands().stream().findFirst().orElse(null);
                        if (band == null) {
                            log.error("No band found for Slice {}", qoSSlicingOperation.vpls().getVplsName());
                        }
                        createP4Meter(deviceId, meterId, portNumber.toLong(), band.rate(), band.burst());
                    } else {
                        meterService.submitWithId(request, meterId);
                    }
                });
            });
            //log.info("meter id"+mids.toString());
            sliceMeterIdDB.put(qoSSlicingOperation.vpls().getQosName(), mids);
            //log.info("METER" + devMetersDB.toString());
            //log.info("sliceMetersIdDB"+sliceMeterIdDB.toString());
        }

        private void removeSliceIntents() {

            Set<Intent> currentIntents = getCurrentIntents(qoSSlicingOperation.vpls().getVplsName());
            //IntentFilter intentFilter = new IntentFilter(intentService, flowRuleService);

            QosSliceData qoSData = qoSSlicingOperation.vpls();

            qoSData.setMeter(false);

            qoSData.setQueue(null);

            try {
                if (sliceFlowDB.containsKey(qoSData.getQosName())) {
                    sliceFlowDB.get(qoSData.getQosName()).forEach(flowRule ->
                            flowRuleService.removeFlowRules(flowRule));
                    sliceFlowDB.remove(qoSData.getQosName());
                    }
                } catch (IllegalArgumentException e) {
                    log.info(" Slice Flow Qos DB not inizialize ");
            }

            if(vpls.getVpls(qoSSlicingOperation.vpls().getVplsName()) != null ){
                Set<Intent> currentUniIntents = currentIntents.stream()
                        .filter(intent -> intent instanceof MultiPointToSinglePointIntent)
                        .collect(Collectors.toSet());

                Set<Intent> targetUniIntents = IntentUtility.buildUniIntents(vpls.getVpls(
                        qoSSlicingOperation.vpls().getVplsName()),
                        qoSData,
                        hostsFromVpls(),
                        appIdvpls
                );

                applyIntentsSync(currentUniIntents, Direction.REMOVE);
                applyIntentsSync(targetUniIntents, Direction.ADD);
            }

            removeMeter();
        }

        private void removeMeter() {
            if (sliceMeterIdDB.containsKey(qoSSlicingOperation.vpls().getQosName())) {

                Map<DeviceId, Set<MeterId>> copydevMetersDB = Maps.newHashMap();

                copydevMetersDB.putAll(selectMeterDev());

                sliceMeterIdDB.get(qoSSlicingOperation.vpls().getQosName()).forEach(meterId -> {

                     copydevMetersDB.forEach( (deviceId, meterIds) -> {

                         meterIds.forEach(meterId1 -> {

                             if(meterId1.equals(meterId)) {
                                 // Check if Device is P4 Switch
                                 Device device = deviceService.getDevice(deviceId);
                                 if (device == null) {
                                     log.error("Device {} not found", deviceId.toString());
                                     return;
                                 }
                                 boolean p4Switch = "p4runtime".equalsIgnoreCase(device.annotations().value("protocol"));
                                 // We cant remove P4 meters
                                 if (!p4Switch && meterService.getMeter(deviceId, meterId) != null) {
                                     Meter reMeter = meterService.getMeter(deviceId, meterId);
                                     MeterRequest request = MeterUtility.MeterRequestfromMeter(reMeter);
                                     meterService.withdraw(request, meterId);
                                 }
                             }
                         });
                    });
                });

                copydevMetersDB.forEach((deviceId, meterIds) -> {
                    meterIds.forEach(meterId -> {
                                devMetersDB.get(deviceId).remove(meterId);
                            }
                    );

                });
            }

            sliceMeterIdDB.remove(qoSSlicingOperation.vpls().getQosName());
            //log.info("devMetersDB"+devMetersDB.toString());
            //log.info("sliceMetersIdDB"+sliceMeterIdDB.toString());
        }


        /**
         * Checks if two sets of Intents are equal.
         *
         * @param intentSet1 the first set of Intents
         * @param intentSet2 the second set of Intents
         * @return true if both set of Intents are equal; otherwise false
         */
        private boolean intentSetEquals(Set<Intent> intentSet1, Set<Intent> intentSet2) {
            if (intentSet1.size() != intentSet2.size()) {
                return false;
            }
            for (Intent intent1 : intentSet1) {
                if (intentSet2.stream()
                        .noneMatch(intent2 -> IntentUtils.intentsAreEqual(intent1, intent2))) {
                    return false;
                }
            }
            return true;
        }

        private void addQos() {

            //log.info("****************** ADD QOS ********************");

            List<DeviceId> deviceIds = new ArrayList<>();
            List<Device> devices = new ArrayList<>();

            vpls.getVpls(qoSSlicingOperation.vpls().getVplsName()).interfaces().forEach(anInterface -> {

                    deviceIds.add(anInterface.connectPoint().deviceId());
            });

            deviceService.getAvailableDevices().forEach(device -> {

                if (deviceIds.contains(device.id())) {
                    devices.add(device);
                    //log.info("Type device"+device.type());

                }

            });

            //log.info("DEVICE ID"+deviceIds.toString());
            //log.info("DEVICE"+devices.toString());

            //QueueUtility.checkQos(devices);

            devices.forEach(device ->{

                    //log.info("inizio installazione");


                        QueueDescription queueDesc = DefaultQueueDescription.builder()
                                .queueId(QueueId.queueId("pippo"))
                                .maxRate(Bandwidth.bps(200))
                                .minRate(Bandwidth.bps(100))
                                //.burst(Long.valueOf(burst))
                                .build();

                        PortDescription portDesc = DefaultPortDescription.builder()
                                .withPortNumber(PortNumber.portNumber(1))
                                .isEnabled(true)
                                .build();


                        Map<Long, QueueDescription> queues = new HashMap<>();

                        queues.put(0L, queueDesc);
                        QosDescription qosDesc = DefaultQosDescription.builder()
                                .qosId(QosId.qosId("prova"))
                                .type(QosDescription.Type.HTB)
                                .maxRate(Bandwidth.bps(Long.valueOf("100000")))
                                .queues(queues)
                                .build();

                        QueueConfigBehaviour queueConfig = device.as(QueueConfigBehaviour.class);
                        QosConfigBehaviour qosConfig = device.as(QosConfigBehaviour.class);
                        PortConfigBehaviour portConfig = device.as(PortConfigBehaviour.class);

                        queueConfig.addQueue(queueDesc);
                        qosConfig.addQoS(qosDesc);
                        portConfig.applyQoS(portDesc, qosDesc);


                   // QueueConfigBehaviour queueConfig = device.as(QueueConfigBehaviour.class);

                    //QosConfigBehaviour qosConfig = device.as(QosConfigBehaviour.class);

                    //QueueDescription queue = QueueUtility.installQueue("pippo");

                    //qosConfig.addQoS()

                    //queueConfig.addQueue(queue);
            });

            //QueueUtility.checkQos(devices);

        }


        /**
         * Helper class which monitors if all Intent operations are completed.
         */
        class IntentCompleter implements IntentListener {

            private static final String INTENT_COMPILE_ERR = "Got {} from intent completer";
            private CompletableFuture<Void> completableFuture;
            private Set<Key> pendingIntentKeys;
            private IntentEvent.Type expectedEventType;

            /**
             * Initialize completer with given Intent keys and expect Intent
             * event type.
             *
             * @param pendingIntentKeys the Intent keys to wait
             * @param expectedEventType expect Intent event type
             */
            public IntentCompleter(Set<Key> pendingIntentKeys,
                                   IntentEvent.Type expectedEventType) {
                this.completableFuture = new CompletableFuture<>();
                this.pendingIntentKeys = Sets.newConcurrentHashSet(pendingIntentKeys);
                this.expectedEventType = expectedEventType;
            }

            @Override
            public void event(IntentEvent event) {
                Intent intent = event.subject();
                Key key = intent.key();
                if (!pendingIntentKeys.contains(key)) {
                    //log.info("PRIMO IF COMPLETER");
                    // ignore Intent events from other VPLS
                    return;
                }
                // Intent failed, throw an exception to completable future
                if (event.type() == IntentEvent.Type.CORRUPT ||
                        event.type() == IntentEvent.Type.FAILED) {
                    //log.info("SECONDO IF COMPLETER");
                    completableFuture.completeExceptionally(new IntentException(intent.toString()));
                    return;
                }
                // If event type matched to expected type, remove from pending
                if (event.type() == expectedEventType) {
                    //log.info("TERZO IF COMPLETER");
                    pendingIntentKeys.remove(key);
                }
                if (pendingIntentKeys.isEmpty()) {
                    //log.info("QUARTO IF COMPLETER");
                    completableFuture.complete(null);
                }
            }

            /**
             * Waits until all pending Intents completed ot timeout.
             */
            public void complete() {
                // If no pending Intent keys, complete directly
                if (pendingIntentKeys.isEmpty()) {
                    return;
                }
                try {
                    completableFuture.get(OPERATION_TIMEOUT, TimeUnit.SECONDS);
                } catch (TimeoutException | InterruptedException |
                        ExecutionException | IntentException e) {
                    // TODO: handle errors more carefully
                    log.warn(INTENT_COMPILE_ERR, e.toString());
                    throw new OperationException(qoSSlicingOperation, e.toString());
                }
            }
        }
    }


    /**
     * A listener for leadership events.
     * Only the leader can process QoS_Slice operation in the ONOS cluster.
     */
    private class InternalLeadershipListener implements LeadershipEventListener {
        private static final String LEADER_CHANGE = "Change leader to {}";

        @Override
        public void event(LeadershipEvent event) {
            switch (event.type()) {
                case LEADER_CHANGED:
                case LEADER_AND_CANDIDATES_CHANGED:
                    isLeader = localNodeId.equals(event.subject().leaderNodeId());
                    if (isLeader) {
                        log.debug(LEADER_CHANGE, localNodeId);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public boolean isRelevant(LeadershipEvent event) {
            return event.subject().topic().equals(appId.name());
        }
    }
}





