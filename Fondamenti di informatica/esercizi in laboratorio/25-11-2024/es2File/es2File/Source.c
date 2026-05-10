#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include "modulo.h"
#include <string.h>

int main() 
{
	Dati D;
	Indirizzo I;
	Elemento E;
	Tabella T;
	FILE* f1, * f2;
	int i, trovato, ins = 0, totC;
	unsigned int C;
	f1 = fopen("dati.txt", "r");
	f2 = fopen("indirizzi.txt", "r");
	while (fscanf(f1, "%u%u", &D.matr, &D.CDL) > 0) 
	{
		trovato = 0;
		rewind(f2);
		while (fscanf(f2, "%d %s %s %s %s %d", &I.matr, I.nome, I.cognome, I.via, I.citta, &I.CAP) == 6 && !trovato)
			if (I.matr == D.matr) 
			{
				trovato = 1;
				E = riempiel(D, I);
				T[ins] = E;
				ins++;
			}
	}
	fclose(f1); fclose(f2);
	printf("Inserire il corso C: ");
	scanf("%u", &C);
	totC = 0;
	for (i = 0; i < ins; i++)
		if (T[i].CDL == C)
		{
			totC++;
		}
	printf("\n Iscritti al corso %u: %f \%\n",
		C, (float)totC * 100 / ins);
	f1 = fopen("bologna.txt", "w");
	for (i = 0; i < ins; i++)
		if (strcmp("bologna", T[i].citta) == 0)
			fprintf(f1, "%s %s %u\n", T[i].nome, T[i].cognome, T[i].matr);
	fclose(f1);
	return 0;
}