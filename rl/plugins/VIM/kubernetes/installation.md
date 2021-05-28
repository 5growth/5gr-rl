============
Installation
============
File config.xml contains section: 
- Port: This section contain information on which port start plugin NBI
- KubernetesConfig: This section point on kubernetes configuration file.
You can get kubernetes configuration file by executing next command on Kubernetes cluster
kubectl config view --flatten > kube.conf
Copy file kube.conf to kubernetes plugin mtp root directory.
- URLprefix: url prefix of plugin on NBI
- NVFIPopList : information about nfpop 
- Zonelist: inforamtion about MTP zone
 
At the command line::
    
    $ pip install -r requirements.txt
    $ python3 -m mtp_plugin_kubernetes


To configure of MTP add next rows to file "domainlist.xml":

        <Domain>
            <Type>VIM</Type>
            <Name>OpenStack</Name>
            <Id>1</Id>
            <MecId>-1</MecId>
            <Ip>127.0.0.1</Ip>
            <Port>54000</Port>
        </Domain>