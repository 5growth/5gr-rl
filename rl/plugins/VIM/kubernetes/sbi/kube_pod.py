import json
import logging
import time

import yaml
from kubernetes import client, config

from mtp_plugin_kubernetes.config.config import ConfigurationFile


class KunernetesAPIFormatError(Exception):
    def __init__(self, *args):
        if args:
            self.message = args[0]
        else:
            self.message = None

    def __str__(self):
        print('calling str')
        if self.message:
            return 'Kunernetes API Format Error, {0} '.format(self.message)
        else:
            return 'KunernetesAPIFormatError has been raised'


class KubPodMultus(object):
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
        try:
            kube_conf = ConfigurationFile().config['VIM']['Server']['KubernetesConfig']
            config.load_kube_config(kube_conf)
        except Exception as ex:
            logging.error(str(ex))

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

    def create_pod(self):
        core_api = client.CoreV1Api()
        yaml_body = \
            "apiVersion: v1\n" + \
            "kind: Pod\n" + \
            "metadata:\n" + \
            "  name: iperf-host\n" + \
            "  annotations:\n" + \
            "    k8s.v1.cni.cncf.io/networks: '[{\n" + \
            "      \"name\": \"vlan-conf\",\n" + \
            "      \"default-route\": [\"0.0.0.0\"]\n" + \
            "    }]'\n" + \
            "spec:\n" + \
            "  containers:\n" + \
            "  - name: iperf-host\n" + \
            "    command: [\"/bin/ash\", \"-c\", \"trap : TERM INT; sleep infinity & wait\"]\n" + \
            "    image: jabbo16/iperf-host:1.0.0\"\n"
        body = yaml.safe_load(yaml_body)
        return core_api.create_namespaced_pod(body=body, namespace='default')

    def delete_pod(self):
        core_api = client.CoreV1Api()
        return core_api.delete_namespaced_pod(name="iperf-host", namespace='default')

    def _parse_allocate_pod_create_request(self, pod):
        return_params = {}
        try:
            return_params['podName'] = pod['computeName']
            return_params['podId'] = return_params['podName'].lower()
            return_params['podId'] = return_params['podId'].replace("_", "-")
            return_params['interfacesData'] = pod['interfaceData']
            return_params['init_script'] = pod['userData']
            return_params['docker_image'] = pod['vcImageId']
        except KeyError as e:
            raise KunernetesAPIFormatError('Inavalid Kubernetes API  reponse format, missing filed "%s"'.format(e))
        return return_params

    def _create_body_for_create_pod_with_multus(self, params):
        interfaces = []
        for interface_data in params['interfacesData']:
            interface = {}
            interface['name'] = interface_data['networkId']
            interface['default-route'] = ["0.0.0.0"]
            if 'ipAddress' in interface_data.keys():
                interface['ips'] = [interface_data['ipAddress']]
            if 'macAddress' in interface_data.keys():
                interface['mac'] = interface_data['macAddress']
            interfaces.append(interface)
        interfaces = json.dumps(interfaces)
        pod_name = params['podId']
        docker_image = params['docker_image']
        init_script = params['init_script']['content'].split(",")
        for element in range(len(init_script)):
            init_script[element] = init_script[element].strip()

        body = \
            {'apiVersion': 'v1',
             'kind': 'Pod',
             'metadata':
                 {'name': pod_name,
                  'annotations': {
                      'k8s.v1.cni.cncf.io/networks': interfaces}},
             'spec': {
                 'containers': [
                     {'name': pod_name,
                      'command': init_script,
                      'image': docker_image
                      }
                 ]
             }}
        return body

    def allocate(self, pod_request):
        params = self._parse_allocate_pod_create_request(pod_request)
        body = self._create_body_for_create_pod_with_multus(params)
        kubernetes_response = self._create_pod_with_multus(body, params)
        response = self._create_response_for_pod(kubernetes_response, params)
        return response

    def _create_response_for_pod(self, kubernetes_response, params):
        dc = int(ConfigurationFile().config['VIM']['DomainInfo']['NVFIPopList']['NfviPop']['nfviPopId'])
        virtual_network_interfaces = []
        kubernetes_interfaces = json.loads(
            kubernetes_response.metadata.annotations['k8s.v1.cni.cncf.io/networks-status'])
        for kubernetes_interface in kubernetes_interfaces:
            if 'interface' not in kubernetes_interface.keys():
                continue
            network_id = kubernetes_interface['name'].split("/")[-1]
            virtual_network_interface = {
                "accelerationCapability": "",
                "bandwidth": "0",
                "ipAddress": kubernetes_interface['ips'],
                "metadata": [
                    {
                        "key": "dc",
                        "value": dc
                    }
                ],
                "networkId": network_id,
                "networkName": None,
                "networkPortId": kubernetes_interface['interface'],
                "operationalState": "ACTIVE",
                "ownerId": None,
                "resourceId": kubernetes_interface['interface'],
                "typeConfiguration": "Multus-cni",
                "typeVirtualNic": "normal"
            }
            if "mac" in kubernetes_interface.keys():
                virtual_network_interface.update({"macAddress": kubernetes_interface["mac"]})
            else:
                virtual_network_interface.update({"macAddress": None})
            virtual_network_interfaces.append(virtual_network_interface)

        return_data = \
            {
                "accelerationCapability": [],
                "computeId": kubernetes_response.metadata.name,
                "computeName": params['podName'],
                "flavourId": None,
                "hostId": kubernetes_response.status.host_ip,
                "operationalState": "enabled",
                "vcImageId": "",
                "virtualCpu": {
                    "cpuArchitecture": "",
                    "cpuClock": 0,
                    "numVirtualCpu": 0,
                    "virtualCpuOversubscriptionPolicy": "",
                    "virtualCpuPinning": {
                        "cpuMap": "",
                        "cpuPinningPolicy": "dynamic",
                        "cpuPinningRules": ""
                    }
                },
                "virtualDisks": "",
                "virtualMemory": {
                    "numaEnabled": False,
                    "virtualMemOversubscriptionPolicy": None,
                    "virtualMemSize": 0
                },
                "virtualNetworkInterface": virtual_network_interfaces,
                "zoneId": "nova"
            }

        return return_data, 201

    def _create_pod_with_multus(self, body, params):
        core_api = client.CoreV1Api()
        try:
            core_api.create_namespaced_pod(body=body,
                                            namespace='default',
                                            _preload_content=False)
        except client.exceptions.ApiException as exp:
            if exp.reason == 'Conflict':
                logging.info('Pod with name: %s already exists', params['podId'])

        while True:
            resp = core_api.read_namespaced_pod(name=params['podId'],
                                                namespace='default')
            if resp.status.phase != 'Pending':
                break
            time.sleep(1)
        return resp

    def terminate(self, computes_id):
        core_api = client.CoreV1Api()
        for name in computes_id:
            try:
                core_api.delete_namespaced_pod(name=name,
                                               namespace='default',
                                               _preload_content=False)
            except client.exceptions.ApiException:
                logging.info('Pod with name: %s is not found', name)

        if type(computes_id) is not list:
            computes_id = [computes_id]
        return computes_id, 200
