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

-- Export de la structure de la base pour codebreak_game
CREATE DATABASE IF NOT EXISTS `codebreak_game` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `codebreak_game`;


-- Export de la structure de table codebreak_game. account
CREATE TABLE IF NOT EXISTS `account` (
  `Id` bigint(20) NOT NULL,
  `Nickname` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Export de données de la table codebreak_game.account: ~0 rows (environ)
DELETE FROM `account`;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
