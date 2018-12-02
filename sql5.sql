CREATE DATABASE  IF NOT EXISTS `iweb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `iweb`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: iweb
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `entrega`
--

DROP TABLE IF EXISTS `entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entrega` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `anotacion` varchar(255) DEFAULT NULL,
  `fecha_entrega` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega`
--

LOCK TABLES `entrega` WRITE;
/*!40000 ALTER TABLE `entrega` DISABLE KEYS */;
INSERT INTO `entrega` VALUES (1,'Venom','1996-10-10'),(2,'La venganza de Milú','2005-08-05'),(3,'Los Vengadores','2018-11-03'),(4,'Robin el torpe','2009-07-12'),(5,'Sketch Mortadelo ','2018-11-23'),(6,'Mortadelo Disfrazado','2017-04-01');
/*!40000 ALTER TABLE `entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `has_entrega`
--

DROP TABLE IF EXISTS `has_entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `has_entrega` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idSerie` int(11) NOT NULL,
  `idEntrega` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_has_entrega_serie_idx` (`idSerie`),
  KEY `FK_has_entrega_entrega_idx` (`idEntrega`),
  CONSTRAINT `FK_has_entrega_entrega` FOREIGN KEY (`idEntrega`) REFERENCES `entrega` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_has_entrega_serie` FOREIGN KEY (`idSerie`) REFERENCES `serie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has_entrega`
--

LOCK TABLES `has_entrega` WRITE;
/*!40000 ALTER TABLE `has_entrega` DISABLE KEYS */;
INSERT INTO `has_entrega` VALUES (1,1,1),(2,3,2),(3,2,3),(4,4,4),(5,7,5),(6,7,6);
/*!40000 ALTER TABLE `has_entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `has_usuario`
--

DROP TABLE IF EXISTS `has_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `has_usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) NOT NULL,
  `idSerie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_has_usuario_serie_idx` (`idSerie`),
  KEY `FK_has_usuario_usuario_idx` (`idUsuario`),
  CONSTRAINT `FK_has_usuario_serie` FOREIGN KEY (`idSerie`) REFERENCES `serie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_has_usuario_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has_usuario`
--

LOCK TABLES `has_usuario` WRITE;
/*!40000 ALTER TABLE `has_usuario` DISABLE KEYS */;
INSERT INTO `has_usuario` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,5),(6,1,6),(7,2,7);
/*!40000 ALTER TABLE `has_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serie`
--

DROP TABLE IF EXISTS `serie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) NOT NULL,
  `categoria` varchar(255) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `valoracion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serie`
--

LOCK TABLES `serie` WRITE;
/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
INSERT INTO `serie` VALUES (1,'Spiderman','Acción','El hombre que araña',7),(2,'Hulk','Acción','¿Será capaz Hulk de hacer frente a Loki?',7),(3,'TinTin','Aventuras','TinTin se adentrará en la base espacial de la URSS',7),(4,'Batman','Acción','El Caballero Oscuro ha vuelto',10),(5,'Oliver y Benji','Deportes','¿Meterán gol por fin?',3),(6,'Deadpool','Antihéroes','El antihéroe más enfermo de todos',8),(7,'Mortadelo y Filemón','Comedia','La T.I.A está corrupta',5);
/*!40000 ALTER TABLE `serie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `urlFoto` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Eduardo Guidet','https://lh3.googleusercontent.com/-_CRwqxCRWE0/WnciaVd46SI/AAAAAAAAAG4/2YWB17Adr0wlBVpeuHfdQDbmD3XT3jcMQCEwYBhgL/w140-h140-p/AAMAAgDuAAgAAQAAAAAAABAoAAAAJGUzNzI2MTAzLTEwM2MtNDcwYi1iNmJhLTVmZjhmMTBiYjgyYQ.bin.jpg','eduardogj96@gmail.com'),(2,'Carlos Canales','https://i2.wp.com/content.invisioncic.com/Mevernote/monthly_2017_08/C.png.7f930ab0dcf675996aaea7002ffbede7.png','pruebaparaingweb@gmail.com');
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

-- Dump completed on 2018-12-02 12:42:45
