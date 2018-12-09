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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega`
--

LOCK TABLES `entrega` WRITE;
/*!40000 ALTER TABLE `entrega` DISABLE KEYS */;
INSERT INTO `entrega` VALUES (1,'Spiderman vs Venom','2005-02-11'),(2,'Lobezno Inmortal','1978-12-30'),(3,'Vatmóvil','2018-11-06'),(4,'Superman sin capa','2015-01-15');
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
INSERT INTO `has_entrega` VALUES (1,1,1),(2,3,2),(3,2,3),(4,4,4);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `has_usuario`
--

LOCK TABLES `has_usuario` WRITE;
/*!40000 ALTER TABLE `has_usuario` DISABLE KEYS */;
INSERT INTO `has_usuario` VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,4),(5,2,5),(6,2,6);
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
  `imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serie`
--

LOCK TABLES `serie` WRITE;
/*!40000 ALTER TABLE `serie` DISABLE KEYS */;
INSERT INTO `serie` VALUES (1,'Spiderman','Acción','El hombre araña',9,'https://as00.epimg.net/meristation/imagenes/2018/08/02/noticias/1533216415_668360_1533216608_noticia_normal.jpg'),(2,'Lobezno','X-Men','Wolverine',7,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQLb819iscOFqiJJeuUZPP029icPf8WNygzOHr2E8f2ERYd1ZO'),(3,'Batman','DC','Podrá Batman parar al Joker',10,'http://s3-eu-west-1.amazonaws.com/cinemania-cdn/wp-content/uploads/2016/07/12133917/vNlsRbF-660x374.jpg'),(4,'Tintin','Aventuras','Tintin y Milú se embarcan en una nueva aventura',7,'https://www.zendalibros.com/wp-content/uploads/2018/03/tintin-y-milu.jpg'),(5,'Superman','Acción','¿Es un pájaro, es un avión...?',1,'https://media.vandal.net/i/1200x630/6-2018/201865121842_2.jpg'),(6,'Deadpool','Antihéroe','El antihéro más loco',9,'https://cronicaglobal.elespanol.com/uploads/s1/25/60/75/4/deadpool.jpeg');
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
  `conectado` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Eduardo Guidet Jiménez','https://lh5.googleusercontent.com/-xl39n_OP18o/AAAAAAAAAAI/AAAAAAAAAGo/iv2Kvjy2TQ0/s96-c/photo.jpg','eduardogj96@gmail.com',1),(2,'Carlos Canales','https://i2.wp.com/content.invisioncic.com/Mevernote/monthly_2017_08/C.png.7f930ab0dcf675996aaea7002ffbede7.png','pruebaparaingweb@gmail.com',0);
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

-- Dump completed on 2018-12-09 13:47:42
