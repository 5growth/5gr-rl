CREATE DATABASE  IF NOT EXISTS `mtpservdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mtpservdb`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mtpservdb
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `affinityorantiaffinityconstraint`
--

DROP TABLE IF EXISTS `affinityorantiaffinityconstraint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `affinityorantiaffinityconstraint` (
  `affinityConstraintId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `scope` varchar(100) DEFAULT NULL,
  `affinityAntiAffinityResourceList` varchar(100) DEFAULT NULL,
  `affinityAntiAffinityResourceGroup` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`affinityConstraintId`),
  KEY `computeServiceId` (`computeServiceId`),
  CONSTRAINT `affinityorantiaffinityconstraint_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `computeservicerequestdata` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `affinityorantiaffinityconstraint`
--

LOCK TABLES `affinityorantiaffinityconstraint` WRITE;
/*!40000 ALTER TABLE `affinityorantiaffinityconstraint` DISABLE KEYS */;
/*!40000 ALTER TABLE `affinityorantiaffinityconstraint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `computeservicerequestdata`
--

DROP TABLE IF EXISTS `computeservicerequestdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `computeservicerequestdata` (
  `locationConstraints` varchar(100) DEFAULT NULL,
  `reservationId` varchar(200) DEFAULT NULL,
  `computeFlavourId` varchar(100) DEFAULT NULL,
  `resourceGroupId` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `vcImageId` varchar(100) DEFAULT NULL,
  `computeName` varchar(100) DEFAULT NULL,
  `appDId` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  CONSTRAINT `computeservicerequestdata_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `computeservices` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computeservicerequestdata`
--

LOCK TABLES `computeservicerequestdata` WRITE;
/*!40000 ALTER TABLE `computeservicerequestdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `computeservicerequestdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `computeservices`
--

DROP TABLE IF EXISTS `computeservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `computeservices` (
  `computeServiceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reqId` int(10) unsigned DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `vnfName` varchar(100) DEFAULT NULL,
  `vmName` varchar(100) DEFAULT NULL,
  `floatingIp` varchar(100) DEFAULT NULL,
  `outcome` varchar(100) DEFAULT NULL,
  `servId` varchar(100) DEFAULT NULL,
  `nfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`computeServiceId`),
  KEY `servId` (`servId`),
  KEY `nfviPopId` (`nfviPopId`),
  CONSTRAINT `computeservices_ibfk_1` FOREIGN KEY (`servId`) REFERENCES `service` (`servid`),
  CONSTRAINT `computeservices_ibfk_2` FOREIGN KEY (`nfviPopId`) REFERENCES `mtpdomdb`.`nfvipop` (`nfvipopid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computeservices`
--

LOCK TABLES `computeservices` WRITE;
/*!40000 ALTER TABLE `computeservices` DISABLE KEYS */;
/*!40000 ALTER TABLE `computeservices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `layer3connectivityinformation`
--

DROP TABLE IF EXISTS `layer3connectivityinformation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `layer3connectivityinformation` (
  `layer3ConnInfoId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `networkId` int(10) unsigned DEFAULT NULL,
  `ipVersion` varchar(100) DEFAULT NULL,
  `gatewayIp` varchar(100) DEFAULT NULL,
  `cidr` varchar(100) DEFAULT NULL,
  `isDhcpEnabled` bit(1) DEFAULT NULL,
  `addressPool` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `netServId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`layer3ConnInfoId`),
  KEY `netServId` (`netServId`),
  CONSTRAINT `layer3connectivityinformation_ibfk_1` FOREIGN KEY (`netServId`) REFERENCES `virtualnetworkdata` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `layer3connectivityinformation`
--

LOCK TABLES `layer3connectivityinformation` WRITE;
/*!40000 ALTER TABLE `layer3connectivityinformation` DISABLE KEYS */;
/*!40000 ALTER TABLE `layer3connectivityinformation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mec_service_instances`
--

DROP TABLE IF EXISTS `mec_service_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mec_service_instances` (
  `appInstanceId` varchar(100) DEFAULT NULL,
  `mecdomId` varchar(50) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  UNIQUE KEY `computeServiceId` (`computeServiceId`),
  CONSTRAINT `mec_service_instances_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `computeservices` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mec_service_instances`
--

LOCK TABLES `mec_service_instances` WRITE;
/*!40000 ALTER TABLE `mec_service_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `mec_service_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networkqos`
--

DROP TABLE IF EXISTS `networkqos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `networkqos` (
  `netQosId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `qosName` varchar(100) DEFAULT NULL,
  `qosValue` varchar(100) DEFAULT NULL,
  `netServId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`netQosId`),
  KEY `netServId` (`netServId`),
  CONSTRAINT `networkqos_ibfk_1` FOREIGN KEY (`netServId`) REFERENCES `virtualnetworkdata` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networkqos`
--

LOCK TABLES `networkqos` WRITE;
/*!40000 ALTER TABLE `networkqos` DISABLE KEYS */;
/*!40000 ALTER TABLE `networkqos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networkservicerequestdata`
--

DROP TABLE IF EXISTS `networkservicerequestdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `networkservicerequestdata` (
  `locationConstraints` varchar(100) DEFAULT NULL,
  `reservationId` varchar(200) DEFAULT NULL,
  `affinityOrAntiAffinityConstraints` varchar(100) DEFAULT NULL,
  `resourceGroupId` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `networkResourceType` varchar(100) DEFAULT NULL,
  `networkResourceName` varchar(100) DEFAULT NULL,
  `netServId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`netServId`),
  CONSTRAINT `networkservicerequestdata_ibfk_1` FOREIGN KEY (`netServId`) REFERENCES `networkservices` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networkservicerequestdata`
--

LOCK TABLES `networkservicerequestdata` WRITE;
/*!40000 ALTER TABLE `networkservicerequestdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `networkservicerequestdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networkservices`
--

DROP TABLE IF EXISTS `networkservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `networkservices` (
  `netServId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `reqId` int(10) unsigned DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `provider` varchar(100) DEFAULT NULL,
  `servId` varchar(100) DEFAULT NULL,
  `federationIsRequired` tinyint(1) DEFAULT NULL,
  `logicalPathId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`netServId`),
  KEY `servId` (`servId`),
  CONSTRAINT `networkservices_ibfk_1` FOREIGN KEY (`servId`) REFERENCES `service` (`servid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networkservices`
--

LOCK TABLES `networkservices` WRITE;
/*!40000 ALTER TABLE `networkservices` DISABLE KEYS */;
/*!40000 ALTER TABLE `networkservices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networksubnet`
--

DROP TABLE IF EXISTS `networksubnet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `networksubnet` (
  `netSubnetId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `resourceId` varchar(100) DEFAULT NULL,
  `ipVersion` varchar(100) DEFAULT NULL,
  `gatewayIp` varchar(100) DEFAULT NULL,
  `cidr` varchar(100) DEFAULT NULL,
  `isDhcpEnabled` bit(1) DEFAULT NULL,
  `addressPool` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `virtualNetworkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`netSubnetId`),
  KEY `virtualNetworkId` (`virtualNetworkId`),
  CONSTRAINT `networksubnet_ibfk_1` FOREIGN KEY (`virtualNetworkId`) REFERENCES `virtualnetwork` (`virtualnetworkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networksubnet`
--

LOCK TABLES `networksubnet` WRITE;
/*!40000 ALTER TABLE `networksubnet` DISABLE KEYS */;
/*!40000 ALTER TABLE `networksubnet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networksubnetdata`
--

DROP TABLE IF EXISTS `networksubnetdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `networksubnetdata` (
  `networkId` int(10) unsigned DEFAULT NULL,
  `ipVersion` varchar(100) DEFAULT NULL,
  `gatewayIp` varchar(100) DEFAULT NULL,
  `cidr` varchar(100) DEFAULT NULL,
  `isDhcpEnabled` bit(1) DEFAULT NULL,
  `addressPool` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `netServId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`netServId`),
  CONSTRAINT `networksubnetdata_ibfk_1` FOREIGN KEY (`netServId`) REFERENCES `networkservicerequestdata` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networksubnetdata`
--

LOCK TABLES `networksubnetdata` WRITE;
/*!40000 ALTER TABLE `networksubnetdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `networksubnetdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `service` (
  `servId` varchar(100) NOT NULL,
  PRIMARY KEY (`servId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supportednetworkqos`
--

DROP TABLE IF EXISTS `supportednetworkqos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `supportednetworkqos` (
  `supportedNetQosId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `qosName` varchar(100) DEFAULT NULL,
  `qosValue` varchar(100) DEFAULT NULL,
  `virtualNetworkId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`supportedNetQosId`),
  KEY `virtualNetworkId` (`virtualNetworkId`),
  CONSTRAINT `supportednetworkqos_ibfk_1` FOREIGN KEY (`virtualNetworkId`) REFERENCES `virtualnetwork` (`virtualnetworkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supportednetworkqos`
--

LOCK TABLES `supportednetworkqos` WRITE;
/*!40000 ALTER TABLE `supportednetworkqos` DISABLE KEYS */;
/*!40000 ALTER TABLE `supportednetworkqos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdata`
--

DROP TABLE IF EXISTS `userdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `userdata` (
  `content` varchar(16000) DEFAULT NULL,
  `method` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  CONSTRAINT `userdata_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `computeservicerequestdata` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdata`
--

LOCK TABLES `userdata` WRITE;
/*!40000 ALTER TABLE `userdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `userdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcompute`
--

DROP TABLE IF EXISTS `virtualcompute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcompute` (
  `computeId` varchar(100) DEFAULT NULL,
  `zoneId` varchar(100) DEFAULT NULL,
  `virtualDisks` varchar(100) DEFAULT NULL,
  `vcImageId` varchar(100) DEFAULT NULL,
  `flavourId` varchar(100) DEFAULT NULL,
  `accelerationCapability` varchar(100) DEFAULT NULL,
  `computeName` varchar(100) DEFAULT NULL,
  `operationalState` varchar(100) DEFAULT NULL,
  `hostId` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  UNIQUE KEY `computeServiceId` (`computeServiceId`),
  CONSTRAINT `virtualcompute_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `computeservices` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcompute`
--

LOCK TABLES `virtualcompute` WRITE;
/*!40000 ALTER TABLE `virtualcompute` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualcompute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcpu`
--

DROP TABLE IF EXISTS `virtualcpu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcpu` (
  `cpuArchitecture` varchar(100) DEFAULT NULL,
  `numVirtualCpu` varchar(100) DEFAULT NULL,
  `cpuClock` varchar(100) DEFAULT NULL,
  `virtualCpuOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  CONSTRAINT `virtualcpu_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `virtualcompute` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcpu`
--

LOCK TABLES `virtualcpu` WRITE;
/*!40000 ALTER TABLE `virtualcpu` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualcpu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcpupinning`
--

DROP TABLE IF EXISTS `virtualcpupinning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcpupinning` (
  `cpuPinningPolicy` varchar(100) DEFAULT NULL,
  `cpuPinningRules` varchar(100) DEFAULT NULL,
  `cpuMap` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  CONSTRAINT `virtualcpupinning_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `virtualcpu` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcpupinning`
--

LOCK TABLES `virtualcpupinning` WRITE;
/*!40000 ALTER TABLE `virtualcpupinning` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualcpupinning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualinterfacedata`
--

DROP TABLE IF EXISTS `virtualinterfacedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualinterfacedata` (
  `interfaceDataId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ipAddress` varchar(100) DEFAULT NULL,
  `macAddress` varchar(100) DEFAULT NULL,
  `floatingIP` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`interfaceDataId`),
  KEY `computeServiceId` (`computeServiceId`),
  CONSTRAINT `virtualinterfacedata_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `computeservicerequestdata` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualinterfacedata`
--

LOCK TABLES `virtualinterfacedata` WRITE;
/*!40000 ALTER TABLE `virtualinterfacedata` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualinterfacedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualmemory`
--

DROP TABLE IF EXISTS `virtualmemory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualmemory` (
  `virtualMemSize` decimal(10,0) DEFAULT NULL,
  `virtualMemOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `numaEnabled` bit(1) DEFAULT NULL,
  `computeServiceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeServiceId`),
  CONSTRAINT `virtualmemory_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `virtualcompute` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualmemory`
--

LOCK TABLES `virtualmemory` WRITE;
/*!40000 ALTER TABLE `virtualmemory` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualmemory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetwork`
--

DROP TABLE IF EXISTS `virtualnetwork`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetwork` (
  `virtualNetworkId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `networkResourceName` varchar(100) DEFAULT NULL,
  `segmentType` varchar(100) DEFAULT NULL,
  `isShared` tinyint(1) NOT NULL,
  `zoneId` varchar(100) DEFAULT NULL,
  `networkResourceId` varchar(100) DEFAULT NULL,
  `networkType` varchar(100) DEFAULT NULL,
  `operationalState` varchar(100) DEFAULT NULL,
  `sharingCriteria` varchar(100) DEFAULT NULL,
  `bandwidth` decimal(10,0) DEFAULT NULL,
  `netResIdRef` int(10) unsigned DEFAULT NULL,
  `nfviPopId` int(10) unsigned DEFAULT NULL,
  `netServId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`virtualNetworkId`),
  KEY `nfviPopId` (`nfviPopId`),
  KEY `netServId` (`netServId`),
  CONSTRAINT `virtualnetwork_ibfk_1` FOREIGN KEY (`nfviPopId`) REFERENCES `mtpdomdb`.`nfvipop` (`nfvipopid`),
  CONSTRAINT `virtualnetwork_ibfk_2` FOREIGN KEY (`netServId`) REFERENCES `networkservices` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetwork`
--

LOCK TABLES `virtualnetwork` WRITE;
/*!40000 ALTER TABLE `virtualnetwork` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualnetwork` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetworkdata`
--

DROP TABLE IF EXISTS `virtualnetworkdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetworkdata` (
  `bandwidth` decimal(10,0) DEFAULT NULL,
  `networkType` varchar(100) DEFAULT NULL,
  `segmentType` varchar(100) DEFAULT NULL,
  `isShared` bit(1) DEFAULT NULL,
  `sharingCriteria` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `netServId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`netServId`),
  CONSTRAINT `virtualnetworkdata_ibfk_1` FOREIGN KEY (`netServId`) REFERENCES `networkservicerequestdata` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetworkdata`
--

LOCK TABLES `virtualnetworkdata` WRITE;
/*!40000 ALTER TABLE `virtualnetworkdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualnetworkdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetworkinterface`
--

DROP TABLE IF EXISTS `virtualnetworkinterface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetworkinterface` (
  `netInterfaceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `resourceId` varchar(100) DEFAULT NULL,
  `ownerId` varchar(100) DEFAULT NULL,
  `networkId` varchar(100) DEFAULT NULL,
  `networkPortId` varchar(100) DEFAULT NULL,
  `ipAddress` varchar(100) DEFAULT NULL,
  `typeVirtualNic` varchar(100) DEFAULT NULL,
  `typeConfiguration` varchar(100) DEFAULT NULL,
  `macAddress` varchar(100) DEFAULT NULL,
  `bandwidth` varchar(100) DEFAULT NULL,
  `accelerationCapability` varchar(100) DEFAULT NULL,
  `operationalState` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `computeServiceId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`netInterfaceId`),
  KEY `computeServiceId` (`computeServiceId`),
  CONSTRAINT `virtualnetworkinterface_ibfk_1` FOREIGN KEY (`computeServiceId`) REFERENCES `virtualcompute` (`computeserviceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetworkinterface`
--

LOCK TABLES `virtualnetworkinterface` WRITE;
/*!40000 ALTER TABLE `virtualnetworkinterface` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualnetworkinterface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetworkport`
--

DROP TABLE IF EXISTS `virtualnetworkport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetworkport` (
  `netPortId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `resourceId` int(10) unsigned DEFAULT NULL,
  `portType` varchar(100) DEFAULT NULL,
  `attachedResourceId` int(10) unsigned DEFAULT NULL,
  `segmentId` int(10) unsigned DEFAULT NULL,
  `bandwidth` decimal(10,0) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `virtualNetworkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`netPortId`),
  KEY `virtualNetworkId` (`virtualNetworkId`),
  CONSTRAINT `virtualnetworkport_ibfk_1` FOREIGN KEY (`virtualNetworkId`) REFERENCES `virtualnetwork` (`virtualnetworkid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetworkport`
--

LOCK TABLES `virtualnetworkport` WRITE;
/*!40000 ALTER TABLE `virtualnetworkport` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualnetworkport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetworkportdata`
--

DROP TABLE IF EXISTS `virtualnetworkportdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetworkportdata` (
  `portType` varchar(100) DEFAULT NULL,
  `networkId` int(10) unsigned DEFAULT NULL,
  `segmentId` int(10) unsigned DEFAULT NULL,
  `bandwidth` decimal(10,0) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `netServId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`netServId`),
  CONSTRAINT `virtualnetworkportdata_ibfk_1` FOREIGN KEY (`netServId`) REFERENCES `networkservicerequestdata` (`netservid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetworkportdata`
--

LOCK TABLES `virtualnetworkportdata` WRITE;
/*!40000 ALTER TABLE `virtualnetworkportdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualnetworkportdata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-16 10:22:38
CREATE DATABASE  IF NOT EXISTS `mtpappddb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mtpappddb`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mtpappddb
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addressdata`
--

DROP TABLE IF EXISTS `addressdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `addressdata` (
  `addressdataId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `addressType` enum('MAC','IPV4','IPV6') NOT NULL,
  `l2AddressData` varchar(100) DEFAULT NULL,
  `l3AddressData` varchar(100) DEFAULT NULL,
  `cpdId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`addressdataId`),
  KEY `cpdId` (`cpdId`),
  CONSTRAINT `addressdata_ibfk_1` FOREIGN KEY (`cpdId`) REFERENCES `appextcpd` (`cpdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addressdata`
--

LOCK TABLES `addressdata` WRITE;
/*!40000 ALTER TABLE `addressdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `addressdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appd`
--

DROP TABLE IF EXISTS `appd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appd` (
  `appDId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appName` varchar(100) DEFAULT NULL,
  `appProvider` varchar(100) DEFAULT NULL,
  `appSoftVersion` varchar(100) DEFAULT NULL,
  `appDVersion` varchar(100) DEFAULT NULL,
  `mecVersion` varchar(100) DEFAULT NULL,
  `appInfoName` varchar(100) DEFAULT NULL,
  `appDescription` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`appDId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appd`
--

LOCK TABLES `appd` WRITE;
/*!40000 ALTER TABLE `appd` DISABLE KEYS */;
INSERT INTO `appd` VALUES (1,'robot-control-app','pfrag','1.0','1.0','1.0','robot-control-app','app for controlling mobile robot'),(2,'edgecache','pfrag','1.0','1.0','1.0','edgecache','edge cache application');
/*!40000 ALTER TABLE `appd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appdnsrule`
--

DROP TABLE IF EXISTS `appdnsrule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appdnsrule` (
  `dnsRuleId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `domainName` varchar(100) DEFAULT NULL,
  `ipAddressType` enum('IP_V6','IP_V4') NOT NULL,
  `ipAddress` varchar(100) DEFAULT NULL,
  `ttl` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`dnsRuleId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `appdnsrule_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appdnsrule`
--

LOCK TABLES `appdnsrule` WRITE;
/*!40000 ALTER TABLE `appdnsrule` DISABLE KEYS */;
INSERT INTO `appdnsrule` VALUES (61,'robot.control','IP_V4','0.0.0.0','0',1),(62,'sfr.fr','IP_V4','0.0.0.0','0',1),(71,'edge.cache.cdn','IP_V4','0.0.0.0','0',2);
/*!40000 ALTER TABLE `appdnsrule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appextcpd`
--

DROP TABLE IF EXISTS `appextcpd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appextcpd` (
  `cpdId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `layerProtocol` enum('Ethernet','MPLS','ODU2','IPV4','IPV6','Pseudo-Wire') NOT NULL,
  `cpRole` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`cpdId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `appextcpd_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appextcpd`
--

LOCK TABLES `appextcpd` WRITE;
/*!40000 ALTER TABLE `appextcpd` DISABLE KEYS */;
INSERT INTO `appextcpd` VALUES (92,'Ethernet','ROOT','MEC CPD',2),(93,'Ethernet','ROOT','CP retrieving origin server data',2),(94,'Ethernet','ROOT','CP for delivering video to clients',2),(95,'Ethernet','ROOT','CP management network',2);
/*!40000 ALTER TABLE `appextcpd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `applatency`
--

DROP TABLE IF EXISTS `applatency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `applatency` (
  `timeUnit` varchar(100) DEFAULT NULL,
  `latency` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `applatency_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applatency`
--

LOCK TABLES `applatency` WRITE;
/*!40000 ALTER TABLE `applatency` DISABLE KEYS */;
INSERT INTO `applatency` VALUES ('ms','100',1),('ms','100',2);
/*!40000 ALTER TABLE `applatency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appserviceoptional`
--

DROP TABLE IF EXISTS `appserviceoptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appserviceoptional` (
  `serviceRequiredId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serName` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `requestedPermissions` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`serviceRequiredId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `appserviceoptional_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appserviceoptional`
--

LOCK TABLES `appserviceoptional` WRITE;
/*!40000 ALTER TABLE `appserviceoptional` DISABLE KEYS */;
INSERT INTO `appserviceoptional` VALUES (30,'','','',1);
/*!40000 ALTER TABLE `appserviceoptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appservicerequired`
--

DROP TABLE IF EXISTS `appservicerequired`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appservicerequired` (
  `serviceRequiredId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serName` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `requestedPermissions` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`serviceRequiredId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `appservicerequired_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appservicerequired`
--

LOCK TABLES `appservicerequired` WRITE;
/*!40000 ALTER TABLE `appservicerequired` DISABLE KEYS */;
INSERT INTO `appservicerequired` VALUES (10,'RNIS','1.0','',1);
/*!40000 ALTER TABLE `appservicerequired` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apptrafficrule`
--

DROP TABLE IF EXISTS `apptrafficrule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `apptrafficrule` (
  `trafficRuleId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `filterType` enum('FLOW','PACKET') NOT NULL,
  `priority` varchar(100) DEFAULT NULL,
  `action` enum('DROP','FORWARD','DECAPSULATED','FORWARD_AS_IS','PASSTHROUGH','DUPLICATED_DECAPSULATED','DUPLICATE_AS_IS') NOT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`trafficRuleId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `apptrafficrule_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apptrafficrule`
--

LOCK TABLES `apptrafficrule` WRITE;
/*!40000 ALTER TABLE `apptrafficrule` DISABLE KEYS */;
INSERT INTO `apptrafficrule` VALUES (15,'FLOW','0','FORWARD',1),(22,'FLOW','0','FORWARD',2);
/*!40000 ALTER TABLE `apptrafficrule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changeappinstancestateopconfig`
--

DROP TABLE IF EXISTS `changeappinstancestateopconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `changeappinstancestateopconfig` (
  `minGracefulTerminationTimeout` varchar(100) DEFAULT NULL,
  `macRecommendedGracefulTerminationTimeout` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `changeappinstancestateopconfig_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changeappinstancestateopconfig`
--

LOCK TABLES `changeappinstancestateopconfig` WRITE;
/*!40000 ALTER TABLE `changeappinstancestateopconfig` DISABLE KEYS */;
INSERT INTO `changeappinstancestateopconfig` VALUES ('0','0',1),('0','0',2);
/*!40000 ALTER TABLE `changeappinstancestateopconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dstinterface`
--

DROP TABLE IF EXISTS `dstinterface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dstinterface` (
  `dstInterfaceId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `interfaceType` enum('TUNNEL','MAC','IP') NOT NULL,
  `srcMACAddress` varchar(100) DEFAULT NULL,
  `dstMACAddress` varchar(100) DEFAULT NULL,
  `dstIPAddress` varchar(100) DEFAULT NULL,
  `trafficRuleId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`dstInterfaceId`),
  KEY `trafficRuleId` (`trafficRuleId`),
  CONSTRAINT `dstinterface_ibfk_1` FOREIGN KEY (`trafficRuleId`) REFERENCES `apptrafficrule` (`trafficruleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dstinterface`
--

LOCK TABLES `dstinterface` WRITE;
/*!40000 ALTER TABLE `dstinterface` DISABLE KEYS */;
/*!40000 ALTER TABLE `dstinterface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requestadditionalcapabilities`
--

DROP TABLE IF EXISTS `requestadditionalcapabilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `requestadditionalcapabilities` (
  `capabilitiesId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `requestedAdditionalCapabilityName` varchar(100) DEFAULT NULL,
  `supportMandatory` tinyint(1) NOT NULL,
  `minRequestedAdditionalCapabilityVersion` varchar(100) DEFAULT NULL,
  `preferredRequestedAdditionalCapabilityVersion` varchar(100) DEFAULT NULL,
  `targetPerformanceParameters` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`capabilitiesId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `requestadditionalcapabilities_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `virtualcomputedescriptor` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requestadditionalcapabilities`
--

LOCK TABLES `requestadditionalcapabilities` WRITE;
/*!40000 ALTER TABLE `requestadditionalcapabilities` DISABLE KEYS */;
/*!40000 ALTER TABLE `requestadditionalcapabilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sercategory`
--

DROP TABLE IF EXISTS `sercategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sercategory` (
  `href` varchar(100) DEFAULT NULL,
  `id` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `serviceRequiredId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`serviceRequiredId`),
  CONSTRAINT `sercategory_ibfk_1` FOREIGN KEY (`serviceRequiredId`) REFERENCES `appservicerequired` (`servicerequiredid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sercategory`
--

LOCK TABLES `sercategory` WRITE;
/*!40000 ALTER TABLE `sercategory` DISABLE KEYS */;
INSERT INTO `sercategory` VALUES ('','','','',10);
/*!40000 ALTER TABLE `sercategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sercategoryoptional`
--

DROP TABLE IF EXISTS `sercategoryoptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sercategoryoptional` (
  `href` varchar(100) DEFAULT NULL,
  `id` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `serviceRequiredId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`serviceRequiredId`),
  CONSTRAINT `sercategoryoptional_ibfk_1` FOREIGN KEY (`serviceRequiredId`) REFERENCES `appserviceoptional` (`servicerequiredid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sercategoryoptional`
--

LOCK TABLES `sercategoryoptional` WRITE;
/*!40000 ALTER TABLE `sercategoryoptional` DISABLE KEYS */;
INSERT INTO `sercategoryoptional` VALUES ('','','','',30);
/*!40000 ALTER TABLE `sercategoryoptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sertransport`
--

DROP TABLE IF EXISTS `sertransport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sertransport` (
  `transportType` enum('REST_HTTP','MB_TOPIC_BASED','MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `security` varchar(100) DEFAULT NULL,
  `serTransportDependenciesId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`serTransportDependenciesId`),
  CONSTRAINT `sertransport_ibfk_1` FOREIGN KEY (`serTransportDependenciesId`) REFERENCES `sertransportdependencies` (`sertransportdependenciesid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sertransport`
--

LOCK TABLES `sertransport` WRITE;
/*!40000 ALTER TABLE `sertransport` DISABLE KEYS */;
INSERT INTO `sertransport` VALUES ('REST_HTTP','','','',20);
/*!40000 ALTER TABLE `sertransport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sertransportdependencies`
--

DROP TABLE IF EXISTS `sertransportdependencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sertransportdependencies` (
  `serTransportDependenciesId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serializers` enum('JSON','XML','PROTOBUF3') NOT NULL,
  `labels` varchar(100) DEFAULT NULL,
  `serviceRequiredId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`serTransportDependenciesId`),
  KEY `serviceRequiredId` (`serviceRequiredId`),
  CONSTRAINT `sertransportdependencies_ibfk_1` FOREIGN KEY (`serviceRequiredId`) REFERENCES `appservicerequired` (`servicerequiredid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sertransportdependencies`
--

LOCK TABLES `sertransportdependencies` WRITE;
/*!40000 ALTER TABLE `sertransportdependencies` DISABLE KEYS */;
INSERT INTO `sertransportdependencies` VALUES (20,'JSON','',10);
/*!40000 ALTER TABLE `sertransportdependencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sertransportdependenciesoptional`
--

DROP TABLE IF EXISTS `sertransportdependenciesoptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sertransportdependenciesoptional` (
  `serTransportDependenciesId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serializers` enum('JSON','XML','PROTOBUF3') NOT NULL,
  `labels` varchar(100) DEFAULT NULL,
  `serviceRequiredId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`serTransportDependenciesId`),
  KEY `serviceRequiredId` (`serviceRequiredId`),
  CONSTRAINT `sertransportdependenciesoptional_ibfk_1` FOREIGN KEY (`serviceRequiredId`) REFERENCES `appserviceoptional` (`servicerequiredid`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sertransportdependenciesoptional`
--

LOCK TABLES `sertransportdependenciesoptional` WRITE;
/*!40000 ALTER TABLE `sertransportdependenciesoptional` DISABLE KEYS */;
INSERT INTO `sertransportdependenciesoptional` VALUES (31,'JSON','',30);
/*!40000 ALTER TABLE `sertransportdependenciesoptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swimagedescriptor`
--

DROP TABLE IF EXISTS `swimagedescriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `swimagedescriptor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `checksum_` varchar(100) DEFAULT NULL,
  `containerFormat` varchar(100) DEFAULT NULL,
  `diskFormat` varchar(100) DEFAULT NULL,
  `minDisk` varchar(100) DEFAULT NULL,
  `minRam` varchar(100) DEFAULT NULL,
  `size_` varchar(100) DEFAULT NULL,
  `swImage` varchar(100) DEFAULT NULL,
  `operatingSystem` varchar(100) DEFAULT NULL,
  `supportedVirtualizationEnvironment` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `swimagedescriptor_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swimagedescriptor`
--

LOCK TABLES `swimagedescriptor` WRITE;
/*!40000 ALTER TABLE `swimagedescriptor` DISABLE KEYS */;
INSERT INTO `swimagedescriptor` VALUES (10,'robotctrl','1.0','','lxd_unified','rootfs','1','512','512','http://127.0.0.1/lxdimages/robotctrl.tar.gz','','lxd',1),(12,'robotctrl','1.0','','lxd_unified','rootfs','1','512','512','http://path-to-edge-cache-image.tar.gz','','lxd',2);
/*!40000 ALTER TABLE `swimagedescriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terminateappinstanceopconfig`
--

DROP TABLE IF EXISTS `terminateappinstanceopconfig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `terminateappinstanceopconfig` (
  `minGracefulTerminationTimeout` varchar(100) DEFAULT NULL,
  `macRecommendedGracefulTerminationTimeout` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `terminateappinstanceopconfig_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terminateappinstanceopconfig`
--

LOCK TABLES `terminateappinstanceopconfig` WRITE;
/*!40000 ALTER TABLE `terminateappinstanceopconfig` DISABLE KEYS */;
INSERT INTO `terminateappinstanceopconfig` VALUES ('0','0',1),('0','0',2);
/*!40000 ALTER TABLE `terminateappinstanceopconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trafficfilter`
--

DROP TABLE IF EXISTS `trafficfilter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `trafficfilter` (
  `trafficFilterId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `srcAddress` varchar(100) DEFAULT NULL,
  `dstAddress` varchar(100) DEFAULT NULL,
  `srcPort` varchar(100) DEFAULT NULL,
  `dstPort` varchar(100) DEFAULT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `token` varchar(100) DEFAULT NULL,
  `srcTunnelAddress` varchar(100) DEFAULT NULL,
  `dstTunnelAddress` varchar(100) DEFAULT NULL,
  `srcTunnelPort` varchar(100) DEFAULT NULL,
  `dstTunnelPort` varchar(100) DEFAULT NULL,
  `qci` varchar(100) DEFAULT NULL,
  `dscp` varchar(100) DEFAULT NULL,
  `tc` varchar(100) DEFAULT NULL,
  `trafficRuleId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`trafficFilterId`),
  KEY `trafficRuleId` (`trafficRuleId`),
  CONSTRAINT `trafficfilter_ibfk_1` FOREIGN KEY (`trafficRuleId`) REFERENCES `apptrafficrule` (`trafficruleid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trafficfilter`
--

LOCK TABLES `trafficfilter` WRITE;
/*!40000 ALTER TABLE `trafficfilter` DISABLE KEYS */;
INSERT INTO `trafficfilter` VALUES (16,'208930100001114','172.16.0.100','','9990','tcp','','','','','','0','0','0',15),(32,'208930100001114','172.16.0.100','','9990','tcp','','','','','','0','0','0',22);
/*!40000 ALTER TABLE `trafficfilter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transport`
--

DROP TABLE IF EXISTS `transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transport` (
  `transportType` enum('REST_HTTP','MB_TOPIC_BASED','MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `security` varchar(100) DEFAULT NULL,
  `transportDependenciesId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`transportDependenciesId`),
  CONSTRAINT `transport_ibfk_1` FOREIGN KEY (`transportDependenciesId`) REFERENCES `transportdependencies` (`transportdependenciesid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transport`
--

LOCK TABLES `transport` WRITE;
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
INSERT INTO `transport` VALUES ('REST_HTTP','','','',40);
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transportdependencies`
--

DROP TABLE IF EXISTS `transportdependencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transportdependencies` (
  `transportdependenciesId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `serializers` enum('JSON','XML','PROTOBUF3') NOT NULL,
  `labels` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`transportdependenciesId`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `transportdependencies_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transportdependencies`
--

LOCK TABLES `transportdependencies` WRITE;
/*!40000 ALTER TABLE `transportdependencies` DISABLE KEYS */;
INSERT INTO `transportdependencies` VALUES (40,'JSON','',1);
/*!40000 ALTER TABLE `transportdependencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transportdescriptoroptional`
--

DROP TABLE IF EXISTS `transportdescriptoroptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transportdescriptoroptional` (
  `transportType` enum('REST_HTTP','MB_TOPIC_BASED','MB_ROUTING','MB_PUBSUB','RPC','RPC_STREAMING','WEBSOCKET') NOT NULL,
  `protocol` varchar(100) DEFAULT NULL,
  `version` varchar(100) DEFAULT NULL,
  `security` varchar(100) DEFAULT NULL,
  `serTransportDependenciesId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`serTransportDependenciesId`),
  CONSTRAINT `transportdescriptoroptional_ibfk_1` FOREIGN KEY (`serTransportDependenciesId`) REFERENCES `sertransportdependenciesoptional` (`sertransportdependenciesid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transportdescriptoroptional`
--

LOCK TABLES `transportdescriptoroptional` WRITE;
/*!40000 ALTER TABLE `transportdescriptoroptional` DISABLE KEYS */;
INSERT INTO `transportdescriptoroptional` VALUES ('REST_HTTP','','','',31);
/*!40000 ALTER TABLE `transportdescriptoroptional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tunnelinfo`
--

DROP TABLE IF EXISTS `tunnelinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tunnelinfo` (
  `tunnelType` enum('GTP-U','GRE') NOT NULL,
  `tunnelDstAddress` varchar(100) DEFAULT NULL,
  `tunnelSrcAddress` varchar(100) DEFAULT NULL,
  `tunnelSpecificData` varchar(100) DEFAULT NULL,
  `dstInterfaceId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`dstInterfaceId`),
  CONSTRAINT `tunnelinfo_ibfk_1` FOREIGN KEY (`dstInterfaceId`) REFERENCES `dstinterface` (`dstinterfaceid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tunnelinfo`
--

LOCK TABLES `tunnelinfo` WRITE;
/*!40000 ALTER TABLE `tunnelinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `tunnelinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcomputedescriptor`
--

DROP TABLE IF EXISTS `virtualcomputedescriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcomputedescriptor` (
  `virtualComputeDescId` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `virtualcomputedescriptor_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcomputedescriptor`
--

LOCK TABLES `virtualcomputedescriptor` WRITE;
/*!40000 ALTER TABLE `virtualcomputedescriptor` DISABLE KEYS */;
INSERT INTO `virtualcomputedescriptor` VALUES ('1c0897de-bc1a-4730-9d99-364fa83d7643',1),('1c0897de-bc1a-4730-9d99-364fa83d7643',2);
/*!40000 ALTER TABLE `virtualcomputedescriptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcpu`
--

DROP TABLE IF EXISTS `virtualcpu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcpu` (
  `cpuArchitecture` varchar(100) DEFAULT NULL,
  `numVirtualCpu` varchar(100) DEFAULT NULL,
  `virtualCpuClock` varchar(100) DEFAULT NULL,
  `virtualCpuOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `virtualcpu_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `virtualcomputedescriptor` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcpu`
--

LOCK TABLES `virtualcpu` WRITE;
/*!40000 ALTER TABLE `virtualcpu` DISABLE KEYS */;
INSERT INTO `virtualcpu` VALUES ('x86_64','1','0','string',1),('x86_64','1','0','string',2);
/*!40000 ALTER TABLE `virtualcpu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcpupinning`
--

DROP TABLE IF EXISTS `virtualcpupinning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcpupinning` (
  `cpuPinningPolicy` enum('static','dynamic') DEFAULT NULL,
  `cpuPinningMap` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `virtualcpupinning_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `virtualcpu` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcpupinning`
--

LOCK TABLES `virtualcpupinning` WRITE;
/*!40000 ALTER TABLE `virtualcpupinning` DISABLE KEYS */;
INSERT INTO `virtualcpupinning` VALUES ('static','',1),('static','',2);
/*!40000 ALTER TABLE `virtualcpupinning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualmemory`
--

DROP TABLE IF EXISTS `virtualmemory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualmemory` (
  `virtualMemSize` varchar(100) DEFAULT NULL,
  `numaEnabled` tinyint(1) DEFAULT NULL,
  `virtualMemOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`appDId`),
  CONSTRAINT `virtualmemory_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `virtualcomputedescriptor` (`appdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualmemory`
--

LOCK TABLES `virtualmemory` WRITE;
/*!40000 ALTER TABLE `virtualmemory` DISABLE KEYS */;
INSERT INTO `virtualmemory` VALUES ('1024',0,'none',1),('1024',0,'none',2);
/*!40000 ALTER TABLE `virtualmemory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetworkinterfacerequirements`
--

DROP TABLE IF EXISTS `virtualnetworkinterfacerequirements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetworkinterfacerequirements` (
  `virtualNetworkInterfaceRequirementsId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `supportMandatory` tinyint(1) NOT NULL,
  `requirement` varchar(100) DEFAULT NULL,
  `cpdId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`virtualNetworkInterfaceRequirementsId`),
  KEY `cpdId` (`cpdId`),
  CONSTRAINT `virtualnetworkinterfacerequirements_ibfk_1` FOREIGN KEY (`cpdId`) REFERENCES `appextcpd` (`cpdid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetworkinterfacerequirements`
--

LOCK TABLES `virtualnetworkinterfacerequirements` WRITE;
/*!40000 ALTER TABLE `virtualnetworkinterfacerequirements` DISABLE KEYS */;
/*!40000 ALTER TABLE `virtualnetworkinterfacerequirements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualstoragedescriptor`
--

DROP TABLE IF EXISTS `virtualstoragedescriptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualstoragedescriptor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `typeOfStorage` varchar(100) DEFAULT NULL,
  `sizeOfStorage` varchar(100) DEFAULT NULL,
  `rdmaEnabled` tinyint(1) DEFAULT NULL,
  `swImageDesc` varchar(100) DEFAULT NULL,
  `appDId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `appDId` (`appDId`),
  CONSTRAINT `virtualstoragedescriptor_ibfk_1` FOREIGN KEY (`appDId`) REFERENCES `appd` (`appdid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualstoragedescriptor`
--

LOCK TABLES `virtualstoragedescriptor` WRITE;
/*!40000 ALTER TABLE `virtualstoragedescriptor` DISABLE KEYS */;
INSERT INTO `virtualstoragedescriptor` VALUES (11,'','1',1,'',1),(12,'','1',1,'',2);
/*!40000 ALTER TABLE `virtualstoragedescriptor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-16 10:22:39
CREATE DATABASE  IF NOT EXISTS `mtpfeddb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mtpfeddb`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mtpfeddb
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `fed_interdomainlink`
--

DROP TABLE IF EXISTS `fed_interdomainlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fed_interdomainlink` (
  `interDomainLinkId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `srcGwId` varchar(100) DEFAULT NULL,
  `dstGwId` varchar(100) DEFAULT NULL,
  `srcGwIp` varchar(100) DEFAULT NULL,
  `dstGwIp` varchar(100) DEFAULT NULL,
  `abstrSrcNfviPopId` int(10) unsigned DEFAULT NULL,
  `abstrDestNfviPopId` int(10) unsigned DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `cost` varchar(100) DEFAULT NULL,
  `totalBandwidth` varchar(100) DEFAULT NULL,
  `reservedBandwidth` varchar(100) DEFAULT NULL,
  `availableBandwidth` varchar(100) DEFAULT NULL,
  `allocatedBandwidth` varchar(100) DEFAULT NULL,
  `networkLayer` varchar(100) DEFAULT NULL,
  `interNfviPopNetworkType` varchar(100) DEFAULT NULL,
  `interNfviPopNetworkTopology` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interDomainLinkId`)
) ENGINE=InnoDB AUTO_INCREMENT=1113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fed_interdomainlink`
--

LOCK TABLES `fed_interdomainlink` WRITE;
/*!40000 ALTER TABLE `fed_interdomainlink` DISABLE KEYS */;
INSERT INTO `fed_interdomainlink` VALUES (1111,'192.168.10.10','192.168.1.20','91','92',91,31,'10','1','1000','','1000','0','','',''),(1112,'192.168.1.20','192.168.10.10','92','91',31,91,'10','1','1000','','1000','0','','','');
/*!40000 ALTER TABLE `fed_interdomainlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fed_logicallink`
--

DROP TABLE IF EXISTS `fed_logicallink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fed_logicallink` (
  `logicalLinkId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `srcRouterId` varchar(100) DEFAULT NULL,
  `dstRouterId` varchar(100) DEFAULT NULL,
  `srcRouterIp` varchar(100) DEFAULT NULL,
  `dstRouterIp` varchar(100) DEFAULT NULL,
  `abstrSrcNfviPopId` int(10) unsigned DEFAULT NULL,
  `abstrDestNfviPopId` int(10) unsigned DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `cost` varchar(100) DEFAULT NULL,
  `totalBandwidth` varchar(100) DEFAULT NULL,
  `reservedBandwidth` varchar(100) DEFAULT NULL,
  `availableBandwidth` varchar(100) DEFAULT NULL,
  `allocatedBandwidth` varchar(100) DEFAULT NULL,
  `networkLayer` varchar(100) DEFAULT NULL,
  `interNfviPopNetworkType` varchar(100) DEFAULT NULL,
  `interNfviPopNetworkTopology` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`logicalLinkId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fed_logicallink`
--

LOCK TABLES `fed_logicallink` WRITE;
/*!40000 ALTER TABLE `fed_logicallink` DISABLE KEYS */;
INSERT INTO `fed_logicallink` VALUES (1,'192.168.10.10','192.168.3.30','91','13',1,11,'70.0',NULL,'1000.0','0.0','1000.0','0.0',NULL,NULL,NULL),(2,'192.168.10.10','192.168.3.30','91','13',1,11,'95.0',NULL,'1000.0','0.0','1000.0','0.0',NULL,NULL,NULL),(3,'192.168.10.10','192.168.4.40','91','14',1,21,'20.0',NULL,'1000.0','0.0','1000.0','0.0',NULL,NULL,NULL),(4,'192.168.3.30','192.168.10.10','13','91',11,1,'70.0',NULL,'1000.0','0.0','1000.0','0.0',NULL,NULL,NULL),(5,'192.168.3.30','192.168.10.10','13','91',11,1,'95.0',NULL,'1000.0','0.0','1000.0','0.0',NULL,NULL,NULL),(6,'192.168.4.40','192.168.10.10','14','91',21,1,'20.0',NULL,'1000.0','0.0','1000.0','0.0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `fed_logicallink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fed_logicalpath`
--

DROP TABLE IF EXISTS `fed_logicalpath`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fed_logicalpath` (
  `logicalPathId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `abstrSrcNfviPopId` int(10) unsigned DEFAULT NULL,
  `abstrDestNfviPopId` int(10) unsigned DEFAULT NULL,
  `srcRouterId` varchar(100) DEFAULT NULL,
  `dstRouterId` varchar(100) DEFAULT NULL,
  `srcRouterIp` varchar(100) DEFAULT NULL,
  `dstRouterIp` varchar(100) DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `availableBandwidth` varchar(100) DEFAULT NULL,
  `reservedBandwidth` varchar(100) DEFAULT NULL,
  `totalBandwidth` varchar(100) DEFAULT NULL,
  `allocatedBandwidth` varchar(100) DEFAULT NULL,
  `networkLayer` varchar(100) DEFAULT NULL,
  `interNfviPopNetworkType` varchar(100) DEFAULT NULL,
  `interNfviPopNetworkTopology` varchar(100) DEFAULT NULL,
  `logicalLinkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`logicalPathId`),
  KEY `logicalLinkId` (`logicalLinkId`),
  CONSTRAINT `fed_logicalpath_ibfk_1` FOREIGN KEY (`logicalLinkId`) REFERENCES `fed_logicallink` (`logicallinkid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fed_logicalpath`
--

LOCK TABLES `fed_logicalpath` WRITE;
/*!40000 ALTER TABLE `fed_logicalpath` DISABLE KEYS */;
INSERT INTO `fed_logicalpath` VALUES (1,1,11,'192.168.10.10','192.168.3.30','91','13','70.0','1000.0','0.0','1000.0','0.0',NULL,NULL,NULL,1),(2,1,11,'192.168.10.10','192.168.3.30','91','13','95.0','1000.0','0.0','1000.0','0.0',NULL,NULL,NULL,2),(3,1,21,'192.168.10.10','192.168.4.40','91','14','20.0','1000.0','0.0','1000.0','0.0',NULL,NULL,NULL,3),(4,11,1,'192.168.3.30','192.168.10.10','13','91','70.0','1000.0','0.0','1000.0','0.0',NULL,NULL,NULL,4),(5,11,1,'192.168.3.30','192.168.10.10','13','91','95.0','1000.0','0.0','1000.0','0.0',NULL,NULL,NULL,5),(6,21,1,'192.168.4.40','192.168.10.10','14','91','20.0','1000.0','0.0','1000.0','0.0',NULL,NULL,NULL,6);
/*!40000 ALTER TABLE `fed_logicalpath` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fed_logicalpath_fed_interdomainlink`
--

DROP TABLE IF EXISTS `fed_logicalpath_fed_interdomainlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fed_logicalpath_fed_interdomainlink` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `logicalPathId` int(10) unsigned DEFAULT NULL,
  `interDomainLinkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logicalPathId` (`logicalPathId`),
  KEY `interDomainLinkId` (`interDomainLinkId`),
  CONSTRAINT `fed_logicalpath_fed_interdomainlink_ibfk_1` FOREIGN KEY (`logicalPathId`) REFERENCES `fed_logicalpath` (`logicalpathid`),
  CONSTRAINT `fed_logicalpath_fed_interdomainlink_ibfk_2` FOREIGN KEY (`interDomainLinkId`) REFERENCES `fed_interdomainlink` (`interdomainlinkid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fed_logicalpath_fed_interdomainlink`
--

LOCK TABLES `fed_logicalpath_fed_interdomainlink` WRITE;
/*!40000 ALTER TABLE `fed_logicalpath_fed_interdomainlink` DISABLE KEYS */;
INSERT INTO `fed_logicalpath_fed_interdomainlink` VALUES (1,1,1111),(2,2,1111),(3,3,1111),(4,4,1112),(5,5,1112),(6,6,1112);
/*!40000 ALTER TABLE `fed_logicalpath_fed_interdomainlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fed_logicalpath_interdomainlink`
--

DROP TABLE IF EXISTS `fed_logicalpath_interdomainlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fed_logicalpath_interdomainlink` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `logicalPathId` int(10) unsigned DEFAULT NULL,
  `interDomainLinkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logicalPathId` (`logicalPathId`),
  KEY `interDomainLinkId` (`interDomainLinkId`),
  CONSTRAINT `fed_logicalpath_interdomainlink_ibfk_1` FOREIGN KEY (`logicalPathId`) REFERENCES `fed_logicalpath` (`logicalpathid`),
  CONSTRAINT `fed_logicalpath_interdomainlink_ibfk_2` FOREIGN KEY (`interDomainLinkId`) REFERENCES `mtpdomdb`.`interdomainlink` (`interdomainlinkid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fed_logicalpath_interdomainlink`
--

LOCK TABLES `fed_logicalpath_interdomainlink` WRITE;
/*!40000 ALTER TABLE `fed_logicalpath_interdomainlink` DISABLE KEYS */;
INSERT INTO `fed_logicalpath_interdomainlink` VALUES (1,1,2),(2,2,2),(3,3,4),(4,4,1),(5,5,1),(6,6,3);
/*!40000 ALTER TABLE `fed_logicalpath_interdomainlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fed_logicalpath_networkresources`
--

DROP TABLE IF EXISTS `fed_logicalpath_networkresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fed_logicalpath_networkresources` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `logicalPathId` int(10) unsigned DEFAULT NULL,
  `networkResId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logicalPathId` (`logicalPathId`),
  KEY `networkResId` (`networkResId`),
  CONSTRAINT `fed_logicalpath_networkresources_ibfk_1` FOREIGN KEY (`logicalPathId`) REFERENCES `fed_logicalpath` (`logicalpathid`),
  CONSTRAINT `fed_logicalpath_networkresources_ibfk_2` FOREIGN KEY (`networkResId`) REFERENCES `mtpdomdb`.`networkresources` (`networkresid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fed_logicalpath_networkresources`
--

LOCK TABLES `fed_logicalpath_networkresources` WRITE;
/*!40000 ALTER TABLE `fed_logicalpath_networkresources` DISABLE KEYS */;
INSERT INTO `fed_logicalpath_networkresources` VALUES (1,1,103),(2,2,104),(3,4,101),(4,5,102);
/*!40000 ALTER TABLE `fed_logicalpath_networkresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nfvipop`
--

DROP TABLE IF EXISTS `nfvipop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `nfvipop` (
  `nfviPopId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `federatedVimId` int(10) unsigned DEFAULT NULL,
  `geographicalLocationInfo` varchar(100) DEFAULT NULL,
  `networkConnectivityEndpoint` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`nfviPopId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nfvipop`
--

LOCK TABLES `nfvipop` WRITE;
/*!40000 ALTER TABLE `nfvipop` DISABLE KEYS */;
INSERT INTO `nfvipop` VALUES (1,1,NULL,'192.168.10.10;192.168.10.11');
/*!40000 ALTER TABLE `nfvipop` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-16 10:22:39
CREATE DATABASE  IF NOT EXISTS `mtpabstrdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mtpabstrdb`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mtpabstrdb
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `abstrcpuresources`
--

DROP TABLE IF EXISTS `abstrcpuresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abstrcpuresources` (
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `abstrResourceZoneId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`abstrResourceZoneId`),
  CONSTRAINT `abstrcpuresources_ibfk_1` FOREIGN KEY (`abstrResourceZoneId`) REFERENCES `abstrzoneid` (`abstrresourcezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstrcpuresources`
--

LOCK TABLES `abstrcpuresources` WRITE;
/*!40000 ALTER TABLE `abstrcpuresources` DISABLE KEYS */;
INSERT INTO `abstrcpuresources` VALUES ('32','0','32','0',2),('32','0','32','0',3);
/*!40000 ALTER TABLE `abstrcpuresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `abstrdomain`
--

DROP TABLE IF EXISTS `abstrdomain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abstrdomain` (
  `abstrDomId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tenantName` varchar(100) DEFAULT NULL,
  `tenantId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`abstrDomId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstrdomain`
--

LOCK TABLES `abstrdomain` WRITE;
/*!40000 ALTER TABLE `abstrdomain` DISABLE KEYS */;
INSERT INTO `abstrdomain` VALUES (1,'SO_abstractview',10);
/*!40000 ALTER TABLE `abstrdomain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `abstrmemoryresources`
--

DROP TABLE IF EXISTS `abstrmemoryresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abstrmemoryresources` (
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `abstrResourceZoneId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`abstrResourceZoneId`),
  CONSTRAINT `abstrmemoryresources_ibfk_1` FOREIGN KEY (`abstrResourceZoneId`) REFERENCES `abstrzoneid` (`abstrresourcezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstrmemoryresources`
--

LOCK TABLES `abstrmemoryresources` WRITE;
/*!40000 ALTER TABLE `abstrmemoryresources` DISABLE KEYS */;
INSERT INTO `abstrmemoryresources` VALUES ('16000','0','16000','0',2),('16000','0','16000','0',3);
/*!40000 ALTER TABLE `abstrmemoryresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `abstrnfvipop`
--

DROP TABLE IF EXISTS `abstrnfvipop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abstrnfvipop` (
  `abstrNfviPopId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vimId` int(10) unsigned DEFAULT NULL,
  `geographicalLocationInfo` varchar(100) DEFAULT NULL,
  `networkConnectivityEndpoint` varchar(100) DEFAULT NULL,
  `abstrDomId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`abstrNfviPopId`),
  KEY `abstrDomId` (`abstrDomId`),
  CONSTRAINT `abstrnfvipop_ibfk_1` FOREIGN KEY (`abstrDomId`) REFERENCES `abstrdomain` (`abstrdomid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstrnfvipop`
--

LOCK TABLES `abstrnfvipop` WRITE;
/*!40000 ALTER TABLE `abstrnfvipop` DISABLE KEYS */;
INSERT INTO `abstrnfvipop` VALUES (11,1,'Pisa','192.168.3.30',1),(21,2,'Pisa','192.168.4.40',1);
/*!40000 ALTER TABLE `abstrnfvipop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `abstrstorageresources`
--

DROP TABLE IF EXISTS `abstrstorageresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abstrstorageresources` (
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `abstrResourceZoneId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`abstrResourceZoneId`),
  CONSTRAINT `abstrstorageresources_ibfk_1` FOREIGN KEY (`abstrResourceZoneId`) REFERENCES `abstrzoneid` (`abstrresourcezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstrstorageresources`
--

LOCK TABLES `abstrstorageresources` WRITE;
/*!40000 ALTER TABLE `abstrstorageresources` DISABLE KEYS */;
/*!40000 ALTER TABLE `abstrstorageresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `abstrzoneid`
--

DROP TABLE IF EXISTS `abstrzoneid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `abstrzoneid` (
  `abstrResourceZoneId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `abstrZoneId` varchar(100) DEFAULT NULL,
  `zoneName` varchar(100) DEFAULT NULL,
  `zoneState` varchar(100) DEFAULT NULL,
  `zoneProperty` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `abstrNfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`abstrResourceZoneId`),
  KEY `abstrNfviPopId` (`abstrNfviPopId`),
  CONSTRAINT `abstrzoneid_ibfk_1` FOREIGN KEY (`abstrNfviPopId`) REFERENCES `abstrnfvipop` (`abstrnfvipopid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `abstrzoneid`
--

LOCK TABLES `abstrzoneid` WRITE;
/*!40000 ALTER TABLE `abstrzoneid` DISABLE KEYS */;
INSERT INTO `abstrzoneid` VALUES (2,'2','Dummy VIM1','enable','power',NULL,11),(3,'3','Dummy VIM2','enable','power',NULL,21);
/*!40000 ALTER TABLE `abstrzoneid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logicallink`
--

DROP TABLE IF EXISTS `logicallink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logicallink` (
  `logicalLinkId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `abstrSrcNfviPopId` int(10) unsigned DEFAULT NULL,
  `abstrDestNfviPopId` int(10) unsigned DEFAULT NULL,
  `srcRouterId` varchar(100) DEFAULT NULL,
  `dstRouterId` varchar(100) DEFAULT NULL,
  `srcRouterIp` varchar(100) DEFAULT NULL,
  `dstRouterIp` varchar(100) DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `availableBandwidth` varchar(100) DEFAULT NULL,
  `reservedBandwidth` varchar(100) DEFAULT NULL,
  `totalBandwidth` varchar(100) DEFAULT NULL,
  `allocatedBandwidth` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`logicalLinkId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logicallink`
--

LOCK TABLES `logicallink` WRITE;
/*!40000 ALTER TABLE `logicallink` DISABLE KEYS */;
INSERT INTO `logicallink` VALUES (1,11,21,'192.168.3.30','192.168.4.40','13','14','70.0','1000000.0','0.0','1000000.0','0.0'),(2,11,21,'192.168.3.30','192.168.4.40','13','14','95.0','1000000.0','0.0','1000000.0','0.0'),(3,21,11,'192.168.4.40','192.168.3.30','14','13','70.0','1000000.0','0.0','1000000.0','0.0'),(4,21,11,'192.168.4.40','192.168.3.30','14','13','95.0','1000000.0','0.0','1000000.0','0.0');
/*!40000 ALTER TABLE `logicallink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logicalpath`
--

DROP TABLE IF EXISTS `logicalpath`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logicalpath` (
  `logicalPathId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `abstrSrcNfviPopId` int(10) unsigned DEFAULT NULL,
  `abstrDestNfviPopId` int(10) unsigned DEFAULT NULL,
  `srcRouterId` varchar(100) DEFAULT NULL,
  `dstRouterId` varchar(100) DEFAULT NULL,
  `srcRouterIp` varchar(100) DEFAULT NULL,
  `dstRouterIp` varchar(100) DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `availableBandwidth` varchar(100) DEFAULT NULL,
  `reservedBandwidth` varchar(100) DEFAULT NULL,
  `totalBandwidth` varchar(100) DEFAULT NULL,
  `allocatedBandwidth` varchar(100) DEFAULT NULL,
  `logicalLinkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`logicalPathId`),
  KEY `logicalLinkId` (`logicalLinkId`),
  CONSTRAINT `logicalpath_ibfk_1` FOREIGN KEY (`logicalLinkId`) REFERENCES `logicallink` (`logicallinkid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logicalpath`
--

LOCK TABLES `logicalpath` WRITE;
/*!40000 ALTER TABLE `logicalpath` DISABLE KEYS */;
INSERT INTO `logicalpath` VALUES (1,11,21,'192.168.3.30','192.168.4.40','13','14','70.0','1000000.0','0.0','1000000.0','0.0',1),(2,11,21,'192.168.3.30','192.168.4.40','13','14','95.0','1000000.0','0.0','1000000.0','0.0',2),(3,21,11,'192.168.4.40','192.168.3.30','14','13','70.0','1000000.0','0.0','1000000.0','0.0',3),(4,21,11,'192.168.4.40','192.168.3.30','14','13','95.0','1000000.0','0.0','1000000.0','0.0',4);
/*!40000 ALTER TABLE `logicalpath` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mec_region_info`
--

DROP TABLE IF EXISTS `mec_region_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mec_region_info` (
  `regionId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `altitude` varchar(100) DEFAULT NULL,
  `range_` varchar(100) DEFAULT NULL,
  `abstrNfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`regionId`),
  KEY `abstrNfviPopId` (`abstrNfviPopId`),
  CONSTRAINT `mec_region_info_ibfk_1` FOREIGN KEY (`abstrNfviPopId`) REFERENCES `abstrnfvipop` (`abstrnfvipopid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mec_region_info`
--

LOCK TABLES `mec_region_info` WRITE;
/*!40000 ALTER TABLE `mec_region_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `mec_region_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `radio_coverage_area`
--

DROP TABLE IF EXISTS `radio_coverage_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `radio_coverage_area` (
  `coverageAreaId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `coverageAreaMinBandwidth` varchar(100) DEFAULT NULL,
  `coverageAreaMaxBandwidth` varchar(100) DEFAULT NULL,
  `coverageAreaDelay` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `altitude` varchar(100) DEFAULT NULL,
  `range_` varchar(100) DEFAULT NULL,
  `abstrNfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`coverageAreaId`),
  KEY `abstrNfviPopId` (`abstrNfviPopId`),
  CONSTRAINT `radio_coverage_area_ibfk_1` FOREIGN KEY (`abstrNfviPopId`) REFERENCES `abstrnfvipop` (`abstrnfvipopid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `radio_coverage_area`
--

LOCK TABLES `radio_coverage_area` WRITE;
/*!40000 ALTER TABLE `radio_coverage_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `radio_coverage_area` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-16 10:22:40
CREATE DATABASE  IF NOT EXISTS `mtpdomdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mtpdomdb`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mtpdomdb
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `computeflavour`
--

DROP TABLE IF EXISTS `computeflavour`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `computeflavour` (
  `computeFlavourId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `flavourId` varchar(100) DEFAULT NULL,
  `accelerationCapability` varchar(100) DEFAULT NULL,
  `nfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`computeFlavourId`),
  KEY `nfviPopId` (`nfviPopId`),
  CONSTRAINT `computeflavour_ibfk_1` FOREIGN KEY (`nfviPopId`) REFERENCES `nfvipop` (`nfvipopid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computeflavour`
--

LOCK TABLES `computeflavour` WRITE;
/*!40000 ALTER TABLE `computeflavour` DISABLE KEYS */;
INSERT INTO `computeflavour` VALUES (1,'flavor_spr2','accCapability1',11),(2,'36a598ea-b979-4b30-b3e2-3df5e0ba35df','accCapability1',12),(3,'3','accCapability1',11),(4,'1','accCapability1',21),(5,'2','accCapability1',21),(6,'3','accCapability1',21),(7,'1','accCapability1',31),(8,'2','accCapability1',31),(9,'3','accCapability1',31),(10,'1','accCapability1',41),(11,'2','accCapability1',41),(12,'3','accCapability1',41);
/*!40000 ALTER TABLE `computeflavour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cpuresources`
--

DROP TABLE IF EXISTS `cpuresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cpuresources` (
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `cpuArchitecture` varchar(100) DEFAULT NULL,
  `numVirtualCpu` varchar(100) DEFAULT NULL,
  `cpuClock` varchar(100) DEFAULT NULL,
  `virtualCpuOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `virtualCpuPinningSupported` varchar(100) DEFAULT NULL,
  `computeResourceTypeId` varchar(100) DEFAULT NULL,
  `accelerationCapability` varchar(100) DEFAULT NULL,
  `resourceZoneId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`resourceZoneId`),
  CONSTRAINT `cpuresources_ibfk_1` FOREIGN KEY (`resourceZoneId`) REFERENCES `zoneid` (`resourcezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cpuresources`
--

LOCK TABLES `cpuresources` WRITE;
/*!40000 ALTER TABLE `cpuresources` DISABLE KEYS */;
INSERT INTO `cpuresources` VALUES ('32','0','32','0','x86_64','32','2400','null','1','21','null',2),('32','0','32','0','x86_64','32','2400','null','1','41','null',3);
/*!40000 ALTER TABLE `cpuresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `domain` (
  `domId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `port` varchar(100) DEFAULT NULL,
  `mecAssociatedDomainID` varchar(100) DEFAULT NULL,
  `abstrDomId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`domId`),
  KEY `abstrDomId` (`abstrDomId`),
  CONSTRAINT `domain_ibfk_1` FOREIGN KEY (`abstrDomId`) REFERENCES `mtpabstrdb`.`abstrdomain` (`abstrdomid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domain`
--

LOCK TABLES `domain` WRITE;
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
INSERT INTO `domain` VALUES (1,'vimdummy','VIM','127.0.0.1','51000','-1',NULL),(2,'vimdummy','VIM','127.0.0.1','52000','-1',NULL),(3,'wimdummy','T-WIM','127.0.0.1','53000','-1',NULL);
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interdomainlink`
--

DROP TABLE IF EXISTS `interdomainlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `interdomainlink` (
  `interDomainLinkId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `srcDomId` int(10) unsigned DEFAULT NULL,
  `dstDomId` int(10) unsigned DEFAULT NULL,
  `srcGwId` varchar(100) DEFAULT NULL,
  `dstGwId` varchar(100) DEFAULT NULL,
  `srcGWIp` varchar(100) DEFAULT NULL,
  `dstGwIp` varchar(100) DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `availableBandwidth` varchar(100) DEFAULT NULL,
  `reservedBandwidth` varchar(100) DEFAULT NULL,
  `totalBandwidth` varchar(100) DEFAULT NULL,
  `allocatedBandwidth` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`interDomainLinkId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interdomainlink`
--

LOCK TABLES `interdomainlink` WRITE;
/*!40000 ALTER TABLE `interdomainlink` DISABLE KEYS */;
INSERT INTO `interdomainlink` VALUES (1,1,3,'192.168.3.30','192.168.1.10','13','23','10','10000000','0','10000000','0'),(2,3,1,'192.168.1.10','192.168.3.30','23','13','10','10000000','0','10000000','0'),(3,2,3,'192.168.4.40','192.168.1.20','14','24','10','10000000','0','10000000','0'),(4,3,2,'192.168.1.20','192.168.4.40','24','14','10','10000000','0','10000000','0');
/*!40000 ALTER TABLE `interdomainlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logicalpath_interdomainlink`
--

DROP TABLE IF EXISTS `logicalpath_interdomainlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logicalpath_interdomainlink` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `logicalPathId` int(10) unsigned DEFAULT NULL,
  `interDomainLinkId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logicalPathId` (`logicalPathId`),
  KEY `interDomainLinkId` (`interDomainLinkId`),
  CONSTRAINT `logicalpath_interdomainlink_ibfk_1` FOREIGN KEY (`logicalPathId`) REFERENCES `mtpabstrdb`.`logicalpath` (`logicalpathid`),
  CONSTRAINT `logicalpath_interdomainlink_ibfk_2` FOREIGN KEY (`interDomainLinkId`) REFERENCES `interdomainlink` (`interdomainlinkid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logicalpath_interdomainlink`
--

LOCK TABLES `logicalpath_interdomainlink` WRITE;
/*!40000 ALTER TABLE `logicalpath_interdomainlink` DISABLE KEYS */;
INSERT INTO `logicalpath_interdomainlink` VALUES (1,1,1),(2,1,4),(3,2,1),(4,2,4),(5,3,3),(6,3,2),(7,4,3),(8,4,2);
/*!40000 ALTER TABLE `logicalpath_interdomainlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logicalpath_networkresources`
--

DROP TABLE IF EXISTS `logicalpath_networkresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `logicalpath_networkresources` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `logicalPathId` int(10) unsigned DEFAULT NULL,
  `networkResId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `logicalPathId` (`logicalPathId`),
  KEY `networkResId` (`networkResId`),
  CONSTRAINT `logicalpath_networkresources_ibfk_1` FOREIGN KEY (`logicalPathId`) REFERENCES `mtpabstrdb`.`logicalpath` (`logicalpathid`),
  CONSTRAINT `logicalpath_networkresources_ibfk_2` FOREIGN KEY (`networkResId`) REFERENCES `networkresources` (`networkresid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logicalpath_networkresources`
--

LOCK TABLES `logicalpath_networkresources` WRITE;
/*!40000 ALTER TABLE `logicalpath_networkresources` DISABLE KEYS */;
INSERT INTO `logicalpath_networkresources` VALUES (1,1,101),(2,2,102),(3,3,103),(4,4,104);
/*!40000 ALTER TABLE `logicalpath_networkresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mec_region_info`
--

DROP TABLE IF EXISTS `mec_region_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `mec_region_info` (
  `regionId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `altitude` varchar(100) DEFAULT NULL,
  `range_` varchar(100) DEFAULT NULL,
  `domId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`regionId`),
  KEY `domId` (`domId`),
  CONSTRAINT `mec_region_info_ibfk_1` FOREIGN KEY (`domId`) REFERENCES `domain` (`domid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mec_region_info`
--

LOCK TABLES `mec_region_info` WRITE;
/*!40000 ALTER TABLE `mec_region_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `mec_region_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memoryresources`
--

DROP TABLE IF EXISTS `memoryresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `memoryresources` (
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `virtualMemSize` decimal(10,0) DEFAULT NULL,
  `virtualMemOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `numaEnabled` bit(1) DEFAULT NULL,
  `computeResourceTypeId` varchar(100) DEFAULT NULL,
  `accelerationCapability` varchar(100) DEFAULT NULL,
  `resourceZoneId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`resourceZoneId`),
  CONSTRAINT `memoryresources_ibfk_1` FOREIGN KEY (`resourceZoneId`) REFERENCES `zoneid` (`resourcezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memoryresources`
--

LOCK TABLES `memoryresources` WRITE;
/*!40000 ALTER TABLE `memoryresources` DISABLE KEYS */;
INSERT INTO `memoryresources` VALUES ('16000','0','16000','0',1,'null',_binary '\0','11','null',2),('16000','0','16000','0',1,'null',_binary '\0','31','null',3);
/*!40000 ALTER TABLE `memoryresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `networkresources`
--

DROP TABLE IF EXISTS `networkresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `networkresources` (
  `networkResId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `srcGwId` varchar(100) DEFAULT NULL,
  `dstGwId` varchar(100) DEFAULT NULL,
  `srcGWIp` varchar(100) DEFAULT NULL,
  `dstGwIp` varchar(100) DEFAULT NULL,
  `delay` varchar(100) DEFAULT NULL,
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `networkResourceTypeId` varchar(100) DEFAULT NULL,
  `networkType` varchar(100) DEFAULT NULL,
  `bandwidth` decimal(10,0) DEFAULT NULL,
  `resourceZoneId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`networkResId`),
  KEY `resourceZoneId` (`resourceZoneId`),
  CONSTRAINT `networkresources_ibfk_1` FOREIGN KEY (`resourceZoneId`) REFERENCES `zoneid` (`resourcezoneid`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `networkresources`
--

LOCK TABLES `networkresources` WRITE;
/*!40000 ALTER TABLE `networkresources` DISABLE KEYS */;
INSERT INTO `networkresources` VALUES (101,'192.168.1.10','192.168.1.20','10','20','50','1000000','0','1000000','0','101','vxlan',1000000,1),(102,'192.168.1.10','192.168.1.20','11','21','75','1000000','0','1000000','0','102','vxlan',1000000,1),(103,'192.168.1.20','192.168.1.10','20','10','50','1000000','0','1000000','0','103','vxlan',1000000,1),(104,'192.168.1.20','192.168.1.10','21','11','75','1000000','0','1000000','0','104','vxlan',1000000,1);
/*!40000 ALTER TABLE `networkresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nfvipop`
--

DROP TABLE IF EXISTS `nfvipop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `nfvipop` (
  `nfviPopId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vimId` int(10) unsigned DEFAULT NULL,
  `geographicalLocationInfo` varchar(100) DEFAULT NULL,
  `networkConnectivityEndpoint` varchar(100) DEFAULT NULL,
  `domId` int(10) unsigned DEFAULT NULL,
  `abstrNfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`nfviPopId`),
  KEY `domId` (`domId`),
  KEY `abstrNfviPopId` (`abstrNfviPopId`),
  CONSTRAINT `nfvipop_ibfk_1` FOREIGN KEY (`domId`) REFERENCES `domain` (`domid`),
  CONSTRAINT `nfvipop_ibfk_2` FOREIGN KEY (`abstrNfviPopId`) REFERENCES `mtpabstrdb`.`abstrnfvipop` (`abstrnfvipopid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nfvipop`
--

LOCK TABLES `nfvipop` WRITE;
/*!40000 ALTER TABLE `nfvipop` DISABLE KEYS */;
INSERT INTO `nfvipop` VALUES (3,3,'Pisa','192.168.1.10;192.168.1.20',3,NULL),(11,1,'Pisa','192.168.3.30',1,11),(21,2,'Pisa','192.168.4.40',2,21);
/*!40000 ALTER TABLE `nfvipop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `radio_coverage_area`
--

DROP TABLE IF EXISTS `radio_coverage_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `radio_coverage_area` (
  `coverageAreaId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `coverageAreaMinBandwidth` varchar(100) DEFAULT NULL,
  `coverageAreaMaxBandwidth` varchar(100) DEFAULT NULL,
  `coverageAreaDelay` varchar(100) DEFAULT NULL,
  `latitude` varchar(100) DEFAULT NULL,
  `longitude` varchar(100) DEFAULT NULL,
  `altitude` varchar(100) DEFAULT NULL,
  `range_` varchar(100) DEFAULT NULL,
  `nfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`coverageAreaId`),
  KEY `nfviPopId` (`nfviPopId`),
  CONSTRAINT `radio_coverage_area_ibfk_1` FOREIGN KEY (`nfviPopId`) REFERENCES `nfvipop` (`nfvipopid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `radio_coverage_area`
--

LOCK TABLES `radio_coverage_area` WRITE;
/*!40000 ALTER TABLE `radio_coverage_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `radio_coverage_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storageresources`
--

DROP TABLE IF EXISTS `storageresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `storageresources` (
  `availableCapacity` varchar(100) DEFAULT NULL,
  `reservedCapacity` varchar(100) DEFAULT NULL,
  `totalCapacity` varchar(100) DEFAULT NULL,
  `allocatedCapacity` varchar(100) DEFAULT NULL,
  `resourceZoneId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`resourceZoneId`),
  CONSTRAINT `storageresources_ibfk_1` FOREIGN KEY (`resourceZoneId`) REFERENCES `zoneid` (`resourcezoneid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storageresources`
--

LOCK TABLES `storageresources` WRITE;
/*!40000 ALTER TABLE `storageresources` DISABLE KEYS */;
/*!40000 ALTER TABLE `storageresources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualcpu`
--

DROP TABLE IF EXISTS `virtualcpu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualcpu` (
  `cpuArchitecture` varchar(100) DEFAULT NULL,
  `numVirtualCpu` varchar(100) DEFAULT NULL,
  `cpuClock` varchar(100) DEFAULT NULL,
  `virtualCpuOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `virtualCpuPinningSupported` varchar(100) DEFAULT NULL,
  `computeFlavourId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeFlavourId`),
  CONSTRAINT `virtualcpu_ibfk_1` FOREIGN KEY (`computeFlavourId`) REFERENCES `computeflavour` (`computeflavourid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualcpu`
--

LOCK TABLES `virtualcpu` WRITE;
/*!40000 ALTER TABLE `virtualcpu` DISABLE KEYS */;
INSERT INTO `virtualcpu` VALUES ('x86','4','1','policy1',NULL,1),('x86','4','1','policy1',NULL,2),('x86','4','1','policy1',NULL,3),('x86','4','1','policy1',NULL,4),('x86','4','1','policy1',NULL,5),('x86','4','1','policy1',NULL,6),('x86','4','1','policy1',NULL,7),('x86','4','1','policy1',NULL,8),('x86','4','1','policy1',NULL,9),('x86','4','1','policy1',NULL,10),('x86','4','1','policy1',NULL,11),('x86','4','1','policy1',NULL,12);
/*!40000 ALTER TABLE `virtualcpu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualmemorydata`
--

DROP TABLE IF EXISTS `virtualmemorydata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualmemorydata` (
  `virtualMemSize` decimal(10,0) DEFAULT NULL,
  `virtualMemOversubscriptionPolicy` varchar(100) DEFAULT NULL,
  `numaEnabled` bit(1) DEFAULT NULL,
  `computeFlavourId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`computeFlavourId`),
  CONSTRAINT `virtualmemorydata_ibfk_1` FOREIGN KEY (`computeFlavourId`) REFERENCES `computeflavour` (`computeflavourid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualmemorydata`
--

LOCK TABLES `virtualmemorydata` WRITE;
/*!40000 ALTER TABLE `virtualmemorydata` DISABLE KEYS */;
INSERT INTO `virtualmemorydata` VALUES (100,'policy1',_binary '',1),(100,'policy1',_binary '',2),(100,'policy1',_binary '',3),(100,'policy1',_binary '',4),(100,'policy1',_binary '',5),(100,'policy1',_binary '',6),(100,'policy1',_binary '',7),(100,'policy1',_binary '',8),(100,'policy1',_binary '',9),(100,'policy1',_binary '',10),(100,'policy1',_binary '',11),(100,'policy1',_binary '',12);
/*!40000 ALTER TABLE `virtualmemorydata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualnetworkinterfacedata`
--

DROP TABLE IF EXISTS `virtualnetworkinterfacedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualnetworkinterfacedata` (
  `netInterfaceDataId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `networkId` int(10) unsigned DEFAULT NULL,
  `networkPortId` int(10) unsigned DEFAULT NULL,
  `typeVirtualNic` varchar(100) DEFAULT NULL,
  `typeConfiguration` varchar(100) DEFAULT NULL,
  `bandwidth` varchar(100) DEFAULT NULL,
  `accelerationCapability` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `computeFlavourId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`netInterfaceDataId`),
  KEY `computeFlavourId` (`computeFlavourId`),
  CONSTRAINT `virtualnetworkinterfacedata_ibfk_1` FOREIGN KEY (`computeFlavourId`) REFERENCES `computeflavour` (`computeflavourid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualnetworkinterfacedata`
--

LOCK TABLES `virtualnetworkinterfacedata` WRITE;
/*!40000 ALTER TABLE `virtualnetworkinterfacedata` DISABLE KEYS */;
INSERT INTO `virtualnetworkinterfacedata` VALUES (1,0,0,'0','','100','','',1),(2,0,0,'0','','10','','',2),(3,0,0,'0','','100','','',3),(4,0,0,'0','','100','','',4),(5,0,0,'0','','100','','',5),(6,0,0,'0','','100','','',6),(7,0,0,'0','','100','','',7),(8,0,0,'0','','100','','',8),(9,0,0,'0','','100','','',9),(10,0,0,'0','','100','','',10),(11,0,0,'0','','100','','',11),(12,0,0,'0','','100','','',12);
/*!40000 ALTER TABLE `virtualnetworkinterfacedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `virtualstoragedata`
--

DROP TABLE IF EXISTS `virtualstoragedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `virtualstoragedata` (
  `storageDataId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `typeOfStorage` varchar(100) DEFAULT NULL,
  `sizeOfStorage` decimal(10,0) DEFAULT NULL,
  `computeFlavourId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`storageDataId`),
  KEY `computeFlavourId` (`computeFlavourId`),
  CONSTRAINT `virtualstoragedata_ibfk_1` FOREIGN KEY (`computeFlavourId`) REFERENCES `computeflavour` (`computeflavourid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `virtualstoragedata`
--

LOCK TABLES `virtualstoragedata` WRITE;
/*!40000 ALTER TABLE `virtualstoragedata` DISABLE KEYS */;
INSERT INTO `virtualstoragedata` VALUES (1,'typeStorage1',10,1),(2,'typeStorage1',10,2),(3,'typeStorage1',10,3),(4,'typeStorage1',10,4),(5,'typeStorage1',10,5),(6,'typeStorage1',10,6),(7,'typeStorage1',10,7),(8,'typeStorage1',10,8),(9,'typeStorage1',10,9),(10,'typeStorage1',10,10),(11,'typeStorage1',10,11),(12,'typeStorage1',10,12);
/*!40000 ALTER TABLE `virtualstoragedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zoneid`
--

DROP TABLE IF EXISTS `zoneid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `zoneid` (
  `resourceZoneId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `zoneId` varchar(100) DEFAULT NULL,
  `zoneName` varchar(100) DEFAULT NULL,
  `zoneState` varchar(100) DEFAULT NULL,
  `zoneProperty` varchar(100) DEFAULT NULL,
  `metadata` varchar(100) DEFAULT NULL,
  `nfviPopId` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`resourceZoneId`),
  KEY `nfviPopId` (`nfviPopId`),
  CONSTRAINT `zoneid_ibfk_1` FOREIGN KEY (`nfviPopId`) REFERENCES `nfvipop` (`nfvipopid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zoneid`
--

LOCK TABLES `zoneid` WRITE;
/*!40000 ALTER TABLE `zoneid` DISABLE KEYS */;
INSERT INTO `zoneid` VALUES (1,'3','WIMname','enabled','WIMProperty',NULL,3),(2,'10','Dummy VIM1','enable','power',NULL,11),(3,'20','Dummy VIM2','enable','power',NULL,21);
/*!40000 ALTER TABLE `zoneid` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-16 10:22:40
