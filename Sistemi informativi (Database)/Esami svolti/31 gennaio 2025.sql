create table AZIONI (
     Nome char(20) not null,
     Valore numeric(1) not null,
     constraint ID_AZIONI_ID primary key (Nome));

create table PORTAFOGLI (
     PID char(20) not null,
     ValoreTotale numeric(1) not null,
     WARNING char not null,
     constraint ID_PORTAFOGLI_ID primary key (PID));

create table AP (
     Nome char(20) not null,
     PID char(20) not null,
     Quantita numeric(1) not null,
     constraint ID_AP_ID primary key (PID, Nome));

alter table AP add constraint FKAP_POR
     foreign key (PID)
     references PORTAFOGLI;

alter table AP add constraint FKAP_AZI_FK
     foreign key (Nome)
     references AZIONI;