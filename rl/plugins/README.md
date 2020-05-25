
# SBI plugins
This folder contains the plugin used by the resource layer.
Resource Layer communicates with plugins using the SBI and NBI Interfaces.
The plugins are divided according the domain topology type in:

## 1. WIM 
It contains the plugins for transport domain:
- **_5G Crosshaul WIM Plugin_** (plugin that controls the transport nodes developed in [5G-Crosshaul EU project](http://5g-crosshaul.eu/) ). Released with [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
- **_5G SDN WIM Plugin_** (plugin to SDN control based domains via [ONOS](https://onosproject.org/) ). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license. 
- **_5G Optical SDN WIM Plugin_** (plugin to control optical domain via [ONOS](https://onosproject.org/) ). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.
- **_Dummy WIM Plugin_** used for internal test. Released with [Apachev2](https://www.apache.org/licenses/LICENSE
- **_ONOS-OpenFlow-Slicing_** (plugin to manage slicing via ONOS using openflow switches). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.-2.0) license.
- **_ONOS-P4-Slicing_** (plugin to manage slicing via ONOS using P4 switches). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.

## 2. VIM
It contains the plugins for datacenter domain:
- **_Openstack Plugin_** (tested on [Openstack queen release](https://releases.openstack.org/queens/)). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.
- **_XenServer Plugin_** (tested on [XenServer version 6.5](https://xenserver.org/open-source-virtualization-download.html)). Released with [GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html) license.
- **_Kubernetes Plugin_** (tested on [Kubernetes 1.14.0](https://dl.k8s.io/v1.14.0/kubernetes.tar.gz)). Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.
- **_Dummy VIM Plugin_** used for internal test. Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.

## 3. MEC
It contains the plugin for MEC  domains:
- **_MECEurecom Plugin_** MEC plugin that control Eurecom MEP platform. Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.
- **_Dummy MEC Plugin_** used for internal test. Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.

## 4. RADIO
It contains the plugin for radio domains:
- **_Radio Plugin_** used for internal test. Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.
- **_DummyRadioPlugin_** used for internal test. Released with [Apachev2](https://www.apache.org/licenses/LICENSE
## 5. Cloudify
It contains the plugin to be used in Cloudify to interacts with the Resource Layer via NBI. Please refer to [Cloudify documentation](https://cloudify.co/plugins/) on how to install itEquipment. Released with [Apachev2](https://www.apache.org/licenses/LICENSE-2.0) license.

