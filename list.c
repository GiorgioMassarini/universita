#include "list.h"

/* OPERAZIONI PRIMITIVE */

list emptylist(void) //costruisce una lista vuota
{
	return NULL;
}

boolean empty(list l) //verifica se la lista e' vuota
{
	return (l == NULL);
}

list cons(element e, list l) //aggiunge un elemento in testa alla lista
{
	list t;
	t = (list)malloc(sizeof(item));
	t->value = e;
	t->next = l;
	return(t);
}

element head(list l) //seleziona la testa della lista
{
	if (empty(l)) exit(-2);
	else return (l->value);
}

list tail(list l) /* seleziona la coda della lista */
{
	if (empty(l)) exit(-1);
	else return (l->next);
}

void showlist(list l) //printa la lista a video
{
	element temp;
	if (!empty(l)) {
		temp = head(l);
		printf("%s %s %s %d %d \n", temp.codice, temp.descrizione, temp.settore, temp.esperienza, temp.stipendio);
		showlist(tail(l));
		return;
	}
	else {
		printf("\n\n");
		return;
	}
}

void freelist(list l) //libera la memoria impegnata da una lista
{
	if (empty(l))
		return;
	else {
		freelist(tail(l));
		free(l);
	}
	return;
}
