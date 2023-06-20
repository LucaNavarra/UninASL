DROP TABLE PRENOTAZIONI;
DROP TABLE VISITE;
DROP TABLE UTENTI;
DROP TABLE OPERATORI;
DROP TABLE MEDICI;
DROP TABLE SPECIALIZZAZIONI;

CREATE TABLE `Specializzazioni` (
	`nome` VARCHAR(50) NOT NULL,
	`durata_ore` INT NOT NULL,
	`durata_minuti` INT NOT NULL,
	`costo` DOUBLE NOT NULL,
	PRIMARY KEY (`nome`),
    CHECK (durata_minuti >= 0 AND durata_minuti < 60),
    CHECK (durata_ore >= 0 AND durata_ore < 24),
	CHECK (costo >= 0)
);

 CREATE TABLE `Operatori` (
	`codice_operatore` INT NOT NULL AUTO_INCREMENT,
	`password` VARCHAR(30) NOT NULL,
	`nome` VARCHAR(50) NOT NULL,
	`cognome` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`codice_operatore`)
);

CREATE TABLE `Medici` (
	`username` VARCHAR(50) NOT NULL,
	`password` VARCHAR(30) NOT NULL,
	`nome` VARCHAR(50) NOT NULL,
	`cognome` VARCHAR(50) NOT NULL,
	`specializzazione` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`username`),
    FOREIGN KEY (specializzazione) REFERENCES SpecializzazionI(nome)
);



CREATE TABLE `Visite` (
	`ora_inizio` INT NOT NULL,
	`ora_fine` INT NOT NULL,
	`minuti_inizio` INT NOT NULL,
	`minuti_fine` INT NOT NULL,
	`stanza` VARCHAR(50) NOT NULL,
	`giorno` VARCHAR(9) NOT NULL,
	`medico_riferimento` VARCHAR(50) NOT NULL,
	`id_orario` INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`id_orario`),
    FOREIGN KEY (medico_riferimento) REFERENCES Medici(username),
    CONSTRAINT Unq_visita UNIQUE (ora_inizio,minuti_inizio,giorno,medico_riferimento),
    CHECK (minuti_inizio >= 0 AND minuti_inizio < 60),
    CHECK (ora_inizio >= 0 AND ora_inizio < 24),
    CHECK (minuti_fine >= 0 AND minuti_fine < 60),
    CHECK (ora_fine >= 0 AND ora_fine < 24),
    CHECK (giorno = 'Lunedi' OR giorno = 'Martedi' OR giorno = 'Mercoledi' OR giorno = 'Giovedi' OR giorno = 'Venerdi' OR giorno = 'Sabato' OR giorno = 'Domenica')
);

CREATE TABLE `Utenti` (
	`username` VARCHAR(50) NOT NULL,
	`password` VARCHAR(30) NOT NULL,
	`nome` VARCHAR(50) NOT NULL,
	`cognome` VARCHAR(50) NOT NULL,
	`cf` VARCHAR(16) NOT NULL UNIQUE,
	`data_nascita` DATE NOT NULL,
	`luogo_nascita` VARCHAR(40) NOT NULL,
	`residenza` VARCHAR(40) NOT NULL,
	`numero_telefono` VARCHAR(15) NOT NULL,
	`email` VARCHAR(40) NOT NULL UNIQUE,
	`ISEE` INT,
	`grado_disabilita` VARCHAR(10) NOT NULL,
	PRIMARY KEY (`username`),
    CHECK (grado_disabilita = 'Grave' OR grado_disabilita = 'Moderata' OR grado_disabilita = 'Lieve' OR grado_disabilita = 'Nessuna'),
	CHECK (ISEE >= 0)
);

CREATE TABLE Prenotazioni (
	id_prenotazione INT NOT NULL AUTO_INCREMENT,
	paziente VARCHAR(50) NOT NULL,
	prezzo DOUBLE,
	visita INT NOT NULL,
	data_visita DATE NOT NULL,
	codice_operatore INT,
	codiceQR BLOB,
	CONSTRAINT Unq_data UNIQUE (visita,data_visita),
	CHECK (prezzo >= 0),
	PRIMARY KEY (id_prenotazione),
    FOREIGN KEY (paziente) REFERENCES Utenti(username),
    FOREIGN KEY (visita) REFERENCES Visite(id_orario),
    FOREIGN KEY (codice_operatore) REFERENCES Operatori(codice_operatore)
);


INSERT INTO SPECIALIZZAZIONI VALUES	('ortopedia',0,45,44.20);
INSERT INTO SPECIALIZZAZIONI VALUES	('remautologia',0,30,43.21);
INSERT INTO SPECIALIZZAZIONI VALUES	('geriatria',0,30, 55.80);
INSERT INTO SPECIALIZZAZIONI VALUES	('oncologia',0,45, 34.50);
INSERT INTO SPECIALIZZAZIONI VALUES	('allergologia_e_immunologia',1,0,87.43);
INSERT INTO SPECIALIZZAZIONI VALUES	('dermatologia_e_veneorologia',0,20,85.80);
INSERT INTO SPECIALIZZAZIONI VALUES	('ematologia',0,15,44.42);
INSERT INTO SPECIALIZZAZIONI VALUES	('endocrinologia',0,30,63.40);
INSERT INTO SPECIALIZZAZIONI VALUES	('gastroenterologia',1,30,93.22);
INSERT INTO SPECIALIZZAZIONI VALUES	('cardiologia',0,20,67.45);
INSERT INTO SPECIALIZZAZIONI VALUES	('pneumologia',0,45,28.50);
INSERT INTO SPECIALIZZAZIONI VALUES	('malattie_infettive',2,0,44.10);
INSERT INTO SPECIALIZZAZIONI VALUES	('nefrologia',1,0,120.45);
INSERT INTO SPECIALIZZAZIONI VALUES	('neurologia',1,0, 144.90);
INSERT INTO SPECIALIZZAZIONI VALUES	('neuropsichiatria_infantile',1,30,120.15);
INSERT INTO SPECIALIZZAZIONI VALUES	('psichiatria',1,30,80);
INSERT INTO SPECIALIZZAZIONI VALUES	('pediatria',0,30,50);
INSERT INTO SPECIALIZZAZIONI VALUES	('ginecologia',0,20,30.67);
INSERT INTO SPECIALIZZAZIONI VALUES	('urologia',0,30,55.80);
INSERT INTO SPECIALIZZAZIONI VALUES	('oftalmologia',1,0,57.48);
INSERT INTO SPECIALIZZAZIONI VALUES	('otorinolaringoiatria',0,30,24.80);
INSERT INTO SPECIALIZZAZIONI VALUES	('radiodiagnostica',0,20,90.56);
INSERT INTO SPECIALIZZAZIONI VALUES	('radioterapia',2,0,70.44);
INSERT INTO SPECIALIZZAZIONI VALUES	('medicina_nucleare',2,0,55.80);
INSERT INTO SPECIALIZZAZIONI VALUES	('anestesia',0,20,49.23);
INSERT INTO SPECIALIZZAZIONI VALUES	('audiologia_e_foniatria',0,15,44.30);
INSERT INTO SPECIALIZZAZIONI VALUES	('medicina_fisica_e_riabilitativa',1,0,98.40);
INSERT INTO SPECIALIZZAZIONI VALUES	('farmacologia_e_tossicologia_clinica',1,0,124.40);
INSERT INTO SPECIALIZZAZIONI VALUES	('igiene_e_medicina_preventiva',0,15,88.45);
INSERT INTO SPECIALIZZAZIONI VALUES	('medicina_del_lavoro',0,30,52);
INSERT INTO SPECIALIZZAZIONI VALUES	('madicina_legale',1,0,93.34);
INSERT INTO SPECIALIZZAZIONI VALUES ('statistica_sanitaria_e_biometria',1,0,45.44);
INSERT INTO SPECIALIZZAZIONI VALUES	('medico_di_base',0,15,40.35);

INSERT INTO Utenti VALUES('Andre0','123','Andrea','Sannino','SNNNDR00P23F839N','2000-09-23','Napoli','Volla','3246864101','andrea.sannino4@studenti.unina.it',20000,'Nessuna');
INSERT INTO Utenti VALUES('LucaNav','luca123','Luca','Navarra','NVRLC05C839W','2000-11-22','Napoli','San Giovanni','3886524178','luc.navarra@studenti.unina.it',20000,'Nessuna');

INSERT INTO Operatori VALUES('1234','Antonio','Frustalupi');
INSERT INTO Operatori VALUES('op03','Fabio','Miranda');
INSERT INTO Operatori VALUES('123','Michele','Ferri');

INSERT INTO Medici VALUES('And_Fanti','medico','Andrea','Fanti','endocrinologia');
INSERT INTO Medici VALUES('Rick01','med01','Riccardo','Bonvegna','cardiologia');
INSERT INTO Medici VALUES('Mark','pol234','Marco','Sardoni','medicina_fisica_e_riabilitativa');
INSERT INTO Medici VALUES('Lollo01','lory','Lorenzo','Lazzarini','cardiologia');
INSERT INTO Medici VALUES('Elis87','elisa01','Elisa','Russo','neurologia');
INSERT INTO Medici VALUES('Gabb','gab01','Gabriel','Kidane','malattie_infettive');
INSERT INTO Medici VALUES('Alby04','sole','Alba','Patrizi','ortopedia');
INSERT INTO Medici VALUES('Damians','Cesc01','Damiano','Cesconi','dermatologia_e_veneorologia');
INSERT INTO Medici VALUES('Erick','1234','Enrico','Sandri','psichiatria');
INSERT INTO Medici VALUES('Caro02','carolyn','Carolina','Fanti','oncologia');
INSERT INTO Medici VALUES('Ceck','13l','Cecilia','Tedeschi','cardiologia');
INSERT INTO Medici VALUES('Edo84','vale46','Edoardo','Valenti','farmacologia_e_tossicologia_clinica');
INSERT INTO Medici VALUES('Ant05','1234','Antonio','Lazzarini','malattie_infettive');
INSERT INTO Medici VALUES('Agn45','tiberio','Agnese','Tiberi','radioterapia');
INSERT INTO Medici VALUES('Alb41','pov45','Alberto','Zangrillo','endocrinologia');
INSERT INTO Medici VALUES('Giuly','4567','Giulia','Giordano','anestesia');

INSERT INTO Visite VALUES(8, 8, 0, 30, 'T2', 'Lunedi', 'And_Fanti');
INSERT INTO Visite VALUES(8, 9, 30, 0, 'T2', 'Lunedi', 'And_Fanti');
INSERT INTO Visite VALUES(10, 10, 15, 45, 'A5-I2', 'Giovedi', 'And_Fanti');
INSERT INTO Visite VALUES(10, 11, 45, 15, 'A5-I2', 'Giovedi', 'And_Fanti');

INSERT INTO Visite VALUES(11, 11, 0, 20, 'C4', 'Martedi', 'Rick01');
INSERT INTO Visite VALUES(11, 11, 20, 40, 'C4', 'Martedi', 'Rikc01');

INSERT INTO Visite VALUES(11, 12, 0, 0, 'A3-T3', 'Mercoledi', 'Mark');
INSERT INTO Visite VALUES(12, 13, 0, 0, 'A3-T3', 'Mercoledi', 'Mark');
INSERT INTO Visite VALUES(15, 16, 0, 0, 'I2', 'Venerdi', 'Mark');

INSERT INTO Visite VALUES(9, 9, 35, 55, 'A3-T2', 'Martedi', 'Lollo01');
INSERT INTO Visite VALUES(9, 10, 55, 15, 'A3-T2', 'Martedi', 'Lollo01');
INSERT INTO Visite VALUES(10, 10, 25, 45, 'A3-T2', 'Martedi', 'Lollo01');

INSERT INTO Visite VALUES(8, 9, 0, 0, 'T4', 'Lunedi', 'Elis87');
INSERT INTO Visite VALUES(9, 10, 00, 0, 'T4', 'Lunedi', 'Elis87');

INSERT INTO Visite VALUES(10, 12, 0, 0, 'I5-T2', 'Martedi', 'Gabb');
INSERT INTO Visite VALUES(14, 16, 0, 0, 'I5-T2', 'Martedi', 'Gabb');
INSERT INTO Visite VALUES(16, 18, 0, 0, 'I5-T2', 'Martedi', 'Gabb');

INSERT INTO Visite VALUES(9, 9, 0, 45, 'A3', 'Venerdi', 'Alby04');
INSERT INTO Visite VALUES(15, 15, 0, 45, 'A3', 'Venerdi', 'Alby04');

INSERT INTO Visite VALUES(11, 11, 30, 50, 'T5', 'Martedi', 'Damians');
INSERT INTO Visite VALUES(11, 12, 50, 10, 'T5', 'Martedi', 'Damians');
INSERT INTO Visite VALUES(15, 15, 20, 40, 'I2', 'Giovedi', 'Damians');
INSERT INTO Visite VALUES(15, 16, 40, 0, 'I2', 'Giovedi', 'Damians');

INSERT INTO Visite VALUES(10, 11, 0, 30, 'A4-I2', 'Mercoledi', 'Erick');
INSERT INTO Visite VALUES(14, 16, 30, 0, 'A4-I2', 'Mercoledi', 'Erick');

INSERT INTO Visite VALUES(8, 9, 15, 0, 'T3', 'Giovedi', 'Caro02');
INSERT INTO Visite VALUES(9, 9, 0, 45, 'T3', 'Giovedi', 'Caro02');
INSERT INTO Visite VALUES(9, 10, 45, 30, 'T3', 'Giovedi', 'Caro02');

INSERT INTO Visite VALUES(8, 8, 0, 20, 'I1', 'Lunedi', 'Ceck');
INSERT INTO Visite VALUES(9, 9, 0, 20, 'I1', 'Lunedi', 'Ceck');

INSERT INTO Visite VALUES(14, 15, 0, 0, 'T1-A5', 'Mercoledi', 'Edo84');
INSERT INTO Visite VALUES(15, 16, 0, 0, 'T1-A5', 'Mercoledi', 'Edo84');
INSERT INTO Visite VALUES(12, 13, 0, 0, 'A2', 'Venerdi', 'Edo84');
INSERT INTO Visite VALUES(13, 14, 0, 0, 'A2', 'Venerdi', 'Edo84');

INSERT INTO Visite VALUES(9, 11, 0, 0, 'T6', 'Martedi', 'Ant05');
INSERT INTO Visite VALUES(14, 16, 0, 0, 'T6', 'Martedi', 'Ant05');

INSERT INTO Visite VALUES(10, 12, 0, 0, 'A5-I3', 'Giovedi', 'Agn45');
INSERT INTO Visite VALUES(14, 16, 0, 0, 'A5-I3', 'Giovedi', 'Agn45');

INSERT INTO Visite VALUES(8, 9, 30, 0, 'T2', 'Martedi', 'Alb41');
INSERT INTO Visite VALUES(9, 9, 0, 30, 'T2', 'Martedi', 'Alb41');
INSERT INTO Visite VALUES(11, 12, 30, 0, 'A5-I2', 'Mercoledi', 'Alb41');
INSERT INTO Visite VALUES(12, 12, 0, 30, 'A5-I2', 'Mercoledi', 'Alb41');

INSERT INTO Visite VALUES(8, 8, 20, 40, 'T3-I1', 'Venerdi', 'Giuly');
INSERT INTO Visite VALUES(8, 9, 40, 0, 'T3-I1', 'Venerdi', 'Giuly');
INSERT INTO Visite VALUES(9, 9, 15, 35, 'A4-I4', 'Venerdi', 'Giuly');
INSERT INTO Visite VALUES(9, 9, 35, 55, 'A4-I4', 'Venerdi', 'Giuly');

/*-----SQL STATEMENT FOR TESTING-----*/
INSERT INTO Medici VALUES('test','test','test','test','otorinolaringoiatria');
INSERT INTO UTENTI VALUES ('test','test','test','test','test','2000-01-01','test','test','test','test',0,'Nessuna');
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Lunedi',8,30,9,0,'test','test',120);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Lunedi',9,0,9,30,'test','test',122);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Martedi',8,30,9,0,'test','test',123);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Martedi',9,0,9,30,'test','test',124);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Mercoledi',8,30,9,0,'test','test',125);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Mercoledi',9,0,9,30,'test','test',126);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Mercoledi',11,30,12,0,'test','test',127);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Martedi',11,30,12,0,'test','test',128);
INSERT INTO VISITE ( GIORNO , ORA_INIZIO , MINUTI_INIZIO , ORA_FINE , MINUTI_FINE , STANZA , MEDICO_RIFERIMENTO, ID_ORARIO ) VALUES ('Giovedi',16,30,17,0,'test','test',129);
INSERT INTO PRENOTAZIONI ( DATA_VISITA , PAZIENTE , VISITA ) VALUES ('2022-05-10','test',123);
INSERT INTO PRENOTAZIONI ( DATA_VISITA , PAZIENTE , VISITA ) VALUES ('2022-07-14','test',129);
