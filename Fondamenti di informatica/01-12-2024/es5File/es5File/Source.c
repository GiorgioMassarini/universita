#define _CRT_SECURE_NO_WARNINGS
#include "modulo.h"
#include <stdio.h>
#include <stdlib.h>

int main()
{
	FILE * fp;
	scontrino temp;
	if ((fp = fopen("reg.dat", "wb")) == NULL)
	{
		printf("ccc");
		exit(-1);
	}
	do
	{
		printf("Inserisci valore e numero di pezzi: ");
		scanf_s("%f %d", &(temp.val), &(temp.num));
		if (temp.val != 0 || temp.num != 0)
		{
			scrivi(fp, temp);
		}
	} while (temp.val != 0 || temp.num != 0);
	fclose(fp);

	if ((fp = fopen("reg.dat", "rb")) == NULL)
	{
		exit(-1);
	}
	while (leggi(fp, &temp) > 0)
	{
		printf("Prezzo: %f, pezzi: %d\n", temp.val, temp.num);
	}
	fclose(fp);
}