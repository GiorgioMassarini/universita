#include <stdio.h>
#include <math.h>

float perimetro(float a, float b, float c)
{
	float perimetro = 0;
	perimetro = a + b + c;
	return perimetro;
}

float area(float a, float b, float c)
{
	float area = 0, semiP = 0;
	semiP = (perimetro(a, b, c) / 2);
	area = sqrt(((semiP) * (semiP - a) * (semiP - b) * (semiP - c)));
	return area;
}


int main()
{
	float a, b, c;
	printf("Inserisci i valori dei lati di un triangolo\n");
	printf("Primo lato: ");
	scanf_s("%f", &a);
	printf("Secondo lato: ");
	scanf_s("%f", &b);
	printf("Terzo lato: ");
	scanf_s("%f", &c);
	while (a < 0 || b < 0 || c < 0)
	{
		printf("Errore!\n");
		printf("Primo lato: ");
		scanf_s("%f", &a);
		printf("Secondo lato: ");
		scanf_s("%f", &b);
		printf("Terzo lato: ");
		scanf_s("%f", &c);
	}
	printf("Valore del perimetro del triangolo: %f", perimetro(a, b, c));
	printf("\nValore dell'area del triangolo: %f\n", area(a, b, c));
}