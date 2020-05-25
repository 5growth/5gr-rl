FROM centos:centos7

RUN yum -y update
RUN yum -y install epel-release
RUN yum -y install python-pip
RUN yum -y install zip unzip
RUN pip install --upgrade pip
RUN pip install -U setuptools
RUN mkdir -p /prom/prom
COPY  cloudify_mtp_plugin /prom/prom/cloudify_mtp_plugin
COPY  setup.py /prom/prom
WORKDIR /prom
RUN zip -r cloudify_mtp_plugin .
RUN ls -la
RUN pip install wagon
WORKDIR /prom
RUN wagon create cloudify_mtp_plugin.zip
RUN unzip cloudify_mtp_plugin-0.0.1-py27-none-manylinux1_x86_64.wgn -d temp_for_zip_extract
RUN sed -i 's/\"supported_platform\": \"manylinux1_x86_64\"/\"supported_platform\": \"linux_x86_64\"/' temp_for_zip_extract/cloudify_mtp_plugin/package.json
WORKDIR /prom/temp_for_zip_extract
RUN zip -r /prom/cloudify_mtp_plugin-0.0.1-py27-none-manylinux1_x86_64.wgn cloudify_mtp_plugin

RUN ls -la