/***************************** APPD SAMPLE DATA *****************************/
/*------------------ appD robotics -------------------------*/

/*insert AppD global info */
INSERT INTO `mtpappddb`.`appd`
(`appDId`,
`appName`,
`appProvider`,
`appSoftVersion`,
`appDVersion`,
`mecVersion`,
`appInfoName`,
`appDescription`)
VALUES
(1,
"robot-control-app",
"pfrag",
"1.0",
"1.0",
"1.0",
"robot-control-app",
"app for controlling mobile robot");

/*insert virtual compute descriptor*/

INSERT INTO `mtpappddb`.`virtualcomputedescriptor`
(`virtualComputeDescId`,
`appDId`)
VALUES
("1c0897de-bc1a-4730-9d99-364fa83d7643",
1); 

INSERT INTO `mtpappddb`.`virtualmemory`
(`virtualMemSize`,
`numaEnabled`,
`virtualMemOversubscriptionPolicy`,
`appDId`)
VALUES
(1024,
0,
"none",
1); 

INSERT INTO `mtpappddb`.`virtualcpu`
(`cpuArchitecture`,
`numVirtualCpu`,
`virtualCpuClock`,
`virtualCpuOversubscriptionPolicy`,
`appDId`)
VALUES
("x86_64",
"1",
"0",
"string",
1); 

INSERT INTO `mtpappddb`.`virtualcpupinning`
(`cpuPinningPolicy`,
`cpuPinningMap`,
`appDId`)
VALUES
("static",
"",
1); 

/*insert sofware image descriptor*/
INSERT INTO `mtpappddb`.`swimagedescriptor`
(`id`,
`name`,
`version`,
`checksum_`,
`containerFormat`,
`diskFormat`,
`minDisk`,
`minRam`,
`size_`,
`swImage`,
`operatingSystem`,
`supportedVirtualizationEnvironment`,
`appDId`)
VALUES
(10,  
"robotctrl",
"1.0",
"",
"lxd_unified",
"rootfs",
"1",
"512",
"512",
"http://127.0.0.1/lxdimages/robotctrl.tar.gz",
"",
"lxd",
1); 


/*insert storage image descriptor*/
INSERT INTO `mtpappddb`.`virtualstoragedescriptor`
(`id`,
`typeOfStorage`,
`sizeOfStorage`,
`rdmaEnabled`,
`swImageDesc`,
`appDId`)
VALUES
(11, 
"",
"1",
1,
"",
1); 

/*insert app service required*/
INSERT INTO `mtpappddb`.`appservicerequired`
(`serviceRequiredId`,
`serName`,
`version`,
`requestedPermissions`,
`appDId`)
VALUES
(10, 
"RNIS",
"1.0",
"",
1); 

INSERT INTO `mtpappddb`.`sertransportdependencies`
(`serTransportDependenciesId`,
`serializers`,
`labels`,
`serviceRequiredId`)
VALUES
(20,
"JSON",
"",
10); 

INSERT INTO `mtpappddb`.`sertransport`
(`transportType`,
`protocol`,
`version`,
`security`,
`serTransportDependenciesId`)
VALUES
("REST_HTTP",
"",
"",
"",
20); 

INSERT INTO `mtpappddb`.`sercategory`
(`href`,
`id`,
`name`,
`version`,
`serviceRequiredId`)
VALUES
("",
"",
"",
"",
10); 

/*insert app service optional*/
INSERT INTO `mtpappddb`.`appserviceoptional`
(`serviceRequiredId`,
`serName`,
`version`,
`requestedPermissions`,
`appDId`)
VALUES
(30,
"",
"",
"",
1); 

INSERT INTO `mtpappddb`.`sertransportdependenciesoptional`
(`serTransportDependenciesId`,
`serializers`,
`labels`,
`serviceRequiredId`)
VALUES
(31,
"JSON",
"",
30); 

INSERT INTO `mtpappddb`.`transportdescriptoroptional`
(`transportType`,
`protocol`,
`version`,
`security`,
`serTransportDependenciesId`)
VALUES
("REST_HTTP",
"",
"",
"",
31); 

INSERT INTO `mtpappddb`.`sercategoryoptional`
(`href`,
`id`,
`name`,
`version`,
`serviceRequiredId`)
VALUES
("",
"",
"",
"",
30); 

/*insert transport dependencies*/
INSERT INTO `mtpappddb`.`transportdependencies`
(`transportdependenciesId`,
`serializers`,
`labels`,
`appDId`)
VALUES
(40,
"JSON",
"",
1); 

INSERT INTO `mtpappddb`.`transport`
(`transportType`,
`protocol`,
`version`,
`security`,
`transportDependenciesId`)
VALUES
("REST_HTTP",
"",
"",
"",
40); 

/*insert app traffic rules*/
INSERT INTO `mtpappddb`.`apptrafficrule`
(`trafficRuleId`,
`filterType`,
`priority`,
`action`,
`appDId`)
VALUES
(15, 
"FLOW",
"0",
"FORWARD",
1); 

INSERT INTO `mtpappddb`.`trafficfilter`
(`trafficFilterId`,
`srcAddress`,
`dstAddress`,
`srcPort`,
`dstPort`,
`protocol`,
`token`,
`srcTunnelAddress`,
`dstTunnelAddress`,
`srcTunnelPort`,
`dstTunnelPort`,
`qci`,
`dscp`,
`tc`,
`trafficRuleId`)
VALUES
(16,
"208930100001114",
"172.16.0.100",
"",
"9990",
"tcp",
"",
"",
"",
"",
"",
"0",
"0",
"0",
15); 


/*insert app DNS rules*/
INSERT INTO `mtpappddb`.`appdnsrule`
(`dnsRuleId`,
`domainName`,
`ipAddressType`,
`ipAddress`,
`ttl`,
`appDId`)
VALUES
(61, 
"robot.control",
"IP_V4",
"0.0.0.0",
"0",
1); 

INSERT INTO `mtpappddb`.`appdnsrule`
(`dnsRuleId`,
`domainName`,
`ipAddressType`,
`ipAddress`,
`ttl`,
`appDId`)
VALUES
(62, 
"sfr.fr",
"IP_V4",
"0.0.0.0",
"0",
1); 


/*insert app latency rules*/
INSERT INTO `mtpappddb`.`applatency`
(`timeUnit`,
`latency`,
`appDId`)
VALUES
("ms",
"100",
1); 


/*insert terminate app instance config*/
INSERT INTO `mtpappddb`.`terminateappinstanceopconfig`
(`minGracefulTerminationTimeout`,
`macRecommendedGracefulTerminationTimeout`,
`appDId`)
VALUES
("0",
"0",
1); 

/*insert change app instance config*/
INSERT INTO `mtpappddb`.`changeappinstancestateopconfig`
(`minGracefulTerminationTimeout`,
`macRecommendedGracefulTerminationTimeout`,
`appDId`)
VALUES
("0",
"0",
1); 



/*------------------ appD edge-cache -------------------------*/
/*insert AppD global info */
INSERT INTO `mtpappddb`.`appd`
(`appDId`,
`appName`,
`appProvider`,
`appSoftVersion`,
`appDVersion`,
`mecVersion`,
`appInfoName`,
`appDescription`)
VALUES
(2,
"edgecache",
"pfrag",
"1.0",
"1.0",
"1.0",
"edgecache",
"edge cache application");

/*insert virtual compute descriptor*/

INSERT INTO `mtpappddb`.`virtualcomputedescriptor`
(`virtualComputeDescId`,
`appDId`)
VALUES
("1c0897de-bc1a-4730-9d99-364fa83d7643",
2); 

INSERT INTO `mtpappddb`.`virtualmemory`
(`virtualMemSize`,
`numaEnabled`,
`virtualMemOversubscriptionPolicy`,
`appDId`)
VALUES
(1024,
0,
"none",
2); 

INSERT INTO `mtpappddb`.`virtualcpu`
(`cpuArchitecture`,
`numVirtualCpu`,
`virtualCpuClock`,
`virtualCpuOversubscriptionPolicy`,
`appDId`)
VALUES
("x86_64",
"1",
"0",
"string",
2); 

INSERT INTO `mtpappddb`.`virtualcpupinning`
(`cpuPinningPolicy`,
`cpuPinningMap`,
`appDId`)
VALUES
("static",
"",
2);

/*insert sofware image descriptor*/
INSERT INTO `mtpappddb`.`swimagedescriptor`
(`id`,
`name`,
`version`,
`checksum_`,
`containerFormat`,
`diskFormat`,
`minDisk`,
`minRam`,
`size_`,
`swImage`,
`operatingSystem`,
`supportedVirtualizationEnvironment`,
`appDId`)
VALUES
(12,  
"robotctrl",
"1.0",
"",
"lxd_unified",
"rootfs",
"1",
"512",
"512",
"http://path-to-edge-cache-image.tar.gz",
"",
"lxd",
2); 


/*insert storage image descriptor*/
INSERT INTO `mtpappddb`.`virtualstoragedescriptor`
(`id`,
`typeOfStorage`,
`sizeOfStorage`,
`rdmaEnabled`,
`swImageDesc`,
`appDId`)
VALUES
(12, 
"",
"1",
1,
"",
2); 

/*insert app traffic rules*/
INSERT INTO `mtpappddb`.`apptrafficrule`
(`trafficRuleId`,
`filterType`,
`priority`,
`action`,
`appDId`)
VALUES
(22, 
"FLOW",
"0",
"FORWARD",
2); 

INSERT INTO `mtpappddb`.`trafficfilter`
(`trafficFilterId`,
`srcAddress`,
`dstAddress`,
`srcPort`,
`dstPort`,
`protocol`,
`token`,
`srcTunnelAddress`,
`dstTunnelAddress`,
`srcTunnelPort`,
`dstTunnelPort`,
`qci`,
`dscp`,
`tc`,
`trafficRuleId`)
VALUES
(32,
"208930100001114",
"172.16.0.100",
"",
"9990",
"tcp",
"",
"",
"",
"",
"",
"0",
"0",
"0",
22); 

/*insert app DNS rules*/
INSERT INTO `mtpappddb`.`appdnsrule`
(`dnsRuleId`,
`domainName`,
`ipAddressType`,
`ipAddress`,
`ttl`,
`appDId`)
VALUES
(71, 
"edge.cache.cdn",
"IP_V4",
"0.0.0.0",
"0",
2); 

/*insert app latency rules*/
INSERT INTO `mtpappddb`.`applatency`
(`timeUnit`,
`latency`,
`appDId`)
VALUES
("ms",
"100",
2); 


/*insert terminate app instance config*/
INSERT INTO `mtpappddb`.`terminateappinstanceopconfig`
(`minGracefulTerminationTimeout`,
`macRecommendedGracefulTerminationTimeout`,
`appDId`)
VALUES
("0",
"0",
2); 

/*insert change app instance config*/
INSERT INTO `mtpappddb`.`changeappinstancestateopconfig`
(`minGracefulTerminationTimeout`,
`macRecommendedGracefulTerminationTimeout`,
`appDId`)
VALUES
("0",
"0",
2); 

/*insert app ext CPD*/
INSERT INTO `mtpappddb`.`appextcpd`
(`cpdId`,
`layerProtocol`,
`cpRole`,
`description`,
`appDId`)
VALUES
(92, 
"Ethernet",
"ROOT",
"MEC CPD",
2); 


INSERT INTO `mtpappddb`.`appextcpd`
(`cpdId`,
`layerProtocol`,
`cpRole`,
`description`,
`appDId`)
VALUES
(93, 
"Ethernet",
"ROOT",
"CP retrieving origin server data",
2); 

INSERT INTO `mtpappddb`.`appextcpd`
(`cpdId`,
`layerProtocol`,
`cpRole`,
`description`,
`appDId`)
VALUES
(94, 
"Ethernet",
"ROOT",
"CP for delivering video to clients",
2); 

INSERT INTO `mtpappddb`.`appextcpd`
(`cpdId`,
`layerProtocol`,
`cpRole`,
`description`,
`appDId`)
VALUES
(95, 
"Ethernet",
"ROOT",
"CP management network",
2); 