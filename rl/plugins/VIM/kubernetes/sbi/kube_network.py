import ipaddress
import json
from decimal import *
import logging
import bitmath
import kubernetes
import urllib3
import yaml
from kubernetes import client, config

from mtp_plugin_kubernetes.config.config import ConfigurationFile
from sbi.kube_get_info import KubeGetInfo

from mtp_plugin_kubernetes.models.reserved_virtual_compute_virtualisation_container_reserved_virtual_network_interface \
    import \
    ReservedVirtualComputeVirtualisationContainerReservedVirtualNetworkInterface  # noqa: F401,E501
from mtp_plugin_kubernetes.models.virtual_compute import VirtualCompute
from mtp_plugin_kubernetes.models.virtual_compute_resource_information_virtual_cpu import \
    VirtualComputeResourceInformationVirtualCPU
from mtp_plugin_kubernetes.models.virtual_compute_virtual_memory import VirtualComputeVirtualMemory


def convert_from_dict_map(param):
    return_dict = {}
    for element in param:
        return_dict.update({element['key']: element['value']})
    return return_dict


class KubNetwork(object):
    def __init__(self):
        self.network_yaml = ""
        self.service_name = ""
        self.container_name = ""
        self.image = ""
        self.container_port = ""
        self.exposed_port = ""
        self.deployment_yaml = ""
        self.service_yaml = ""
        self.service_protocol = ""
        self.external_ips = ""
        self.set_memory = ""
        self.set_cpu = ""
        self.deployment_name = None
        try:
            kube_conf = ConfigurationFile().config['VIM']['Server']['KubernetesConfig']
            config.load_kube_config(kube_conf)
        except Exception as ex:
            logging.error(str(ex))

    def update_network(self):
        self.network_yaml = \
            "apiVersion: \"k8s.cni.cncf.io/v1\" \n\
kind: NetworkAttachmentDefinition \n\
metadata: \n\
  name: vlan-conf \n\
spec: \n\
  config: \'{ \n\
      \"cniVersion\": \"0.4.0\", \n\
      \"type\": \"vlan\", \n\
      \"master\": \"ens9\", \n\
      \"vlanId\": 10, \n\
      \"ipam\": { \n\
        \"type\": \"host-local\", \n\
        \"subnet\": \"192.171.1.0/24\", \n\
        \"rangeStart\": \"192.171.1.100\", \n\
        \"rangeEnd\": \"192.171.1.230\", \n\
        \"routes\": [{ \n\
          \"dst\": \"0.0.0.0/0\" \n\
        }] \n\
      } \n\
    }' \n\
"

    def allocate(self, network):
        if network['networkResourceType'] == 'network':
            return self._create_response_for_network(network)
        elif network['networkResourceType'] == 'subnet':
            return self._create_subnetwork(network)

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

    def create_network(self):
        # Create deployment
        extensions_v1beta1 = client.ExtensionsV1beta1Api()
        deployment = yaml.safe_load(self.network_yaml)
        api_response = extensions_v1beta1.create_namespaced_deployment(
            body=deployment,
            namespace="default")
        return api_response

    def create_namespaced_multus_network(self, body, namespace):
        path_params = {'namespace': namespace}
        query_params = []
        header_params = {'Accept': 'application/json'}
        body_params = body
        form_params = []
        local_var_files = {}
        auth_settings = ['BearerToken']
        collection_formats = {}
        local_var_params = {'async_req': ['BearerToken'], '_return_http_data_only': True}
        extensions_v1beta1 = client.ExtensionsV1beta1Api()

        return extensions_v1beta1.api_client.call_api(
            '/apis/k8s.cni.cncf.io/v1/namespaces/{namespace}/network-attachment-definitions', 'POST',
            path_params,
            query_params,
            header_params,
            body=body_params,
            post_params=form_params,
            files=local_var_files,
            response_type='',  # noqa: E501
            auth_settings=auth_settings,
            async_req=local_var_params.get('async_req'),
            _return_http_data_only=local_var_params.get('_return_http_data_only'),  # noqa: E501
            _preload_content=local_var_params.get('_preload_content', False),
            _request_timeout=local_var_params.get('_request_timeout'),
            collection_formats=collection_formats).get()

    def get_namespaced_multus_network(self, networkname, namespace):
        path_params = {'namespace': namespace, 'networkname': networkname}
        query_params = []
        header_params = None
        body_params = None
        form_params = []
        local_var_files = {}
        auth_settings = ['BearerToken']
        collection_formats = {}
        local_var_params = {'async_req': ['BearerToken'], '_return_http_data_only': True}
        extensions_v1beta1 = client.ExtensionsV1beta1Api()

        return extensions_v1beta1.api_client.call_api(
            '/apis/k8s.cni.cncf.io/v1/namespaces/{namespace}/network-attachment-definitions/{networkname}', 'GET',
            path_params,
            query_params,
            header_params,
            body=body_params,
            post_params=form_params,
            files=local_var_files,
            response_type='',  # noqa: E501
            auth_settings=auth_settings,
            async_req=local_var_params.get('async_req'),
            _return_http_data_only=local_var_params.get('_return_http_data_only'),  # noqa: E501
            _preload_content=local_var_params.get('_preload_content', False),
            _request_timeout=local_var_params.get('_request_timeout'),
            collection_formats=collection_formats).get()

    def delete_namespaced_multus_network(self, networkname, namespace):
        path_params = {'namespace': namespace, 'networkname': networkname}
        query_params = []
        header_params = None
        body_params = None
        form_params = []
        local_var_files = {}
        auth_settings = ['BearerToken']
        collection_formats = {}
        local_var_params = {'async_req': ['BearerToken'], '_return_http_data_only': True}
        extensions_v1beta1 = client.ExtensionsV1beta1Api()

        return extensions_v1beta1.api_client.call_api(
            '/apis/k8s.cni.cncf.io/v1/namespaces/{namespace}/network-attachment-definitions/{networkname}', 'DELETE',
            path_params,
            query_params,
            header_params,
            body=body_params,
            post_params=form_params,
            files=local_var_files,
            response_type='',  # noqa: E501
            auth_settings=auth_settings,
            async_req=local_var_params.get('async_req'),
            _return_http_data_only=local_var_params.get('_return_http_data_only'),  # noqa: E501
            _preload_content=local_var_params.get('_preload_content', False),
            _request_timeout=local_var_params.get('_request_timeout'),
            collection_formats=collection_formats).get()

    def list_namespaced_multus_network(self, namespace):
        path_params = {'namespace': namespace}
        query_params = []
        header_params = None
        body_params = None
        form_params = []
        local_var_files = {}
        auth_settings = ['BearerToken']
        collection_formats = {}
        local_var_params = {'async_req': ['BearerToken'], '_return_http_data_only': True}
        extensions_v1beta1 = client.ExtensionsV1beta1Api()

        return extensions_v1beta1.api_client.call_api(
            '/apis/k8s.cni.cncf.io/v1/namespaces/{namespace}/network-attachment-definitions', 'GET',
            path_params,
            query_params,
            header_params,
            body=body_params,
            post_params=form_params,
            files=local_var_files,
            response_type='',  # noqa: E501
            auth_settings=auth_settings,
            async_req=local_var_params.get('async_req'),
            _return_http_data_only=local_var_params.get('_return_http_data_only'),  # noqa: E501
            _preload_content=local_var_params.get('_preload_content', False),
            _request_timeout=local_var_params.get('_request_timeout'),
            collection_formats=collection_formats).get()

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
        service = yaml.safe_load(self.service_yaml)
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

    def _create_response_for_network(self, network):
        network_resource_name = network['networkResourceName']
        network_resource_id = network_resource_name.replace("_", "-")
        network['metadata'] = convert_from_dict_map(network['metadata'])
        vlan = ""
        if 'interpop_vlan' in network['metadata'].keys():
            vlan = network['metadata']['interpop_vlan']
        response = \
            {
                "networkData": {
                    "bandwidth": 0,
                    "isShared": False,
                    "metadata": [
                        {
                            "key": "id",
                            "value": network_resource_id
                        },
                        {
                            "key": "name",
                            "value": network_resource_name
                        },
                        {
                            "key": "admin_state_up",
                            "value": "True"
                        },
                        {
                            "key": "provider:network_type",
                            "value": "vlan"
                        },
                        {
                            "key": "provider:segmentation_id",
                            "value": vlan
                        },
                        {
                            "key": "ipv4_address_scope",
                            "value": "None"
                        },
                        {
                            "key": "ipv6_address_scope",
                            "value": "None"
                        },
                    ],
                    "networkPort": [],
                    "networkQoS": [],
                    "networkResourceId": network_resource_id,
                    "networkResourceName": network_resource_name,
                    "networkType": "vlan",
                    "operationalState": "enabled",
                    "segmentType": int(vlan),
                    "sharingCriteria": "",
                    "subnet": [],
                    "zoneId": ""
                },
                "networkPortData": None,
                "subnetData": None
            }
        return response, 201

    def _create_subnetwork(self, network):
        params = self._parse_subnetwork_create_request(network)
        body = self._create_body_for_create_multus_network(params)
        kubernetes_response = None
        try:
            kubernetes_response = KubNetwork().create_namespaced_multus_network(body=body, namespace='default')
        except kubernetes.client.exceptions.ApiException as exp:
            if exp.reason == 'Conflict':
                kubernetes_response = KubNetwork().get_namespaced_multus_network(networkname=params['networkId'],
                                                                                 namespace='default')
                # with open('tests/integration/mock_results/mock_get_namespaced_multus_network.txt', 'wb') as infile:
                #     infile.write(kubernetes_response.data)
                #     infile.close()
        response = self._create_response_for_subnetwork(json.loads(kubernetes_response.data), params)
        return response, 201

    def _create_body_for_create_multus_network(self, params):
        physical_interface = ConfigurationFile().config['VIM']['DomainInfo']['NVFIPopList']['NfviPop'][
            'inter_pop_physical_network']
        network_id = params['networkId']
        network_id = network_id.replace("_", "-")
        config_body = {'cniVersion': '0.4.0',
                       'type': 'vlan',
                       'master': physical_interface,
                       'vlanId': int(params['interpop_vlan']),
                       'ipam': {'type': 'host-local',
                                'subnet': params['cidr'],
                                'rangeStart': params['range_start'],
                                'rangeEnd': params['range_end'],
                                'routes': [{'dst': '0.0.0.0/0'}]}}
        body = {'apiVersion': 'k8s.cni.cncf.io/v1',
                'kind': 'NetworkAttachmentDefinition',
                'metadata': {'name': network_id},
                'spec': {'config': json.dumps(config_body)}}
        return body

    def _parse_subnetwork_create_request(self, network):
        return_params = {}
        network['typeSubnetData']['metadata'] = convert_from_dict_map(network['typeSubnetData']['metadata'])
        if 'networkId' in network['typeSubnetData']:
            return_params['networkId'] = network['typeSubnetData']['networkId']
            return_params['networkResourceName'] = network['networkResourceName']
            return_params['cidr'] = network['typeSubnetData']['cidr']
            return_params.update(network['typeSubnetData']['metadata'])
            return_params['isDhcpEnabled'] = network['typeSubnetData']['isDhcpEnabled']
            pool_range = self.indexes_to_addr_pools(return_params['cidr'], network['typeSubnetData']['addressPool'])
            return_params.update(pool_range)
        return return_params

    def indexes_to_addr_pools(self, cidr, pool):
        subnet = ipaddress.ip_network(cidr)
        supernet = subnet.supernet(new_prefix=24)
        ip_range = pool[0]
        supernet_hosts = list(supernet.hosts())
        if ip_range > 11:
            raise Exception("Address pool number higher than 11")
        start = ip_range * 20 + 1
        end = (ip_range + 1) * 20 - 1
        start_ip = str(supernet_hosts[start])
        end_ip = str(supernet_hosts[end])
        address_pool = {"range_start": start_ip, "range_end": end_ip}
        return address_pool

    def _create_response_for_subnetwork(self, kubernetes_response, request_params):
        kubernetes_response['spec']['config'] = json.loads(kubernetes_response['spec']['config'])
        range_start = kubernetes_response['spec']['config']['ipam']['rangeStart']
        allocation_pools = [{'start': range_start,
                             'end': kubernetes_response['spec']['config']['ipam']['rangeEnd']}]
        allocation_pools_str = json.dumps(allocation_pools).replace('"', '\'')
        address_pool_number = self.convert_range_start_to_address_pool(range_start)
        return_data = {
            "networkData": None,
            "networkPortData": None,
            "subnetData": {
                "addressPool": [
                    address_pool_number
                ],
                "cidr": kubernetes_response['spec']['config']['ipam']['subnet'],
                "gatewayIp": None,
                "ipVersion": 4,
                "isDhcpEnabled": True,
                "metadata": [
                    {
                        "key": "id",
                        "value": kubernetes_response['metadata']['name']
                    },
                    {
                        "key": "name",
                        "value": request_params['networkResourceName']
                    },
                    {
                        "key": "network_id",
                        "value": kubernetes_response['metadata']['name']
                    },
                    {
                        "key": "ip_version",
                        "value": "4"
                    },
                    {
                        "key": "enable_dhcp",
                        "value": "True"
                    },
                    {
                        "key": "ipv6_address_mode",
                        "value": "None"
                    },
                    {
                        "key": "gateway_ip",
                        "value": "None"
                    },
                    {
                        "key": "cidr",
                        "value": kubernetes_response['spec']['config']['ipam']['subnet']
                    },
                    {
                        "key": "allocation_pools",
                        "value": allocation_pools_str
                    }
                ],
                "networkId": kubernetes_response['metadata']['name'],
                "operationalState": "enabled",
                "resourceId": kubernetes_response['metadata']['name']
            }
        }
        return return_data

    def terminate(self, networks_id):
        for name in networks_id:
            try:
                kubernetes_response = KubNetwork().delete_namespaced_multus_network(networkname=name,
                                                                                    namespace="default")
            except client.exceptions.ApiException:
                logging.error('Network with name: %s is not found', name)
        if type(networks_id) is not list:
            networks_id = [networks_id]
        return networks_id, 200

    def get_free_vlans(self):

        vlan_from = ConfigurationFile().config['VIM']['DomainInfo']['NVFIPopList']['NfviPop']['vlanFrom']
        vlan_till = ConfigurationFile().config['VIM']['DomainInfo']['NVFIPopList']['NfviPop']['vlanTill']
        inter_pop_physical_network = ConfigurationFile().config['VIM']['DomainInfo']['NVFIPopList']['NfviPop'][
            'inter_pop_physical_network']
        vlan_array = list(range(int(vlan_from), int(vlan_till)))
        kubernetes_response = KubNetwork().list_namespaced_multus_network("default")
        networks = json.loads(kubernetes_response.data).get("items", None)
        for network in networks:
            config_str = network["spec"]["config"]
            kube_config = json.loads(config_str)
            if kube_config.get('master', None) == inter_pop_physical_network and \
                    kube_config.get('type', None) == 'vlan':
                vlan_id = kube_config.get('vlanId', None)
                if vlan_id in vlan_array:
                    vlan_array.remove(vlan_id)
        return vlan_array

    def convert_range_start_to_address_pool(self, range_start):
        last_octet = int(range_start.split(".")[3])
        return_address_pool_number = 0
        for address_pool_number in range(0, 12):
            start = address_pool_number * 20 + 1
            end = (address_pool_number + 1) * 20 - 1
            if last_octet in range(start, end):
                return_address_pool_number = address_pool_number
                break
        return return_address_pool_number
