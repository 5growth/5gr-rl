properties([
    parameters([
        string(name: 'ssh_creds', defaultValue: 'ubuntu_ssh', description: 'ssh credentials id for target node'),

        string(name: 'rhost_ip_mon', defaultValue: '', description: 'target mon node ip address'),
        string(name: 'git_branch_mon', defaultValue: 'SO-MON-integration', description: 'mon git branch/tag name'),
        string(name: 'ci_branch_mon', defaultValue: 'master', description: 'mon ci git branch/tag name'),

        string(name: 'rhost_ip_so', defaultValue: '', description: 'target node ip address'),
        string(name: 'git_branch_so', defaultValue: 'master', description: 'git branch/tag name'),
        string(name: 'ci_branch_so', defaultValue: 'renaming', description: 'ci git branch/tag name'),

        string(name: 'git_branch_vs', defaultValue: 'master', description: 'git branch/tag name'),
        string(name: 'ci_branch_vs', defaultValue: 'master', description: 'ci git branch/tag name'),

        string(name: 'git_branch_mtp', defaultValue: 'master', description: '5g git branch/tag name'),
//        string(name: 'ci_branch_mtp', defaultValue: 'mtp_dummy', description: 'ci git branch/tag name'),
        string(name: 'ci_branch_mtp', defaultValue: 'master', description: 'ci git branch/tag name'),
// instantiate paramas
        choice(choices: ['m1.medium', 'm1.small2', 'm1.large'], description: 'What flavor to use?', name: 'flavor'),
        string(name: 'ssh_cfy', defaultValue: 'centos_ssh', description: 'ssh credentials id for target node'),
        string(name: 'cfy_node_ip', description: 'Cloudify ip address', defaultValue: '10.5.1.68'),
        string(name: 'depl_id', defaultValue: 'rl-test', description: 'deployment name'),
    ])
])
//{
def String nodeIp
def remote = [:]
remote.name = "${params.cfy_node_ip}"
remote.host = "${params.cfy_node_ip}"
remote.allowAnyHosts = true
//}
node {
    withCredentials([sshUserPrivateKey(credentialsId: "${params.ssh_cfy}", keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName'),
]) {
remote.user = userName
remote.identityFile = identity
try {
    stage('cleanup') {
    sh '''
    rm -rf Dockerfile || true
    rm -rf *.yml || true
    sudo docker system prune -a -f || true
    '''
    }
stage('config') {
parallel dockerfile: {
    sh '''
cat > Dockerfile << EOF
FROM ubuntu:latest
RUN apt-get update
RUN apt-get install -y wget
RUN wget http://repository.cloudifysource.org/cloudify/19.07.18/community-release/cloudify-cli-community-19.07.18.deb
RUN dpkg -i *.deb
RUN mkdir infraDev
COPY infra.yml infraDev
RUN cfy profiles use 10.5.1.68 -u admin -p admin -t default_tenant
RUN cfy install infraDev/infra.yml -b myinfra
RUN cfy deployments outputs myinfra | grep -i value | awk '{print $2}' | tee myinfra.IP
EOF
  '''
                },
compose: {
    sh """
cat > docker-compose.yml << EOF
version: '3'
services:
  cfydev:
    container_name: cfydev
    image: ubuntu:latest
    build:
      context: .
      dockerfile: ./Dockerfile
EOF
    """
                        },
blueprint: {
    sh """
cat > infra.yml << EOF
tosca_definitions_version: cloudify_dsl_1_3

imports:
  - http://www.getcloudify.org/spec/cloudify/4.3/types.yaml
  - plugin:cloudify-openstack-plugin
  - plugin:cloudify-utilities-plugin

inputs:

  username: {default: 5gr-test}
  password: {default: secret-test}
  project_id: {default: 114f57f1565745f1801bd6fcb6811c52}
  tenant_name: {default: 5gr-test}
  auth_url: {default: 'http://10.5.1.95:5000/v3'}

  user_domain_name: {default: Default}

  region: {default: "RegionOne"}

  ssh_key:
    default: jenkins-key

  img_my:
    default: xenial-server-cloudimg-amd64-disk1

  flavor_name:
    default: 'm1.small2'

  public_network_name1:
    default: provider1

  public_subnet_name1:
    default: provider1-v4

dsl_definitions:

  openstack_config: &openstack_config
    username: { get_input: username }
    password: { get_input: password }
    project_id: { get_input: project_id }
    auth_url: { get_input: auth_url }
    region: { get_input: region }
    user_domain_name: { get_input: user_domain_name }

node_templates:

  my-openstack-keypair:
    type: cloudify.openstack.nodes.KeyPair
    properties:
      use_external_resource: true
      resource_id: { get_input: ssh_key }
      private_key_path: '/etc/cloudify/key/jenkins-key'
      openstack_config: *openstack_config

  public_network:
    type: cloudify.openstack.nodes.Network
    properties:
      openstack_config: *openstack_config
      use_external_resource: true
      resource_id: { get_input: public_network_name1 }

  public_subnet:
    type: cloudify.openstack.nodes.Subnet
    properties:
      openstack_config: *openstack_config
      use_external_resource: true
      resource_id: { get_input: public_subnet_name1 }

  node_dev_port:
    type: cloudify.openstack.nodes.Port
    properties:
      resource_id: 'node_dev_port'
      openstack_config: *openstack_config
    relationships:
      - type: cloudify.relationships.contained_in
        target: public_network
      - type: cloudify.relationships.depends_on
        target: public_subnet

  node_dev:
    type: cloudify.openstack.nodes.Server
    properties:
      agent_config:
        install_method: none
      resource_id: 'node_dev'
      openstack_config: *openstack_config
      image: { get_input: img_my }
      flavor: { get_input: flavor_name }
      agent_config:
        install_method: none
    relationships:
      - target: node_dev_port
        type: cloudify.openstack.server_connected_to_port
      - target: my-openstack-keypair
        type: cloudify.openstack.server_connected_to_keypair

outputs:

  endpoint:
    description: ip provider network
    value: { get_attribute: [node_dev, ip]}
EOF
    """
            }
}
stage('instantiate VM'){
    sh """
    sed -i 's/10.5.1.68/${params.cfy_node_ip}/g' Dockerfile
    sed -i 's/myinfra/${params.depl_id}/g' Dockerfile
    sed -i 's/cfydev/${params.depl_id}/g' docker-compose.yml
    sed -i 's/node_dev/${params.depl_id}/g' infra.yml
    sed -i 's/m1.small2/${params.flavor}/g' infra.yml

    sudo docker-compose up --build
    sudo docker system prune -a -f
        """
    sshCommand remote: remote, command: "cfy deployments outputs ${params.depl_id} | grep -i value | awk '{print \$2}' | tee ${params.depl_id}.IP"
    sshCommand remote: remote, command: "echo 'private key is:' | tee -a ${params.depl_id}.IP"
    sshCommand remote: remote, command: "cat /etc/cloudify/key/jenkins-key | tee -a ${params.depl_id}.IP"
    sshGet remote: remote, from: "${params.depl_id}.IP", into: "${params.depl_id}.IP", override: true
    sh "cat ${params.depl_id}.IP"
    nodeIp = sh(script: "head -n 1 ${params.depl_id}.IP", returnStdout: true)
//    sh "exit 1"
}
    } catch(er) {
//clear Jenkins
    sh 'sudo docker system prune -a -f'
    sshCommand remote: remote, command: "rm -rf ${params.depl_id}.IP"
    currentBuild.result = 'FAILURE'
    //destroy VM
    sshCommand remote: remote, command: "cfy uninstall -f ${params.depl_id} -p 'ignore_failure=true' || true"
//mail
    emailext attachLog: true, //attachmentsPattern: 'generatedFile.txt',
    body: "Failed instantiate VM stage\n ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    recipientProviders: [brokenBuildSuspects()],
    subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
    throw er
        }}}
//string vars{
string d_path_mon = "mon1"
string s_path_mon = "repo/containerization/monitoring_platform"
string d_path_so = "so1"
string s_path_so = "repo/containerization/so"
string d_path_vs = "vs1"
string s_path_vs = "repo/containerization/vertical_slicer"
string d_path_mtp = "rl1"
string s_path_mtp = "repo/containerization/rl"
//}
//libs{
def dschr = new com.libs.dockerCheckRemote()
def br = new com.libs.buildRemote()
def dr = new com.libs.deployRemote()
def vrfr = new com.libs.verify()
//}
    node {
try {
//try build & deploy mtp
stage('mtp') {
    dschr.dockerScriptRemote("$nodeIp", params.ssh_creds)
    br.remoteBuild("$d_path_mtp", "$s_path_mtp", 'rl_', params.ci_branch_mtp, params.git_branch_mtp, params.ssh_creds, "$nodeIp")
    dr.remoteDeploy("$d_path_mtp", 'rl_', params.ssh_creds, "$nodeIp")
    vrfr.remoteVerify("$d_path_mtp", "$s_path_mtp", 'rl', params.ssh_creds, "$nodeIp")
	}
} catch(er) {
    //ERR
//mail
    emailext attachLog: true, //attachmentsPattern: 'generatedFile.txt',
    body: "Failed deploy Monitoring Platform stage\n ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    recipientProviders: [brokenBuildSuspects()],
    subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
//destroy VM
    sshCommand remote: remote, command: "cfy uninstall -f ${params.depl_id} -p 'ignore_failure=true' || true"
//clear Jenkins
    sh 'sudo docker system prune -a -f'
    sshCommand remote: remote, command: "rm -rf ${params.depl_id}.IP"
    currentBuild.result = 'FAILURE'
    throw er
}
}
node {
try {
    // try to deploy ready/tested other components
stage('mon') {
  dschr.dockerScriptRemote("$nodeIp", params.ssh_creds)
  br.remoteBuild("$d_path_mon", "$s_path_mon", 'mon_', params.ci_branch_mon, params.git_branch_mon, params.ssh_creds, "$nodeIp")
  dr.remoteDeploy("$d_path_mon", 'mon_', params.ssh_creds, "$nodeIp")
}
stage('vs') {
    dschr.dockerScriptRemote("$nodeIp", params.ssh_creds)
    br.remoteBuild("$d_path_vs", "$s_path_vs", 'vs_', params.ci_branch_vs, params.git_branch_vs, params.ssh_creds, "$nodeIp")
    dr.remoteDeploy("$d_path_vs", 'vs_', params.ssh_creds, "$nodeIp")
    vrfr.remoteVerify("$d_path_vs", "$s_path_vs", 'vs', params.ssh_creds, "$nodeIp")
}
stage('so') {
    dschr.dockerScriptRemote("$nodeIp", params.ssh_creds)
    br.remoteBuild("$d_path_so", "$s_path_so", 'so_', params.ci_branch_so, params.git_branch_so, params.ssh_creds, "$nodeIp")
    dr.remoteDeploy("$d_path_so", 'so_', params.ssh_creds, "$nodeIp")
    vrfr.remoteVerify("$d_path_so", "$s_path_so", 'so', params.ssh_creds, "$nodeIp")
}
} catch(er) {
//ERR
    currentBuild.result = 'FAILURE'
//mail
    emailext attachLog: true, //attachmentsPattern: 'generatedFile.txt',
    body: "Failed deploy Other stack stage\n ${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
    recipientProviders: [brokenBuildSuspects()],
    subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
//destroy VM
    node {
        withCredentials([sshUserPrivateKey(credentialsId: "${params.ssh_cfy}", keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName'),
        ]) {
            remote.user = userName
            remote.identityFile = identity
        sshCommand remote: remote, command: "cfy uninstall -f ${params.depl_id} -p 'ignore_failure=true' || true"
        sshCommand remote: remote, command: "rm -rf ${params.depl_id}.IP"
        throw er
}}
//clear Jenkins
    sh 'sudo docker system prune -a -f'
}}
node {
    withCredentials([sshUserPrivateKey(credentialsId: "${params.ssh_cfy}", keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName'),
    ]) {
        remote.user = userName
        remote.identityFile = identity
            stage('deinstantiate'){
                sshCommand remote: remote, command: "cfy uninstall -f ${params.depl_id} -p 'ignore_failure=true' || true"
                sshCommand remote: remote, command: "rm -rf ${params.depl_id}.IP"
            }
        }
    }