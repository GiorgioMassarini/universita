#include "list.h"

list leggi_posizioni(char* fileName)
{
	FILE* f;
	Posizione temp;
	list risultato;
	char codice[6];
	char descrizione[32];
	char settore[2];
	int esperienza;
	int stipendio;;
	f = fopen(fileName, "r");
	if (f == NULL)
	{
		printf("Errore nell'apertura del file!");
		exit(-1);
	}
	risultato = emptylist();
	while (fscanf(f, "%s %s %c %d %d", codice, descrizione, settore, &esperienza, &stipendio) == 5)
	{
		strcpy(temp.codice, codice);
		strcpy(temp.descrizione, descrizione);
		settore[1] = '\0';
		strcpy(temp.settore, settore);
		temp.esperienza = esperienza;
		temp.stipendio = stipendio;
		risultato = cons(temp, risultato);
	}
	fclose(f);
	return risultato; //se la lettura � andata male restituisce gi� cos� una lista vuota
}

Candidato* leggi_candidati(FILE* file, int* dim)
{
	(*dim) = 0;
	char nome[48];
	char settore[2];
	int esperienza;
	int conta = 0;
	Candidato* risultato;
	while (fscanf(file, "%s %c %d", nome, settore, &esperienza) == 3)
	{
		(*dim)++;
	}
	rewind(file);
	risultato = (Candidato*)malloc((sizeof(Candidato)) * (*dim));
	while (fscanf(file, "%s %c %d", nome, settore, &esperienza) == 3)
	{
		nome[47] = '\0';
		strcpy(risultato[conta].nome, nome);
		settore[1] = '\0';
		strcpy(risultato[conta].settore, settore);
		risultato[conta].esperienza = esperienza;
		conta = conta + 1;
	}
	return risultato;
}

Candidato* trova_candidati(char* p_id, list p, Candidato* c, int dim_c, int* dim)
{
	boolean trovato;
	Candidato* risultato;
	trovato = false;
	list temp;
	element temporaneo;
	(*dim) = 0;
	int i = 0;
	int conta = 0;
	temp = p;
	while (!empty(temp))
	{
		temporaneo = head(temp);
		if (strcmp(temporaneo.codice, p_id) == 0)
		{
			trovato = true;
		}
		temp = tail(temp);
	}
	if (trovato == false)
	{
		return NULL; //puntatore a NULL
	}
	temp = p;
	while (!empty(temp))
	{
		temporaneo = head(temp);
		if (strcmp(temporaneo.codice, p_id) == 0)
		{
			for (i = 0; i < dim_c; i++)
			{
				if (strcmp(temporaneo.settore, c[i].settore) == 0)
				{
					if (temporaneo.esperienza <= c[i].esperienza)
					{
						(*dim)++;
					}
				}
			}
		}
		temp = tail(temp);
	}
	risultato = (Candidato*)malloc((sizeof(Candidato)) * (*dim));
	temp = p;
	while (!empty(temp))
	{
		temporaneo = head(temp);
		if (strcmp(temporaneo.codice, p_id) == 0)
		{
			for (i = 0; i < dim_c; i++)
			{
				if (strcmp(temporaneo.settore, c[i].settore) == 0)
				{
					if (temporaneo.esperienza <= c[i].esperienza)
					{
						strcpy(risultato[conta].nome, c[i].nome);
						strcpy(risultato[conta].settore, c[i].settore);
						risultato[conta].esperienza = c[i].esperienza;
						conta = conta + 1;
					}
				}
			}
		}
		temp = tail(temp);
	}
	return risultato;
}
