-- MySQL dump 10.13  Distrib 5.1.58, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: kune_dev
-- ------------------------------------------------------
-- Server version	5.1.58-1ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `access_lists`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_lists` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admins_id` bigint(20) DEFAULT NULL,
  `editors_id` bigint(20) DEFAULT NULL,
  `viewers_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8BFAE0FA45D900E6` (`editors_id`),
  KEY `FK8BFAE0FA522D370B` (`viewers_id`),
  KEY `FK8BFAE0FAA3EB21C8` (`admins_id`),
  CONSTRAINT `FK8BFAE0FAA3EB21C8` FOREIGN KEY (`admins_id`) REFERENCES `group_list` (`id`),
  CONSTRAINT `FK8BFAE0FA45D900E6` FOREIGN KEY (`editors_id`) REFERENCES `group_list` (`id`),
  CONSTRAINT `FK8BFAE0FA522D370B` FOREIGN KEY (`viewers_id`) REFERENCES `group_list` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `container_translation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `container_translation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `language_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK312BE3F39F03C503` (`language_id`),
  CONSTRAINT `FK312BE3F39F03C503` FOREIGN KEY (`language_id`) REFERENCES `globalize_languages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `containers`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `containers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` bigint(20) NOT NULL,
  `deletedOn` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `toolName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `typeId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `accessLists_id` bigint(20) DEFAULT NULL,
  `language_id` bigint(20) DEFAULT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8A84412F8E388F` (`accessLists_id`),
  KEY `FK8A844125DDFDF26` (`parent_id`),
  KEY `FK8A844129F76A8FB` (`owner_id`),
  KEY `FK8A844129F03C503` (`language_id`),
  CONSTRAINT `FK8A844129F03C503` FOREIGN KEY (`language_id`) REFERENCES `globalize_languages` (`id`),
  CONSTRAINT `FK8A844125DDFDF26` FOREIGN KEY (`parent_id`) REFERENCES `containers` (`id`),
  CONSTRAINT `FK8A844129F76A8FB` FOREIGN KEY (`owner_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FK8A84412F8E388F` FOREIGN KEY (`accessLists_id`) REFERENCES `access_lists` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `containers_container_translation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `containers_container_translation` (
  `containers_id` bigint(20) NOT NULL,
  `containerTranslations_id` bigint(20) NOT NULL,
  UNIQUE KEY `containerTranslations_id` (`containerTranslations_id`),
  KEY `FK1BA62F8657C40ABE` (`containers_id`),
  KEY `FK1BA62F861FB22672` (`containerTranslations_id`),
  CONSTRAINT `FK1BA62F861FB22672` FOREIGN KEY (`containerTranslations_id`) REFERENCES `container_translation` (`id`),
  CONSTRAINT `FK1BA62F8657C40ABE` FOREIGN KEY (`containers_id`) REFERENCES `containers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content_translations`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content_translations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contentId` bigint(20) DEFAULT NULL,
  `language_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF10565E89F03C503` (`language_id`),
  CONSTRAINT `FKF10565E89F03C503` FOREIGN KEY (`language_id`) REFERENCES `globalize_languages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contents`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contents` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` bigint(20) NOT NULL,
  `deletedOn` datetime DEFAULT NULL,
  `filename` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mimesubtype` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mimetype` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `modifiedOn` bigint(20) DEFAULT NULL,
  `publishedOn` datetime DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_bin NOT NULL,
  `typeId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `waveId` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `accessLists_id` bigint(20) DEFAULT NULL,
  `container_id` bigint(20) DEFAULT NULL,
  `language_id` bigint(20) NOT NULL,
  `lastRevision_id` bigint(20) DEFAULT NULL,
  `license_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDE2F5B1AF8E388F` (`accessLists_id`),
  KEY `FKDE2F5B1AFC54C30F` (`lastRevision_id`),
  KEY `FKDE2F5B1A1FE0D12F` (`license_id`),
  KEY `FKDE2F5B1A622077EF` (`container_id`),
  KEY `FKDE2F5B1A9F03C503` (`language_id`),
  CONSTRAINT `FKDE2F5B1A9F03C503` FOREIGN KEY (`language_id`) REFERENCES `globalize_languages` (`id`),
  CONSTRAINT `FKDE2F5B1A1FE0D12F` FOREIGN KEY (`license_id`) REFERENCES `licenses` (`id`),
  CONSTRAINT `FKDE2F5B1A622077EF` FOREIGN KEY (`container_id`) REFERENCES `containers` (`id`),
  CONSTRAINT `FKDE2F5B1AF8E388F` FOREIGN KEY (`accessLists_id`) REFERENCES `access_lists` (`id`),
  CONSTRAINT `FKDE2F5B1AFC54C30F` FOREIGN KEY (`lastRevision_id`) REFERENCES `revisions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contents_content_translations`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contents_content_translations` (
  `contents_id` bigint(20) NOT NULL,
  `translations_id` bigint(20) NOT NULL,
  UNIQUE KEY `translations_id` (`translations_id`),
  KEY `FKF5E4F9ED8B538FEE` (`contents_id`),
  KEY `FKF5E4F9ED879058DB` (`translations_id`),
  CONSTRAINT `FKF5E4F9ED879058DB` FOREIGN KEY (`translations_id`) REFERENCES `content_translations` (`id`),
  CONSTRAINT `FKF5E4F9ED8B538FEE` FOREIGN KEY (`contents_id`) REFERENCES `contents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contents_kusers`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contents_kusers` (
  `contents_id` bigint(20) NOT NULL,
  `authors_id` bigint(20) NOT NULL,
  KEY `FK956FE9428B538FEE` (`contents_id`),
  KEY `FK956FE94298059548` (`authors_id`),
  CONSTRAINT `FK956FE94298059548` FOREIGN KEY (`authors_id`) REFERENCES `kusers` (`id`),
  CONSTRAINT `FK956FE9428B538FEE` FOREIGN KEY (`contents_id`) REFERENCES `contents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customproperties`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customproperties` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ext_media_descriptors`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ext_media_descriptors` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `detectRegex` varchar(255) COLLATE utf8_bin NOT NULL,
  `embedTemplate` longtext COLLATE utf8_bin NOT NULL,
  `height` int(11) NOT NULL,
  `idRegex` varchar(255) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `siteurl` varchar(255) COLLATE utf8_bin NOT NULL,
  `width` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `globalize_countries`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalize_countries` (
  `id` bigint(20) NOT NULL,
  `code` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `currency_code` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `currency_decimal_sep` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `currency_format` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `date_format` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `decimal_sep` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `english_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `number_grouping_scheme` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `thousands_sep` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `globalize_languages`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalize_languages` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `date_format` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `date_format_short` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `direction` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `english_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `english_name_locale` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `english_name_modifier` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `iso_639_1` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `iso_639_2` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `iso_639_3` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `macro_language` bit(1) DEFAULT NULL,
  `native_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `native_name_locale` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `native_name_modifier` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `pluralization` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `rfc_3066` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `scope` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `iso_639_2` (`iso_639_2`),
  UNIQUE KEY `iso_639_3` (`iso_639_3`),
  UNIQUE KEY `rfc_3066` (`rfc_3066`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `globalize_translations`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalize_translations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `facet` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `noteForTranslators` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `pluralization_index` int(11) DEFAULT NULL,
  `table_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `text` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tr_key` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gtype` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `language_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKCB245A905E56098` (`parent_id`),
  KEY `FKCB245A909F03C503` (`language_id`),
  CONSTRAINT `FKCB245A909F03C503` FOREIGN KEY (`language_id`) REFERENCES `globalize_languages` (`id`),
  CONSTRAINT `FKCB245A905E56098` FOREIGN KEY (`parent_id`) REFERENCES `globalize_translations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_list`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_list_groups`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_list_groups` (
  `group_list_id` bigint(20) NOT NULL,
  `list_id` bigint(20) NOT NULL,
  PRIMARY KEY (`group_list_id`,`list_id`),
  KEY `FK531B66D5B075E82E` (`group_list_id`),
  KEY `FK531B66D5472D9F30` (`list_id`),
  CONSTRAINT `FK531B66D5472D9F30` FOREIGN KEY (`list_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FK531B66D5B075E82E` FOREIGN KEY (`group_list_id`) REFERENCES `group_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admissionType` varchar(255) COLLATE utf8_bin NOT NULL,
  `backgroundImage` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `backgroundMime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `createdOn` bigint(20) NOT NULL,
  `groupType` varchar(255) COLLATE utf8_bin NOT NULL,
  `logo` longblob,
  `mimesubtype` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mimetype` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `longName` varchar(50) COLLATE utf8_bin NOT NULL,
  `shortName` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `workspaceTheme` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `defaultContent_id` bigint(20) DEFAULT NULL,
  `defaultLicense_id` bigint(20) DEFAULT NULL,
  `socialNetwork_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `longName` (`longName`),
  UNIQUE KEY `shortName` (`shortName`),
  KEY `FKB63DD9D44E0DFF30` (`defaultContent_id`),
  KEY `FKB63DD9D43EC9394F` (`socialNetwork_id`),
  KEY `FKB63DD9D4BBB28A30` (`defaultLicense_id`),
  CONSTRAINT `FKB63DD9D4BBB28A30` FOREIGN KEY (`defaultLicense_id`) REFERENCES `licenses` (`id`),
  CONSTRAINT `FKB63DD9D43EC9394F` FOREIGN KEY (`socialNetwork_id`) REFERENCES `social_networks` (`id`),
  CONSTRAINT `FKB63DD9D44E0DFF30` FOREIGN KEY (`defaultContent_id`) REFERENCES `contents` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groups_tool_configurations`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups_tool_configurations` (
  `groups_id` bigint(20) NOT NULL,
  `toolsConfig_id` bigint(20) NOT NULL,
  `mapkey` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`groups_id`,`mapkey`),
  UNIQUE KEY `toolsConfig_id` (`toolsConfig_id`),
  KEY `FK1CDF00D9E3FE623A` (`groups_id`),
  KEY `FK1CDF00D94B11FAD0` (`toolsConfig_id`),
  CONSTRAINT `FK1CDF00D94B11FAD0` FOREIGN KEY (`toolsConfig_id`) REFERENCES `tool_configurations` (`id`),
  CONSTRAINT `FK1CDF00D9E3FE623A` FOREIGN KEY (`groups_id`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kusers`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kusers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` bigint(20) NOT NULL,
  `diggest` longblob NOT NULL,
  `email` varchar(255) COLLATE utf8_bin NOT NULL,
  `emailCheckDate` bigint(20) DEFAULT NULL,
  `emailConfirmHash` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `emailNotifFreq` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `emailVerified` bit(1) DEFAULT NULL,
  `lastLogin` bigint(20) DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(40) COLLATE utf8_bin NOT NULL,
  `sNetVisibility` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `salt` longblob NOT NULL,
  `shortName` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `timezone` varchar(255) COLLATE utf8_bin NOT NULL,
  `country_id` bigint(20) NOT NULL,
  `language_id` bigint(20) NOT NULL,
  `userGroup_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `emailConfirmHash` (`emailConfirmHash`),
  UNIQUE KEY `shortName` (`shortName`),
  KEY `FKBD3D187D28E3727A` (`userGroup_id`),
  KEY `FKBD3D187D61C72291` (`country_id`),
  KEY `FKBD3D187D9F03C503` (`language_id`),
  CONSTRAINT `FKBD3D187D9F03C503` FOREIGN KEY (`language_id`) REFERENCES `globalize_languages` (`id`),
  CONSTRAINT `FKBD3D187D28E3727A` FOREIGN KEY (`userGroup_id`) REFERENCES `groups` (`id`),
  CONSTRAINT `FKBD3D187D61C72291` FOREIGN KEY (`country_id`) REFERENCES `globalize_countries` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `licenses`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `licenses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `imageUrl` varchar(255) COLLATE utf8_bin NOT NULL,
  `isCC` bit(1) NOT NULL,
  `isCopyleft` bit(1) NOT NULL,
  `isDeprecated` bit(1) NOT NULL,
  `longName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `rdf` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `shortName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `longName` (`longName`),
  UNIQUE KEY `shortName` (`shortName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rates`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rates` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` bigint(20) NOT NULL,
  `value` double DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `rater_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `content_id` (`content_id`,`rater_id`),
  KEY `FK6744F93B23C462F` (`content_id`),
  KEY `FK6744F93AAD99BE` (`rater_id`),
  CONSTRAINT `FK6744F93AAD99BE` FOREIGN KEY (`rater_id`) REFERENCES `kusers` (`id`),
  CONSTRAINT `FK6744F93B23C462F` FOREIGN KEY (`content_id`) REFERENCES `contents` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `revisions`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revisions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` longtext COLLATE utf8_bin,
  `createdOn` bigint(20) NOT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `version` int(11) NOT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `editor_id` bigint(20) DEFAULT NULL,
  `previous_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1E2243F8B23C462F` (`content_id`),
  KEY `FK1E2243F882CA86C9` (`previous_id`),
  KEY `FK1E2243F8831BB603` (`editor_id`),
  CONSTRAINT `FK1E2243F8831BB603` FOREIGN KEY (`editor_id`) REFERENCES `kusers` (`id`),
  CONSTRAINT `FK1E2243F882CA86C9` FOREIGN KEY (`previous_id`) REFERENCES `revisions` (`id`),
  CONSTRAINT `FK1E2243F8B23C462F` FOREIGN KEY (`content_id`) REFERENCES `contents` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `social_networks`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_networks` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `visibility` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `accessLists_id` bigint(20) DEFAULT NULL,
  `pendingCollaborators_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7E961097F8E388F` (`accessLists_id`),
  KEY `FK7E96109764FC5696` (`pendingCollaborators_id`),
  CONSTRAINT `FK7E96109764FC5696` FOREIGN KEY (`pendingCollaborators_id`) REFERENCES `group_list` (`id`),
  CONSTRAINT `FK7E961097F8E388F` FOREIGN KEY (`accessLists_id`) REFERENCES `access_lists` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_user_content`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_user_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` bigint(20) NOT NULL,
  `content_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK38BE208AB23C462F` (`content_id`),
  KEY `FK38BE208AE8AF66E5` (`user_id`),
  KEY `FK38BE208A4F22390F` (`tag_id`),
  CONSTRAINT `FK38BE208A4F22390F` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  CONSTRAINT `FK38BE208AB23C462F` FOREIGN KEY (`content_id`) REFERENCES `contents` (`id`),
  CONSTRAINT `FK38BE208AE8AF66E5` FOREIGN KEY (`user_id`) REFERENCES `kusers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdOn` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tool_configurations`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tool_configurations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `root_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDB4DCBC434BBDBEE` (`root_id`),
  CONSTRAINT `FKDB4DCBC434BBDBEE` FOREIGN KEY (`root_id`) REFERENCES `containers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-05 21:18:23
