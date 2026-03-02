create table PREZZI (
     Data date not null,
     Prezzo INT not null,
     IdNegozio INT not null,
     Nome varchar(30) not null,
     constraint ID_PREZZI_ID primary key (Data));

create table PRODOTTI (
     Nome varchar(30) not null,
     PrezzoBase numeric(1) not null,
     constraint ID_PRODOTTI_ID primary key (Nome));

alter table PREZZI add constraint FKPN_FK
     foreign key (IdNegozio)
     references NEGOZI;

alter table PREZZI add constraint FKPP_FK
     foreign key (Nome)
     references PRODOTTI;
---------------------------





SELECT DISTINCT P.ProdID, P.Categoria, P.Prezzo
FROM Prodotti P, Reclami R, Esiti E
WHERE P.ProdID=R.ProdID
AND R.RID=E.RID
AND p.prezzo>50 
GROUP BY p.prodID, P.Categoria, P.Prezzo
HAVING 2*count(E.rimborsato) > count(*);

WITH DIFF_RECLAMI (Categoria, ProdID, NumGiorni) AS (
SELECT P.categoria, R1.prodID, DAYS(R2.data)-DAYS(R1.data)
FROM Prodotti P, Reclami R1, Reclami R2
WHERE p.ProdoID=R1.ProdID
AND R1.prodId=R2.ProdID
AND R1.RID != R2.RID
AND R1.DATA<=R2.DATA
)
SELECT D.*
FROM DIFF_RECLAMI D
WHERE D.NumGIorni = (SELECT MIN(D1.D.NUMGIORNI)
					 FROM DIFF_RECLAMI D1
					 WHERE D.Categoria = D1.Categoria) ;


CREATE OR REPLACE TRIGGER CONTROLLO_PREZZO
BEFORE INSERT ON PREZZI
REFERENCING NEW AS N
FOR EACH ROW
IF(differisce + del 20 %) 
THEN SIGNAL SQLSTATE '70001'('Prezzo non valido');
ELSE UPDATE PREZZI
SET Prezzo=N.Prezzo
END IF



CREATE TABLE E1 (
K1 INT NOT NULL PRIMARY KEY,
A INT NOT NULL,
TIPO12 SMALLINT NOT NULL CHECK (TIPO12 IN (1,2)),
B INT,
K1E3 INT,
C INT,
CHECK ((TIPO12 = 1 AND B IS NULL AND K1E3 IS NULL AND C IS NULL)
 OR (TIPO12 = 2 AND B IS NOT NULL),
CHECK ((K1E3 IS NULL AND C IS NULL)
 OR (K1E3 IS NOT NULL AND C IS NOT NULL)) );


CREATE TABLE E3 (
K3 INT NOT NULL,
K1 INT NOT NULL REFERENCES E1,
C INT NOT NULL,
PRIMARY KEY (K3,k1) );


ALTER TABLE E1
ADD CONSTRAINT FK_E3 FOREIGN KEY (K1E3,k3) REFERENCES E3;



-- Trigger che garantisce che E3.K1 referenzi un’istanza di E2
CREATE OR REPLACE TRIGGER R1
BEFORE INSERT ON E3
REFERENCING NEW AS N
FOR EACH ROW
WHEN ( EXISTS ( SELECT *
 FROM E1
 WHERE N.K1 = E1.K1 AND E1.TIPO12 = 1 ) )
SIGNAL SQLSTATE '70001' ('La tupla referenzia una tupla che non appartiene a E2!');
-- Il vincolo al punto c) può essere violato solo inserendo in E1 un’istanza che appartiene a E2 e partecipa a R3
CREATE TRIGGER PUNTO_C
BEFORE INSERT ON E1
REFERENCING NEW AS N
FOR EACH ROW
WHEN ( N.B < 10 )
SIGNAL SQLSTATE '70002' ('La tupla inserita in E3 non rispetta il vincolo del punto c)! ');
