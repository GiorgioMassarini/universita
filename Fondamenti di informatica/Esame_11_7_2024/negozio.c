#include "negozio.h"

Formaggio* leggiFormaggi(char* fileName, int* dim)
{
	FILE* f;
	Formaggio* risultato;
	char nome[32];
	char tipo[16];
	float prezzo;
	float quantita;
	int conta = 0;
	(*dim) = 0;
	f = fopen(fileName, "r");
	if (f == NULL)
	{
		printf("Errore nell'apertura del file!");
		return NULL;
	}
	while (fscanf(f, "%s %s %f %f", nome, tipo, &prezzo, &quantita) == 4)
	{
		(*dim)++;
	}
	rewind(f);
	risultato = (Formaggio*)malloc((sizeof(Formaggio)) * (*dim));
	while (fscanf(f, "%s %s %f %f", nome, tipo, &prezzo, &quantita) == 4)
	{
		strcpy(risultato[conta].nome, nome);
		strcpy(risultato[conta].tipo, tipo);
		risultato[conta].prezzo = prezzo;
		risultato[conta].quantita = quantita;
		conta = conta + 1;
	}
	fclose(f);
	return risultato;
}

list filtraFormaggi(Formaggio* elenco, int dim, char* tipo, float maxPrice)
{
	list risultato;
	int i = 0;
	risultato = emptylist();
	if (maxPrice <= 0)
	{
		for (i = 0; i < dim; i++)
		{
			if (strcmp(elenco[i].tipo, tipo) == 0)
			{
				risultato = cons(elenco[i], risultato);
			}
		}
		return risultato;
	}
	for (i = 0; i < dim; i++)
	{
		if ((strcmp(elenco[i].tipo, tipo) == 0)&&(elenco[i].prezzo<=maxPrice))
		{
			risultato = cons(elenco[i], risultato);
		}
	}
	return risultato;
}

void stampaFormaggi(list elenco)
{
	Formaggio temp;
	if (empty(elenco))
	{
		return 0;
	}
	else
	{
		temp = head(elenco);
		printf("Nome: %s, Tipo: %s, Prezzo al kg: %.2f, quantita': %.2f\n", temp.nome, temp.tipo, temp.prezzo, temp.quantita);
		elenco = tail(elenco);
		stampaFormaggi(elenco); //FUNZIONE RICORSIVA
	}
}

//FUNZIONI PER L'ORDINAMENTO DI UNA STRUTTURA (quickSort)

void scambia(Formaggio* a, Formaggio* b)
{
	Formaggio tmp = *a;
	*a = *b;
	*b = tmp;
}

int compare(Formaggio t1, Formaggio t2) //IMPONE LE CONDIZIONI DELL'ORDINAMENTO
{
	int result;
	result = strcmp(t1.tipo, t2.tipo); //CONDIZIONE 1, LESSICOGRAFICO TIPO
	if (result == 0) 
		result = strcmp(t1.nome, t2.nome); //CONDIZIONE 2, LESSICOGRAFICO NOME
	/*
	if (result == 0)                 //CONDIZIONE 3, COSTO MAGGIORE/MINORE
	{
		if (t1.costo < t2.costo)
			result = -1;
		else
			if (t1.costo > t2.costo)
				result = 1;
	}
	*/
	return result;
}

void quickSortR(Formaggio a[], int iniz, int fine)
{
	int i, j, iPivot;
	Formaggio pivot;
	if (iniz < fine)
	{
		i = iniz;
		j = fine;
		iPivot = fine;
		pivot = a[iPivot];
		do /* trova la posizione del pivot */
		{
			while (i < j && compare(a[i], pivot) <= 0) i++;
			while (j > i && compare(a[j], pivot) >= 0) j--;
			if (i < j) scambia(&a[i], &a[j]);
		} while (i < j);
		if (i != iPivot && compare(a[i], a[iPivot]))
		{
			scambia(&a[i], &a[iPivot]);
			iPivot = i;
		}
		/* ricorsione sulle sottoparti, se necessario */
		if (iniz < iPivot - 1)
			quickSortR(a, iniz, iPivot - 1);
		if (iPivot + 1 < fine)
			quickSortR(a, iPivot + 1, fine);
	} /* (iniz < fine) */
}/* quickSortR */


void quickSort(Formaggio* a, int dim)
{
	quickSortR(a, 0, dim - 1);
}

void ordina(Formaggio* elenco, int dim)
{
	quickSort(elenco, dim);
}

//FINE FUNZIONI PER L'ORDINAMENTO DI UNA STRUTTURA (quickSort)

float controvalore(Formaggio* elenco, int dim, char* tipo)
{
	float risultato = 0;
	int i = 0;
	for (i = 0; i < dim; i++)
	{
		if (strcmp(elenco[i].tipo, tipo) == 0)
		{
			risultato = risultato + ((elenco[i].prezzo) * (elenco[i].quantita));
		}
	}
	return risultato;
}

void stampaControvalori(Formaggio* elenco, int dim)
{
	int i=0;
	if (strcmp(elenco[0].tipo, elenco[1].tipo) != 0)
	{
		printf("Il controvalore per i formaggi di tipo %s e' di: %.2f\n", elenco[i].tipo, controvalore(elenco, dim, elenco[i].tipo));
	}
	for (i = 1; i < dim; i++)
	{
		if (strcmp(elenco[i].tipo, elenco[i - 1].tipo) == 0)
		{
			void;
		}
		else
		{
			printf("Il controvalore per i formaggi di tipo %s e' di: %.2f\n", elenco[i].tipo, controvalore(elenco, dim, elenco[i].tipo));
		}
	}
}