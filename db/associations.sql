CREATE TABLE `associations` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NOME` varchar(40) NOT NULL,
  `IDFIDAL` varchar(10) NOT NULL,
  `CF` varchar(16),
  `PIVA` varchar(11),
  `CITTA` varchar(50) NOT NULL,
  `INDIRIZZO` varchar(50) NOT NULL,
  `CAP` varchar(5) NOT NULL,
  `PROVINCIA` varchar(50) NOT NULL,
  `TELEFONO` varchar(20) NOT NULL,
  `EMAIL` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3