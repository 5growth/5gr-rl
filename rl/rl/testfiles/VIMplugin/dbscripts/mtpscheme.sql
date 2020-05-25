CREATE DATABASE mtpabstrdb;



/* List of abstract Domain */
CREATE TABLE mtpabstrdb.abstrdomain (
    	abstrDomId  INT UNSIGNED NOT NULL AUTO_INCREMENT,/*correspond to vimid*/
    	tenantName VARCHAR (50), 
    	tenantId INT UNSIGNED,
PRIMARY KEY (abstrDomId)    		
);

/* List of NFVI POP (node of abstract domains)*/
CREATE TABLE mtpabstrdb.abstrnfvipop  (
    	abstrNfviPopId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	vimId INT UNSIGNED, 
    	geographicalLocationInfo VARCHAR (50),
    	networkConnectivityEndpoint VARCHAR (50),
	abstrDomId INT UNSIGNED, /*key used for stored in 		domain tables */
FOREIGN KEY (abstrDomId) REFERENCES mtpabstrdb.abstrdomain (abstrDomId),
PRIMARY KEY (abstrNfviPopId)
    		);



/* List of zoneid (useful for TD1.3) */
CREATE TABLE mtpabstrdb.abstrzoneid (
 abstrResourceZoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
abstrZoneId INT UNSIGNED NOT NULL,
zoneName		VARCHAR (50),
zoneState		VARCHAR (50),
zoneProperty	VARCHAR (50),
metadata 		VARCHAR (50),
/*FOREIGN and Primary Keys*/	
abstrNfviPopId INT UNSIGNED,
FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop  (abstrNfviPopId),
	PRIMARY KEY (abstrResourceZoneId)
    		);

/*table including all memory resources*/
/*table including capacity information of virtual memory*/
CREATE TABLE mtpabstrdb.abstrmemoryresources (
	

    	/*memory parameter according IFA005*/
	availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
	
/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),
	PRIMARY KEY (abstrResourceZoneId)
    		);

/*table including all storage resources*/
/*table including capacity information of virtual storage*/

CREATE TABLE mtpabstrdb.abstrstorageresources (
	/*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
    	/*storage parameter according IFA005*/
/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),
	PRIMARY KEY (abstrResourceZoneId)
     		);

/*table including all CPU resources*/
/*table including capacity information of virtual cpu*/

CREATE TABLE mtpabstrdb.abstrcpuresources (
    	/*capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),

/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),	
PRIMARY KEY (abstrResourceZoneId)
		);


/*table including all memory resources*/
CREATE TABLE mtpabstrdb.logicallink (
	logicalLinkId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
abstrSrcNfviPopId INT UNSIGNED,
abstrDestNfviPopId INT UNSIGNED,
srcRouterId 	VARCHAR (50),
	dstRouterId	VARCHAR (50),
    	srcRouterIp 	VARCHAR (50),
	dstRouterIp 	VARCHAR (50),
delay 			VARCHAR (50),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (50),
reservedBandwidth	VARCHAR (50),
totalBandwidth		VARCHAR (50),
allocatedBandwidth	VARCHAR (50),
/*Primary and Foreign Keys*/
	PRIMARY KEY (logicalLinkId)
     		);







/*table including all memory resources*/
CREATE TABLE mtpabstrdb.logicalpath (
	logicalPathId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
abstrSrcNfviPopId INT UNSIGNED,
abstrDestNfviPopId INT UNSIGNED,
srcRouterId 	VARCHAR (50),
	dstRouterId	VARCHAR (50),
    	srcRouterIp 	VARCHAR (50),
	dstRouterIp 	VARCHAR (50),
delay 			VARCHAR (50),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (50),
reservedBandwidth	VARCHAR (50),
totalBandwidth		VARCHAR (50),
allocatedBandwidth	VARCHAR (50),
/*Primary and Foreign Keys*/
logicalLinkId INT UNSIGNED, 
	FOREIGN KEY (logicalLinkId) REFERENCES mtpabstrdb.logicallink (logicalLinkId),
	PRIMARY KEY (logicalPathId)
     		);


/******************************************************************/

CREATE DATABASE mtpdomdb;


/*//////// TABLE CONTAINS ELEMENTS ///////////////////*/



/* List of WIM/VIM/MEC/RADIO Domain */
CREATE TABLE mtpdomdb.domain (
    	domId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*From xml files correspond to vimid IFA005 */
    	name VARCHAR (50), /*From xml files*/
        type VARCHAR (50), /*Radio, Transport, VIM, MEC*/
        ip VARCHAR (50), /*From xml files*/
    	port VARCHAR (50), /*From xml files*/
	abstrDomId INT UNSIGNED, 
	FOREIGN KEY (AbstrDomId) REFERENCES mtpabstrdb.abstrdomain (AbstrDomId),
    	PRIMARY KEY (domId) 
    		);

/* List of NFVI POP */
CREATE TABLE mtpdomdb.nfvipop (
    	nfviPopId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	vimId INT UNSIGNED, 
    	geographicalLocationInfo VARCHAR (50),
    	networkConnectivityEndpoint VARCHAR (50),
	domId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (domId) REFERENCES mtpdomdb.DOMAIN (domId),	
abstrNfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop  (abstrNfviPopId),
    	PRIMARY KEY (nfviPopId)
    		);

/* List of Resource Zones */
CREATE TABLE mtpdomdb.zoneid (
    	resourceZoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	/*List of zoneid IFA005 parameter + extension*/
zoneId INT UNSIGNED NOT NULL,
zoneName		VARCHAR (50),
zoneState		VARCHAR (50),
zoneProperty	VARCHAR (50),
metadata 		VARCHAR (50),
	/*Foreign and Primary Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
PRIMARY KEY (resourceZoneId)
    		);


/*table including all memory resources*/
CREATE TABLE mtpdomdb.memoryresources (
    	/*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
/* IFA 005 VirtualMemory parameters*/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (50),
numaEnabled 	BIT(1),
/* IFA 005 VirtualComputeResourceInformation parameters*/
computeResourceTypeId 	VARCHAR (50),
accelerationCapability	VARCHAR (50),

      /**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
    		);



/*table including all CPU resources*/
CREATE TABLE mtpdomdb.cpuresources (
    	/*cpu parameter according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
/* IFA 005 VirtualCpu parameters*/
cpuArchitecture VARCHAR (50),
numVirtualCpu  	VARCHAR (50),
cpuClock 		VARCHAR (50),
virtualCpuOversubscriptionPolicy VARCHAR (50),
virtualCpuPinningSupported		VARCHAR (50),	
/* IFA 005 VirtualComputeResourceInformation parameters*/
computeResourceTypeId 	VARCHAR (50),
accelerationCapability	VARCHAR (50),
	/**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
		);





/*table including all storage resources*/
CREATE TABLE mtpdomdb.storageresources (
    	/*storage parameter according IFA005*/
     /*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50), 
/**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
     		);



















/*table including all memory resources*/
CREATE TABLE mtpdomdb.networkresources (
	networkResId INT UNSIGNED NOT NULL AUTO_INCREMENT,
srcGwId 	VARCHAR (50),
	dstGwId	VARCHAR (50),
    	srcGWIp 	VARCHAR (50),
	dstGwIp 	VARCHAR (50),
delay 			VARCHAR (50),
/*Capacity info according IFA005*/
availableCapacity	VARCHAR (50),
reservedCapacity		VARCHAR (50),
totalCapacity 		VARCHAR (50),
allocatedCapacity	VARCHAR (50),
/*Parameters according IFA005 VirtualNetworkResourceInformation information element */
networkResourceTypeId 	VARCHAR (50),
networkType 		VARCHAR (50),
bandwidth DECIMAL,

/*Primary and Foreign Keys*/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),	
PRIMARY KEY (networkResId)
     		);









/*table containing interdomain connection*/
CREATE TABLE mtpdomdb.interdomainlink (
	interDomainLinkId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	srcDomId 	INT UNSIGNED,
	dstDomId 	INT UNSIGNED,
	srcGwId 	VARCHAR (50),
	dstGwId	VARCHAR (50),
    	srcGWIp 	VARCHAR (50),
	dstGwIp 	VARCHAR (50),
delay		VARCHAR (50),
availableBandwidth	VARCHAR (50),
reservedBandwidth	VARCHAR (50),
totalBandwidth 		VARCHAR (50),
allocatedBandwidth	VARCHAR (50),

	PRIMARY KEY (interDomainLinkId)
     		);


/* List of VIRTUAL COMPUTE FLAVOUR */
CREATE TABLE mtpdomdb.computeflavour (
computeFlavourId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/* Parameters of IFA005 ComputeFlavour Information Element*/
flavourId INT UNSIGNED NOT NULL,
accelerationCapability 	VARCHAR (50),
/* Primary and Foreign Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.NFVIPOP (nfviPopId),    
PRIMARY KEY (computeFlavourId) 
    		);


CREATE TABLE mtpdomdb.virtualcpu (
cpuArchitecture VARCHAR (50),
numVirtualCpu  	VARCHAR (50),
cpuClock 		VARCHAR (50),
virtualCpuOversubscriptionPolicy VARCHAR (50),
virtualCpuPinningSupported		VARCHAR (50),	
/**/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),

	PRIMARY KEY (computeFlavourId)
		);


CREATE TABLE mtpdomdb.virtualnetworkinterfacedata (
netInterfaceDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
networkId 		INT UNSIGNED,
networkPortId 	INT UNSIGNED,
typeVirtualNic 	VARCHAR (50),
typeConfiguration	VARCHAR (50),
bandwidth	VARCHAR (50),
accelerationCapability VARCHAR (50),
metadata	VARCHAR (50),
/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (netInterfaceDataId) 
    		);



CREATE TABLE mtpdomdb.virtualmemorydata (
/**/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (50),
numaEnabled 	BIT(1),
/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (computeFlavourId) 
    		);

CREATE TABLE mtpdomdb.virtualstoragedata (
storageDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
typeOfStorage	VARCHAR (50),
sizeOfStorage 	DECIMAL,

/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (storageDataId) 
    		);




/*********************************************************************/

CREATE DATABASE mtpservdb;

/*//////// TABLE CONTAINS ELEMENTS ///////////////////*/

/* List of SERVICE ID */
CREATE TABLE mtpservdb.service (
    	servId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*use resourcegroupid IFA005?*/
	/*list of service parameter(TBD)*/
     
    	PRIMARY KEY (servId) 
    		);








/* List of compute allocation request */
CREATE TABLE mtpservdb.computeservices (
    	computeServiceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	reqId INT UNSIGNED, /*used for retrieve and set the outcome*/
	status VARCHAR (50), /*status of request “pending”, “OK”, “NOK”*/
	vnfName VARCHAR (50),
	vmName VARCHAR (50),
	floatingIp VARCHAR (50),
     outcome VARCHAR (50),
	/*Primary and Foreign Keys*/
      servId INT UNSIGNED, /*key used for stored in service tables */
	FOREIGN KEY (servId) REFERENCES mtpservdb.SERVICE (servId),
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
    	PRIMARY KEY (computeServiceId) 
    		);

/* List of network allocation request */
CREATE TABLE mtpservdb.networkservices (
    	netServId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*key used for stored in service tables */

    	reqId INT UNSIGNED,  /*used for retrieve and set the outcome*/
status VARCHAR (50), /*status of request “pending”, “OK”, “NOK”*/
/*list of parameters for IFA005 network allocate*/
	servId INT UNSIGNED, 
	FOREIGN KEY (ServId) REFERENCES mtpservdb.service (servId),
	logicalPathId INT UNSIGNED,
FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
    	PRIMARY KEY (netServId) 
    		);

/* List of network allocation request */


CREATE TABLE mtpservdb.virtualcompute (
/*list of parameters for IFA005 network allocate*/
computeId 		INT UNSIGNED,
zoneId 		INT UNSIGNED,
virtualDisks 	VARCHAR (50),
vcImageId		VARCHAR (50),
flavourId 		INT UNSIGNED,
accelerationCapability 	VARCHAR (50),
computeName 	VARCHAR (50),
operationalState	VARCHAR (50),
hostId 		VARCHAR (50),
/* Primary and Foreign Keys */
computeServiceId INT UNSIGNED UNIQUE, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualcpu (
cpuArchitecture VARCHAR (50),
numVirtualCpu  	VARCHAR (50),
cpuClock 		VARCHAR (50),
virtualCpuOversubscriptionPolicy VARCHAR (50),
/**/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (computeServiceId) 
		);


CREATE TABLE mtpservdb.virtualnetworkinterface (
netInterfaceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
resourceId		INT UNSIGNED,
ownerId 		INT UNSIGNED,
networkId 		INT UNSIGNED, /*Reference to VirtualNetwork (see ifa005)*/
networkPortId 	INT UNSIGNED, /*Reference to VirtualNetworkPort (see ifa005)*/
ipAddress		VARCHAR (50),
typeVirtualNic 	VARCHAR (50),
typeConfiguration	VARCHAR (50),
macAddress		VARCHAR (50),
bandwidth	VARCHAR (50),
accelerationCapability VARCHAR (50),
operationalState		VARCHAR (50),
metadata	VARCHAR (50),
/* Primary and Foreign Keys*/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (netInterfaceId) 
    		);















CREATE TABLE mtpservdb.virtualmemory (
/**/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (50),
numaEnabled 	BIT(1),
/* Primary and Foreign Keys*/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualcpupinning (
cpuPinningPolicy VARCHAR (50),
cpuPinningRules   	VARCHAR (50),
cpuMap		VARCHAR (50),	
/**/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcpu (computeServiceId),
PRIMARY KEY (computeServiceId) 
		);



/*list of parameters for IFA005 compute allocate*/
CREATE TABLE mtpservdb.computeservicerequestdata (
	/*list of parameters for IFA005 compute allocate*/
locationConstraints VARCHAR (50),
  reservationId VARCHAR (50),
  computeFlavourId VARCHAR (50),
  resourceGroupId VARCHAR (50),
  metadata VARCHAR (50), /*it is a list*/
  vcImageId VARCHAR (50),
  computeName VARCHAR (50),
/*Primary and Foreign Keys*/
	computeServiceId INT UNSIGNED NOT NULL,	
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
    	PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualinterfacedata (
interfaceDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ipAddress VARCHAR (50),
macAddress VARCHAR (50),
	/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
	FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
PRIMARY KEY (interfaceDataId) 
    		);


CREATE TABLE mtpservdb.AffinityOrAntiAffinityConstraint (
affinityConstraintId INT UNSIGNED NOT NULL AUTO_INCREMENT,
type VARCHAR (50),
scope VARCHAR (50),
affinityAntiAffinityResourceList VARCHAR (50),	
affinityAntiAffinityResourceGroup VARCHAR (50),

/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
    	PRIMARY KEY (affinityConstraintId) 
    		);

CREATE TABLE mtpservdb.userdata (
content VARCHAR (50),
method VARCHAR (50),
/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
 PRIMARY KEY (computeServiceId) 
    		);







CREATE TABLE mtpservdb.networkservicerequestdata (
/*list of parameters for IFA005 network allocate*/
  locationConstraints VARCHAR (50),
  reservationId VARCHAR (50),
  affinityOrAntiAffinityConstraints VARCHAR (50), /*it is a list*/
  resourceGroupId VARCHAR (50),
metadata VARCHAR (50), /*it is a List*/
networkResourceType VARCHAR (50),
  networkResourceName VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservices (netServId),
PRIMARY KEY (netServId) 
    		);

CREATE TABLE mtpservdb.networksubnetdata (
networkId		INT UNSIGNED,
ipVersion		VARCHAR (50),
gatewayIp		VARCHAR (50),		
cidr			VARCHAR (50),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (50),
metadata		VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
PRIMARY KEY (netServId) 
    		);



CREATE TABLE mtpservdb.virtualnetworkportdata (
portType		VARCHAR (50),
networkId		INT UNSIGNED,	
segmentId		INT UNSIGNED,
bandwidth	DECIMAL,
metadata	VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
    	PRIMARY KEY (netServId) 
    		);






CREATE TABLE mtpservdb.virtualnetworkdata (
/*list of parameters for IFA005 network allocate*/
  bandwidth Decimal,
networkType	VARCHAR (50),
segmentType	VARCHAR (50),
isShared      	BIT(1),
sharingCriteria VARCHAR (50),
metadata VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
    	PRIMARY KEY (netServId) 
    		);




CREATE TABLE mtpservdb.networkqos
 (
     netQosId 					INT UNSIGNED NOT NULL AUTO_INCREMENT,
	qosName VARCHAR (50),
qosValue VARCHAR (50),
/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.VIRTUALNETWORKDATA (netServId),
PRIMARY KEY (netQosId)
     		);




CREATE TABLE mtpservdb.layer3connectivityinformation (
layer3ConnInfoId INT UNSIGNED NOT NULL AUTO_INCREMENT,
networkId		INT UNSIGNED,
ipVersion		VARCHAR (50),
gatewayIp		VARCHAR (50),		
cidr			VARCHAR (50),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (50),
metadata		VARCHAR (50),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.virtualnetworkdata (netServId),
PRIMARY KEY (layer3ConnInfoId)
    		);




CREATE TABLE mtpservdb.virtualnetwork (
/*list of parameters for IFA005 network allocate*/
  virtualNetworkId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  networkResourceName VARCHAR (50),
  segmentType VARCHAR (50),  
  isShared BOOL NOT NULL,
  zoneId INT UNSIGNED,
  networkResourceId INT UNSIGNED NOT NULL,
  networkType VARCHAR (50),  
  operationalState VARCHAR (50),  
  sharingCriteria VARCHAR (50),  
  bandwidth DECIMAL,
  netResIdRef INT UNSIGNED,  
	/*Primary and Foreign Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),

netServId INT UNSIGNED NOT NULL,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservices (netServId),
    	PRIMARY KEY (virtualNetworkId) 
    		);


CREATE TABLE mtpservdb.supportednetworkqos
 (
     supportedNetQosId 					INT UNSIGNED NOT NULL AUTO_INCREMENT,
	qosName VARCHAR (50),
qosValue VARCHAR (50),

/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED NOT NULL,
FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId),
PRIMARY KEY (supportedNetQosId)
     		);




CREATE TABLE mtpservdb.virtualnetworkport (
netPortId		INT UNSIGNED NOT NULL AUTO_INCREMENT,

resourceId 	INT UNSIGNED,
portType		VARCHAR (50),
attachedResourceId	INT UNSIGNED, /* Reference to VirtualNetworkInterface */
segmentId		INT UNSIGNED,
bandwidth	DECIMAL,
metadata	VARCHAR (50),
	/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED,
	FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId),
    	
PRIMARY KEY (netPortId) 
    		);



CREATE TABLE mtpservdb.networksubnet (
netSubnetId INT UNSIGNED NOT NULL AUTO_INCREMENT,
resourceId 	INT UNSIGNED,
ipVersion		VARCHAR (50),
gatewayIp		VARCHAR (50),		
cidr			VARCHAR (50),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (50),
metadata		VARCHAR (50),
	/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED,
	FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualNetwork (virtualNetworkId), 
PRIMARY KEY (netSubnetId)
    		);











/*IFA005 Information Elements */







/* //////////// ASSOCIATION TABLE /////////////////////////// */


CREATE TABLE mtpdomdb.logicalpath_networkresources (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	networkResId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
	FOREIGN KEY (networkResId) REFERENCES mtpdomdb.networkresources (networkResId),
PRIMARY KEY (id)
	     	);

CREATE TABLE mtpdomdb.logicalpath_interdomainLink (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	interDomainLinkId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
	FOREIGN KEY (interDomainLinkId) REFERENCES mtpdomdb.interdomainlink (interDomainLinkId),
PRIMARY KEY (id)

	     	);




