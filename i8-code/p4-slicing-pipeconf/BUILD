COMPILE_DEPS = CORE_DEPS + [
    "@minimal_json//jar",
    "//protocols/p4runtime/model:onos-protocols-p4runtime-model",
    "//protocols/p4runtime/api:onos-protocols-p4runtime-api",
    "//drivers/default:onos-drivers-default",
]

osgi_jar(
    deps = COMPILE_DEPS,
)

BUNDLES = [
    "//apps/p4-slicing/pipeconf:onos-apps-p4-slicing-pipeconf",
]

onos_app(
    app_name = "org.onosproject.p4slicing.pipeconf",
    category = "Pipeconf",
    description = "Provides pipeconf for the P4 Slicer",
    included_bundles = BUNDLES,
    required_apps = [
        "org.onosproject.drivers.p4runtime",
    ],
    title = "P4 Slicer Pipeconf",
    url = "http://onosproject.org",
)
