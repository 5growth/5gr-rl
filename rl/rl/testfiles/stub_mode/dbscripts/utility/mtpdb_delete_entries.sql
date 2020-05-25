DELETE FROM `mtpdomdb`.`cpuresources` WHERE resourceZoneId<1000000;
DELETE FROM `mtpdomdb`.`memoryresources` WHERE resourceZoneId<1000000;
DELETE FROM `mtpdomdb`.`networkresources` WHERE networkResId<10000000;
DELETE FROM `mtpdomdb`.`zoneid` WHERE resourceZoneId<10000000;
DELETE FROM `mtpdomdb`.`nfvipop` WHERE nfviPopId < 10000000;
DELETE FROM `mtpdomdb`.`domain` WHERE domId<100000000;
DELETE FROM `mtpdomdb`.`interdomainlink` WHERE interDomainLinkId<100000000;


<!--COMPUTE FLAVOUR -->

DELETE FROM `mtpdomdb`.`virtualstoragedata`WHERE computeFlavourId<1000;
DELETE FROM `mtpdomdb`.`virtualnetworkinterfacedata` WHERE computeFlavourId<1000;
DELETE FROM `mtpdomdb`.`virtualmemorydata` WHERE computeFlavourId<1000;
DELETE FROM `mtpdomdb`.`virtualcpu` WHERE computeFlavourId<1000;
DELETE FROM `mtpdomdb`.`computeflavour` WHERE computeFlavourId<1000;



DELETE FROM `mtpabstrdb`.`abstrcpuresources` WHERE abstrResourceZoneId<1000000;
DELETE FROM `mtpabstrdb`.`abstrmemoryresources` WHERE abstrResourceZoneId<1000000;
DELETE FROM `mtpabstrdb`.`logicallink` WHERE logicalLinkId<1000000000;
DELETE FROM `mtpabstrdb`.`logicalpath` WHERE logicalPathId<1000000000;
DELETE FROM `mtpabstrdb`.`abstrzoneid` WHERE abstrResourceZoneId<10000;
DELETE FROM `mtpabstrdb`.`abstrnfvipop` WHERE abstrNfviPopId < 10000000;
DELETE FROM `mtpabstrdb`.`abstrdomain` WHERE abstrDomId<100000000;

