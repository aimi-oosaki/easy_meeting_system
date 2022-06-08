-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: easy_meeting_system
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `agendas`
--

DROP TABLE IF EXISTS `agendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agendas` (
  `agenda_id` int NOT NULL AUTO_INCREMENT,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(250) NOT NULL,
  `meeting_id` int NOT NULL,
  PRIMARY KEY (`agenda_id`),
  KEY `FK5vyr2qorqkxqlsr0dutn6kfig` (`meeting_id`),
  CONSTRAINT `FK5vyr2qorqkxqlsr0dutn6kfig` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agendas`
--

LOCK TABLES `agendas` WRITE;
/*!40000 ALTER TABLE `agendas` DISABLE KEYS */;
INSERT INTO `agendas` VALUES (2,'あ','勤怠管理について',5),(3,'あ','来年度の新製品について',4),(4,'あ','来年度予算について',2),(5,'あ','各プロジェクトの進捗',1),(6,'あ','△△機能について',6),(7,'あ','××廃止について',6);
/*!40000 ALTER TABLE `agendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin_flag` int NOT NULL,
  `code` varchar(255) NOT NULL,
  `company_id` int NOT NULL,
  `delete_flag` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `team_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa5cxjw6yuqlbp0np1g51o03gf` (`team_id`),
  CONSTRAINT `FKa5cxjw6yuqlbp0np1g51o03gf` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,1,'1',1,0,'テスト太郎','EF356838DB5E67E62448F69D31D6E4B822AA13915B872B957276BEB85F2096C1',1),(2,0,'2',1,0,'テスト花子','6D103BBBC824445552E1AEEED41CC13EC16DB424DB6D6EC80AC50DD8DF4256CA',1),(3,1,'3',1,0,'テスト次郎','B022A9E4E37DC4260D228B6B5CB9EFAA31FB96159761106874F8D8EDECE5E7B3',1),(4,0,'4',1,0,'テスト美香','05F484FDDF3BDE4F0DE80BF59609417B121F38E55F19974E2C335C75BC6635E5',1);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ideas`
--

DROP TABLE IF EXISTS `ideas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ideas` (
  `idea_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL,
  `employee_id` int NOT NULL,
  `want_id` int NOT NULL,
  PRIMARY KEY (`idea_id`),
  KEY `FKgl9ia1hfbl3rn0wwi3bao84dw` (`employee_id`),
  KEY `FKsf05yj4mppq62non00p37d2x6` (`want_id`),
  CONSTRAINT `FKgl9ia1hfbl3rn0wwi3bao84dw` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKsf05yj4mppq62non00p37d2x6` FOREIGN KEY (`want_id`) REFERENCES `wants` (`want_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ideas`
--

LOCK TABLES `ideas` WRITE;
/*!40000 ALTER TABLE `ideas` DISABLE KEYS */;
INSERT INTO `ideas` VALUES (1,'XXはよく使われる機能であるため、画面トップに配置してはいかがでしょうか。',1,1);
/*!40000 ALTER TABLE `ideas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meetings`
--

DROP TABLE IF EXISTS `meetings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meetings` (
  `meeting_id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `delete_flag` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `team_id` int DEFAULT NULL,
  PRIMARY KEY (`meeting_id`),
  KEY `FK6mjivyoqkqlbs7y3clyfmes57` (`team_id`),
  CONSTRAINT `FK6mjivyoqkqlbs7y3clyfmes57` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meetings`
--

LOCK TABLES `meetings` WRITE;
/*!40000 ALTER TABLE `meetings` DISABLE KEYS */;
INSERT INTO `meetings` VALUES (1,'2022-06-25',0,'進捗会議',1),(2,'2022-06-27',0,'役員会議',1),(3,'2022-07-01',0,'定例会議',1),(4,'2022-07-09',0,'アイデア会議',1),(5,'2022-07-06',0,'課題解決会議',1),(6,'2022-07-21',0,'アイデア会議',1);
/*!40000 ALTER TABLE `meetings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minutes`
--

DROP TABLE IF EXISTS `minutes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `minutes` (
  `minute_id` int NOT NULL AUTO_INCREMENT,
  `attendees` varchar(50) NOT NULL,
  `decided` varchar(250) NOT NULL,
  `pending` varchar(255) NOT NULL,
  `employee_id` int NOT NULL,
  `meeting_id` int NOT NULL,
  PRIMARY KEY (`minute_id`),
  KEY `FKck50b56vusn52wfv6xth73xod` (`employee_id`),
  KEY `FKti7e7g48jlqckux5gk2joicf6` (`meeting_id`),
  CONSTRAINT `FKck50b56vusn52wfv6xth73xod` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKti7e7g48jlqckux5gk2joicf6` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`meeting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minutes`
--

LOCK TABLES `minutes` WRITE;
/*!40000 ALTER TABLE `minutes` DISABLE KEYS */;
INSERT INTO `minutes` VALUES (1,'テスト太郎、テスト花子','これ決定','これ保留',1,6),(2,'テスト太郎、テスト花子、テスト美香','これが決定しました','これは保留です',1,3);
/*!40000 ALTER TABLE `minutes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `want_id` int NOT NULL AUTO_INCREMENT,
  `due_date` date NOT NULL,
  `problem` varchar(400) DEFAULT NULL,
  `progress` varchar(20) NOT NULL,
  `solution` varchar(400) DEFAULT NULL,
  `status` int NOT NULL,
  `title` varchar(50) NOT NULL,
  `employee_id` int NOT NULL,
  `team_id` int NOT NULL,
  PRIMARY KEY (`want_id`),
  KEY `FKjc3xiile6e5jbtmywxw5vfm7f` (`employee_id`),
  KEY `FK7ohls81a92yq2hlgcml3h1atu` (`team_id`),
  CONSTRAINT `FK7ohls81a92yq2hlgcml3h1atu` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`),
  CONSTRAINT `FKjc3xiile6e5jbtmywxw5vfm7f` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (1,'2022-06-09','××機能の追加が期限内に終わるのか不明','1','△△社にこまめに連絡をする',0,'〇〇システム開発',1,1),(2,'2022-07-02','××機能の追加が期限内に終わるのか不明','1','△△社にこまめに連絡をする',0,'△△システム開発!',1,1),(3,'2022-08-19','△△の入荷が遅れる可能性あり!','3','△△の入荷が遅れる場合に備え、代わりの業者を探しておく!',0,'△△システム開発!',1,1),(4,'2022-08-25','XXの入荷が送れる可能性あり!','2','XXの入荷が送れる場合の代わりの業者の目星をつけておく!',0,'△△システム開発!',1,1),(6,'2022-09-14','人員不足','3','新規メンバーのアサインを検討してほしい',0,'■■システム',4,1);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teams`
--

DROP TABLE IF EXISTS `teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teams` (
  `team_id` int NOT NULL AUTO_INCREMENT,
  `company_id` int NOT NULL,
  `delete_flag` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teams`
--

LOCK TABLES `teams` WRITE;
/*!40000 ALTER TABLE `teams` DISABLE KEYS */;
INSERT INTO `teams` VALUES (1,1,0,'総務部'),(2,1,0,'開発部'),(3,1,0,'研究部'),(4,1,0,'人事部'),(5,1,0,'営業部'),(6,1,0,'マーケティング部');
/*!40000 ALTER TABLE `teams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `todos`
--

DROP TABLE IF EXISTS `todos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todos` (
  `todo_id` int NOT NULL AUTO_INCREMENT,
  `consequence` varchar(400) DEFAULT NULL,
  `deadline` date NOT NULL,
  `status` int NOT NULL,
  `what` varchar(200) NOT NULL,
  `employee_id` int NOT NULL,
  `meeting_id` int NOT NULL,
  `team_id` int NOT NULL,
  PRIMARY KEY (`todo_id`),
  KEY `FKacmtt1r5owa49tjpssakp7ct9` (`employee_id`),
  KEY `FK35fcyj5isja165cn2bywx7yqm` (`meeting_id`),
  KEY `FKf5fjjfkfk9f2fnm3y0mgkrie9` (`team_id`),
  CONSTRAINT `FK35fcyj5isja165cn2bywx7yqm` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`meeting_id`),
  CONSTRAINT `FKacmtt1r5owa49tjpssakp7ct9` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  CONSTRAINT `FKf5fjjfkfk9f2fnm3y0mgkrie9` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todos`
--

LOCK TABLES `todos` WRITE;
/*!40000 ALTER TABLE `todos` DISABLE KEYS */;
INSERT INTO `todos` VALUES (1,'あ','2022-07-15',1,'見積もりをもらう',1,6,1),(2,'あ','2022-07-19',0,'要件定義書を作成する',3,6,1),(3,'あ','2022-06-19',0,'〇〇さんに連絡する',2,6,1),(4,'あ','2022-06-23',0,'××さんに△△を聞く',3,6,1);
/*!40000 ALTER TABLE `todos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wants`
--

DROP TABLE IF EXISTS `wants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wants` (
  `want_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(250) NOT NULL,
  `due_date` date NOT NULL,
  `title` varchar(50) NOT NULL,
  `meeting_id` int NOT NULL,
  `team_id` int NOT NULL,
  PRIMARY KEY (`want_id`),
  KEY `FK5cjgndgvpknyst4ymhhs70yyv` (`meeting_id`),
  KEY `FKa2j5evub82pox6fnr5i5b7qen` (`team_id`),
  CONSTRAINT `FK5cjgndgvpknyst4ymhhs70yyv` FOREIGN KEY (`meeting_id`) REFERENCES `meetings` (`meeting_id`),
  CONSTRAINT `FKa2j5evub82pox6fnr5i5b7qen` FOREIGN KEY (`team_id`) REFERENCES `teams` (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wants`
--

LOCK TABLES `wants` WRITE;
/*!40000 ALTER TABLE `wants` DISABLE KEYS */;
INSERT INTO `wants` VALUES (1,'〇〇製品の〇〇について、XXが使いづらいとの声を多くいただいています。このXXをもっと使いやすくするためのアイデアをお願いします！','2022-07-02','〇〇商品について',6,1),(2,'〇〇機能をもっと使いやすくするにはどうすればいいでしょうか。アイデアをください！','2022-06-22','xx商品について',6,1);
/*!40000 ALTER TABLE `wants` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-08 20:00:10
