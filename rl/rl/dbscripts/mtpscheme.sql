CREATE DATABASE mtpappddb;

CREATE TABLE mtpappddb.appd (
  	
appDId INT UNSIGNED NOT NULL AUTO_INCREMENT,
appName VARCHAR (100), 
appProvider VARCHAR (100),
appSoftVersion VARCHAR (100),
appDVersion VARCHAR (100),
mecVersion VARCHAR (100),
appInfoName VARCHAR (100),
appDescription VARCHAR (100),
/*virtualComputeDescriptor*/ 
/*swImageDescriptor*/
/*virtualStorageDescriptor*/
/*appExtCpd *//*This is a struct, See IFA011  */


/*appServiceRequired*/ 
/*appServiceOptional*/ 
/*appServiceProduced*/    /*not used  */  
/*appFeatureRequired */   /*not used  */ 
/*appFeatureOptional*/    /*not used  */ 
/*transportDependencies*/
/*appTrafficRule*/ 
/*appDNSRule */
/*appLatency*/ 
/*terminateAppInstanceOpConfig*/
/*changeAppInstanceStateOpConfig*/	
PRIMARY KEY (appDId) 
);


/*********************appServiceRequired*********************************************/
	
	CREATE TABLE mtpappddb.appservicerequired  (

	serviceRequiredId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	serName VARCHAR (100),
	/*serCategory VARCHAR (100),*/ /* this is a struct. See  ETSI GS MEC 011 */
	version VARCHAR (100),
	/* serTransportDependencies*/
	requestedPermissions VARCHAR (100), /*not specified in MEC010-02 */

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (serviceRequiredId)
				);


		CREATE TABLE mtpappddb.sercategory  (

		href VARCHAR (100),
		id  VARCHAR (100),
		name VARCHAR (100),
		version VARCHAR (100),

		serviceRequiredId INT UNSIGNED, /* */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appservicerequired (serviceRequiredId),
		PRIMARY KEY (serviceRequiredId)
					);			
			

		CREATE TABLE mtpappddb.sertransportdependencies  (

		serTransportDependenciesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		/*transport*/ 
		serializers ENUM('JSON', 'XML', 'PROTOBUF3') NOT NULL,
		labels VARCHAR (100),

		serviceRequiredId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appservicerequired (serviceRequiredId),
		PRIMARY KEY (serTransportDependenciesId)

					);

			
			CREATE TABLE mtpappddb.sertransport  (

			transportType ENUM('REST_HTTP', 'MB_TOPIC_BASED', 'MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
			protocol  VARCHAR (100),
			version  VARCHAR (100),
			security  VARCHAR (100), /* this is a struct. See  ETSI GS MEC 011 */

			serTransportDependenciesId INT UNSIGNED,
			FOREIGN KEY (serTransportDependenciesId) REFERENCES mtpappddb.sertransportdependencies (serTransportDependenciesId),
				PRIMARY KEY (serTransportDependenciesId)
						);
/******************************************************************/

/****************appFeatureOptional**************************************************/
	
	CREATE TABLE mtpappddb.appserviceoptional  (

	serviceRequiredId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	serName VARCHAR (100),
	/*serCategory VARCHAR (100),*/ /* this is a struct. See  ETSI GS MEC 011 */
	version VARCHAR (100),
	/* serTransportDependencies*/
	requestedPermissions VARCHAR (100), /*not specified in MEC010-02 */

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (serviceRequiredId)
				);


		CREATE TABLE mtpappddb.sercategoryoptional  (

		href VARCHAR (100),
		id  VARCHAR (100),
		name VARCHAR (100),
		version VARCHAR (100),

		serviceRequiredId INT UNSIGNED, /* */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appserviceoptional (serviceRequiredId),
		PRIMARY KEY (serviceRequiredId)
					);			
			

		CREATE TABLE mtpappddb.sertransportdependenciesoptional  (

		serTransportDependenciesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		/*transport*/ 
		serializers ENUM('JSON', 'XML', 'PROTOBUF3') NOT NULL,
		labels VARCHAR (100),

		serviceRequiredId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (serviceRequiredId) REFERENCES mtpappddb.appserviceoptional (serviceRequiredId),
		PRIMARY KEY (serTransportDependenciesId)
					);

			
			CREATE TABLE mtpappddb.transportdescriptoroptional  (

			transportType ENUM('REST_HTTP', 'MB_TOPIC_BASED', 'MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
			protocol  VARCHAR (100),
			version  VARCHAR (100),
			security  VARCHAR (100), /* this is a struct. See  ETSI GS MEC 011 */

			serTransportDependenciesId INT UNSIGNED,
			FOREIGN KEY (serTransportDependenciesId) REFERENCES mtpappddb.sertransportdependenciesoptional (serTransportDependenciesId),
				PRIMARY KEY (serTransportDependenciesId)
						);
/******************************************************************/


/****************transportDependencies**************************************************/
	
	CREATE TABLE mtpappddb.transportdependencies  (

	transportdependenciesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	/*transport*/
	/*serializers*/
    serializers ENUM('JSON', 'XML', 'PROTOBUF3') NOT NULL,
	labels VARCHAR (100),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (transportdependenciesId)	
				);


				
		CREATE TABLE mtpappddb.transport  (

			transportType ENUM('REST_HTTP', 'MB_TOPIC_BASED', 'MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
			protocol  VARCHAR (100),
			version  VARCHAR (100),
			security  VARCHAR (100), /* this is a struct. See  ETSI GS MEC 011 */

			transportDependenciesId INT UNSIGNED,
			FOREIGN KEY (transportDependenciesId) REFERENCES mtpappddb.transportdependencies (transportDependenciesId),
				PRIMARY KEY (transportDependenciesId)
						);		
		
/******************************************************************/


/****************appTrafficRule**************************************************/

	CREATE TABLE mtpappddb.apptrafficrule  (

	trafficRuleId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	filterType  ENUM('FLOW', 'PACKET') NOT NULL,
	priority   VARCHAR (100),
	/*trafficFilter*/
	action ENUM('DROP', 'FORWARD','DECAPSULATED','FORWARD_AS_IS', 'PASSTHROUGH', 'DUPLICATED_DECAPSULATED', 'DUPLICATE_AS_IS') NOT NULL,
	/*dstInterface*/
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (trafficRuleId)	
				);
		
				
		CREATE TABLE mtpappddb.trafficfilter  (
		
		trafficFilterId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		srcAddress VARCHAR (100),
		dstAddress VARCHAR (100),
		srcPort VARCHAR (100),
		dstPort VARCHAR (100),
		protocol VARCHAR (100),
		token VARCHAR (100),
		srcTunnelAddress VARCHAR (100),
		dstTunnelAddress VARCHAR (100),
		srcTunnelPort VARCHAR (100),
		dstTunnelPort VARCHAR (100),
		qci VARCHAR (100),
		dscp VARCHAR (100),
		tc VARCHAR (100),
		
		trafficRuleId INT UNSIGNED,
		FOREIGN KEY (trafficRuleId) REFERENCES mtpappddb.apptrafficrule (trafficRuleId),
		PRIMARY KEY (trafficFilterId)
						);


		CREATE TABLE mtpappddb.dstinterface  (
		
		dstInterfaceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		interfaceType ENUM('TUNNEL', 'MAC','IP') NOT NULL,
		/*TunnelInfo tunnelInfo */
		srcMACAddress VARCHAR (100),
		dstMACAddress VARCHAR (100),
		dstIPAddress VARCHAR (100),
		
		trafficRuleId INT UNSIGNED,
		FOREIGN KEY (trafficRuleId) REFERENCES mtpappddb.apptrafficrule (trafficRuleId),
		PRIMARY KEY (dstInterfaceId)
						);		
									
						
			CREATE TABLE mtpappddb.tunnelinfo  (
		
			tunnelType ENUM('GTP-U','GRE') NOT NULL,
			tunnelDstAddress  VARCHAR (100),
			tunnelSrcAddress  VARCHAR (100),
			tunnelSpecificData  VARCHAR (100),
		
			dstInterfaceId INT UNSIGNED,
			FOREIGN KEY (dstInterfaceId) REFERENCES mtpappddb.dstinterface (dstInterfaceId),
			PRIMARY KEY (dstInterfaceId)
						);				
		
/******************************************************************/
 

/****************appDNSRule**************************************************/

	CREATE TABLE mtpappddb.appdnsrule  (

	dnsRuleId 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	domainName  VARCHAR (100),
	ipAddressType ENUM('IP_V6','IP_V4') NOT NULL,
    ipAddress VARCHAR (100),
    ttl VARCHAR (100),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (dnsRuleId)	
				);			
/******************************************************************/

/****************appLatency**************************************************/

	CREATE TABLE mtpappddb.applatency  (

	timeUnit VARCHAR (100),
	latency VARCHAR (100),

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);			
		
/******************************************************************/

/****************terminateAppInstanceOpConfig**************************************************/

	CREATE TABLE mtpappddb.terminateappinstanceopconfig  (
	  
	minGracefulTerminationTimeout  VARCHAR (100),
	macRecommendedGracefulTerminationTimeout  VARCHAR (100),

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);			
		
/******************************************************************/


/****************changeAppInstanceStateOpConfig**************************************************/

	CREATE TABLE mtpappddb.changeappinstancestateopconfig  (
	  
	minGracefulTerminationTimeout  VARCHAR (100),
	macRecommendedGracefulTerminationTimeout  VARCHAR (100),

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);			
		
/******************************************************************/


/****************appExtCpd**************************************************/

	CREATE TABLE mtpappddb.appextcpd  (
	  
	cpdId 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	layerProtocol ENUM('Ethernet','MPLS','ODU2','IPV4','IPV6','Pseudo-Wire') NOT NULL,
	cpRole VARCHAR (100),
	description VARCHAR (100),
	/*addressData*/
	/*virtualNetworkInterfaceRequirements*/
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (cpdId )	
				);			
	
	
		CREATE TABLE mtpappddb.addressdata  (
	
	    addressdataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		addressType ENUM('MAC','IPV4','IPV6') NOT NULL,
		l2AddressData VARCHAR (100), 
		l3AddressData  VARCHAR (100),
	
		cpdId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (cpdId) REFERENCES mtpappddb.appextcpd (cpdId),
		PRIMARY KEY (addressdataId)	
				);	
		
		
		CREATE TABLE mtpappddb.virtualnetworkinterfacerequirements  (
		
		virtualNetworkInterfaceRequirementsId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	    name  VARCHAR (100),
		description  VARCHAR (100),
		supportMandatory BOOL NOT NULL,
		requirement VARCHAR (100),
		
		cpdId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (cpdId) REFERENCES mtpappddb.appextcpd (cpdId),
		PRIMARY KEY (virtualNetworkInterfaceRequirementsId)	
				);
		
/******************************************************************/

/****************virtualStorageDescriptor**************************************************/

	CREATE TABLE mtpappddb.virtualstoragedescriptor (
	  
	id 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	typeOfStorage VARCHAR (100),
	sizeOfStorage VARCHAR (100),
	rdmaEnabled BOOL,
	swImageDesc VARCHAR (100),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (id)	
				);			
/***************************************************************************/

/****************swImageDescriptor**************************************************/

	CREATE TABLE mtpappddb.swimagedescriptor (
	  
	id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name  VARCHAR (100),
	version  VARCHAR (100),
	checksum_   VARCHAR (100),
	containerFormat  VARCHAR (100),
	diskFormat  VARCHAR (100),
	minDisk  VARCHAR (100),
	minRam  VARCHAR (100),
	size_  VARCHAR (100),
	swImage  VARCHAR (100),
	operatingSystem  VARCHAR (100),
	supportedVirtualizationEnvironment VARCHAR (100),
	
	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (id)	
				);	

/***************************************************************************/

/****************virtualComputeDescriptor**************************************************/

	CREATE TABLE mtpappddb.virtualcomputedescriptor  (
	  
	virtualComputeDescId  VARCHAR (100),
	/*List<RequestAdditionalCapabilityData> requestAdditionalCapabilities*/
	/*VirtualMemoryData virtualMemory*/
	/*VirtualCpuData virtualCpu*/

	appDId INT UNSIGNED, /*key used for stored in 		domain tables */
	FOREIGN KEY (appDId) REFERENCES mtpappddb.appd (appDId),
	PRIMARY KEY (appDId)	
				);		

		CREATE TABLE mtpappddb.requestadditionalcapabilities  (
	  
		capabilitiesId INT UNSIGNED NOT NULL AUTO_INCREMENT,
		requestedAdditionalCapabilityName VARCHAR (100),
		supportMandatory  BOOL NOT NULL,
		minRequestedAdditionalCapabilityVersion VARCHAR (100),
		preferredRequestedAdditionalCapabilityVersion VARCHAR (100),
		targetPerformanceParameters VARCHAR (100),

		appDId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcomputedescriptor (appDId),
		PRIMARY KEY (capabilitiesId)	
					);			

					
		CREATE TABLE mtpappddb.virtualmemory  (
	  
		virtualMemSize VARCHAR (100),
		numaEnabled BOOL,
		virtualMemOversubscriptionPolicy VARCHAR (100),

		appDId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcomputedescriptor (appDId),
		PRIMARY KEY (appDId)	
					);	
		
		
		CREATE TABLE mtpappddb.virtualcpu  (
	  
		cpuArchitecture VARCHAR (100),
		numVirtualCpu VARCHAR (100),
		virtualCpuClock VARCHAR (100),
		virtualCpuOversubscriptionPolicy VARCHAR (100),
		/*VirtualCpuPinningData virtualCpuPinning*/
	
		appDId INT UNSIGNED, /*key used for stored in 		domain tables */
		FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcomputedescriptor (appDId),
		PRIMARY KEY (appDId)	
					);	
					
					
			CREATE TABLE mtpappddb.virtualcpupinning  (
	
		    cpuPinningPolicy ENUM('static','dynamic'),
			cpuPinningMap VARCHAR (100),
		
			appDId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (appDId) REFERENCES mtpappddb.virtualcpu (appDId),
			PRIMARY KEY (appDId)	
					);	
		
		
/******************************************************************/




CREATE DATABASE mtpabstrdb;



/* List of abstract Domain */
CREATE TABLE mtpabstrdb.abstrdomain (
    	abstrDomId  INT UNSIGNED NOT NULL AUTO_INCREMENT,/*correspond to vimid*/
    	tenantName VARCHAR (100), 
    	tenantId INT UNSIGNED,
PRIMARY KEY (abstrDomId)    		
);

/* List of NFVI POP (node of abstract domains)*/
CREATE TABLE mtpabstrdb.abstrnfvipop  (
    	abstrNfviPopId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	vimId INT UNSIGNED, 
    	geographicalLocationInfo VARCHAR (100),
    	networkConnectivityEndpoint VARCHAR (100),
	abstrDomId INT UNSIGNED, /*key used for stored in 		domain tables */
FOREIGN KEY (abstrDomId) REFERENCES mtpabstrdb.abstrdomain (abstrDomId),
PRIMARY KEY (abstrNfviPopId)
    		);


	/* Radio specific table*/
	/**********Radio coverage area table********************************************************/

		CREATE TABLE mtpabstrdb.radio_coverage_area (
			coverageAreaKey INT UNSIGNED NOT NULL AUTO_INCREMENT,
            coverageAreaId VARCHAR (100),
            geographicalInfo VARCHAR (100),
			coverageAreaMinBandwidth VARCHAR (100), 
			coverageAreaMaxBandwidth VARCHAR (100), 
			coverageAreaDelay VARCHAR (100), 
  
			latitude  VARCHAR (100), 
			longitude  VARCHAR (100), 
			altitude  VARCHAR (100), 
			range_  VARCHAR (100), 
				
			abstrNfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop (abstrNfviPopId),
			PRIMARY KEY (coverageAreaKey)		
		);
        
        /**********pnf list area table********************************************************/

		CREATE TABLE mtpabstrdb.pnfs (
		
			pnfkey INT UNSIGNED NOT NULL AUTO_INCREMENT,
			pnfid VARCHAR (1000), 
			pnfstatus VARCHAR (100), 
							
			abstrNfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop (abstrNfviPopId),
			PRIMARY KEY (pnfkey)		
		);
	/******************************************************************/	

/* MEC specific table*/
/**********MEC Regions table********************************************************/

	CREATE TABLE mtpabstrdb.mec_region_info (
	
			regionId INT UNSIGNED NOT NULL AUTO_INCREMENT,
			latitude  VARCHAR (100), 
			longitude  VARCHAR (100), 
			altitude  VARCHAR (100), 
			range_  VARCHAR (100), 
			
			abstrNfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop (abstrNfviPopId),
			PRIMARY KEY (regionId)		
	);
/******************************************************************/
			
					
			
			
/* List of zoneid (useful for TD1.3) */
CREATE TABLE mtpabstrdb.abstrzoneid (
 abstrResourceZoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
abstrZoneId             VARCHAR (100),
zoneName		VARCHAR (100),
zoneState		VARCHAR (100),
zoneProperty	VARCHAR (100),
metadata 		VARCHAR (100),
/*FOREIGN and Primary Keys*/	
abstrNfviPopId INT UNSIGNED,
FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop  (abstrNfviPopId),
	PRIMARY KEY (abstrResourceZoneId)
    		);

/*table including all memory resources*/
/*table including capacity information of virtual memory*/
CREATE TABLE mtpabstrdb.abstrmemoryresources (
	

    	/*memory parameter according IFA005*/
	availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100),
	
/**/
abstrResourceZoneId INT UNSIGNED,
FOREIGN KEY (abstrResourceZoneId) REFERENCES mtpabstrdb.abstrzoneid (abstrResourceZoneId),
	PRIMARY KEY (abstrResourceZoneId)
    		);

/*table including all storage resources*/
/*table including capacity information of virtual storage*/

CREATE TABLE mtpabstrdb.abstrstorageresources (
	/*Capacity info according IFA005*/
availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100),
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
availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100),

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
srcRouterId 	VARCHAR (100),
	dstRouterId	VARCHAR (100),
    	srcRouterIp 	VARCHAR (100),
	dstRouterIp 	VARCHAR (100),
delay 			VARCHAR (100),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (100),
reservedBandwidth	VARCHAR (100),
totalBandwidth		VARCHAR (100),
allocatedBandwidth	VARCHAR (100),
/*Primary and Foreign Keys*/
	PRIMARY KEY (logicalLinkId)
     		);







/*table including all memory resources*/
CREATE TABLE mtpabstrdb.logicalpath (
	logicalPathId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
abstrSrcNfviPopId INT UNSIGNED,
abstrDestNfviPopId INT UNSIGNED,
srcRouterId 	VARCHAR (100),
	dstRouterId	VARCHAR (100),
    	srcRouterIp 	VARCHAR (100),
	dstRouterIp 	VARCHAR (100),
delay 			VARCHAR (100),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (100),
reservedBandwidth	VARCHAR (100),
totalBandwidth		VARCHAR (100),
allocatedBandwidth	VARCHAR (100),
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
    	name VARCHAR (100), /*From xml files*/
        type VARCHAR (100), /*Radio, Transport, VIM, MEC*/
        ip VARCHAR (100), /*From xml files*/
    	port VARCHAR (100), /*From xml files*/
		mecAssociatedDomainID VARCHAR (100), /*From xml files*/
		
	abstrDomId INT UNSIGNED, 
	FOREIGN KEY (AbstrDomId) REFERENCES mtpabstrdb.abstrdomain (AbstrDomId),
    	PRIMARY KEY (domId) 
    		);

/* MEC specific table*/
/**********MEC Regions table********************************************************/

	CREATE TABLE mtpdomdb.mec_region_info (
	
			regionId INT UNSIGNED NOT NULL AUTO_INCREMENT,
			latitude  VARCHAR (100), 
			longitude  VARCHAR (100), 
			altitude  VARCHAR (100), 
			range_  VARCHAR (100), 
			
			domId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (domId) REFERENCES mtpdomdb.domain (domId),
			PRIMARY KEY (regionId)		
	);
/******************************************************************/

			
			
			
			
			
			
/* List of NFVI POP */
CREATE TABLE mtpdomdb.nfvipop (
    	nfviPopId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	vimId INT UNSIGNED, 
    	geographicalLocationInfo VARCHAR (100),
    	networkConnectivityEndpoint VARCHAR (100),
	domId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (domId) REFERENCES mtpdomdb.domain (domId),	
abstrNfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (abstrNfviPopId) REFERENCES mtpabstrdb.abstrnfvipop  (abstrNfviPopId),
    	PRIMARY KEY (nfviPopId)
    		);

			
	/* Radio specific table*/
	/**********Radio coverage area table********************************************************/

		CREATE TABLE mtpdomdb.radio_coverage_area (
		
			coverageAreaKey INT UNSIGNED NOT NULL AUTO_INCREMENT,
            coverageAreaId VARCHAR (100),
            geographicalInfo VARCHAR (100),
			coverageAreaMinBandwidth VARCHAR (100), 
			coverageAreaMaxBandwidth VARCHAR (100), 
			coverageAreaDelay VARCHAR (100), 
  
			latitude  VARCHAR (100), 
			longitude  VARCHAR (100), 
			altitude  VARCHAR (100), 
			range_  VARCHAR (100), 
				
			nfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
			PRIMARY KEY (coverageAreaKey)		
		);
        
		/**********pnf list area table********************************************************/

		CREATE TABLE mtpdomdb.pnfs (
		
			pnfkey INT UNSIGNED NOT NULL AUTO_INCREMENT,
			pnfid VARCHAR (1000), 
			pnfstatus VARCHAR (100), 
							
			nfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
			PRIMARY KEY (pnfkey)		
		);
        
        
        /**********mf list area table********************************************************/
        CREATE TABLE mtpdomdb.mfs (
		
			mfskey INT UNSIGNED NOT NULL AUTO_INCREMENT,
			pnfid VARCHAR (1000), 
			vnfid VARCHAR (1000),
            mfid VARCHAR (1000), 
							
			nfviPopId INT UNSIGNED, /*key used for stored in 		domain tables */
			FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
			PRIMARY KEY (mfskey)		
		);
	/******************************************************************/			
			
			
			
/* List of Resource Zones */
CREATE TABLE mtpdomdb.zoneid (
    	resourceZoneId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	/*List of zoneid IFA005 parameter + extension*/
zoneId                  VARCHAR (100),
zoneName		VARCHAR (100),
zoneState		VARCHAR (100),
zoneProperty	VARCHAR (100),
metadata 		VARCHAR (100),
	/*Foreign and Primary Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
PRIMARY KEY (resourceZoneId)
    		);


/*table including all memory resources*/
CREATE TABLE mtpdomdb.memoryresources (
    	/*Capacity info according IFA005*/
availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100),
/* IFA 005 VirtualMemory parameters*/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (100),
numaEnabled 	BIT(1),
/* IFA 005 VirtualComputeResourceInformation parameters*/
computeResourceTypeId 	VARCHAR (100),
accelerationCapability	VARCHAR (100),

      /**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
    		);



/*table including all CPU resources*/
CREATE TABLE mtpdomdb.cpuresources (
    	/*cpu parameter according IFA005*/
availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100),
/* IFA 005 VirtualCpu parameters*/
cpuArchitecture VARCHAR (100),
numVirtualCpu  	VARCHAR (100),
cpuClock 		VARCHAR (100),
virtualCpuOversubscriptionPolicy VARCHAR (100),
virtualCpuPinningSupported		VARCHAR (100),	
/* IFA 005 VirtualComputeResourceInformation parameters*/
computeResourceTypeId 	VARCHAR (100),
accelerationCapability	VARCHAR (100),
	/**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
		);





/*table including all storage resources*/
CREATE TABLE mtpdomdb.storageresources (
    	/*storage parameter according IFA005*/
     /*Capacity info according IFA005*/
availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100), 
/**/
	resourceZoneId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (resourceZoneId) REFERENCES mtpdomdb.zoneid (resourceZoneId),
	PRIMARY KEY (resourceZoneId)
     		);



















/*table including all memory resources*/
CREATE TABLE mtpdomdb.networkresources (
	networkResId INT UNSIGNED NOT NULL AUTO_INCREMENT,
srcGwId 	VARCHAR (100),
	dstGwId	VARCHAR (100),
    	srcGWIp 	VARCHAR (100),
	dstGwIp 	VARCHAR (100),
delay 			VARCHAR (100),
/*Capacity info according IFA005*/
availableCapacity	VARCHAR (100),
reservedCapacity		VARCHAR (100),
totalCapacity 		VARCHAR (100),
allocatedCapacity	VARCHAR (100),
/*Parameters according IFA005 VirtualNetworkResourceInformation information element */
networkResourceTypeId 	VARCHAR (100),
networkType 		VARCHAR (100),
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
	srcGwId 	VARCHAR (100),
	dstGwId	VARCHAR (100),
    	srcGWIp 	VARCHAR (100),
	dstGwIp 	VARCHAR (100),
delay		VARCHAR (100),
availableBandwidth	VARCHAR (100),
reservedBandwidth	VARCHAR (100),
totalBandwidth 		VARCHAR (100),
allocatedBandwidth	VARCHAR (100),

	PRIMARY KEY (interDomainLinkId)
     		);


/* List of VIRTUAL COMPUTE FLAVOUR */
CREATE TABLE mtpdomdb.computeflavour (
computeFlavourId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/* Parameters of IFA005 ComputeFlavour Information Element*/
flavourId VARCHAR (100),
accelerationCapability 	VARCHAR (100),
/* Primary and Foreign Keys*/
nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),    
PRIMARY KEY (computeFlavourId) 
    		);


CREATE TABLE mtpdomdb.virtualcpu (
cpuArchitecture VARCHAR (100),
numVirtualCpu  	VARCHAR (100),
cpuClock 		VARCHAR (100),
virtualCpuOversubscriptionPolicy VARCHAR (100),
virtualCpuPinningSupported		VARCHAR (100),	
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
typeVirtualNic 	VARCHAR (100),
typeConfiguration	VARCHAR (100),
bandwidth	VARCHAR (100),
accelerationCapability VARCHAR (100),
metadata	VARCHAR (100),
/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (netInterfaceDataId) 
    		);



CREATE TABLE mtpdomdb.virtualmemorydata (
/**/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (100),
numaEnabled 	BIT(1),
/* Primary and Foreign Keys*/
computeFlavourId INT UNSIGNED,
FOREIGN KEY (computeFlavourId) REFERENCES mtpdomdb.computeflavour (computeFlavourId),
PRIMARY KEY (computeFlavourId) 
    		);

CREATE TABLE mtpdomdb.virtualstoragedata (
storageDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
typeOfStorage	VARCHAR (100),
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
    	servId VARCHAR (100), /*use resourcegroupid IFA005?*/

	/*list of service parameter(TBD)*/
     
    	PRIMARY KEY (servId) 
    		);


/* list of pnf service*/
CREATE TABLE mtpservdb.physicalservices (
	physicalServiceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    reqId INT UNSIGNED, /*used for retrieve and set the outcome*/
	status VARCHAR (100), /*status of request “pending”, “OK”, “NOK”*/
	/*Primary and Foreign Keys*/
	servId VARCHAR (100), /*key used for stored in service tables */
	FOREIGN KEY (servId) REFERENCES mtpservdb.service (servId),
	nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
	PRIMARY KEY (physicalServiceId) 
);

CREATE TABLE mtpservdb.physicalservicerequestdata (
	/*list of parameters for IFA005 compute allocate*/
	pnfId VARCHAR (1000),
    abstrpopId VARCHAR (1000),
    metadata VARCHAR (1000),
	/*Primary and Foreign Keys*/
	physicalServiceId INT UNSIGNED NOT NULL,	
	FOREIGN KEY (physicalServiceId) REFERENCES mtpservdb.physicalservices (physicalServiceId),
	PRIMARY KEY (physicalServiceId) 
);

CREATE TABLE mtpservdb.physicalresources (
	/*list of parameters for IFA005 network allocate*/
	physicalResourcesId 		VARCHAR (100),
	pnfId VARCHAR (1000),
    abstrpopId VARCHAR (1000),
    metadata VARCHAR (1000),
	/* Primary and Foreign Keys */
	physicalServiceId INT UNSIGNED UNIQUE, 
	FOREIGN KEY (physicalServiceId) REFERENCES mtpservdb.physicalservices (physicalServiceId),
	PRIMARY KEY (physicalServiceId) 
);



/* List of compute allocation request */
CREATE TABLE mtpservdb.computeservices (
    	computeServiceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	reqId INT UNSIGNED, /*used for retrieve and set the outcome*/
	status VARCHAR (100), /*status of request “pending”, “OK”, “NOK”*/
	vnfName VARCHAR (100),
	vmName VARCHAR (100),
	floatingIp VARCHAR (100),
     outcome VARCHAR (100),
	/*Primary and Foreign Keys*/
      servId VARCHAR (100), /*key used for stored in service tables */
	FOREIGN KEY (servId) REFERENCES mtpservdb.service (servId),
        nfviPopId INT UNSIGNED, /*key used for stored in domain tables */
	FOREIGN KEY (nfviPopId) REFERENCES mtpdomdb.nfvipop (nfviPopId),
    	PRIMARY KEY (computeServiceId) 
    		);
			
			

/* List of network allocation request */
CREATE TABLE mtpservdb.networkservices (
    	netServId INT UNSIGNED NOT NULL AUTO_INCREMENT, /*key used for stored in service tables */
    	reqId INT UNSIGNED,  /*used for retrieve and set the outcome*/
        status VARCHAR (100), /*status of request “pending”, “OK”, “NOK”*/
        provider VARCHAR (100),
/*list of parameters for IFA005 network allocate*/
	servId VARCHAR (100),
federationIsRequired BOOL,	
	FOREIGN KEY (ServId) REFERENCES mtpservdb.service (servId),
	logicalPathId INT UNSIGNED,
/*FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),*/
    	PRIMARY KEY (netServId) 
    		);

/* List MEC App instance ids  */

CREATE TABLE mtpservdb.mec_service_instances (
appInstanceId VARCHAR (100),
	mecdomId VARCHAR(50), /* used to retrieve mecid*/
/* Primary and Foreign Keys */
computeServiceId INT UNSIGNED UNIQUE, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);
			
			
/* List of network allocation request */

CREATE TABLE mtpservdb.virtualcompute (
/*list of parameters for IFA005 network allocate*/
computeId 		VARCHAR (100),
zoneId 		VARCHAR (100),
virtualDisks 	VARCHAR (100),
vcImageId		VARCHAR (100),
flavourId 		VARCHAR (100),
accelerationCapability 	VARCHAR (100),
computeName 	VARCHAR (100),
operationalState	VARCHAR (100),
hostId 		VARCHAR (100),
/* Primary and Foreign Keys */
computeServiceId INT UNSIGNED UNIQUE, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualcpu (
cpuArchitecture VARCHAR (100),
numVirtualCpu  	VARCHAR (100),
cpuClock 		VARCHAR (100),
virtualCpuOversubscriptionPolicy VARCHAR (100),
/**/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (computeServiceId) 
		);


CREATE TABLE mtpservdb.virtualnetworkinterface (
netInterfaceId INT UNSIGNED NOT NULL AUTO_INCREMENT,
/**/
resourceId		VARCHAR (100),
ownerId 		VARCHAR (100),
networkId 		VARCHAR (100), /*Reference to VirtualNetwork (see ifa005)*/
networkPortId 	VARCHAR (100), /*Reference to VirtualNetworkPort (see ifa005)*/
ipAddress		VARCHAR (100),
typeVirtualNic 	VARCHAR (100),
typeConfiguration	VARCHAR (100),
macAddress		VARCHAR (100),
bandwidth	VARCHAR (100),
accelerationCapability VARCHAR (100),
operationalState		VARCHAR (100),
metadata	VARCHAR (100),
/* Primary and Foreign Keys*/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (netInterfaceId) 
    		);



CREATE TABLE mtpservdb.virtualmemory (
/**/
virtualMemSize 	DECIMAL,
virtualMemOversubscriptionPolicy		VARCHAR (100),
numaEnabled 	BIT(1),
/* Primary and Foreign Keys*/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcompute (computeServiceId),
PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualcpupinning (
cpuPinningPolicy VARCHAR (100),
cpuPinningRules   	VARCHAR (100),
cpuMap		VARCHAR (100),	
/**/
computeServiceId INT UNSIGNED, 
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.virtualcpu (computeServiceId),
PRIMARY KEY (computeServiceId) 
		);



/*list of parameters for IFA005 compute allocate*/
CREATE TABLE mtpservdb.computeservicerequestdata (
	/*list of parameters for IFA005 compute allocate*/
locationConstraints VARCHAR (100),
  reservationId VARCHAR (200),
  computeFlavourId VARCHAR (100),
  resourceGroupId VARCHAR (100),
  metadata VARCHAR (100), /*it is a list*/
  vcImageId VARCHAR (100),
  computeName VARCHAR (100),
  vnfId VARCHAR (1000),
  appDId VARCHAR (100),
  /*Primary and Foreign Keys*/
  computeServiceId INT UNSIGNED NOT NULL,	
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservices (computeServiceId),
    	PRIMARY KEY (computeServiceId) 
    		);


CREATE TABLE mtpservdb.virtualinterfacedata (
interfaceDataId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ipAddress VARCHAR (100),
macAddress VARCHAR (100),
floatingIP VARCHAR (100),
	/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
	FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
PRIMARY KEY (interfaceDataId) 
    		);


CREATE TABLE mtpservdb.affinityorantiaffinityconstraint (
affinityConstraintId INT UNSIGNED NOT NULL AUTO_INCREMENT,
type VARCHAR (100),
scope VARCHAR (100),
affinityAntiAffinityResourceList VARCHAR (100),	
affinityAntiAffinityResourceGroup VARCHAR (100),

/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
    	PRIMARY KEY (affinityConstraintId) 
    		);

CREATE TABLE mtpservdb.userdata (
content VARCHAR (16000),
method VARCHAR (100),
/*Primary and Foreign Keys*/
computeServiceId INT UNSIGNED NOT NULL,
FOREIGN KEY (computeServiceId) REFERENCES mtpservdb.computeservicerequestdata (computeServiceId),
 PRIMARY KEY (computeServiceId) 
    		);







CREATE TABLE mtpservdb.networkservicerequestdata (
/*list of parameters for IFA005 network allocate*/
  locationConstraints VARCHAR (100),
  reservationId VARCHAR (200),
  affinityOrAntiAffinityConstraints VARCHAR (100), /*it is a list*/
  resourceGroupId VARCHAR (100),
metadata VARCHAR (100), /*it is a List*/
networkResourceType VARCHAR (100),
  networkResourceName VARCHAR (100),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservices (netServId),
PRIMARY KEY (netServId) 
    		);

CREATE TABLE mtpservdb.networksubnetdata (
networkId		INT UNSIGNED,
ipVersion		VARCHAR (100),
gatewayIp		VARCHAR (100),		
cidr			VARCHAR (100),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (100),
metadata		VARCHAR (100),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
PRIMARY KEY (netServId) 
    		);



CREATE TABLE mtpservdb.virtualnetworkportdata (
portType		VARCHAR (100),
networkId		INT UNSIGNED,	
segmentId		INT UNSIGNED,
bandwidth	DECIMAL,
metadata	VARCHAR (100),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
    	PRIMARY KEY (netServId) 
    		);






CREATE TABLE mtpservdb.virtualnetworkdata (
/*list of parameters for IFA005 network allocate*/
  bandwidth Decimal,
networkType	VARCHAR (100),
segmentType	VARCHAR (100),
isShared      	BIT(1),
sharingCriteria VARCHAR (100),
metadata VARCHAR (100),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.networkservicerequestdata (netServId),
    	PRIMARY KEY (netServId) 
    		);




CREATE TABLE mtpservdb.networkqos
 (
     netQosId 					INT UNSIGNED NOT NULL AUTO_INCREMENT,
	qosName VARCHAR (100),
qosValue VARCHAR (100),
/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
	FOREIGN KEY (netServId) REFERENCES mtpservdb.virtualnetworkdata (netServId),
PRIMARY KEY (netQosId)
     		);




CREATE TABLE mtpservdb.layer3connectivityinformation (
layer3ConnInfoId INT UNSIGNED NOT NULL AUTO_INCREMENT,
networkId		INT UNSIGNED,
ipVersion		VARCHAR (100),
gatewayIp		VARCHAR (100),		
cidr			VARCHAR (100),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (100),
metadata		VARCHAR (100),
	/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.virtualnetworkdata (netServId),
PRIMARY KEY (layer3ConnInfoId)
    		);




CREATE TABLE mtpservdb.virtualnetwork (
/*list of parameters for IFA005 network allocate*/
  virtualNetworkId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  networkResourceName VARCHAR (100),
  segmentType VARCHAR (100),  
  isShared BOOL NOT NULL,
  zoneId VARCHAR (100),
  networkResourceId VARCHAR (100),
  networkType VARCHAR (100),  
  operationalState VARCHAR (100),  
  sharingCriteria VARCHAR (100),  
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
	qosName VARCHAR (100),
qosValue VARCHAR (100),

/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED NOT NULL,
FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId),
PRIMARY KEY (supportedNetQosId)
     		);




CREATE TABLE mtpservdb.virtualnetworkport (
netPortId		INT UNSIGNED NOT NULL AUTO_INCREMENT,

resourceId 	INT UNSIGNED,
portType		VARCHAR (100),
attachedResourceId	INT UNSIGNED, /* Reference to VirtualNetworkInterface */
segmentId		INT UNSIGNED,
bandwidth	DECIMAL,
metadata	VARCHAR (100),
	/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED,
	FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId),
    	
PRIMARY KEY (netPortId) 
    		);



CREATE TABLE mtpservdb.networksubnet (
netSubnetId INT UNSIGNED NOT NULL AUTO_INCREMENT,
resourceId 	VARCHAR (100),
ipVersion		VARCHAR (100),
gatewayIp		VARCHAR (100),		
cidr			VARCHAR (100),
isDhcpEnabled    BIT(1),
addressPool	VARCHAR (100),
metadata		VARCHAR (100),
	/*Primary and Foreign Keys*/
virtualNetworkId INT UNSIGNED,
	FOREIGN KEY (virtualNetworkId) REFERENCES mtpservdb.virtualnetwork (virtualNetworkId), 
PRIMARY KEY (netSubnetId)
    		);


CREATE TABLE mtpservdb.networkservices_reverse_networkservices (
id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	netServId  VARCHAR (100),
	reverse_netServId VARCHAR (100),
PRIMARY KEY (id)
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

CREATE TABLE mtpdomdb.logicalpath_interdomainlink (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	interDomainLinkId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpabstrdb.logicalpath (logicalPathId),
	FOREIGN KEY (interDomainLinkId) REFERENCES mtpdomdb.interdomainlink (interDomainLinkId),
PRIMARY KEY (id)

	     	);


			/***************************************** FEDERATED RESOURCES DB ***********************************************************/
			
			
CREATE DATABASE mtpfeddb;


/* List of NFVI POP */
CREATE TABLE mtpfeddb.nfvipop  (
		
    	nfviPopId  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    	/*federatedVimId INT UNSIGNED,*/
        federatedVimId VARCHAR (100),
    	geographicalLocationInfo VARCHAR (100),
    	networkConnectivityEndpoint VARCHAR (100),
		PRIMARY KEY (nfviPopId)
    		);



/*table including all memory resources*/
CREATE TABLE mtpfeddb.fed_logicallink (
	logicalLinkId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
	srcRouterId 	VARCHAR (100),
	dstRouterId	VARCHAR (100),
	srcRouterIp 	VARCHAR (100),
	dstRouterIp 	VARCHAR (100),
	abstrSrcNfviPopId INT UNSIGNED,
	abstrDestNfviPopId INT UNSIGNED,
	delay 			VARCHAR (100),
	cost 			VARCHAR (100),
	totalBandwidth		VARCHAR (100),
	reservedBandwidth	VARCHAR (100),
	availableBandwidth	VARCHAR (100),
    allocatedBandwidth	VARCHAR (100),
	networkLayer		VARCHAR (100),
interNfviPopNetworkType	VARCHAR (100),
interNfviPopNetworkTopology 	VARCHAR (100),
/*Primary and Foreign Keys*/
	PRIMARY KEY (logicalLinkId)
     		);
		

		/*table including all memory resources*/
CREATE TABLE mtpfeddb.fed_logicalpath (
	logicalPathId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
abstrSrcNfviPopId INT UNSIGNED,
abstrDestNfviPopId INT UNSIGNED,
srcRouterId 	VARCHAR (100),
	dstRouterId	VARCHAR (100),
    	srcRouterIp 	VARCHAR (100),
	dstRouterIp 	VARCHAR (100),
delay 			VARCHAR (100),
/*Network capacity info according IFA005*/
availableBandwidth	VARCHAR (100),
reservedBandwidth	VARCHAR (100),
totalBandwidth		VARCHAR (100),
allocatedBandwidth	VARCHAR (100),
networkLayer		VARCHAR (100),
interNfviPopNetworkType	VARCHAR (100),
interNfviPopNetworkTopology 	VARCHAR (100),
/*Primary and Foreign Keys*/
logicalLinkId INT UNSIGNED, 
	FOREIGN KEY (logicalLinkId) REFERENCES mtpfeddb.fed_logicallink (logicalLinkId),
	PRIMARY KEY (logicalPathId)
     		);
		
		
		
		
/*table including all memory resources*/
CREATE TABLE mtpfeddb.fed_interdomainlink (
	interDomainLinkId INT UNSIGNED NOT NULL AUTO_INCREMENT,    	
	srcGwId 	VARCHAR (100),
	dstGwId	VARCHAR (100),
	srcGwIp 	VARCHAR (100),
	dstGwIp 	VARCHAR (100),
	abstrSrcNfviPopId INT UNSIGNED,
	abstrDestNfviPopId INT UNSIGNED,
	delay 			VARCHAR (100),
	cost 			VARCHAR (100),
	totalBandwidth		VARCHAR (100),
	reservedBandwidth	VARCHAR (100),
	availableBandwidth	VARCHAR (100),
        allocatedBandwidth	VARCHAR (100),
	networkLayer		VARCHAR (100),
interNfviPopNetworkType	VARCHAR (100),
interNfviPopNetworkTopology 	VARCHAR (100),
/*Primary and Foreign Keys*/
	PRIMARY KEY (interDomainLinkId)
     		);

			
			

CREATE TABLE mtpfeddb.fed_logicalpath_networkresources (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	networkResId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpfeddb.fed_logicalpath (logicalPathId),
	FOREIGN KEY (networkResId) REFERENCES mtpdomdb.networkresources (networkResId),
PRIMARY KEY (id)
	     	);

CREATE TABLE mtpfeddb.fed_logicalpath_fed_interdomainlink (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	interDomainLinkId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpfeddb.fed_logicalpath (logicalPathId),
	FOREIGN KEY (interDomainLinkId) REFERENCES mtpfeddb.fed_interdomainlink (interDomainLinkId),
PRIMARY KEY (id)

	     	);
			
CREATE TABLE mtpfeddb.fed_logicalpath_interdomainlink (
    	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	logicalPathId INT UNSIGNED,
	interDomainLinkId INT UNSIGNED,
	FOREIGN KEY (logicalPathId) REFERENCES mtpfeddb.fed_logicalpath (logicalPathId),
	FOREIGN KEY (interDomainLinkId) REFERENCES mtpdomdb.interdomainlink (interDomainLinkId),
PRIMARY KEY (id)

	     	);




