-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 17, 2016 at 10:54 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `watchandrate`
--

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `COMMENT_CONTENT` text,
  `HASURL` tinyint(1) DEFAULT NULL,
  `COMMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `REVIEW_ID` int(11) NOT NULL,
  PRIMARY KEY (`COMMENT_ID`),
  KEY `FK_HAVE` (`REVIEW_ID`),
  KEY `FK_MAKE` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `comment_notification`
--

CREATE TABLE IF NOT EXISTS `comment_notification` (
  `NOTIFICATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID_SENDER` int(11) NOT NULL,
  `USER_ID_RECIEVER` int(11) NOT NULL,
  `RECIEVED` tinyint(1) DEFAULT NULL,
  `REVIEW_ID` int(11) NOT NULL,
  `RATE_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE IF NOT EXISTS `movie` (
  `MOVIENAME` char(100) DEFAULT NULL,
  `TYPE` char(50) DEFAULT NULL,
  `YEAR` char(50) DEFAULT NULL,
  `DESCRIPTION` text,
  `MOVIEIMAGE` varchar(250) DEFAULT NULL,
  `RATESYSTEM` double DEFAULT NULL,
  `MOVIE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `API_ID` int(11) NOT NULL,
  `Story` int(11) DEFAULT '0',
  `Direction` int(11) DEFAULT '0',
  `Acting` int(11) DEFAULT '0',
  `Motion` int(11) DEFAULT '0',
  `Music` int(11) DEFAULT '0',
  `VOTE_COUNT` int(11) DEFAULT '0',
  PRIMARY KEY (`MOVIE_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`MOVIENAME`, `TYPE`, `YEAR`, `DESCRIPTION`, `MOVIEIMAGE`, `RATESYSTEM`, `MOVIE_ID`, `API_ID`, `Story`, `Direction`, `Acting`, `Motion`, `Music`, `VOTE_COUNT`) VALUES
('Home', 'none', '2015-03-18', 'When Earth is taken over by the overly-confident Boov, an alien race in search of a new place to call home, all humans are promptly relocated, while all Boov get busy reorganizing the planet. But when one resourceful girl, Tip (Rihanna), manages to avoid capture, she finds herself the accidental accomplice of a banished Boov named Oh (Jim Parsons). The two fugitives realize there’s a lot more at stake than intergalactic relations as they embark on the road trip of a lifetime.', '/nIvfqZkq4nQmmIwOowRgBLhWoDd.jpg', 6.87, 3, 228161, 0, 0, 0, 0, 0, 1235);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `USER_ID_SENDER` int(11) NOT NULL,
  `USER_ID_RECIEVER` int(11) NOT NULL,
  `RECIEVED` tinyint(1) DEFAULT NULL,
  `NOTIFICATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REVIEW_ID` int(11) NOT NULL,
  `RATE_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`),
  KEY `FK_CONTAIN` (`RATE_ID`),
  KEY `FK_NOTIFY` (`REVIEW_ID`),
  KEY `FK_OWNS` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `rate`
--

CREATE TABLE IF NOT EXISTS `rate` (
  `RATENUM` int(11) DEFAULT NULL,
  `RATE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `REVIEW_ID` int(11) NOT NULL,
  `NOTIFICATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RATE_ID`),
  KEY `FK_CONTAIN2` (`NOTIFICATION_ID`),
  KEY `FK_DORATE` (`USER_ID`),
  KEY `FK_HAVE_RATES` (`REVIEW_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `rate_notification`
--

CREATE TABLE IF NOT EXISTS `rate_notification` (
  `NOTIFICATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID_SENDER` int(11) DEFAULT NULL,
  `USER_ID_RECIEVER` int(11) DEFAULT NULL,
  `RECIEVED` tinyint(1) DEFAULT NULL,
  `REVIEW_ID` int(11) DEFAULT NULL,
  `RATE_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE IF NOT EXISTS `review` (
  `TITLE` text,
  `REVIEW_CONTENT` text,
  `ENABLE_COMMENT` tinyint(1) DEFAULT NULL,
  `IMAGE_REVIEW` varchar(250) DEFAULT NULL,
  `STAR_RATE` float DEFAULT NULL,
  `REVIEW_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) DEFAULT NULL,
  `MOVIE_ID` int(11) DEFAULT NULL,
  `DATE` varchar(45) NOT NULL,
  PRIMARY KEY (`REVIEW_ID`),
  KEY `FK_DO` (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`TITLE`, `REVIEW_CONTENT`, `ENABLE_COMMENT`, `IMAGE_REVIEW`, `STAR_RATE`, `REVIEW_ID`, `USER_ID`, `MOVIE_ID`, `DATE`) VALUES
('7lw', 'azft :D', 1, 'none', 2, 1, 2, 228161, '2016-06-16'),
('7lw', 'azft :D', 1, 'none', 3, 2, 2, 228161, '2016-06-16'),
('7lw', 'azft :D', 1, 'none', 1.5, 3, 2, 228161, '2016-06-16'),
('7lw', 'azft :D', 1, 'none', 2.5, 4, 2, 228161, '2016-06-16'),
('7lw', 'azft :D', 1, 'none', 1, 5, 2, 228161, '2016-06-16'),
('7lw', 'azft :D', 1, 'none', 5, 6, 2, 228161, '2016-06-16'),
('7lw', 'azft :D', 1, 'none', 4, 7, 2, 228161, '2016-06-16');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `PASS` varchar(250) DEFAULT NULL,
  `USERNAME` varchar(250) DEFAULT NULL,
  `EMAIL` varchar(250) DEFAULT NULL,
  `USERIMAGE` varchar(250) NOT NULL DEFAULT 'none',
  `SCORE` float NOT NULL DEFAULT '0',
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`PASS`, `USERNAME`, `EMAIL`, `USERIMAGE`, `SCORE`, `USER_ID`) VALUES
('as', 'Hosam Azzam', 'sa', 'none', 0, 2),
('as', 'hosa', 'sa', 'none', 0, 3),
('123456', 'hosam', 'hos', 'none', 5, 4),
('dsf', 'edfr', 'gfd', 'none', 0, 5),
('dsf', 'edfr', 'gfd', 'none', 0, 6),
('as', 'as', 'ss', 'none', 0, 7);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK_HAVE` FOREIGN KEY (`REVIEW_ID`) REFERENCES `review` (`REVIEW_ID`),
  ADD CONSTRAINT `FK_MAKE` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `comment_notification`
--
ALTER TABLE `comment_notification`
  ADD CONSTRAINT `FK_TYPE1` FOREIGN KEY (`NOTIFICATION_ID`) REFERENCES `notification` (`NOTIFICATION_ID`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK_CONTAIN` FOREIGN KEY (`RATE_ID`) REFERENCES `rate` (`RATE_ID`),
  ADD CONSTRAINT `FK_NOTIFY` FOREIGN KEY (`REVIEW_ID`) REFERENCES `review` (`REVIEW_ID`),
  ADD CONSTRAINT `FK_OWNS` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `rate`
--
ALTER TABLE `rate`
  ADD CONSTRAINT `FK_CONTAIN2` FOREIGN KEY (`NOTIFICATION_ID`) REFERENCES `notification` (`NOTIFICATION_ID`),
  ADD CONSTRAINT `FK_DORATE` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `FK_HAVE_RATES` FOREIGN KEY (`REVIEW_ID`) REFERENCES `review` (`REVIEW_ID`);

--
-- Constraints for table `rate_notification`
--
ALTER TABLE `rate_notification`
  ADD CONSTRAINT `FK_TYPE2` FOREIGN KEY (`NOTIFICATION_ID`) REFERENCES `notification` (`NOTIFICATION_ID`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `FK_DO` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
