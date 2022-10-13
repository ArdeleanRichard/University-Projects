-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Host: sql11.freemysqlhosting.net
-- Generation Time: Mar 25, 2018 at 01:46 PM
-- Server version: 5.5.58-0ubuntu0.14.04.1
-- PHP Version: 7.0.28-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sql11228756`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `mail`, `password`, `name`) VALUES
(1, 'admin', 'admin', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE `game` (
  `id` int(11) NOT NULL,
  `matchid` int(11) DEFAULT NULL,
  `score1` int(11) DEFAULT '0',
  `score2` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`id`, `matchid`, `score1`, `score2`) VALUES
(644, 118, 0, 0),
(645, 118, 0, 0),
(646, 118, 0, 0),
(647, 118, 0, 0),
(648, 118, 0, 0),
(649, 119, 0, 0),
(650, 119, 0, 0),
(651, 119, 0, 0),
(652, 119, 0, 0),
(653, 119, 0, 0),
(654, 120, 0, 0),
(655, 120, 0, 0),
(656, 120, 0, 0),
(657, 120, 0, 0),
(658, 120, 0, 0),
(659, 121, 4, 0),
(660, 121, 0, 0),
(661, 121, 0, 0),
(662, 121, 0, 0),
(663, 121, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `match`
--

CREATE TABLE `match` (
  `id` int(11) NOT NULL,
  `tournamentID` int(11) DEFAULT NULL,
  `player1` int(11) DEFAULT NULL,
  `player2` int(11) DEFAULT NULL,
  `winner` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='	';

--
-- Dumping data for table `match`
--

INSERT INTO `match` (`id`, `tournamentID`, `player1`, `player2`, `winner`) VALUES
(118, 40, 6, 4, 0),
(119, 40, 2, 8, 0),
(120, 40, 3, 7, 0),
(121, 40, 5, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `id` int(11) NOT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `mail`, `password`, `name`) VALUES
(1, 'r', 'cg==', 'Rici'),
(2, 'x', 'eA==', 'Xander'),
(3, 'f', 'Zg==', 'Flaviu'),
(4, 'v', 'dg==', 'Victor'),
(5, 'c', 'Yw==', 'Ciubo'),
(6, 'a', 'YQ==', 'Anghel'),
(7, 'p', 'cA==', 'Peti'),
(8, 't', 'dA==', 'Toderut');

-- --------------------------------------------------------

--
-- Table structure for table `tournament`
--

CREATE TABLE `tournament` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Announced',
  `winner` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tournament`
--

INSERT INTO `tournament` (`id`, `name`, `status`, `winner`) VALUES
(33, 'Test', 'test', 1),
(34, 'Test', 'test', 4),
(36, 'test', 'test', 6),
(37, 'Test', 'test', 1),
(40, 'Test', 'test', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `match`
--
ALTER TABLE `match`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tournament`
--
ALTER TABLE `tournament`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `game`
--
ALTER TABLE `game`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=664;
--
-- AUTO_INCREMENT for table `match`
--
ALTER TABLE `match`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=122;
--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `tournament`
--
ALTER TABLE `tournament`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
