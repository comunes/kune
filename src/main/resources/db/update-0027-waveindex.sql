DROP TABLE IF EXISTS `participants_waves`;

DROP TABLE IF EXISTS `waves`;

DROP TABLE IF EXISTS `participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participants` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `address` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `waves` (
  `domain` varchar(255) NOT NULL,
  `waveId` varchar(255) NOT NULL,
  `waveletId` varchar(255) NOT NULL,
  `creationTime` bigint(20) DEFAULT NULL,
  `lastModifiedTime` bigint(20) DEFAULT NULL,
  `rendered` longtext,
  `id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`domain`,`waveId`,`waveletId`),
  KEY `FK6BACC9AB9A696E6` (`id`),
  CONSTRAINT `FK6BACC9AB9A696E6` FOREIGN KEY (`id`) REFERENCES `participants` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participants_waves` (
  `participants_id` bigint(20) NOT NULL,
  `waves_domain` varchar(255) NOT NULL,
  `waves_waveId` varchar(255) NOT NULL,
  `waves_waveletId` varchar(255) NOT NULL,
  PRIMARY KEY (`participants_id`,`waves_domain`,`waves_waveId`,`waves_waveletId`),
  KEY `FKAAA6EF3BEBD95FE5` (`participants_id`),
  KEY `FKAAA6EF3BDDE119E1` (`waves_domain`,`waves_waveId`,`waves_waveletId`),
  CONSTRAINT `FKAAA6EF3BDDE119E1` FOREIGN KEY (`waves_domain`, `waves_waveId`, `waves_waveletId`) REFERENCES `waves` (`domain`, `waveId`, `waveletId`),
  CONSTRAINT `FKAAA6EF3BEBD95FE5` FOREIGN KEY (`participants_id`) REFERENCES `participants` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
