create table APP (
     Nome char(10) not null primary key,
     Creatore char(10) not null	);

create table VERSIONI (
     Nome char(10) not null references APP,
     Sigla char(10) not null,
     DataCreazione DATE not null,
     constraint ID_VERSIONI_ID primary key (Nome, Sigla));

create table DOWNLOAD (
     Nome char(10) not null,
     Sigla char(10) not null,
     URL varchar(30) not null,
     Numero INT not null DEFAULT 0,
     constraint ID_DOWNLOAD_ID primary key (URL, Nome, Sigla),
	 constraint FKDV_FK foreign key (Nome, Sigla) references VERSIONI );

----------------------------------------


SELECT P.Categoria, DEC(AVG(E.Rimborso/P.Prezzo),4,3)
FROM Prodotti P, Reclami R, Esiti E
WHERE P.ProdID=R.ProdID
AND R.RID=E.RID
GROUP BY P.Categoria
HAVING count(R) >1




WITH TEMPI_ESITI (Categoria,NumGiorni) AS (
SELECT P.Categoria, DAYS(R2.Data)-DAYS(R1.Data)
FROM PRODOTTI P, RECLAMI R1, RECLAMI R2
WHERE P.ProdID = R1.ProdID
AND R1.ProdID = R2.ProdID
AND R1.RID != R2.RID
AND R1.Data <= R2.Data
)
SELECT D.*
FROM DIFF_RECLAMI D
WHERE D.NumGiorni = ( SELECT MIN(D1.NumGiorni)
 					  FROM DIFF_RECLAMI D1
 				      WHERE D.Categoria = D1.Categoria ) ;




CREATE OR REPLACE TRIGGER NUOVA_VERSIONE
AFTER INSERT ON VERSIONI
REFERENCING NEW AS N
FOR EACH ROW
INSERT INTO DOWNLOAD(Nome,Sigla,URL)
SELECT DISTINCT N.Nome, N.Sigla, URL
FROM DOWNLOAD D
WHERE D.Nome= N.Nome;

INSERT INTO VERSIONI(Nome,Sigla,DataCreazione)
VALUES(:nomeApp,:siglaVersione, CURRENT DATE) ;



CREATE TABLE E2 (
K1 INT NOT NULL PRIMARY KEY,
A INT NOT NULL,
B INT NOT NULL );
CREATE TABLE E3 (
K1 INT NOT NULL PRIMARY KEY,
A INT NOT NULL,
C INT NOT NULL );
CREATE TABLE E4 (
K4 INT NOT NULL PRIMARY KEY,
D INT NOT NULL,
K1E2 INT REFERENCES E2,
K1E3 INT REFERENCES E3,
CONSTRAINT R1_DEFINED CHECK ((K1E2 IS NULL AND K1E3 IS NOT NULL) OR
 (K1E2 IS NOT NULL AND K1E3 IS NULL)) );


