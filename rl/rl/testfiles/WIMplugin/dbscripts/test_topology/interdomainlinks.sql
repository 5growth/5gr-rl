/*****************************SAMPLE DATA *****************************/
/*------------------ InterDomain Links -------------------------*/


/*----------------------------------DOM1-DOM5---------------------------*/

/*--------------------------------- LINK 1 –> DOM1-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(1, 
 5,
"16842753", /*1.1.0.1*/
"83951617", /*5.1.0.1*/
"15.1.0.1",
"15.1.0.5",
"10",
"10000000",
"0",
"10000000",
"0");

/*--------------------------------- LINK 2 –> DOM5-DOM1 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(5, 
 1,
"83951617", /*5.1.0.1*/
"16842753", /*1.1.0.1*/
"15.1.0.5",
"15.1.0.1",
"10",
"10000000",
"0",
"10000000",
"0");




/*--------------------------------- LINK 3 –> DOM1-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(1, 
 5,
"16908289", /*1.2.0.1*/
"83951617", /*5.1.0.1*/
"15.2.0.1",
"15.2.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 4 –> DOM5-DOM1 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(5, 
 1,
"83951617", /*5.1.0.1 */
"16908289", /*1.2.0.1*/
"15.2.0.5",
"15.2.0.1",
"10",
"10000000",
"0",
"10000000",
"0");


/*----------------------------------DOM2-DOM5---------------------------*/

/*--------------------------------- LINK 1 –> DOM2-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"33619969", /*2.1.0.1*/
"84017153", /*5.2.0.1*/
"25.1.0.1",
"25.1.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 2 –> DOM5-DOM2 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(5, 
 2,
"84017153", /*5.2.0.1 */
"33619969", /*2.1.0.1*/
"25.1.0.5",
"25.1.0.1",
"10",
"10000000",
"0",
"10000000",
"0");



/*--------------------------------- LINK 3 –> DOM2-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"33685505", /*2.2.0.1*/
"84017153", /*5.2.0.1*/
"25.2.0.1",
"25.2.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 4 –> DOM5-DOM2 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"84017153", /*5.2.0.1 */
"33685505", /*2.2.0.1*/
"25.2.0.5",
"25.2.0.1",
"10",
"10000000",
"0",
"10000000",
"0");
/*----------------------------------DOM3-DOM5---------------------------*/

/*--------------------------------- LINK 1 –> DOM3-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"50397185", /*3.1.0.1*/
"84148225", /*5.4.0.1*/
"35.1.0.1",
"35.1.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 2 –> DOM5-DOM3 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(5, 
 2,
"84148225", /*5.4.0.1*/
"50397185", /*3.1.0.1 */
"35.1.0.5",
"35.1.0.1",
"10",
"10000000",
"0",
"10000000",
"0");



/*--------------------------------- LINK 3 –> DOM3-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"50462721", /*3.2.0.1*/
"84148225", /*5.4.0.1*/
"35.2.0.1",
"35.2.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 4 –> DOM5-DOM3 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"84148225", /*5.4.0.1 */
"50462721", /*3.2.0.1*/
"35.2.0.5",
"35.2.0.1",
"10",
"10000000",
"0",
"10000000",
"0");




/*----------------------------------DOM4-DOM5---------------------------*/

/*--------------------------------- LINK 1 –> DOM4-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"67174401", /*4.1.0.1*/
"84082689", /*5.3.0.1*/
"45.1.0.1",
"45.1.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 2 –> DOM5-DOM4 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(5, 
 2,
"84082689", /*5.3.0.1*/
"67174401", /*4.1.0.1*/
"35.1.0.1",
"35.1.0.5",
"10",
"10000000",
"0",
"10000000",
"0");



/*--------------------------------- LINK 3 –> DOM4-DOM5 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"67239937", /*4.2.0.1*/
"84082689", /*5.3.0.1*/
"45.2.0.1",
"45.2.0.5",
"10",
"10000000",
"0",
"10000000",
"0");
/*--------------------------------- LINK 4 –> DOM5-DOM4 --------------------*/

INSERT INTO `mtpdomdb`.`interdomainlink`
(`srcDomId`,
`dstDomId`,
`srcGwId`,
`dstGwId`,
`srcGWIp`,
`dstGwIp`,
`delay`,
`availableBandwidth`,
`reservedBandwidth`,
`totalBandwidth`,
`allocatedBandwidth`)
VALUES
(2, 
 5,
"84082689", /*5.3.0.1 */
"67239937", /*4.2.0.1*/
"45.2.0.5",
"45.2.0.1",
"10",
"10000000",
"0",
"10000000",
"0");


