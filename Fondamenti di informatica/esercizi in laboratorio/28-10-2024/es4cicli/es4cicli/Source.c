#include <stdio.h>
int main()
{
	float a, n, somma=0, potenza=1;
	int contatore = 0, i=0;
	printf("Inserisci valore A: ");
	scanf_s("%f", &a);
	printf("Inserisci valore N: ");
	scanf_s("%f", &n);
	while (contatore < n)
	{
		contatore = contatore + 1;
		while (i < contatore)
		{
			potenza = potenza * a;
			i = i + 1;
		}
		somma = somma + potenza;
	}
	printf("Risultato: %f", somma+1);
}