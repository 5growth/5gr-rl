from __future__ import print_function

import bitmath
import kubernetes
from kubernetes.client.rest import ApiException

from mtp_plugin_kubernetes.config.config import ConfigurationFile


class KubeGetInfo:
    configuration = None
    api_instance = None
    allocatable_processor = None
    allocatable_memory = None
    allocated_processor = None
    allocated_memory = None
    architecture = None
    total_memory = None
    total_processor = None

    def __init__(self):
        kube_conf = ConfigurationFile().config['VIM']['Server']['KubernetesConfig']
        self.configuration = kubernetes.config.load_kube_config(kube_conf)
        self.api_instance = kubernetes.client.CoreV1Api(kubernetes.client.ApiClient(self.configuration))
        self.get_information()

    def get_information(self):
        try:
            list_node = self.api_instance.list_node()
            items = list_node.items
            total_processor_local = 0
            total_memory_local = bitmath.Byte(0)
            for item in items:
                allocatable = item.status.allocatable
                self.architecture = str(item.status.node_info.architecture)
                total_processor_local = total_processor_local + float(allocatable['cpu'])
                allocatable_memory_str = allocatable['memory'] + "B"
                total_memory_local = total_memory_local + bitmath.parse_string(allocatable_memory_str)
            self.total_processor = str(total_processor_local)
            self.total_memory = str(total_memory_local.best_prefix().KiB.format("{value:.0f}"))

            list_pod_for_all_namespaces = self.api_instance.list_pod_for_all_namespaces()
            allocated_processor_local = 0
            allocated_memory_local = bitmath.Byte(0)
            for item in list_pod_for_all_namespaces.items:
                containers = item.spec.containers
                for container in containers:
                    requests = container.resources.requests
                    if requests is None:
                        continue
                    if 'cpu' in requests:
                        if 'm' in requests['cpu']:
                            cpu_str = requests['cpu'].replace("m", "")
                            container_cpu = float(cpu_str) / 1000
                        else:
                            cpu_str = requests['cpu']
                            container_cpu = float(cpu_str)
                        allocated_processor_local = allocated_processor_local + container_cpu
                    if 'memory' in requests:
                        memory_str = requests['memory'] + "B"
                        allocated_memory_local = allocated_memory_local + bitmath.parse_string(memory_str)
            allocated_processor_local = float("{0:.2f}".format(allocated_processor_local))
            self.allocated_processor = str(allocated_processor_local)
            self.allocated_memory = allocated_memory_local.KiB.format("{value:.0f}")
            self.allocatable_processor = str(total_processor_local - allocated_processor_local)
            allocatable_memory_local = total_memory_local - allocated_memory_local
            self.allocatable_memory = allocatable_memory_local.KiB.format("{value:.0f}")

        except ApiException as e:
            print("Exception when calling CoreV1Api->list_resource_quota_for_all_namespaces: %s\n" % e)


if __name__ == '__main__':
    kube = KubeGetInfo()
    print("totalCapacity_processor = " + kube.total_processor)
    print("allocatedCapacity_processor = " + kube.allocated_processor)
    print("availableCapacity_processor = " + kube.allocatable_processor)
    print("totalCapacity_memory = " + kube.total_memory)
    print("allocatedCapacity_memory = " + kube.allocated_memory)
    print("availableCapacity_memory = " + kube.allocatable_memory)
