-- --------------------------------------------------------
-- Hôte:                         127.0.0.1
-- Version du serveur:           5.6.20 - MySQL Community Server (GPL)
-- Serveur OS:                   Win32
-- HeidiSQL Version:             9.0.0.4865
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Export de la structure de la base pour codebreak_login
DROP DATABASE IF EXISTS `codebreak_login`;
CREATE DATABASE IF NOT EXISTS `codebreak_login` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `codebreak_login`;


-- Export de la structure de table codebreak_login. account
DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `Nickname` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Power` int(11) DEFAULT '0',
  `CreationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `LastConnectionDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `LastConnectionIp` varchar(15) DEFAULT '?.?.?.?',
  `RemainingSubscription` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `BannedUntil` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Banned` bit(1) DEFAULT b'0',
  `SecretQuestion` varchar(50) DEFAULT 'secret',
  `SecretAnswer` varchar(50) DEFAULT 'secret',
  `Email` varchar(50) DEFAULT 'test@test.test',
  `Connected` bit(1) DEFAULT b'0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name` (`Name`),
  UNIQUE KEY `Nickname` (`Nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Export de données de la table codebreak_login.account: ~1 rows (environ)
DELETE FROM `account`;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`Id`, `Name`, `Nickname`, `Password`, `Power`, `CreationDate`, `LastConnectionDate`, `LastConnectionIp`, `RemainingSubscription`, `BannedUntil`, `Banned`, `SecretQuestion`, `SecretAnswer`, `Email`, `Connected`) VALUES
	(1, 'Smarken', 'Smarken', 'test', 0, '2016-06-03 13:26:47', '2016-06-03 13:26:47', '?.?.?.?', '2016-06-03 13:26:47', '2016-06-03 13:26:47', b'0', 'test', 'test', 'test@test.test', b'0');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


-- Export de la structure de table codebreak_login. gameservice
DROP TABLE IF EXISTS `gameservice`;
CREATE TABLE IF NOT EXISTS `gameservice` (
  `Id` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Ip` varchar(15) NOT NULL,
  `Port` int(11) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Export de données de la table codebreak_login.gameservice: ~1 rows (environ)
DELETE FROM `gameservice`;
/*!40000 ALTER TABLE `gameservice` DISABLE KEYS */;
INSERT INTO `gameservice` (`Id`, `Name`, `Ip`, `Port`) VALUES
	(1, 'Jiva', '127.0.0.1', 5556);
/*!40000 ALTER TABLE `gameservice` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
