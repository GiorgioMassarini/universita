-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Thu Jan 22 21:50:03 2026 
-- * LUN file:  
-- * Schema: SCHEMA/SQL 
-- ********************************************* 


-- Database Section
-- ________________ 

create database SCHEMA;


-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table CALENDARIO (
     Data char(1) not null,
     MaxBiglietti char(1) not null,
     NumVenduti char(1) not null,
     CodSPett char(1) not null,
     constraint ID_CALENDARIO_ID primary key (Data));

create table SPETTACOLI (
     CodSPett char(1) not null,
     Titolo char(1) not null,
     constraint ID_SPETTACOLI_ID primary key (CodSPett));

create table PRENOTAZIONI (
     CodPren char(1) not null,
     Cliente char(1) not null,
     NumBiglietti char(1) not null,
     Data char(1) not null,
     constraint ID_PRENOTAZIONI_ID primary key (CodPren));


-- Constraints Section
-- ___________________ 

alter table CALENDARIO add constraint FKSC_FK
     foreign key (CodSPett)
     references SPETTACOLI;

alter table SPETTACOLI add constraint ID_SPETTACOLI_CHK
     check(exists(select * from CALENDARIO
                  where CALENDARIO.CodSPett = CodSPett)); 

alter table PRENOTAZIONI add constraint FKCP_FK
     foreign key (Data)
     references CALENDARIO;


-- Index Section
-- _____________ 

create unique index ID_CALENDARIO_IND
     on CALENDARIO (Data);

create index FKSC_IND
     on CALENDARIO (CodSPett);

create unique index ID_SPETTACOLI_IND
     on SPETTACOLI (CodSPett);

create unique index ID_PRENOTAZIONI_IND
     on PRENOTAZIONI (CodPren);

create index FKCP_IND
     on PRENOTAZIONI (Data);

