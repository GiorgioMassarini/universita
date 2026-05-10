#include <stdio.h>
int massimo = 0;
int mioMax(int x, int y)
{
	if (x > y)
	{
		massimo = x;
	}
	else
	{
		massimo = y;
	}
	return massimo;
}

int  max3(int x, int y, int z)
{
	if (mioMax(x, y) == x)
	{
		if (mioMax(x, z) == x)
		{
			massimo = x;
		}
		else
		{
			if (mioMax(y, z) == z)
			{
				massimo = z;
			}
		}
	}
	else
	{
		if (mioMax(y, z) == y)
		{
			massimo = y;
		}
	}
	return massimo;
}



int main()
{
	int x=0, y=0, z=0;
	printf("Primo valore: ");
	scanf_s("%d", &x);
	printf("Secondo valore: ");
	scanf_s("%d", &y);
	printf("terzo valore: ");
	scanf_s("%d", &z);
	printf("Valore massimo: %d", max3(x, y, z));
}