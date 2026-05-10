#include <stdio.h>
int main()
{
	int numeri, conta=0, valore, i=1,somma=0,fattoriale=1;
	printf("Quanti numeri vuoi inserire?: ");
	scanf_s("%d", &numeri);
	while (conta < numeri)
	{
		printf("Valore: ");
		scanf_s("%d", &valore);
		fattoriale = 1;
		i = 1;
		while (i <= valore)
		{
			fattoriale = fattoriale * i;
			i = i + 1;
		}
		somma = somma + fattoriale;
		conta = conta + 1;
	}
	printf("Risultato: %d", somma);
}