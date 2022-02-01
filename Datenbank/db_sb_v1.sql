-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 14. Okt 2021 um 23:33
-- Server-Version: 10.4.20-MariaDB
-- PHP-Version: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `db_sb_v1`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_end of_the_day`
--

CREATE TABLE `tb_end of_the_day` (
  `cl_id_end` int(11) NOT NULL,
  `cl_graduation_date` date NOT NULL,
  `cl_goods_nr` int(10) UNSIGNED NOT NULL,
  `cl_user_nr` int(10) UNSIGNED DEFAULT NULL,
  `cl_self_service_stand_nr` int(10) UNSIGNED NOT NULL,
  `cl_graduation_count` int(10) UNSIGNED DEFAULT NULL,
  `cl_graduation_sold` int(11) UNSIGNED DEFAULT NULL,
  `cl_graduation_revenue` decimal(6,2) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_goods`
--

CREATE TABLE `tb_goods` (
  `cl_goods_nr` int(11) UNSIGNED NOT NULL,
  `cl_goods_name_nr` int(30) UNSIGNED DEFAULT NULL,
  `cl_goods_unit_nr` int(11) UNSIGNED DEFAULT NULL,
  `cl_goods_price` decimal(4,2) UNSIGNED DEFAULT NULL,
  `cl_goods_note` varchar(200) NOT NULL,
  `cl_self_service_stand_nr` int(11) UNSIGNED DEFAULT NULL,
  `cl_goods_active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_goods`
--

INSERT INTO `tb_goods` (`cl_goods_nr`, `cl_goods_name_nr`, `cl_goods_unit_nr`, `cl_goods_price`, `cl_goods_note`, `cl_self_service_stand_nr`, `cl_goods_active`) VALUES
(308, 4, 13, '3.00', '', 20, 0),
(309, 4, 14, '0.20', '', 20, 0),
(311, 1, 13, '2.00', 'Sorte ', 20, 1),
(319, 1, 14, '5.00', '', 20, 0),
(320, 1, 8, '5.00', '', 20, 1),
(322, 2, 12, '1.50', '', 20, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_goods_name`
--

CREATE TABLE `tb_goods_name` (
  `cl_goods_name_nr` int(10) UNSIGNED NOT NULL,
  `cl_goods_name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_goods_name`
--

INSERT INTO `tb_goods_name` (`cl_goods_name_nr`, `cl_goods_name`) VALUES
(1, 'Kartoffel'),
(2, 'Kürbis'),
(4, 'Ei'),
(5, 'Möhren');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_receipt_goods`
--

CREATE TABLE `tb_receipt_goods` (
  `cl_goods_receipt_nr` int(11) NOT NULL,
  `cl_date_receipt` date DEFAULT NULL,
  `cl_goods_pieces` int(11) DEFAULT NULL,
  `cl_goods_nr` int(11) UNSIGNED DEFAULT NULL,
  `cl_user_nr` int(11) UNSIGNED DEFAULT NULL,
  `cl_self_service_stand_nr` int(11) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_receipt_goods`
--

INSERT INTO `tb_receipt_goods` (`cl_goods_receipt_nr`, `cl_date_receipt`, `cl_goods_pieces`, `cl_goods_nr`, `cl_user_nr`, `cl_self_service_stand_nr`) VALUES
(1, '2021-10-12', 2, 308, 20, 20),
(2, '2021-10-12', 8, 308, 20, 20),
(3, '2021-10-12', 3, 311, 20, 20),
(4, '2021-10-12', 3, 311, 20, 20),
(7, '2021-10-08', 1, 308, 20, 20),
(18, '2021-10-12', 180, 309, 20, 20),
(19, '2021-10-13', 8, 309, 20, 20),
(20, '2021-10-13', 8, 311, 20, 20);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_role`
--

CREATE TABLE `tb_role` (
  `cl_role_nr` int(3) UNSIGNED NOT NULL,
  `cl_role_name` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_role`
--

INSERT INTO `tb_role` (`cl_role_nr`, `cl_role_name`) VALUES
(1, 'Administrator'),
(2, 'Editor'),
(5, 'Super-Administrator');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_self_service_stand`
--

CREATE TABLE `tb_self_service_stand` (
  `cl_self_service_stand_nr` int(10) UNSIGNED NOT NULL,
  `cl_self_service_stand_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_self_service_stand`
--

INSERT INTO `tb_self_service_stand` (`cl_self_service_stand_nr`, `cl_self_service_stand_name`) VALUES
(8, 'Haus'),
(10, 'Radlstop'),
(11, 'Radlstopp'),
(19, 'Eierhütte'),
(20, '42'),
(21, 'Dall'),
(22, 'TestStand'),
(23, '56');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_self_service_stand_user_nr`
--

CREATE TABLE `tb_self_service_stand_user_nr` (
  `cl_self_service_stand_nr_id` int(11) NOT NULL,
  `cl_self_service_stand_nr` int(11) UNSIGNED NOT NULL,
  `cl_user_nr` int(10) UNSIGNED NOT NULL,
  `cl_role_nr` int(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_self_service_stand_user_nr`
--

INSERT INTO `tb_self_service_stand_user_nr` (`cl_self_service_stand_nr_id`, `cl_self_service_stand_nr`, `cl_user_nr`, `cl_role_nr`) VALUES
(12, 19, 11, 1),
(13, 20, 20, 1),
(14, 21, 21, 1),
(15, 22, 22, 1),
(16, 23, 23, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_unit`
--

CREATE TABLE `tb_unit` (
  `cl_unit_nr` int(11) UNSIGNED NOT NULL,
  `cl_unit_name` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_unit`
--

INSERT INTO `tb_unit` (`cl_unit_nr`, `cl_unit_name`) VALUES
(8, '5 KG'),
(12, 'Stück'),
(13, '500 g Packung'),
(14, 'Sack'),
(17, '500 g');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tb_user`
--

CREATE TABLE `tb_user` (
  `cl_user_nr` int(11) UNSIGNED NOT NULL,
  `cl_user_name` varchar(255) DEFAULT NULL,
  `cl_user_password` varchar(255) DEFAULT NULL,
  `cl_user_email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `tb_user`
--

INSERT INTO `tb_user` (`cl_user_nr`, `cl_user_name`, `cl_user_password`, `cl_user_email`) VALUES
(10, 'Hugo', '1234!!dsfsdfGGFD', 'test@web.de'),
(11, 'Heinz', '$2a$10$AG6rat4M2tBdxHb7g8rN/ufBvWZC2OzsdvJNRN38MwxU.shPKvMIK', 'test@web.de'),
(12, 'Hugo123', '$2a$10$flLekOZv8DtCe9Pm18/6P.0DzJ96hd/XJ90D3SiaImrAr1JCE46vm', 'test@web.de'),
(13, 'Hugo12348', '$2a$10$/4GoH7zNq0CTKmL0o4lIjeeA8Dd2ln09oQNwOyfyqGPquQ2yQv.bO', 'test@web.de'),
(14, 'Hugo1234854', '$2a$10$538/WYCvjtWX0mV7i94wZ.aFbmyBlfn5HCN2y.WQiMknZ5fyitqJe', 'Christine-Dall@web.de'),
(15, 'Hugo1234854777', '$2a$10$D/r2NhvWi2qbqAaalry6Cu389qIw5yqx3i7/dG3a6avRNSNo3sXAy', 'Christine-Dall@web.de'),
(16, 'Hugo123485774', '$2a$10$axdmP/6yYSOUt0927nP18O6VuMedG4zO6hhKNYHoZviARdNqItDOq', 'test@web.de'),
(17, 'Hugo123485411', '$2a$10$6KMlmGqvvWvWo6PHxND4/.iAFPV2CVeUgniGSdSmpUeFWqwVKqk.2', 'Christine-Dall@web.de'),
(18, 'Hugo123485477', '$2a$10$r4eqEnk5BafoKVCqokvfx.f9DgTRgZLcYgyFLF8ekOCZtYI22WBAu', 'Christine-Dall@web.de'),
(19, 'Hugo42', '$2a$10$l/C62u4Gnn9gTkxx9zpwPeO7RrHGNhUBOrQ5vtYPpzLrvOhfihEgK', 'Christine-Dall@web.de'),
(20, 'ChristineD', '$2a$10$tC7hEiwguxlm87Fi0ZLpWOBIkfKsu7LomoFEPmifrH5zL2oPjqv6O', 'moin-christine@web.de'),
(21, 'ChristineDa', '$2a$10$9udMaUoLKav87u6HmQLGOujdOGYrSYCkkNkC49xouoliTo2A0biEm', 'Christine-Dall@web.de'),
(22, 'Test', '$2a$10$smUaKp/UlmtwSjzVmZSwJO7DYmPxFi0yNDg8F3Br.dIjWfnbOOmxC', 'test@web.de'),
(23, 'Igel', '$2a$10$V15K7134IclfkmhMhex7quvnUVloUvWuHar5MhjFiP2ukc6nWPTeS', 'tui@webde');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `tb_end of_the_day`
--
ALTER TABLE `tb_end of_the_day`
  ADD PRIMARY KEY (`cl_id_end`),
  ADD KEY `cl_user_nr` (`cl_user_nr`),
  ADD KEY `cl_goods_nr` (`cl_goods_nr`),
  ADD KEY `cl_self_service_stand_nr` (`cl_self_service_stand_nr`);

--
-- Indizes für die Tabelle `tb_goods`
--
ALTER TABLE `tb_goods`
  ADD PRIMARY KEY (`cl_goods_nr`),
  ADD KEY `cl_goods_unit_nr` (`cl_goods_unit_nr`),
  ADD KEY `cl_self_service_stand_nr` (`cl_self_service_stand_nr`),
  ADD KEY `cl_goods_name_nr` (`cl_goods_name_nr`);

--
-- Indizes für die Tabelle `tb_goods_name`
--
ALTER TABLE `tb_goods_name`
  ADD PRIMARY KEY (`cl_goods_name_nr`);

--
-- Indizes für die Tabelle `tb_receipt_goods`
--
ALTER TABLE `tb_receipt_goods`
  ADD PRIMARY KEY (`cl_goods_receipt_nr`),
  ADD KEY `cl_goods_nr` (`cl_goods_nr`),
  ADD KEY `cl_user_nr` (`cl_user_nr`),
  ADD KEY `cl_self_service_stand_nr` (`cl_self_service_stand_nr`);

--
-- Indizes für die Tabelle `tb_role`
--
ALTER TABLE `tb_role`
  ADD PRIMARY KEY (`cl_role_nr`);

--
-- Indizes für die Tabelle `tb_self_service_stand`
--
ALTER TABLE `tb_self_service_stand`
  ADD PRIMARY KEY (`cl_self_service_stand_nr`);

--
-- Indizes für die Tabelle `tb_self_service_stand_user_nr`
--
ALTER TABLE `tb_self_service_stand_user_nr`
  ADD PRIMARY KEY (`cl_self_service_stand_nr_id`),
  ADD KEY `cl_role_nr` (`cl_role_nr`),
  ADD KEY `cl_self_service_stand_nr` (`cl_self_service_stand_nr`),
  ADD KEY `cl_user_nr` (`cl_user_nr`);

--
-- Indizes für die Tabelle `tb_unit`
--
ALTER TABLE `tb_unit`
  ADD PRIMARY KEY (`cl_unit_nr`);

--
-- Indizes für die Tabelle `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`cl_user_nr`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `tb_end of_the_day`
--
ALTER TABLE `tb_end of_the_day`
  MODIFY `cl_id_end` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `tb_goods`
--
ALTER TABLE `tb_goods`
  MODIFY `cl_goods_nr` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=324;

--
-- AUTO_INCREMENT für Tabelle `tb_goods_name`
--
ALTER TABLE `tb_goods_name`
  MODIFY `cl_goods_name_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT für Tabelle `tb_receipt_goods`
--
ALTER TABLE `tb_receipt_goods`
  MODIFY `cl_goods_receipt_nr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT für Tabelle `tb_role`
--
ALTER TABLE `tb_role`
  MODIFY `cl_role_nr` int(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT für Tabelle `tb_self_service_stand`
--
ALTER TABLE `tb_self_service_stand`
  MODIFY `cl_self_service_stand_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT für Tabelle `tb_self_service_stand_user_nr`
--
ALTER TABLE `tb_self_service_stand_user_nr`
  MODIFY `cl_self_service_stand_nr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT für Tabelle `tb_unit`
--
ALTER TABLE `tb_unit`
  MODIFY `cl_unit_nr` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT für Tabelle `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `cl_user_nr` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `tb_end of_the_day`
--
ALTER TABLE `tb_end of_the_day`
  ADD CONSTRAINT `tb_end of_the_day_ibfk_1` FOREIGN KEY (`cl_user_nr`) REFERENCES `tb_user` (`cl_user_nr`),
  ADD CONSTRAINT `tb_end of_the_day_ibfk_2` FOREIGN KEY (`cl_goods_nr`) REFERENCES `tb_goods` (`cl_goods_nr`),
  ADD CONSTRAINT `tb_end of_the_day_ibfk_3` FOREIGN KEY (`cl_self_service_stand_nr`) REFERENCES `tb_self_service_stand` (`cl_self_service_stand_nr`);

--
-- Constraints der Tabelle `tb_goods`
--
ALTER TABLE `tb_goods`
  ADD CONSTRAINT `tb_goods_ibfk_2` FOREIGN KEY (`cl_goods_unit_nr`) REFERENCES `tb_unit` (`cl_unit_nr`),
  ADD CONSTRAINT `tb_goods_ibfk_3` FOREIGN KEY (`cl_self_service_stand_nr`) REFERENCES `tb_self_service_stand` (`cl_self_service_stand_nr`),
  ADD CONSTRAINT `tb_goods_ibfk_4` FOREIGN KEY (`cl_goods_name_nr`) REFERENCES `tb_goods_name` (`cl_goods_name_nr`);

--
-- Constraints der Tabelle `tb_receipt_goods`
--
ALTER TABLE `tb_receipt_goods`
  ADD CONSTRAINT `tb_receipt_goods_ibfk_1` FOREIGN KEY (`cl_goods_nr`) REFERENCES `tb_goods` (`cl_goods_nr`),
  ADD CONSTRAINT `tb_receipt_goods_ibfk_2` FOREIGN KEY (`cl_user_nr`) REFERENCES `tb_user` (`cl_user_nr`),
  ADD CONSTRAINT `tb_receipt_goods_ibfk_3` FOREIGN KEY (`cl_self_service_stand_nr`) REFERENCES `tb_self_service_stand` (`cl_self_service_stand_nr`);

--
-- Constraints der Tabelle `tb_self_service_stand_user_nr`
--
ALTER TABLE `tb_self_service_stand_user_nr`
  ADD CONSTRAINT `tb_self_service_stand_user_nr_ibfk_1` FOREIGN KEY (`cl_role_nr`) REFERENCES `tb_role` (`cl_role_nr`),
  ADD CONSTRAINT `tb_self_service_stand_user_nr_ibfk_2` FOREIGN KEY (`cl_self_service_stand_nr`) REFERENCES `tb_self_service_stand` (`cl_self_service_stand_nr`),
  ADD CONSTRAINT `tb_self_service_stand_user_nr_ibfk_3` FOREIGN KEY (`cl_user_nr`) REFERENCES `tb_user` (`cl_user_nr`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
