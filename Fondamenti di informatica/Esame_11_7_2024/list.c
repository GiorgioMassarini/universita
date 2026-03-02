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
		printf("%d ", temp);
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
/*
boolean contains(list l, element e) //controlla se un elemento e' contenuto in una lista
{
	boolean found = false;
	while (!empty(l) && !found)
	{
		found = (head(l) == e);
		l = tail(l);
	}
	return found;
}

list intersect(list l1, list l2) //crea una terza lista contenente l'intersezione delle due liste date (no elementi ripetuti)
{
	list l3;
	element value;
	l3 = emptylist();
	while (!empty(l1))
	{
		value = head(l1);
		if ((contains(l1, value)) && (contains(l2, value)) && !(contains(l3, value)))
		{
			l3 = cons(value, l3);
		}
		l1 = tail(l1);
	}
	return l3;
}

list diff(list l1, list l2) //crea una terza lista con la differenza [l1-l2] (gli elementi di l1 non presenti in l2) (no ripetizioni)
{
	list l3;
	element value;
	l3 = emptylist();
	while (!empty(l1))
	{
		value = head(l1);
		if (!contains(l2, value) && !contains(l3, value))
		{
			l3 = cons(value, l3);
		}
		l1 = tail(l1);
	}
	return l3;
}
*/