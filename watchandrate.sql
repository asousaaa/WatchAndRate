-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Host: 127.12.5.2:3306
-- Generation Time: Jun 28, 2016 at 08:29 AM
-- Server version: 5.5.45
-- PHP Version: 5.3.3

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=31 ;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`COMMENT_CONTENT`, `HASURL`, `COMMENT_ID`, `USER_ID`, `REVIEW_ID`) VALUES
('no no file w7sh', 0, 1, 2, 10),
('no 7lw :P', 0, 2, 2, 10),
('ummmm ymkn', 1, 3, 3, 10),
('looooool xD', 1, 4, 2, 10),
('lool ', 0, 6, 3, 13),
('how ,afsh komntat 5als :(', 0, 7, 3, 13),
('loool a5ern', 0, 8, 3, 15),
('lool', 0, 9, 3, 16),
('hey guys', 0, 10, 9, 10),
('@Ess welcome', 0, 11, 2, 10),
('mabrook ^-^ ', 0, 12, 2, 20),
('hhhhh habl bcoment 3la nafse xD', 0, 13, 2, 20),
('hhhhhhhhh good work ', 0, 14, 9, 20),
('lool ', 0, 15, 3, 10),
('7elw awy', 0, 16, 4, 15),
('msh awi', 0, 19, 5, 12),
('msh awi', 0, 20, 2, 12),
('wl ya wl', 0, 21, 4, 12),
('w b3deeen', 0, 22, 6, 12),
('greats review ;)', 0, 23, 3, 22),
('nice go one ', 0, 24, 9, 22),
('This is a good movie', 0, 30, 2, 12);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`MOVIENAME`, `TYPE`, `YEAR`, `DESCRIPTION`, `MOVIEIMAGE`, `RATESYSTEM`, `MOVIE_ID`, `API_ID`, `Story`, `Direction`, `Acting`, `Motion`, `Music`, `VOTE_COUNT`) VALUES
('Home', 'none', '2015-03-18', 'When Earth is taken over by the overly-confident Boov, an alien race in search of a new place to call home, all humans are promptly relocated, while all Boov get busy reorganizing the planet. But when one resourceful girl, Tip (Rihanna), manages to avoid capture, she finds herself the accidental accomplice of a banished Boov named Oh (Jim Parsons). The two fugitives realize there’s a lot more at stake than intergalactic relations as they embark on the road trip of a lifetime.', '/nIvfqZkq4nQmmIwOowRgBLhWoDd.jpg', 6.87, 3, 228161, 0, 0, 0, 0, 0, 1235),
('Up', 'none', '2009-05-13', 'After a lifetime of dreaming of traveling the world, 78-year-old homebody Carl flies away on an unbelievable adventure with Russell, an 8-year-old Wilderness Explorer, unexpectedly in tow. Together, the unlikely pair embarks on a thrilling odyssey full of jungle beasts and rough terrain.', '/gfFqBcoFW8uczyl2ytVmVmUg82k.jpg', 7.54, 4, 14160, 0, 0, 0, 0, 0, 3374),
('Taken 3', 'none', '2015-01-01', 'Ex-government operative Bryan Mills finds his life is shattered when he''s falsely accused of a murder that hits close to home. As he''s pursued by a savvy police inspector, Mills employs his particular set of skills to track the real killer and exact his unique brand of justice.', '/ikDwR3i2bczqnRf1urJTy77YTFf.jpg', 6.12, 5, 260346, 0, 0, 0, 0, 0, 1350),
('Tom', 'none', '2016-01-14', 'An astronaut encounters a major problem upon returning to earth after a long space voyage.', 'null', 0, 6, 377750, 0, 0, 0, 0, 0, 0),
('Hello', 'none', '2014-01-01', 'When Max (Eric Stoltz), urged on by "Risk Management," a self-help book for the hapless, decides to approach his fellow ferry-commuter Rory (Susanna Thompson), he hopes simply saying hello might change his life for the better. But Rory only accepts contact by contract. Max finds he can play along. As the two negotiate a whirlwind relationship on paper, Rory slowly lets down her guard; but when her unresolved personal life intervenes in the form of Donald (Kevin Tighe), Max must manage a little more risk than he bargained on.', 'null', 3.5, 7, 299922, 0, 0, 0, 0, 0, 1),
('Gone Baby Gone', 'none', '2007-10-19', 'Two Boston area detectives investigate a little girl''s kidnapping, which ultimately turns into a crisis both professionally and personally. Based on the Dennis Lehane novel.', '/4Nxv90So2AHtaLBJ0UQrmex6aa.jpg', 6.95, 8, 4771, 0, 0, 0, 0, 0, 441);

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
  `COMMENT_ID` int(11) DEFAULT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`),
  KEY `FK_CONTAIN` (`COMMENT_ID`),
  KEY `FK_NOTIFY` (`REVIEW_ID`),
  KEY `FK_OWNS` (`USER_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`USER_ID_SENDER`, `USER_ID_RECIEVER`, `RECIEVED`, `NOTIFICATION_ID`, `REVIEW_ID`, `COMMENT_ID`, `USER_ID`) VALUES
(5, 3, 0, 1, 12, 19, 5),
(2, 3, 0, 2, 12, 20, 2),
(4, 3, 0, 3, 12, 21, 4),
(6, 3, 0, 4, 12, 22, 6),
(3, 13, 0, 5, 22, 23, 3),
(9, 13, 0, 6, 22, 24, 9),
(2, 3, 0, 7, 12, 30, 2);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`TITLE`, `REVIEW_CONTENT`, `ENABLE_COMMENT`, `IMAGE_REVIEW`, `STAR_RATE`, `REVIEW_ID`, `USER_ID`, `MOVIE_ID`, `DATE`) VALUES
('zaft ', 'zaften talata 4', 1, 'none', 3, 10, 3, 228161, '2016-06-20'),
('zaf test image', 'loo', 0, '316-41-3513428443_1539623752721796_4303214590424622777_n.png', 3, 12, 3, 228161, '2016-06-20'),
('lool', 'msm3tsh 3ano 5als', 1, 'none', 3, 13, 3, 377750, '2016-06-20'),
('twest', 'test ', 1, '318-34-20facepic11.png', 3, 14, 3, 377750, '2016-06-20'),
('test final test', 'uploade imge', 1, '323-44-0513428443_1539623752721796_4303214590424622777_n.png', 3, 15, 3, 377750, '2016-06-20'),
('test after solve', 'ya rab', 1, '300-03-55facepic8.png', 3, 16, 3, 377750, '2016-06-21'),
('loo', 'olll', 1, '300-28-2313432274_1372080062808956_4250377347112070594_n.jpg', 3, 17, 3, 377750, '2016-06-21'),
('test ', 'test', 1, '301-23-17facepic4.png', 3, 18, 3, 299922, '2016-06-21'),
('test sd', 'test new server', 0, '316-25-01facepic9.png', 3, 19, 3, 299922, '2016-06-21'),
('final review', 'a5erb nf3t :D ', 1, '217-25-50received_1062117490501409.jpeg', 2, 20, 2, 228161, '2016-06-21'),
('Will Keep You Debating On What Is Truly Right\n\n', 'I had wanted to see this movie for sometime now. I have finally gotten to see it and can tell you it is no disappointment. This movie had a particular touch that gave a genuine and authentic feeling to it.\n\nBen Affleck has a way of writing about life on the streets in Boston that just grabs you. Good Will Hunting was a fine example of that but Gone Baby Gone displayed his directing abilities as well. This movie appeared that it could fall apart any second but yet the story held up and managed to get its message out. Ben Affleck seems to be very creative and knows how to get your attention right when he want you to.\n', 1, 'none', 13, 22, 13, 4771, '2016-06-25');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`PASS`, `USERNAME`, `EMAIL`, `USERIMAGE`, `SCORE`, `USER_ID`) VALUES
('asd', 'Hosam Azzam', 'sa', '2received_1064696240243534.jpeg', 4, 2),
('as', 'hosam', 'sa', '3facepic13.png', 4.5, 3),
('123456', 'hosam', 'hos', 'none', 2, 4),
('dsf', 'islam', 'gfd', 'none', 3, 5),
('dsf', 'Ail', 'gfd', 'none', 2, 6),
('as', 'as', 'ss', 'none', 0, 7),
('esso', 'Safrota', 'safrota', '8facepic5.png', 3.5, 8),
('123', 'Ess', 'Ess', '9received_956006441112515.jpeg', 2.5, 9),
('123', 'ess', 'ess', 'none', 0, 10),
('8919661', 'HassaN', 'hassanmohmah@hotmail.com', 'none', 0, 11),
('12345', 'heba', 'heba@gmail.com', 'none', 0, 12),
('heba', 'heba', 'heba@gmail.com', 'none', 0, 13);

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
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `FK_CONTAIN` FOREIGN KEY (`COMMENT_ID`) REFERENCES `comment` (`COMMENT_ID`),
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
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `FK_DO` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
