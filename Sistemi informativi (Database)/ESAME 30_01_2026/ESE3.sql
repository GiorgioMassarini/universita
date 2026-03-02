--GIORGIO MASSARINI 0001164426

--ESERCIZIO 3.2
create table RISULTATI (
     Punti INT not null,
     TimeStamp TimeStamp not null,
     Nickname varchar(20) not null,
     constraint ID_RISULTATI_ID primary key (TimeStamp));

create table GIOCATORI (
     Nickname varchar(20) not null,
     NumRecord INT not null,
     constraint ID_GIOCATORI_ID primary key (Nickname));



--ESERCIZIO 3.3
CREATE OR REPLACE TRIGGER NUOVO_RISULTATO 
AFTER INSERT ON RISULTATI
REFERENCING NEW AS N
FOR EACH ROW
IF(N.Punti > 
   (SELECT R.Punti
   	FROM RISUALTI R))
THEN UPDATE NumRecord=NumRecord+1;