import datetime

from dateutil.tz import tzutc
from kubernetes.client import V1Pod, V1PodStatus, V1ObjectMeta


def stub_read_namespaced_pod(**args):
    v1_pod = V1Pod(
        **{'api_version': 'v1',
         'kind': 'Pod',
         'metadata': V1ObjectMeta(**{'annotations': {'k8s.v1.cni.cncf.io/network-status': '[{\n'
                                                                           '    '
                                                                           '"name": '
                                                                           '"",\n'
                                                                           '    '
                                                                           '"interface": '
                                                                           '"eth0",\n'
                                                                           '    "ips": '
                                                                           '[\n'
                                                                           '        '
                                                                           '"10.244.0.205"\n'
                                                                           '    ],\n'
                                                                           '    "mac": '
                                                                           '"8a:b9:aa:99:6e:91",\n'
                                                                           '    '
                                                                           '"default": '
                                                                           'true,\n'
                                                                           '    "dns": '
                                                                           '{}\n'
                                                                           '},{\n'
                                                                           '    '
                                                                           '"name": '
                                                                           '"default/fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl",\n'
                                                                           '    '
                                                                           '"interface": '
                                                                           '"net1",\n'
                                                                           '    "ips": '
                                                                           '[\n'
                                                                           '        '
                                                                           '"192.168.10.17"\n'
                                                                           '    ],\n'
                                                                           '    "mac": '
                                                                           '"52:54:00:11:2a:27",\n'
                                                                           '    "dns": '
                                                                           '{}\n'
                                                                           '}]',
                                      'k8s.v1.cni.cncf.io/networks': '[{"name": '
                                                                     '"fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl", '
                                                                     '"default-route": '
                                                                     '["0.0.0.0"], '
                                                                     '"ips": '
                                                                     '["192.168.10.17"], '
                                                                     '"mac": '
                                                                     '"52:54:00:11:2a:29"}]',
                                      'k8s.v1.cni.cncf.io/networks-status': '[{\n'
                                                                            '    '
                                                                            '"name": '
                                                                            '"",\n'
                                                                            '    '
                                                                            '"interface": '
                                                                            '"eth0",\n'
                                                                            '    '
                                                                            '"ips": [\n'
                                                                            '        '
                                                                            '"10.244.0.205"\n'
                                                                            '    ],\n'
                                                                            '    '
                                                                            '"mac": '
                                                                            '"8a:b9:aa:99:6e:91",\n'
                                                                            '    '
                                                                            '"default": '
                                                                            'true,\n'
                                                                            '    '
                                                                            '"dns": '
                                                                            '{}\n'
                                                                            '},{\n'
                                                                            '    '
                                                                            '"name": '
                                                                            '"default/fgt-91e0e29-3b9b-46de-9015-863cb2283795-n6-cmm-vl",\n'
                                                                            '    '
                                                                            '"interface": '
                                                                            '"net1",\n'
                                                                            '    '
                                                                            '"ips": [\n'
                                                                            '        '
                                                                            '"192.168.10.17"\n'
                                                                            '    ],\n'
                                                                            '    '
                                                                            '"mac": '
                                                                            '"52:54:00:11:2a:27",\n'
                                                                            '    '
                                                                            '"dns": '
                                                                            '{}\n'
                                                                            '}]'},
                      'cluster_name': None,
                      'creation_timestamp': datetime.datetime(2020, 12, 29, 12, 28, 36, tzinfo=tzutc()),
                      'deletion_grace_period_seconds': None,
                      'deletion_timestamp': None,
                      'finalizers': None,
                      'generate_name': None,
                      'generation': None,
                      'labels': None,
                      'managed_fields': [{'api_version': 'v1',
                                          'fields_type': 'FieldsV1',
                                          'fields_v1': {'f:metadata': {'f:annotations': {'.': {},
                                                                                         'f:k8s.v1.cni.cncf.io/networks': {}}},
                                                        'f:spec': {'f:containers': {
                                                            'k:{"name":"fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1"}': {
                                                                '.': {},
                                                                'f:command': {},
                                                                'f:image': {},
                                                                'f:imagePullPolicy': {},
                                                                'f:name': {},
                                                                'f:resources': {},
                                                                'f:terminationMessagePath': {},
                                                                'f:terminationMessagePolicy': {}}},
                                                                   'f:dnsPolicy': {},
                                                                   'f:enableServiceLinks': {},
                                                                   'f:restartPolicy': {},
                                                                   'f:schedulerName': {},
                                                                   'f:securityContext': {},
                                                                   'f:terminationGracePeriodSeconds': {}}},
                                          'manager': 'OpenAPI-Generator',
                                          'operation': 'Update',
                                          'time': datetime.datetime(2020, 12, 29, 12, 28, 36, tzinfo=tzutc())},
                                         {'api_version': 'v1',
                                          'fields_type': 'FieldsV1',
                                          'fields_v1': {'f:metadata': {
                                              'f:annotations': {'f:k8s.v1.cni.cncf.io/network-status': {},
                                                                'f:k8s.v1.cni.cncf.io/networks-status': {}}}},
                                          'manager': 'multus',
                                          'operation': 'Update',
                                          'time': datetime.datetime(2020, 12, 29, 12, 28, 38, tzinfo=tzutc())},
                                         {'api_version': 'v1',
                                          'fields_type': 'FieldsV1',
                                          'fields_v1': {
                                              'f:status': {'f:conditions': {'k:{"type":"ContainersReady"}': {'.': {},
                                                                                                             'f:lastProbeTime': {},
                                                                                                             'f:lastTransitionTime': {},
                                                                                                             'f:status': {},
                                                                                                             'f:type': {}},
                                                                            'k:{"type":"Initialized"}': {'.': {},
                                                                                                         'f:lastProbeTime': {},
                                                                                                         'f:lastTransitionTime': {},
                                                                                                         'f:status': {},
                                                                                                         'f:type': {}},
                                                                            'k:{"type":"Ready"}': {'.': {},
                                                                                                   'f:lastProbeTime': {},
                                                                                                   'f:lastTransitionTime': {},
                                                                                                   'f:status': {},
                                                                                                   'f:type': {}}},
                                                           'f:containerStatuses': {},
                                                           'f:hostIP': {},
                                                           'f:phase': {},
                                                           'f:podIP': {},
                                                           'f:podIPs': {'.': {},
                                                                        'k:{"ip":"10.244.0.205"}': {'.': {},
                                                                                                    'f:ip': {}}},
                                                           'f:startTime': {}}},
                                          'manager': 'kubelet',
                                          'operation': 'Update',
                                          'time': datetime.datetime(2020, 12, 29, 12, 28, 40, tzinfo=tzutc())}],
                      'name': 'fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1',
                      'namespace': 'default',
                      'owner_references': None,
                      'resource_version': '2453414',
                      'self_link': '/api/v1/namespaces/default/pods/fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1',
                      'uid': 'e862a80b-fc42-42b5-969a-33e397e5f816'}),
         'spec': {'active_deadline_seconds': None,
                  'affinity': None,
                  'automount_service_account_token': None,
                  'containers': [{'args': None,
                                  'command': ['/bin/ash',
                                              '-c',
                                              'trap : TERM INT; sleep infinity & wait'],
                                  'env': None,
                                  'env_from': None,
                                  'image': 'jabbo16/iperf-host:1.0.0',
                                  'image_pull_policy': 'IfNotPresent',
                                  'lifecycle': None,
                                  'liveness_probe': None,
                                  'name': 'fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1',
                                  'ports': None,
                                  'readiness_probe': None,
                                  'resources': {'limits': None, 'requests': None},
                                  'security_context': None,
                                  'startup_probe': None,
                                  'stdin': None,
                                  'stdin_once': None,
                                  'termination_message_path': '/dev/termination-log',
                                  'termination_message_policy': 'File',
                                  'tty': None,
                                  'volume_devices': None,
                                  'volume_mounts': [{'mount_path': '/var/run/secrets/kubernetes.io/serviceaccount',
                                                     'mount_propagation': None,
                                                     'name': 'default-token-59pwl',
                                                     'read_only': True,
                                                     'sub_path': None,
                                                     'sub_path_expr': None}],
                                  'working_dir': None}],
                  'dns_config': None,
                  'dns_policy': 'ClusterFirst',
                  'enable_service_links': True,
                  'ephemeral_containers': None,
                  'host_aliases': None,
                  'host_ipc': None,
                  'host_network': None,
                  'host_pid': None,
                  'hostname': None,
                  'image_pull_secrets': None,
                  'init_containers': None,
                  'node_name': 'master-node',
                  'node_selector': None,
                  'overhead': None,
                  'preemption_policy': 'PreemptLowerPriority',
                  'priority': 0,
                  'priority_class_name': None,
                  'readiness_gates': None,
                  'restart_policy': 'Always',
                  'runtime_class_name': None,
                  'scheduler_name': 'default-scheduler',
                  'security_context': {'fs_group': None,
                                       'run_as_group': None,
                                       'run_as_non_root': None,
                                       'run_as_user': None,
                                       'se_linux_options': None,
                                       'supplemental_groups': None,
                                       'sysctls': None,
                                       'windows_options': None},
                  'service_account': 'default',
                  'service_account_name': 'default',
                  'share_process_namespace': None,
                  'subdomain': None,
                  'termination_grace_period_seconds': 30,
                  'tolerations': [{'effect': 'NoExecute',
                                   'key': 'node.kubernetes.io/not-ready',
                                   'operator': 'Exists',
                                   'toleration_seconds': 300,
                                   'value': None},
                                  {'effect': 'NoExecute',
                                   'key': 'node.kubernetes.io/unreachable',
                                   'operator': 'Exists',
                                   'toleration_seconds': 300,
                                   'value': None}],
                  'topology_spread_constraints': None,
                  'volumes': [{'aws_elastic_block_store': None,
                               'azure_disk': None,
                               'azure_file': None,
                               'cephfs': None,
                               'cinder': None,
                               'config_map': None,
                               'csi': None,
                               'downward_api': None,
                               'empty_dir': None,
                               'fc': None,
                               'flex_volume': None,
                               'flocker': None,
                               'gce_persistent_disk': None,
                               'git_repo': None,
                               'glusterfs': None,
                               'host_path': None,
                               'iscsi': None,
                               'name': 'default-token-59pwl',
                               'nfs': None,
                               'persistent_volume_claim': None,
                               'photon_persistent_disk': None,
                               'portworx_volume': None,
                               'projected': None,
                               'quobyte': None,
                               'rbd': None,
                               'scale_io': None,
                               'secret': {'default_mode': 420,
                                          'items': None,
                                          'optional': None,
                                          'secret_name': 'default-token-59pwl'},
                               'storageos': None,
                               'vsphere_volume': None}]},
         'status': V1PodStatus(**{'conditions': [{'last_probe_time': None,
                                    'last_transition_time': datetime.datetime(2020, 12, 29, 12, 28, 36, tzinfo=tzutc()),
                                    'message': None,
                                    'reason': None,
                                    'status': 'True',
                                    'type': 'Initialized'},
                                   {'last_probe_time': None,
                                    'last_transition_time': datetime.datetime(2020, 12, 29, 12, 28, 40, tzinfo=tzutc()),
                                    'message': None,
                                    'reason': None,
                                    'status': 'True',
                                    'type': 'Ready'},
                                   {'last_probe_time': None,
                                    'last_transition_time': datetime.datetime(2020, 12, 29, 12, 28, 40, tzinfo=tzutc()),
                                    'message': None,
                                    'reason': None,
                                    'status': 'True',
                                    'type': 'ContainersReady'},
                                   {'last_probe_time': None,
                                    'last_transition_time': datetime.datetime(2020, 12, 29, 12, 28, 36, tzinfo=tzutc()),
                                    'message': None,
                                    'reason': None,
                                    'status': 'True',
                                    'type': 'PodScheduled'}],
                    'container_statuses': [
                        {'container_id': 'docker://21f98bce8f30b874a99a1871ab2267c5632ebc4b2841fc74535415f2e50e0684',
                         'image': 'jabbo16/iperf-host:1.0.0',
                         'image_id': 'docker-pullable://jabbo16/iperf-host@sha256:d2e9422dcf0700f7c67fbe6a48d7204f4d4dcba0ad5b4d71d9a93af50d3ae547',
                         'last_state': {'running': None,
                                        'terminated': None,
                                        'waiting': None},
                         'name': 'fgt-91e0e29-3b9b-46de-9015-863cb2283795-0-darlvnf-1',
                         'ready': True,
                         'restart_count': 0,
                         'started': True,
                         'state': {
                             'running': {'started_at': datetime.datetime(2020, 12, 29, 12, 28, 40, tzinfo=tzutc())},
                             'terminated': None,
                             'waiting': None}}],
                    'ephemeral_container_statuses': None,
                    'host_ip': '192.168.100.150',
                    'init_container_statuses': None,
                    'message': None,
                    'nominated_node_name': None,
                    'phase': 'Running',
                    'pod_i_ps': [{'ip': '10.244.0.205'}],
                    'pod_ip': '10.244.0.205',
                    'qos_class': 'BestEffort',
                    'reason': None,
                    'start_time': datetime.datetime(2020, 12, 29, 12, 28, 36, tzinfo=tzutc())})}
    )
    return v1_pod
