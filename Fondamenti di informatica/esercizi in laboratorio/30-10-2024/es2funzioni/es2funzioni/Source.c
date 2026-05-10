#include <stdio.h>

int somma(int n)
{
	int k = 0, tot=0;
	while (k <= n)
	{
		tot = tot + k;
		k = k + 1;
	}
	return tot;
}
int somma2(int n)
{
	int i, tot = 0;
	for (i = 1; i <= n; i++)
	{
		tot = tot + somma(i);
	}
	return tot;
}

int main()
{
	int n = 0;
	printf("Valore n: ");
	scanf_s("%d", &n);
	printf("Risultato: %d", somma2(n));
}