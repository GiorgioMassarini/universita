#include <stdio.h>
int main()
{
	int N=1, i;
	float somma = 0, valore=0;
	while (N >= 0)
	{
		somma = 0;
		valore = 0;
		i = 0;
		printf("Inserisci il valore N: ");
		scanf_s("%d", &N);
		for (i = 0; i <= N; i++)
		{
			valore = ( 4.0 / ((2 * i) + 1));
			if (i%2 == 1)
			{
				valore = -valore;
			}
			somma = somma + valore;
		}
		printf("Risultato: %f\n", somma);
	}
	printf("Programma terminato");
}