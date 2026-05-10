#include <stdio.h>

int isPitagora(int a, int b, int c)
{
	if (a * a == b * b + c * c)
	{
		return 1;
	}
	if (b * b == a * a + c * c)
	{
		return 1;
	}
	if (c * c == a * a + b * b)
	{
		return 1;
	}
	return 0;
}


int main()
{
	int n,i,j,k;
	printf("Inserisci valore n: ");
	scanf_s("%d", &n);
	for (i = 1; i <= n; i++)
	{
		for (j = i; j <= n; j++)
		{
			for (k = j; k <= n; k++)
			{
				if (isPitagora(i, j, k))
				{
					printf("\n%d-%d-%d", i, j, k);
				}
			}
		}
	}
}