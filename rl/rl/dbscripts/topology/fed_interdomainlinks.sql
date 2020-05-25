/*****************************SAMPLE DATA *****************************/
/*------------------ Federeted InterDomain Links -------------------------*/



INSERT INTO `mtpfeddb`.`fed_interdomainlink`
(`interDomainLinkId`,
`srcGwIp`,
`dstGwIp`,
`srcGwId`,
`dstGwId`,
`abstrSrcNfviPopId`,
`abstrDestNfviPopId`,
`delay`,
`cost`,
`totalBandwidth`,
`reservedBandwidth`,
`availableBandwidth`,
`networkLayer`,
`interNfviPopNetworkType`,
`interNfviPopNetworkTopology`)
VALUES
(1111,
"0.0.0.91",
"0.0.0.92",
"192.168.10.10",
"192.168.1.20",
91,
31,
"10",
"1",
"1000",
"",
"1000",
"",
"",
"");


INSERT INTO `mtpfeddb`.`fed_interdomainlink`
(`interDomainLinkId`,
`srcGwIp`,
`dstGwIp`,
`srcGwId`,
`dstGwId`,
`abstrSrcNfviPopId`,
`abstrDestNfviPopId`,
`delay`,
`cost`,
`totalBandwidth`,
`reservedBandwidth`,
`availableBandwidth`,
`networkLayer`,
`interNfviPopNetworkType`,
`interNfviPopNetworkTopology`)
VALUES
(1112,
"0.0.0.92",
"0.0.0.91",
"192.168.1.20",
"192.168.10.10",
31,
91,
"10",
"1",
"1000",
"",
"1000",
"",
"",
"");











