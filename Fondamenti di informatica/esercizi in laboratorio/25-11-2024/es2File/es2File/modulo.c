#include "modulo.h"


Elemento riempiel(Dati d, Indirizzo i) 
{
	Elemento e;
	e.matr = d.matr;
	e.CDL = d.CDL;
	strcpy(e.nome, i.nome);
	strcpy(e.cognome, i.cognome);
	strcpy(e.via, i.via);
	strcpy(e.citta, i.citta);
	e.CAP = i.CAP;
	return e;
}