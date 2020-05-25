/*****************************COMPUTE FLAVOUR SAMPLE DATA *****************************/

/*----------------------------------NfviPopId=11---------------------------*/

/*--------------------------------- COMPUTE_FLAVOUR_ID=1    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (1,"flavor_spr2","accCapability1",11);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,1);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",1);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",1);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 1);

/*--------------------------------- COMPUTE_FLAVOUR_ID=2    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (2,"flavor_vFCS","accCapability1",11);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,2);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",2);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",10,"","",2);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 2);
/*--------------------------------- COMPUTE_FLAVOUR_ID=3    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (3,"flavor_vEPC","accCapability1",11);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,3);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",3);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",3);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 3);
/**********************************************************************************/
/*----------------------------------NfviPopId=21---------------------------*/

/*--------------------------------- COMPUTE_FLAVOUR_ID=1    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (4,"flavor_vEPC","accCapability1",21);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,4);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",4);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",4);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 4);

/*--------------------------------- COMPUTE_FLAVOUR_ID=2    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (5,"flavor_vFCS","accCapability1",21);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,5);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",5);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",5);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 5);
/*--------------------------------- COMPUTE_FLAVOUR_ID=3    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (6,"3","accCapability1",21);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,6);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",6);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",6);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 6);

/**********************************************************************************/
/*----------------------------------NfviPopId=31---------------------------*/

/*--------------------------------- COMPUTE_FLAVOUR_ID=1    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (7,"1","accCapability1",31);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,7);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",7);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",7);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 7);

/*--------------------------------- COMPUTE_FLAVOUR_ID=2    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (8,"2","accCapability1",31);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,8);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",8);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",8);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 8);
/*--------------------------------- COMPUTE_FLAVOUR_ID=3    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (9,"3","accCapability1",31);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,9);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",9);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",9);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 9);

/**********************************************************************************/
/*----------------------------------NfviPopId=41---------------------------*/

/*--------------------------------- COMPUTE_FLAVOUR_ID=1    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (10,"1","accCapability1",41);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,10);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",10);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",10);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 10);

/*--------------------------------- COMPUTE_FLAVOUR_ID=2    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (11,"2","accCapability1",41);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,11);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",11);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",11);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 11);
/*--------------------------------- COMPUTE_FLAVOUR_ID=3    --------------------*/

INSERT INTO `mtpdomdb`.`computeflavour`
(`computeFlavourId`,`flavourId`,`accelerationCapability`,`NfviPopId`)
VALUES (12,"3","accCapability1",41);

INSERT INTO `mtpdomdb`.`virtualstoragedata`
(`typeOfStorage`,`sizeOfStorage`,`computeFlavourId`)
VALUES ("typeStorage1",10,12);

INSERT INTO `mtpdomdb`.`virtualcpu`
(`cpuArchitecture`,`numVirtualCpu`,`cpuClock`,`virtualCpuOversubscriptionPolicy`,`computeFlavourId`)
VALUES ("x86",4,1,"policy1",12);

INSERT INTO `mtpdomdb`.`virtualnetworkinterfacedata`
(`networkId`,`networkPortId`,`typeVirtualNic`,`typeConfiguration`,`bandwidth`,`accelerationCapability`,`metadata`,`computeFlavourId`)
VALUES (0,0,0,"",100,"","",12);

INSERT INTO `mtpdomdb`.`virtualmemorydata`
(`virtualMemSize`,`virtualMemOversubscriptionPolicy`,`numaEnabled`,`computeFlavourId`)
VALUES (100,"policy1",true, 12);