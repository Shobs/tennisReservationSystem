-- MySQL dump 10.13  Distrib 8.0.12, for osx10.14 (x86_64)
--
-- Host: localhost    Database: cs157a
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Archive`
--

DROP TABLE IF EXISTS `Archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Archive` (
  `reservationId` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `tennisCourtId` int(11) DEFAULT NULL,
  `paymentId` int(11) DEFAULT NULL,
  `reservationTimeStart` timestamp NULL DEFAULT NULL,
  `reservationTimeEnd` timestamp NULL DEFAULT NULL,
  `updateAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`reservationId`),
  KEY `username` (`username`),
  KEY `tennisCourtId` (`tennisCourtId`),
  KEY `paymentId` (`paymentId`),
  CONSTRAINT `archive_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `archive_ibfk_2` FOREIGN KEY (`tennisCourtId`) REFERENCES `tenniscourt` (`tenniscourtid`),
  CONSTRAINT `archive_ibfk_3` FOREIGN KEY (`paymentId`) REFERENCES `payment` (`paymentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Archive`
--

LOCK TABLES `Archive` WRITE;
/*!40000 ALTER TABLE `Archive` DISABLE KEYS */;
INSERT INTO `Archive` VALUES (1,'janice.cummings',9,1,'2018-12-02 16:00:00','2018-12-02 17:00:00','2018-12-01 16:00:00'),(2,'brycen.paucek',4,2,'2018-12-03 16:00:00','2018-12-03 17:00:00','2018-12-01 17:00:00'),(3,'brycen.paucek',4,3,'2018-12-03 23:00:00','2018-12-04 00:00:00','2018-12-01 20:00:00');
/*!40000 ALTER TABLE `Archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payment`
--

DROP TABLE IF EXISTS `Payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Payment` (
  `paymentId` int(11) NOT NULL AUTO_INCREMENT,
  `cost` int(11) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paymentId`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payment`
--

LOCK TABLES `Payment` WRITE;
/*!40000 ALTER TABLE `Payment` DISABLE KEYS */;
INSERT INTO `Payment` VALUES (1,40,'American Express'),(2,58,'Visa'),(3,58,'Visa'),(106,120,'Visa'),(107,31,'Discover Card'),(108,58,'American Express'),(109,40,'MasterCard'),(110,80,'Visa'),(111,30,'Visa'),(112,50,'Visa'),(113,33,'American Express'),(114,17,'MasterCard'),(115,25,'Discover Card'),(116,12,'MasterCard'),(117,25,'American Express'),(118,40,'Visa'),(119,15,'Visa'),(120,15,'Discover Card'),(121,31,'Discover Card'),(122,17,'Discover Card'),(123,17,'Visa'),(124,17,'Visa');
/*!40000 ALTER TABLE `Payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RecreationCenter`
--

DROP TABLE IF EXISTS `RecreationCenter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `RecreationCenter` (
  `recCenterId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `rentalPricePerHour` int(11) DEFAULT NULL,
  PRIMARY KEY (`recCenterId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RecreationCenter`
--

LOCK TABLES `RecreationCenter` WRITE;
/*!40000 ALTER TABLE `RecreationCenter` DISABLE KEYS */;
INSERT INTO `RecreationCenter` VALUES (1,'Venus',40),(2,'Jupiter',33),(3,'Mercury',29),(4,'Puto',25),(5,'Mars',12),(6,'Earth',31),(7,'Neptune',24),(8,'Sun',14),(9,'Uranus',22),(10,'Nimiri',33),(11,'Titan',33),(12,'Sirius',33);
/*!40000 ALTER TABLE `RecreationCenter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservation`
--

DROP TABLE IF EXISTS `Reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Reservation` (
  `reservationId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `tennisCourtId` int(11) DEFAULT NULL,
  `paymentId` int(11) DEFAULT NULL,
  `reservationTimeStart` timestamp NULL DEFAULT NULL,
  `reservationTimeEnd` timestamp NULL DEFAULT NULL,
  `updateAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`reservationId`),
  KEY `username` (`username`),
  KEY `tennisCourtId` (`tennisCourtId`),
  KEY `paymentId` (`paymentId`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`tennisCourtId`) REFERENCES `tenniscourt` (`tenniscourtid`),
  CONSTRAINT `reservation_ibfk_3` FOREIGN KEY (`paymentId`) REFERENCES `payment` (`paymentid`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservation`
--

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;
INSERT INTO `Reservation` VALUES (21,'alysson.lowe',9,106,'2018-12-29 20:00:00','2018-12-29 23:00:00','2018-12-29 20:00:44'),(22,'angus.stiedemann',10,107,'2019-01-03 04:00:00','2019-01-03 05:15:00','2019-01-03 04:00:10'),(23,'alysson.lowe',4,108,'2018-12-24 20:00:00','2018-12-24 22:00:00','2018-12-24 20:00:20'),(24,'carter.schoen',9,109,'2018-12-27 14:00:00','2018-12-27 15:00:00','2018-12-27 14:00:10'),(25,'christopher.bailey',9,110,'2018-12-21 23:00:00','2018-12-22 01:00:00','2018-12-21 23:00:28'),(26,'tessie73',3,111,'2018-12-28 05:00:00','2018-12-28 06:15:00','2018-12-28 05:00:05'),(27,'proob',12,112,'2018-12-30 17:00:00','2018-12-30 18:30:00','2018-12-30 17:00:58'),(28,'shields.lottie',1,113,'2019-01-09 04:00:00','2019-01-09 05:00:00','2019-01-09 04:00:40'),(29,'caitlyn04',1,114,'2019-01-08 20:00:00','2019-01-08 20:30:00','2019-01-08 20:00:25'),(30,'chet02',11,115,'2018-12-31 05:00:00','2018-12-31 05:45:00','2018-12-31 05:00:01'),(31,'caitlyn04',8,116,'2018-12-28 20:30:00','2018-12-28 21:20:00','2018-12-28 20:30:00'),(32,'alysson.lowe',1,117,'2018-12-27 14:00:00','2018-12-27 14:45:00','2018-12-27 14:00:00'),(33,'janice.cummings',9,118,'2018-12-09 16:00:00','2018-12-09 17:00:00','2018-12-09 16:00:00'),(34,'alysson.lowe',6,119,'2018-12-30 04:00:43','2018-12-30 04:30:43','2018-12-30 04:00:43'),(35,'alysson.lowe',6,120,'2018-12-28 20:00:01','2018-12-28 20:30:01','2018-12-28 20:00:01'),(36,'casimir82',10,121,'2018-12-29 16:00:00','2018-12-29 17:15:00','2018-12-29 16:00:00'),(37,'chet02',12,122,'2019-01-03 11:00:00','2019-01-03 11:30:00','2019-01-03 11:00:00'),(38,'dawn21',12,123,'2019-01-03 11:30:00','2019-01-03 12:00:00','2019-01-03 11:30:00'),(39,'avery56',12,124,'2019-01-03 10:30:00','2019-01-03 11:00:00','2019-01-03 10:30:00');
/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TennisCourt`
--

DROP TABLE IF EXISTS `TennisCourt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `TennisCourt` (
  `tennisCourtId` int(11) NOT NULL AUTO_INCREMENT,
  `recCenterId` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tennisCourtId`),
  KEY `recCenterId` (`recCenterId`),
  CONSTRAINT `tenniscourt_ibfk_1` FOREIGN KEY (`recCenterId`) REFERENCES `recreationcenter` (`reccenterid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TennisCourt`
--

LOCK TABLES `TennisCourt` WRITE;
/*!40000 ALTER TABLE `TennisCourt` DISABLE KEYS */;
INSERT INTO `TennisCourt` VALUES (1,2,'Hard'),(2,3,'Hard'),(3,7,'Grass'),(4,3,'Hard'),(5,6,'Sand'),(6,3,'Sand'),(7,10,'Sand'),(8,8,'Grass'),(9,1,'Sand'),(10,4,'Grass'),(11,12,'Grass'),(12,11,'Grass');
/*!40000 ALTER TABLE `TennisCourt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `User` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('alysson.lowe','8c26182707bd50e1d84ecf0754dd341897c2931d',0),('angus.stiedemann','f13176ac41c3e555cd81dc872f4c036437daa76a',0),('aurelia.zemlak','3d39bedd9a6f99cbc2d12e5f317dfd8c23122c1e',0),('avery56','99cc169fd7133d4db42f8f0cbcd444f123fa2c6f',1),('bauch.darby','2ee640b011dabad9f37a494027093dd4abba5aba',0),('bhermann','6feb221dd60a23a37f2b628c1c2a42257f668e5c',0),('blair18','c38c10340962391700632745283ae2c2fe311e6e',1),('brycen.paucek','4f000034ec723a414cbc801b8ae3f5b6cfba6b61',0),('caitlyn04','ecae38a7b14c9df977121f038e702d734379aad4',0),('camron31','65eb7f9cc6aee492558852375fa3a085c48770cd',0),('carolina68','07913fa1bd96b52e889b2bc462c709a51417fc2b',0),('carter.schoen','474b1a4dc4416792736eaa0cd6662f5e78557d20',0),('casimir82','450cb2a1fae549b112b0b4e24f3937986508180b',0),('chet02','eef33629fbaf0ed286c989c70d652c38d3971111',1),('christopher.bailey','745372253cd48f873d1c29eef31809796b91b596',0),('collier.ali','66077075bc77843bf8ab63a20d96ca0f7bc20fb2',0),('coty62','bc63803293af3041d27054c24c1f249b64970b56',0),('crona.eudora','a219b56c63152feeee22e04d4eefb1330dc8fe6f',0),('csipes','0f3db14008ce7490fd2af9d64e1b5da70b6bf668',0),('dankunding','2b096ce60ca6f20b188c730756cbc071233d4672',0),('dawn21','242fa9e743534e0a4a4bb772a90ddf6581073005',0),('ddavis','9e1adf8a155a0a6c60f2fd1053a65ee36b6693af',0),('deshawn17','04a3c97b5a0c08c42782e516d7aeeb27da527eff',0),('dion.o\'hara','09923ef03ffc96169b7c07163f2136623719346c',0),('elisabeth73','740644ff78b626ecbfc7ead5336f444a897261c2',0),('estefania.kub','e79f1ecd99f33f88eb55883ea38cc9b3cc12e6d2',0),('estrella.volkman','ff851ecf59c31171f182d3523c64b8a64c674e20',0),('estrella53','877727c52c69a41941ed79b09f8e564e6eea77c4',0),('ewald98','089455869bac01e17464cb10367a1cd9fbd94751',0),('ewell.fadel','0af16d5d7adb655594fb205d6572acee4963e1e4',0),('fcrona','8877d0101065ee8a04b22af15f27bd15f4feea12',0),('finn99','28d7a6318552a7014e9b7d1b4865276663e864c8',0),('garfield60','ccc336b1eb54bf027d40b1c50192e62e9a93b2ed',0),('garrett48','ce97d26da3d19200bb67495070bf4db5d82197e6',0),('golden.glover','4483c9542eceb1559b12b9164ec3b32869b99dad',0),('greenholt.mireille','a72d0dd5a9fad104b8f9f5ebcb2c265d91ea6366',0),('hartmann.anibal','0bbb20c3b0361638401c09cbd2be1e6f408f836e',1),('henry27','346f74cacb74699ad0e4a2e15f7882f5f0591a20',0),('hillard07','870f8e9aa1af832e52a5754f0fb012679db52af3',0),('hintz.nora','e56107d947ba1e7355d332ab88a2156df5fafe09',0),('hirthe.euna','5ab527cc3c187c48824542313943292f4d7d965c',0),('irma.kohler','2f9a17c54b98763a05da06a77def258e8bf20c58',1),('janice.cummings','f4e5dc03b16caef2a1bd8e5959e9646b1fee7704',0),('juliana.morissette','3a22a90f6779ae7fd9c30bfa3568685409d645db',0),('justus47','a7fe3af27b3c6f3f5b289fa7c7e01bbb73f167a8',0),('kallie31','6ac3bfb65bc9ef7c0ee1562c3ed5732207a42743',0),('kariane.braun','7d8d25971b07e87686a5d7b56b2eca426d613a48',0),('keeley.rowe','df06f8f21bbbd73412ba6961dca9ea627f58bfe6',0),('kenna.crooks','85fdece83d32c7796df02527f354686b7a3d477d',0),('kiera.johns','1629919f7d5c7a99aaf8ca8a1ccecec5100500ad',0),('konopelski.delmer','fc37c198fc969ac803179b0ec803ac8eb5d1b5f2',0),('krempel','859acf8bea41e87cab01fbb6dc6420349f0fe1a3',0),('kub.darrell','c95070c63487b29d6b4a9d34b67098b83244bc82',0),('laila.witting','bf687c35fe36212cdcf40a3782c0602d30433df6',0),('larkin.bria','d36fe11c87dd729740982173821d19d0eb3eab0b',0),('libbie.rath','a468f0d37bb0bda5a43ead3928e495f3de394f9c',0),('london.orn','59a03dd4a61208e52f674737d5cb163f286fa179',0),('lturner','d2bf09f7ab7cf99e9b1190b47892a4c0f63fa5de',0),('lyric18','ec946582dd609083a43caf83139b6706960ff991',0),('macey.roob','b28043df425927b67507d710de315884e29898a2',0),('mayer.kelsi','efae810dba20d35f435e9a07f9ea27670af45c93',0),('mckenna40','e757bde82fa8e40737d2b22048ca5292ac354a34',0),('meda.kuphal','37a4769d854ad5ed378a5177f7e7e55f2d7f89ca',0),('mkassulke','4dedeed2e23b15a6007c212f16ec897937fda69a',0),('mueller.cary','7dec6cacda908b659afd13e77c1ee5aa06d82da8',0),('nader.opal','a4c5e6ce907240ea3e58f246e1b1896437fc68ba',0),('nannie14','fa33711ef4cb91255dc5c286885917e80ef60b05',0),('orpha.hayes','051699a8aea548e436692be407ff4f677aec8cb7',1),('osborne.fisher','6dbb18f2dbaf355beaafc715788a9e8d6a945ac2',0),('paige.baumbach','fe0191a4061244a4865c16c3562a6961b615442a',0),('predovic.leon','1360761083894aaec7ae57be86ed52ac3855d71c',0),('proob','0557f86bd116402d92406560692f1420d4c0cd78',0),('pstehr','8427198fac6ff8817bfed9afb04d6cc10887c8bd',0),('qschinner','a5358182086e88dea49cbb934f760be535483cb5',0),('raoul.dooley','9b15dac3e6607730cf6f18e0f54fab86f0262ab8',0),('reed.treutel','f6c7c0c83cfb42d95b9ed3d184440dc677b6caf8',0),('reichert.presley','b0184469b4dcb986a62cc19e146563cf385d9fbc',0),('rex72','d6e69f49eb2b6f42ec7f0bdfeb6f02e42b783f89',0),('rico.hilpert','6c7bf41026167fe9fe2bea3d02b673767ee53628',0),('riley.walker','ef0321c13c57f555accaec2ef1a9d93b4fc32fb9',0),('rjaskolski','253de2e1c50cc259abf0ee2009b5805ff187c64a',0),('rudolph.greenfelder','d9d6543fa0da1fac7b54cb61e30679e1bddcabe4',0),('ruecker.camylle','fc74c933ee602aead4f9287d1e9dfc323cb4b671',0),('sallie85','7955aa29a14dfd328d1b48b24ac33e11f8b8edef',0),('schaden.weston','d1406294dc5de7cb409aafd776676beb279f772a',0),('scottie.beahan','b2e1f621d1b9df0e7ab3e082a26f05f906159e85',0),('shemar.o\'reilly','dd3de048b8b4f735913db62d990a170f54824742',0),('shields.lottie','2fe1167fe2ae014eb9715776a85fbc0bd0776482',0),('tessie73','bd550e9a2ee27e669ace3dd043237453f259cd86',0),('tkulas','b182ec1819430a04adcd779fcc9ae39fd0a7e74c',0),('trace66','04d5f25b79cba119c8bc766a542ed515815bea15',0),('trent.mertz','5157b70a1cc3b40f92247bc0e7c9eb1b46b6cf57',0),('tressa.hudson','9f03f62278869183c25da68818222800b5a051d5',0),('valentin.runte','abc6b7593d8fa03deab41226fa7154bdec6b5ac5',0),('vbarrows','3cebe6494a29d632ac7949aca58014c4590aa20c',0),('verna81','71da6517c46fa70a8a29291525fac07e428e7691',0),('vrenner','a0bd69464af8d34861333fa7e1d0b2da5253e2ac',0),('vshields','8f98854b4f07faa2b109d79e6dd822071e7d0543',0),('walter.justice','61da3c2685e4c62cfddca700a04e2fd86e9330e8',0),('willow.paucek','b4f6c733dc3f1c2c481ce5ad15e339ebac41f2fd',0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
--
-- WARNING: old server version. The following dump may be incomplete.
--
DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `adminSignup` BEFORE INSERT ON `User` FOR EACH ROW BEGIN If new.isAdmin = true AND (select count(*) from User where isAdmin = true) > 5 THEN SET new.isAdmin = false; END IF; END */;;
DELIMITER ;
--
-- WARNING: old server version. The following dump may be incomplete.
--
DELIMITER ;;
/*!50003 SET SESSION SQL_MODE="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION" */;;
/*!50003 CREATE */ /*!50017 DEFINER=`root`@`localhost` */ /*!50003 TRIGGER `deleteUser` BEFORE DELETE ON `User` FOR EACH ROW BEGIN DELETE FROM Reservation WHERE username = old.username;END */;;
DELIMITER ;

--
-- Dumping routines for database 'cs157a'
--
/*!50003 DROP PROCEDURE IF EXISTS `Archive` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `Archive`(IN cutOff TIMESTAMP)
BEGIN
  INSERT INTO Archive SELECT * FROM Reservation WHERE reservationTimeEnd < cutOff;
  DELETE FROM Reservation WHERE reservationTimeEnd < cutOff;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-05 23:01:02
