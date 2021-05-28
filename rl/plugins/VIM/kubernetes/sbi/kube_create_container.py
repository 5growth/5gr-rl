import json
from decimal import *
import logging
import bitmath
import urllib3
import yaml
from kubernetes import client, config

from sbi.kube_get_info import KubeGetInfo
from mtp_plugin_kubernetes.config.config import ConfigurationFile
from mtp_plugin_kubernetes.models.reserved_virtual_compute_virtualisation_container_reserved_virtual_network_interface \
    import \
    ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface  # noqa: F401,E501
from mtp_plugin_kubernetes.models.virtual_compute import VirtualCompute
from mtp_plugin_kubernetes.models.virtual_compute_resource_information_virtual_cpu import \
    VirtualComputeResourceInformationVirtualCPU
from mtp_plugin_kubernetes.models.virtual_compute_virtual_memory import VirtualComputeVirtualMemory


class AllocateCompute(object):
    deployment_name = ""
    service_name = ""
    container_name = ""
    image = ""
    container_port = ""
    exposed_port = ""
    deployment_yaml = ""
    service_yaml = ""
    service_protocol = ""
    external_ips = ""
    set_memory = ""
    set_cpu = ""

    def __init__(self):
        try:
            kube_conf = ConfigurationFile().config['VIM']['Server']['KubernetesConfig']
            config.load_kube_config(kube_conf)
        except Exception as ex:
            logging.error(str(ex))

    def update_deployment(self):
        self.deployment_yaml = \
            "apiVersion: extensions/v1beta1 \n\
kind: Deployment \n\
metadata: \n\
  name: " + self.deployment_name + "\n\
spec: \n\
  replicas: 1\n\
  template: \n\
    metadata:\n\
      labels:\n\
        app: " + self.container_name + "\n\
    spec:\n\
      containers:\n\
      - name: " + self.container_name + "\n\
        image: " + self.image + "\n\
        resources:\n\
            requests:\n\
                " + self.set_memory + "\n\
                " + self.set_cpu + "\n\
            limits:\n\
                " + self.set_memory + "\n\
                " + self.set_cpu + "\n\
        ports:\n\
        - containerPort: " + self.container_port + ""

    def update_service(self):
        self.service_yaml = \
            "kind: Service \n\
apiVersion: v1\n\
metadata:\n\
  name: " + self.service_name + "\n\
spec:\n\
  selector:\n\
    app: " + self.container_name + "\n\
  ports:\n\
  - protocol: " + self.service_protocol + "\n\
    port: " + self.exposed_port + "\n\
    targetPort: " + self.container_port + "\n\
  externalIPs:\n\
  - " + self.external_ips + ""

    def allocate_compute(self, compute):
        dict_metadata = {}
        if compute is not None:
            for metadata_item in compute['metadata']:
                dict_metadata[metadata_item['key']] = metadata_item['value']
            self.image = compute['vcImageId']
            self.container_name = compute['computeName']
            self.external_ips = compute['interfaceData'][0]['ipAddress']
            self.deployment_name = compute['computeName'] + "-deployment"
            self.service_name = compute['computeName'] + "-service"
            self.container_port = dict_metadata['container_port']
            self.exposed_port = dict_metadata['exposed_port']
            self.service_protocol = dict_metadata['service_protocol']
            if "memory" in dict_metadata.keys():
                self.set_memory = "memory: " + dict_metadata['memory']
            if "cpu" in dict_metadata.keys():
                self.set_cpu = "cpu: " + dict_metadata['cpu']

        self.update_deployment()
        self.update_service()
        try:
            self.create_deployment()
        except client.rest.ApiException as ex:
            if ex.status == 409:
                body = json.loads(ex.body)
                reason = body['message']
                return reason, ex.status
        except urllib3.exceptions.MaxRetryError as ex:
            reason = str(ex.reason)
            return reason, 401

        try:
            self.create_service()
        except client.rest.ApiException as ex:
            if ex.status == 409:
                body = json.loads(ex.body)
                reason = body['message']
                return reason, ex.status
        except urllib3.exceptions.MaxRetryError as ex:
            reason = str(ex.reason)
            return reason, 401

        kube_info = KubeGetInfo()
        virtual_cpu = VirtualComputeResourceInformationVirtualCPU()
        virtual_cpu.cpu_architecture = kube_info.architecture
        virtual_cpu.cpu_clock = 0
        return_cpu = Decimal(dict_metadata['cpu']).quantize(Decimal('1.'), rounding=ROUND_UP)
        virtual_cpu.num_virtual_cpu = return_cpu
        virtual_cpu.virtual_cpu_oversubscription_policy = "null"
        virtual_cpu.virtual_cpu_pinning_supported = "null"
        virtual_memory = VirtualComputeVirtualMemory()
        virtual_memory.numa_enabled = False
        virtual_memory.virtual_mem_oversubscription_policy = "null"
        memory_amount = dict_metadata['memory']

        if 'i' in memory_amount:
            if 'B' in memory_amount:
                return_memory_amount = bitmath.parse_string(memory_amount)
            else:
                return_memory_amount = bitmath.parse_string(memory_amount + "B")
        else:
            return_memory_amount = bitmath.parse_string(memory_amount + "KiB")

        virtual_memory.virtual_mem_size = return_memory_amount.KiB.format("{value:.0f}")
        interface = ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface(
            ip_address=self.external_ips)
        virtual_compute = VirtualCompute()
        virtual_compute.acceleration_capability = []
        virtual_compute.compute_id = compute['computeName']
        virtual_compute.compute_name = compute['computeName']
        virtual_compute.flavour_id = 0
        virtual_compute.host_id = ""
        virtual_compute.operational_state = "creating"
        virtual_compute.vc_image_id = self.image
        virtual_compute.virtual_cpu = virtual_cpu
        virtual_compute.virtual_disks = ""
        virtual_compute.virtual_memory = virtual_memory
        virtual_compute.virtual_network_interface = [interface]
        virtual_compute.zone_id = ConfigurationFile().config['VIM']['DomainInfo']['Zonelist']['Zone']['zoneId']
        return virtual_compute, 200

    def terminate_compute(self, compute_id):
        self.deployment_name = compute_id[0] + "-deployment"
        self.service_name = compute_id[0] + "-service"
        try:
            self.delete_service()
            self.delete_deployment()
        except client.rest.ApiException as ex:
            body = json.loads(ex.body)
            reason = body['message']
            return reason, ex.status
        except urllib3.exceptions.MaxRetryError as ex:
            reason = str(ex.reason)
            return reason, 401
        return compute_id, 200

    def create_deployment(self):
        # Create deployment
        extensions_v1beta1 = client.ExtensionsV1beta1Api()
        deployment = yaml.load(self.deployment_yaml)
        api_response = extensions_v1beta1.create_namespaced_deployment(
            body=deployment,
            namespace="default")
        return api_response

    def delete_deployment(self):
        # Delete deployment
        extensions_v1beta1 = client.ExtensionsV1beta1Api()
        api_response = extensions_v1beta1.delete_namespaced_deployment(
            name=self.deployment_name,
            namespace="default",
            body=client.V1DeleteOptions(
                propagation_policy='Foreground',
                grace_period_seconds=5))
        return api_response

    def create_service(self):
        service = yaml.load(self.service_yaml)
        corev1api = client.CoreV1Api()
        api_response = corev1api.create_namespaced_service(
            body=service,
            namespace="default")
        return api_response

    def delete_service(self):
        corev1api = client.CoreV1Api()
        api_response = corev1api.delete_namespaced_service(
            name=self.service_name,
            namespace="default",
            body=client.V1DeleteOptions(
                propagation_policy='Foreground',
                grace_period_seconds=5))
        return api_response

    def get_info_request(self, kwargs):
        response = []
        if 'computeId' in kwargs.keys():
            if kwargs['computeId'][0] != '':
                for compute in kwargs['computeId']:
                    response.append(self.get_info_compute(compute))
                return response

        api_instance = client.AppsV1Api()
        api_response = api_instance.list_namespaced_deployment(namespace="default")
        for item in api_response.items:
            response.append(self.get_info_compute(item.metadata.name.replace("-deployment", "")))
        return response

    def get_info_compute(self, compute):

        name = compute + "-deployment"
        api_instance = client.AppsV1Api()
        api_response = api_instance.read_namespaced_deployment(name, namespace="default")
        image = api_response.spec.template.spec.containers[0].image
        compute_name = api_response.spec.template.spec.containers[0].name
        resources = api_response.spec.template.spec.containers[0].resources

        if resources.limits is not None:
            if 'memory' in resources.limits:
                memory_bitmath = bitmath.parse_string((resources.limits['memory']) + "B")
                memory_reserved = memory_bitmath.KiB.format("{value:.0f}")
            else:
                memory_reserved = "0"
            if 'cpu' in resources.limits:
                if 'm' in resources.limits['cpu']:
                    cpu_str = resources.limits['cpu'].replace("m", "")
                    cpu_reserved = float(cpu_str) / 1000
                else:
                    cpu_str = resources.limits['cpu']
                    cpu_reserved = float(cpu_str)
            else:
                cpu_reserved = "0"
        else:
            cpu_reserved = "0"
            memory_reserved = "0"

        node_name = ""
        pod_ip = ""
        host_ip = ""
        status = ""
        api_instance = client.CoreV1Api()
        pods = api_instance.list_pod_for_all_namespaces()
        for pod in pods.items:
            if pod.metadata.name.startswith(compute):
                status = pod.status.phase
                node_name = pod.spec.node_name
                pod_ip = pod.status.pod_ip
                host_ip = pod.status.host_ip
                break

        name_service = compute + "-service"
        service_response = api_instance.read_namespaced_service(name_service, namespace="default")
        ip_address = service_response.spec.external_i_ps[0]
        container_port = service_response.spec.ports[0].target_port
        exposed_port = service_response.spec.ports[0].port
        service_protocol = service_response.spec.ports[0].protocol

        kube_info = KubeGetInfo()

        virtual_cpu = VirtualComputeResourceInformationVirtualCPU()
        virtual_cpu.cpu_architecture = kube_info.architecture
        virtual_cpu.cpu_clock = 0
        return_cpu = Decimal(cpu_reserved).quantize(Decimal('1.'), rounding=ROUND_UP)
        virtual_cpu.num_virtual_cpu = str(return_cpu)
        virtual_cpu.virtual_cpu_oversubscription_policy = "null"
        virtual_cpu.virtual_cpu_pinning_supported = "null"
        virtual_memory = VirtualComputeVirtualMemory()
        virtual_memory.numa_enabled = False
        virtual_memory.virtual_mem_oversubscription_policy = "null"
        memory_amount = memory_reserved

        if 'i' in memory_amount:
            if 'B' in memory_amount:
                return_memory_amount = bitmath.parse_string(memory_amount)
            else:
                return_memory_amount = bitmath.parse_string(memory_amount + "B")
        else:
            return_memory_amount = bitmath.parse_string(memory_amount + "KiB")

        virtual_memory.virtual_mem_size = return_memory_amount.KiB.format("{value:.0f}")

        interface = ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface(ip_address)
        metadata = [{"key": "container_port", "value": container_port}, {"key": "exposed_port", "value": exposed_port},
                    {"key": "service_protocol", "value": service_protocol}]
        interface.metadata = metadata

        virtual_compute = VirtualCompute()
        virtual_compute.acceleration_capability = []
        virtual_compute.compute_id = "1"
        virtual_compute.compute_name = compute
        virtual_compute.flavour_id = 0
        virtual_compute.host_id = node_name
        virtual_compute.operational_state = status
        virtual_compute.vc_image_id = self.image
        virtual_compute.virtual_cpu = virtual_cpu
        virtual_compute.virtual_disks = ""
        virtual_compute.virtual_memory = virtual_memory
        virtual_compute.virtual_network_interface = [interface]
        virtual_compute.zone_id = ConfigurationFile().config['VIM']['DomainInfo']['Zonelist']['Zone']['zoneId']
        return virtual_compute
