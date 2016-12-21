-- MySQL dump 10.13  Distrib 5.7.16, for Linux (x86_64)
--
-- Host: localhost    Database: TARJETA
-- ------------------------------------------------------
-- Server version	5.6.28

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
-- Table structure for table `canjeos`
--

DROP TABLE IF EXISTS `canjeos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `canjeos` (
  `idcanjeos` int(11) NOT NULL AUTO_INCREMENT,
  `idPremio` int(11) DEFAULT NULL,
  `idTarjeta` int(11) DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcanjeos`),
  KEY `prem_idx` (`idPremio`),
  KEY `tarj_idx` (`idTarjeta`),
  CONSTRAINT `prem` FOREIGN KEY (`idPremio`) REFERENCES `premios` (`idPremios`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tarj` FOREIGN KEY (`idTarjeta`) REFERENCES `tarjeta` (`idTarjeta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canjeos`
--

LOCK TABLES `canjeos` WRITE;
/*!40000 ALTER TABLE `canjeos` DISABLE KEYS */;
/*!40000 ALTER TABLE `canjeos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `idTarjeta` int(11) DEFAULT '0',
  `nick` varchar(45) NOT NULL,
  `pass` varchar(100) NOT NULL,
  PRIMARY KEY (`idCliente`),
  KEY `idTarjeta_idx` (`idTarjeta`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (64,19,'mendi','8f0e2f76e22b43e2855189877e7dc1e1e7d98c226c95db247cd1d547928334a9'),(65,0,'admin','8f0e2f76e22b43e2855189877e7dc1e1e7d98c226c95db247cd1d547928334a9');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compras` (
  `idCompras` int(11) NOT NULL AUTO_INCREMENT,
  `idTienda` int(11) DEFAULT NULL,
  `idTarjeta` int(11) DEFAULT NULL,
  `importe` double NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idCompras`),
  KEY `idTienda_idx` (`idTienda`),
  KEY `idTarjeta_idx` (`idTarjeta`),
  KEY `idTienda_idx1` (`idTienda`),
  KEY `idTarjeta_idx1` (`idTarjeta`),
  CONSTRAINT `Tarjeta` FOREIGN KEY (`idTarjeta`) REFERENCES `tarjeta` (`idTarjeta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Tienda` FOREIGN KEY (`idTienda`) REFERENCES `tienda` (`idTienda`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premios`
--

DROP TABLE IF EXISTS `premios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `premios` (
  `idPremios` int(11) NOT NULL AUTO_INCREMENT,
  `concepto` varchar(45) NOT NULL,
  `cantidad` varchar(45) NOT NULL,
  `puntosNecesarois` int(11) NOT NULL,
  PRIMARY KEY (`idPremios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premios`
--

LOCK TABLES `premios` WRITE;
/*!40000 ALTER TABLE `premios` DISABLE KEYS */;
/*!40000 ALTER TABLE `premios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta`
--

DROP TABLE IF EXISTS `tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta` (
  `idTarjeta` int(11) NOT NULL AUTO_INCREMENT,
  `nombreCompleto` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `puntos` int(11) DEFAULT '0',
  PRIMARY KEY (`idTarjeta`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta`
--

LOCK TABLES `tarjeta` WRITE;
/*!40000 ALTER TABLE `tarjeta` DISABLE KEYS */;
INSERT INTO `tarjeta` VALUES (19,'mendi','66666666','a','aa',0);
/*!40000 ALTER TABLE `tarjeta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tienda`
--

DROP TABLE IF EXISTS `tienda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tienda` (
  `idTienda` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idTienda`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tienda`
--

LOCK TABLES `tienda` WRITE;
/*!40000 ALTER TABLE `tienda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tienda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'TARJETA'
--

--
-- Dumping routines for database 'TARJETA'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-21 15:27:40
