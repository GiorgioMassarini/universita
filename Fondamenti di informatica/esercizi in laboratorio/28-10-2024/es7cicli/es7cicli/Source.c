#include <stdio.h>
int main()
{
	int somma = 0, max=0,i=0;
	char corrente='x', precedente, zero = '0', primo;
	printf("Valore: ");
	precedente = getchar();
	getchar();
	while (corrente != '0')
	{
		printf("Valore: ");
		corrente = getchar();
		getchar();
		while ((int)precedente <= (int)corrente)
		{
			if (i == 0)
			{
				primo = precedente;
				somma = somma + ((int)primo - (int)zero);
			}
			i = i + 1;
			if ((int)precedente <= (int)corrente)
			{
				somma = somma + ((int)corrente - (int)zero);
			}
			break;
		}
		if ((int)precedente > (int)corrente)
		{
			somma = 0;
			i = 0;
		}
		if (somma > max)
		{
			max = somma;
		}
		precedente = corrente;
	}
	printf("Risultato: %d", max);
}