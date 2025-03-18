#include "hr.h"

int main()
{
	list posizioni;
	Candidato* candidati;
	FILE* f;
	int dim_c;
	int dim;
	int i = 0;
	Candidato* adatti;
	posizioni=leggi_posizioni("posizioni.txt");
	printf("--------POSIZIONI DISPONIBILI--------\n");
	showlist(posizioni);
	f = fopen("candidati.txt", "r");
	if (f == NULL)
	{
		printf("errore nell'apertura del file!");
		exit(-1);
	}
	candidati = leggi_candidati(f, &dim_c);
	fclose(f);
	printf("--------CANDIDATI--------\n");
	for (i = 0; i < dim_c; i++)
	{
		printf("%s %s %d \n", candidati[i].nome, candidati[i].settore, candidati[i].esperienza);
	}
	printf("\n\n");
	adatti=trova_candidati("J_013", posizioni, candidati, dim_c, &dim);
	printf("-------CANDIDATI IDONEI---------\n");
	for (i = 0; i < dim; i++)
	{
		printf("%s : %s : %d \n", adatti[i].nome, adatti[i].settore, adatti[i].esperienza);
	}
	printf("\nNumero candidati idonei alla posizione: %d\n\n\n", dim);
}
