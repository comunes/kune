DROP TABLE IF EXISTS `invitation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invitation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` bigint(20) DEFAULT NULL,
  `hash` varchar(255) NOT NULL,
  `invitedToToken` varchar(255) DEFAULT NULL,
  `notifType` varchar(255) DEFAULT NULL,
  `invitTo` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `fromUser_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hash` (`hash`),
  KEY `FK473F7799890F3BFB` (`fromUser_id`),
  CONSTRAINT `FK473F7799890F3BFB` FOREIGN KEY (`fromUser_id`) REFERENCES `kusers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
