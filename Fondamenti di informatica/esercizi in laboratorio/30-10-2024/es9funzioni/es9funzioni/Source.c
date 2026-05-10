#include <stdio.h>

int isprimo(int x)
{
	int i, primo = 1;
	for (i = 2; i < x; i++)
	{
		if (x % i == 0)
		{
			primo = 0;
		}
	}
	return primo;
}

int main()
{
	int n, conta = 0;
	printf("Valore N: ");
	scanf_s("%d", &n);
	printf("Numeri primi: ");
	while (conta < n)
	{
		if (isprimo(conta) == 1)
		{
			printf("%d ", conta);
		}
		conta = conta + 1;
	}
}