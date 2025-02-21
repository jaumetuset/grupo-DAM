-- MySQL dump 10.13  Distrib 8.0.41, for Linux (x86_64)
--
-- Host: localhost    Database: adt_grupoA
-- ------------------------------------------------------
-- Server version	8.0.41-0ubuntu0.22.04.1

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
-- Current Database: `adt_grupoA`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `adt_grupoA` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `adt_grupoA`;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Articulos que utilizan algun motor de combustion','Motor'),(2,'Articulos que utilizan algun motor de combustion','clase');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `foto`
--

DROP TABLE IF EXISTS `foto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `producto_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKh2tmp56onjuf25p2186khs7v1` (`producto_id`),
  CONSTRAINT `FKomlrc9y9utqxavpbjk1yocuef` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foto`
--

LOCK TABLES `foto` WRITE;
/*!40000 ALTER TABLE `foto` DISABLE KEYS */;
INSERT INTO `foto` VALUES (1,'http://api.grupoa.com:8080/MyApp/uploads/1739918834492_cuenta-android.png',5),(2,'http://api.grupoa.com:8080/MyApp/uploads/1739919601606_upload_resized.jpg',10),(4,'http://api.grupoa.com:8080/MyApp/uploads/1739920125898_upload_resized.jpg',11),(5,'http://api.grupoa.com:8080/MyApp/uploads/1739965752878_upload_resized.jpg',12),(6,'http://api.grupoa.com:8080/MyApp/uploads/1740052695832_upload_resized.jpg',13);
/*!40000 ALTER TABLE `foto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `antiquity` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `maps` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `categoria` int NOT NULL,
  `usuario_id` int NOT NULL,
  `imagenes` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKydg56fw3q3f2lfcvtpkt36ls` (`categoria`),
  KEY `FK4f8g2yvj0uj7hqxlauy8p8k39` (`usuario_id`),
  CONSTRAINT `FK4f8g2yvj0uj7hqxlauy8p8k39` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKydg56fw3q3f2lfcvtpkt36ls` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `producto_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (2,'5 years','ODOO',NULL,NULL,'PC BBBBBBBBBBBBB',0,2,11,NULL),(5,'2 years','cadira',NULL,NULL,'silla',39,1,19,NULL),(6,'jj','oso',NULL,NULL,'Impresora ',64,2,8,NULL),(10,'bdvzb','vdvsbsbnv',NULL,NULL,'hdhsh',69,1,8,NULL),(11,'3 anys','cane',NULL,NULL,'canela',2000,1,19,NULL),(12,'20 días','Nintendo Switch',NULL,NULL,'Nintendo',20,2,8,NULL),(13,'3 Meses','Yyy',NULL,NULL,'Jonathan ',33,2,8,NULL);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `admin` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `imagen` longblob,
  `poblacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,_binary '\0','viaaaaaaaaaaaacentbena005@gmail.com','Vicent','1234556788aaa',NULL,'Aiacor'),(5,_binary '\0','viaaaaaaaaaaaaaaacentbena005@gmail.com','Vicent','1234556788aaa',NULL,'Aiacor'),(7,_binary '\0','vicentbenavent2005@gmail.com','Vicent','1234556788aaa',NULL,'Aiacor'),(8,_binary '\0','lucia@gmail.com','Luci','12345678o',NULL,'Xativa'),(9,_binary '\0','jaume@gmail.com','jaume','12344321a',NULL,'Llosa '),(11,_binary '\0','vicentbenavent2005@gmail.com','Vicent','1234556788aaa',NULL,'Aiacor'),(15,_binary '\0','jaumetuset@gmail.com','jaume','12345654asdasd',NULL,'toledo'),(16,_binary '\0','pererarpa2@gmail.com','peprer','123456789012asdasd',NULL,'teruel'),(17,_binary '\0','vicentebosque@gmail.com','vicenta','vicentebosque1234',NULL,'toledo'),(18,_binary '\0','viaaaaaaaaaaaaaaaaaacentbena005@gmail.com','Vicent','1234556788aaa',NULL,'Aiacor'),(19,_binary '\0','canela@gmail.com','canela','123456678a',NULL,'koko'),(20,_binary '\0','pautortosa@gmail.com','Viruhh','12345678o',NULL,'Canals'),(21,_binary '\0','perepepepe@gmail.com','pere ','123456712',NULL,'alcudia');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-20 13:24:34
